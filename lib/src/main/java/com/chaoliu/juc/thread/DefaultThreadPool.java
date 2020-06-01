package com.chaoliu.juc.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池技术
 * @author chentong
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUM = 10;
    private static final int DEFAULT_WORKER_NUM = 5;
    private static final int MIN_WORKER_NUM = 1;

    private final List<Worker> workers = Collections.synchronizedList( new ArrayList<Worker>() );

    private final Object lock = new Object();

    private volatile AtomicInteger workerNum = new AtomicInteger( 0 );

    private final JobQueue<Job> queue = new JobQueue<>();

    public DefaultThreadPool() {
        addWorkers( DEFAULT_WORKER_NUM );
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            queue.enqueue( job );
        }
    }

    @Override
    public void addWorkers(int num) {

        synchronized (lock) {
            if (num + this.workerNum.get() > MAX_WORKER_NUM) {
                num = MAX_WORKER_NUM - this.workerNum.get();
                if (num <= 0) return;
            }

            for (int i = 0; i < num; i++) {
                Worker worker = new Worker( queue );
                workers.add( worker );
                Thread thread = new Thread( worker, "ThreadPool-Worker-" + workerNum.incrementAndGet() );
                thread.start();
            }
        }

    }

    @Override
    public void removeWorker(int num) {

        synchronized (lock) {
            //线程池最小线程数
            if (num >= this.workerNum.get()) {
                num = this.workerNum.get() - MIN_WORKER_NUM;
                if (num <= 0) return;
            }

            int count = 0;

            while (count < num) {

                Worker worker = workers.get( count );
                if (workers.remove( worker )) {
                    worker.shutdown();
                    count++;
                }
            }
            //减少线程
            workerNum.getAndAdd( -num );
        }

    }

    @Override
    public void shutdown() {
        synchronized (lock){
            for (Worker worker : workers) {
                worker.shutdown();
            }
            workers.clear();
        }
    }

    @Override
    public int getJobSize() {
        return queue.getJobSize();
    }

}

class Worker implements Runnable {

    private volatile boolean running = true;

    private JobQueue queue;

    public Worker(JobQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            Runnable job = queue.dequeue();
            if (job != null) {
                try {
                    job.run();
                } catch (Exception e) {
                }
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}

//工作队列
class JobQueue<Job extends Runnable> {

    private final LinkedList<Job> jobs = new LinkedList<>();

    private final Object lock = new Object();

    public void enqueue(Job job) {
        synchronized (lock) {
            lock.notifyAll();
            jobs.addLast( job );
        }
    }

    public Job dequeue() {

        synchronized (lock) {
            while (jobs.isEmpty()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Job job = jobs.removeFirst();
            return job;
        }
    }

    public int getJobSize() {
        synchronized (lock) {
            return jobs.size();
        }
    }

}

//线程池
interface ThreadPool<Job extends Runnable> {

    //执行job
    void execute(Job job);

    //关闭连接池
    void shutdown();

    //增加works
    void addWorkers(int num);

    //减少工作线程
    void removeWorker(int num);

    int getJobSize();

}

