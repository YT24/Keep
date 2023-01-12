package com.keep.app.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现多线程顺序调用 A -> B -> C
 */
public class ConditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    shareData.printByNum(1);
                    //shareData.print1();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    shareData.printByNum(2);
                    //shareData.print2();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    shareData.printByNum(3);
                    //shareData.print3();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "C").start();
    }
}

class ShareData {
    private int number = 1; // A:1 B:2 C:2
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printByNum(int num) throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (number != num) {
                if (num == 1) {
                    condition1.await();
                } else if (num == 2) {
                    condition2.await();
                } else if (num == 3) {
                    condition3.await();
                }
            }

            // 打印
            for (int i = 0; i < getPrintCount(num); i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + number);
            }

            // 唤醒
            if (num == 1) {
                number = 2;
                condition2.signal();
            } else if (num == 2) {
                number = 3;
                condition3.signal();
            } else if (num == 3) {
                number = 1;
                condition1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private Integer getPrintCount(int num) {
        int flag = 0;
        if (num == 1) {
            flag = 1;
        } else if (num == 2) {
            flag = 2;
        } else if (num == 3) {
            flag = 3;
        }
        return flag;
    }

    public void print1() throws InterruptedException {
        lock.lock();
        try {
            while (number != 1) {
                condition1.await();
            }
            // 打印
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + number);
            }
            // 唤醒
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print2() throws InterruptedException {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }
            // 打印
            for (int i = 0; i < 2; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + number);
            }
            // 唤醒
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print3() throws InterruptedException {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            // 打印
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + number);
            }
            // 唤醒
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
