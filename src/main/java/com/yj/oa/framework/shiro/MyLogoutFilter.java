package com.yj.oa.framework.shiro;

import com.yj.oa.common.constant.Constants;
import com.yj.oa.common.utils.HttpHeaderUtil;
import com.yj.oa.common.utils.ServletUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.ShiroUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.aspect.SystemLogAspect;
import com.yj.oa.framework.aspect.enums.OperLogStatusEnum;
import com.yj.oa.framework.aspect.enums.OperLogTypeEnum;
import com.yj.oa.project.po.OperLog;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.operlog.IOperLogService;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @描述: 退出过滤器
 *
 * @date: 2018/9/30 7:56
 */
public class MyLogoutFilter extends LogoutFilter{
    private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);


    /**
     * 退出后重定向的地址
     */
    private String loginUrl;

    public String getLoginUrl()
    {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl)
    {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        try
        {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            try
            {
                User user = (User) subject.getPrincipal();
                if (user!=null)
                {
                }
                // 退出登录
                subject.logout();
                //清除session
                ServletUtils.getSession().invalidate();
            }
            catch (SessionException ise)
            {
                log.error("logout fail.", ise);
            }
            WebUtils.issueRedirect(request, response, redirectUrl);
        }
        catch (Exception e)
        {
            log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
        }

        return false;
    }

    /**
     * 退出跳转URL
     */
    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject)
    {
        String url = getLoginUrl();
        if (StringUtils.isNotEmpty(url))
        {
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
