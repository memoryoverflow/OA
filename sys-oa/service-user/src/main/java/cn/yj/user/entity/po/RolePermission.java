package cn.yj.user.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@Data
public class RolePermission implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String roleId;

    private String permissionId;

    public RolePermission()
    {
    }

    public RolePermission(String roleId, String permissionId)
    {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
