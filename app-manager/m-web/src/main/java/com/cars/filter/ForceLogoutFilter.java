package com.cars.filter;

import com.cars.util.global.GlobalConst;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by wangyupeng on 2018/6/27 16:29
 * 强制退出拦截器
 * 如果用户会话中存在 Constants.SESSION_FORCE_LOGOUT_KEY 属性，表示被管理员强制退出了；然后调用 Subject.logout() 退出，且重定向到登录页面
 */
public class ForceLogoutFilter extends AccessControlFilter {

    /**
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Session session = getSubject(request, response).getSession(false);
        if(session == null) {
            return true;
        }
        return session.getAttribute(GlobalConst.SESSION_FORCE_LOGOUT_KEY) == null;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
//            Session session = subject.getSession();
//            Serializable sessionId = session.getId();
//            session.removeAttribute(sessionId);
            subject.logout();//强制退出

        } catch (Exception e) {
            e.printStackTrace();
        }
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(request, response, loginUrl);
        return false;
    }
}
