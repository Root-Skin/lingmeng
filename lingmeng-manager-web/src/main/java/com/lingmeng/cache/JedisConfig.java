package com.lingmeng.cache;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.lingmeng.common.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis配置，项目启动注入JedisPool
 *
 * @author shiilu
 * @date 2018/9/5 10:35
 */
@Configuration
public class JedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisConfig.class);

    @Autowired
    private RedisConfig redisConfig;


    @Bean
    public JedisPool redisPoolFactory() {

        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(redisConfig.getPool().getMaxIdle());
            jedisPoolConfig.setMaxWaitMillis(redisConfig.getPool().getMaxWait());
            jedisPoolConfig.setMaxTotal(redisConfig.getPool().getMaxActive());
            jedisPoolConfig.setMinIdle(redisConfig.getPool().getMaxIdle());
            if (redisConfig.getDatabase() < 0 || redisConfig.getDatabase() > 15) {
                redisConfig.setDatabase(0);
            }

            JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPassword(), redisConfig.getDatabase());
            LOGGER.info("初始化Redis连接池JedisPool成功!地址: " + redisConfig.getHost() + ":" +  redisConfig.getPort() + " 数据库：" + redisConfig.getDatabase());
            return jedisPool;
        } catch (Exception e) {
            LOGGER.error("初始化Redis连接池JedisPool异常:" + e.getMessage());
        }
        return null;
    }
     /**
      * @Author skin
      * @Date  2021/1/6
      * @Description 在@Configuration下使用@Bean产生的是单例Bean(spring管理的)
      **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("com.lingmeng.");
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
