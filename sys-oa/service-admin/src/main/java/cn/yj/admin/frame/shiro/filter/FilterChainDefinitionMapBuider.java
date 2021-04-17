package cn.yj.admin.frame.shiro.filter;


import java.util.LinkedHashMap;

/**
 * <br>
 * 配置shrio 拦截url
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
public class FilterChainDefinitionMapBuider
{
    public static LinkedHashMap<String, String> buiderFilterChainDefinitionMap()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        /**  配置受保护页面 就是无需验证权限的页面 以及 需要权限访问的面
         *   1) anon 可以匿名访问 无需登陆验证
         *   2）authc 必须要权限认证才可以访问
         *   3) logout 登出过滤器
         */

        //注册
        map.put("/getVerify", "anon");

        map.put("/login", "anon");
        map.put("/favicon.ico", "anon");


        map.put("/**", "authc");
        map.put("/**", "loginFilter");
        return map;
    }
}
