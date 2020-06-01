package com.chaoliu.juc.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class CasExample {

    AtomicInteger atomicInteger = new AtomicInteger( 0 );

    public void set(int i) {
        atomicInteger.set( i );
    }

    public int get() {
        return atomicInteger.get();
    }

    public int getAndIncrement(){
        return atomicInteger.getAndIncrement();
    }

}
