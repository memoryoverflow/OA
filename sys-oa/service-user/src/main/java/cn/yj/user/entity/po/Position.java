package cn.yj.user.entity.po;

import cn.yj.common.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:54
 */
@Data
public class Position extends BaseEntity implements Serializable
{
    @NotBlank(message = "岗位名称不能为空")
    private String positionName;

    @NotBlank(message = "岗位编码不能为空")
    private String positionCode;
    private String remark;

}
