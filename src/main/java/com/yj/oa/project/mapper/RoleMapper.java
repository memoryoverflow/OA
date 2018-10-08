package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Role;

import java.util.List;

public interface RoleMapper {
    /**
     * 添加
     * @param record
     * @return
     */
    int insert(Role record);

    /**
     * 列表
     * @param role
     * @return
     */
    List<Role> selectRoleList(Role role);

    /**
     * 唯一性检查
     * @param roleName
     * @return
     */
    Role checkRoleNameUnique(String roleName);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(Integer[]ids);

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
     */
    int updateByPrimaryKeySelective(Role role);
}