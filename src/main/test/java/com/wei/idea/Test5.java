package com.wei.idea;

import java.util.concurrent.TimeUnit;

/**
 * @author wei
 * @date 2019/5/23-22:00
 */
public class Test5 {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        //使用两个Blocked线程 一个获取锁成功 另一个被阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();
    }
    static class  TimeWaiting implements Runnable{

        @Override
        public void run() {
            while (true){
                Test4.second(100);
            }
        }
    }
    static  class Waiting implements  Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static class  Blocked implements  Runnable{
        public  void run(){
            synchronized (Blocked.class){
                Test4.second(100);
            }
        }
    }

}
