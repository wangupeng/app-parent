package com.cars.controller.sys;

import com.cars.model.sys.SysResource;
import com.cars.model.sys.SysRole;
import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysRoleService;
import com.cars.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Controller
@RequestMapping("/sys/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService2;

    /**
     * 列表
     * @return
     */
    @RequestMapping
    public ModelAndView listRole(SysRole sysRole){
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(sysRole.getPageNum(), sysRole.getPageSize());
        List<SysRole> listRole = sysRoleService2.select(sysRole);
        PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(listRole);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("sys/role/listRole");
        return mv;
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "sys/role/addRole";
    }
    /**
     * 添加
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public int addRole(SysRole sysRole,@SessionAttribute SysUser userSession){
        sysRole.setCreateUser(userSession.getUserName());
        sysRole.setCreateDate(DateUtil.getSystemDate());
        return sysRoleService2.insert(sysRole);
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "sys/role/updateRole";
    }
    /**
     * 修改角色
     * @param sysRole
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public int updateRole(SysRole sysRole,@SessionAttribute SysUser userSession){
        sysRole.setLastModifiedUser(userSession.getUserName());
        sysRole.setLastModifiedDate(DateUtil.getSystemDate());
        return sysRoleService2.update(sysRole);
    }

    /**
     * 删除角色
     * @param roleCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public int deleteRole(String roleCode){
        return sysRoleService2.delete(roleCode);
    }

    @RequestMapping("/toAuthorize")
    public String toAuthorize(){
        return "sys/role/authorize";
    }
    /**
     * 授权
     * @param roleCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRoleResource")
    public int addRoleResource(String roleCode,@RequestParam(value = "resourceIds",required = false)String[] resourceIds){
        Map<String, Object> map = new HashMap<>();
        map.put("roleCode", roleCode);
        map.put("resourceIds", resourceIds);
        return sysRoleService2.addRoleResource(map);
    }

    /**
     * 根据角色ID获取资源
     * @param roleCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/listResourceByRoleCode")
    public List<SysResource> listResourceByRoleCode(String roleCode){
        List<SysResource> list = sysRoleService2.listResourceByRoleCode(roleCode);
        return list;
    }

    /**
     * 根据roleCode获取角色信息
     * @param roleCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleByRoleCode")
    public SysRole getRoleByRoleCode(String roleCode){
        SysRole sysRole = sysRoleService2.selectById(roleCode);
        return sysRole;
    }

    /**
     * ajax获取所有角色
     * @param sysRole
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRole")
    public List<SysRole> getRole(SysRole sysRole){
        return sysRoleService2.select(sysRole);
    }

    /**
     * 判断roleCode是否已存在
     * @param roleCode
     * @return
     */
    @RequestMapping("/checkExist")
    @ResponseBody
    public String checkExist(String roleCode){
        SysRole sysRole = sysRoleService2.selectById(roleCode);
        String existFlag = "false";
        if(sysRole==null){
            existFlag ="true";
        }
        return existFlag;
    }
}
