package com.keep.app.desginPattern.strategy_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginHController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @Transactional
    @PostMapping("/login/third")
    public String login(@RequestParam LoginType loginType, @RequestParam Long userId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginType(loginType);
        loginRequest.setUserId(userId);
        return loginService.login(loginRequest);
    }
}