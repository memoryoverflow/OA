package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;
import java.util.List;

public class Meet extends BasePo{
    private Integer id;

    private String createBy;

    private String title;

    private String descr;

    private Date startTime;

    private String meetRoomId;

    private Date createTime;

    private String status;

    private MeetingRoom meetRoom;
    private List<User> users;

    public MeetingRoom getMeetRoom()
    {
        return meetRoom;
    }

    public void setMeetRoom(MeetingRoom meetRoom)
    {
        this.meetRoom = meetRoom;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescr()
    {
        return descr;
    }

    public void setDescr(String descr)
    {
        this.descr = descr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getMeetRoomId() {
        return meetRoomId;
    }

    public void setMeetRoomId(String meetRoomId) {
        this.meetRoomId = meetRoomId == null ? null : meetRoomId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Meet{");
        sb.append("id=").append(id);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", meetRoomId='").append(meetRoomId).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", status='").append(status).append('\'');
        sb.append(", meetRoom=").append(meetRoom);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}