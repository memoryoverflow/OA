package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;
import java.util.List;

public class Dept extends BasePo{

    private Integer depId;
    private String leader;

    private Date createTime;


    private String deptName;

    private String introduce;

    private Integer status;

    private List<User> users;


    public Integer getDepId()
    {
        return depId;
    }

    public void setDepId(Integer depId)
    {
        this.depId = depId;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getIntroduce()
    {
        return introduce;
    }

    public void setIntroduce(String introduce)
    {
        this.introduce = introduce;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Dept{");
        sb.append("depId=").append(depId);
        sb.append(", leader='").append(leader).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", introduce='").append(introduce).append('\'');
        sb.append(", status=").append(status);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}