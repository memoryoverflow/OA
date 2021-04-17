package cn.yj.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 增删改自动更新 update_time
 * </p>
 *
 * @author 永健
 * @since 2020-05-29 10:41:28
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);


    @Override
    public void insertFill(MetaObject metaObject)
    {
        this.setInsertFieldValByName("createTime", new Date(), metaObject);
        this.setInsertFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject)
    {
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }
}
