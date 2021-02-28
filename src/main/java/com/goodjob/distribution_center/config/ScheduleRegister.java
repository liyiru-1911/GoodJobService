package com.goodjob.distribution_center.config;

import com.goodjob.distribution_center.domain.ScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册中心
 */
public class ScheduleRegister {

    @Autowired
    private TaskScheduler taskScheduler;

    private Map<String, ScheduleTask> scheduleTaskMap = new ConcurrentHashMap<>();

    // 新增定时任务
    public void add(Runnable task, String cron, String id) {
        remove(id);
        CronTask cronTask = new CronTask(task, cron);
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        this.scheduleTaskMap.put(id, scheduleTask);
    }

    // 移除定时任务
    public void remove(String id) {
        ScheduleTask scheduleTask = this.scheduleTaskMap.remove(id);
        if (scheduleTask != null) scheduleTask.stop();
    }
}
