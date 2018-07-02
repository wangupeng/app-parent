package com.cars.dao.sys;

import com.cars.base.BaseDao;
import com.cars.model.sys.SysResource;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyupeng on 2017/8/18.
 */
public interface SysResourceDao extends BaseDao<SysResource> {

    /**
     * 查询资源
     * @return
     */
    List<SysResource> listResource(SysResource sysResource);

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    int deleteResource(String resourceId);

    /**
     * 根据ID类型查询资源
     * @param resourceId
     * @return
     */
    SysResource getResourceById(String resourceId);

    /**
     * 获取菜单资源
     * @param map
     * @return
     */
    List<SysResource> loadUserResource(Map<String, Object> map);
}
