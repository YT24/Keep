package com.keep.sso.ticket;

public interface Ticket {

    String getId();

    boolean expired();
}
