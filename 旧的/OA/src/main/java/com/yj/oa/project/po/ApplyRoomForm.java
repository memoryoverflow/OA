package com.yj.oa.project.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 永健
 *
 * @描述 会议室申请表单
 *
 * @date 2018/9/25 14:23
 */
public class ApplyRoomForm{

    private String id;

    /** 发起人Id */
    private String proposer_Id;
    /** 实例ID*/
    private String procInstanId;

    /** 代理人ID */
    private String agent_Id;

    /** 申请房间名称 */
    private String roomName;

    /** 发起时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**审批完成结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 理由 */
    private String reason;

    /**开始使用时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useStartTime;
    /**使用结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useEndTime;


    /** 备注 */
    private String remark;

    /**回复内容*/
    private String reply;

    /** 状态 */
    private Integer status;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProcInstanId()
    {
        return procInstanId;
    }

    public void setProcInstanId(String procInstanId)
    {
        this.procInstanId = procInstanId;
    }

    public String getProposer_Id()
    {
        return proposer_Id;
    }

    public void setProposer_Id(String proposer_Id)
    {
        this.proposer_Id = proposer_Id;
    }

    public String getAgent_Id()
    {
        return agent_Id;
    }

    public void setAgent_Id(String agent_Id)
    {
        this.agent_Id = agent_Id;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
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

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public Date getUseStartTime()
    {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime)
    {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime()
    {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime)
    {
        this.useEndTime = useEndTime;
    }

    public String getReply()
    {
        return reply;
    }

    public void setReply(String reply)
    {
        this.reply = reply;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
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
        final StringBuffer sb = new StringBuffer("ApplyRoomForm{");
        sb.append("id='").append(id).append('\'');
        sb.append(", proposer_Id='").append(proposer_Id).append('\'');
        sb.append(", procInstanId='").append(procInstanId).append('\'');
        sb.append(", agent_Id='").append(agent_Id).append('\'');
        sb.append(", roomName='").append(roomName).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", reason='").append(reason).append('\'');
        sb.append(", useStartTime='").append(useStartTime).append('\'');
        sb.append(", useEndTime=").append(useEndTime);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", reply='").append(reply).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}