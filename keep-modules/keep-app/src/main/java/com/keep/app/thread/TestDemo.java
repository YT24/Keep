package com.keep.app.thread;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TestDemo {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.lock();

        HashMap hashMap = new HashMap();
        hashMap.put(1,1);


        System.out.println( 20 >>> 2);
        System.out.println(8 ^ 2);
    }
}
