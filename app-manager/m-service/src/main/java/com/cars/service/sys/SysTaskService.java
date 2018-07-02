package com.cars.service.sys;

import com.cars.dao.sys.SysTaskDao;
import com.cars.model.sys.SysTask;
import com.cars.model.sys.SysUser;
import com.cars.service.base.BaseService;
import com.cars.task.MyScheduler;
import com.cars.util.date.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Component
public class SysTaskService extends BaseService<SysTask> {
    @Autowired
    private SysTaskDao taskDao;
    @Autowired
    private MyScheduler myScheduler ;

    private static Logger logger = LoggerFactory.getLogger("sysLog");

    /**
     * 在启动程序时启动定时任务
     * @return
     */
    public void startTaskOnRun(){
        List<SysTask> list = taskDao.select(null);
        for(SysTask sysTask:list){
            if("1".equals(sysTask.getStatus())){
                boolean isStarted = myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
                if(isStarted){
                    logger.info("定时任务："+sysTask.getJobName()+"."+sysTask.getJobGroup()+"已启动。");
                }
            }
        }
    }

    /**
     * 开始任务
     * @param jobId
     * @return
     */
    public int start(String jobId){
        return taskDao.start(jobId);
    }
    /**
     * 暂停任务
     * @param jobId
     * @return
     */
    public int pause(String jobId){
        return taskDao.pause(jobId);
    }
}
