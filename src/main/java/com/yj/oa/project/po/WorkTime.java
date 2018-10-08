package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

public class WorkTime extends BasePo{
    private Integer id;

    private Date attendMorStartTime;

    private Date attendMorendTime;

    private Date workStartTimeMor;

    private Date workEndTimeMor;

    private Date attendMorLeaveStartTime;

    private Date attendMorLeaveEndTime;

    private Date attendAfterNoonStartTime;

    private Date attendAfterNoonendTime;

    private Date workStartTimeAfterNoon;

    private Date workEndTimeAfterNoon;

    private Date attendAfterLeaveStartTime;

    private Date attendAfterLeaveEndTime;

    private Date createTime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAttendMorStartTime() {
        return attendMorStartTime;
    }

    public void setAttendMorStartTime(Date attendMorStartTime) {
        this.attendMorStartTime = attendMorStartTime;
    }

    public Date getAttendMorendTime() {
        return attendMorendTime;
    }

    public void setAttendMorendTime(Date attendMorendTime) {
        this.attendMorendTime = attendMorendTime;
    }

    public Date getWorkStartTimeMor() {
        return workStartTimeMor;
    }

    public void setWorkStartTimeMor(Date workStartTimeMor) {
        this.workStartTimeMor = workStartTimeMor;
    }

    public Date getWorkEndTimeMor() {
        return workEndTimeMor;
    }

    public void setWorkEndTimeMor(Date workEndTimeMor) {
        this.workEndTimeMor = workEndTimeMor;
    }

    public Date getAttendMorLeaveStartTime() {
        return attendMorLeaveStartTime;
    }

    public void setAttendMorLeaveStartTime(Date attendMorLeaveStartTime) {
        this.attendMorLeaveStartTime = attendMorLeaveStartTime;
    }

    public Date getAttendMorLeaveEndTime() {
        return attendMorLeaveEndTime;
    }

    public void setAttendMorLeaveEndTime(Date attendMorLeaveEndTime) {
        this.attendMorLeaveEndTime = attendMorLeaveEndTime;
    }

    public Date getAttendAfterNoonStartTime() {
        return attendAfterNoonStartTime;
    }

    public void setAttendAfterNoonStartTime(Date attendAfterNoonStartTime) {
        this.attendAfterNoonStartTime = attendAfterNoonStartTime;
    }

    public Date getAttendAfterNoonendTime() {
        return attendAfterNoonendTime;
    }

    public void setAttendAfterNoonendTime(Date attendAfterNoonendTime) {
        this.attendAfterNoonendTime = attendAfterNoonendTime;
    }

    public Date getWorkStartTimeAfterNoon() {
        return workStartTimeAfterNoon;
    }

    public void setWorkStartTimeAfterNoon(Date workStartTimeAfterNoon) {
        this.workStartTimeAfterNoon = workStartTimeAfterNoon;
    }

    public Date getWorkEndTimeAfterNoon() {
        return workEndTimeAfterNoon;
    }

    public void setWorkEndTimeAfterNoon(Date workEndTimeAfterNoon) {
        this.workEndTimeAfterNoon = workEndTimeAfterNoon;
    }

    public Date getAttendAfterLeaveStartTime() {
        return attendAfterLeaveStartTime;
    }

    public void setAttendAfterLeaveStartTime(Date attendAfterLeaveStartTime) {
        this.attendAfterLeaveStartTime = attendAfterLeaveStartTime;
    }

    public Date getAttendAfterLeaveEndTime() {
        return attendAfterLeaveEndTime;
    }

    public void setAttendAfterLeaveEndTime(Date attendAfterLeaveEndTime) {
        this.attendAfterLeaveEndTime = attendAfterLeaveEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}