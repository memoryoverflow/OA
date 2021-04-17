package cn.yj.admin.mapper;

import cn.yj.admin.entity.po.User;
import cn.yj.annotation.pagehelper.StartPage;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 永健
 * @since 2020-04-04 03:15
 */
public interface UserMapper extends BaseMapper<User>
{

    /**
     * @param loginName
     * @return
     */
    User login(String loginName);

    @Select("select * from tb_user where id=#{id}")
    @Override
    User selectById(Serializable id);

    @Select("select * from tb_user where token=#{token}")
    User selectByToken(String token);


    @Select("select id,name,login_name,password,token,status,emp_code from tb_user where login_name=#{loginName}")
    User selectByLoginName(String loginName);

    @Select("select id,name,emp_code,login_name,password,token,status from tb_user where emp_code=#{empCode}")
    User selectByEmpCode(String empCode);

    List<User> selectByEmpCodes(String[] empCode);


    @Select("select id,name as userName,emp_code as empCode from tb_user")
    List<Map<String, String>> listIdName();

    @Select("select id,position_code from tb_user where position_code=#{positionCode}")
    List<User> selectUserListByPositionCode(String positionCode);


    @StartPage
    List<User> listUserCodeAndDept(@Param("map") Map<String, Object> params, Page<User> page);

}
