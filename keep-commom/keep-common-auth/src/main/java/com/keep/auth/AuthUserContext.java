package com.keep.auth;


import com.keep.common.core.domain.vo.UserInfoVo;

public class AuthUserContext {

    private static final ThreadLocal<UserInfoVo> threadLocal = new ThreadLocal<>();

    public static Object getUserTo(){

        return threadLocal.get();
    }
    public static void setUserInfoVo(UserInfoVo userTo){
        threadLocal.set(userTo);
    }

    public static void remove(){
        threadLocal.remove();
    }
}
