package cn.yj.common.baseDao;

import cn.yj.annotation.pagehelper.StartPage;
import cn.yj.annotation.pagehelper.page.Page;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 12:04
 */
public interface BaseMapper<T>
{
    int insert(T t);

    int updateById(T t);

    int removeByIds(Serializable[] ids);

    T selectById(Serializable id);

    @StartPage
    List<T> findList(@Param("map") Map<String, Object> params, Page<T> page);
}
