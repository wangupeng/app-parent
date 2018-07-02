package com.cars.core.taglib;

import com.cars.model.sys.SysUser;
import com.cars.util.global.GlobalConst;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 * Created by wangyupeng on 2018/6/27 18:10
 */
public class Functions {
    /**
     * 获取用户身份
     * @param session
     * @return
     */
//    public static String principal(Session session) {
//        String userName = "";
//        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//        if(principalCollection!=null){
//            SysUser sysUser = (SysUser)principalCollection.getPrimaryPrincipal();
//            if(sysUser != null){
//                userName = sysUser.getUserName();
//            }
//        }
//        return userName;
//    }

    public static String principal(Session session) {
        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SysUser sysUser = (SysUser)principalCollection.getPrimaryPrincipal();
        return sysUser.getUserName();
    }
    /**
     * 判断是否强制退出
     * @param session
     * @return
     */
    public static boolean isForceLogout(Session session) {
        return session.getAttribute(GlobalConst.SESSION_FORCE_LOGOUT_KEY) != null;
    }


}
