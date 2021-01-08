package cn.yj.user.service.impl;

import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.ConsVal;
import cn.yj.user.entity.po.Role;
import cn.yj.user.mapper.RoleMapper;
import cn.yj.user.mapper.RolePermissionMapper;
import cn.yj.user.mapper.UserRoleMapper;
import cn.yj.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService
{

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public Page<Role> findList(Map<String, Object> map, Page<Role> page)
    {
        return page.setRows(baseMapper.findList(map, page));
    }


    @Override
    public boolean toRoleAuth(String roleId, String[] menuIds)
    {
        // 判断是否是管理的角色，如果是管理员的角色不允许操作
        if (ConsVal.SUPER_ADMIN_ID.equals(roleId))
        {
            throw new ServiceException("不允许修改超级管理员的权限");
        }

        // 先删除 再授权
        rolePermissionMapper.deleteByRoleId(roleId);

        // 授权
        for (String menuId : menuIds)
        {
            rolePermissionMapper.insert(roleId, menuId);
        }
        return true;
    }

    @CheckObjectValue(keyValue = @KeyValue(type = Role.class, name = {"id", "name", "code"}))
    @Override
    public boolean updateById(Role entity)
    {
        Role role = baseMapper.selectByName(entity.getRoleName());
        if (StringUtils.isNotNull(role) && !role.getId().equals(entity.getId()))
        {
            throw new ServiceException(entity.getRoleName() + "已经存在");
        }

        role = baseMapper.selectByCode(entity.getCode());
        if (StringUtils.isNotNull(role) && !role.getId().equals(entity.getId()))
        {
            throw new ServiceException(entity.getCode() + "已经存在");
        }
        entity.setUpdateTime(new Date());
        return super.updateById(entity);
    }

    @Override
    public Role selectByName(String name)
    {
        return baseMapper.selectByName(name);
    }


    @CheckObjectValue(keyValue = @KeyValue(type = Role.class, name = {"name", "code"}))
    @Override
    public boolean insert(Role role)
    {
        String name = role.getRoleName();
        if (StringUtils.isNotNull(baseMapper.selectByName(name)))
        {
            throw new ServiceException("名称：" + role.getRoleName() + "已经存在");
        }

        if (StringUtils.isNotNull(baseMapper.selectByCode(role.getCode())))
        {
            throw new ServiceException("编码：" + role.getRoleName() + "已经存在");
        }
        role.setId(UUIdUtils.getUUId32());
        role.setUpdateTime(new Date());
        role.setCreateTime(new Date());
        return baseMapper.insert(role) > 0;
    }

    @Override
    public List<Map<String, Object>> listIdNameAll()
    {
        return baseMapper.listIdNameAll();
    }

    @Override
    public List<Map<String, Object>> selectRolesNameCodeIdByUserId(String userId)
    {
        return baseMapper.selectRolesNameCodeIdByUserId(userId);
    }

    @Override
    public boolean removeByIds(Serializable[] ids)
    {
        for (Serializable id : ids)
        {
            if (userRoleMapper.selectCountByRoleId(id) > 0)
            {
                throw new ServiceException("存在角色与用户绑定,不可删除");
            }
            rolePermissionMapper.deleteByRoleId(id);
        }
        return super.removeByIds(ids);
    }
}
