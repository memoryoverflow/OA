package cn.yj.user.service.impl;

import cn.yj.common.ServiceImpl;
import cn.yj.common.UUIdUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import cn.yj.user.entity.po.Department;
import cn.yj.user.entity.po.User;
import cn.yj.user.mapper.DepartmentMapper;
import cn.yj.user.service.IDepartmentService;
import cn.yj.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 12:47
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService
{

    @Resource
    IUserService iUserService;


    @Override
    @CheckObjectValue(keyValue = @KeyValue(type = Department.class, name = {"deptName"}))
    public boolean insert(Department department)
    {
        if (StringUtils.isNotNull(baseMapper.selectByName(department.getDeptName())))
        {
            throw new ServiceException("该部门名称 [ " + department.getDeptName() + " ]已经在");
        }

        String deptCode = department.getDeptCode();
        if (StringUtils.isNotBlank(deptCode) && StringUtils.isNotNull(baseMapper.selectByCode(deptCode)))
        {
            throw new ServiceException("该部门编码 [ " + department.getDeptName() + " ]已经在");
        }
        department.setId(UUIdUtils.getUUId32());
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        return super.insert(department);
    }

    @Override
    @CheckObjectValue(keyValue = @KeyValue(type = Department.class, name = {"id", "deptName"}))
    public boolean updateById(Department department)
    {
        Department departmentById = baseMapper.selectById(department.getId());
        Department departmentDb = baseMapper.selectByName(department.getDeptName());
        if (StringUtils.isNotNull(departmentDb) && !departmentDb.getId().equals(department.getId()))
        {
            throw new ServiceException("该部门名称 [ " + department.getDeptName() + " ]已经在");
        }

        // 上级部门不能是自己
        departmentDb = selectById(department.getId());
        if (StringUtils.isNotBlank(department.getDeptParentId()) && department.getDeptParentId().equals(departmentDb.getId()))
        {
            throw new ServiceException("上级部门不能是自己");
        }

        String deptCode = department.getDeptCode();
        if (StringUtils.isNotBlank(deptCode))
        {
            departmentDb = baseMapper.selectByCode(deptCode);
            if (StringUtils.isNotNull(departmentDb) && !department.getId().equals(departmentDb.getId()))
            {
                throw new ServiceException("该部门编码 [ " + department.getDeptCode() + " ]已经在");
            }
        }
        department.setUpdateTime(new Date());


        // 更新用户的部门编码code
        if (StringUtils.isNotBlank(departmentById.getDeptCode()) && !departmentById.getDeptCode().equals(department.getDeptCode()))
        {
            List<User> userListByPositionCode = iUserService.getUserListByPositionCode(departmentById.getDeptCode());
            if (!userListByPositionCode.isEmpty())
            {
                userListByPositionCode.forEach(user -> {
                    user.setDeptCode(department.getDeptCode());
                    iUserService.updateUserInfoById(user);
                });
            }
        }

        return super.updateById(department);
    }

    @Override
    public List<Department> treeData()
    {
        return baseMapper.treeData();
    }

    @Override
    public List<Map<String, Object>> listIdName()
    {
        return baseMapper.listIdName();
    }
}
