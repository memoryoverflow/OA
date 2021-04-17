package cn.yj.admin.frame.shiro;

import cn.yj.admin.frame.shiro.config.FilterChainConfig;
import cn.yj.admin.frame.shiro.config.ShiroAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2021-04-02 09:53
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ShiroAutoConfig.class, FilterChainConfig.class})
public @interface EnableShiroManager
{
    /**
     * 是否启用redis
     *
     * @return
     */
    boolean enableRedisManager() default false;
}
