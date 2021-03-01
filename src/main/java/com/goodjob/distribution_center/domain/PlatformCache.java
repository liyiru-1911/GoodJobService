package com.goodjob.distribution_center.domain;

import com.goodjob.distribution_center.domain.pojo.Platform;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度中心在此缓存全部平台信息
 * 该类为单例模式，全局唯一
 * 采用线程安全的map确保并发访问安全性
 */
public class PlatformCache {

    private PlatformCache() {}

    private static volatile PlatformCache platformCache;

    public static PlatformCache getInstance() {
        if (platformCache == null) {
            synchronized (PlatformCache.class) {
                if (platformCache == null) {
                    platformCache = new PlatformCache();
                }
            }
        }
        return platformCache;
    }

    private Map<String, Platform> platFormCacheMap = new ConcurrentHashMap<>();

    // 新增
    public void add(Platform platform) {
        this.platFormCacheMap.put(platform.getUuid(), platform);
    }

    // 删除
    public void remove(String uuid) {
        this.platFormCacheMap.remove(uuid);
    }

    // 修改
    public void update(Platform platform) {
        add(platform);
    }

    // 查询
    public Platform get(String uuid) {
        return this.platFormCacheMap.get(uuid);
    }

}
