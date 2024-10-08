package com.devkk.micro.framework.cache;

import com.devkk.micro.common.CacheModuleEnum;
import com.devkk.micro.config.CacheConfig;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author zhongkunming
 */
public abstract class CacheService {

    private final CacheConfig config;

    protected CacheService(CacheConfig config) {
        this.config = config;
    }

    protected String setupKey(CacheModuleEnum module, String key) {
        module = Objects.isNull(module) ? CacheModuleEnum.DEFAULT : module;
        return config.getPrefix() + ":" + module.getValue() + ":" + key;
    }

    public <R> R get(String key) {
        return get(null, key, null);
    }

    public <R> R get(String key, Supplier<R> supplier) {
        return get(null, key, supplier);
    }

    public abstract <R> R get(CacheModuleEnum module, String key);

    public <R> R get(CacheModuleEnum module, String key, Supplier<R> supplier) {
        R r = get(module, key);
        if (Objects.isNull(r) && Objects.nonNull(supplier)) {
            r = supplier.get();
            set(module, key, r);
        }
        return r;
    }


    public void set(String key, Object value) {
        set(null, key, value, config.getTimeout());
    }

    public void set(String key, Object value, Integer timeout) {
        set(null, key, value, timeout);
    }

    public void set(CacheModuleEnum module, String key, Object value) {
        set(module, key, value, config.getTimeout());
    }

    public abstract void set(CacheModuleEnum module, String key, Object value, Integer timeout);


    public void delete(String key) {
        delete(null, key);
    }

    public abstract void delete(CacheModuleEnum module, String key);

    public abstract void deletePattern(CacheModuleEnum module, String patten);


    public boolean exists(String key) {
        return exists(null, key);
    }

    public abstract boolean exists(CacheModuleEnum module, String key);


    public void ttl(String key) {
        ttl(null, key, config.getTimeout());
    }

    public void ttl(String key, Integer timeout) {
        ttl(null, key, timeout);
    }

    public void ttl(CacheModuleEnum module, String key) {
        ttl(module, key, config.getTimeout());
    }

    public abstract void ttl(CacheModuleEnum module, String key, Integer timeout);
}
