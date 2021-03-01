package com.goodjob.distribution_center.service;

import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;

import java.util.Map;

/**
 * 从JobCenter接受任务的新增、删除、暂停、修改等命令
 * 支撑任务的热部署
 *
 */
public interface JobChangeService {

    /**
     * 新增任务
     *
     * @param job
     * @return
     */
    Map<String, Object> addJob(Job job);

    /**
     * 删除任务
     *
     * @param uuid
     * @return
     */
    Map<String, Object> deletJob(String uuid);

    /**
     * 暂停任务
     *
     * @param uuid
     * @return
     */
    Map<String, Object> pauseJob(String uuid);

    /**
     * 与暂停对应，重启任务
     *
     * @param uuid
     * @return
     */
    Map<String, Object> reStartJob(String uuid);

    /**
     * 任务切换其所属平台
     *
     * @param jobUuid
     * @param newPlatFormUuid
     * @return
     */
    Map<String, Object> updateWhichPlatformJobBelong(String jobUuid, String newPlatFormUuid);

    /**
     * 任务切换其执行计划
     *
     * @param uuid
     * @param cron
     * @return
     */
    Map<String, Object> updateJobCron(String uuid, String cron);

    /**
     * 新增平台
     *
     * @param platform
     * @return
     */
    Map<String, Object> addPlatform(Platform platform);

    /**
     * 删除平台
     *
     * @param uuid
     * @return
     */
    Map<String, Object> deletPlatform(String uuid);

    /**
     * 修改平台下属机器
     *
     * @param uuid
     * @param servers
     * @return
     */
    Map<String, Object> updateServers(String uuid, String[] servers);
}
