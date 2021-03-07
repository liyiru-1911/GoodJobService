package com.goodjob.distribution_center.dao;

import com.goodjob.distribution_center.domain.Entity.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TaskDao {

    @Insert("insert into task(job_uuid, last_run_start_time, last_run_end_time, used_worker_urls, accepted, try_times, success, msg, complete) " +
            "values (#{jobUuid}, #{lastRunStartTime}, #{lastRunEndTime}, #{usedWorkerUrls}, #{accepted}, #{tryTimes}, #{success}, #{msg}, #{complete})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    @Select("select * from task where id = #{id}")
    @Results(id = "allData", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "last_run_start_time", property = "lastRunStartTime"),
            @Result(column = "last_run_end_time", property = "lastRunEndTime"),
            @Result(column = "used_worker_urls", property = "usedWorkerUrls"),
            @Result(column = "accepted", property = "accepted"),
            @Result(column = "try_times", property = "tryTimes"),
            @Result(column = "success", property = "success"),
            @Result(column = "msg", property = "msg"),
            @Result(column = "complete", property = "complete")
    })
    Task findById(@Param("id") int id);

    @Update({ "update task set job_uuid = #{jobUuid}, last_run_start_time = #{lastRunStartTime}, last_run_end_time = #{lastRunEndTime}, used_worker_urls = #{usedWorkerUrls}" +
            "accepted = #{accepted}, try_times = #{tryTimes}, success = #{success}, msg = #{msg}, complete = #{complete} where id = #{id}" })
    int update(Task task);
}
