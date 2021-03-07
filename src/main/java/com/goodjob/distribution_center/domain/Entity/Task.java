package com.goodjob.distribution_center.domain.Entity;


import javax.persistence.*;

/**
 * 执行情况
 * 每次执行前生成，分发和收到反馈后完善，最终结果同步到JobCenter
 */
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // 所属Job的uuid
    @Column(name = "job_uuid")
    private String jobUuid;

    // 最后一次被调度时间 （如果重试，则记录最后一次调度）
    @Column(name = "last_run_start_time")
    private String lastRunStartTime;

    // 最后一次执行结束时间 （对应lastRunStartTime的调度）
    @Column(name = "last_run_end_time")
    private String lastRunEndTime;

    // 用过的worker url,用逗号隔开，后使用者加在后面 (标识执行机、用于故障转移路由策略)
    @Column(name = "used_worker_urls")
    private String usedWorkerUrls;

    // 是否被worker接受
    @Column(name = "accepted")
    private Boolean accepted;

    // 执行次数
    @Column(name = "try_times")
    private Integer tryTimes;

    // 执行结果 （是否成功）
    @Column(name = "success")
    private Boolean success;

    // 执行结果消息
    @Column(name = "msg")
    private String msg;

    @Column(name = "complete")
    private String complete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public String getLastRunStartTime() {
        return lastRunStartTime;
    }

    public void setLastRunStartTime(String lastRunStartTime) {
        this.lastRunStartTime = lastRunStartTime;
    }

    public String getLastRunEndTime() {
        return lastRunEndTime;
    }

    public void setLastRunEndTime(String lastRunEndTime) {
        this.lastRunEndTime = lastRunEndTime;
    }

    public String getUsedWorkerUrls() {
        return usedWorkerUrls;
    }

    public void setUsedWorkerUrls(String usedWorkerUrls) {
        this.usedWorkerUrls = usedWorkerUrls;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
