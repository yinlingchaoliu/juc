package com.chaoliu.juc.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

//原子操作与非线程安全计数器
public class ABACasCount {

    private AtomicInteger atomicI = new AtomicInteger( 0 );
    private AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>( 0, 0 );

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

    //当版本好号超过128报错
    public void safeABACount() {

        for (;;){
            //当前值
            int ref = atomicStampedRef.getReference();
            //版本
            int stamp = atomicStampedRef.getStamp();
            boolean suc = atomicStampedRef.compareAndSet( ref, ++ref, stamp, stamp + 1 );
            System.out.println( suc + " ref = " + ref + " stamp = " + stamp );
            if (suc)
                break;
        }
    }

    public void unSafeCount() {
        i++;
    }

    public int getSafeCount() {
        return atomicI.get();
    }

    public int getUnSafeCount() {
        return i;
    }

    public int getSafeABACount() {
        return atomicStampedRef.getReference();
    }


}
