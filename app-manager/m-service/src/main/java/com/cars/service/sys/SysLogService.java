package com.cars.service.sys;

import com.cars.dao.sys.SysLogDao;
import com.cars.model.sys.SysLog;
import com.cars.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyupeng on 2018/6/25 16:48
 */
@Service
public class SysLogService extends BaseService<SysLog> {
    @Autowired
    private SysLogDao sysLogDao;

    public int insertBatch(List<SysLog> list){
        return sysLogDao.insertBatch(list);
    }
}
