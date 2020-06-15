package com.chaoliu.juc.lock;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写生产与消费者 用条件锁写的时候，避免计数过多
 *
 * @param <T>
 */
public class BoundedQueue<T> {

    private Object[] items;
    private Queue queue = new LinkedBlockingQueue();

    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    private int size = 10;

    public BoundedQueue(int size) {
        this.size = size;
    }

    public void add(T item) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[addIndex] = item;
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            ++count;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object item = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.signalAll();
            return (T) item;
        } finally {
            lock.unlock();
        }
    }

}
