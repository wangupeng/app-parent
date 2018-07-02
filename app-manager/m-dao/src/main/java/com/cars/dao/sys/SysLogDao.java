package com.cars.dao.sys;

import com.cars.base.BaseDao;
import com.cars.model.sys.SysLog;

import java.util.List;

/**
 * Created by wangyupeng on 2018/6/25 16:47
 */
public interface SysLogDao extends BaseDao<SysLog> {
    int insertBatch(List<SysLog> list);
}
