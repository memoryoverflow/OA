package com.yj.oa.project.service.schedule;

import com.yj.oa.project.po.Note;
import com.yj.oa.project.po.Schedule;

import java.util.List;

/**
 * @author 永健
 */
public interface IScheduleService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Schedule record,String[] userIds);

    Schedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Schedule record,String[] userIds);

    List<Schedule> selectScheduleList(Schedule schedule);

    int updateComplete(Schedule schedule);
}
