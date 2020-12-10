package com.yj.oa.framework.shiro;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Role;
import com.yj.oa.project.po.User;
import com.yj.oa.project.service.user.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author  永健
 * 登录验证reaml
 */
@Component("uReaml")
public class UserRealm extends AuthorizingRealm{
    private final static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    IUserService loginService;


    /**
     *
     * @描述: 登陆认证
     *
     * @params:
     * @return:
     * @date: 2018/9/29 21:35
     */
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException
    {

        //拿到封装好账户密码的token
        UsernamePasswordToken u_pTaken = (UsernamePasswordToken) authenticationToken;
        String loginName = u_pTaken.getUsername();

        //用户校验
        User user = loginService.login(loginName);
        if (user == null)
        {
            throw new UnknownAccountException("用户不存在！");
        }

        String status = user.getStatus().toString();
        if (status.equals(CsEnum.user.USER_USER_BLOCKED.getValue()))
        {
            throw new LockedAccountException("用户被锁定！");
        }


        /**  用户存在密码交给 realm 比对
         * 1).principal: 认证的实体信息，可以使username 也可以是数据表对应的用户实体类对象
         * 2).credentials:  密码
         * 3).realmName: 当前reaml 对象的name. 丢奥用父类的getName() 方法即可
         **/


        /** 数据库的密码是进行过MD5盐值加密的，表单传过来的密码 进行md5加密后对比
         *hashAlgorithmName
         **/


        //加盐 计算盐值 保证每个加密后的 MD5 不一样
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUid());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPwd(), credentialsSalt,
                                                                     this.getName());
        return info;
    }


    /**
     * 授权逻辑
     **/

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        //1.从PrincipalCollection 中获取登陆用户的信息,获取登陆者的id
        User principal = (User) principalCollection.getPrimaryPrincipal();

        //2.利用当前用户的信息用户当前用户的角色或权限
        Set<String> roles = new HashSet<>();
        Set<String> menus = new HashSet<>();

        //从用户中取出权限
        Role role = principal.getRole();

        List<Permission> permissionList = role.getPermissionList();

        if (permissionList != null)
        {
            for (Permission p : permissionList)
            {
                if (p.getCode() != null && !p.getCode().equals(""))
                {
                    menus.add(p.getCode());
                }
            }
        }


        roles.add(role.getRoleName());
        logger.info("### 登录授权，用户=[{}],角色=[{}]", principal.getName(), role.getRoleName());


        //3.创建 SimpleAuthenticationInfo 对象，设置角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(menus);
        return info;

    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo()
    {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
