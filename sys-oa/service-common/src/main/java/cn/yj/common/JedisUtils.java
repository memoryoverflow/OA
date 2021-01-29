package cn.yj.common;

import cn.yj.tools.readconfig.PropertiesUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-05-26 14:15
 */
public class JedisUtils
{
    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static String DEFAULT_HOST = "127.0.0.1";

    private static String host;
    private static int port;
    private static int timeout;
    private static String password = "";
    private static int database;

    private static JedisPool jedisPool;

    static
    {
        host = PropertiesUtils.getStringValue("spring.redis.host", DEFAULT_HOST);
        port = PropertiesUtils.getIntValue("spring.redis.port", Protocol.DEFAULT_PORT);
        timeout = PropertiesUtils.getIntValue("spring.redis.timeout", Protocol.DEFAULT_TIMEOUT);
        password = PropertiesUtils.getStringValue("spring.redis.password", password);
        database = PropertiesUtils.getIntValue("spring.redis.database", Protocol.DEFAULT_DATABASE);
        init();
    }


    private static void init()
    {
        if (jedisPool == null)
        {
            synchronized (JedisUtils.class)
            {
                if (jedisPool == null)
                {
                    if (host.contains(":"))
                    {
                        String[] hostAndPort = host.split(":");
                        jedisPool = new JedisPool(getJedisPoolConfig(), hostAndPort[0], Integer.parseInt(hostAndPort[1]), timeout, password, database);
                    }
                    else
                    {
                        jedisPool = new JedisPool(getJedisPoolConfig(), host, port, timeout, password, database);
                    }
                }
            }
        }
    }

    public static void save(String key, String value, int expireSeconds)
    {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.expire(key, expireSeconds);
        jedis.close();
        logger.info("保存 key,{}", key);
    }

    public static void remove(String key)
    {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
        logger.info("删除 key,{}", key);
    }

    public static String get(String key)
    {
        logger.info("获取 key,{}", key);
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public static void setObject(String key, Object value)
    {
        Jedis jedis = getJedis();
        jedis.set(key, JSON.toJSONString(value));
        jedis.close();
        logger.info("保存 key,{}", key);
    }

    public static <T> T getObject(String key, Class<T> tClass)
    {
        logger.info("获取 key,{}", key);
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value == null ? null : JSON.parseObject(value, tClass);
    }


    public static Jedis getJedis()
    {
        if (jedisPool == null)
        {
            init();
        }
        return jedisPool.getResource();
    }

    private static void close(Jedis jedis)
    {
        if (!jedis.isConnected())
        {
            jedis.close();
        }
    }

    private static JedisPoolConfig getJedisPoolConfig()
    {
        return new JedisPoolConfig();
    }

}
