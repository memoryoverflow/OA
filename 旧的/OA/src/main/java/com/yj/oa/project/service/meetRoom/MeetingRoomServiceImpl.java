package com.yj.oa.project.service.meetRoom;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.MeetingRoomMapper;
import com.yj.oa.project.po.MeetingRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class MeetingRoomServiceImpl implements IMeetingRoomService{
    private static Logger log= LoggerFactory.getLogger(MeetingRoomServiceImpl.class);

    @Autowired
    MeetingRoomMapper roomService;


    /**
     *
     * @描述 添加
     *
     * @date 2018/9/20 12:17
     */
    @Override
    public int insertSelective(MeetingRoom record)
    {
        return roomService.insertSelective(record);
    }

    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/20 12:18
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids)
    {
        try
        {
            return roomService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            throw new RuntimeException("操作失败！");
        }
    }

    /**
     *
     * @描述 修改
     *
     * @date 2018/9/20 12:20
     */
    @Override
    public int updateByPrimaryKeySelective(MeetingRoom meetingRoom)
    {
        return roomService.updateByPrimaryKeySelective(meetingRoom);
    }

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/20 12:20
     */
    @Override
    public List<MeetingRoom> selectMeetRoomList(MeetingRoom meetingRoom)
    {
        return roomService.selectMeetRoomList(meetingRoom);
    }

    /**
     *
     * @描述 名字唯一性校验
     *
     * @date 2018/9/20 12:21
     */
    public String checkRoomNameUnique(MeetingRoom meetingRoom)
    {
        Integer id = StringUtils.isNull(meetingRoom.getId()) ? -1 : meetingRoom.getId();
        MeetingRoom info = roomService.checkRoomNameUnique(meetingRoom.getMeetRoomName());
        if (StringUtils.isNotNull(info) && !info.getId().equals(id))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

    /**
     *
     * @描述 主键查询
     *
     * @date 2018/9/20 12:36
     */
    @Override
    public MeetingRoom selectByPrimaryKey(Integer id)
    {
        return roomService.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述 通过房间名称查询
     *
     * @date 2018/9/22 16:46
     */
    @Override
    public MeetingRoom selectByRoomName(String roomName)
    {
        return roomService.selectByRoomName(roomName);
    }

    /**
     * @描述 根据房间名修改状态 房间名唯一
     * @date 2018/9/20 12:36
     */
    public int updateByRoomName(MeetingRoom meetingRoom)
    {
        return roomService.updateByRoomName(meetingRoom);
    }
}
