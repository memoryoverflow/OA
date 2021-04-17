package cn.yj.admin.entity.vo;

import cn.yj.admin.entity.po.Position;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:56
 */
@Data
public class PositionVo extends Position implements Serializable
{
    private Map<String, Object> department;
}
