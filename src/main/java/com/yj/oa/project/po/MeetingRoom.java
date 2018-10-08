package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

public class MeetingRoom extends BasePo{

    private Integer id;

    private String meetRoomName;

    private String meetRoomPlace;

    private String capacity;

    private Date createTime;

 /**
 * @ 描述 房间状态情况 0：空闲，1：预约中 2：使用中，3停用
 * @date 2018/9/22 14:10
 */
    private Integer status;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getMeetRoomName()
    {
        return meetRoomName;
    }

    public void setMeetRoomName(String meetRoomName)
    {
        this.meetRoomName = meetRoomName;
    }

    public String getMeetRoomPlace()
    {
        return meetRoomPlace;
    }

    public void setMeetRoomPlace(String meetRoomPlace)
    {
        this.meetRoomPlace = meetRoomPlace;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public void setCapacity(String capacity)
    {
        this.capacity = capacity;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("MeetingRoom{");
        sb.append("id=").append(id);
        sb.append(", meetRoomName='").append(meetRoomName).append('\'');
        sb.append(", meetRoomPlace='").append(meetRoomPlace).append('\'');
        sb.append(", capacity='").append(capacity).append('\'');
        sb.append(", crateeTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}