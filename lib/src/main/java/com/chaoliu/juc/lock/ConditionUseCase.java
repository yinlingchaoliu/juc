package com.chaoliu.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//条件锁
public class ConditionUseCase {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    public void produce(){
        lock.lock();

        try {
            //唤醒所有
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }



    public void consume() throws InterruptedException {

        lock.lock();
        try {
            condition.wait();
        }finally {
            lock.unlock();
        }


    }



}
