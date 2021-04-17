package cn.yj.admin.frame.shiro.interceptor;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.PermissionAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 00:50
 */
public class MyPermissionAnnotationHandler extends PermissionAnnotationHandler
{
    public MyPermissionAnnotationHandler()
    {
        super();
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException
    {
//        String token = ServletUtils.getRequest().getHeader("token");
//
//        if (token != null && StringUtils.isNotBlank(token))
//        {
//            RequiresPermissions rpAnnotation = (RequiresPermissions) a;
//            String[] perms = getAnnotationValue(a);
//
//            if (perms.length == 1)
//            {
//                ShiroUtils.checkPermission(token, perms[0]);
//                return;
//            }
//            if (Logical.AND.equals(rpAnnotation.logical()))
//            {
//                ShiroUtils.checkPermissions(token, Arrays.asList(perms), false);
//                return;
//            }
//
//            if (Logical.OR.equals(rpAnnotation.logical()))
//            {
//                ShiroUtils.checkPermissions(token, Arrays.asList(perms), true);
//                return;
//            }
//        }
        super.assertAuthorized(a);
    }
}
