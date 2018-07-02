package com.cars.shiro;

import com.cars.model.sys.SysResource;
import com.cars.service.sys.SysResourceService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangyupeng on 2018/6/25 22:23
 * 自定义ShiroFilterFactoryBean，动态从数据库获取权限
 * 要记住在增删改权限数据库时记得调用setFilterChainDefinitions（）方法重新加载
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
    /**配置中的过滤链*/
    public static String definitions;

    /**权限service*/
    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 从数据库动态读取权限
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {
        ShiroPermissionFactory.definitions = definitions;

        //数据库动态权限
        List<SysResource> resourcList= sysResourceService.listResource(null);

        //拼接所有请求（即url）所需要的权限（认证，权限ID），拼接时必须加入\n用来换行，否则无效。
        for(SysResource sysResource : resourcList){
            //字符串拼接权限
//            definitions = definitions+sysResource.getResourceUrl() + " = "+"authc,perms["+sysResource.getResourceUrl()+"]\n";
            definitions = definitions+sysResource.getResourceUrl() + " = "+"forceLogout,perms["+sysResource.getResourceUrl()+"],kickout\n";
//            definitions = definitions+sysResource.getResourceUrl() + " = "+"perms["+sysResource.getResourceUrl()+"],kickout\n";
        }

//        definitions = definitions+"/** = authc";
        definitions = definitions+"/** = forceLogout,authc,kickout";

//        definitions = definitions+"/** = authc,kickout";

        //从配置文件加载权限配置
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }

        //加入权限集合
        setFilterChainDefinitionMap(section);
    }
}
