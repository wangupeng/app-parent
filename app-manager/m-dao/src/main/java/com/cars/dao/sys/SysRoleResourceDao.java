package com.cars.dao.sys;

import com.cars.base.BaseDao;
import com.cars.model.sys.SysResource;
import com.cars.model.sys.SysRoleResource;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyupeng on 2017/8/18.
 */
public interface SysRoleResourceDao extends BaseDao<SysRoleResource> {

    /**
     * 增加角色资源对应关系
     * @param map
     * @return
     */
    int add(Map<String, Object> map);

    /**
     * 根据资源id删除角色资源对应关系
     * @param resourceId
     * @return
     */
    int deleteByResourceId(String resourceId);


    /**
     * 根据角色ID获取资源
     * @param roleCode
     * @return
     */
    List<SysResource> listByRoleCode(String roleCode);
}
