package com.keep.sso.ticket.service;

import com.keep.sso.ticket.entity.KeepUserDeviceType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.sso.ticket.entity.Ticket;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-05
 */
public interface KeepUserDeviceTypeService extends IService<KeepUserDeviceType> {

    void saveByTicket(Ticket ticket);
}
