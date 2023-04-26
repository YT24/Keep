package com.keep.app.desginPattern.observer;

public class FansObserver implements Observer{

    String name;

    public FansObserver(String name) {
        this.name = name;
    }

    @Override
    public void receive(String msg) {
        System.out.println(name + " 收到消息：" + msg);
    }
}
