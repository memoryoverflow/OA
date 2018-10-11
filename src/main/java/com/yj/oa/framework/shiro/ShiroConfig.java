package com.yj.oa.framework.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{


    /**
     * 登录url
     */
    private static final String loginUrl = "/oa/toLogin";
    private static final String UNAUTH = "/oa/unauth";

    //没有权限的页面


    /* *  配置 安全管理器 securityManager
     *  securityManager需要realm
     *  将其注入
     **/

    @Bean("SecurityManager")
    public SecurityManager securityManager(@Qualifier("userRealm") Realm realm,
                                                               @Qualifier("SessionManager") DefaultSessionManager sessionManager,
                                                               CookieRememberMeManager cookieRememberMeManager
    )
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(cookieRememberMeManager);
        //securityManager.setCacheManager(this.cacheManager());
        return securityManager;
    }


    /**
     * 配置 ShiroFilterFactoryBean
     * <p>
     * ShiroFilterFactoryBean需要 securityManager 将其注入
     **/

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Qualifier("SecurityManager") SecurityManager securityManager,
            FilterChainDefinitionMapBuider filterChainDefinitionMapBuider
    )
    {
        //实例化 ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //没有登陆会被拦截 直接跳转到登录页面
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl(UNAUTH);

        // 自定义登出filter
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        MyLogoutFilter myLogoutFilter = new MyLogoutFilter();
        myLogoutFilter.setLoginUrl(loginUrl);
        filters.put("sessionExpireFilter",new MySessionExpiredFilter());
        filters.put("logout", myLogoutFilter);
        shiroFilterFactoryBean.setFilters(filters);

        //设置访问路劲需要和不需要的 请求 url
        shiroFilterFactoryBean.setFilterChainDefinitionMap(
                filterChainDefinitionMapBuider.BuiderFilterChainDefinitionMap());

        return shiroFilterFactoryBean;
    }


    /**
     * 设置加密方式以及加密次数
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher()
    {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        //设置加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        return hashedCredentialsMatcher;
    }

    /**
     * 将自定义的realm 注入到当前的 realm 中
     * 将HashedCredentialsMatcher 加密方式 构造注入 realm方法 中
     */
    @Bean("userRealm")
    public Realm realm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher,
                       @Qualifier("uReaml") UserRealm realm)
    {
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return realm;
    }


    /**
     * session会话
     */
    @Bean("SessionManager")
    public DefaultWebSessionManager defaultWebSessionManager()
    {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();

        //defaultWebSessionManager.setSessionDAO(abstractSessionDAO);

        // 没5秒检查一次session是否过期
        //defaultWebSessionManager.setSessionValidationInterval(1000);

        //设置session超时时间。一旦超时将其删除
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);

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
     * 缓存管理器
     */

//    public EhCacheManager cacheManager()
//    {
//        EhCacheManager ehCacheManager = new EhCacheManager();
//        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return ehCacheManager;
//    }

    /**
     * 记住我
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager()
    {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie());
        //rememberMe cookie加密的密钥默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    public SimpleCookie cookie()
    {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //单位秒 设置7天
        simpleCookie.setMaxAge(604800);
        return simpleCookie;
    }


    /**
     * shiroWeb
     */
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }


    /**
     * 启用权限注解
     *
     * @return
     */

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator()
    {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("SecurityManager") SecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
