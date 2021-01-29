package cn.yj.user.service.impl;

import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.ConsVal;
import cn.yj.user.entity.po.Permission;
import cn.yj.user.entity.po.Role;
import cn.yj.user.entity.po.RolePermission;
import cn.yj.user.entity.vo.MenuTree;
import cn.yj.user.entity.vo.MenuTreeData;
import cn.yj.user.mapper.PermissionMapper;
import cn.yj.user.mapper.RoleMapper;
import cn.yj.user.mapper.RolePermissionMapper;
import cn.yj.user.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService
{

    private final RoleMapper roleMapper;

    private final RolePermissionMapper rolePermissionMapper;


    @Autowired
    public PermissionServiceImpl(RoleMapper roleMapper, RolePermissionMapper rolePermissionMapper)
    {
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public Page<Permission> findList(Map<String, Object> map, Page<Permission> page)
    {
        return page.setRows(baseMapper.findList(map, page));
    }

    @Override
    public Set<String> selectUserPermissionCodes(String userId)
    {
        return baseMapper.selectUserPermissionCodes(userId);
    }

    @Override
    public List<MenuTree> selectUserMenuTree(String userId)
    {
        return baseMapper.selectUserMenuTree(userId);
    }

    @Override
    public List<String> selectUserMenuTreeIds(String userId)
    {
        return baseMapper.selectUserMenuTreeIds(userId);
    }

    public Set<String> selectMenuIdByRoleId(String roleId)
    {
        Set<String> set = new HashSet<>();
        List<RolePermission> menuRoles = rolePermissionMapper.selectListByRoleId(roleId);
        menuRoles.forEach(menuRole -> {
            set.add(menuRole.getPermissionId());
        });
        return set;
    }

    @Override
    public List<MenuTreeData> selectAllMenuTree(String name)
    {
        return baseMapper.selectAllMenuTree(name);
    }

    @Override
    public Set<String> selectMenIdsByParentId(String parentId)
    {
        Set<String> set = new HashSet<>();
        include(set, parentId);
        return set;
    }


    private void include(Set<String> set, String parentId)
    {
        Permission menu = baseMapper.selectById(parentId);
        if (StringUtils.isNotNull(menu))
        {
            set.add(menu.getId());
            if (StringUtils.isNotNull(menu.getParentId()) && !ConsVal.DEFAULT_PARENT_VAL.equals(menu.getParentId()))
            {
                include(set, menu.getParentId());
            }
        }
    }


    public Set<String> selectMenuSonIdsByMenId(String menuId)
    {
        Set<String> set = new HashSet<>();
        getSonIds(set, menuId);
        return set;
    }


    @CheckObjectValue(keyValue = @KeyValue(type = Permission.class, name = {"perName", "type", "sort"}))
    @Override
    public boolean insert(Permission entity)
    {
        if (StringUtils.isNotNull(baseMapper.selectByName(entity.getPerName())))
        {
            throw new ServiceException(entity.getPerName() + " 名称已经存在");
        }

        entity.setId(UUIdUtils.getUUId32());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setDeleted(Permission.DELETED_VAL);
        entity.setStatus(Permission.STATUS_VAL);
        if (baseMapper.insert(entity) > 0)
        {
            Role role = roleMapper.selectByCode(ConsVal.SUPER_ADMIN_CODE);
            if (StringUtils.isNull(role))
            {
                throw new ServiceException("系统中没有超级管理员");
            }
            rolePermissionMapper.insert(role.getId(), entity.getId());
        }

        return true;
    }

    /**
     * 修改菜单
     * String name, Integer parentId, Integer id
     *
     * @param perm
     * @return
     */
    @Override
    public boolean updateChange(Permission perm)
    {
        Permission permission = baseMapper.selectByName(perm.getPerName());
        if (StringUtils.isNotNull(permission))
        {
            if (!permission.getId().equals(perm.getId()))
            {
                throw new ServiceException("菜单名字已经存在");
            }
        }
        return baseMapper.updateById(perm) > 0;
    }

    @Override
    public List<Map<String, String>> getIdAndNameByMenuParentIdIsZeroAndUrlIsNull()
    {
        return baseMapper.selectIdAndNameByMenuParentIdIsZeroAndUrlIsNull();
    }

    @Override
    public boolean removeByIds(Serializable[] idList)
    {
        // 删除子节点
        for (Serializable id : idList)
        {
            baseMapper.removeByParentId(id);
        }

        return baseMapper.removeByIds(idList) > 0;
    }

    private void getSonIds(Set<String> set, String menuId)
    {
        List<Permission> list = baseMapper.listByParentId(menuId);
        if (!list.isEmpty())
        {
            list.forEach(menu -> {
                if (StringUtils.isNotNull(menu))
                {
                    set.add(menu.getId());
                    getSonIds(set, menu.getId());
                }
            });
        }
    }

}
