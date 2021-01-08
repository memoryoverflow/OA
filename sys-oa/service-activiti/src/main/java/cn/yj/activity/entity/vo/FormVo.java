package cn.yj.activity.entity.vo;

import cn.yj.activity.entity.po.Form;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:28
 */
@Data
public class FormVo extends Form
{
    /**
     * id、name、code
     */
    private Map<String, String> agentUser;

}
