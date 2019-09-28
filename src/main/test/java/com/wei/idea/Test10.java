package com.wei.idea;



/**
 * @author wei
 * @date 2019/6/24-22:41
 */
public class Test10 {
    public static void main(String[] args) {
        Thread thread1=new Thread(new MyThread5());
        Thread thread2=new Thread(new MyThread6());
        thread1.start();
        thread2.start();
    }

}
class  MyThread5 implements Runnable{

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                if (Tools.tl.get() == null) {
                    System.out.println("1");
                    Tools.tl.set("ThreadA" + (i + 1));
                } else {
                    System.out.println("ThreadA get Value=" + Tools.tl.get());
                }
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class  MyThread6 implements  Runnable{

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                if (Tools.tl.get() == null) {
                    Tools.tl.set("ThreadB" + (i + 1));
                } else {
                    System.out.println("ThreadB get Value=" + Tools.tl.get());
                }
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Tools {

    public static ThreadLocal tl = new ThreadLocal();

}
