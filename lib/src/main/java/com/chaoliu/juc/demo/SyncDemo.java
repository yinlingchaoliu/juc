package com.chaoliu.juc.demo;

//锁的三种用法
public class SyncDemo {

    private Object lock = new Object();
    private static Object sLock = new Object();

    public synchronized void methodInstance() {
        System.out.println( "锁是当前实例对象" );
    }

    public synchronized static void methodClass() {
        System.out.println( "锁是当前类Class对象" );
    }

    public static void methodConfig() {
        synchronized (SyncDemo.class) {
            System.out.println( "锁是synchronized配置对象 - 当前类Class对象" );
        }
    }

    public static void methodConfig3() {
        synchronized (sLock) {
            System.out.println( "锁是synchronized配置对象" );
        }
    }

    public void methodConfig1() {
        synchronized (lock) {
            System.out.println( "锁是synchronized配置对象" );
        }
    }

    public void methodConfig2() {
        synchronized (this) {
            System.out.println( "锁是synchronized配置对象 - 当前实例对象" );
        }
    }

    public static void main(String[] args) {
        SyncDemo demo = new SyncDemo();

        //当前实例对象
        demo.methodInstance();
        demo.methodConfig2();
        System.out.println();

        //当前类Class对象
        SyncDemo.methodClass();
        SyncDemo.methodConfig();
        System.out.println();

        //配置对象
        SyncDemo.methodConfig3();
        demo.methodConfig1();
    }

}
