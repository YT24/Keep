package com.keep.sso.policy;

import java.io.Serializable;

public interface ExpirationPolicy extends Serializable {


    boolean isExpired(/*TicketState ticketState*/);


    Long getTimeToLive();


    Long getTimeToIdle();
}