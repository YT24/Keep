package para.sso.ticket.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import para.sso.authentication.Authentication;
import para.sso.ticket.*;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @description: 自定义TicketRegistry实现
 * @author: caohh
 * @date: 2020/12/23 18:53
 **/
//@Component("jpaShardingTicketRegistry")
@EnableTransactionManagement(proxyTargetClass = true)
//@Transactional(rollbackFor = Exception.class, transactionManager = "shardingTransactionManager", readOnly = false)
//@ConditionalOnProperty(prefix = "sso.authn", name = "turnOnMultiLayerCache", havingValue = "true")
@SuppressWarnings("all")
public class JpaShardingTicketRegistry extends AbstractTicketRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaShardingTicketRegistry.class);

    private TicketCatalog ticketCatalog;

    private PlatformTransactionManager shardingTransactionManager;

    private EntityManager entityManager;

    public JpaShardingTicketRegistry(TicketCatalog ticketCatalog, PlatformTransactionManager shardingTransactionManager, EntityManager entityManager) {
        this.ticketCatalog = ticketCatalog;
        this.shardingTransactionManager = shardingTransactionManager;
        this.entityManager = entityManager;
    }

    /**
     * 默认值
     */
    private final LockModeType lockType = LockModeType.OPTIMISTIC;



    @Override
    public void addTicket(final Ticket ticket) {
        new TransactionTemplate(shardingTransactionManager).execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
               entityManager.persist(ticket);
                LOGGER.debug("Added ticket [{}] to registry.", ticket);
                return null;
            }
        });

    }

    @Override
    public Ticket getTicket(final String ticketId) {
        return getRawTicket(ticketId);
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public long deleteAll() {
        final Collection<TicketDefinition> tkts = this.ticketCatalog.findAll();
        final AtomicLong count = new AtomicLong();

        new TransactionTemplate(shardingTransactionManager).execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                tkts.forEach(t -> {
                    final String entityName = getTicketEntityName(t);
                    final Query query = entityManager.createQuery("delete from " + entityName);
                    LOGGER.debug("Deleting ticket entity [{}]", entityName);
                    count.addAndGet(query.executeUpdate());
                });
                return null;
            }
        });
        return count.get();
    }

    @Override
    public Collection<Ticket> getTickets() {
        final Collection<TicketDefinition> tkts = this.ticketCatalog.findAll();
        final List<Ticket> tickets = new ArrayList<>();
        tkts.forEach(t -> {
            try {
                final Query query = this.entityManager.createQuery("select t from " + getTicketEntityName(t) + " t",
                        t.getImplementationClass());
                tickets.addAll(query.getResultList());
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        });
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByUsername(String username) {
        return null;
    }

    @Override
    public Ticket updateTicket(final Ticket ticket) {
        this.entityManager.merge(ticket);
        LOGGER.debug("Updated ticket [{}].", ticket);
        return ticket;
    }

    private static String getTicketEntityName(final TicketDefinition tk) {
        return tk.getImplementationClass().getSimpleName();
    }

    /**
     * Gets the ticket from the database, as is.
     * In removals, there is no need to distinguish between TGTs and PGTs since PGTs inherit from TGTs
     *
     * @param ticketId the ticket id
     * @return the raw ticket
     */
    public Ticket getRawTicket(final String ticketId) {
        try {
            final TicketDefinition tkt = this.ticketCatalog.find(ticketId);
            return this.entityManager.find(tkt.getImplementationClass(), ticketId, LockModeType.NONE);
        } catch (final Exception e) {
            LOGGER.error("Error getting ticket [{}] from registry.", ticketId, e);
        }
        return null;
    }

    @Override
    public long sessionCount() {
        final TicketDefinition md = this.ticketCatalog.find(TicketGrantingTicket.PREFIX);
        return countToLong(this.entityManager.createQuery("select count(t) from " + getTicketEntityName(md) + " t")
                .getSingleResult());
    }

    @Override
    public long serviceTicketCount() {
        final TicketDefinition md = this.ticketCatalog.find(ServiceTicket.PREFIX);
        return countToLong(this.entityManager.createQuery("select count(t) from " + getTicketEntityName(md) + " t")
                .getSingleResult());
    }

    @Override
    public Ticket getTicketToReUse(Authentication authentication) {
        return null;
    }

    @Override
    public Stream<? extends Ticket> getTicketsStream(Stream<String> keysStream) {
        return null;
    }

    @Override
    public void removeCleanListValue(String value) {

    }

    @Override
    public List<Object> getCleanList(Long i) {
        return null;
    }

    @Override
    public void addToCleanList(String value) {

    }

    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        final int totalCount;
        final TicketDefinition md = this.ticketCatalog.find(ticketId);

        if (md.getProperties().isCascade()) {
            totalCount = deleteTicketGrantingTickets(ticketId);
        } else {
            new TransactionTemplate(shardingTransactionManager).execute(new TransactionCallback() {
                public Object doInTransaction(TransactionStatus status) {
                    try {
                        final Query query = entityManager
                                .createQuery("delete from " + getTicketEntityName(md) + " o where o.id = :id");
                        query.setParameter("id", ticketId);
                        query.executeUpdate();
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        LOGGER.error(e.getMessage(), e);
                    }
                    return null;
                }
            });
            totalCount = 1;
            // totalCount = query.executeUpdate();
        }
        return totalCount != 0;
    }

    /**
     * Delete ticket granting tickets int.
     *
     * @param ticketId the ticket id
     * @return the int
     */
    private int deleteTicketGrantingTickets(final String ticketId) {
        int totalCount = 0;

        final TicketDefinition st = this.ticketCatalog.find(ServiceTicket.PREFIX);

        Query query = entityManager
                .createQuery("delete from " + getTicketEntityName(st) + " s where s.ticketGrantingTicket.id = :id");
        query.setParameter("id", ticketId);
        totalCount += query.executeUpdate();

        final TicketDefinition tgt = this.ticketCatalog.find(TicketGrantingTicket.PREFIX);
        query = entityManager
                .createQuery("delete from " + getTicketEntityName(tgt) + " t where t.ticketGrantingTicket.id = :id");
        query.setParameter("id", ticketId);
        totalCount += query.executeUpdate();

        query = entityManager.createQuery("delete from " + getTicketEntityName(tgt) + " t where t.id = :id");
        query.setParameter("id", ticketId);
        totalCount += query.executeUpdate();

        return totalCount;
    }

    private static long countToLong(final Object result) {
        return ((Number) result).longValue();
    }


    @Override
    public long deleteAll(Set<String> tickets) {
        final List<String> list = Arrays.asList(new String[]{"pi_ticket_granting_ticket","pi_oauth_tokens"});
        final AtomicLong count = new AtomicLong();
        new TransactionTemplate(shardingTransactionManager).execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                list.forEach(t -> {
                    final Query query = entityManager.createNativeQuery("delete from " + t + " where (UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL time_to_live second))) * 1000 > create_time") ;
                    LOGGER.debug("Deleting ticket entity [{}]", t);
                    count.addAndGet(query.executeUpdate());
                });
                return null;
            }
        });
        return count.get();
    }
}
