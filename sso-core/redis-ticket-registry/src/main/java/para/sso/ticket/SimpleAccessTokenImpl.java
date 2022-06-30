package para.sso.ticket;

import para.sso.ticket.accesstoken.AccessToken;
import para.sso.ticket.accesstoken.OAuthAccessTokenExpirationPolicy;

import javax.persistence.Entity;

@Entity
public class SimpleAccessTokenImpl extends SimpleOAuthToken {
    private static final long serialVersionUID = -60175457238560722L;

    public SimpleAccessTokenImpl() {
    }

    public SimpleAccessTokenImpl(String id, String user, long expirationPolicyMaxTimeToLiveInSeconds,
            long expirationPolicyTimeToKillInSeconds, long lastTimeUsed,
            long creationTime, int countOfUses, Boolean expired,
            String ticketGrantingTicket_ID, String sId, String sOUrl, String sAId) {
        super(id, user, expirationPolicyMaxTimeToLiveInSeconds, expirationPolicyTimeToKillInSeconds, lastTimeUsed,
                creationTime, countOfUses, expired,
                ticketGrantingTicket_ID, sId, sOUrl, sAId);
    }

    @Override
    public ExpirationPolicy getExpirationPolicy() {
        return new OAuthAccessTokenExpirationPolicy(expMax,
                expIdle);
    }

    @Override
    public String getPrefix() {
        return AccessToken.PREFIX;
    }
}
