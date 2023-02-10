package com.keep.app.desginPattern.xiangyuan;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Slf4j
public class ConnectionPool {

    private int poolSize = 10;

    // 连接
    private Connection[] connections;

    // 链接状态数组，0：表示空闲 1：表示占用
    private AtomicIntegerArray states;


    public ConnectionPool(int poolSize){
        this.poolSize = poolSize;
        this.connections = new Connection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);
        for (int i = 0; i < poolSize; i++){
            connections[i] = new MockConnection("连接" + i);
        }
    }

    public Connection acquire() {
        while (true){
            for (int i = 0; i < poolSize; i++) {
                if(states.get(i) == 0){
                    states.compareAndSet(i,0,1);
                    log.info("acquire {}",connections[i]);
                    return connections[i];
                }
            }
            synchronized (this){
                try {
                    this.wait();
                    log.info("wait.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void release(Connection conn){
        for (int i = 0; i < poolSize; i++) {
            if(connections[i] == conn){
                states.set(i,0);
                synchronized (this){
                    this.notifyAll();
                }
                log.info("release {}",conn);
            }
        }
    }

    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() ->{
                Connection connection = pool.acquire();
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.release(connection);
            }).start();
        }
    }
}
