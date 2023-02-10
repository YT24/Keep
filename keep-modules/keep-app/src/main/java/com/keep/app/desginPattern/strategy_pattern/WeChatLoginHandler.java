package com.keep.app.desginPattern.strategy_pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WeChatLoginHandler implements LoginHandler<String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取登录类型
     *
     * @return
     */
    @Override
    public LoginType getLoginType() {
        return LoginType.WE_CHAT;
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @Override
    public String handleLogin(LoginRequest request) {
        logger.info("微信登录：userId：{}", request.getUserId());
        String weChatName = getWeChatName(request);
        return "微信登录成功";
    }

    private String getWeChatName(LoginRequest request) {
        return "wupx";
    }
}