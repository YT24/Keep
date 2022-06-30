package para.sso.ticket.registry;

import com.paraview.session.hanlder.ParaJdbcSessionHandler;
import com.paraview.session.hanlder.ParaRedisSessionHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import para.sso.authentication.Authentication;
import para.sso.configuration.CasConfigurationProperties;
import para.sso.redis.SSORedisTemplate;
import para.sso.ticket.Ticket;
import para.sso.ticket.TicketGrantingTicket;
import para.sso.ticket.TicketGrantingTicketImpl;
import para.sso.ticket.conversion.ITicketConversionHandler;
import para.sso.ticket.conversion.TicketConversionManager;
import para.sso.ticket.message.RedisMessagePublisher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Key-value ticket registry implementation that stores tickets in redis keyed on the ticket ID.
 *
 * @author serv
 * @since 5.1.0
 */
@SuppressWarnings("unchecked")
public class MultiLayerCacheTicketRegistry extends AbstractTicketRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiLayerCacheTicketRegistry.class);

    private static final String CAS_TICKET_PREFIX = "CAS_TICKET:";
    private static final String NO_REDIS_CLIENT_IS_DEFINED = "No redis client is defined.";
    private static final String TICKET_MAPPING_PREFIX = "TICKET_MAPPING:";
    private static final String SESSION_MGR_PREFIX = "SESSION_MGR_PREFIX:";
    public static final String TICKET_V2 = "V2";
    public static final String REDIS_PUB_DELETE = "delete:";
    public static final String REDIS_PUB_DELETE_ALL = "deleteAll";
    public static final int SYNC_TYPE_NO_OPS = 0;
    public static final int SYNC_TYPE_SYNC_LOCAL_CACHE = 1;
    public static final int SYNC_TYPE_SYNC_LOCAL_AND_REDIS = 2;

    private RedisTicketRegistry redisTicketRegistry;

    private CachingTicketRegistry cachingTicketRegistry;

    @Autowired
    @Qualifier("jpaShardingTicketRegistry")
    private JpaShardingTicketRegistry jpaShardingTicketRegistry;

    @Autowired
    private TicketConversionManager ticketConversionManager;

    @Autowired
    private CasConfigurationProperties casProperties;

    private RedisMessagePublisher redisPublisher;

    @Autowired
    @Qualifier("jpaTicketExecutor")
    private ExecutorService jpaTicketExecutor;

    @Autowired
    private HttpServletRequest request;

    @PersistenceContext(name = "shardingPersistenceUnit", unitName = "shardingEntityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private SSORedisTemplate ssoRedisTemplate;


    @Autowired
    private ParaJdbcSessionHandler jdbcSessionHandler;

    @Autowired
    private ParaRedisSessionHandler redisSessionHandler;

    private volatile Integer redisExceptionCounts = 0;

    private boolean redisHeartBeatSuccess = true;

    public MultiLayerCacheTicketRegistry(CachingTicketRegistry cachingTicketRegistry,
            RedisTicketRegistry redisTicketRegistry, RedisMessagePublisher redisPublisher) {
        this.cachingTicketRegistry = cachingTicketRegistry;
        this.redisTicketRegistry = redisTicketRegistry;
        this.redisPublisher = redisPublisher;
    }

    @Scheduled(initialDelayString = "${sso.redisStatus.startDelay:1000}",
            fixedDelayString = "${sso.redisStatus.repeatInterval:5000}")
    public void redisHeartBeatCheck() {
        try {
            ssoRedisTemplate.opsForValue().set("flag", "true");
            if (redisExceptionCounts > 5) {
                setRedisHeartBeatSuccess(true);
                ssoRedisTemplate.setRedisHeartBeatSuccess(true);
                LOGGER.info("present environment : Redis!");
                ssoRedisTemplate.clearExpiredRecords();

                try {
                    redisSessionHandler.switchSession();
                } catch (IllegalAccessException illegalAccessException) {
                    LOGGER.error("illegalAccessException", illegalAccessException);
                } catch (InvocationTargetException invocationTargetException) {
                    LOGGER.error("invocationTargetException", invocationTargetException);
                } catch (InstantiationException instantiationException) {
                    LOGGER.error("instantiationException", instantiationException);
                }
            }
            redisExceptionCounts = 0;
        } catch (RuntimeException e) {
            redisExceptionCounts++;
            LOGGER.info("redis exception count: {}", redisExceptionCounts);
            if (redisExceptionCounts > 5 && redisExceptionCounts < 10) {
                setRedisHeartBeatSuccess(false);
                ssoRedisTemplate.setRedisHeartBeatSuccess(false);
                LOGGER.info("present environment : NonRedis!");

                try {
                    jdbcSessionHandler.switchSession();
                } catch (IllegalAccessException illegalAccessException) {
                    LOGGER.error("illegalAccessException", illegalAccessException);
                } catch (InvocationTargetException invocationTargetException) {
                    LOGGER.error("invocationTargetException", invocationTargetException);
                } catch (InstantiationException instantiationException) {
                    LOGGER.error("instantiationException", instantiationException);
                }
            }
        }
    }

    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        jpaTicketExecutor.submit(() -> {
            jpaShardingTicketRegistry.deleteTicket(ticketId);

            //异步删除，如果删除前请求进入调用get方法，会获取到并且补偿本地缓存和redis，为了防止这种情况，这里再删除一次。
            cachingTicketRegistry.deleteSingleTicket(ticketId);
            if (isRedisHeartBeatSuccess()) {
                redisTicketRegistry.deleteSingleTicket(ticketId);
                redisPublisher.publish(REDIS_PUB_DELETE + ticketId);
            }
        });
        cachingTicketRegistry.deleteSingleTicket(ticketId);
        if (isRedisHeartBeatSuccess()) {
            redisPublisher.publish(REDIS_PUB_DELETE + ticketId);
            redisTicketRegistry.deleteSingleTicket(ticketId);
        }
        return true;
    }

    @Override
    public void addTicket(Ticket ticket) {
        ITicketConversionHandler ticketConversionHandler = ticketConversionManager.getHandler(ticket.getPrefix());
        Ticket simpleTicket = null;
        if (ticketConversionHandler != null) {
            simpleTicket = ticketConversionHandler.convertToSimple(ticket);
        }
        String username = "";
        if (ticket.getPrefix().equals(TicketGrantingTicket.PREFIX)) {
            TicketGrantingTicketImpl ticketGrantTicketImpl = (TicketGrantingTicketImpl) ticket;
            username = ticketGrantTicketImpl.getAuthentication().getPrincipal().getId();
        }
        cachingTicketRegistry.addTicket(simpleTicket != null ? simpleTicket : ticket);
        if (isRedisHeartBeatSuccess()) {
            redisTicketRegistry.addTicket(simpleTicket != null ? simpleTicket : ticket);
            redisTicketRegistry.addTGTMappings(simpleTicket !=null?simpleTicket.getId() : ticket.getId(), username, request);
        }
        Ticket finalSimpleTicket = simpleTicket;
        LOGGER.info(ticket.getId());
        //jpaShardingTicketRegistry.addTicket(finalSimpleTicket);
        jpaTicketExecutor.submit(() -> jpaShardingTicketRegistry.addTicket(finalSimpleTicket));
    }

    @Override
    public Ticket getTicket(final String ticketId) {

        if (StringUtils.isBlank(ticketId)) {
            return null;
        }

        if (!isTicketNewVersion(ticketId)) {
            return redisTicketRegistry.getTicket(ticketId);
        }

        Ticket ticket = getTicketV2(ticketId);

        return ticket;

    }

    @Override
    public long deleteAll() {
        jpaTicketExecutor.submit(() -> jpaShardingTicketRegistry.deleteAll());
        long size = cachingTicketRegistry.deleteAll();
        if (isRedisHeartBeatSuccess()) {
            redisPublisher.publish(REDIS_PUB_DELETE_ALL);
            size = redisTicketRegistry.deleteAll();
        }
        return size;
    }

    @Override
    public Collection<Ticket> getTickets() {
        return redisTicketRegistry.getTickets();
    }

    @Override
    public List<Ticket> getTicketsByUsername(String username) {
        return redisTicketRegistry.getTicketsByUsername(username);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        if (isTicketNewVersion(ticket.getId())) {
            ITicketConversionHandler ticketConversionHandler = ticketConversionManager.getHandler(ticket.getPrefix());
            if (ticketConversionHandler != null) {
                ticket = ticketConversionHandler.convertToSimple(ticket);
            }
        }
        Ticket finalTicket = ticket;
        jpaTicketExecutor.submit(() -> jpaShardingTicketRegistry.updateTicket(finalTicket));
        cachingTicketRegistry.updateTicket(ticket);
        if (isRedisHeartBeatSuccess()) {
            redisTicketRegistry.updateTicket(ticket);
        }
        return ticket;
    }

    @Override
    public Ticket getTicketToReUse(Authentication authentication) {
        if (!isRedisHeartBeatSuccess()) {
            return null;
        }
        redisTicketRegistry.setRequest(this.request);
        return redisTicketRegistry.getTicketToReUse(authentication);
    }

    @Override
    public Stream<? extends Ticket> getTicketsStream(Stream<String> keysStream) {
        return redisTicketRegistry.getTicketsStream(keysStream);
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

    private static String getPatternTGTRedisKey() {
        return CAS_TICKET_PREFIX + "TGT-*";
    }

    // pattern all ticket redisKey
    private static String getPatternTicketRedisKey() {
        return CAS_TICKET_PREFIX + "*";
    }

    /**
     * If not time out value is specified, expire the ticket immediately.
     *
     * @param ticket the ticket
     * @return timeout
     */
    private static int getTimeout(final Ticket ticket) {
        final int ttl = ticket.getExpirationPolicy().getTimeToLive().intValue();
        if (ttl == 0) {
            return 1;
        }
        return ttl;
    }

    private boolean isTicketNewVersion(String ticketId) {
        String ticketVersion = null;
        String[] strArray = ticketId.split("-");
        if (strArray != null && strArray.length > 1) {
            ticketVersion = strArray[1];
        }
        return TICKET_V2.equals(ticketVersion);
    }

    private Ticket getTicketV2(String ticketId) {
        // 1.查询本地缓存
        Ticket ticket = cachingTicketRegistry.getTicket(ticketId);
        // 2. 查询redis
        int syncType = SYNC_TYPE_NO_OPS;//0 不补偿
        if (ticket == null && isRedisHeartBeatSuccess()) {
            ticket = redisTicketRegistry.getTicket(ticketId);
            // 更新本地缓存
            if (ticket != null) {
                syncType = SYNC_TYPE_SYNC_LOCAL_CACHE;//更新本地缓存
            }
        }
        // 3. 查询db
        if (ticket == null) {
            ticket = jpaShardingTicketRegistry.getTicket(ticketId);
            if (ticket != null) {
                syncType = SYNC_TYPE_SYNC_LOCAL_AND_REDIS;//更新本地缓存和redis
            }
        }
        if (null != ticket) {
            switch (syncType) {
                case SYNC_TYPE_SYNC_LOCAL_CACHE:
                    cachingTicketRegistry.addTicket(ticket);
                    break;
                case SYNC_TYPE_SYNC_LOCAL_AND_REDIS:
                    cachingTicketRegistry.addTicket(ticket);
                    if (isRedisHeartBeatSuccess()) {
                        redisTicketRegistry.addTicket(ticket);
                    }
                    break;
                default:
                    break;
            }

            ITicketConversionHandler ticketConversionHandler = ticketConversionManager.getHandler(ticket.getPrefix());
            if (ticketConversionHandler != null) {
                ticket = ticketConversionHandler.convertSimpleToOriginal(ticket, ticketId, this);
            }
        }
        return ticket;
    }

    @Override
    public long deleteAll(Set<String> tickets) {
        redisTicketRegistry.deleteAll(tickets);
        Future<Long> longFuture = jpaTicketExecutor.submit(() -> jpaShardingTicketRegistry.deleteAll(null));
        long count = 0l;
        try {
             count = longFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean isRedisHeartBeatSuccess() {
        return redisHeartBeatSuccess;
    }

    public void setRedisHeartBeatSuccess(boolean redisHeartBeatSuccess) {
        this.redisHeartBeatSuccess = redisHeartBeatSuccess;
    }
}
