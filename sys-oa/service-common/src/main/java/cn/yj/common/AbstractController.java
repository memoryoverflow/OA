package cn.yj.common;

import cn.yj.annotation.pagehelper.PageBaseController;
import cn.yj.entity.BController;
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
        return (T1) getCurrentUser().getId();
    }

    @Override
    protected LoginUser getCurrentUser()
    {
        //        LoginUser loginUser = new LoginUser();
        //        loginUser.setLoginName("admin");
        //        loginUser.setName("admin");
        //        return loginUser;
        return ((LoginUser) SecurityUtils.getSubject().getPrincipal());
    }

    @Override
    protected <T1> T1 getUserRoles()
    {
        return (T1) ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getRoles();
    }

    @Override
    protected <T1> T1 getUserToken()
    {
        return super.getUserToken();
    }

}
