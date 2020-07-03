package com.chaoliu.juc.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池常见用法
public class Pool {

    private ExecutorService fixedThreadPool;

    int corePoolSize = 10;
    int maximumPoolSize = Integer.MAX_VALUE;
    long keepAliveTime = 60;
    TimeUnit unit = TimeUnit.SECONDS;
    BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>( 10 );
    RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

    ThreadFactory threadFactory = Executors.defaultThreadFactory();

    void pool() {
        fixedThreadPool = new ThreadPoolExecutor( corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory, handler );
        ExecutorService fixedThreadPool = newFixedThreadPool( 10 );
        ExecutorService cachedThreadPool = newCachedThreadPool();
        ExecutorService singleThreadExecutor = newSingleThreadExecutor();
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool( 10 );
        int cpu = Runtime.getRuntime().availableProcessors();
    }


    //固定线程池
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor( nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>() );
    }

    //缓存线程池
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor( 0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>() );
    }


    //单个线程池
    public static ExecutorService newSingleThreadExecutor() {
        return new ThreadPoolExecutor( 1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>() );
    }

    //定时线程池
//    public static ExecutorService newScheduledThreadPoolExecutor(int corePoolSize) {
//        return new ThreadPoolExecutor( corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
//                new ScheduledThreadPoolExecutor.DelayedWorkQueue() );
//    }

}
