package cn.yj.user.mapper;

import cn.yj.common.BaseMapper;
import cn.yj.user.entity.po.Permission;
import cn.yj.user.entity.vo.MenuTree;
import cn.yj.user.entity.vo.MenuTreeData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface PermissionMapper extends BaseMapper<Permission>
{

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
     * web树形表格数据
     *
     * @param name
     * @return
     */
    List<MenuTreeData> selectAllMenuTree(@Param("name") String name);

    Set<String> selectUserPermissionCodes(String userId);

    Permission selectByName(String name);


    int removeByParentId(Serializable parentId);



    List<Permission> listByParentId(String parentId);


    /**
     * 获取父Id为0目录，并且排除掉首页，没有子级并且有有路由的
     * 只查询 id、perName连个字段
     *
     * @return
     */
    @Select("select id,per_name as perName from tb_permission where parent_id = 0 and url is null ")
    List<Map<String, String>> selectIdAndNameByMenuParentIdIsZeroAndUrlIsNull();
}
