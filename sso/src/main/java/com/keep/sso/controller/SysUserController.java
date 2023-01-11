package com.keep.sso.controller;


import com.keep.common.entity.ResponseResult;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.entity.vo.UserInfoVo;
import com.keep.sso.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@RestController
@RequestMapping("api/v1/sysUser")
public class SysUserController implements BaseController{

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@RequestBody LoginParam loginParam){
        return ResponseResult.success(sysUserService.login(loginParam));
    }

    @GetMapping("/info")
    public ResponseResult<UserInfoVo> detail(@RequestHeader("Authorization") String token){
        return ResponseResult.success(sysUserService.getUserInfo(token));
    }

    @PostMapping("/create")
    public ResponseResult<LoginVo> login(@RequestBody SysUserParam param){
        sysUserService.create(param);
        return ResponseResult.success();
    }

}

