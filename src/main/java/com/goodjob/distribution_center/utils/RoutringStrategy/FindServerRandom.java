package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;

import java.util.Random;

public class FindServerRandom implements FindServer {
    @Override
    public String findServer(Platform platform, Task task) throws RoutingStrategyException {
        int serverCount = platform.getServers().length;
        if (serverCount > 0) {
            int randomServerIndex = getRandomNum(0, serverCount - 1);
            return platform.getServers()[randomServerIndex];
        } else {
            throw new RoutingStrategyException("平台服务器数量为0");
        }
    }

    // 随机获取一个范围在 [min, max] 中的数
    private int getRandomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
