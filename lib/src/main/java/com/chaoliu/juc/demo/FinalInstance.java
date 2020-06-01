package com.chaoliu.juc.demo;

public class FinalInstance {

    private FinalInstance(){

    }

    private static class Holder{
        static final FinalInstance instance = new FinalInstance();
    }

    public static FinalInstance getInstance(){
        return Holder.instance;
    }

}
