package com.chaoliu.juc.ctl;

import java.util.concurrent.CountDownLatch;

//等待其他线程完成
//倒数计数
//计数器只能使用1次 系统启动
public class CountDownLatchTest {

    private static CountDownLatch latch = new CountDownLatch( 2 );

    public static void main(String[] args) throws InterruptedException {

        new Thread( new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                System.out.println("1");

            }
        } ).start();


        new Thread( new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                System.out.println("2");
            }
        } ).start();

        latch.await();
        System.out.println("3");
    }

//    join实现
//    void join(){
//        while (isActive()){
//            wait(0);
//        }
//    }

}
