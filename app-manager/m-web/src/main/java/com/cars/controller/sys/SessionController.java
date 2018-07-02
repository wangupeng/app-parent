package com.cars.controller.sys;

import com.cars.model.sys.SysUser;
import com.cars.util.global.GlobalConst;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wangyupeng on 2018/6/27 16:19
 */
@Controller
@RequestMapping("/sys/sessions")
public class SessionController {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 提供了展示所有在线会话列表，通过 sessionDAO.getActiveSessions() 获取所有在线的会话。
     * 此处展示会话列表的缺点是：sessionDAO.getActiveSessions() 提供了获取所有活跃会话集合，如果做一般企业级应用问题不大，因为在线用户不多；但是如果应用的在线用户非常多，此种方法就不适合了，解决方案就是分页获取：
     * Page<Session> getActiveSessions(int pageNumber, int pageSize);
     * Page 对象除了包含 pageNumber、pageSize 属性之外，还包含 totalSessions（总会话数）、Collection （当前页的会话）。
     * 分页获取时，如果是 MySQL 这种关系数据库存储会话比较好办，
     * 如果使用 Redis 这种数据库可以考虑这样存储：
     *  session.id=会话序列化数据
     *  session.ids=会话id Set列表（接着可以使用LLEN获取长度，LRANGE分页获取）
     * 会话创建时（如 sessionId=123），那么 redis 命令如下所示：
     *  SET session.123 "Session序列化数据"
     *  LPUSH session.ids 123
     * 会话删除时（如 sessionId=123），那么 redis 命令如下所示：
     *  DEL session.123
     *  LREM session.ids 123
     * 获取总活跃会话：
     *  LLEN session.ids
     * 分页获取活跃会话：
     *  LRANGE key 0 10 #获取到会话ID
     *  MGET session.1 session.2……  #根据第一条命令获取的会话ID获取会话数据
     * @param model
     * @return
     */
    @RequestMapping()
    public String list(Model model) {
//        Page<Session> getActiveSessions();
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        List<Session> list = new ArrayList<Session>();
        for(Session session:sessions){
            PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(principalCollection!=null){
                list.add(session);
            }
        }

        model.addAttribute("sessions", list);
        model.addAttribute("sesessionCount", list.size());

        return "sys/session/list";
    }

    /**
     * 强制退出某一个会话，此处只在指定会话中设置 GlobalConst.SESSION_FORCE_LOGOUT_KEY 属性，之后通过 ForceLogoutFilter 判断并进行强制退出。
     * @param sessionId
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/{sessionId}/forceLogout")
    public String forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
        try {
            Session session = sessionDAO.readSession(sessionId);
            if(session != null) {
                session.setAttribute(GlobalConst.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
        } catch (Exception e) {/*ignore*/}
//        redirectAttributes.addFlashAttribute("msg", "强制退出成功！");
        return "redirect:/sys/sessions";
    }
}
