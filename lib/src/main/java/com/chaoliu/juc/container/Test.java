package com.chaoliu.juc.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
    //分段锁技术
    ConcurrentHashMap map = new ConcurrentHashMap(  );

    //cas算法
    ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue(  );

    //block

}
