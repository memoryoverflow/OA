package cn.yj.common.baseDao;

import cn.yj.annotation.pagehelper.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-30 12:48
 */
public abstract class ServiceImpl<M extends BaseMapper, T> implements IService<T>
{
    @Autowired
    protected M baseMapper;

    public M getBaseMapper()
    {
        return baseMapper;
    }

    @Override
    public boolean insert(T t)
    {
        return baseMapper.insert(t) > 0;
    }

    @Override
    public boolean updateById(T t)
    {
        return baseMapper.updateById(t) > 0;
    }

    @Override
    public boolean removeByIds(Serializable[] ids)
    {
        return baseMapper.removeByIds(ids) > 0;
    }

    @Override
    public T selectById(Serializable id)
    {
        return (T) baseMapper.selectById(id);
    }

    @Override
    public Page<T> findList(Map<String, Object> params, Page<T> page)
    {
        baseMapper.findList(params, page);
        return page;
    }
}
