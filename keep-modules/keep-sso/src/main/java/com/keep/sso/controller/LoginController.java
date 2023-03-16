package com.keep.sso.controller;


import com.keep.common.core.domain.entity.ResponseResult;
import com.keep.common.core.domain.vo.UserInfoVo;
import com.keep.sso.entity.User;
import com.keep.sso.login.filter.ILoginFilter;
import com.keep.sso.login.filter.LoginFilterEnum;
import com.keep.sso.login.filter.LoginFilterFactor;
import com.keep.sso.ticket.entity.param.LoginParam;
import com.keep.sso.entity.vo.LoginVo;
import com.keep.sso.mapper.UserMapper;
import com.keep.sso.ticket.service.KeepUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.keep.sso.ticket.service.TokenRegistryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author system
 * @since 2022-12-13
 */
@Api(tags = "用户认证中心")
@RestController
@RequestMapping("/api/v1/oauth")
public class LoginController implements BaseController {

    @Autowired
    private KeepUserService sysUserService;

    @Autowired
    private UserMapper userMapper;


    @ApiOperation("登录")
    @PostMapping("/accessToken")
    public ResponseResult<LoginVo> login(@RequestBody @Validated LoginParam loginParam) {
        List<ILoginFilter> loginFilters = LoginFilterFactor.createLoginFilters(Arrays.asList(LoginFilterEnum.values()));
        loginFilters.stream().forEach(iLoginFilter -> {
            iLoginFilter.doFilter(loginParam);
        });
        return ResponseResult.success(sysUserService.login(loginParam));
    }

    @SneakyThrows
    @ApiOperation("用户信息获取")
    @GetMapping("/profile")
    public ResponseResult<UserInfoVo> detail(@RequestHeader(value = "accessToken", required = true) String accessToken) {
        return ResponseResult.success(sysUserService.getUserInfo(accessToken));
    }

    @PostMapping("/create")
    public ResponseResult create() {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("24");
        userMapper.insert(user);
        return ResponseResult.success();
    }

}

