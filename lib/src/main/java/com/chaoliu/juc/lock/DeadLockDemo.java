package com.chaoliu.juc.lock;

//测试死锁
public class DeadLockDemo {

    private Object A = new Object();
    private Object B = new Object();

    private Runnable run1 = new Runnable() {
        @Override
        public void run() {
            synchronized (A){
                try {
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println("1");
                }
            }
        }
    };

    private Runnable run2 = new Runnable() {
        @Override
        public void run() {

            synchronized (B){
                synchronized (A){
                    System.out.println("2");
                }
            }
        }
    };

    public static void main(String[] args){
        new DeadLockDemo().deadLock();
    }

    private void deadLock(){
        new Thread( run1 ).start();
        new Thread( run2 ).start();
    }

}
