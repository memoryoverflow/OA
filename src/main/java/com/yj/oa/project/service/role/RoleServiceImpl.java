package com.yj.oa.project.service.role;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.PermissionMapper;
import com.yj.oa.project.mapper.RoleMapper;
import com.yj.oa.project.mapper.RolePermissionMapper;
import com.yj.oa.project.mapper.UserMapper;
import com.yj.oa.project.po.Permission;
import com.yj.oa.project.po.Role;
import com.yj.oa.project.po.RolePermission;
import com.yj.oa.project.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

    private Logger log = LoggerFactory.getLogger(Transactional.class);

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    /**
     *
     * @描述 添加角色
     *
     * @date 2018/9/17 8:54
     */

    @Override
    public int insert(Role record, Integer[] perIds) throws Exception
    {

        List<RolePermission> rolePermissionList = new ArrayList<>();

        if (perIds.length <= 0)
        {
            throw new Exception("请为当前角色，添加权限！");
        }

        try
        {
            //返回自增主键
            int insert = roleMapper.insert(record);
            //批量插入角色权限中间表
            rolePermissionMapper.insertBatch(getList(record.getRoleId(), perIds));
            return insert;
        }
        catch (Exception e)
        {
            log.error("添加角色失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }
    }

    /**
     * @描述 角色列表 条件查询
     * @date 2018/9/15 13:06
     */
    @Override
    public List<Role> selectRoleList(Role role)
    {
        return roleMapper.selectRoleList(role);
    }


    /**
     *
     * @描述 验证角色名称是否唯一
     *
     * @date 2018/9/16 16:07
     */
    @Override
    public String checkRoleNameUnique(Role role)
    {
        if (role.getRoleId() == null)
        {
            role.setRoleId(-1);
        }
        Integer roleId = role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getRoleId()) && !info.getRoleId().equals(
                roleId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }


    /**
     * @描述 批量删除
     * @ Param ids:角色Id
     * @date 2018/9/16 15:58
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids) throws Exception
    {
        User user = new User();
        List<User> users = new ArrayList<>();

        //   判断当前删除的角色是否还有用户
        for (Integer id : ids)
        {
            user.setRole_ID(id);
            users = userMapper.selectByUser(user);

            if (users.size() > 0)
            {
                throw new Exception("用户拥有此角色，不允许删除！");
            }
        }

        try
        {
            //删除角色
            int i = roleMapper.deleteByPrimaryKeys(ids);
            //删除角色所拥有的权限
            rolePermissionMapper.deleteByRoleIdKeys(ids);

            return i;
        }
        catch (Exception e)
        {
            log.error("删除角色失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }

    }

    /**
     *
     * @描述 通过主键查询单条
     *
     * @date 2018/9/16 16:41
     */
    @Override
    public Role selectByPrimaryKey(Integer roleId)
    {
        return roleMapper.selectByPrimaryKey(roleId);
    }


    /**
     *
     * @描述 更改权限
     *
     * @date 2018/9/16 16:41
     */
    @Override
    public int updateByPrimaryKeyPowerSelective(Role role, Integer[] ids)
    {
        //  删除原有权限
        rolePermissionMapper.deleteByRoleIdyKey(role.getRoleId());

        if (ids != null || ids.length > 0)
        {
            //  添加新的权限
            return rolePermissionMapper.insertBatch(getList(role.getRoleId(), ids));
        }
        return 0;
    }


    /**
     *
     * @描述 更改
     *
     * @date 2018/9/16 16:41
     */
    @Override
    public int updateByPrimaryKeySelective(Role role) throws Exception
    {


        // 校验是否允许停用
        List<User> users = new ArrayList<>();
        if (role.getStatus() == 1)
        {
            User user = new User();
            user.setRole_ID(role.getRoleId());
            users = userMapper.selectByUser(user);
            if (users.size() > 0)
            {
                throw new Exception("角色已分配，不允许停用！");
            }
        }

        return roleMapper.updateByPrimaryKeySelective(role);


    }


    public static List<RolePermission> getList(Integer roleId, Integer[] ids)
    {
        List<RolePermission> permissionList = new ArrayList<>();
        for (Integer id :
                ids)
        {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole_Id(roleId);
            rolePermission.setPermission_Id(id);
            permissionList.add(rolePermission);
        }
        return permissionList;
    }
}
