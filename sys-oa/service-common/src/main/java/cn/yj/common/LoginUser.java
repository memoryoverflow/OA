package cn.yj.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-05-26 10:47
 */
public class LoginUser implements Serializable
{
    private String id;

    private String loginName;

    private String name;

    private String empCode;

    private String token;

    private boolean admin;

    private List<Map<String, Object>> roles;

    private Set<String> permissions;

    public String getId()
    {
        return id;
    }

    public LoginUser setId(String id)
    {
        this.id = id;
        return this;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public LoginUser setLoginName(String loginName)
    {
        this.loginName = loginName;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public LoginUser setName(String name)
    {
        this.name = name;
        return this;
    }

    public List<Map<String, Object>> getRoles()
    {
        return roles;
    }

    public LoginUser setRoles(List<Map<String, Object>> roles)
    {
        this.roles = roles;
        return this;
    }

    public String getToken()
    {
        return token;
    }

    public LoginUser setToken(String token)
    {
        this.token = token;
        return this;
    }

    public Set<String> getPermission()
    {
        return permissions;
    }

    public LoginUser setPermission(Set<String> permissions)
    {
        this.permissions = permissions;
        return this;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public LoginUser setEmpCode(String empCode)
    {
        this.empCode = empCode;
        return this;
    }

    public Set<String> getPermissions()
    {
        return permissions;
    }

    public LoginUser setPermissions(Set<String> permissions)
    {
        this.permissions = permissions;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public LoginUser setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}
