package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;
import java.util.List;

public class Schedule extends BasePo{
    private Integer id;

    private String createBy;

    private Date startTime;

    private Date endTime;

    private String title;

    private String descr;

    private String status;
    private Integer isComplete;
    private Date createTime;

    private List<User> userList;
    private User user;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getTitle()
    {
        return title;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getIsComplete()
    {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete)
    {
        this.isComplete = isComplete;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public void setUserList(List<User> userList)
    {
        this.userList = userList;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Schedule{");
        sb.append("id=").append(id);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", title='").append(title).append('\'');
        sb.append(", descr='").append(descr).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", userList=").append(userList);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}