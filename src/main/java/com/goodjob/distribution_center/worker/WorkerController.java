package com.goodjob.distribution_center.worker;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

/**
 * worker端对外暴露的接口，接受调度中心下发的任务执行请求
 */
@RestController
@RequestMapping("/goodJobWorker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping("/run")
    public Map<String, Object> run() {
        // TODO 根据入参序列化出handler
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("***任务进入***");
            workerService.findHandlerAndRunThenSendResultBack(getHandlerTest());
            result.put("success", true);
            result.put("msg", "执行器状态健康，分发成功");
        } catch (RejectedExecutionException e) {
            System.out.println("执行器任务满，拒绝执行！");
            result.put("success", false);
            result.put("msg", "执行器繁忙");
        }
        System.out.println("***任务结束***");
        return result;
    }

    // 测试用Handler
    private Handler getHandlerTest() {
        Handler handler = new Handler();
        handler.setClassName("com.goodjob.distribution_center.worker.TestRunHandler");
        handler.setMethodName("workerProcessJob");
        String[] paramTypes = new String[2];
        paramTypes[0] = "java.lang.String";
        paramTypes[1] = "java.lang.Integer";
        handler.setParamTypes(paramTypes);
        Object[] params = new Object[2];
        params[0] = "hello word";
        params[1] = 6;
        handler.setParams(params);
        return handler;
    }
}
