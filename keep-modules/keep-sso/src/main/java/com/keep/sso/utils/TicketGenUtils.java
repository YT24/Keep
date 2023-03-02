package com.keep.sso.utils;

import com.keep.common.core.domain.constants.CommanConstants;
import com.keep.redis.entity.Ticket;
import com.keep.redis.entity.TokenCacheTicket;
import com.keep.sso.entity.KeepUser;

import java.util.concurrent.TimeUnit;

public class TicketGenUtils {

    public static Ticket getTokenCacheTicket(Long userId){
        TokenCacheTicket ticket = new TokenCacheTicket ();
        ticket.setId(CommanConstants.USER_PREF+userId);

        return ticket;
    }
}
