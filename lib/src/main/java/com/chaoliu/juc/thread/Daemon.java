package com.chaoliu.juc.thread;

public class Daemon {

    public static void main(String[] args){
        Thread thread = new Thread( new DaemonRunner(),"DaemonRunner" );
        thread.setDaemon( true );
        thread.start();
    }

    static class DaemonRunner implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep( 10_000L );
            }catch (Exception e){

            }finally {
                System.out.println("DaemonRunner is finish");
            }
        }
    }


}
