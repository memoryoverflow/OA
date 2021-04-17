package cn.yj.admin.frame.shiro.interceptor;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-06-16 22:08
 */
public class MyRoleAnnotationHandler extends RoleAnnotationHandler
{

    public MyRoleAnnotationHandler()
    {
        super();
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException
    {
        //        String token = ServletUtils.getRequest().getHeader("token");
        //        if (StringUtils.isNotBlank(token))
        //        {
        //            RequiresRoles rrAnnotation = (RequiresRoles) a;
        //            String[] roles = rrAnnotation.value();
        //            if (roles.length == 1)
        //            {
        //                ShiroUtils.checkRole(token, roles[0]);
        //                return;
        //            }
        //
        //            if (Logical.AND.equals(rrAnnotation.logical()))
        //            {
        //                ShiroUtils.checkRoles(token, Arrays.asList(roles), false);
        //                return;
        //            }
        //
        //            if (Logical.OR.equals(rrAnnotation.logical()))
        //            {
        //                if (roles.length != 0)
        //                {
        //                    ShiroUtils.checkRoles(token, Arrays.asList(roles), true);
        //                }
        //                return;
        //            }
        //        }
        super.assertAuthorized(a);
    }
}
