package com.cars.task;

import org.quartz.*;

import java.util.Date;

/**
 * Created by wangyupeng on 2018/4/7 00:38
 */
@DisallowConcurrentExecution/*不要并发地执行同一个job*/
@PersistJobDataAfterExecution/*当设置 @PersistJobDataAfterExecution 时，在执行完 Job 的 execution 方法后保存 JobDataMap 当中固定数据，以便任务在重复执行的时候具有相同的 JobDataMap；在默认情况下也就是没有设置 @PersistJobDataAfterExecution 的时候每个 job 都拥有独立 JobDataMap。*/
public class SimpleJob2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(jobExecutionContext.getTrigger().getJobKey());
        System.out.println(new Date());
    }
}
