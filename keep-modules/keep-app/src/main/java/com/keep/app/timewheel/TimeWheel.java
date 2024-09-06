package com.keep.app.timewheel;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

@Slf4j
public class TimeWheel {

    //槽的数量
    private Integer slotCount;

    //基本时间跨度间隔
    private Long tickDuration;

    //时间轮的槽
    private List<Slot> slots;

    //当前指针位置
    private AtomicInteger currentIndex;


    public TimeWheel(int slotCount, long tickDuration) {
        this.slotCount = slotCount;
        this.tickDuration = tickDuration;
        this.slots = new LinkedList<>();
        LongStream.range(0, slotCount).forEach(i -> slots.add(new Slot()));
        this.currentIndex = new AtomicInteger(0);
    }

    public void addTask(Task task) {
        int delay = (int) (task.getDelay() / tickDuration);
        int index = (currentIndex.get() + delay) % slotCount;
        slots.get(index).addTask(task);
    }

    public void tick() {
        int index = currentIndex.getAndIncrement() % slotCount;
        Slot currentSlot = slots.get(index);
        currentSlot.executeTasks();
        currentSlot.clearTasks();
    }


    private static class Slot {

        private final List<Task> tasks = new LinkedList<>();

        public void addTask(Task task) {
            tasks.add(task);
        }


        public void executeTasks() {
            tasks.forEach(Task::run);
        }

        public void clearTasks() {
            tasks.clear();
        }

    }

    public static abstract class Task {

        private final long delay;

        public Task(long delay) {
            this.delay = delay;
        }

        public long getDelay() {
            return delay;
        }

        public abstract void run();
    }


    public static void main(String[] args) {
        //创建一个10槽的时间轮，每次tick间隔为1秒
        TimeWheel timeWheel = new TimeWheel(10, 1000);

        //添加2个任务
        timeWheel.addTask(new TimeWheel.Task(3000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 1 执行");
            }
        });

        timeWheel.addTask(new TimeWheel.Task(7000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 2 执行");
            }
        });

        timeWheel.addTask(new TimeWheel.Task(9000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 3 执行");
            }
        });

        //模拟时间轮的tick  每秒tick一次
        for (int i = 0; i < 10; i++) {
            timeWheel.tick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}