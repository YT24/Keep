package para.sso.ticket.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import para.sso.ticket.SimpleAccessTokenImpl;
import para.sso.ticket.SimpleRefreshTokenImpl;
import para.sso.ticket.SimpleTicketGrantingTicket;
import para.sso.ticket.Ticket;

import java.io.IOException;

public class TicketUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketUtils.class);

    private static final ObjectMapper MAPPER;

    // following good software practices, utils can not have constructors
    private TicketUtils() {}

    static {
        MAPPER = new ObjectMapper();
        //        module.addDeserializer(Ticket.class, new TicketDeserializer());
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setVisibility(MAPPER.getSerializationConfig()
                        .getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static String buildJSONFromMessage(final Ticket message)
            throws JsonProcessingException {
        return MAPPER.writeValueAsString(message);
    }

    public static Ticket buildMessageFromJSON(String ticketId, final String jsonMessage)
            throws IOException {
        if (ticketId.startsWith("AT-")) {
            SimpleAccessTokenImpl ticket = MAPPER.readValue(jsonMessage, SimpleAccessTokenImpl.class);
            ticket.setId(ticketId);
            return ticket;
        } else if (ticketId.startsWith("RT-")) {
            SimpleRefreshTokenImpl ticket = MAPPER.readValue(jsonMessage, SimpleRefreshTokenImpl.class);
            ticket.setId(ticketId);
            return ticket;
        } else if (ticketId.startsWith("TGT-")) {
            SimpleTicketGrantingTicket ticket = MAPPER.readValue(jsonMessage, SimpleTicketGrantingTicket.class);
            ticket.setId(ticketId);
            return ticket;
        }
        return MAPPER.readValue(jsonMessage, Ticket.class);
    }

    public static String objectToString(Object obj) {
        String result = null;
        try {
            result = MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}
