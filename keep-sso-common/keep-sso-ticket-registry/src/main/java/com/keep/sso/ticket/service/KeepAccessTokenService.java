package com.keep.sso.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.sso.ticket.entity.KeepAccessToken;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-03-03
 */
public interface KeepAccessTokenService extends IService<KeepAccessToken>,KeepTokenService, InitializingBean {

}
