package cn.yj.admin.mapper;

import cn.yj.admin.entity.po.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface RolePermissionMapper
{


    @Insert("insert into tb_role_permission (role_id,permission_id) values (#{roleId},#{permissionId})")
    int insert(@Param("roleId") String roleId, @Param("permissionId") String permissionId);

    @Select("select id,role_id,permission_id from tb_role_permission where role_id=#{roleId}")
    List<RolePermission> selectListByRoleId(String roleId);

    @Delete("delete from tb_role_permission where role_id=#{roleId}")
    int deleteByRoleId(Serializable roleId);
}
