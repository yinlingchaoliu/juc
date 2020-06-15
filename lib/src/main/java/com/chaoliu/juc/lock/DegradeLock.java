package com.chaoliu.juc.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//锁降级
//读写锁不支持升级
//降级指把持住当前写锁，再获取读锁，随后释放先前拥有的写锁
public class DegradeLock {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    private volatile boolean update = true;

    public void processData() {

        readLock.lock();

        if (!update) {
            //先释放读锁
            readLock.unlock();
            //写锁 降级为读锁
            writeLock.lock();
            try {
                if (!update){
                    update = true;
                }
                //保证数据可见性
                readLock.lock();
            }finally {
                writeLock.lock();
            }
        }

        try {
            //todo 执行操作
        }finally {
            readLock.unlock();
        }

    }

    public void update(){
        writeLock.lock();
        try {
            update = false;
        }finally {
            writeLock.unlock();
        }
    }

}
