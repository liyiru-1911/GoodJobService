package com.goodjob.distribution_center.worker;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyiru-1911
 * 模拟客户端处理器
 * 1.请在处理器类加上@Component
 * 2.执行方法返回值必须为Map<String, Object>, 并提供success、msg参数标识执行是否成功
 * 3.执行方法使用try catch结构
 * 4.由于异步执行，请在启动类上加上@EnableAsync
 */
@Component
public class TestRunHandler {

    public Map<String, Object> workerProcessJob(String s, Integer b) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("执行器workerProcessJob开始处理，当前线程 : " + Thread.currentThread().getName());
            // 在此填写处理逻辑
            if (b == 6) {
                result.put("success", true);
                result.put("msg", "成功执行，很快");
            } else {
                result.put("success", false);
                result.put("msg", "执行失败，");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "执行出现异常");
        }
        return result;
    }
}
