package cn.yj.user.service.impl;

import cn.yj.common.LoginUser;
import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.CheckUtils;
import cn.yj.commons.utils.ServletUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.commons.utils.ValidateCodeUtil;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.params.check.Require;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.LoginModel;
import cn.yj.user.entity.po.User;
import cn.yj.user.mapper.UserMapper;
import cn.yj.user.mapper.UserRoleMapper;
import cn.yj.user.service.IUserService;
import cn.yj.user.shiro.MD5;
import cn.yj.user.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService
{

    private final UserRoleMapper userRoleMapper;

    private static final String DEFAULT_PASSWORD = "123466";

    @Autowired
    public UserServiceImpl(UserRoleMapper userRoleMapper)
    {
        this.userRoleMapper = userRoleMapper;
    }


    @Override
    public LoginUser login(@Require LoginModel loginModel)
    {
        // checkCode(loginModel.getCode());

        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(new UsernamePasswordToken(loginModel.getLoginName(), loginModel.getPassword()));
        } catch (IncorrectCredentialsException e)
        {
            throw new ServiceException("密码错误！");
        } catch (UnknownAccountException e)
        {
            // 用户不存在
            throw new ServiceException(e.getMessage());
        } catch (LockedAccountException e)
        {
            // 用户锁定
            throw new ServiceException(e.getMessage());
        } catch (AuthenticationException e)
        {
            throw new ServiceException("登陆失败！");
        }

        LoginUser principal = (LoginUser) subject.getPrincipal();
        baseMapper.updateById(new User(principal.getId(), principal.getToken()));
        ShiroUtils.setUser(principal);
        return principal;
    }

    @Override
    public User selectByLoginName(String loginName)
    {
        return baseMapper.selectByLoginName(loginName);
    }

    private void checkCode(String code)
    {
        Object attribute = ServletUtils.getSession().getAttribute(ValidateCodeUtil.RANDOMCODEKEY);
        if (attribute == null)
        {
            throw new ServiceException("验证码过期/验证码不存在");
        }
        if (!attribute.toString().toUpperCase().equals(code.toUpperCase()))
        {
            throw new ServiceException("验证码错误");
        }
    }


    @CheckObjectValue(keyValue = {@KeyValue(type = User.class, name = {"loginName", "name", "deptId", "positionId", "roleIds"})})
    @Override
    public boolean insert(User entity)
    {
        String loginName = entity.getLoginName();
        if (StringUtils.isNotNull(baseMapper.selectByLoginName(loginName)))
        {
            throw new ServiceException("该登陆名已经存在");
        }

        if (!CheckUtils.isEmail(entity.getEmail()))
        {
            throw new ServiceException("邮箱格式不正确");
        }

        String userId = UUIdUtils.getUUId32();
        entity.setPassword(MD5.getInstance().getMD5(loginName.concat(DEFAULT_PASSWORD)));
        entity.setId(userId);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        super.insert(entity);

        String[] roleIds = entity.getRoleIds();

        for (int i = 0; i < roleIds.length; i++)
        {
            userRoleMapper.insert(roleIds[i], entity.getId());
        }

        return true;
    }


    @CheckObjectValue(keyValue = {@KeyValue(type = User.class, name = {"id", "loginName", "name", "deptId", "positionId", "roleIds"})})
    @Override
    public boolean updateById(User entity)
    {
        // 登录名验证
        String loginName = entity.getLoginName();
        User user = baseMapper.selectByLoginName(loginName);
        if (StringUtils.isNotNull(user) && !user.getId().equals(entity.getId()))
        {
            throw new ServiceException("该登陆名已经存在");
        }
        entity.setUpdateTime(new Date());
        baseMapper.updateById(entity);

        // 角色更新
        String[] roleIds = entity.getRoleIds();
        userRoleMapper.deleteByUserId(entity.getId());
        for (int i = 0; i < roleIds.length; i++)
        {
            userRoleMapper.insert(roleIds[i], entity.getId());
        }

        return true;
    }

    @Override
    public User findByToken(String token)
    {
        return baseMapper.selectByToken(token);
    }

    @Override
    public boolean removeByIds(Serializable[] ids)
    {
        for (int i = 0; i < ids.length; i++)
        {
            userRoleMapper.deleteByUserId(ids[i]);
        }
        return super.removeByIds(ids);
    }

    @Override
    public boolean reloadPassword(@Require String userId, @Require String newPassword)
    {
        User user = baseMapper.selectById(userId);
        if (StringUtils.isNull(user))
        {
            throw new ServiceException("该账户不存在");
        }
        return baseMapper.updateById(new User(userId).setPassword(MD5.getInstance().getMD5(user.getLoginName().concat(newPassword)))) > 0;
    }

    @CheckObjectValue(keyValue = {@KeyValue(type = User.class, name = {"id"})})
    @Override
    public boolean updateUserInfoById(User user)
    {
        return baseMapper.updateById(user) > 0;
    }

    @Override
    public List<Map<String, String>> listIdName()
    {
        return baseMapper.listIdName();
    }
}
