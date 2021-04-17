package cn.yj.admin.frame.shiro.interceptor;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 17:03
 */
public class MyAuthorizationAttributeSourceAdvisor extends AuthorizationAttributeSourceAdvisor
{
    public MyAuthorizationAttributeSourceAdvisor()
    {
        setAdvice(new MyAopAllianceAnnotationsAuthorizingMethodInterceptor());
    }
}
