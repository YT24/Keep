package com.keep.app.desginPattern.jdk_and_cglib_proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    /**
     * 需要被代理的对象
     */
    private Object targetObject;

    /**
     * 将目标对象传入进行代理
     */
    public Object newProxy(Object targetObject) {
        this.targetObject = targetObject;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.targetObject.getClass());
        enhancer.setCallback(this);
        //返回代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        System.out.println("******cglib****执行前********方法："+method.getName()+"****参数："+args[0].toString());
        Object obj =  method.invoke(targetObject, args);
        System.out.println("******cglib****执行后********");
        return obj;

    }



}
