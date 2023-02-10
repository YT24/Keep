package com.keep.common.domain.constants;

public class CommanConstants {

    /**
     * 令牌自定义标识
     */
    public static final String HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 用户ID字段
     */
    public static final String USER_ID = "userId";

    /**
     * 用户名字段
     */
    public static final String USERNAME = "username";

    public static final String TOKEN_HEADER = "Authorization";

    public static final String APP_SECRET_KEY = "keep_app_secret";

    public static final Long AT_EXPIRED_TIME = 3600 * 1000L;

    public static final Long RT_EXPIRED_TIME = 3600 * 1000 * 24 * 7L;
}
