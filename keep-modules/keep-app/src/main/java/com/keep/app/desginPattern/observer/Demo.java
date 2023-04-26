package com.keep.app.desginPattern.observer;

public class Demo {



    public static void main(String[] args) {
        Uploader uploader = new Uploader();

        Observer o1 = new FansObserver("粉丝A");
        Observer o2 = new FansObserver("粉丝B");
        Observer o3 = new FansObserver("粉丝C");

        uploader.addObserver(o1);
        uploader.addObserver(o2);
        uploader.addObserver(o3);

        uploader.sendMsg("发布了一条篮球视频");
        uploader.removeObserver(o3);
        uploader.sendMsg("发布了一条唱歌视频");

    }



}

class A extends Thread{

    @Override
    public void run() {
        System.out.println("12344");
    }
}
