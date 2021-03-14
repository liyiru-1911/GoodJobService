package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;
import org.springframework.stereotype.Component;
/**
 * 当前支持的路由策略有：
 * first - 获取首个
 * last - 获取最后一个
 * random - 随机获取
 * fail_over - 错误转移，错误依次重试直到全部尝试完
 * TODO weights_random - 权重随机
 *
 * 说明：为了减少同步时间，降低性能瓶颈，放弃需要全局同步平台状态的LRU和轮询算法。
 *      如果只在单个调度中心上实现，那么对性能影响不大，但同时这两种算法也会显得略为鸡肋
 */
@Component
public class RoutingStrategy {

    public String findWorkerByRoutingStrategy(Platform platform, String routingStrategyCode, Task task) throws Exception {
        if (platform.getServers().length >= 1) {
            return platform.getServers()[0];
        } else {
            throw new RoutingStrategyException("平台服务器数量为0");
        }
    }

}
