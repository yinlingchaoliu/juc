package com.chaoliu.juc.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 面试的时候，有人问，三个任务，前两个执行完了，才执行第三个，问怎么写代码
 * 按照顺序执行
 * @author chentong
 *
 */
public class TaskWaitLock implements Task{

    public ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock ();

    public TaskLock tasks = null;

    public void addTask(TaskLock taskLock){

        if (taskLock == null) return;

        rwlock.writeLock().tryLock();
        try {
            if (tasks == null){
                tasks = taskLock;
                tasks.next = null;
            }else{
                TaskLock task = tasks.next;
                while(task!=null){
                    if (task.next == null){
                        task.next = taskLock;
                        task.next = null;
                        break;
                    }
                     task = task.next;
                }
            }
        }finally {
            rwlock.writeLock().unlock();
        }
    }

    @Override
    public void action() {

        rwlock.readLock().tryLock();
        try {

            if (tasks == null) {
                return;
            }

            TaskLock task = tasks;
            while (task!=null){
                task.action();
                task = task.next;
            }

        }finally {
            rwlock.readLock().unlock();
        }

    }

}

class TaskLock implements Task {

    public ReentrantLock lock = new ReentrantLock();

    public TaskLock next;

    public Task task;

    public void setTask(Task task) {
        this.task = task;
    }

    public ReentrantLock lock(){
        return lock;
    }

    @Override
    public void action() {
        lock.tryLock();
        try {
            task.action();
        }finally {
            lock.unlock();
        }
    }
}

interface Task {
    void action();
}