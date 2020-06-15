package com.chaoliu.juc.ctl;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//公用有限资源限制
//一次性只能有限资源并行执行
public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool( THREAD_COUNT );

    private static Semaphore semaphore = new Semaphore( 10 );


    public static void main(String[] args){

        for (int i = 0; i<THREAD_COUNT;i++){

            threadPool.execute( new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("----work----");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } );
        }

        threadPool.shutdown();
    }

}
