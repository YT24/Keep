package com.keep.app.timewheel;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

@Slf4j
public class MultiLevelTimeWheel {

    //时间轮列表，用于存储各层时间轮
    private final List<TimeWheel> timeWheels;

    //时间轮的层数
    private final int levels;

    public MultiLevelTimeWheel(int slotCount, long baseTickDuration, int levels) {
        this.levels = levels;
        this.timeWheels = new LinkedList<>();
        //初始化每一层时间轮，随着层级增加，tickDuration也要扩大
        for (int i = 0; i < levels; i++) {
            long tickDuration = (long) Math.pow(slotCount, i) * baseTickDuration;
            timeWheels.add(new TimeWheel(slotCount, tickDuration, i));
        }
    }

    public void addTask(Task task) {
        //从第一层时间轮开始尝试添加任务
        addTaskToWheel(task, 0);
    }

    private void addTaskToWheel(Task task, int level) {
        if (level >= levels) {
            log.error("任务延迟过长，无法添加到时间轮中: {}", task.getDelay());
            throw new RuntimeException("任务延迟过长，无法添加到时间轮中");
        }
        //如果延迟在当前层级的槽范围内，直接添加任务
        TimeWheel currentWheel = timeWheels.get(level);
        int delay = (int) (task.getDelay() / currentWheel.tickDuration);
        if (delay < currentWheel.slotCount) {
            currentWheel.addTask(task);
            log.info("任务添加到第{}层时间轮的第{}个槽中", level, (currentWheel.currentIndex.get() + delay) % currentWheel.slotCount);
        } else {
            //如果延迟超出当前层级的槽范围，将任务移动到下一层时间轮
            task.reduceDelay(currentWheel.slotCount * currentWheel.tickDuration);
            log.info("任务溢出到第{}层时间轮，剩余延迟时间: {}", level + 1, task.getDelay());
            addTaskToWheel(task, level + 1);
        }
    }

    /**
     * 执行时间轮的 tick 操作
     * 每次 tick 都会推进第一个时间轮并处理任务溢出的情况
     */
    public void tick() {
        TimeWheel firstWheel = timeWheels.get(0);
        firstWheel.tick();

        //当第一层时间轮完成一个完整的循环时，处理更高层级的任务
        if (firstWheel.currentIndex.get() % firstWheel.slotCount == 0) {
            log.info("第一层时间轮完成一个轮回，开始处理更高层任务");
            for (int i = 1; i < levels; i++) {
                TimeWheel higherLevelWheel = timeWheels.get(i);
                if (higherLevelWheel.hasPendingTasks()) {
                    List<Task> overflowTasks = higherLevelWheel.getCurrentSlotTasks();
                    log.info("从第{}层时间轮中取出{}个任务放入第一层时间轮", i, overflowTasks.size());
                    for (Task task : overflowTasks) {
                        task.reduceDelay(higherLevelWheel.tickDuration);
                        addTaskToWheel(task, 0);
                    }
                    higherLevelWheel.clearCurrentSlotTasks();
                    //上层指针移动1
                    higherLevelWheel.incrementIndex();
                }
            }
        }
    }

    private static class TimeWheel {
        //槽的数量
        private Integer slotCount;

        //基本时间跨度间隔
        private Long tickDuration;

        //时间轮的槽
        private List<Slot> slots;

        //当前指针位置
        private AtomicInteger currentIndex;

        //当前时间轮的层级
        private final int level;

        public TimeWheel(int slotCount, long tickDuration, int level) {
            this.slotCount = slotCount;
            this.tickDuration = tickDuration;
            this.slots = new LinkedList<>();
            LongStream.range(0, slotCount).forEach(i -> slots.add(new Slot()));
            this.currentIndex = new AtomicInteger(0);
            this.level = level;
        }

        public void addTask(Task task) {
            int delay = (int) (task.getDelay() / tickDuration);
            int index = (currentIndex.get() + delay) % slotCount;
            slots.get(index).addTask(task);
        }

        public void tick() {
            int index = currentIndex.getAndIncrement() % slotCount;
            Slot currentSlot = slots.get(index);
            log.info("时间{} 第{}层时间轮的第{}个槽执行了{}个任务", DateUtil.now(), level, index, currentSlot.tasks.size());
            currentSlot.executeTasks();
            currentSlot.clearTasks();
        }

        public void incrementIndex() {
            currentIndex.getAndIncrement();
        }

        public boolean hasPendingTasks() {
            return slots.stream().anyMatch(slot -> !slot.tasks.isEmpty());
        }

        public List<Task> getCurrentSlotTasks() {
            int index = currentIndex.get() % slotCount;
            return new LinkedList<>(slots.get(index).tasks);
        }

        public void clearCurrentSlotTasks() {
            int index = currentIndex.get() % slotCount;
            slots.get(index).clearTasks();
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
    }

    public static abstract class Task {

        private long delay;

        public Task(long delay) {
            this.delay = delay;
        }

        public long getDelay() {
            return delay;
        }

        public void reduceDelay(long time) {
            this.delay -= time;
        }

        public abstract void run();
    }

    public static void main(String[] args) {
        // 创建一个多层时间轮，每层10个槽，基本tick间隔为1秒，共2层
        MultiLevelTimeWheel multiLevelTimeWheel = new MultiLevelTimeWheel(10, 1000, 3);

        // 添加3个任务
        multiLevelTimeWheel.addTask(new MultiLevelTimeWheel.Task(3000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 1 执行");
            }
        });

        multiLevelTimeWheel.addTask(new MultiLevelTimeWheel.Task(18000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 2 执行");
            }
        });

        multiLevelTimeWheel.addTask(new MultiLevelTimeWheel.Task(22000) {
            @Override
            public void run() {
                log.info(DateUtil.now() + "    任务 3 执行");
            }
        });

        log.info("\n");
        log.info("-----------------------------");
        log.info("\n");

        //模拟时间轮的tick，每秒钟tick一次
        for (int i = 0; i < 20; i++) {
            multiLevelTimeWheel.tick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}