package com.keep.sso.ticket.service.impl;

import com.keep.sso.ticket.entity.KeepRefreshToken;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.manager.TicketRegistryServiceManager;
import com.keep.sso.ticket.service.KeepRefreshTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.sso.ticket.mapper.KeepRefreshTokenMapper;
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
public class KeepRefreshTokenServiceImpl extends ServiceImpl<KeepRefreshTokenMapper, KeepRefreshToken> implements KeepRefreshTokenService {

    private static String RT_PREF = "RT-";

    @Autowired
    private TicketRegistryServiceManager ticketRegistryServiceManager;


    @Override
    public void afterPropertiesSet() throws Exception {
        ticketRegistryServiceManager.setService(KeepRefreshToken.class,this);
    }


    @Override
    public boolean saveTicket(Ticket ticket) {
        return this.save((KeepRefreshToken) ticket);
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        return super.updateById((KeepRefreshToken) ticket);
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
