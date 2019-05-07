package com.wei.idea;


public class Test1 {
    public static void main(String[] args) {
        final Test1 test1=new Test1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.test3();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.test1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.test2();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test1.test4();
            }
        }).start();
    }

   public static   synchronized void  test1(){
       System.out.println("执行test1");
       try {
           //执行test1后 休眠3秒
           Thread.sleep(3000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
   public void test2(){
        synchronized(this){
            System.out.println("执行test2");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
   }
   public void  test3(){
        synchronized(Test1.class){
            System.out.println("执行test3");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
   }
   public void test4(){
        String s="11";
        synchronized (this){
            System.out.println("执行test4=="+s);
        }
   }
}
