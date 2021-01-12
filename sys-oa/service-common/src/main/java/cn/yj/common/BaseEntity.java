package cn.yj.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-28 21:42
 */
public class BaseEntity
{
    private String id;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    private Date createTime;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    private Date updateTime;

    public String getId()
    {
        return id;
    }

    public BaseEntity setId(String id)
    {
        this.id = id;
        return this;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public BaseEntity setCreateTime(Date createTime)
    {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public BaseEntity setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
        return this;
    }
}
