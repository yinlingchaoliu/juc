package com.chaoliu.juc.ctl;

import java.util.concurrent.CyclicBarrier;

//循环计数
// reset重置次数 ，重复使用
public class CyclicBarrierTest {


    public static void main(String[] args) {
        testBarrierAction();
    }

    static void testBarrier() {
        final CyclicBarrier barrier = new CyclicBarrier( 2 );
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println( 1 );
            }
        } ).start();

        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println( 2 );
            }
        } ).start();
    }


    static void testBarrierAction() {
        final CyclicBarrier barrier = new CyclicBarrier( 2, new BarrierAction() );
        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println( 1 );
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } ).start();

        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println( 2 );
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } ).start();
    }


    static class BarrierAction implements Runnable {
        @Override
        public void run() {
            System.out.println( "======BarrierAction=======" );
        }
    }


}
