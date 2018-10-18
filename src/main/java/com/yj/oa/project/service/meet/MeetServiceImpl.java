package com.yj.oa.project.service.meet;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.MeetMapper;
import com.yj.oa.project.mapper.UserMeetMapper;
import com.yj.oa.project.po.Meet;
import com.yj.oa.project.po.User;
import com.yj.oa.project.po.UserMeet;
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
public class MeetServiceImpl implements IMeetService{

    @Autowired
    MeetMapper meetMapper;
    @Autowired
    UserMeetMapper userMeetMapper;

    /**
     *
     * @描述: 根据主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:06
     */
    @Override
    public Meet selectByPrimaryKey(Integer id)
    {
        return meetMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:08
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids)
    {
        //删除中间表信息
        userMeetMapper.deleteByMeetIdKeys(ids);

        return meetMapper.deleteByPrimaryKeys(ids);
    }


    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int insertSelective(Meet record, String[] userIds)
    {
        int i = meetMapper.insertSelective(record);

        Integer meetId = record.getId();

        List<UserMeet> list = getListUserMeet(meetId, userIds);
        userMeetMapper.insertSelective(list);

        return i;
    }


    /**
     *
     * @描述: 修改
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:09
     */
    @Override
    public int updateByPrimaryKeySelective(Meet record, String[] userIds)
    {
        //1.先删除原来的用户
        Integer meetId = record.getId();
        Integer[] meetIds = {meetId};
        userMeetMapper.deleteByMeetIdKeys(meetIds);

        if (!StringUtils.isEmpty(userIds) && userIds.length > 0)
        {
            //2.插入新的用户
            List<UserMeet> list = getListUserMeet(meetId, userIds);
            userMeetMapper.insertSelective(list);
        }



        return meetMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述: 数据列表
     *
     * @params:
     * @return:
     * @date: 2018/9/26 21:11
     */
    @Override
    public List<Meet> selectByMeetList(Meet meet)
    {
        return meetMapper.selectByMeetList(meet);
    }

    /**
     *
     * @描述:  我的会议 首页通知
     *
     * @params:
     * @return:
     * @date: 2018/9/28 12:18
    */
    @Override
    public List<Meet> selectMyMeetList(String uId)
    {
        return meetMapper.selectMyMeetList(uId);
    }


    public static List<UserMeet> getListUserMeet(Integer meetId, String[] userIds)
    {
        List<UserMeet> list = new ArrayList<>();
        if (!StringUtils.isEmpty(userIds) && userIds.length > 0)
        {
            for (String uId : userIds)
            {
                UserMeet userMeet = new UserMeet();
                userMeet.setMeetId(meetId);
                userMeet.setUserId(uId);
                list.add(userMeet);
            }
        }
        return list;
    }
}
