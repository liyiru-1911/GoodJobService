package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindServerFailOver implements FindServer {

    @Override
    public String findServer(Platform platform, Task task) throws RoutingStrategyException {
        int serverCount = platform.getServers().length;
        if (serverCount > 0) {
            if (task == null) { // 首次运行
                return findServerForFirstTime(platform);
            } else {    // 失败重试运行
                Set<String> usedWorkerSet = new HashSet<>(Arrays.asList(task.getUsedWorkerUrls().split(",")));
                for (String oneOfAllWorker : platform.getServers()) {
                    if (!usedWorkerSet.contains(oneOfAllWorker)) return oneOfAllWorker;
                }
                throw new RoutingStrategyException("故障转移已全部尝试完，路由策略异常");
            }
        } else {
            throw new RoutingStrategyException("平台服务器数量为0");
        }
    }

    private String findServerForFirstTime(Platform platform) {
        return platform.getServers()[0];
    }
}
