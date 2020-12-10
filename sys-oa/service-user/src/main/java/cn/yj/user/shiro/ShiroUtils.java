package cn.yj.user.shiro;

import cn.yj.common.JedisUtils;
import cn.yj.tools.exception.Error;
import cn.yj.tools.exception.ServiceException;
import cn.yj.common.LoginUser;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 00:17
 */
public class ShiroUtils
{
    private static final ConcurrentHashMap<String, Set<String>> ROLES_MAP = new ConcurrentHashMap<String, Set<String>>();
    private static final ConcurrentHashMap<String, Set<String>> PERMISSIONS = new ConcurrentHashMap<>();

    private static final String ROLE_KEY = "roles_key_";
    private static final String PERM_KEY = "perm_key_";

    public static void setUser(LoginUser user)
    {
        //ROLES_MAP.put(user.getToken(), user.getRoles().stream().map(role -> (String) role.get("code")).collect(Collectors.toSet()));
        //PERMISSIONS.put(user.getToken(), user.getPermission());
        JedisUtils.setObject(ROLE_KEY.concat(user.getToken()), user.getRoles().stream().map(role -> (String) role.get("code")).collect(Collectors.toSet()));
        JedisUtils.setObject(PERM_KEY.concat(user.getToken()), user.getPermission());
    }

    public static void checkRole(String token, String role)
    {
        Set<String> roles = JedisUtils.getObject(ROLE_KEY.concat(token), Set.class);//ROLES_MAP.get(token);
        if (roles == null || !roles.contains(role))
        {
            throw new ServiceException(Error.权限异常, "没有角色权限");
        }
    }

    public static void checkRoles(String token, Collection<String> roles, boolean or)
    {
        Set<String> rolesCache = JedisUtils.getObject(ROLE_KEY.concat(token), Set.class);
        boolean flag = false;
        if (roles != null && !roles.isEmpty())
        {
            for (String roleName : roles)
            {
                if (!or)
                {
                    flag = true;
                    checkRole(token, roleName);
                }
                else
                {
                    if (rolesCache.contains(roleName))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag)
            {
                throw new ServiceException(Error.权限异常, "没有角色权限");
            }
        }
    }

    public static void checkPermission(String token, String perm)
    {
        Set<String> perms = JedisUtils.getObject(PERM_KEY.concat(token), Set.class);
        if (perms == null || !perms.contains(perm))
        {
            throw new ServiceException(Error.权限异常);
        }
    }

    public static void checkPermissions(String token, Collection<String> perms, boolean or)
    {
        boolean flag = false;
        if (perms != null && !perms.isEmpty())
        {
            for (String perm : perms)
            {
                if (!or)
                {
                    flag = true;
                    checkPermission(token, perm);
                }
                else
                {
                    Set<String> permsCache = JedisUtils.getObject(ROLE_KEY.concat(token), Set.class);
                    if (permsCache.contains(perm))
                    {
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag)
            {
                throw new ServiceException(Error.权限异常, "没有角色权限");
            }
        }
    }
}
