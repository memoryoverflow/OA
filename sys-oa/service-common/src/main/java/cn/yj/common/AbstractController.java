package cn.yj.common;

import cn.yj.annotation.pagehelper.PageBaseController;
import cn.yj.entity.BController;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-12 10:57
 */
public abstract class AbstractController<T> extends BController<T> implements PageBaseController<T>
{
    @Override
    protected <T1> T1 getCurrentUserId()
    {
        return (T1)((LoginUser)SecurityUtils.getSubject().getPrincipal()).getId();
    }

    @Override
    protected <T1> T1 getUserRoles()
    {
        return (T1)((LoginUser)SecurityUtils.getSubject().getPrincipal()).getRoles();
    }

    @Override
    protected <T1> T1 getUserToken()
    {
        return super.getUserToken();
    }

}
