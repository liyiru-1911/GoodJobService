package com.goodjob.distribution_center.controller;

import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/initJob")
public class InitController {
    @Autowired
    InitService initService;

    @GetMapping("/initJobs")
    public Map<String, Object> initJobs() {
        // TODO 从参数中获取传入的任务列表
        return initService.incomingJobs(testGetJobs());
    }

    @GetMapping("/initPlatforms")
    public Map<String, Object> initPlatforms() {
        // TODO 从参数列表中获取传入的平台列表
        return initService.incomingPlatforms(testGetPlatforms());
    }

    private List<Job> testGetJobs() {
        Job job = new Job();
        job.setUuid("1234567");
        job.setName("first job");
        job.setDescription("第一个运行的Job");
        job.setOwner("liyiru");
        job.setPlatformUuid("123");
        job.setPlatformName("测试平台");
        job.setRoutingStrategyCode("first");
        job.setRoutingStrategyName("获取第一个机器");
        job.setReTryTimes(0);
        job.setStatus("true");
        job.setCron("0/10 * * * * ? ");
        job.setClassName("com.goodjob.distribution_center.worker.TestRunHandler");
        job.setMethodName("workerProcessJob");
        String[] paramTypes = new String[2];
        paramTypes[0] = "java.lang.String";
        paramTypes[1] = "java.lang.Integer";
        job.setParamTypes(paramTypes);
        Object[] params = new Object[2];
        params[0] = "hello word";
        params[1] = 6;
        job.setParams(params);

        List<Job> jobs = new ArrayList<>();
        jobs.add(job);
        return jobs;
    }

    private List<Platform> testGetPlatforms() {
        Platform platform = new Platform();
        platform.setUuid("123");
        platform.setName("测试平台");
        String[] servers = new String[1];
        servers[0] = "192.168.8.103:8888/goodJobWorker/run";
        platform.setServers(servers);
        List<Platform> platforms = new ArrayList<>();
        platforms.add(platform);
        return platforms;
    }
}
