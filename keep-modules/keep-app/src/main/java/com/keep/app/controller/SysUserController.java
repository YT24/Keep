package com.keep.app.controller;

import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.domain.dto.UserUpdateDto;
import com.keep.app.service.SysUserService;
import com.keep.common.core.domain.entity.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户中心")
@RequestMapping("/api/v1/user")
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("用户创建")
    @PostMapping("create")
    public ResponseResult create(@RequestBody @Validated UserCreateDto userCreateDto){
        sysUserService.create(userCreateDto);
        return ResponseResult.success();
    }

    @ApiOperation("修改用户信息")
    @PostMapping("update")
    public ResponseResult updateUserInfo(@RequestBody @Validated UserUpdateDto userUpdateDto){
        sysUserService.updateById(userUpdateDto);
        return ResponseResult.success();
    }
}
