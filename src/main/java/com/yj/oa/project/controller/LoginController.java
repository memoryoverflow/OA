package com.yj.oa.project.controller;


import com.yj.oa.common.constant.Constants;
import com.yj.oa.common.utils.ServletUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.ShiroUtils;
import com.yj.oa.framework.annotation.Operlog;
import com.yj.oa.framework.shiro.LoginService;
import com.yj.oa.framework.web.controller.BaseController;
import com.yj.oa.framework.web.po.AjaxResult;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/oa")
public class LoginController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private String prefix = "system/user/";

    @Autowired
    LoginService loginService;


    @Autowired
    IUserService userService;



    /**
     *
     * @描述: 执行登录操作
     *
     * @params: user:用户登录信息；
     *          validateCode：验证码
     * @return:
     * @date: 2018/9/29 21:20
     */
    @RequestMapping("/login")
    @Operlog(descr = "用户登录", modal = "登录模块")
    @ResponseBody
    public AjaxResult Logining(User user, String validateCode, Boolean rememberMe, HttpServletRequest request)
    {
        HttpSession session = ServletUtils.getSession();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPwd());
        token.setRememberMe(rememberMe);
        Subject subject = SecurityUtils.getSubject();

        //验证用户名和密码 验证码的问题
        try
        {
            loginService.checkLogin(user.getName(), user.getPwd(), validateCode);
        }
        catch (Exception e)
        {
            session.setAttribute(Constants.LOGIN_ERROR, e.getMessage());
            return error(e.getMessage());
        }

        try
        {
            if (!subject.isAuthenticated())
            {
                subject.login(token);
            }

        }
        catch (IncorrectCredentialsException e)
        {
            session.setAttribute(Constants.LOGIN_ERROR,"密码错误");
            return error("密码错误！");
        }
        catch (UnknownAccountException e)
        {
            session.setAttribute(Constants.LOGIN_ERROR,e.getMessage());
            return error(e.getMessage());
        }
        catch (LockedAccountException e)
        {
            session.setAttribute("login",e.getMessage());
            return error(e.getMessage());
        }
        catch (AuthenticationException e)
        {
//            String msg = "用户名或密码错误！";
//            if (!StringUtils.isEmpty(e.getMessage()))
//            {
//                msg = e.getMessage();
//            }
            session.setAttribute(Constants.LOGIN_ERROR,e.getMessage());
            return error("系统异常！");
        }

        return success();
    }


    /**
 s sl
       *
     * @描述: 登录页面
     *
     * @params:
     * @return:
     * @date: 2018/9/29 21:20
     */
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "login";
    }


}
