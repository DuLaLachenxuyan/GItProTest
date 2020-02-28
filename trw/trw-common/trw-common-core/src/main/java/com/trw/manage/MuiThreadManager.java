package com.trw.manage;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理器
 *
 */
public class MuiThreadManager {

    //日志记录操作延时
    private final int OPERATE_DELAY_TIME = 10;

    //异步操作记录日志的线程池
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(OPERATE_DELAY_TIME);

    private MuiThreadManager() {
    }

    public static MuiThreadManager muiTManager = new MuiThreadManager();

    public static MuiThreadManager me() {
        return muiTManager;
    }

    public void executeLog(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
