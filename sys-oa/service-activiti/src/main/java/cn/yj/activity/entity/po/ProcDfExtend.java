package cn.yj.activity.entity.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 16:16
 */
@Data
public class ProcDfExtend implements Serializable
{
    @NotBlank(message = "id 不允许为空")
    private String id;

    @NotBlank(message = "所属类型不允许为空")
    private String type;

    private String useRole;

    private String remark;


}
