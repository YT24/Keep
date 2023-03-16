package com.keep.app.desginPattern.factory_filter;

public abstract class BaseLoginFilter implements ILoginFilter{

    void sendMsg(){
        System.out.println("过滤了");
    }
}
