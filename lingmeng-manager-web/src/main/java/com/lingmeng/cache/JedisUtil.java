package com.lingmeng.cache;

import com.lingmeng.constant.cache.Constant;
import com.lingmeng.exception.RestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Author skin
 * @Date 2021/1/6
 * @Description JedisUtil(推荐存Byte数组 ， 存Json字符串效率更慢)
 **/
@Component
public class JedisUtil {

    /**
     * 静态注入JedisPool连接池
     * 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
     * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
     * https://blog.csdn.net/W_Z_W_888/article/details/79979103
     */
    private static JedisPool jedisPool;


    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    /**
     * @Author skin
     * @Date 2021/1/6
     * @Description 获取实列
     **/
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RestException("获取Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * 释放Jedis资源
     *
     * @param
     * @return void
     * @author shililu
     * @date 2018/9/5 9:16
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new RestException("释放Jedis资源异常:" + e.getMessage());
        }
    }

    /**
     * @Author skin
     * @Date 2021/1/6
     * @Description get(key)
     **/
    public static Object getObject(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(key.getBytes());
            if (bytes != null) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            return "";
        }
        return null;
    }

    /**
     * @Author skin
     * @Date 2021/1/6
     * @Description 设置键值对
     **/
    public static String setObject(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams param = new SetParams();
            param.nx();
            param.px(expireTime);
            String result = jedis.set(lockKey, requestId, param);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * @Author skin
     * @Date 2021/1/6
     * @Description key-value(并带有时间)
     **/
    public static String setObject(String key, Object value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (Constant.OK.equals(result)) {
                jedis.expire(key.getBytes(), expiretime);
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取redis键值-Json
     *
     * @param key
     * @return java.lang.Object
     * @author shililu
     * @date 2018/9/4 15:47
     */
    public static String getJson(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            return "";
        }
    }


    public static String setJson(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            return "";
        }
    }


    public static String setJson(String key, String value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key, value);
            if (Constant.OK.equals(result)) {
                jedis.expire(key, expiretime);
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }


    public static Long delKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            return null;
        }
    }


    public static Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            return false;

        }
    }


    /**
     * 枷锁
     *
     * @param key
     * @param expire
     * @return
     */
    public static boolean setnx(String key, int expire) {

        try (Jedis jedis = jedisPool.getResource()) {
            long status = jedis.setnx(key, "1");
            if (status == 1) {
                jedis.expire(key, expire);
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @Author skin
     * @Date 2021/1/6
     * @Description 删除缓存
     **/
    public static void delByKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            throw new RestException("delByKey异常:key=" + key + " cause=" + e.getMessage());
        }
    }


    public static void setList(String key, String data) {

        if (StringUtils.isNotEmpty(data)) {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.lpush(key, data);
            } catch (Exception e) {
                throw new RestException("添加集合异常:key" + key + "cause=" + e.getMessage());
            }
        }
    }

    public static List<String> getList(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            throw new RestException("获取集合异常:key" + key + "cause=" + e.getMessage());
        }
    }


    public static Set<String> getAllKeys() {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys("*");
        } catch (Exception e) {
            throw new RestException("获取所有key异常" + "cause=" + e.getMessage());
        }
    }

}
