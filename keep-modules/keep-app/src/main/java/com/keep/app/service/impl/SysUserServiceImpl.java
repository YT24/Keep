package com.keep.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.domain.dto.UserUpdateDto;
import com.keep.app.domain.entity.SysUser;
import com.keep.app.mapper.SysUserMapper;
import com.keep.app.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author system
 * @since 2023-02-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public void create(UserCreateDto userCreateDto) {
        this.save(JSONObject.parseObject(JSONObject.toJSONString(userCreateDto),SysUser.class));
    }

    @Override
    public void updateById(UserUpdateDto userUpdateDto) {
        this.updateById(JSONObject.parseObject(JSONObject.toJSONString(userUpdateDto),SysUser.class));
    }
}
