package com.cars.dao.sys;

import com.cars.base.BaseDao;
import com.cars.model.sys.SysTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface SysTaskDao extends BaseDao<SysTask> {

    /**
     * 开始任务
     * @param jobId
     * @return
     */
    int start(String jobId);

    /**
     * 暂停任务
     * @param jobId
     * @return
     */
    int pause(String jobId);
}