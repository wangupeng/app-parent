package com.cars.core.log;

import com.cars.model.sys.SysLog;
import com.cars.model.sys.SysUser;
import com.cars.service.sys.SysLogService;
import com.cars.util.date.DateUtil;
import com.cars.util.string.IPUtil;
import com.cars.util.string.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangyupeng on 2018/4/3 18:08
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private SysLogService logService;
    @Autowired
    private LogQueue logQueue;

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    /**
     * 定义一个切入点.
     * 解释下：
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com..controller..*.*(..))")
    public void webLog(){}

    /**
     * 登录方法的切入点
     */
    @Pointcut("execution(* com.cars.controller.sys.LoginController.login(..))")
    public void login(){
    }

    /**
     * 添加业务逻辑方法切入点
     */
    @Pointcut("execution(* com.cars.controller.*.save(..))")
    public void insert() {
    }

    /**
     * 修改业务逻辑方法切入点
     */
    @Pointcut("execution(* com.cars.controller.*.update(..))")
    public void update() {
    }

    /**
     * 删除业务逻辑方法切入点
     */
    @Pointcut("execution(* com.cars.controller.*.delete(..))")
    public void delete() {
    }


    @Before(value = "login()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 登录操作(后置通知)
     * @param joinPoint
     * @throws Throwable
     */
    @AfterReturning(value = "login()")
    public void loginLog(JoinPoint joinPoint) throws Throwable {
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userSession");
        if(sysUser!=null){
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            SysLog sysLog = new SysLog();
            sysLog.setLogId(StringUtil.uuid());
            sysLog.setUserName(sysUser.getUserName());
            sysLog.setOperaIp(IPUtil.getIp());
            sysLog.setOperaDate(DateUtil.getSystemTime());
            sysLog.setOperaUrl(request.getRequestURL().toString());
            sysLog.setMethodName(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            sysLog.setDealTime(System.currentTimeMillis() - startTime.get());
            logQueue.add(sysLog);
        }
    }
}
