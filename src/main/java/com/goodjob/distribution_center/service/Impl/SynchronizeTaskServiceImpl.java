package com.goodjob.distribution_center.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.service.SynchronizeTaskService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class SynchronizeTaskServiceImpl implements SynchronizeTaskService {

    @Override
    public Task generateTask(Job job) {
        Task task = new Task();
        task.setJobUuid(job.getUuid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        return task;
    }

    @Override
    public void saveConfirmation(int taskId, JSONObject distributeResult) {
    }

    @Override
    public Map<String, Object> syncTaskResult(Map<String, Object> taskResult) {
        return null;
    }
}
