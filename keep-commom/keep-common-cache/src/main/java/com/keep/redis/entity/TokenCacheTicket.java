package com.keep.redis.entity;

import lombok.Data;

@Data
public class TokenCacheTicket implements Ticket{

    private String id;


    private boolean expired;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean expired() {
        return false;
    }
}
