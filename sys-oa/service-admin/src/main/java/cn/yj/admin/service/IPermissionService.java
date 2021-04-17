package cn.yj.admin.service;

import cn.yj.admin.entity.po.Permission;
import cn.yj.admin.entity.vo.MenuTree;
import cn.yj.admin.entity.vo.MenuTreeData;
import cn.yj.common.baseDao.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IPermissionService extends IService<Permission>{

    Set<String> selectUserPermissionCodes(String userId);

    /**
     * 用户的菜单
     *
     * @param userId
     * @return
     */
    List<MenuTree> selectUserMenuTree(@Param("userId") String userId);


    /**
     * 查询用户的权限Id
     *
     * @param userId
     * @return
     */
    List<String> selectUserMenuTreeIds(@Param("userId") String userId);


    /**
     * 根据角色Id 获取当前角色的所有菜单Id
     *
     * @param roleId
     * @return
     */
    Set<String> selectMenuIdByRoleId(String roleId);

    /**
     * 数据表格的数据内容
     *
     * @param name
     * @return
     */
    List<MenuTreeData> selectAllMenuTree(String name);

    /**
     * 根据当前节点的父id 获取 上级所有节点的id
     *
     * @param parentId
     * @return
     */
    Set<String> selectMenIdsByParentId(String parentId);

    Set<String> selectMenuSonIdsByMenId(String menuId);


    boolean updateChange(Permission permission);


    List<Map<String, String>> getIdAndNameByMenuParentIdIsZeroAndUrlIsNull();
}
