package com.goodjob.distribution_center.service;

import com.goodjob.distribution_center.domain.Entity.Task;

/**
 * 任务分发服务
 */
public interface DistributeService {

    /**
     * 调度中心核心服务 - 分发任务
     * 查询job详情
     * 创建task
     * 查询所使用的的平台信息
     * 查询路由策略
     * 更新task
     * 合成Handler
     * 分发任务
     * 接受worker的确认信息
     *
     * @param jobUuid
     * @param task 首次创建则为null
     */
    void distributeJob(String jobUuid, Task task);
}
