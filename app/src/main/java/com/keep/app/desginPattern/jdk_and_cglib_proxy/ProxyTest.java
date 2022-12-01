package com.keep.app.desginPattern.jdk_and_cglib_proxy;

public class ProxyTest {

    public static void main(String[] args) {
		//JDK-proxy
		JDKProxy jdkProxy = new JDKProxy();
		UserManager userManagerJDK = (UserManager) jdkProxy.newProxy(new UserManagerImpl());
		userManagerJDK.addUser("KOBE", "24");


//		// 让代理对象的class文件写入到磁盘
//		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
//		// 指明一个类加载器，要操作class文件，怎么少得了类加载器呢
//		ClassLoader classLoader = JDKProxy.class.getClassLoader();
//		// 为代理对象指定要是实现哪些接口，这里我们要为UserServiceImpl这个目标对象创建动态代理，所以需要为代理对象指定实现UserService接口
//		Class[] classes = new Class[]{UserManager.class};
//		// 初始化一个InvocationHandler，并初始化InvocationHandler中的目标对象
//		InvocationHandler invocationHandler = (InvocationHandler) new JDKProxy(new UserManagerImpl());
//		// 创建动态代理
//		UserManager userManager = (UserManager) Proxy.newProxyInstance(classLoader, classes, invocationHandler);
//		// 执行代理对象的方法，通过观察控制台的结果，判断我们是否对目标对象(UserServiceImpl)的方法进行了增强
//		userManager.addUser("KOBE", "24");




		//CGLIB-proxy
		CglibProxy cglibProxy = new CglibProxy();
		UserManager userManagerCglib = (UserManager) cglibProxy.newProxy(new UserManagerImpl());
		userManagerCglib.addUser("jardon", "23");
    }
}
