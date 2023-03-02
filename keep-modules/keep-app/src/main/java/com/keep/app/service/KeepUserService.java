package com.keep.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.domain.dto.UserUpdateDto;
import com.keep.app.domain.entity.KeepUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author system
 * @since 2023-02-12
 */
public interface KeepUserService extends IService<KeepUser> {

    void create(UserCreateDto userCreateDto);

    void updateById(UserUpdateDto userUpdateDto);
}
