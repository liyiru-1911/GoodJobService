package com.goodjob.distribution_center.service;

import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;

import java.util.List;
import java.util.Map;

/**
 * 从JobCenter接受任务并初始化
 */
public interface InitService {

    /**
     * 初始化前JobCenter会检查每台注册调度中心是否健在
     *
     * @return 是否健在
     */
    Map<String, Object> healthCheck();

    /**
     * 传入任务并部署
     *
     * @param jobs
     * @return
     */
    Map<String, Object> incomingJobs(List<Job> jobs);

    /**
     * 传入平台信息并部署
     *
     * @param platforms
     * @return
     */
    Map<String, Object> incomingPlatforms(List<Platform> platforms);

}
