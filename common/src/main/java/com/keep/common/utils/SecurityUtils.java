package com.keep.common.utils;


import com.keep.common.constants.CommanConstants;
import jodd.typeconverter.Convert;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class SecurityUtils {

    /**
     * 获取用户
     */
    public static String getUsername()
    {
        String username = ServletUtils.getRequest().getHeader(CommanConstants.DETAILS_USERNAME);
        return ServletUtils.urlDecode(username);
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId()
    {
        return Convert.toLong(ServletUtils.getRequest().getHeader(CommanConstants.DETAILS_USER_ID));
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
