package com.goodjob.distribution_center.utils;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import org.springframework.stereotype.Component;

@Component
public class RoutingStrategy {

    /**
     * TODO 根据路由策略获取执行机 - 首次执行
     *
     * @param platform 任务所属平台详情
     * @param routingStrategyCode 任务所用路由策略代码  *当前实现(first/last/round/random/fail_over)
     *                            TODO lRU（最近最少使用）/ weights_round(权重轮询)
     * @return 执行机url
     */
    public String findWorkerByRoutingStrategyForFirstTime(Platform platform, String routingStrategyCode) {
        if (platform.getServers().length >= 1) {
            return platform.getServers()[0];
        }
        return "";
    }

    /**
     * TODO 获取执行机 - 再次执行（tryTimes未用完 or fail_over）
     *
     * @param platform
     * @param routingStrategyCode
     * @param task
     * @return
     */
    public String findWorkerByRoutingStrategyForTryAgain(Platform platform, String routingStrategyCode, Task task) {
        if (platform.getServers().length >= 1) {
            return platform.getServers()[0];
        }
        return "";
    }
}
