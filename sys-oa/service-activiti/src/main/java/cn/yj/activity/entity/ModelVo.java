package cn.yj.activity.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 16:27
 */
@Data
public class ModelVo
{
    /**
     * 模型名称
     */
    @NotBlank(message = "模型名称不能为空")
    private String modelName;

    /**
     * 模型说明
     */
    private String modelDesc;

    /**
     * model key
     */
    @NotBlank(message = "模型Key不能为空")
    private String modelKey;

    public ModelVo()
    {
    }

    public ModelVo(String modelName, String modelDesc, String modelKey)
    {
        this.modelName = modelName;
        this.modelDesc = modelDesc;
        this.modelKey = modelKey;
    }
}
