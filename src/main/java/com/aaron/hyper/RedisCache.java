package com.aaron.hyper;

import com.aaron.hyper.utils.ApplicationContextHolder;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by AARON on 2017/7/15.
 */
public class RedisCache implements Cache {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String instantId;

    private RedisTemplate redisTemplate;

    private static final int REDIS_EXPIRE_TIMEOUT_30 = 30;

    public RedisCache(String instantId) {

        if (instantId == null) {

            throw new IllegalArgumentException("Cache instances require an instantId");
        }
        this.instantId = instantId;
    }

    @Override
    public String getId() {

        return instantId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void putObject(Object key, Object value) {

        RedisTemplate redisTemplate = getRedisTemplate();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set(key, value, REDIS_EXPIRE_TIMEOUT_30, TimeUnit.MINUTES);

        logger.debug("Put Result Cache Into Redis");
    }

    @Override
    public Object getObject(Object key) {

        RedisTemplate redisTemplate = getRedisTemplate();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        logger.debug("Get Cached Result From Redis");

        return valueOperations.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object removeObject(Object key) {

        RedisTemplate redisTemplate = getRedisTemplate();

        redisTemplate.delete(key);

        return null;
    }

    @Override
    public void clear() {

        RedisTemplate redisTemplate = getRedisTemplate();

        redisTemplate.execute((RedisCallback) connection -> {

            connection.flushDb();

            return null;
        });

        logger.debug("Clear All Cached Data From Redis!");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate() {

        if (redisTemplate == null) {

            redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
