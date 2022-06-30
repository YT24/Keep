package para.sso.ticket.conversion;

import para.sso.ticket.Ticket;
import para.sso.ticket.registry.TicketRegistry;

public interface ITicketConversionHandler {
    Ticket convertToSimple(Ticket ticket);

    Ticket convertSimpleToOriginal(Ticket ticket, String ticketId, TicketRegistry ticketRegistry);
}
