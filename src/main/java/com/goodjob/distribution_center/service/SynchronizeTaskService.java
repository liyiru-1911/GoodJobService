package com.goodjob.distribution_center.service;

import com.alibaba.fastjson.JSONObject;
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
     * 保存worker确认接受任务信息
     */
    void saveConfirmation(int task, HashMap distributeResult);

    /**
     * 保存任务执行结果并同步到JobCenter
     *
     * @param taskResult
     * @return
     */
    Map<String, Object> syncTaskResult(Map<String, Object> taskResult);

}
