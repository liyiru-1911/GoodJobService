package com.goodjob.distribution_center.dao;

import com.goodjob.distribution_center.domain.Entity.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TaskDao {

    @Insert("insert into task(job_uuid, last_run_start_time, last_run_end_time, last_run_worker_url, accepted, try_times, success, msg) " +
            "values (#{jobUuid}, #{lastRunStartTime}, #{lastRunEndTime}, #{lastRunWorkerUrl}, #{accepted}, #{tryTimes}, #{success}, #{msg})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

}
