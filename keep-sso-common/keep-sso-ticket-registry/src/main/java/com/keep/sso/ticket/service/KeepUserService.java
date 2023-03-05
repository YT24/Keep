package com.keep.sso.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.ticket.entity.KeepUser;

import com.keep.sso.ticket.entity.param.LoginParam;
import com.keep.sso.ticket.entity.param.SysUserParam;
import com.keep.sso.ticket.entity.vo.OauthLoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
public interface KeepUserService extends IService<KeepUser> {

    OauthLoginVo login(LoginParam loginParam);

    UserInfoVo getUserInfo(String token);

    void create(SysUserParam param);
}
