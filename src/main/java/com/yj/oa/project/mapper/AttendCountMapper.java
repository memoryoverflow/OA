package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Attend;
import com.yj.oa.project.po.AttendCount;

import java.util.List;

public interface AttendCountMapper {

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
    int insertSelective(AttendCount record);

    /**
     * 主键查找
     * @param id
     * @return
     */
    AttendCount selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AttendCount record);

    /**
     * 列表
     * @param attendCount
     * @return
     */
    List<AttendCount> selectAttendCountList(AttendCount attendCount);
}