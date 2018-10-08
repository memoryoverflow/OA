package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Schedule;

import java.util.List;

public interface ScheduleMapper{

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Schedule record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    Schedule selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Schedule record);

    /**
     * 列表
     * @param schedule
     * @return
     */
    List<Schedule> selectScheduleList(Schedule schedule);
}
