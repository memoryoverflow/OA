package cn.yj.admin.verificationCode;

import cn.yj.tools.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 自定义一些key
 * </p>
 *
 * @author 永健
 * @since 2019-09-10 21:28
 */
abstract class VerificationCodeSaveType
{
    protected static void save(String code, HttpServletRequest request)
    {
        throw new ServiceException("请实现 save()");
    }

    protected static String get(HttpSession session)
    {
        throw new ServiceException("请实现 get()");
    }
}

