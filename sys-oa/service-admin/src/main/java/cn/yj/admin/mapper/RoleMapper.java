package cn.yj.admin.mapper;

import cn.yj.admin.entity.po.Role;
import cn.yj.common.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface RoleMapper extends BaseMapper<Role>{

    Role selectByCode(String code);


    Role selectByName(String name);

    @Select("select id,code as roleCode,role_name as roleName from tb_role")
    List<Map<String, Object>> listIdNameAll();

    List<Map<String, Object>> selectRolesNameCodeIdByUserId(String userId);
}
