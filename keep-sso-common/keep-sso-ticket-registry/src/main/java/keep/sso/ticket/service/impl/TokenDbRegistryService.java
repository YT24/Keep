package keep.sso.ticket.service.impl;

import keep.sso.ticket.entity.Ticket;
import keep.sso.ticket.service.TokenRegistryService;
import org.springframework.stereotype.Service;

@Service("tokenDbRegistryService")
public class TokenDbRegistryService implements TokenRegistryService {

    @Override
    public void addToken(Ticket token) {

    }
}
