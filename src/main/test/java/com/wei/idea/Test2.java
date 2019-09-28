package com.wei.idea;

import java.util.ArrayList;
import java.util.List;

public class Test2 {

    private static final  long count=10000000L;

    public static void main(String[] args) {

        conust();
        test2();
    }
    private  static void conust(){

        // long long1 = System.currentTimeMillis();
        long millis = System.currentTimeMillis();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int a=0;
                for (long i = 0; i <count; i++) {
                        a+=5;
                }
            }
        });
        thread.start();
        int b=0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        try {
            long time = System.currentTimeMillis() - millis;
            thread.join();
            System.out.println("共消耗;"+time+";b="+b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  static  void  test2(){
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("消耗时间:" + time+"ms,b="+b+",a="+a);
    }
}
