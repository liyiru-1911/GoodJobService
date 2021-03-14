package com.goodjob.distribution_center.config;

import com.goodjob.distribution_center.domain.JobCache;
import com.goodjob.distribution_center.domain.ScheduleTask;
import com.goodjob.distribution_center.service.DistributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册中心
 */
@Component
public class ScheduleRegister {

    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    DistributeService distributeService;

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

    // 装载调度定时任务
    public void loadJob(String jobUuid) {
        String cron = JobCache.getInstance().get(jobUuid).getCron();
        Runnable task = ()-> {
            try {
                if (cron != null) {
                    distributeService.distributeJob(jobUuid, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        add(task, cron, jobUuid);
    }
}
