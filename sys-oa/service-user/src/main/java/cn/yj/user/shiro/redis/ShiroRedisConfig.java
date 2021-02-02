package cn.yj.user.shiro.redis;

import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-11-29 17:12
 */
@Configuration
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
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

}
