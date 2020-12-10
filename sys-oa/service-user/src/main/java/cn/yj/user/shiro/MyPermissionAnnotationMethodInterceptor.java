package cn.yj.user.shiro;

import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

/**
 * <br>
 * 自定义权限拦截器
 *
 * @author 永健
 * @since 2020-11-29 00:46
 */
public class MyPermissionAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor
{
    public MyPermissionAnnotationMethodInterceptor()
    {
        super(new MyPermissionAnnotationHandler());
    }

}
