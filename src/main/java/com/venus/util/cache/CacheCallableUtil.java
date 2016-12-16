package com.venus.util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzy
 * @description guava cache util
 */
public class CacheCallableUtil {

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
     * @param indexCallable 可以自定义 Callable配置switch；
     *                      eg:需要查询数据库 自定义DBCallable indexCallable 1
     * @return
     * @throws ExecutionException
     */
    public static Object getEntity(Object key,Object value,int... indexCallable) throws ExecutionException {
        Callable<Object> callable = initCallable(key,value);
        if(indexCallable.length > 0){
            switch (indexCallable[0]){
                case 0 : callable = initDBCallable(key);
            }
        }

        Object var = cache.get(key,callable);
        return var;
    }

    /**
     * default defind Callable
     * @param key
     * @param value
     * @return
     */
    private static Callable<Object> initCallable(final Object key, final Object value){
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println(key + " set " + value);
                return value;
            }
        };
    }

    /**
     * ext Callable is DBCallable
     * @serialData  indexCallable 0
     * @param key
     * @return
     */
    private static Callable<Object> initDBCallable(final Object key){
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println(key + " set ");
                return "DB";
            }
        };
    }

    @Test
    public void CacheTest() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 20; i++){
            Object consult = CacheCallableUtil.getEntity("test0","测试",0);
            System.out.println(consult);
            TimeUnit.SECONDS.sleep(1);
        }

    }
}