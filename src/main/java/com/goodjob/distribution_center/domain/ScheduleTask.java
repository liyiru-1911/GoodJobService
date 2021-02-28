package com.goodjob.distribution_center.domain;

import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务Future类
 */
public class ScheduleTask {

    public volatile ScheduledFuture<?> future;

    public void stop() {
        ScheduledFuture<?> future = this.future;
        if (future != null) future.cancel(true);
    }

}
