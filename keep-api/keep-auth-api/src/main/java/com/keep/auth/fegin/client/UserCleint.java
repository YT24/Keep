package com.keep.auth.fegin.client;

import com.keep.common.core.config.fegin.CustomizedConfiguration;
import com.keep.common.core.domain.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "keep-sso", path = "/api/v1/sysUser",contextId = "keep-app",configuration = CustomizedConfiguration.class)
public interface UserCleint {

    @GetMapping("info")
    UserInfoVo userInfo(@RequestHeader(value = "Authorization",required = true) String token);

    @PostMapping("create")
    void create();
}
