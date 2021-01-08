package cn.yj.activity.entity.po;


import cn.yj.common.BaseEntity;
import cn.yj.common.StringToDateDeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <br>
 * 表单
 *
 * @author 永健
 * @since 2020-12-11 11:19
 */
@Data
public class Form extends BaseEntity implements Serializable
{
    @NotBlank(message = "名称不可空")
    private String formName;

    @NotBlank(message = "内容不可空")
    private String formContent;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    private Date startTime;

    @JsonDeserialize(using = StringToDateDeSerializer.class)
    private Date endTime;

    private String createUserId;
    private String procInstId;
    private String procDefId;
    private Integer status;
    private String reply;
    private String agentUserCode;

}
