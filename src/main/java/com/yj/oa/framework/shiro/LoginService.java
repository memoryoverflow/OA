package com.yj.oa.framework.shiro;

import com.yj.oa.common.utils.ServletUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.yj.oa.project.po.VerificationCode.ValidateCodeUtil.RANDOMCODEKEY;

/**
 * @author 永健
 * 登录验证用户的还在那个号密码以及验证码 是否为空
 */
@Component
public class LoginService{
    private final static Logger logger = LoggerFactory.getLogger(LoginService.class);
    /**
     * 验证码 sessinName
     */
    private final static String VALIDATE_CODE="validateCode";

    @Autowired
    IUserService iUserService;

    /**
     *
     * @描述: 登录校验
     *
     * @date: 2018/9/29 21:47
     */
    public void checkLogin(String loginName, String pwd,String validateCode) throws Exception
    {
        HttpServletRequest request = ServletUtils.getRequest();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(RANDOMCODEKEY);
        String inputCode = validateCode.toUpperCase();

        //1.验证码校验
        if (StringUtils.isEmpty(inputCode))
        {
            throw new Exception("验证码为空！");
        }

        if (!inputCode.equals(code.toUpperCase()))
        {
            throw new Exception("验证码错误！");
        }


        //用户名或者密码为空
        if (StringUtils.isEmpty(loginName)||StringUtils.isEmpty(pwd))
        {
            throw new Exception("用户或密码为空！");
        }
    }

}
