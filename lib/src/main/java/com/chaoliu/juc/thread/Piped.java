package com.chaoliu.juc.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {

    private PipedWriter out;
    private PipedReader in;

    public Piped()  {
        out = new PipedWriter();
        in = new PipedReader();
        //输入流于输出进行连接
        try {
            out.connect( in );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() throws IOException {
        int receive = 0;
        //读取 系统输入流
        while ((receive = System.in.read()) != -1) {
            out.write( receive );
        }
    }

    public void read() throws IOException {
        int receive = 0;
        while ((receive = in.read()) != -1) {
            System.out.print( (char) receive );
        }
    }

    public void close(){
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
