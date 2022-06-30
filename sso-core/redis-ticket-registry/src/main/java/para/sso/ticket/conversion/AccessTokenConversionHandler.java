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
import para.sso.authentication.principal.SimpleWebApplicationServiceImpl;
import para.sso.ticket.ExpirationPolicy;
import para.sso.ticket.SimpleAccessTokenImpl;
import para.sso.ticket.Ticket;
import para.sso.ticket.TicketGrantingTicketImpl;
import para.sso.ticket.accesstoken.AccessToken;
import para.sso.ticket.accesstoken.AccessTokenImpl;
import para.sso.ticket.registry.TicketRegistry;
import para.sso.ticket.support.TicketGrantingTicketExpirationPolicy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AccessTokenConversionHandler implements InitializingBean, ITicketConversionHandler {
    @Autowired
    private TicketConversionManager ticketConversionManager;

    public PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    @Override
    public void afterPropertiesSet() throws Exception {
        ticketConversionManager.setHandler(AccessToken.PREFIX, this);
    }

    @Override
    public Ticket convertToSimple(Ticket ticket) {
        AccessTokenImpl accessToken = (AccessTokenImpl) ticket;
        SimpleWebApplicationServiceImpl service = (SimpleWebApplicationServiceImpl) accessToken.getService();

        SimpleAccessTokenImpl sat = new SimpleAccessTokenImpl(accessToken.getId(),
                accessToken.getAuthentication().getPrincipal().getId(),
                accessToken.getExpirationPolicy().getTimeToLive(),
                accessToken.getExpirationPolicy().getTimeToIdle(), accessToken.getLastTimeUsed().toInstant().toEpochMilli(),
                accessToken.getCreationTime().toInstant().toEpochMilli(),
                accessToken.getCountOfUses(), accessToken.isExpired(),
                accessToken.getGrantingTicket().getId(),
                service == null ? null : service.getId(),
                service == null ? null : service.getOriginalUrl(),
                service == null ? null : service.getArtifactId());
        return sat;
    }

    @Override
    public Ticket convertSimpleToOriginal(Ticket ticket, String ticketId, TicketRegistry ticketRegistry) {
        SimpleAccessTokenImpl sat = (SimpleAccessTokenImpl) ticket;
        sat.setId(ticketId);
        Principal principal = principalFactory.createPrincipal(sat.getUser());

        CredentialMetaData metadata = new BasicCredentialMetaData(new BasicIdentifiableCredential(sat.getUser()));

        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(sat.getCt());
        ZonedDateTime createTime = ZonedDateTime.ofInstant(instant, zoneId);

        Authentication authentication = new DefaultAuthentication(createTime, Stream
                .of(metadata).collect(Collectors.toList()), principal, new HashMap<>(),
                new HashMap<>(), new HashMap<>());

        ExpirationPolicy expirationPolicy = sat.getExpirationPolicy();

        SimpleWebApplicationServiceImpl service = new SimpleWebApplicationServiceImpl(sat.getsId(),
                sat.getsOUrl(), sat.getsAId());

        Ticket tgtFromRedis = ticketRegistry.getTicket(sat.getTgtId());
        if (tgtFromRedis == null) {
            return null;
        }
        TicketGrantingTicketImpl tgt = (TicketGrantingTicketImpl) tgtFromRedis;

        AccessTokenImpl accessToken = new AccessTokenImpl(sat.getId(), service, authentication, expirationPolicy, tgt);
        return accessToken;
    }
}
