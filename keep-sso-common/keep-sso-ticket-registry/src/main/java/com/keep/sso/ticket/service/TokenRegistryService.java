package com.keep.sso.ticket.service;

import com.keep.sso.ticket.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TokenRegistryService{

    <S extends Ticket> void addToken(S token);

    Optional<Ticket> getTgtByUserName(String username,String deviceType);

    void deleteTicket(String ticketId,Class clazz);

    void updateToken(Ticket tgt);

//    Ticket getTicketById(String token);
}
