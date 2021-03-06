package com.goodjob.distribution_center.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodjob.distribution_center.dao.TaskDao;
import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.JobCache;
import com.goodjob.distribution_center.domain.PlatformCache;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.net.HttpClientUtil;
import com.goodjob.distribution_center.net.Serialization;
import com.goodjob.distribution_center.service.DistributeService;
import com.goodjob.distribution_center.service.SynchronizeTaskService;
import com.goodjob.distribution_center.utils.RoutingStrategy;
import com.goodjob.distribution_center.worker.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DistributeServiceImpl implements DistributeService {
    // TODO 测试，需要统一配置
    private static final String localHostUrl = "111.222.111.22:8080/incomingTaskResult";

    @Autowired
    SynchronizeTaskService synchronizeTaskService;
    @Autowired
    TaskDao taskDao;
    @Autowired
    RoutingStrategy routingStrategy;
    @Autowired
    HttpClientUtil httpClientUtil;
    @Autowired
    Serialization serialization;

    @Override
    public void createTaskAndDistributeItForFirstTime(String uuid) {

        JobCache jobCache = JobCache.getInstance();
        Job job = jobCache.get(uuid);
        PlatformCache platformCache = PlatformCache.getInstance();
        Platform platform = platformCache.get(job.getPlatformUuid());

        String workerUrl = routingStrategy.findWorkerByRoutingStrategyForFirstTime(platform, job.getRoutingStrategyCode());

        Task task = synchronizeTaskService.generateTask(job, workerUrl);
        int taskId = taskDao.insert(task);

        Handler handler = createHandler(localHostUrl, taskId, job);

        HashMap distributeResult = distributeHandlerToWorker(workerUrl, handler);
        synchronizeTaskService.saveConfirmation(taskId, distributeResult);
    }

    /**
     * 创建处理请求
     *
     * @param localHostUrl
     * @param taskId
     * @param job
     * @return
     */
    private Handler createHandler(String localHostUrl, int taskId, Job job) {
        Handler handler = new Handler();
        handler.setClassName(job.getClassName());
        handler.setMethodName(job.getMethodName());
        handler.setParamTypes(job.getParamTypes());
        handler.setParams(job.getParams());
        handler.setTaskUrl(localHostUrl);
        handler.setTaskId(taskId);
        return handler;
    }

    /**
     * TODO 分发任务请求
     *
     * @param workerUrl
     * @param handler
     * @return
     */
    private HashMap distributeHandlerToWorker(String workerUrl, Handler handler) {
        String handlerString = JSONObject.toJSONString(handler);
        String confirmation = HttpClientUtil.httpPostWithJson(workerUrl, handlerString);
        return JSONObject.parseObject(confirmation, HashMap.class);
    }

}
