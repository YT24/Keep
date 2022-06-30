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
import para.sso.ticket.SimpleRefreshTokenImpl;
import para.sso.ticket.Ticket;
import para.sso.ticket.TicketGrantingTicketImpl;
import para.sso.ticket.refreshtoken.RefreshToken;
import para.sso.ticket.refreshtoken.RefreshTokenImpl;
import para.sso.ticket.registry.TicketRegistry;
import para.sso.ticket.support.TicketGrantingTicketExpirationPolicy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RefreshTokenConversionHandler implements InitializingBean, ITicketConversionHandler {
    @Autowired
    private TicketConversionManager ticketConversionManager;

    public PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    @Override
    public void afterPropertiesSet() throws Exception {
        ticketConversionManager.setHandler(RefreshToken.PREFIX, this);
    }

    @Override
    public Ticket convertToSimple(Ticket ticket) {
        RefreshTokenImpl refreshToken = (RefreshTokenImpl) ticket;
        SimpleWebApplicationServiceImpl service = (SimpleWebApplicationServiceImpl) refreshToken.getService();
        SimpleRefreshTokenImpl srt = new SimpleRefreshTokenImpl(refreshToken.getId(),
                refreshToken.getAuthentication().getPrincipal().getId(),
                refreshToken.getExpirationPolicy().getTimeToLive(),
                refreshToken.getExpirationPolicy().getTimeToIdle(), refreshToken.getLastTimeUsed().toInstant().toEpochMilli(),
                refreshToken.getCreationTime().toInstant().toEpochMilli(),
                refreshToken.getCountOfUses(), refreshToken.isExpired(),
                refreshToken.getGrantingTicket().getId(),
                service == null ? null : service.getId(),
                service == null ? null : service.getOriginalUrl(),
                service == null ? null : service.getArtifactId());
        return srt;
    }

    @Override
    public Ticket convertSimpleToOriginal(Ticket ticket, String ticketId, TicketRegistry ticketRegistry) {
        SimpleRefreshTokenImpl srt = (SimpleRefreshTokenImpl) ticket;
        srt.setId(ticketId);
        Principal principal = principalFactory.createPrincipal(srt.getUser());
        CredentialMetaData metadata = new BasicCredentialMetaData(new BasicIdentifiableCredential(srt.getUser()));

        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.ofEpochMilli(srt.getCt());
        ZonedDateTime createTime = ZonedDateTime.ofInstant(instant, zoneId);

        Authentication authentication = new DefaultAuthentication(createTime, Stream
                .of(metadata).collect(Collectors.toList()), principal, new HashMap<>(),
                new HashMap<>(), new HashMap<>());
        ExpirationPolicy expirationPolicy = srt.getExpirationPolicy();

        SimpleWebApplicationServiceImpl service = new SimpleWebApplicationServiceImpl(srt.getsId(),
                srt.getsOUrl(), srt.getsAId());

        Ticket tgtFromRedis = ticketRegistry.getTicket(srt.getTgtId());
        if (tgtFromRedis == null) {
            return null;
        }
        TicketGrantingTicketImpl tgt = (TicketGrantingTicketImpl) tgtFromRedis;

        RefreshTokenImpl accessToken = new RefreshTokenImpl(srt.getId(), service, authentication, expirationPolicy,
                tgt);
        return accessToken;
    }
}
