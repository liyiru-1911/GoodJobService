package com.goodjob.distribution_center.utils.RoutringStrategy;

import com.goodjob.distribution_center.utils.MyException.RoutingStrategyException;

import java.util.HashMap;
import java.util.Map;

public class FindServerFactory {

    private static final Map<String, FindServer> findServerMap = new HashMap<>();

    // 路由策略类是无状态的，只提供算法，故可以复用同一策略对象
    static {
        findServerMap.put("first", new FindServerFirst());
        findServerMap.put("last", new FindServerLast());
        findServerMap.put("random", new FindServerRandom());
        findServerMap.put("fail_over", new FindServerFailOver());
    }

    protected static FindServer getFindServer(String routingStrategyCode) throws Exception {
        if (routingStrategyCode == null || routingStrategyCode.isEmpty()) {
            throw new RoutingStrategyException("路由策略代号为空");
        }
        return findServerMap.get(routingStrategyCode);
    }
}
