package cn.yj.admin.service;

import cn.yj.admin.LoginModel;
import cn.yj.admin.entity.po.User;
import cn.yj.admin.mapper.UserMapper;
import cn.yj.admin.verificationCode.ValidateCodeUtil;
import cn.yj.common.LoginUser;
import cn.yj.commons.utils.ServletUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-04-02 18:49
 */
@Component
public class LoginService{


    @Resource
    UserMapper baseMapper;

    public User selectByLoginName(String loginName) {
        return baseMapper.selectByLoginName(loginName);
    }

    public LoginUser login(@Require LoginModel loginModel) {
        checkCode(loginModel.getCode());

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(loginModel.getLoginName(), loginModel.getPassword()));
        } catch (IncorrectCredentialsException e) {
            throw new ServiceException("密码错误！");
        } catch (UnknownAccountException e) {
            // 用户不存在
            throw new ServiceException(e.getMessage());
        } catch (LockedAccountException e) {
            // 用户锁定
            throw new ServiceException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new ServiceException("登陆失败！");
        }

        LoginUser principal = (LoginUser) subject.getPrincipal();
        baseMapper.updateById(new User(principal.getId(), principal.getToken()));
        return principal;
    }

    private void checkCode(String code) {
        String exitsCode = ValidateCodeUtil.getInstance().getExitsCode(ServletUtils.getRequest());
        if (StringUtils.isBlank(exitsCode)) {
            throw new ServiceException("验证码过期,请重新获取");
        }

        if (!exitsCode.equalsIgnoreCase(code)) {
            throw new ServiceException("验证码错误");
        }
    }

}
