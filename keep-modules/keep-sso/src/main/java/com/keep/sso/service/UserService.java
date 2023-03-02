package com.keep.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.sso.entity.User;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2022-12-01
 */
public interface UserService extends IService<User> {


    LoginVo login(LoginParam param);

    void create(SysUserParam param);
}
