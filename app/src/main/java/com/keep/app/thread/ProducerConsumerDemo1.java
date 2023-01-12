package com.keep.app.thread;

public class ProducerConsumerDemo1 {

    public static void main(String[] args) {
        Aircondition aircondition = new Aircondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }





}

class Aircondition{

    private volatile int number = 0;

    public synchronized void increment() throws InterruptedException {
        // 1.判断  while 防止虚假唤醒
        while(number != 0){
            this.wait();
        }
        // 2.执行
        number++;

        System.out.println(Thread.currentThread().getName() + "\t" + number);
        // 3.通知
        this.notifyAll();


    }

    public  synchronized  void  decrement() throws InterruptedException {
        // 1.判断 while 防止虚假唤醒
        while(number == 0){
            this.wait();
        }
        // 执行
        number--;

        System.out.println(Thread.currentThread().getName() + "\t" + number);
        // 通知
        this.notifyAll();

    }
}