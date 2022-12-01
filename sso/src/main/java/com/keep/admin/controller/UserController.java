package com.keep.admin.controller;


import com.keep.admin.entity.param.LoginParam;
import com.keep.admin.entity.vo.LoginVo;
import com.keep.admin.service.UserService;
import com.keep.common.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2022-12-01
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseResult<LoginVo> login(LoginParam param){
        LoginVo vo = userService.login(param);
        return ResponseResult.success();
    }

}

