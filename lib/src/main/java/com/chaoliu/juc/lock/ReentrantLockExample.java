package com.chaoliu.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private int i = 0;
    private ReentrantLock lock = new ReentrantLock();

    public void writer() {
        lock.tryLock();
        try {
            i++;
        } finally {
            lock.unlock();
        }
    }

    public int reader() {
        lock.tryLock();
        try {
            return i;
        } finally {
            lock.unlock();
        }
    }

}
