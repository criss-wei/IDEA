package com.wei.idea;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei
 * @date 2019/6/24-23:36
 */
public class Test8 {
    public static void main(String[] args) {
        try {
            RunThread thread = new RunThread();
            thread.start();
            Thread.sleep(1000);
            thread.setRunning(false);
            System.out.println("已经赋值为false");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void  test(){
        List<String> list=new ArrayList<>();
        MyThread1 myThread1=new MyThread1(list);
        MyThread2 myThread2=new MyThread2(list);
        myThread1.start();
        myThread2.start();

    }
}



class RunThread extends Thread {

    private volatile boolean isRunning = true;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("进入run了");
        System.out.println(isRunning);

        while (isRunning== true) {
        }
        System.out.println(isRunning());
        System.out.println("线程被停止了！");
    }
}

class  MyThread1 extends  Thread{
    private List<String> list=null;
    public  MyThread1(List<String> list){
        this.list=list;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                list.add("");
                System.out.println("添加了" + (i + 1) + "个元素");
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class  MyThread2 extends  Thread{

    private List<String> list=null;
    public  MyThread2(List<String> list){
        this.list=list;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (list.size() == 5) {
                    System.out.println("==5了，线程b要退出了！");
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
