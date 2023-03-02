package com.keep.app.controller;

import com.keep.app.domain.dto.UserCreateDto;
import com.keep.app.mapper.UserMapper;
import com.keep.app.service.KeepUserService;
import com.keep.auth.fegin.client.UserCleint;
import com.keep.common.core.domain.entity.ResponseResult;
import io.seata.spring.annotation.GlobalTransactional;
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
    private KeepUserService sysUserService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCleint userCleint;



    @GlobalTransactional
    @ApiOperation("修改用户信息")
    @PostMapping("test")
    public ResponseResult seataTest(@RequestBody @Validated UserCreateDto createDto){
//        userMapper.insert(JSONObject.parseObject(JSONObject.toJSONString(createDto), User.class));
//        userCleint.create();
//        System.out.println(1/0);


        return ResponseResult.success();
    }
}
