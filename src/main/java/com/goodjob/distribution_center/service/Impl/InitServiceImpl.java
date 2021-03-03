package com.goodjob.distribution_center.service.Impl;

import com.goodjob.distribution_center.domain.pojo.Job;
import com.goodjob.distribution_center.service.InitService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InitServiceImpl implements InitService {

    @Override
    public Map<String, Object> healthCheck() {
        return null;
    }

    @Override
    public Map<String, Object> incomingJobs(List<Job> jobs) {
        return null;
    }
}
