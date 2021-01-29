package cn.yj.core.notice;

import cn.yj.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 14:55
 */
@TableName("tb_notice")
public class Notice extends BaseEntity
{
    @NotBlank(message = "title is not null")
    private String title;

    @NotBlank(message = "content is not null")
    private String content;

    private String createBy;

    private Integer type;

    private String enclosure;

    public String getTitle()
    {
        return title;
    }

    public Notice setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getContent()
    {
        return content;
    }

    public Notice setContent(String content)
    {
        this.content = content;
        return this;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public Notice setCreateBy(String createBy)
    {
        this.createBy = createBy;
        return this;
    }

    public Integer getType()
    {
        return type;
    }

    public Notice setType(Integer type)
    {
        this.type = type;
        return this;
    }

    public String getEnclosure()
    {
        return enclosure;
    }

    public Notice setEnclosure(String enclosure)
    {
        this.enclosure = enclosure;
        return this;
    }
}
