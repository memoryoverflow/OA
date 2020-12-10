package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ApplyRoomForm;
import com.yj.oa.project.po.LeaveForm;

import java.util.List;

public interface LeaveFormMapper{
    /**
     *
     * 批量删除
     * @mbggenerated
     */
    int deleteByprocInstIds(String[] ids);

    /**
     *添加
     * @mbggenerated
     */
    int insertSelective(LeaveForm record);

    /**
     *主键查询
     *
     * @mbggenerated
     */
    LeaveForm selectByPrimaryKey(Integer id);

    /**
     * 修改状态
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LeaveForm record);


    /**
     * 列表
     * @mbggenerated
     */
    List<LeaveForm> selectLeavFormList(LeaveForm a);

    /**
     * 统计请假天数
     */
    List<LeaveForm> selectByUserIdAndDate(LeaveForm leaveForm);
}