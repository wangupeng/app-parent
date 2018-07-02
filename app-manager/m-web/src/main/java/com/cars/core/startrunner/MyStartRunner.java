package com.cars.core.startrunner;

import com.cars.service.sys.SysTaskService;
import com.cars.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangyupeng on 2018/6/26 15:13
 * 启动项目时执行
 */
@Component
public class MyStartRunner {
    @Autowired
    private SysTaskService sysTaskService;

    @PostConstruct
    public void test(){
        //在启动程序时启动定时任务
        sysTaskService.startTaskOnRun();
        System.out.println("启动时执行："+DateUtil.getSystemDate());// new Date()为获取当前系统时间
    }

}
