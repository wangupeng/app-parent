package com.cars.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by wangyupeng on 2018/4/7 15:04
 */
@Component
public class MyScheduler {
    public boolean startJob(String cron,String group,String jobId,String job){
        boolean isStarted = false;
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            Class jobClass = Class.forName(job);
            // 创建jobDetail实例，绑定Job实现类，指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(jobClass/*MyJob1.class*/) .withIdentity(jobId, group).build();//设置Job的名字和组
            //jobDetail.getJobDataMap().put("name","MyName");//动态添加数据

            //corn表达式
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron/*"0/2 * * * * ?"*/);

            //设置定时任务的时间触发规则
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobId,group) .withSchedule(scheduleBuilder).build();

            // 把作业和触发器注册到任务调度中,返回值为任务出发时间
            scheduler.scheduleJob(jobDetail,cronTrigger);

            // 启动调度
            scheduler.start();

            isStarted = scheduler.isStarted();
        } catch (Exception e) {
            e.printStackTrace();
            isStarted = false;
        }finally {
        }
        return isStarted;
    }

    /****
     * 删除一个任务
     * @param triggerName
     * @param triggerGroupName
     */
    public boolean deleteJob(String triggerName,String triggerGroupName){
        boolean isDeleted = false;
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobKey jobKey = new JobKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail==null){
                return isDeleted;
            }
            isDeleted = scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}
