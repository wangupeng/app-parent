package com.cars.shiro;

import com.cars.model.sys.SysResource;
import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysResourceService;
import com.cars.service.sys.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyupeng on 2017/8/18.
 * 自定义realm
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysResourceService sysResourceService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        //根据用户名查询用户信息
        SysUser user = sysUserService.getUserByUserName(username);
        Session session = SecurityUtils.getSubject().getSession();

        if(user==null) {
            throw new UnknownAccountException();
        }
        if ("2".equals(user.getStatus())) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，可自定义
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户
                user.getPassWord(), //密码
                ByteSource.Util.bytes(user.getSalt()),//盐
                getName()  //realm name
        );
        // 当验证都通过后，把用户信息放在session里
        session.setAttribute("userSession", user);
        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userName",user.getUserName());
        //根据用户ID查询权限
        List<SysResource> resourceList = sysResourceService.loadUserResource(map);
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(SysResource resources: resourceList){
            info.addStringPermission(resources.getResourceUrl());
        }
        return info;
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
