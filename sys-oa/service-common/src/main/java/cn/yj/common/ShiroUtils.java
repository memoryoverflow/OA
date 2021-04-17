package cn.yj.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-17 18:25
 */
public class ShiroUtils
{
    public static <T> T getCurrentUser()
    {
        Subject subject;
        try
        {
            subject = SecurityUtils.getSubject();
            if (subject == null)
            {
                return null;
            }

            Object principal = subject.getPrincipal();
            if (principal == null)
            {
                return null;
            }
            return (T) principal;
        } catch (Exception e)
        {
            System.out.println("用户未登陆");
        }

        return null;
    }
}
