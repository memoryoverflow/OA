package cn.yj.admin.service;

import cn.yj.admin.entity.po.Role;
import cn.yj.common.baseDao.IService;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IRoleService extends IService<Role>
{

    boolean toRoleAuth(String roleId, String[] menuIds);


    Role selectByName(String name);

    List<Map<String,Object>> listIdNameAll();

    List<Map<String, Object>> selectRolesNameCodeIdByUserId(String userId);
}
