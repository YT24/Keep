package com.keep.sso.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.enums.TicketTypeEnum;
import com.keep.sso.ticket.manager.TicketRegistryServiceManager;
import com.keep.sso.ticket.service.KeepTgtTokenService;
import com.keep.sso.ticket.entity.KeepTgtToken;
import com.keep.sso.ticket.mapper.KeepTgtTokenMapper;
import com.keep.sso.ticket.service.KeepTokenService;
import com.keep.sso.ticket.service.KeepUserDeviceTypeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
@Service
public class KeepTgtTokenServiceImpl extends ServiceImpl<KeepTgtTokenMapper, KeepTgtToken> implements KeepTgtTokenService {


    private static String AT_PREF = "TGT-";

    @Autowired
    private TicketRegistryServiceManager ticketRegistryServiceManager;

    @Autowired
    private KeepUserDeviceTypeService userDeviceTypeService;

    @Override
    public void afterPropertiesSet() {
        ticketRegistryServiceManager.setService(KeepTgtToken.class,this);
    }


    @Override
    public boolean saveTicket(Ticket ticket) {
        userDeviceTypeService.saveByTicket(ticket);
        return this.save((KeepTgtToken) ticket);
    }
}
