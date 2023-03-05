package com.keep.sso.ticket.service;

import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.Ticket;

public interface KeepTokenService {

    boolean saveTicket(Ticket ticket);
}
