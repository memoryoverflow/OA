package cn.yj.admin.service;

import cn.yj.admin.entity.po.User;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.baseDao.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IUserService extends IService<User>
{

    User findByToken(String token);

    /**
     * 重置密码
     *
     * @param userId
     * @param newPassword
     * @return
     */
    boolean reloadPassword(String userId, String newPassword);


    /**
     * 只查询两个字段 id、name
     *
     * @return
     */
    List<Map<String, String>> listIdName();

    List<User> getUserListByEmpCodes(String[] empCodes);

    List<User> getUserListByPositionCode(String empCodes);

    boolean updateUserPositionCodeById(String userId, String positionCode);

    boolean updateUserDepartmentCodeById(String userId, String departCode);

    boolean activeAndBlackUser(Integer setStatus, String userId);

    Page<User> listUserCodeAndDept(Map<String, Object> param, Page<User> create_time);
}
