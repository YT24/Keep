package com.keep.app.desginPattern.strategy_pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WeboLoginHandler implements LoginHandler<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取登录类型
     *
     * @return
     */
    @Override
    public LoginType getLoginType() {
        return LoginType.WEI_BO;
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @Override
    public String handleLogin(LoginRequest request) {
        logger.info("微博登录：userId：{}", request.getUserId());
        return "微博登录成功";
    }
}