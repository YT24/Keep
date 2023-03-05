package com.keep.sso.ticket.policy;

public class TicketExpirationPolicy implements ExpirationPolicy {





    @Override
    public boolean isExpired() {
        return false;
    }
}