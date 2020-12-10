package com.yj.oa.project.service.menu;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.PermissionMapper;
import com.yj.oa.project.mapper.RolePermissionMapper;
import com.yj.oa.project.po.dto.MenuTree;
import com.yj.oa.project.po.dto.PermTree;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Role;
import com.yj.oa.project.po.RolePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 永健
 */
@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService{
    private Logger log = LoggerFactory.getLogger(Transactional.class);

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    /**
     *
     * @描述 删除
     *
     * @date 2018/9/17 14:41
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids) throws Exception
    {


        //   删除菜单时候，判断是否有角色拥有当前菜单信息 是否存在子菜单
        for (Integer id : ids)
        {
            int i = rolePermissionMapper.selectByPermissionKey(id);
            if (i>0)
            {
                throw new Exception("菜单已分配，无法删除！");
            }

            //是否存在子菜单
            Permission permission = new Permission();
            permission.setParenId(id);
            List<Permission> permissionList = permissionMapper.selectPersissionList(permission);
            if (!StringUtils.isEmpty(permissionList)||permissionList.size()>0)
            {
                throw new Exception("存在子菜单，无法删除！");
            }

        }

        try
        {
            return permissionMapper.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            log.error(" $$$$$ 删除菜单失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }
    }


    /**
     *
     * @描述 添加
     *
     * @date 2018/9/17 14:39
     */
    @Override
    public int insertSelective(Permission record)
    {
        int i = 0;
        try
        {
            i = permissionMapper.insertSelective(record);
        }
        catch (Exception e)
        {
            log.error("添加菜单失败=[{}]",e);
        }
        //添加菜单后，同时赋予管理员角色权限
//        RolePermission rolePermission = new RolePermission();
//        rolePermission.setRole_Id(1);
//        rolePermission.setPermission_Id(record.getPermissionId());
//        rolePermissionMapper.insertSelective(rolePermission);

        return i;
    }

    /**
     *
     * @描述 根据主键查询
     *
     * @date 2018/9/17 14:40
     */
    @Override
    public Permission selectByPrimaryKey(Integer permissionId)
    {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    /**
     *
     * @描述 更改
     *
     * @date 2018/9/17 14:40
     */
    @Override
    public int updateByPrimaryKeySelective(Permission record)
    {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述 验证菜单唯一性
     *
     * @date 2018/9/17 23:50
     */
    @Override
    public String checkMenuNameUnique(Permission permission)
    {
        Integer menuId = StringUtils.isNull(permission.getPermissionId()) ? -1 : permission.getPermissionId();
        Permission info = permissionMapper.checkMenuNameUnique(permission);
        if (StringUtils.isNotNull(info) && !info.getPermissionId().equals(menuId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }



    /**
     *
     * @描述 返回集合
     *
     * @date 2018/9/17 22:24
     */
    @Override
    public List<Permission> selectPersissionList(Permission record)
    {
        return permissionMapper.selectPersissionList(record);
    }


    /**
     * 查询左侧菜单
     * @param uid 用户Id
     * @return
     */
    @Override
    public List<MenuTree> selectMenuTree(String uid)
    {
        return permissionMapper.selectMenuTree(uid);
    }


    /**
     * 查询 所有菜单权限的结构树状
     * @return
     */
    @Override
    public List<PermTree> selectPermTree()
    {
        return permissionMapper.selectPermTree();
    }


    /**
     *
     * @描述 生成树形菜单
     *
     * @date 2018/9/17 22:18
     */
//    public static List<Object> getTreeMenuData(List<Permission> permissionList)
//    {
//        int pId = 0;
//        int class2Id = 0;
//
//        LinkedList<Object> array1 = new LinkedList<>();
//        for (int i = 0; i < permissionList.size(); i++)
//        {
//            LinkedList<Object> array2 = new LinkedList<>();
//            Permission p = permissionList.get(i);
//            if (p.getType() == 1)
//            {
//                //一级菜单Id
//                pId = p.getPermissionId();
//
//                Map<String, Object> treeData1 = new HashMap();
//                treeData1.put("name", p.getPerName());
//                treeData1.put("id", p.getPermissionId());
//                treeData1.put("checked ", "false");
//
//
//                //二级菜单
//                for (int j = 0; j < permissionList.size(); j++)
//                {
//                    Permission p2 = permissionList.get(j);
//                    LinkedList<Object> array3 = new LinkedList<>();
//                    if (p2.getParenId() == pId && p2.getType() == 2)
//                    {
//
//                        class2Id = p2.getPermissionId();
//
//                        Map<String, Object> treeData2 = new HashMap();
//                        treeData2.put("name", p2.getPerName() + "   " + p2.getUrl());
//                        treeData2.put("id", p2.getPermissionId());
//                        treeData2.put("checked ", "false");
//
//                        //三级菜单 (按钮)
//                        for (int k = 0; k < permissionList.size(); k++)
//                        {
//                            Permission p3 = permissionList.get(k);
//
//                            if (p3.getParenId() == class2Id && p3.getType() == 3)
//                            {
//
//                                Map<String, Object> treeData3 = new HashMap();
//                                treeData3.put("name", p3.getPerName());
//                                treeData3.put("id", p3.getPermissionId());
//                                treeData3.put("checked ", "false");
//                                array3.add(treeData3);
//
//                            }
//                        }
//                        treeData2.put("children", array3);
//                        array2.add(treeData2);
//
//                    }
//                }
//                treeData1.put("children", array2);
//                array1.add(treeData1);
//            }
//        }
//        return array1;
//    }
}
