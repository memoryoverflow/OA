package com.yj.oa.project.mapper;

import com.yj.oa.project.po.ApplyRoomForm;

import java.util.List;

public interface ApplyRoomFormMapper{
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
    int insertSelective(ApplyRoomForm record);

    /**
     *主键查询
     *
     * @mbggenerated
     */
    ApplyRoomForm selectByPrimaryKey(String id);

    /**
     * 修改状态
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ApplyRoomForm record);


    /**
     * 列表
     * @mbggenerated
     */
    List<ApplyRoomForm> selectApplyRoomList(ApplyRoomForm a);
}