package com.keep.app.desginPattern.strategy_pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginHandlerFactory loginHandlerFactory;

    @Override
    public String login(LoginRequest request) {
        LoginType loginType = request.getLoginType();
        // 根据 loginType 找到对应的登录处理器
        LoginHandler<Serializable> loginHandler =
                loginHandlerFactory.getHandler(loginType);
        // 处理登录
        return loginHandler.handleLogin(request);
    }
}