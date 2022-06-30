package para.sso.ticket;

import para.sso.ticket.refreshtoken.OAuthRefreshTokenExpirationPolicy;
import para.sso.ticket.refreshtoken.RefreshToken;

import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
public class SimpleRefreshTokenImpl extends SimpleOAuthToken {
    private static final long serialVersionUID = 2171951964928645996L;

    public SimpleRefreshTokenImpl() {
    }

    public SimpleRefreshTokenImpl(String id, String user, long expirationPolicyMaxTimeToLiveInSeconds,
            long expirationPolicyTimeToKillInSeconds, long lastTimeUsed,
            long creationTime, int countOfUses, Boolean expired,
            String ticketGrantingTicket_ID, String sId, String sOUrl, String sAId) {
        super(id, user, expirationPolicyMaxTimeToLiveInSeconds, expirationPolicyTimeToKillInSeconds, lastTimeUsed,
                creationTime, countOfUses, expired,
                ticketGrantingTicket_ID, sId, sOUrl, sAId);
    }

    @Override
    public ExpirationPolicy getExpirationPolicy() {
        return new OAuthRefreshTokenExpirationPolicy(expMax);
    }

    @Override
    public String getPrefix() {
        return RefreshToken.PREFIX;
    }
}
