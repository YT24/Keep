package com.keep.app.service;

import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.domain.dto.UserUpdateDto;
import com.keep.app.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-02-12
 */
public interface SysUserService extends IService<SysUser> {

    void create(UserCreateDto userCreateDto);

    void updateById(UserUpdateDto userUpdateDto);
}
