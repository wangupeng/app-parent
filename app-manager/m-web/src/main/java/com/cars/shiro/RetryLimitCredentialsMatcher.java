package com.cars.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangyupeng on 2018/7/1 18:43
 * 登录失败多次，抛出异常
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    //集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    private Cache<String, AtomicInteger> passwordRetryCache;
    @Autowired
    HttpServletRequest request;

    public RetryLimitCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        HttpSession session = request.getSession();
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry data
            passwordRetryCache.remove(username);
            session.setAttribute("yzm",false);
        }else{
            if(retryCount.incrementAndGet() >= 3) {
                session.setAttribute("yzm",true);
    //            logger.warn("username: " + username + " tried to login more than 5 times in period");
                throw new ExcessiveAttemptsException("username: " + username + " tried to login more than 5 times in period"
                );
            }
        }

        return matches;
    }
}
