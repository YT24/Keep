package com.keep.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.entity.KeepUser;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
public interface KeepUserService extends IService<KeepUser> {

    LoginVo login(LoginParam loginParam);

    UserInfoVo getUserInfo(String token);

    void create(SysUserParam param);
}
