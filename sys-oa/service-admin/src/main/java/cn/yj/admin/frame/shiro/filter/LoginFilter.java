package cn.yj.admin.frame.shiro.filter;

import cn.yj.commons.utils.ServletUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.Error;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.subject.Subject;
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
public class LoginFilter extends FormAuthenticationFilter{
    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()){
            log.error("$$$$$$$$ 拦截未登录的请求-=> " + ServletUtils.getRequest().getRequestURI());
            out(R.error(Error.未登陆异常.getCode(), "未登录/会话过期"));
            out(R.error(Error.未登陆异常.getCode(), "未登录/会话过期"));
            return false;
        }
        return true;
    }

    private void out(R r) {
        PrintWriter out = null;
        try {
            HttpServletResponse response = getResponse();
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //设置返回类型
            response.setContentType("application/json");
            out = response.getWriter();
            //输出
            response.getWriter().write(JSON.toJSONString(r));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

}
