package com.keep.sso.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.entity.param.LoginParam;
import com.keep.sso.entity.param.SysUserParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private SysUserService sysUserService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@RequestBody LoginParam loginParam){
        return ResponseResult.success(sysUserService.login(loginParam));
    }

    @SneakyThrows
    @ApiOperation("用户信息获取")
    @GetMapping("/info")
    public ResponseResult<UserInfoVo> detail(@RequestHeader(value = "Authorization") String token){
        return ResponseResult.success(sysUserService.getUserInfo(token));
    }

}

