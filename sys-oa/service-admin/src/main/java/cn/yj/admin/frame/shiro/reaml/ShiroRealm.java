package cn.yj.admin.frame.shiro.reaml;

import cn.yj.admin.ConsVal;
import cn.yj.admin.entity.po.User;
import cn.yj.admin.frame.shiro.ShiroCredentialsMatcher;
import cn.yj.admin.service.IPermissionService;
import cn.yj.admin.service.IRoleService;
import cn.yj.admin.service.LoginService;
import cn.yj.common.LoginUser;
import cn.yj.common.Status;
import cn.yj.commons.utils.StringUtils;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-05-21 22:31:42
 */
public class ShiroRealm extends AuthorizingRealm{
    private final static Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    @Lazy
    private LoginService userService;


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
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser principal = (LoginUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo;
        if (!principal.isAdmin()) {

            List<Map<String, Object>> rolesList = principal.getRoles();
            Set<String> code = rolesList.stream().map(role -> (String) role.get("code")).collect(Collectors.toSet());
            authorizationInfo = new SimpleAuthorizationInfo(code.stream().filter(a -> !StringUtils.isBlank(a)).collect(Collectors.toSet()));
            authorizationInfo.setStringPermissions(principal.getPermission().stream().filter(a -> !StringUtils.isBlank(a)).collect(Collectors.toSet()));
        }else {
            authorizationInfo=new SimpleAuthorizationInfo();
            authorizationInfo.addStringPermission("*:*:*");
            authorizationInfo.addRole(ConsVal.SUPER_ADMIN_CODE);
        }
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //加盐 计算盐值 保证每个加密后的 MD5 不一样 登录名+随机字符窜
        UsernamePasswordToken u_pTaken = (UsernamePasswordToken) token;
        String username = u_pTaken.getUsername();
        User user = userService.selectByLoginName(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getStatus().equals(Status.User.禁用.getCode())) {
            throw new UnknownAccountException("此账户不可用");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setId(user.getId());
        loginUser.setName(user.getName());
        loginUser.setLoginName(user.getLoginName());
        loginUser.setToken(UUID.randomUUID().toString());
        loginUser.setEmpCode(user.getEmpCode());
        List<Map<String, Object>> maps = roleService.selectRolesNameCodeIdByUserId(user.getId());
        maps.forEach(map -> map.forEach((key, val) -> {
            if ("code".equals(key) && ConsVal.SUPER_ADMIN_CODE.equals(val)) {
                loginUser.setAdmin(true);
            }
        }));
        loginUser.setRoles(maps);

        // 权限
        loginUser.setPermission(permissionService.selectUserPermissionCodes(user.getId()));
        if (loginUser.isAdmin()){
            Set<String> perms = new HashSet<>();
            perms.add("*:*:*");
            loginUser.setPermission(perms);
        }

        return new SimpleAuthenticationInfo(loginUser, user.getPassword(), null, this.getName());
    }


    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(new ShiroCredentialsMatcher());
    }

}
