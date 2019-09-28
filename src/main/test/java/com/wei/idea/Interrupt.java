package com.wei.idea;

import java.util.concurrent.TimeUnit;

/**
 * @author wei
 * @date 2019/9/9-19:27
 */
public class Interrupt implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            // try {
            //     Thread.sleep(1000);
                System.out.println("wei");
            // } catch (InterruptedException e) {
            //          System.out.println("exection:"+Thread.currentThread().isInterrupted());//线程在阻塞状态 通过interrupt操作不会中止线程
            //          // 只会唤醒 抛出异常 异常会复位
            //          // Thread.currentThread().interrupt();
            //          e.printStackTrace();
            //          Thread.interrupted();
            // }


        }
        System.out.println("thread1:"+Thread.currentThread().isInterrupted());
        Thread.interrupted();//线程复位
        if (!Thread.currentThread().isInterrupted()){
            System.out.println("thread1:"+Thread.currentThread().isInterrupted());
        }
        System.out.println("wei out");
    }

    public static void main(String[] args) {
        Interrupt interrupt=new Interrupt();
        Thread thread=new Thread(interrupt);
        thread.start();
        System.out.println("main"+thread.isInterrupted());
        try {
            Thread.sleep(500);
            thread.interrupt();
        } catch (InterruptedException e) {

        }
    }
}
