package com.cars.controller.sys;

import com.cars.common.exception.MyException;
import com.cars.model.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Controller
public class LoginController {

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String toLogin(HttpServletRequest request, Model model){
        if(request.getParameter("forceLogout") != null) {
            model.addAttribute("msg", "您已经被管理员强制退出，请重新登录");
        }
        return "login";
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(HttpServletRequest request, SysUser user, BindingResult result){
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassWord())) {
            request.setAttribute("user", user);
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(),user.getPassWord());
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        try {
            if("jCaptcha.error".equals(exceptionClassName)){
                request.setAttribute("msg", "验证码错误！");
                token.clear();
                return "login";
            }
            subject.login(token);
            user.setMissCount(0);
            return "redirect:index";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("user", user);
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("user", user);
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    @RequestMapping(value={"/index","/",""} )
    public String index(){
        return "index";
    }

    @RequestMapping(value="/iconfont")
    public String iconfont(){
        return "common/iconfont";
    }
    @RequestMapping(value="/500")
    public String error(){
        return "common/500";
    }
    @RequestMapping(value="/403")
    public String unauthorizedUrl(){
        return "common/403";
    }
}
