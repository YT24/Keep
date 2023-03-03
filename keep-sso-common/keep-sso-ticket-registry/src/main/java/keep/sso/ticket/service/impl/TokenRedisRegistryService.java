package keep.sso.ticket.service.impl;

import keep.sso.ticket.entity.Ticket;
import keep.sso.ticket.service.TokenRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("tokenRedisRegistryService")
public class TokenRedisRegistryService implements TokenRegistryService {

    @Autowired
    @Qualifier("tokenDbRegistryService")
    private TokenRegistryService tokenDbRegistryService;

    @Override
    public void addToken(Ticket token) {

    }
}
