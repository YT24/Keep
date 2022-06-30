package para.sso.ticket.conversion;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TicketConversionManager {
    private static final Map<String, ITicketConversionHandler> MAPS = new HashMap<>();

    public ITicketConversionHandler getHandler(String ticketPrefix) {
        ITicketConversionHandler iTicketConversionHandler = MAPS.get(ticketPrefix);
        return iTicketConversionHandler;
    }

    public void setHandler(String type, ITicketConversionHandler iTicketConversionHandler) {
        MAPS.put(type, iTicketConversionHandler);
    }
}
