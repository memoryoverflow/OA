package cn.yj.core.notice;

import cn.yj.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.criteria.CriteriaBuilder;
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
    public static Integer POWER_NONE = 1;
    public static Integer POWER_ROLE = 2;
    public static Integer POWER_DEPT = 3;
    public static Integer POWER_USER = 4;

    @NotBlank(message = "title is not null")
    private String title;

    @NotBlank(message = "content is not null")
    private String content;

    private String createBy;

    private Integer type;

    private String enclosure;

    /**
     * 权限类型 1：无
     * 2：角色
     * 3：部门
     * 4:指定用户
     */
    private Integer powerType;

    /**
     * 角色code
     * 部门code
     * 用户code
     */
    private String powerCodes;

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

    public Integer getPowerType()
    {
        return powerType;
    }

    public Notice setPowerType(Integer powerType)
    {
        this.powerType = powerType;
        return this;
    }

    public String getPowerCodes()
    {
        return powerCodes;
    }

    public Notice setPowerCodes(String powerCodes)
    {
        this.powerCodes = powerCodes;
        return this;
    }
}
