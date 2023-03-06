package com.keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.sso.ticket.entity.KeepAccessToken;
import com.keep.sso.ticket.entity.KeepRefreshToken;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.manager.TicketRegistryServiceManager;
import com.keep.sso.ticket.service.KeepAccessTokenService;
import com.keep.sso.ticket.mapper.KeepAccessTokenMapper;
import com.keep.sso.ticket.service.KeepTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
@Service
public class KeepAccessTokenServiceImpl extends ServiceImpl<KeepAccessTokenMapper, KeepAccessToken> implements KeepAccessTokenService {

    private static String AT_PREF = "AT-";

    @Autowired
    private TicketRegistryServiceManager ticketRegistryServiceManager;

    @Override
    public void afterPropertiesSet() {
        ticketRegistryServiceManager.setService(KeepAccessToken.class,this);
    }

    @Override
    public boolean saveTicket(Ticket ticket) {
        return super.save((KeepAccessToken) ticket);
    }

    @Override
    public boolean updateTicket(Ticket ticket) {

        return super.updateById((KeepAccessToken) ticket);
    }

    @Override
    public boolean deleteTicket(String ticketId) {

        return super.removeById(ticketId);
    }

    @Override
    public Ticket selectByTicketId(String ticketId) {
        return super.getById(ticketId);
    }
}
