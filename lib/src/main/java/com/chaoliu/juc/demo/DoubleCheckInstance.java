package com.chaoliu.juc.demo;

public class DoubleCheckInstance {
    private volatile static DoubleCheckInstance instance;

    private DoubleCheckInstance() {
    }

    public static DoubleCheckInstance getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckInstance.class) {
                if (instance == null) {
                    instance = new DoubleCheckInstance();
                }
            }
        }
        return instance;
    }

}
