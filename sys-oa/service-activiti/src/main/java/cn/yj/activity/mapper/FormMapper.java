package cn.yj.activity.mapper;

import cn.yj.activity.entity.po.Form;
import cn.yj.common.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:24
 */
public interface FormMapper extends BaseMapper<Form>
{
    @Select("select * from tb_form where id=#{id}")
    @Override
    Form selectById(Serializable id);

    @Select("select * from tb_form where proc_def_id=#{procDefId}")
    Form selectByProcDefId(String procDefId);

}
