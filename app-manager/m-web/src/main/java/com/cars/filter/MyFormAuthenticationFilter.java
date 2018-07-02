package com.cars.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by wangyupeng on 2018/6/29 19:36
 * 用于验证码验证的 Shiro 拦截器在用于身份认证的拦截器之前运行；
 * 但是如果验证码验证拦截器失败了，就不需要进行身份认证拦截器流程了；
 * 所以需要修改下如 FormAuthenticationFilter 身份认证拦截器，当验证码验证失败时不再走身份认证拦截器。
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    /**
     * 重写的 父类方法 issueSuccessFilter  让自己重写的方法起到作用  以防止 登录成功后 不调到 successUrl 的问题
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response,getSuccessUrl(), null, true);
    }
}
