package com.keep.common.core.utils;


import com.keep.common.core.domain.constants.CommanConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class SecurityUtils {

    /**
     * 获取用户
     */
    public static String getUsername()
    {
        String username = ServletUtils.getRequest().getHeader(CommanConstants.USERNAME);
        return ServletUtils.urlDecode(username);
    }


    /**
     * 获取请求token
     */
    public static String getToken()
    {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request)
    {
        String token = ServletUtils.getRequest().getHeader(CommanConstants.HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(CommanConstants.TOKEN_PREFIX))
        {
            token = token.replace(CommanConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

}
