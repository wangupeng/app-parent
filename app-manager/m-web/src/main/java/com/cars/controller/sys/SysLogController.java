package com.cars.controller.sys;

import com.cars.model.sys.SysLog;
import com.cars.service.sys.SysLogService;
import com.cars.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by wangyupeng on 2018/6/25 16:48
 */
@Controller
@RequestMapping("/sys/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;
    /**
     * 日志列表
     * @param sysLog
     * @return
     */

    @RequestMapping
    public ModelAndView listLog(SysLog sysLog){
        if(sysLog.getStartDate()==null||"".equals(sysLog.getStartDate())){
            sysLog.setStartDate(DateUtil.getSystemDate());
            sysLog.setEndDate(DateUtil.getSystemDate());
        }

        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(sysLog.getPageNum(), sysLog.getPageSize());
        //查询用户列表
        Example example = new Example(SysLog.class);
        example.createCriteria().andGreaterThanOrEqualTo("operaDate", sysLog.getStartDate()+" 00:00:00").andLessThanOrEqualTo("operaDate",sysLog.getEndDate()+" 24:00:00");
        List<SysLog> listLog = sysLogService.selectByExample(example);

        PageInfo<SysLog> pageInfo = new PageInfo<SysLog>(listLog);
        mv.addObject("pageInfo",pageInfo);

        mv.addObject("sysLog",sysLog);
        mv.setViewName("sys/log/listLog");
        return mv;
    }
}
