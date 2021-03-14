package com.goodjob.distribution_center.service.Impl;

import com.goodjob.distribution_center.dao.TaskDao;
import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.JobCache;
import com.goodjob.distribution_center.domain.PlatformCache;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.service.SynchronizeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SynchronizeTaskServiceImpl implements SynchronizeTaskService {
    @Autowired
    TaskDao taskDao;

    @Lazy
    @Autowired
    DistributeServiceImpl distributeService;

    @Override
    public void generateTask(Job job, Task task) {
        if (task == null) {
            task = new Task();
            task.setJobUuid(job.getUuid());
            task.setTryTimes(1);
        } else {
            task.setTryTimes(task.getTryTimes() + 1);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
    }

    @Override
    public void saveNoPlatformResult(Task task) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunEndTime(df.format(new Date()));
        if (needTryAgain(task)) {
            distributeService.distributeJob(task.getJobUuid(), task);
        } else {
            complete(task, false, "找不到对应平台信息，请检查平台配置情况");
        }
    }

    @Override
    public void saveRoutingStrategyExceptionResult(Task task, String exceptionMsg) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunEndTime(df.format(new Date()));
        if (needTryAgain(task)) {
            distributeService.distributeJob(task.getJobUuid(), task);
        } else {
            complete(task, false, exceptionMsg);
        }
    }

    @Override
    public void saveWorkerUrl(Task task, String workerUrl) {
        String taskUsedWorkerUrls = task.getUsedWorkerUrls();
        if (taskUsedWorkerUrls == null || taskUsedWorkerUrls.isEmpty()) {
            task.setUsedWorkerUrls(workerUrl);
        } else {
            task.setUsedWorkerUrls(taskUsedWorkerUrls + "," + workerUrl);
        }
    }

    @Override
    public void saveConfirmationResult(int taskId, HashMap distributeResult) {
        if ((boolean) distributeResult.get("success")) {
            return;
        }
        // 未分发成功，判断是否需要再次尝试
        Task task = taskDao.findById(taskId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunEndTime(df.format(new Date()));
        if (needTryAgain(task)) {
            distributeService.distributeJob(task.getJobUuid(), task);
        } else {    // 不再重试，最后一次执行结果未worker未接受
            complete(task, false, "任务分发失败，原因：" + distributeResult.get("msg"));
        }
    }

    @Override
    public void saveTaskResult(Map<String, Object> taskResult) {

    }

    /**
     * 未执行成功的任务是否需要再次执行
     * 当前再次执行的情况不外乎：tryTimes未用完、设置了故障转移且未用完
     *
     * @param task
     * @return
     */
    private boolean needTryAgain(Task task) {
        JobCache jobCache = JobCache.getInstance();
        Job job = jobCache.get(task.getJobUuid());
        PlatformCache platformCache = PlatformCache.getInstance();
        Platform platform = platformCache.get(job.getPlatformUuid());
        // 判断故障转移
        if ("fail_over".equals(job.getRoutingStrategyCode())) {
            return needTryAgainWhenFailOver(task, platform);
        }
        return job.getReTryTimes() > task.getTryTimes();
    }

    /**
     * 故障转移是否需要继续
     *
     * @param task
     * @param platform
     * @return
     */
    private boolean needTryAgainWhenFailOver(Task task, Platform platform) {
        Set<String> usedWorkerSet = new HashSet<>(Arrays.asList(task.getUsedWorkerUrls().split(",")));
        for (String oneOfAllWorker : platform.getServers()) {
            if (!usedWorkerSet.contains(oneOfAllWorker)) return true;
        }
        return false;
    }

    /**
     * 单次任务完结
     * TODO 更新状态并同步到JobCenter
     *
     * @param task
     */
    private void complete(Task task, Boolean success, String msg) {
        task.setSuccess(success);
        task.setMsg(msg);
        task.setComplete("complete");
        taskDao.insert(task);
    }
}
