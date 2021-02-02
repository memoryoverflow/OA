package cn.yj.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-28 21:42
 */
public class BaseEntity implements Serializable
{
    @TableId(type = IdType.UUID)
    private String id;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
