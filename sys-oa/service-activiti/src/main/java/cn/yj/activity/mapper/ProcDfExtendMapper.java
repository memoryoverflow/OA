package cn.yj.activity.mapper;

import cn.yj.activity.entity.po.ProcDfExtend;
import cn.yj.common.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:24
 */
public interface ProcDfExtendMapper<T extends ProcDfExtend> extends BaseMapper<T>
{

    @Select("select id,use_role as useRole,type,remark from act_re_procdef_extend where id=#{id}")
    @Override
    T selectById(Serializable id);
}
