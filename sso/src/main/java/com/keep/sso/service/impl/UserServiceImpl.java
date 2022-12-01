package com.keep.sso.service.impl;

import com.keep.sso.entity.User;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.UserMapper;
import com.keep.sso.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2022-12-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public LoginVo login(LoginParam param) {
        return null;
    }
}
