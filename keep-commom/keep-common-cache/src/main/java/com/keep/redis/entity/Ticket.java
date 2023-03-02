package com.keep.redis.entity;

import lombok.Data;

public interface Ticket {

    String getId();

    boolean expired();
}
