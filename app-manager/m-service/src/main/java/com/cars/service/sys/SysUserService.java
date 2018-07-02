package com.cars.service.sys;

import com.cars.dao.sys.SysUserDao;
import com.cars.model.sys.SysUser;
import com.cars.service.base.BaseService;
import com.cars.service.password.PasswordHelper;
import com.cars.util.date.DateUtil;
import com.cars.util.global.GlobalConst;
import com.cars.util.string.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Service
public class SysUserService extends BaseService<SysUser> {
    @Autowired
    private SysUserDao userDao;

    /**
     * 用户列表
     * @param sysUser
     * @return
     */
    public List<SysUser> listUser(SysUser sysUser){
        return userDao.list(sysUser);
    }

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @Transactional
    public int insert(SysUser sysUser){
        //密码加密，及存储盐值
        sysUser.setPassWord(GlobalConst.PASS_WORD);//设置默认密码
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(sysUser);

        //获取当前登录用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        sysUser.setLastModifiedUser(user.getUserName());
        sysUser.setLastModifiedDate(DateUtil.getSystemTime());
        sysUser.setCreateUser(user.getUserName());
        sysUser.setCreateDate(DateUtil.getSystemTime());
        sysUser.setStatus("1");

        return userDao.insert(sysUser);
    }

    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    @Transactional
    public int update(SysUser sysUser){
        //获取当前登录用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        sysUser.setLastModifiedUser(user.getUserName());
        sysUser.setLastModifiedDate(DateUtil.getSystemTime());
        return userDao.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 锁定用户
     * @param userName
     * @return
     */
    @Transactional
    public int lockUser(String userName){
        return userDao.lockUser(userName);
    }

    /**
     * 解锁用户
     * @param userName
     * @return
     */
    @Transactional
    public int unlockUser(String userName){
        return userDao.unlockUser(userName);
    }

    /**
     * 重置密码
     * @param userName
     * @return
     */
    @Transactional
    public int resetPassWord(String userName){
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        //密码加密，及存储盐值
        sysUser.setPassWord(GlobalConst.PASS_WORD);//设置默认密码
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(sysUser);
        return userDao.resetPassWord(sysUser);
    }


    /**
     * 判断页面获取的旧密码是否与数据库存储密码相同
     * @param userName
     * @param oldPassWord
     * @return
     */
    public boolean checkOldPassWord(String userName,String oldPassWord){
        boolean flag = false;
        if(StringUtil.isNotNullOrEmpty(oldPassWord)){
            //获取数据库存储的密码和盐
            SysUser sysUser = userDao.getOldPassSalt(userName);
            //对页面获取的旧密码用数据库查出的盐加密，与数据库查出的密码比较
            PasswordHelper passwordHelper = new PasswordHelper();
            String pOldPassWord = passwordHelper.encryptPasswordWithSalt(oldPassWord,sysUser.getSalt());

            if(pOldPassWord.equals(sysUser.getPassWord())){
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @Transactional
    public int updatePassWord(SysUser sysUser){
        //获取当前登录用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        //密码加密，及存储盐值
        sysUser.setUserName(user.getUserName());
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(sysUser);

        int n = userDao.updatePassWord(sysUser);
        return n;
    }

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    public SysUser getUserByUserName(String userName){
        SysUser sysUser = userDao.getUserByUserName(userName);
        return sysUser;
    }
}
