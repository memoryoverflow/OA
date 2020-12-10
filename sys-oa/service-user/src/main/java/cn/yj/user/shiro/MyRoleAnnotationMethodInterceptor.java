package cn.yj.user.shiro;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-06-16 22:07
 */
public class MyRoleAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor
{

    /**
     * @param resolver
     * @since 1.1
     */
    public MyRoleAnnotationMethodInterceptor(AnnotationResolver resolver)
    {
        super(new MyRoleAnnotationHandler(), resolver);
    }

}
