package com.chaoliu.juc.util;

import com.chaoliu.juc.demo.CasCount;

import java.util.ArrayList;
import java.util.List;

public class CasUtil {


    //todo 测试线程安全
    public static void testThread(final Runnable runnable) {

        int LENGTH = 10;

        final CasCount cas = new CasCount();
        List<Thread> threads = new ArrayList<Thread>( LENGTH );

        for (int j = 0; j < LENGTH; j++) {
            Thread thread = new Thread( runnable );
            threads.add( thread );
        }

        for (Thread t : threads) {
            t.start();
        }

        //等待所有线程执行完毕
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
