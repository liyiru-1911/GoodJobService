package com.goodjob.distribution_center.service.Impl;

import com.goodjob.distribution_center.config.ScheduleRegister;
import com.goodjob.distribution_center.domain.JobCache;
import com.goodjob.distribution_center.domain.PlatformCache;
import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.domain.pojo.Platform;
import com.goodjob.distribution_center.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitServiceImpl implements InitService {
    @Autowired
    ScheduleRegister scheduleRegister;

    @Override
    public Map<String, Object> healthCheck() {
        return null;
    }

    @Override
    public Map<String, Object> incomingJobs(List<Job> jobs) {
        Map<String, Object> result = new HashMap<>();
        try {
            JobCache jobCache = JobCache.getInstance();
            for (Job job : jobs) {
                jobCache.add(job);
                if ("true".equals(job.getStatus())) {
                    scheduleRegister.loadJob(job.getUuid());
                }
            }
            result.put("success", true);
            result.put("msg", "装载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "装载失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> incomingPlatforms(List<Platform> platforms) {
        Map<String, Object> result = new HashMap<>();
        try {
            PlatformCache platformCache = PlatformCache.getInstance();
            for (Platform platform : platforms) {
                platformCache.add(platform);
            }
            result.put("success", true);
            result.put("msg", "装载完毕");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "装载失败");
        }
        return result;
    }
}
