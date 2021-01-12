package cn.yj.user.entity.po;

import cn.yj.common.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-28 21:23
 */
public class User extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;


    public User()
    {
    }

    public User(String id)
    {
        this.setId(id);
    }

    public User(String id, String token)
    {
        super.setId(id);
        this.token = token;
    }

    /**
     * 删除：1：已删除
     * 0： 未删除
     */
    private Integer deleted;


    @NotBlank(message = "用户名不允许为空")
    private String name;


    private String token;

    @NotBlank(message = "编号不能为空")
    private String empCode;


    @NotBlank(message = "登陆名不允许为空")
    private String loginName;

    private String password;


    @NotBlank(message = "电话不允许为空")
    private String phone;


    @NotBlank(message = "邮箱不允许为空")
    private String email;


    @NotNull(message = "请选择角色")
    private String[] roleIds;

    /**
     * 用户状态
     */
    private Integer status;

    private String deptId;

    private String positionId;


    public Integer getDeleted()
    {
        return deleted;
    }

    public User setDeleted(Integer deleted)
    {
        this.deleted = deleted;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public User setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getToken()
    {
        return token;
    }

    public User setToken(String token)
    {
        this.token = token;
        return this;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public User setLoginName(String loginName)
    {
        this.loginName = loginName;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public User setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public String getPhone()
    {
        return phone;
    }

    public User setPhone(String phone)
    {
        this.phone = phone;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public User setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public String[] getRoleIds()
    {
        return roleIds;
    }

    public User setRoleIds(String roleIds)
    {
        this.roleIds = roleIds.split(",");
        return this;
    }

    public Integer getStatus()
    {
        return status;
    }

    public User setStatus(Integer status)
    {
        this.status = status;
        return this;
    }

    public String getDeptId()
    {
        return deptId;
    }

    public User setDeptId(String deptId)
    {
        this.deptId = deptId;
        return this;
    }

    public String getPositionId()
    {
        return positionId;
    }

    public User setPositionId(String positionId)
    {
        this.positionId = positionId;
        return this;
    }

    public String getEmpCode()
    {
        return empCode;
    }

    public User setEmpCode(String empCode)
    {
        this.empCode = empCode;
        return this;
    }
}
