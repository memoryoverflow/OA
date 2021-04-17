package cn.yj.admin.service.impl;

import cn.yj.admin.ConsVal;
import cn.yj.admin.entity.po.User;
import cn.yj.admin.frame.shiro.MD5;
import cn.yj.admin.mapper.UserMapper;
import cn.yj.admin.mapper.UserRoleMapper;
import cn.yj.admin.service.IUserService;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.Status;
import cn.yj.common.UUIdUtils;
import cn.yj.common.baseDao.ServiceImpl;
import cn.yj.commons.utils.CheckUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.CheckObjectValue;
import cn.yj.params.check.annotation.KeyValue;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import cn.yj.tools.readconfig.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService{

    private final UserRoleMapper userRoleMapper;

    private static String DEFAULT_PASSWORD = "123456";

    @Autowired
    public UserServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Page<User> findList(Map<String, Object> params, Page<User> page) {
        return page.startPage().setRows(baseMapper.findList(params, page));
    }

    @CheckObjectValue(keyValue = {@KeyValue(type = User.class, name = {"loginName", "name", "empCode", "positionCode", "roleIds"})})
    @Override
    public boolean insert(User entity) {
        String loginName = entity.getLoginName();
        if (StringUtils.isNotNull(baseMapper.selectByLoginName(loginName))) {
            throw new ServiceException("该登陆名已经存在");
        }
        if (StringUtils.isNotNull(baseMapper.selectByEmpCode(entity.getEmpCode()))) {
            throw new ServiceException("该用户编码已经存在");
        }

        if (!CheckUtils.isEmail(entity.getEmail())) {
            throw new ServiceException("邮箱格式不正确");
        }

        String userId = UUIdUtils.getUUId32();
        entity.setPassword(MD5.getInstance().getMD5(loginName.concat(PropertiesUtils.getStringValue("login.user-default-password", DEFAULT_PASSWORD))));
        entity.setId(userId);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setStatus(ConsVal.USER_STATUS_DEFAULT);
        entity.setDeleted(ConsVal.DELETED_DEFAULT);
        super.insert(entity);

        String[] roleIds = entity.getRoleIds();

        for (int i = 0; i < roleIds.length; i++) {
            userRoleMapper.insert(roleIds[i], entity.getId());
        }

        return true;
    }


    /**
     * 用户修改编辑
     *
     * @param entity
     * @return
     */
    @CheckObjectValue(keyValue = {@KeyValue(type = User.class, name = {"id", "loginName", "name", "empCode", "positionCode", "roleIds"})})
    @Override
    public boolean updateById(User entity) {
        // 登录名验证
        String loginName = entity.getLoginName();

        User user1 = baseMapper.selectByEmpCode(entity.getEmpCode());
        if (StringUtils.isNotNull(user1) && !user1.getId().equals(entity.getId())) {
            throw new ServiceException("该用户编码已经存在");
        }

        if (StringUtils.isNotNull(user1) && !user1.getLoginName().equals(loginName)) {
            throw new ServiceException("登陆名不可修改");
        }

        if (!CheckUtils.isEmail(entity.getEmail())) {
            throw new ServiceException("邮箱格式不正确");
        }

        entity.setUpdateTime(new Date());
        baseMapper.updateById(entity);

        // 角色更新
        String[] roleIds = entity.getRoleIds();
        userRoleMapper.deleteByUserId(entity.getId());
        for (int i = 0; i < roleIds.length; i++) {
            userRoleMapper.insert(roleIds[i], entity.getId());
        }

        return true;
    }

    @Override
    public boolean updateUserPositionCodeById(@Require String userId, @Require String positionCode) {
        User user = new User().setPositionCode(positionCode);
        user.setId(userId);
        return baseMapper.updateById(user) > 0;
    }


    @Override
    public boolean updateUserDepartmentCodeById(@Require String userId, @Require String deptCode) {
        User user = new User().setDeptCode(deptCode);
        user.setId(userId);
        return baseMapper.updateById(user) > 0;
    }

    @Override
    public boolean activeAndBlackUser(@Require Integer status, @Require String userId) {
        if (StringUtils.isNull(Status.User.equals(status))) {
            throw new ServiceException("非法状态：".concat(String.valueOf(status)));
        }
        User user = new User().setStatus(status);
        user.setId(userId);
        return super.updateById(user);
    }

    @Override
    public Page<User> listUserCodeAndDept(Map<String, Object> param, Page<User> page) {
        return page.startPage().setRows(baseMapper.listUserCodeAndDept(param, page));
    }

    @Override
    public User findByToken(String token) {
        return baseMapper.selectByToken(token);
    }

    @Override
    public boolean removeByIds(Serializable[] ids) {
        for (int i = 0; i < ids.length; i++) {
            userRoleMapper.deleteByUserId(ids[i]);
        }
        return super.removeByIds(ids);
    }

    @Override
    public boolean reloadPassword(@Require String userId, @Require String newPassword) {
        User user = baseMapper.selectById(userId);
        if (StringUtils.isNull(user)) {
            throw new ServiceException("该账户不存在");
        }
        return baseMapper.updateById(new User(userId).setPassword(MD5.getInstance().getMD5(user.getLoginName().concat(newPassword)))) > 0;
    }

    @Override
    public List<Map<String, String>> listIdName() {
        return baseMapper.listIdName();
    }

    @Override
    public List<User> getUserListByEmpCodes(String[] empCodes) {
        if (!StringUtils.isEmpty(empCodes)) {
            return baseMapper.selectByEmpCodes(empCodes);
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getUserListByPositionCode(String empCodes) {
        return baseMapper.selectUserListByPositionCode(empCodes);
    }
}
