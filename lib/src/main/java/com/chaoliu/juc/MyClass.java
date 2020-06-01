package com.chaoliu.juc;

import com.chaoliu.juc.demo.ABA;
import com.chaoliu.juc.demo.ABACasCount;
import com.chaoliu.juc.demo.CasCount;
import com.chaoliu.juc.demo.CasExample;
import com.chaoliu.juc.demo.VolatileFeaturesExample;
import com.chaoliu.juc.thread.Piped;
import com.chaoliu.juc.thread.ProduceAndConsume;
import com.chaoliu.juc.thread.Profiler;
import com.chaoliu.juc.util.TestUtil;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.chaoliu.juc.util.TestUtil.getArgs;
import static com.chaoliu.juc.util.TestUtil.testThread;

/**
 * 单元测试类
 *
 * @author chentong
 * @desc
 */
public class MyClass {

//    public static void main(String[] args) {
//        //测试管道
//        testPipe();
//    }

    @Test
    public void testHelloWorld() {
        System.out.println( "hello world" );
    }

    @Test
    public void testCount() {
        final CasCount cas = new CasCount();

        //测试并发
        testThread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    cas.unSafeCount();
                }
            }
        } );

        System.out.println( cas.getUnSafeCount() );
    }


    @Test
    public void testCasCount() {

        final CasCount cas = new CasCount();

        //测试并发
        testThread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    cas.safeCount();
                }
            }
        } );

        System.out.println( cas.getSafeCount() );

    }

    @Test
    public void testABA() {
        ABA.main( getArgs() );
    }

    @Test
    public void testABACount() {
        final ABACasCount cas = new ABACasCount();

        //测试并发
        testThread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    cas.safeCount();
                    cas.unSafeCount();
                }
            }
        } );

        System.out.println( cas.getUnSafeCount() );
        System.out.println( cas.getSafeCount() );

        //测试ABA
        testThread( new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    cas.safeABACount();
                }
            }
        } );

        System.out.println( cas.getSafeABACount() );
    }


    @Test
    public void testVolatile() {

        final VolatileFeaturesExample example = new VolatileFeaturesExample();

        testThread( new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10000; i++) {
                    example.getAndIncrement();
                }

            }
        } );

        System.out.println( example.get() );

    }


    @Test
    public void testCasAdd() {
        final CasExample example = new CasExample();

        testThread( new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10000; i++) {
                    example.getAndIncrement();
                }

            }
        } );

        System.out.println( example.get() );
    }


    @Test
    public void testProduceAndConsume() {

        final ProduceAndConsume queue = new ProduceAndConsume();

        TestUtil.testThread( new Runnable() {
            @Override
            public void run() {
                queue.produce();
                queue.consume();
            }
        } );

    }


    @Test
    public void testPipe() {

        final Piped piped = new Piped();
        Thread thread1 = new Thread( new Runnable() {
            @Override
            public void run() {

                try {
                    piped.write();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } );

        Thread thread2 = new Thread( new Runnable() {
            @Override
            public void run() {

                try {
                    piped.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } );

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            piped.close();
        }


    }


    @Test
    public void testProfile() {
        Profiler.begin();
        try {
            TimeUnit.SECONDS.sleep( 1 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println( Profiler.end() );
        }
    }

}
