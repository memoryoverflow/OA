package com.yj.oa.project.mapper;

import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.po.dto.PermTree;
import com.yj.oa.project.po.Permission;

import java.util.List;

public interface PermissionMapper {

    /**
     * 删除
     * @param permissionId
     * @return
     */
    int deleteByPrimaryKeys(Integer[] permissionId);


    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Permission record);

    /**
     * 主键查询
     * @param permissionId
     * @return
     */
    Permission selectByPrimaryKey(Integer permissionId);

    /**
     *  菜单名唯一性查询
     * @param permission
     * @return
     */
    Permission checkMenuNameUnique(Permission permission);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * 列表
     * @param record
     * @return
     */
    List<Permission> selectPersissionList(Permission record);

    /**
     *  查询权限结构
     */
    List<PermTree> selectPermTree();


    /**
     * selectMenuTree 菜单列表结构 根据用户Id查询出左侧菜单列表
     */
    List<MenuTree> selectMenuTree(String uid);
}