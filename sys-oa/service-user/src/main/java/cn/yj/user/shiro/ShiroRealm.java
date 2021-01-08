package cn.yj.user.shiro;

import cn.yj.common.LoginUser;
import cn.yj.common.Status;
import cn.yj.user.entity.po.User;
import cn.yj.user.service.IPermissionService;
import cn.yj.user.service.IRoleService;
import cn.yj.user.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
@Component
public class ShiroRealm extends AuthorizingRealm
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    @Lazy
    private IUserService userService;


    @Autowired
    @Lazy
    private IRoleService roleService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;


    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        LoginUser principal = (LoginUser) principals.getPrimaryPrincipal();
        List<Map<String, Object>> rolesList = principal.getRoles();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(rolesList.stream().map(role -> (String) role.get("code")).collect(Collectors.toSet()));
        authorizationInfo.setStringPermissions(principal.getPermission());
        return authorizationInfo;
    }

    /**
     * 登陆
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        //加盐 计算盐值 保证每个加密后的 MD5 不一样 登录名+随机字符窜
        UsernamePasswordToken u_pTaken = (UsernamePasswordToken) token;
        String username = u_pTaken.getUsername();
        User user = userService.selectByLoginName(username);
        if (user == null)
        {
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getStatus().equals(Status.User.禁用.getCode()))
        {
            throw new UnknownAccountException("此账户不可用");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setName(user.getName());
        loginUser.setLoginName(user.getLoginName());
        loginUser.setToken(UUID.randomUUID().toString());
        loginUser.setRoles(roleService.selectRolesNameCodeIdByUserId(user.getId()));

        // 权限
        loginUser.setPermission(permissionService.selectUserPermissionCodes(user.getId()));

        return new SimpleAuthenticationInfo(loginUser, user.getPassword(), null, this.getName());
    }


    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher)
    {
        super.setCredentialsMatcher(new ShiroCredentialsMatcher());
    }
}
