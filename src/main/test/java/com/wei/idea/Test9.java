package com.wei.idea;

/**
 * @author wei
 * @date 2019/6/24-21:56
 */
public class Test9 {

    /**
     * 轮询模式  一个生产 一个消费 交替执行  多个生产 多个消费
     * @param args
     */
    public static void main(String[] args) {
        Test9 test9=new Test9();
        MyThread3 myThread3=new MyThread3(test9);
        MyThread4 myThread4=new MyThread4(test9);
        myThread3.start();
        myThread4.start();

    }

    private String lock="wei";

    public void setValue() {
        try {
            synchronized (lock) {
                if (!ValueObject.value.equals("")) {
                    System.out.println("生产者 "
                            + Thread.currentThread().getName() + " WAITING了☆");
                    lock.wait();
                }
                System.out.println("消费者 " + Thread.currentThread().getName()
                        + " RUNNABLE了");
                String value = System.currentTimeMillis() + "_"
                        + System.nanoTime();
                Thread.sleep(1000);
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName()+"：set"+ value);
                ValueObject.value = value;
                lock.notify();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getValue() {
        try {
            synchronized (lock) {
                if (ValueObject.value.equals("")) {
                    System.out.println("消费者 "
                            + Thread.currentThread().getName() + " WAITING了☆");
                    lock.wait();
                }
                System.out.println("消费者 " + Thread.currentThread().getName()
                        + " RUNNABLE了");
                Thread.sleep(1000);
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName()+"get"+ ValueObject.value);
                ValueObject.value = "";
                lock.notify();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class  ValueObject{
    public static String value="";
}
class  MyThread3 extends Thread{
    private Test9 test9;
    public  MyThread3(Test9 test9){
        System.out.println(test9);
        this.test9=test9;
    }
    @Override
    public void run() {
        while (true){
            test9.setValue();
        }

    }
}
class  MyThread4 extends Thread {
    private Test9 test9;
    public  MyThread4(Test9 test9){
        System.out.println(test9);
        this.test9=test9;
    }
    @Override
    public void run() {
        while (true){
            test9.getValue();
        }
    }
}

