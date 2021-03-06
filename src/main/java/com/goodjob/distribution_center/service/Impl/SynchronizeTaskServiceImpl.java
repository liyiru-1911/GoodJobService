package com.goodjob.distribution_center.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.service.SynchronizeTaskService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SynchronizeTaskServiceImpl implements SynchronizeTaskService {

    @Override
    public Task generateTask(Job job, String workerUrl) {
        Task task = new Task();
        task.setJobUuid(job.getUuid());
        task.setTryTimes(1);
        task.setLastRunWorkerUrl(workerUrl);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        return task;
    }

    @Override
    public void saveConfirmation(int taskId, HashMap distributeResult) {
        System.out.println("*************worker是否确认接收任务应答***************");
        System.out.println(distributeResult.get("success"));
    }

    @Override
    public Map<String, Object> syncTaskResult(Map<String, Object> taskResult) {
        return null;
    }
}
