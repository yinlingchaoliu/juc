package com.chaoliu.juc.ctl;


import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//同步等待 交换者
//线程数据交换
//AB数据校对
public class ExchangerTest {

    private static Exchanger<String> exchanger = new Exchanger<String>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool( 2 );

    public static void main(String[] args){

        threadPool.execute( new Runnable() {
            @Override
            public void run() {
                String strB = "this is bank B";
                try {
                    String strA = exchanger.exchange( strB);
                    System.out.println("threadPool-1 equal "+strA.equals( strB ));
                    System.out.println("threadPool-1 A:"+strA);
                    System.out.println("threadPool-1 B:"+strB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } );

        threadPool.execute( new Runnable() {
            @Override
            public void run() {

                String strA = "this is bank A";
                try {
                   String strB =  exchanger.exchange( strA );
                    System.out.println("threadPool-2 equal "+strA.equals( strB ));
                    System.out.println("threadPool-2 A:"+strA);
                    System.out.println("threadPool-2 B:"+strB);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } );

    }

}
