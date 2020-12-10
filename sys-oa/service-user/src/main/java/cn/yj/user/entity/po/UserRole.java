package cn.yj.user.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lyj.forward.generation.annotation.LTable;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@LTable
public class UserRole implements Serializable
{
    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Integer id;


    private String roleId;


    private String userId;

    public UserRole()
    {
    }

    public UserRole(String roleId, String userId)
    {
        this.roleId = roleId;
        this.userId = userId;
    }

    public Integer getId()
    {
        return id;
    }

    public UserRole setId(Integer id)
    {
        this.id = id;
        return this;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public UserRole setRoleId(String roleId)
    {
        this.roleId = roleId;
        return this;
    }

    public String getUserId()
    {
        return userId;
    }

    public UserRole setUserId(String userId)
    {
        this.userId = userId;
        return this;
    }
}
