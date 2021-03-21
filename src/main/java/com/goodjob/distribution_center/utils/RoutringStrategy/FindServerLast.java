package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;

public class FindServerLast implements FindServer {
    @Override
    public String findServer(Platform platform, Task task) throws RoutingStrategyException {
        int serverCount = platform.getServers().length;
        if (serverCount > 0) {
            return platform.getServers()[serverCount - 1];
        } else {
            throw new RoutingStrategyException("平台服务器数量为0");
        }
    }
}
