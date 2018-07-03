package com.cars.model.sys;

import com.cars.util.page.Page;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Table(name = "demo_sys_user")
public class SysUser extends Page {
    @Id
    private String userName;//主键,登陆账号，唯一
    private String passWord;//密码
    @Transient
    private String oldPassWord;//旧密码
    private String roleCode;//角色ID
    @Transient
    private String roleName;
    private String salt;//加密用的盐
    private String mobile;//手机号
    private String status;//状态,1:正常，2：锁定
    private String realName;//真实姓名
    private String createUser;//创建人
    private String createDate;//创建时间
    private String lastModifiedUser;//上次修改人
    private String lastModifiedDate;//上次修改时间
    @Transient
    private Integer missCount = 0;//密码输入错误次数
    private String rememberMe;//记住我状态

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getMissCount() {
        return missCount;
    }

    public void setMissCount(Integer missCount) {
        this.missCount = missCount;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", oldPassWord='" + oldPassWord + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", salt='" + salt + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                ", realName='" + realName + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastModifiedUser='" + lastModifiedUser + '\'' +
                ", lastModifiedDate='" + lastModifiedDate + '\'' +
                ", missCount=" + missCount +
                ", rememberMe='" + rememberMe + '\'' +
                '}';
    }
}
