package com.yj.oa.project.service.role;

import com.yj.oa.project.po.Role;

import java.util.List;

/**
 * @author 永健
 * @date 2018/9/15 13:04
 * @描述
 */
public interface IRoleService {
    /**
     * 添加
     * @param record
     * @param perIds
     * @return
     * @throws Exception
     */
    int insert(Role record,Integer[] perIds) throws Exception;



    /**
     * @描述 查询所有
     * @date 2018/9/15 13:05
     */

    List<Role> selectRoleList(Role role);

    /**
     * @描述唯一性
     * @date 2018/9/16 15:58
     */
    String checkRoleNameUnique(Role role);

     /**
      *
      * @描述 批量删除
      *
      * @date 2018/9/16 15:58
      */
    int deleteByPrimaryKeys(Integer[] ids) throws Exception;

    /**
     * 主键查询
     * @param roleId
     * @return
     */
    Role selectByPrimaryKey(Integer roleId);

    /**
     * 修改
     * @param role
     * @return
     * @throws Exception
     */
    int updateByPrimaryKeySelective(Role role)throws Exception;

    /**
     * 修改角色权限
     * @param role
     * @param ids
     * @return
     */
    int updateByPrimaryKeyPowerSelective(Role role,Integer[] ids);
}
