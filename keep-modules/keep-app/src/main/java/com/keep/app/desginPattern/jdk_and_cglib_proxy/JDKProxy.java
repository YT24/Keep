package com.keep.app.desginPattern.jdk_and_cglib_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {

    /**
     * 需要代理的目标对象
     */
    private Object targetObject;

    public JDKProxy(UserManagerImpl userManager) {
        this.targetObject = userManager;
        System.out.println(this);//com.example.yangt.desginPattern.jdk_and_cglib_proxy.JDKProxy@3830f1c0
    }

    public JDKProxy() {
    }

    /**
     * 将目标对象传入进行代理
     */

    public Object newProxy(Object targetObject) {
        this.targetObject = targetObject;
        System.out.println(this);//com.example.yangt.desginPattern.jdk_and_cglib_proxy.JDKProxy@3830f1c0
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 一般我们进行逻辑处理的函数比如这个地方是模拟检查权限
//        System.out.println("*****jdk*****执行前********方法："+method.getName()+"****参数："+args[0].toString());
        Object ret = method.invoke(targetObject, args);
//        System.out.println("*****jdk*****执行后********");
        return ret;
    }
}
