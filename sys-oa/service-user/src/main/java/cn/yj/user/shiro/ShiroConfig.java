package cn.yj.user.shiro;

import cn.yj.user.service.IUserService;
import cn.yj.user.shiro.filter.FilterChainDefinitionMapBuider;
import cn.yj.user.shiro.filter.LoginFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * shiro  配置类
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
@Configuration
public class ShiroConfig
{

    @Bean("securityManager")
    public SecurityManager securityManager(ShiroRealm shiroRealm,DefaultWebSessionManager defaultWebSessionManager)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(defaultWebSessionManager);
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroRealm shiroRealm, IUserService userService,DefaultWebSessionManager defaultWebSessionManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager(shiroRealm,defaultWebSessionManager));


        // 注意 这个自定义filter 不要放到Spring管理 否则会在在拦截连中拦截所有， login anon 配置了不起作用的
        Map<String, Filter> filterHashMap = new HashMap<>(1);
        filterHashMap.put("loginFilter", new LoginFilter(userService));
        shiroFilterFactoryBean.setFilters(filterHashMap);

        // 拦截URL
        shiroFilterFactoryBean.setFilterChainDefinitionMap(FilterChainDefinitionMapBuider.BuiderFilterChainDefinitionMap());

        return shiroFilterFactoryBean;
    }


    @Bean("sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager(RedisCacheManager redisCacheManager,RedisSessionDAO redisSessionDAO)
    {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO);
        defaultWebSessionManager.setCacheManager(redisCacheManager);
        return defaultWebSessionManager;
    }


    /**
     * 会话id
     */
    @Bean
    public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator()
    {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 启用权限注解
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator()
    {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 权限注解处理器
     *
     * @param securityManager
     * @return
     */
    //@Bean
    public StaticMethodMatcherPointcutAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager)
    {
        MyAuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new MyAuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    // @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor()
    {
        return new LifecycleBeanPostProcessor();
    }

}
