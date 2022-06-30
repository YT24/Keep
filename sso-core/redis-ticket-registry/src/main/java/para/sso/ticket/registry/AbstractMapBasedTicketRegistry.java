package para.sso.ticket.registry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import para.sso.ticket.Ticket;

import java.util.Collection;
import java.util.Map;

/**
 * This is {@link AbstractMapBasedTicketRegistry}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
public abstract class AbstractMapBasedTicketRegistry extends AbstractTicketRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMapBasedTicketRegistry.class);

    @Override
    public void addTicket(Ticket ticket) {
        Ticket encTicket = encodeTicket(ticket);
        LOGGER.debug("Added ticket [{}] to registry.", ticket.getId());
        getMapInstance().put(encTicket.getId(), encTicket);
    }

    @Override
    public Ticket getTicket(final String ticketId) {
        String encTicketId = encodeTicketId(ticketId);
        if (StringUtils.isBlank(ticketId)) {
            return null;
        }
        Ticket found = getMapInstance().get(encTicketId);
        if (found == null) {
            LOGGER.debug("Ticket [{}] could not be found", encTicketId);
            return null;
        }

        Ticket result = decodeTicket(found);
        return result;
    }

    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        String encTicketId = encodeTicketId(ticketId);
        return !StringUtils.isBlank(encTicketId) && getMapInstance().remove(encTicketId) != null;
    }

    @Override
    public long deleteAll() {
        int size = getMapInstance().size();
        getMapInstance().clear();
        return size;
    }

    @Override
    public Collection<Ticket> getTickets() {
        return decodeTickets(getMapInstance().values());
    }

    @Override
    public Ticket updateTicket(final Ticket ticket) {
        addTicket(ticket);
        return ticket;
    }

    /**
     * Create map instance, which must ben created during initialization phases
     * and always be the same instance.
     *
     * @return the map
     */
    public abstract Map<String, Ticket> getMapInstance();
}
