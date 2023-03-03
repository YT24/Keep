package keep.sso.ticket.service;

import keep.sso.ticket.entity.Ticket;

public interface TokenRegistryService<T>{

    <S extends Ticket> void addToken(S token);
}
