package com.cars.controller.sys;

import com.cars.model.sys.SysTask;
import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysTaskService;
import com.cars.task.MyScheduler;
import com.cars.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Controller
@RequestMapping("/sys/sysTask")
public class SysTaskController {
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private MyScheduler myScheduler ;

    /**
     * 任务列表
     * @param sysTask
     * @return
     */
    @RequestMapping
    public ModelAndView list(SysTask sysTask){
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(sysTask.getPageNum(), sysTask.getPageSize());
        //查询任务列表
        Example example = new Example(SysTask.class);
        example.createCriteria().andLike("jobName",sysTask.getJobName());
        List<SysTask> listTask = sysTaskService.selectByExample(example);

//        List<SysTask> listTask = sysTaskService.select(sysTask);
        PageInfo<SysTask> pageInfo = new PageInfo<SysTask>(listTask);
        mv.addObject("pageInfo",pageInfo);

        mv.addObject("sysTask",sysTask);
        mv.setViewName("sys/task/listTask");
        return mv;
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "sys/task/addTask";
    }
    /**
     * 添加任务,同时启动任务
     * @param sysTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public int add(SysTask sysTask,@SessionAttribute SysUser userSession){
        int n = 0;
        boolean isStarted = myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
        if(isStarted){
            sysTask.setCreateUser(userSession.getUserName());
            sysTask.setCreateDate(DateUtil.getSystemTime());
            sysTask.setUpdateUser(userSession.getUserName());
            sysTask.setStatus("1");
            sysTask.setJobId(DateUtil.getShortSystemTime());
            n = sysTaskService.insert(sysTask);
            if(n<=0) myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
        }
        return n;
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "sys/task/updateTask";
    }
    /**
     * 修改任务
     * @param sysTask
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public int update(SysTask sysTask,@SessionAttribute SysUser userSession){
        int n = 0;
        SysTask sysTask2 = sysTaskService.selectById(sysTask.getJobId());
        try {
            boolean isDeleted = myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
            if(isDeleted){
                boolean isStarted = myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
                if(isStarted){
                    sysTask.setUpdateUser(userSession.getUserName());
                    sysTask.setUpdateDate(DateUtil.getSystemTime());
                    n = sysTaskService.update(sysTask);
                    if(n<=0){
                        myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
                        myScheduler.startJob(sysTask2.getCronExpression(),sysTask2.getJobGroup(),sysTask2.getJobName(),sysTask2.getJobClass());
                    }
                }else{
                    myScheduler.startJob(sysTask2.getCronExpression(),sysTask2.getJobGroup(),sysTask2.getJobName(),sysTask2.getJobClass());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * 删除任务
     * @param jobId
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public int deleteTask(String jobId){
        int n = 0;
        SysTask sysTask = sysTaskService.selectById(jobId);
        boolean isDeleted = myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
        if(isDeleted){
            n = sysTaskService.deleteByPrimaryKey(jobId);
            if(n<=0) myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
        }
        return n;
    }

    /**
     * 暂停任务
     * @param jobId
     * @return
     */
    @ResponseBody
    @RequestMapping("/pause")
    public int pause(String jobId){
        int n = 0;
        try {
            SysTask sysTask = sysTaskService.selectById(jobId);
            boolean isDeleted = myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
            if(isDeleted){
                n = sysTaskService.pause(jobId);
                if(n<=0) myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }
    /**
     * 恢复任务
     * @param jobId
     * @return
     */
    @ResponseBody
    @RequestMapping("/start")
    public int start(String jobId){
        int n = 0;
        try {
            SysTask sysTask = sysTaskService.selectById(jobId);
            boolean isStarted = myScheduler.startJob(sysTask.getCronExpression(),sysTask.getJobGroup(),sysTask.getJobName(),sysTask.getJobClass());
            if(isStarted){
                n = sysTaskService.start(jobId);
                if(n<=0) myScheduler.deleteJob(sysTask.getJobName(),sysTask.getJobGroup());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n;
    }

    /**
     * 根据任务名获取任务
     * @param jobId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTaskByJobId")
    public SysTask getTaskByJobId(String jobId){
        return sysTaskService.selectById(jobId);
    }

    /*@RequestMapping("/checkExist")
    @ResponseBody
    public String checkExist(String TaskName){
        SysTask sysTask = TaskService.getTaskByTaskName(TaskName);
        String existFlag = "false";
        if(sysTask==null){
            existFlag ="true";
        }
        return existFlag;
    }*/
}
