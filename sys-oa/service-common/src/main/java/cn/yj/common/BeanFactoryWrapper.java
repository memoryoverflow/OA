package cn.yj.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-07 14:31
 */
public abstract class BeanFactoryWrapper implements BeanFactoryAware
{
    private static BeanFactory factory;

    protected static BeanFactory getFactory()
    {
        return factory;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException
    {
        if (factory == null)
        {
            factory = beanFactory;
        }
    }
}
