package cn.yj.admin.entity.vo;

import cn.yj.admin.entity.po.Permission;
import lombok.Data;

import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-28 21:49
 */
@Data
public class PermissionVo extends Permission
{
    private List<Permission> children;
}
