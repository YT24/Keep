package com.keep.common.fegin;

import com.keep.common.fegin.config.CustomizedConfiguration;
import com.keep.common.fegin.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "keep-sso", path = "/api/v1/sysUser",contextId = "keep-app",configuration = CustomizedConfiguration.class)
public interface UserInfoClient {

    @GetMapping("/info")
    UserInfoVo getUserInfo(@RequestHeader("Authorization") String token);

}
