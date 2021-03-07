package com.goodjob.distribution_center.service;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Job;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务执行情况数据同步服务
 * 包括生成task、保存task、接收task反馈信息、同步已执行task数据到JobCenter
 */
public interface SynchronizeTaskService {

    /**
     * 生成任务执行情况
     *
     * @param job
     * @return
     */
    Task generateTask(Job job, String workerUrl);

    /**
     * 再次执行时改变执行结果信息
     */
    void changeTaskWhenTryAgain(Task task, String workerUrl);

    /**
     * 保存找不到平台信息异常 - 首次创建
     */
    void saveNoPlatformResult(Job job);

    /**
     * 保存找不到平台信息异常
     */
    void saveNoPlatformResult(Task task);

    /**
     * 处理路由策略异常 - 首次创建
     */
    void saveRoutingStrategyExceptionResult(Job job);

    /**
     * 处理路由策略异常
     */
    void saveRoutingStrategyExceptionResult(Task task);

    /**
     * 保存worker确认接受任务信息
     */
    void saveConfirmationResult(int taskId, HashMap distributeResult);

    /**
     * 保存任务执行结果
     */
    void saveTaskResult(Map<String, Object> taskResult);

}
