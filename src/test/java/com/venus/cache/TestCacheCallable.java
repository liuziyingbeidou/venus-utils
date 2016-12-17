package com.venus.cache;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzy on 2016/12/17 0017.
 */
public class TestCacheCallable {
    @Test
    public void CacheCallableTest() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 20; i++){
            Object consult = CacheCallable.get("test1","Test DB");

            System.out.println(consult);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void CacheLoaderTest() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 20; i++){
            Object consult = CacheLoading.get("tesst-0","database");
            System.out.println(consult);
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
