package com.keep.app.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskManager {

    // 定时任务列表
    private final List<ScheduledTask> tasks = new ArrayList<>();

    // 初始化任务
    public TaskManager() {

        // 示例任务1
        tasks.add(new ScheduledTask("Task 1", "*/5 * * * * ?", () -> log.info("执行任务 1")));

        // 示例任务2
        tasks.add(new ScheduledTask("Task 2", "*/5 * * * * ?", () -> log.info("执行任务 2")));

        // 你可以继续添加更多的任务...
    }

    public List<ScheduledTask> getTasks() {
        return tasks;
    }
}
