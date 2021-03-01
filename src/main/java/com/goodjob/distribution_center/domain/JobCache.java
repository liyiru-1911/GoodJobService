package com.goodjob.distribution_center.domain;


import com.goodjob.distribution_center.domain.pojo.Job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度中心在此缓存全部任务信息
 * 该类为单例模式，全局唯一
 * 采用线程安全的map确保并发访问安全性
 */
public class JobCache {

    private JobCache() {}

    private static volatile JobCache jobCache;

    public static JobCache getInstance() {
        if (jobCache == null) {
            synchronized (JobCache.class) {
                if (jobCache == null) {
                    jobCache = new JobCache();
                }
            }
        }
        return jobCache;
    }

    private Map<String, Job> jobCacheMap = new ConcurrentHashMap<>();

    // 新增 *调度中心不生产job，所有job都一定来自于JobCenter，故一定自带UUID
    public void add(Job job) {
        this.jobCacheMap.put(job.getUuid(), job);
    }

    // 删除
    public void remove(String uuid) {
        this.jobCacheMap.remove(uuid);
    }

    // 修改
    public void update(Job job) {
        add(job);
    }

    // 查询
    public Job get(String uuid) {
        return this.jobCacheMap.get(uuid);
    }

}
