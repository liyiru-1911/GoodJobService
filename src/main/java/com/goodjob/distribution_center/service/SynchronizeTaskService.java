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
     * 生成任务执行情况 （区分首次执行和重试两种情况）
     *
     * @param job
     * @param task 如果为空，则是首次创建；否则更新状态即可
     * @return
     */
    void generateTask(Job job, Task task);

    /**
     * 保存找不到平台信息异常
     */
    void saveNoPlatformResult(Task task);

    /**
     * 处理路由策略异常
     */
    void saveRoutingStrategyExceptionResult(Task task, String exceptionMsg);

    /**
     * 保存路由策略选取的worker
     */
    void saveWorkerUrl(Task task, String workerUrl);

    /**
     * 保存worker返回的确认接受
     */
    void saveConfirmationResult(int taskId, HashMap distributeResult);

    /**
     * 保存任务执行结果
     */
    void saveTaskResult(Map<String, Object> taskResult);

}
