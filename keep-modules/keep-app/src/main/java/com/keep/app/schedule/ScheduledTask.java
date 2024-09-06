package com.keep.app.schedule;

import lombok.Data;
import org.redisson.executor.CronExpression;

import java.util.Date;

@Data
public class ScheduledTask {
    private Long tblId;
    private final String name;    // 任务名称
    private final String cron;      // cron表达式
    private Long delayMillis;  // 下一次执行时间间隔
    private final Runnable task;  // 要执行的任务逻辑

    public ScheduledTask(String name, String cron, Runnable task) {
        this.name = name;
        this.cron = cron;
        this.delayMillis = getNextExecutionDelayMillis(cron);
        this.task = task;
    }

    /**
     * 根据corn表达式获取下次执行时间间隔
     *
     * @param cron corn
     * @return Long
     */
    public Long getNextExecutionDelayMillis(String cron) {
        CronExpression cronExpression = new CronExpression(cron);
        Date date = cronExpression.getTimeAfter(new Date());
        return date.getTime() - System.currentTimeMillis();
    }

    /**
     * 设置定时任务下次执行间隔时间
     *
     * @param cron corn
     */
    public void setNextExecutionDelayMillis(String cron) {
        this.delayMillis = getNextExecutionDelayMillis(cron);
    }

    public void execute() {
        task.run();
    }
}
