package com.chaoliu.juc.demo;

import com.chaoliu.juc.util.CasUtil;

import java.util.concurrent.atomic.AtomicInteger;

//原子操作与非线程安全计数器
public class CasCount {

    private AtomicInteger atomicI = new AtomicInteger( 0 );
    private int i = 0;
    private static final int LENGTH = 100;

    public static void main(String[] args) {

        final CasCount cas = new CasCount();

        //测试并发
        CasUtil.testThread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    cas.safeCount();
                    cas.unSafeCount();
                }
            }
        } );

        System.out.println( cas.getUnSafeCount() );
        System.out.println( cas.getSafeCount() );

    }

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
