package com.yj.oa.framework.shiro;


import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * @author 永健
 *
 * 配置shrio 拦截url
 *
 */
@Component
public class FilterChainDefinitionMapBuider {
    public LinkedHashMap<String, String> BuiderFilterChainDefinitionMap() {
        //LinkedHashMap是有序的
        LinkedHashMap<String, String> ListMap = new LinkedHashMap<>();
         /**  配置受保护页面 就是无需验证权限的页面 以及 需要权限访问的面
         *   1) anon 可以匿名访问 无需登陆验证
         *   2）authc 必须要权限认证才可以访问
         *   3) logout 登出过滤器
         **/

        ListMap.put("/","anon");

        //不拦截静态资源
        ListMap.put("/favicon.ico**", "anon");
        ListMap.put("/ruoyi.png**", "anon");
        ListMap.put("/css/**", "anon");
        ListMap.put("/docs/**", "anon");
        ListMap.put("/fonts/**", "anon");
        ListMap.put("/img/**", "anon");
        ListMap.put("/ajax/**", "anon");
        ListMap.put("/js/**", "anon");
        ListMap.put("/ruoyi/**", "anon");
        ListMap.put("/druid/**", "anon");
        ListMap.put("/captcha/captchaImage**", "anon");



        //登录操作
        ListMap.put("/oa/login","anon");
        //登录页面
        ListMap.put("/oa/toLogin","anon");
        //未授权页面
        ListMap.put("/oa/unauth","anon");
        //验证码
        ListMap.put("/code/getVerify","anon");
        //登出
        ListMap.put("/oa/logout","logout");

        //设置所有资源需要访问权限
        ListMap.put("/**", "sessionExpireFilter,authc");
        return ListMap;
    }


}
