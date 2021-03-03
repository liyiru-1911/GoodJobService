package com.goodjob.distribution_center.utils;

import com.goodjob.distribution_center.domain.pojo.Platform;
import org.springframework.stereotype.Component;

@Component
public class RoutingStrategy {

    /**
     * 获取执行机入口
     *
     * @param platform 任务所属平台详情
     * @param routingStrategyCode 任务所用路由策略代码  *当前实现(first/last/round/random/fail_over)
     *                            TODO lRU（最近最少使用）/ weights_round(权重轮询)
     * @return 执行机url
     */
    public String findWorkerByRoutingStrategy(Platform platform, String routingStrategyCode) {
        return "233.233.233.233:8888/service/data/calculateData";
    }
}
