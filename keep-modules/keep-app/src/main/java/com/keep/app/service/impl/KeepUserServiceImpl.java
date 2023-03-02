package com.keep.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.domain.dto.UserUpdateDto;
import com.keep.app.domain.entity.KeepUser;
import com.keep.app.mapper.KeepUserMapper;
import com.keep.app.service.KeepUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-02-12
 */
@Service
public class KeepUserServiceImpl extends ServiceImpl<KeepUserMapper, KeepUser> implements KeepUserService {


    @GlobalTransactional
    @Transactional
    @Override
    public void create(UserCreateDto userCreateDto) {
        this.save(JSONObject.parseObject(JSONObject.toJSONString(userCreateDto), KeepUser.class));
    }

    @Override
    public void updateById(UserUpdateDto userUpdateDto) {
        this.updateById(JSONObject.parseObject(JSONObject.toJSONString(userUpdateDto), KeepUser.class));
    }
}
