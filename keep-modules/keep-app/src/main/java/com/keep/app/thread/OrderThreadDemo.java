package com.keep.app.thread;

import lombok.SneakyThrows;

public class OrderThreadDemo {

    public static void main(String[] args) {
        // 线程 1，3 ，2 顺序执行

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("打开冰箱");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                t1.join();
                System.out.println("拿一瓶可乐");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                t2.join();
                System.out.println("关上冰箱");
            }
        });
        t3.start();
        t2.start();
        t1.start();

    }
}
