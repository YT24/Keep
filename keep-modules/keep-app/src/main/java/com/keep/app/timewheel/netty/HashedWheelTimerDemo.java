package com.keep.app.timewheel.netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HashedWheelTimerDemo {

    public static void main(String[] args) {
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
        hashedWheelTimer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                log.info(timeout.toString());
                log.info("定时任务执行");
            }
        }, 3, TimeUnit.SECONDS);
        hashedWheelTimer.start();
    }

}
