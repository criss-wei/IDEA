package com.wei.idea;

import org.junit.jupiter.api.Test;

/**
 * @author wei
 * @date 2019/6/23-23:17
 */
public class Test7 {

    /**
     * 静态方法锁当前class
     */
    @Test
    public void test1(){
        final Service1.Service2 s=new Service1.Service2();
        Thread t1=new Thread(new Runnable()
            {
                public void run(){
                   s.service1();
                }
            }
        );
        Thread t2=new Thread(new Runnable()
        {
            public void run(){
                Service1.Service2.service2();
            }
        });
        t1.start();
        t2.start();
    }
}
class Service1{
    static class Service2
    {

        public synchronized   void service1()
        {
            for (int i = 2000; i >0; i--) {
                System.out.println("service1:"+i);
            }

        }
        public static synchronized void service2()
        {
            for (int i = 2000; i >0; i--) {
                System.out.println("service2:"+i);
            }
        }

    }}