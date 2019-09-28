package com.wei.idea;

import java.util.concurrent.TimeUnit;

/**
 * @author wei
 * @date 2019/9/9-22:10
 */
public class SyschronizedDemo {
    static Integer count=0;
    public static void  incr(){
        synchronized (count) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(()->SyschronizedDemo.incr()).start();
        }
        TimeUnit.SECONDS.sleep(10);
        System.out.println("result:"+count);
    }
}
