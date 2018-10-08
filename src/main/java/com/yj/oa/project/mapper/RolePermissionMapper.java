package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.RolePermission;

import java.util.List;

public interface RolePermissionMapper {
    /**
     *
     * 根据角色删除
     * @mbggenerated
     */
    int deleteByRoleIdyKey(Integer roleId);

    /**
     * 批量删除
     * @param roleIds
     * @return
     */
    int deleteByRoleIdKeys(Integer[] roleIds);



    /**
     * 添加
     */
    int insertSelective(RolePermission record);

    /**
     * 删除菜单时候，判断是否有角色拥有当前菜单信息 是否存在子菜单
     * 查询有少个
     */
    int selectByPermissionKey(Integer id);


    /**
     *
     * @描述 批量插入
     *
     * @date 2018/9/16 16:35
     */
    int insertBatch(List<RolePermission> list);
}