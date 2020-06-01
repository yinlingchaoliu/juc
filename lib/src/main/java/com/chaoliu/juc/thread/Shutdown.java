package com.chaoliu.juc.thread;

/**
 * 优雅退出线程
 */
public class Shutdown {



    static class Runner implements Runnable {

        private volatile boolean on = true;

        @Override
        public void run() {

            //中断退出
            while (on && !Thread.currentThread().isInterrupted()){ //循环判断
                // todo  working
            }

        }

        //关闭线程
        public void cancel(){
            on = false;
        }
    }
}
