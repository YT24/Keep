package com.keep.common.domain.entity;


import com.keep.common.fegin.vo.UserInfoVo;

public class UserVoHolder {

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
