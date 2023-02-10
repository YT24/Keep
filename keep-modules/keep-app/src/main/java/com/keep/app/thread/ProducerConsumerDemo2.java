package com.keep.app.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo2 {

    public static void main(String[] args) {
        AirconditionNew aircondition = new AirconditionNew();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }





}

class AirconditionNew{

    private int number = 0;

    private Lock lock = new ReentrantLock();

    private Condition conditionA = lock.newCondition();
//    private Condition conditionB = lock.newCondition();
//    private Condition conditionC = lock.newCondition();
//    private Condition conditionD = lock.newCondition();

    public void increment()  {

        lock.lock();
        try {
            // 1.判断  while 防止虚假唤醒
            while(number != 0){
                conditionA.await();
            }
            // 2.执行
            number++;

            System.out.println(Thread.currentThread().getName() + "\t" + number);

            conditionA.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }

    public void decrement() {

        lock.lock();
        try {
            // 1.判断  while 防止虚假唤醒
            while(number == 0){
                conditionA.await();
            }
            // 2.执行
            number--;

            System.out.println(Thread.currentThread().getName() + "\t" + number);

            conditionA.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }

}