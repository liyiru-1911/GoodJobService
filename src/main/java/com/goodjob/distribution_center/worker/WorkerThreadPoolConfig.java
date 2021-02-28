package com.goodjob.distribution_center.worker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class WorkerThreadPoolConfig {
    /**
     * 执行器线程池说明：
     * 1.定时任务运行不均，不维护过度核心线程
     * 2.由于异步执行，重点在于扩大吞吐量，有界队列较大
     * 3.拒绝策略：为了减轻调度中心等待压力，且提供了重试机制，对单次执行要求不高
     *      故抛出拒绝执行异常，返回系统繁忙
     */
    @Bean("workerTaskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}
