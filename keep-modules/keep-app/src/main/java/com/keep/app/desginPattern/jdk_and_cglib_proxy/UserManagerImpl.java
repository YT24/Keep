package com.keep.app.desginPattern.jdk_and_cglib_proxy;

public class UserManagerImpl implements UserManager {

    @Override
    public void addUser(String id, String password) {
        System.out.println("======调用了UserManagerImpl.addUser()方法======" + "id:" + id + "---password:" + password);
    }

}
