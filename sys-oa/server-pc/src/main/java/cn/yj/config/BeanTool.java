package cn.yj.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-06-15 21:30
 */
@Component
public class BeanTool implements ApplicationContextAware
{
    private static ApplicationContext act;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        act = applicationContext;
    }

    public static  <T> T getBean(Class<T> tClass){
        return act.getBean(tClass);
    }
}
