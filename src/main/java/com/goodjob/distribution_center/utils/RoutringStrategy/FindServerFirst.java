package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;

public class FindServerFirst implements FindServer {

    @Override
    public String findServer(Platform platform, Task task) throws RoutingStrategyException {
        if (platform.getServers().length > 0) {
            return platform.getServers()[0];
        } else {
            throw new RoutingStrategyException("平台服务器数量为0");
        }
    }
}
