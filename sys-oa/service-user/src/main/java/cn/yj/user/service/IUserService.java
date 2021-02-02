package cn.yj.user.service;

import cn.yj.common.IService;
import cn.yj.user.LoginModel;
import cn.yj.common.LoginUser;
import cn.yj.user.entity.po.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface IUserService extends IService<User>
{

    LoginUser login(LoginModel model);

    User selectByLoginName(String loginName);

    User findByToken(String token);

    /**
     * 重置密码
     * @param userId
     * @param newPassword
     * @return
     */
    boolean reloadPassword(String userId,String newPassword);

    boolean updateUserInfoById(User user);

    /**
     * 只查询两个字段 id、name
     * @return
     */
    List<Map<String,String>> listIdName();

    List<User> getUserListByEmpCodes(String[] empCodes);

    List<User> getUserListByPositionCode(String empCodes);
}
