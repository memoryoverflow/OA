package cn.yj.user.shiro.filter;

import cn.yj.commons.utils.ServletUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.Error;
import cn.yj.user.service.IUserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static cn.yj.commons.utils.ServletUtils.getResponse;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
public class LoginFilter extends FormAuthenticationFilter
{
    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    private IUserService userService;

    public LoginFilter(IUserService userService)
    {
        this.userService = userService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
    {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS"))
        {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    private boolean checkUser(String token)
    {

        if (userService.findByToken(token) != null)
        {
            return true;
        }

        return false;
    }

    private boolean checkToken(HttpServletRequest request)
    {
        String token = request.getHeader("token");

        boolean flag = false;
        if (token != null && StringUtils.isNotBlank(token))
        {
            flag = checkUser(token);
        }
        if (!flag)
        {
            token = ServletUtils.getRequest().getParameter("token");
            if (token != null && StringUtils.isNotBlank(token))
            {
                flag = checkUser(token);
            }
        }

        return flag;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {

        if (checkToken(ServletUtils.getRequest())){
            return true;
        }

        // 判断是否是未登录被拦截后的那个登录页面跳转
        if (!this.isLoginRequest(request, response))
        {
            if (log.isTraceEnabled())
            {
                log.info("$$$$$$$ 正在尝试访问需要身份验证的路径。转发到身份验证URL [" + this.getLoginUrl() + "]");
            }
            log.error("$$$$$$$$ 拦截未登录的请求-=> " + ServletUtils.getRequest().getRequestURI());
            out(R.error(Error.未登陆异常.getCode(), "未登录/会话过期"));
            return false;
        }
        return true;
    }

    private void out(R r)
    {
        PrintWriter out = null;
        try
        {
            HttpServletResponse response = getResponse();
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //设置返回类型
            response.setContentType("application/json");
            out = response.getWriter();
            //输出
            response.getWriter().write(JSON.toJSONString(r));
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            out.flush();
            out.close();
        }
    }

}
