package com.chaoliu.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Lock rlock = rwlock.readLock();
    private Lock wlock = rwlock.writeLock();

    private int value = 12;

    public int getValue() {
        rlock.lock();
        try {
            return value;
        } finally {
            rlock.unlock();
        }
    }

    public void setValue(int value) {
        wlock.lock();
        try {
            this.value = value;
        } finally {
            wlock.unlock();
        }
    }

    public void reset(){
        wlock.lock();
        try {
            this.value = 0;
        } finally {
            wlock.unlock();
        }
    }

}
