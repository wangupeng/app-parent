package com.cars.controller.sys;

import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Controller
@RequestMapping("/sys/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    /**
     * 用户列表
     * @param sysUser
     * @return
     */
    @RequestMapping
    public ModelAndView listUser(SysUser sysUser){
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        //查询用户列表
        List<SysUser> listUser = userService.listUser(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(listUser);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("sysUser",sysUser);
        mv.setViewName("sys/user/listUser");
        return mv;
    }

    /**
     * 查询所有
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectAll")
    public List<SysUser> selectAll(SysUser sysUser){
        return userService.selectAll();
    }


    @RequestMapping("/toAdd")
    public String toAdd(){
        return "sys/user/addUser";
    }
    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public int addUser(SysUser sysUser){
        return userService.insert(sysUser);
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "sys/user/updateUser";
    }
    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public int updateUser(SysUser sysUser){
        return userService.update(sysUser);
    }

    /**
     * 删除用户
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public int deleteUser(String userName){
        return userService.deleteByPrimaryKey(userName);
    }

    /**
     * 锁定用户
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/lockUser")
    public int lockUser(String userName){
        return userService.lockUser(userName);
    }

    /**
     * 解锁用户
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/unlockUser")
    public int unlockUser(String userName){
        return userService.unlockUser(userName);
    }

    /**
     * 重置密码
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetPassWord")
    public int resetPassWord(String userName){
        return userService.resetPassWord(userName);
    }

    @RequestMapping("/toUpdatePSW")
    public String toChangePassword(){
        return "sys/user/updatePassWord";
    }
    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatePassWord")
    public int updatePassWord(SysUser sysUser,@SessionAttribute SysUser userSession){
        int n = 0;
        if(userService.checkOldPassWord(userSession.getUserName(),sysUser.getOldPassWord())){
            n = userService.updatePassWord(sysUser);
        }else{
            //原密码不正确
            n=2;
        }
        return n;
    }

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserByUserName")
    public SysUser getUserByUserName(String userName){
        return userService.getUserByUserName(userName);
    }

    @RequestMapping("/checkExist")
    @ResponseBody
    public String checkExist(String userName){
        SysUser sysUser = userService.getUserByUserName(userName);
        String existFlag = "false";
        if(sysUser==null){
            existFlag ="true";
        }
        return existFlag;
    }
}
