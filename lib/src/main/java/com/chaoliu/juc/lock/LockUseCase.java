package com.chaoliu.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockUseCase {

    private Lock lock = new ReentrantLock();

    //1. 尝试非阻塞地获取锁
    //2. 能被中断地获取锁
    //3. 超时获取锁
    public void testLock() {
        lock.lock();
        try {
            // todo work
        } finally {
            lock.unlock();
        }
    }

}
