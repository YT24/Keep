package com.keep.sso.ticket.service.impl;

import com.keep.sso.ticket.entity.KeepUserDeviceType;
import com.keep.sso.ticket.entity.Ticket;
import com.keep.sso.ticket.mapper.KeepUserDeviceTypeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.sso.ticket.service.KeepUserDeviceTypeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-03-05
 */
@Service
public class KeepUserDeviceTypeServiceImpl extends ServiceImpl<KeepUserDeviceTypeMapper, KeepUserDeviceType> implements KeepUserDeviceTypeService {

    @Override
    public void saveByTicket(Ticket ticket) {
        KeepUserDeviceType userDeviceType = new KeepUserDeviceType();
        userDeviceType.setUsername(ticket.getUsername());
        userDeviceType.setDeviceType(ticket.getDeviceType());
        userDeviceType.setTgtId(ticket.getId());
        userDeviceType.setCreateTime(LocalDateTime.now());
        this.save(userDeviceType);
    }
}
