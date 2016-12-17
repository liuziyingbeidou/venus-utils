package com.venus.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author liuzy
 * @function guava cache
 */
public class CacheCallable {

    private static final Logger logger = Logger.getLogger(CacheCallable.class.getName());

    static Callable<Object> initCallable;

    /**
     * make constructor private and update tests to create Callable<Object>
     */
    CacheCallable() {}


    /**
     * init build Cache
     * default cache expire 30min after
     * more config params
     */
    private static Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(100).expireAfterWrite(30,TimeUnit.MINUTES)
            .recordStats()
            .build();

    /**
     * get<K,V,indexCallable>
     * @param key
     * @param value
     * @param defCallable 自定义Callable ;默认 defaultCallable
     * @return
     * @throws ExecutionException
     */
    public static Object get(Object key,Object value,Callable<Object>... defCallable) throws ExecutionException {
        initCallable = defaultCallable(key,value);
        if(defCallable.length > 0){
            initCallable = defCallable[0];
        }
        Object var = cache.get(key,initCallable);
        return var;
    }

    /**
     * default defind Callable
     * @param key
     * @param value
     * @return
     */
    private static Callable<Object> defaultCallable(final Object key, final Object value){
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                logger.info(key + " set " + value);
                return value;
            }
        };
    }
}