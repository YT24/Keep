package com.keep.common.entity;


public class UserToHolder {

    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static Object getUserTo(){

        return threadLocal.get();
    }

    public static void setUserTo(Object userTo){
        threadLocal.set(userTo);
    }

    public static void remove(){
        threadLocal.remove();
    }
}
