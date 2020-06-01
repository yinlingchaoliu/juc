package com.chaoliu.juc.demo;

import java.util.concurrent.atomic.AtomicInteger;

//原子操作与非线程安全计数器
public class CasCount {

    private AtomicInteger atomicI = new AtomicInteger( 0 );
    private int i = 0;

    public void safeCount() {
        for (; ; ) {
            int i = atomicI.get();
            //cas  期望值  更新值
            boolean suc = atomicI.compareAndSet( i, ++i );
            if (suc)
                break;
        }
    }

    public int getSafeCount() {
        return atomicI.get();
    }

    public void unSafeCount() {
        i++;
    }

    public int getUnSafeCount() {
        return i;
    }

}
