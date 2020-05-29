package com.chaoliu.juc.demo;

public class SyncDemo {

    Object A = new Object();

    public synchronized void method() {
        System.out.println( "锁是当前实例对象" );
    }

    public synchronized static void method1() {
        System.out.println( "锁是当前类Class" );
    }

    public void method2() {
        synchronized (A) {
            System.out.println( "锁是synchronized配置对象" );
        }
    }

    public static void main(String[] args){
        SyncDemo demo = new SyncDemo();
        demo.method();
        demo.method2();
        SyncDemo.method1();
    }

}
