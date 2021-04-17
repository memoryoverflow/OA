package cn.yj.admin.frame.shiro.redis;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 17:12
 */
@EnableConfigurationProperties(RedisProperties.class)
public class ShiroRedisConfig
{

    @Resource
    private RedisProperties redisProperties;


    @Bean
    public RedisCacheManager cacheManager()
    {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean("sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager(RedisCacheManager redisCacheManager, RedisSessionDAO redisSessionDAO)
    {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO);
        defaultWebSessionManager.setCacheManager(redisCacheManager);
        return defaultWebSessionManager;
    }


    public RedisManager redisManager()
    {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHost());
        redisManager.setPort(redisProperties.getPort());
        redisManager.setTimeout(Integer.valueOf(String.valueOf(redisProperties.getTimeout().getSeconds())));
        redisManager.setDatabase(redisProperties.getDatabase());
        redisManager.setPassword(redisProperties.getPassword());
        return redisManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO()
    {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

}
