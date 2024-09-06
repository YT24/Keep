package com.keep.app.schedule;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CronScheduler {

    private static HashedWheelTimer timer = new HashedWheelTimer(60, TimeUnit.SECONDS);

    private static Map<Long, Timeout> timeoutMap = new ConcurrentHashMap<Long, Timeout>();

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        startScheduling(taskManager.getTasks());
    }

    public static void startScheduling(List<ScheduledTask> tasks) {
        // 对每个任务进行调度
        for (ScheduledTask task : tasks) {
            scheduleTask(task);
        }
    }

    private static void scheduleTask(ScheduledTask task) {
        // 获取距离下一次执行的延迟时间
        long delayMillis = task.getDelayMillis();

        // 使用 HashedWheelTimer 调度任务
        Timeout newTimeout = timer.newTimeout(timeout -> {
            // 执行任务
            task.execute();

            // 重新设置任务的下一个执行时间
            task.setNextExecutionDelayMillis(task.getCron());

            // 调度下一个执行
            scheduleTask(task);

        }, delayMillis, TimeUnit.MILLISECONDS);

        //timeoutMap.put(task.getTblId(), newTimeout);
    }
}
