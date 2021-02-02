package cn.yj.user.mapper;

import cn.yj.common.BaseMapper;
import cn.yj.user.entity.po.Position;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 11:54
 */
public interface PositionMapper extends BaseMapper<Position>
{
    @Select("select * from tb_position where position_name=#{name}")
    Position selectByName(String name);

    @Select("select * from tb_position where position_code=#{positionCode}")
    Position selectByCode(String positionCode);

    @Select("select id,position_name as name,position_code as code from tb_position")
    List<Map<String, String>> listIdAndNameCode();
}
