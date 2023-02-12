package com.keep.sso.service;

import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface SysUserService extends IService<SysUser> {

    LoginVo login(LoginParam loginParam);

    UserInfoVo getUserInfo(String token);

    void create(SysUserParam param);
}
