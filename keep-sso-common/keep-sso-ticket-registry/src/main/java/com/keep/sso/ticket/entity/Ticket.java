package com.keep.sso.ticket.entity;

public interface Ticket {

    boolean expired();


    String getUsername();

    String getId();

    String getDeviceType();
}
