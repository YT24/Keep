package keep.sso.ticket.policy;

import java.io.Serializable;

public class TicketExpirationPolicy implements ExpirationPolicy {





    @Override
    public boolean isExpired() {
        return false;
    }
}