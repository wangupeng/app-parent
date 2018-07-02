package com.cars.controller.sys;

import com.cars.model.sys.SysResource;
import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysResourceService;
import com.cars.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Controller
@RequestMapping("/sys/sysResource")
public class SysResourceController {
    @Autowired
    private SysResourceService sysResourceService;

    @RequestMapping
    public String listResource(){
        return "sys/resource/listResource";
    }

    /**
     * 根据parentId查询资源
     * @return
     */
    @ResponseBody
    @RequestMapping("/listResourceByParentId")
    public List<SysResource> listResourceByParentId(SysResource sysResource){
        List<SysResource> listResource = sysResourceService.listResource(sysResource);
        return listResource;
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "sys/resource/addResource";
    }
    /**
     * 新增资源
     * @param sysResource
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public int addResource(SysResource sysResource){
        sysResource.setResourceId(DateUtil.getShortSystemTime());
        return sysResourceService.insert(sysResource);
    }

    /**
     * 修改资源
     * @param resourceId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getResourceById")
    public SysResource getResourceById(String resourceId){
        return sysResourceService.getResourceById(resourceId);
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "sys/resource/updateResource";
    }

    /**
     * 更新资源
     * @param sysResource
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public int updateResource(SysResource sysResource){
        return sysResourceService.update(sysResource);
    }

    /**
     * 删除资源
     * @param resourceId
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public int deleteResource(String resourceId){
        return sysResourceService.deleteResource(resourceId);
    }


    /**
     * 获取 模块 资源
     * @return
     */
    @ResponseBody
    @RequestMapping("/listModuleResource")
    public List<Map<String,String>> listModuleResource(SysResource sysResource){
        sysResource.setResourceType("1");
        PageHelper.startPage(1, 9999);
        List<SysResource> listResource = sysResourceService.listResource(sysResource);
        SysResource s = new SysResource();
        s.setResourceId("0");
        s.setResourceName("资源管理");
        listResource.add(s);

        List<Map<String,String>> list = new ArrayList<>();
        for (SysResource resource : listResource) {
            Map<String,String> map = new HashMap<>();
            map.put("id",resource.getResourceId());
            map.put("pid",resource.getParentId());
            map.put("name",resource.getResourceName());
            list.add(map);
        }
        return list;
    }

    /**
     * 查询用户拥有的菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadMenu")
    public List<SysResource> loadMenu(@SessionAttribute SysUser userSession){
        Map<String,Object> map = new HashMap<>();
        map.put("resourceType",1);
        map.put("userName",userSession.getUserName());
        return sysResourceService.loadUserResource(map);
    }

    /**
     * 生成所有资源Ztree的树节点
     * @return
     */
    @ResponseBody
    @RequestMapping("/createResourceZtree")
    public List<Map<String,String>> createResourceZtree(){
        List<Map<String,String>> list = sysResourceService.createResourceZtree(null);
        return list;
    }
}
