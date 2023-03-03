package com.keep.sso.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.entity.User;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.UserMapper;
import com.keep.sso.service.KeepUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import keep.sso.ticket.service.TokenRegistryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Api(tags = "用户认证中心")
@RestController
@RequestMapping("/api/v1/sysUser")
public class LoginController implements BaseController{

    @Autowired
    private KeepUserService sysUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Qualifier("tokenMultRegistryService")
    private TokenRegistryService tokenRegistryService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@RequestBody @Validated LoginParam loginParam){
        return ResponseResult.success(sysUserService.login(loginParam));
    }

    @SneakyThrows
    @ApiOperation("用户信息获取")
    @GetMapping("/info")
    public ResponseResult<UserInfoVo> detail(@RequestHeader(value = "Authorization") String token){
        return ResponseResult.success(sysUserService.getUserInfo(token));
    }

    @PostMapping("/create")
    public ResponseResult create(){
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("24");
        userMapper.insert(user);
        return ResponseResult.success();
    }

}

