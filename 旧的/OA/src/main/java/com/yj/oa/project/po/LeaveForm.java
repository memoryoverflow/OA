package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LeaveForm extends BasePo{

    private String id;



    /** 发起人Id */
    private String proposer_Id;
    /** 实例ID*/
    private String procInstanId;

    /** 代理人ID */
    private String agent_Id;

    /** 标题*/
    private String title;
    /**请假类型 0：事假:1：病假:2：公假*/
    private String leaveType;

    /** 审批发起时间 */
    private Date createTime;

    /**审批完成结束时间*/
    private Date endTime;

    /** 请假生效时间*/
    private Date leaveTime;
    /**到期时间*/
    private Date expireTime;


    /** 备注 */
    private String leaveContent;

    /**回复内容*/
    private String reply;

    /** 状态 */
    private Integer status;

    private Date currDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProposer_Id()
    {
        return proposer_Id;
    }

    public void setProposer_Id(String proposer_Id)
    {
        this.proposer_Id = proposer_Id;
    }

    public String getProcInstanId()
    {
        return procInstanId;
    }

    public void setProcInstanId(String procInstanId)
    {
        this.procInstanId = procInstanId;
    }

    public String getAgent_Id()
    {
        return agent_Id;
    }

    public void setAgent_Id(String agent_Id)
    {
        this.agent_Id = agent_Id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLeaveType()
    {
        return leaveType;
    }

    public void setLeaveType(String leaveType)
    {
        this.leaveType = leaveType;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getLeaveTime()
    {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime)
    {
        this.leaveTime = leaveTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }

    public String getLeaveContent()
    {
        return leaveContent;
    }

    public void setLeaveContent(String leaveContent)
    {
        this.leaveContent = leaveContent;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getCurrDate()
    {
        return currDate;
    }

    public void setCurrDate(Date currDate)
    {
        this.currDate = currDate;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("LeaveForm{");
        sb.append("id='").append(id).append('\'');
        sb.append(", proposer_Id='").append(proposer_Id).append('\'');
        sb.append(", procInstanId='").append(procInstanId).append('\'');
        sb.append(", agent_Id='").append(agent_Id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", leaveTime='").append(leaveTime).append('\'');
        sb.append(", expireTime='").append(expireTime).append('\'');
        sb.append(", leaveContent='").append(leaveContent).append('\'');
        sb.append(", reply='").append(reply).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}