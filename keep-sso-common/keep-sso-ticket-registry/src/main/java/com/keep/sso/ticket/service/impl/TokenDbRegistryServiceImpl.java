package com.keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.manager.TicketRegistryServiceManager;
import com.keep.sso.ticket.service.KeepTokenService;
import com.keep.sso.ticket.service.KeepUserDeviceTypeService;
import com.keep.sso.ticket.service.TokenRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenDbRegistryServiceImpl implements TokenRegistryService {

    @Autowired
    private TicketRegistryServiceManager registryServiceManager;

    @Autowired
    private TokenRedisRegistryServiceImpl tokenRedisRegistryService;

    @Autowired
    private KeepUserDeviceTypeService userDeviceTypeService;

    @Override
    public void addToken(Ticket ticket) {
        KeepTokenService tokenService = registryServiceManager.getService(ticket.getClass());
        tokenService.saveTicket(ticket);
        tokenRedisRegistryService.addToken(ticket);

    }

    @Override
    public Optional<Ticket> getTgtByUserName(String username,String deviceType) {
        return Optional.empty();
    }
}
