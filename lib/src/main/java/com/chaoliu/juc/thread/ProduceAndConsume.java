package com.chaoliu.juc.thread;

/**
 * 生产者-消费者
 */
public class ProduceAndConsume {

    private Object lock = new Object();

    private volatile boolean flag = true;

    private volatile int i = 0;

    //生产者
    public void produce() {

        synchronized (lock) {

            //todo 代码执行逻辑

            i = i + 1;
            System.out.println( "produce: + 1 " + i );

            flag = false;
            lock.notifyAll();
        }

    }

    //消费者
    public void consume() {

        synchronized (lock) {

            while (flag) {
                try {
                    lock.wait(); //线程进入waitting状态, 会释放对象锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            flag = true;

            //todo 执行逻辑

            i = i - 1;
            System.out.println( "consume: -1 " + i );

        }

    }


    public void consumeWait(long mills) {

        synchronized (lock) {
            long future = System.currentTimeMillis() + mills;
            long remaining = mills;

            while (remaining > 0) {
                try {
                    lock.wait( remaining );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                remaining = future - System.currentTimeMillis();
            }

            //todo 执行代码


        }
    }

}
