package com.goodjob.distribution_center.service;

/**
 * 任务分发服务
 */
public interface DistributeService {

    /**
     * 调度中心核心服务 - 分发任务
     * job A的时间到，根据A的uuid查询到其详情
     * 创建task
     * 创建处理请求Handler
     * 获取A所属的平台信息
     * 根据A的路由策略选择机器
     * 分发Handler并回填是否分发成功（并非是否执行成功）
     * 保存task
     *
     * @param uuid 被调度job的uuid
     */
    void createTaskAndDistributeIt(String uuid);

}
