package com.keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.KeepUserDeviceType;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.enums.TicketTypeEnum;
import com.keep.sso.ticket.manager.TicketRegistryServiceManager;
import com.keep.sso.ticket.service.KeepTokenService;
import com.keep.sso.ticket.service.KeepUserDeviceTypeService;
import com.keep.sso.ticket.service.TokenRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
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
    }

    @Override
    public Optional<Ticket> getTgtByUserName(String username,String deviceType) {
        QueryWrapper<KeepUserDeviceType> query = new QueryWrapper<>();
        query.lambda().eq(KeepUserDeviceType::getUsername,username);
        query.lambda().eq(KeepUserDeviceType::getDeviceType,deviceType);
        KeepUserDeviceType userDeviceType = userDeviceTypeService.getOne(query);
        if(Objects.nonNull(userDeviceType)){
            KeepTokenService service = registryServiceManager.getService(TicketTypeEnum.getClassByTicketId(userDeviceType.getTgtId()));
            Ticket ticket =  service.selectByTicketId(userDeviceType.getTgtId());
            //回写redis
            tokenRedisRegistryService.addToken(ticket);
            return Optional.ofNullable(ticket);
        }
        return Optional.empty();
    }

    @Override
    public void deleteTicket(String ticketId,Class clazz) {
        KeepTokenService tokenService = registryServiceManager.getService(clazz);
        tokenService.deleteTicket(ticketId);
    }

    public void updateToken(Ticket ticket) {
        KeepTokenService tokenService = registryServiceManager.getService(ticket.getClass());
        tokenService.updateTicket(ticket);

    }

    @Override
    public Ticket getTicketById(String ticketId) {
        KeepTokenService tokenService = registryServiceManager.getService(TicketTypeEnum.getClassByTicketId(ticketId));
        return tokenService.selectByTicketId(ticketId);
    }
}
