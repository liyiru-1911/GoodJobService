package com.goodjob.distribution_center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 调度中心任务调度线程池配置
 */
@Configuration
public class ScheduleThreadPoolConfig {

    private static ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public TaskScheduler taskScheduler() {
        return getThreadPoolTaskScheduler();
    }

    private synchronized ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        if (threadPoolTaskScheduler == null) {
            threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
            threadPoolTaskScheduler.initialize();
            threadPoolTaskScheduler.setPoolSize(50);
            threadPoolTaskScheduler.setThreadNamePrefix("ScheduleTaskThreadPool");
            threadPoolTaskScheduler.setAwaitTerminationSeconds(120);
            threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        }
        return threadPoolTaskScheduler;
    }
}
