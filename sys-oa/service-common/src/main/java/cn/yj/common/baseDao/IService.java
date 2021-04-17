package cn.yj.common.baseDao;

import cn.yj.annotation.pagehelper.page.Page;

import java.io.Serializable;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 12:04
 */
public interface IService<T>
{
    /**
     * 添加
     *
     * @param t
     * @return
     */
    boolean insert(T t);

    /**
     * 修改
     *
     * @param t
     * @return
     */
    boolean updateById(T t);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean removeByIds(Serializable[] ids);

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    T selectById(Serializable id);

    /**
     * 分页
     *
     * @param params
     * @param page
     * @return
     */
    Page<T> findList(Map<String, Object> params, Page<T> page);
}
