package cn.yj.admin.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface UserRoleMapper
{

    @Delete(value = "delete from tb_user_role where user_id=#{userId}")
    int deleteByUserId(Serializable userId);

    @Insert("insert into tb_user_role (role_id,user_id) values (#{roleId},#{userId})")
    int insert(@Param("roleId") String roleId, @Param("userId") String userId);

    @Select("select count(id) from tb_user_role where role_id=#{roleId}")
    long selectCountByRoleId(Serializable roleId);
}
