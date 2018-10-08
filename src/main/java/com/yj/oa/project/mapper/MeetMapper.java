package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Meet;

import java.util.List;

public interface MeetMapper {

    /**
     * 主键查询
     * @param id
     * @return
     */
    Meet selectByPrimaryKey(Integer id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Meet record);

    /**
     * 修改啊
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Meet record);

    /**
     * 列表
     * @param meet
     * @return
     */
    List<Meet> selectByMeetList(Meet meet);

    /**
     * 根据用户id获取个人会议
     * @param uId
     * @return
     */
    List<Meet> selectMyMeetList(String uId);


}