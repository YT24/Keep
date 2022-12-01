package com.keep.admin.service;

import com.keep.admin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.admin.entity.param.LoginParam;
import com.keep.admin.entity.vo.LoginVo;

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
}
