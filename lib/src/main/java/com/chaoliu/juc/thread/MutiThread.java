package com.chaoliu.juc.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

//线程信息打印
public class MutiThread {

    //[4] Signal Dispatcher // dispatcher -> jvm
    //[3] Finalizer         // call finalizer method
    //[2] Reference Handler // clear refHandler
    //[1] main              // call main method
    public static void main(String[] arg) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads( false, false );
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println( "["+threadInfo.getThreadId() + "] " + threadInfo.getThreadName() );
        }
    }


}
