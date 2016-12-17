package com.venus.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by liuzy on 2016/12/17
 * @function CacheLoader
 */
public class CacheLoading {

    private static final Logger logger = Logger.getLogger(CacheCallable.class.getName());

    private static LoadingCache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(20)
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<Object, Object>() {
                @Override
                public Object load(Object key) throws Exception {
                    return key;
                }
            });

    public static Object get(Object key,Object value) throws ExecutionException {
        Object var = cache.get(key);
        if(var.equals(key)) {
            logger.info("执行其他操作,查询该值");
            /**执行其他操作，获取值**/
            put(key, value);
        }
        return cache.get(key);
    }

    public static void put(Object key, Object value) {
        cache.put(key, value);
    }

}