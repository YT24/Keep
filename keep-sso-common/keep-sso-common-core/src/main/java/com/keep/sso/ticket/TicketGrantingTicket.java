package com.keep.sso.ticket;

import java.io.Serializable;
import java.util.HashSet;


public class TicketGrantingTicket implements Ticket, Serializable {
    private static final long serialVersionUID = -608429899098767392L;


    private String id;

    private String user;

    //@Column(name = "time_to_live")
    private long expMax;

    //@Column(name = "time_to_idle")
    private long expIdle;

    private long ct;

    private int cou;

    /**
     * descendant_tickets
     */
    private HashSet<String> dt = new HashSet<>();

    private Boolean expired = Boolean.FALSE;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean expired() {
        return false;
    }
}
