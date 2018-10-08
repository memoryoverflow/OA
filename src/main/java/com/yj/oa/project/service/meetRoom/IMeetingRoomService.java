package com.yj.oa.project.service.meetRoom;

import com.yj.oa.project.po.MeetingRoom;

import java.util.List;

/**
 * @author 永健
 */
public interface IMeetingRoomService{

    /**
     * 添加
     */
    int insertSelective(MeetingRoom record);

    /**
     * 批量删除
     */
    int deleteByPrimaryKeys(Integer[]ids);

    /**
     * 修改
     */
    int updateByPrimaryKeySelective(MeetingRoom meetingRoom);

    /**
     * 列表查询
     */
    List<MeetingRoom> selectMeetRoomList(MeetingRoom meetingRoom);
    /**
     * 名字唯一性
     * @date
     */
    String checkRoomNameUnique(MeetingRoom meetingRoom);

    /**
     * 主键查询
     * @date
     */
    MeetingRoom selectByPrimaryKey(Integer id);
    /**
     * 房间名查询
     * @date
     */
    MeetingRoom selectByRoomName(String roomName);

    /**
     * 根据房间名修改状态 房间名唯一
     * @date
     */
    int updateByRoomName(MeetingRoom meetingRoom);
}
