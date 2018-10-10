package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

public class Attend extends BasePo{
    private Integer id;

    private String userId;

    private Date currDate;

    private String week;

    private Date attendMorStart;

    private Date attendMorLeave;

    private Date attendNoonStart;

    private Date attendNoonLeave;

    private Integer status;

    private Integer isDel;

    private String deptId;

    private User user;
    private Dept dept;

    public String getDeptId()
    {
        return deptId;
    }

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public Dept getDept()
    {
        return dept;
    }

    public void setDept(Dept dept)
    {
        this.dept = dept;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCurrDate() {
        return currDate;
    }

    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public Date getAttendMorStart() {
        return attendMorStart;
    }

    public void setAttendMorStart(Date attendMorStart) {
        this.attendMorStart = attendMorStart;
    }

    public Date getAttendMorLeave() {
        return attendMorLeave;
    }

    public void setAttendMorLeave(Date attendMorLeave) {
        this.attendMorLeave = attendMorLeave;
    }

    public Date getAttendNoonStart() {
        return attendNoonStart;
    }

    public void setAttendNoonStart(Date attendNoonStart) {
        this.attendNoonStart = attendNoonStart;
    }

    public Date getAttendNoonLeave() {
        return attendNoonLeave;
    }

    public void setAttendNoonLeave(Date attendNoonLeave) {
        this.attendNoonLeave = attendNoonLeave;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}