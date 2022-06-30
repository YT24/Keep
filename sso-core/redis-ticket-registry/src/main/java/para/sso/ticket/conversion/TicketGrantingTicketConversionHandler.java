package para.sso.ticket.conversion;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import para.sso.authentication.Authentication;
import para.sso.authentication.BasicCredentialMetaData;
import para.sso.authentication.BasicIdentifiableCredential;
import para.sso.authentication.CredentialMetaData;
import para.sso.authentication.DefaultAuthentication;
import para.sso.authentication.principal.DefaultPrincipalFactory;
import para.sso.authentication.principal.Principal;
import para.sso.authentication.principal.PrincipalFactory;
import para.sso.ticket.ExpirationPolicy;
import para.sso.ticket.SimpleTicketGrantingTicket;
import para.sso.ticket.Ticket;
import para.sso.ticket.TicketGrantingTicketImpl;
import para.sso.ticket.registry.TicketRegistry;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TicketGrantingTicketConversionHandler implements InitializingBean, ITicketConversionHandler {
    @Autowired
    private TicketConversionManager ticketConversionManager;

    public PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    @Override
    public void afterPropertiesSet() throws Exception {
        ticketConversionManager.setHandler("TGT", this);
    }

    @Override
    public Ticket convertToSimple(Ticket ticket) {
        TicketGrantingTicketImpl tgt = (TicketGrantingTicketImpl) ticket;
        HashSet<String> descendantTickets = (HashSet<String>) tgt.getDescendantTickets();
        SimpleTicketGrantingTicket stgt = new SimpleTicketGrantingTicket(tgt.getId(),
                tgt.getAuthentication().getPrincipal().getId(), tgt.getExpirationPolicy().getTimeToLive(),
                tgt.getExpirationPolicy().getTimeToIdle(),
                tgt.getLastTimeUsed() == null ? 0 : tgt.getLastTimeUsed().toInstant().toEpochMilli(),
                tgt.getPreviousTimeUsed() == null ? 0 : tgt.getPreviousTimeUsed().toInstant().toEpochMilli(),
                tgt.getCreationTime() == null ? 0 : tgt.getCreationTime().toInstant().toEpochMilli(),
                tgt.getCountOfUses(), tgt.isExpired(), descendantTickets);
        return stgt;
    }

    @Override
    public Ticket convertSimpleToOriginal(Ticket ticket, String ticketId, TicketRegistry ticketRegistry) {
        SimpleTicketGrantingTicket stgt = (SimpleTicketGrantingTicket) ticket;
        stgt.setId(ticketId);
        Principal principal = principalFactory.createPrincipal(stgt.getUser());
        CredentialMetaData metadata = new BasicCredentialMetaData(new BasicIdentifiableCredential(stgt.getUser()));


        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(stgt.getCt());
        ZonedDateTime createTime = ZonedDateTime.ofInstant(instant, zoneId);

        Authentication authentication = new DefaultAuthentication(createTime, Stream
                .of(metadata).collect(Collectors.toList()), principal, new HashMap<>(),
                new HashMap<>(), new HashMap<>());
        ExpirationPolicy expirationPolicy = stgt.getExpirationPolicy();
        TicketGrantingTicketImpl tgt = new TicketGrantingTicketImpl(stgt.getId(), authentication, expirationPolicy);
        tgt.getDescendantTickets().addAll(stgt.getDt());
        return tgt;
    }
}
