package com.goodjob.distribution_center.service;

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


}
