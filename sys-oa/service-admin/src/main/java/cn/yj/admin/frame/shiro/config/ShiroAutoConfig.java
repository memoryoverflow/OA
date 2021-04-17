package cn.yj.admin.frame.shiro.config;

import cn.yj.admin.frame.shiro.EnableShiroManager;
import cn.yj.admin.frame.shiro.reaml.ShiroRealm;
import cn.yj.admin.frame.shiro.redis.ShiroRedisConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-04-02 09:56
 */
public class ShiroAutoConfig implements ImportSelector
{

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata)
    {
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableShiroManager.class.getName());
        boolean enableRedisManager = Boolean.parseBoolean(annotationAttributes.get("enableRedisManager").toString());
        if (enableRedisManager){
            return new String[]{ShiroRedisConfig.class.getName(), ShiroConfig.class.getName(), ShiroRealm.class.getName()};
        }

        return new String[]{ShiroDefaultWebSessionManagerConfig.class.getName(),ShiroConfig.class.getName(),ShiroRealm.class.getName()};
    }
}
