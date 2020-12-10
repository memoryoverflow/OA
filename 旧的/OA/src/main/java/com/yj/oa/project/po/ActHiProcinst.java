package com.yj.oa.project.po;

import com.yj.oa.framework.web.po.BasePo;

import java.util.Date;

/**
 * @author 永健
 *
 * @描述  实例进程，一个请假一个实例
 *
 * @date 2018/9/25 14:22
 */
public class ActHiProcinst extends BasePo{

    private String id;

    private String procInstId;

    private String businessKey;

    private String procDefId;

    private Date startTime;

    private Date endTime;

    private Long duration;

    private String startUserId;

    private String startActId;

    private String endActId;

    private String superProcessInstanceId;

    private String deleteReason;

    private String tenantId;

    private String name;

    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey == null ? null : businessKey.trim();
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId == null ? null : procDefId.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId == null ? null : startUserId.trim();
    }

    public String getStartActId() {
        return startActId;
    }

    public void setStartActId(String startActId) {
        this.startActId = startActId == null ? null : startActId.trim();
    }

    public String getEndActId() {
        return endActId;
    }

    public void setEndActId(String endActId) {
        this.endActId = endActId == null ? null : endActId.trim();
    }

    public String getSuperProcessInstanceId() {
        return superProcessInstanceId;
    }

    public void setSuperProcessInstanceId(String superProcessInstanceId) {
        this.superProcessInstanceId = superProcessInstanceId == null ? null : superProcessInstanceId.trim();
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason == null ? null : deleteReason.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("ActHiProcinst{");
        sb.append("id='").append(id).append('\'');
        sb.append(", procInstId='").append(procInstId).append('\'');
        sb.append(", businessKey='").append(businessKey).append('\'');
        sb.append(", procDefId='").append(procDefId).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", duration=").append(duration);
        sb.append(", startUserId='").append(startUserId).append('\'');
        sb.append(", startActId='").append(startActId).append('\'');
        sb.append(", endActId='").append(endActId).append('\'');
        sb.append(", superProcessInstanceId='").append(superProcessInstanceId).append('\'');
        sb.append(", deleteReason='").append(deleteReason).append('\'');
        sb.append(", tenantId='").append(tenantId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}