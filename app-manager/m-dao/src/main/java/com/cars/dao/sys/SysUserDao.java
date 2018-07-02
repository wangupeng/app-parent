package com.cars.dao.sys;

import com.cars.base.BaseDao;
import com.cars.model.sys.SysUser;

import java.util.List;

public interface SysUserDao extends BaseDao<SysUser> {

    /**
     * 用户列表
     * @return
     */
    List<SysUser> list(SysUser sysUser);

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    /**
     * 锁定用户
     * @param userName
     * @return
     */
    int lockUser(String userName);

    /**
     * 解锁用户
     * @param userName
     * @return
     */
    int unlockUser(String userName);

    /**
     * 重置密码
     * @param sysUser
     * @return
     */
    int resetPassWord(SysUser sysUser);

    /**
     * 获取旧密码的盐
     * @param userName
     * @return
     */
    SysUser getOldPassSalt(String userName);
    /**
     * 修改密码
     * @return
     */
    int updatePassWord(SysUser sysUser);
}