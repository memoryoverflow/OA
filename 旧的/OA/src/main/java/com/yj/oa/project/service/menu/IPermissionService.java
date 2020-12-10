package com.yj.oa.project.service.menu;

import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.po.dto.PermTree;
import com.yj.oa.project.po.Permission;

import java.util.List;

/**
 * @author 永健
 * @date 2018/9/16 16:50
 * @描述
 */
public interface IPermissionService {

    int deleteByPrimaryKeys(Integer[] permissionId) throws Exception;

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    String checkMenuNameUnique(Permission permission);

    /**
     *
     * @描述 返回集合
     *
     * @date 2018/9/17 22:24
     */
    List<Permission> selectPersissionList(Permission record);

    List<MenuTree> selectMenuTree(String uid);

    /**
     *  查询权限结构
     */
    List<PermTree> selectPermTree();
}
