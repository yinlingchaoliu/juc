package com.chaoliu.juc.thread;

public class Profiler {

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<Long>();

    public static void begin(){
        TIME_THREAD_LOCAL.set( System.currentTimeMillis() );
    }

    public static long end(){
        return System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
    }

}
