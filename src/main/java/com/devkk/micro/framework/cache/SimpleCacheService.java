package com.devkk.micro.framework.cache;

import com.devkk.micro.common.CacheModuleEnum;
import com.devkk.micro.config.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongkunming
 */
@Component
public class SimpleCacheService extends CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public SimpleCacheService(CacheConfig config, RedisTemplate<String, Object> redisTemplate) {
        super(config);
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <R> R get(CacheModuleEnum module, String key) {
        key = setupKey(module, key);
        return (R) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(CacheModuleEnum module, String key, Object value, Integer timeout) {
        key = setupKey(module, key);
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }


    @Override
    public void delete(CacheModuleEnum module, String key) {
        key = setupKey(module, key);
        redisTemplate.delete(key);
    }

    @Override
    public void deletePattern(CacheModuleEnum module, String patten) {
        String keyPatten = setupKey(module, patten);
        Set<String> keys = redisTemplate.keys(keyPatten);
        if (Objects.isNull(keys)) {
            return;
        }
        redisTemplate.delete(keys);
    }

    @Override
    public boolean exists(CacheModuleEnum module, String key) {
        key = setupKey(module, key);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void ttl(CacheModuleEnum module, String key, Integer timeout) {
        key = setupKey(module, key);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
        }
    }
}
