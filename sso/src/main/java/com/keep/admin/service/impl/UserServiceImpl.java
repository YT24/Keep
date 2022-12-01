package com.keep.admin.service.impl;

import com.keep.admin.entity.User;
import com.keep.admin.entity.param.LoginParam;
import com.keep.admin.entity.vo.LoginVo;
import com.keep.admin.mapper.UserMapper;
import com.keep.admin.service.UserService;
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
