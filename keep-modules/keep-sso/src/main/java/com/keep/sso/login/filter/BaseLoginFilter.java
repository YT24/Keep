package com.keep.sso.login.filter;

public abstract class BaseLoginFilter implements ILoginFilter{

    void sendMsg(){
        System.out.println("过滤了");
    }
}
