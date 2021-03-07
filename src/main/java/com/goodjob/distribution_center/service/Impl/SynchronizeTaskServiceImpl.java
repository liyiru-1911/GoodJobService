package com.goodjob.distribution_center.service.Impl;

import com.goodjob.distribution_center.dao.TaskDao;
import com.goodjob.distribution_center.domain.Entity.Task;
import com.goodjob.distribution_center.domain.JobCache;
import com.goodjob.distribution_center.domain.PlatformCache;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.service.SynchronizeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SynchronizeTaskServiceImpl implements SynchronizeTaskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    DistributeServiceImpl distributeService;

    @Override
    public Task generateTask(Job job, String workerUrl) {
        Task task = new Task();
        task.setJobUuid(job.getUuid());
        task.setTryTimes(1);
        task.setUsedWorkerUrls(workerUrl);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        return task;
    }

    @Override
    public void changeTaskWhenTryAgain(Task task, String workerUrl) {
        task.setTryTimes(task.getTryTimes() + 1);
        task.setUsedWorkerUrls(task.getUsedWorkerUrls() + "," + workerUrl);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        taskDao.update(task);
    }

    @Override
    public void saveNoPlatformResult(Job job) {
        Task task = new Task();
        task.setJobUuid(job.getUuid());
        task.setTryTimes(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        task.setLastRunEndTime(df.format(new Date()));
        task.setSuccess(false);
        task.setMsg("找不到对应平台信息，无法执行，请检查");
        complete(task);
    }

    @Override
    public void saveNoPlatformResult(Task task) {
        task.setTryTimes(task.getTryTimes() + 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        task.setLastRunEndTime(df.format(new Date()));
        task.setSuccess(false);
        task.setMsg("找不到对应平台信息，无法执行，请检查");
        complete(task);
    }

    @Override
    public void saveRoutingStrategyExceptionResult(Job job) {
        Task task = new Task();
        task.setJobUuid(job.getUuid());
        task.setTryTimes(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        task.setLastRunEndTime(df.format(new Date()));
        tryOrNotWhenRoutingStrategyException(task);
    }

    @Override
    public void saveRoutingStrategyExceptionResult(Task task) {
        task.setTryTimes(task.getTryTimes() + 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setLastRunStartTime(df.format(new Date()));
        task.setLastRunEndTime(df.format(new Date()));
        tryOrNotWhenRoutingStrategyException(task);
    }

    @Override
    public void saveConfirmationResult(int taskId, HashMap distributeResult) {
        Task task = taskDao.findById(taskId);
        // 分发成功
        if ((boolean) distributeResult.get("success")) {
            task.setAccepted(true);
            taskDao.update(task);
            return;
        }
        // 未分发成功，判断是否需要再次尝试
        if (needTryAgain(task)) {
            distributeService.distributeAgain(task);
        } else {    // 不再重试，最后一次执行结果未worker未接受
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            task.setLastRunEndTime(df.format(new Date()));
            task.setAccepted(false);
            task.setSuccess(false);
            task.setMsg("分发失败，" + distributeResult.get("msg"));
            complete(task);
        }
    }

    @Override
    public void saveTaskResult(Map<String, Object> taskResult) {

    }

    /**
     * 未执行成功的任务是否需要再次执行
     * 当前再次执行的情况分为：tryTimes未用完、设置了故障转移且未用完
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
     * 路由策略异常后续处理
     */
    private void tryOrNotWhenRoutingStrategyException(Task task) {
        if (needTryAgain(task)) {   // 重试
            distributeService.distributeAgain(task);
        } else {    // 不再重试
            task.setSuccess(false);
            task.setMsg("路由策略异常，请联系管理员");
            complete(task);
        }
    }

    /**
     * 单次任务完结，更新状态并同步到JobCenter
     *
     * @param task
     */
    private void complete(Task task) {
        task.setComplete("complete");
        taskDao.insert(task);
    }
}
