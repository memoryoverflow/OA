package com.yj.oa.project.service.schedule;

import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.ScheduleMapper;
import com.yj.oa.project.mapper.ScheduleUserMapper;
import com.yj.oa.project.po.Schedule;
import com.yj.oa.project.po.ScheduleUser;
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
public class ScheduleServiceImpl implements IScheduleService{
    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    ScheduleUserMapper scheduleUserMapper;


    /**
     *
     * @描述: 批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] id)
    {
        //删除中间表
        scheduleUserMapper.deleteByPrimaryKeys(id);
        return scheduleMapper.deleteByPrimaryKeys(id);
    }

    /**
     *
     * @描述: 添加
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public int insertSelective(Schedule record, String[] userIds)
    {
        int i = scheduleMapper.insertSelective(record);
        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            List<ScheduleUser> listScheduleList = getListScheduleList(record.getId(),userIds);
            scheduleUserMapper.insertSelective(listScheduleList);
        }
        return i;
    }


    /**
     *
     * @描述: 主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
     */
    @Override
    public Schedule selectByPrimaryKey(Integer id)
    {

        return scheduleMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述: 修改
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:45
     */
    @Override
    public int updateByPrimaryKeySelective(Schedule record,String[] userIds)
    {
        //删除原有的
        Integer[] ids={record.getId()};
        scheduleUserMapper.deleteByPrimaryKeys(ids);

        if (!StringUtils.isEmpty(userIds) && userIds.length>0)
        {
            //插入新的
            List<ScheduleUser> listScheduleList = getListScheduleList(record.getId(),userIds);
            scheduleUserMapper.insertSelective(listScheduleList);
        }

        return scheduleMapper.updateByPrimaryKeySelective(record);
    }



    /**
     *
     * @描述:  修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2018/9/29 14:02
    */
    public int updateComplete(Schedule schedule){
        return scheduleMapper.updateByPrimaryKeySelective(schedule);
    }





    /**
     *
     * @描述: 列表
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:50
     */
    @Override
    public List<Schedule> selectScheduleList(Schedule schedule)
    {
        return scheduleMapper.selectScheduleList(schedule);
    }


    private static List<ScheduleUser>  getListScheduleList(Integer sId, String[] userIds)
    {
        List<ScheduleUser> objects = new ArrayList<>();
        for (String id:userIds)
        {
            ScheduleUser scheduleUser = new ScheduleUser();
            scheduleUser.setsId(sId);
            scheduleUser.setSuId(id);
            objects.add(scheduleUser);
        }
        return objects;
    }
}

