package cn.yj.user.entity.po;

import cn.yj.user.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:54
 */
public class Department extends BaseEntity implements Serializable
{
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private String deptCode;
    private String deptPhone;
    private String deptParentId;
    private String deptAddress;
    private String userId;
    private String remark;

    public String getDeptName()
    {
        return deptName;
    }

    public Department setDeptName(String deptName)
    {
        this.deptName = deptName;
        return this;
    }

    public String getDeptCode()
    {
        return deptCode;
    }

    public Department setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
        return this;
    }

    public String getDeptPhone()
    {
        return deptPhone;
    }

    public Department setDeptPhone(String deptPhone)
    {
        this.deptPhone = deptPhone;
        return this;
    }

    public String getDeptParentId()
    {
        return deptParentId;
    }

    public Department setDeptParentId(String deptParentId)
    {
        this.deptParentId = deptParentId;
        return this;
    }

    public String getDeptAddress()
    {
        return deptAddress;
    }

    public Department setDeptAddress(String deptAddress)
    {
        this.deptAddress = deptAddress;
        return this;
    }

    public String getUserId()
    {
        return userId;
    }

    public Department setUserId(String userId)
    {
        this.userId = userId;
        return this;
    }

    public String getRemark()
    {
        return remark;
    }

    public Department setRemark(String remark)
    {
        this.remark = remark;
        return this;
    }
}
