package com.chaoliu.juc.demo;

/**
 * 轻量级线程安全
 */
public class VolatileFeaturesExample {

    volatile long v1 = 0L;

    public void set(long l) {
        v1 = l;
    }

    //v1++ 不是原子操作
    //读写是原子操作，读+写不是
    public void getAndIncrement() {
        v1++;
    }

    public long get() {
        return v1;
    }

}
