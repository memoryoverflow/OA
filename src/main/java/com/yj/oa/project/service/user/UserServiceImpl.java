package com.yj.oa.project.service.user;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.common.utils.shiro.Encryption;
import com.yj.oa.project.mapper.RoleMapper;
import com.yj.oa.project.mapper.UserMapper;
import com.yj.oa.project.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;


    /**
     *
     * @描述: 登录
     *
     * @params:
     * @return:
     * @date: 2018/9/29 22:02
     */
    @Override
    public User login(String uid)
    {
        return userMapper.login(uid);
    }


    /**
     * @描述 新增用户
     * @date 2018/9/15 21:21
     */
    @Override
    public int insertSelective(User record)
    {
        //uid 为 当前日期+ 按顺序加一
        Long l = userMapper.selectUserMaxId();
        String uid = record.getUid();
        if (l == null)
        {
            uid = uid + "0";
        }
        else
        {
            uid=String.valueOf((l+1));
        }
        record.setUid(uid);
        return userMapper.insertSelective(record);
    }

    /**
     * @author 永健
     * @描述 登录操作，通过主键Id查询用户消息
     * @date 2018/9/15 10:07
     */
    @Override
    public User selectByPrimaryKey(String uId)
    {
        User user = userMapper.selectByPrimaryKey(uId);
        return user;
    }


    /**
     * @描述 批量删除
     * @date 2018/9/16 9:44
     */
    @Override
    public int deleteByPrimaryKeys(String[] ids) throws Exception
    {
        for (String id : ids)
        {
            if (User.isBoss(id))
            {
                throw new Exception("不允许删除管理员！");
            }
        }
        try
        {
            return userMapper.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            throw new RuntimeException("系统异常！");
        }

    }


    /**
     * @描述 用户列表 以及 分页查询
     * @date 2018/9/15 11:44
     */
    @Override
    public List<User> selectByUser(User user)
    {
        return userMapper.selectByUser(user);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        if (user.getUid() == null)
        {
            user.setUid("-1");
        }
        String userId = user.getUid();
        User info = userMapper.checkPhoneUnique(user.getTel());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getUid())
                && !info.getUid().equals(userId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     */
    @Override
    public String checkEmailUnique(User user)
    {
        if (user.getUid() == null)
        {
            user.setUid("-1");
        }
        String userId = user.getUid();

        User info = userMapper.checkEmailUnique(user.getEmail());
        System.out.println(user.getEmail());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getUid())
                && !info.getUid().equals(userId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

    /**
     * @描述 保存修改的用户信息
     * @date 2018/9/15 15:21
     */
    @Override
    public int updateByPrimaryKeySelective(User user)
    {
        Integer roleId = user.getRole_ID();

        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * @描述 密码重置
     * @date 2018/9/16 10:34
     */
    @Override
    public int resrtPwd(User user)
    {
        String md5Pwd = Encryption.getMD5(user.getPwd(), user.getUid()).toString();
        user.setUid(md5Pwd);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *
     * @描述: 校验用户登录名唯一性
     *
     * @params:
     * @return:
     * @date: 2018/10/2 17:03
     */
    @Override
    public String checkLoginNameUnique(User user)
    {
        if (user.getUid() == null)
        {
            user.setUid("-1");
        }
        String userId = user.getUid();

        User info = userMapper.checkLoginNameUnique(user.getLoginName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getUid())
                && !info.getUid().equals(userId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

}
