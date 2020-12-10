package com.yj.oa.project.mapper;

import com.yj.oa.project.po.WorkTime;

import java.util.List;

public interface WorkTimeMapper {
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
    int insertSelective(WorkTime record);

    /**
     * 主键查询
     * @param id
     * @return
     */
    WorkTime selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WorkTime record);

    /**
     * 查询正在使用的那一条数据
     * @return
     */
    WorkTime selectUsing();

    /**
     * 列表
     * @param workTime
     * @return
     */
    List<WorkTime> selectWorkTimeList(WorkTime workTime);
}