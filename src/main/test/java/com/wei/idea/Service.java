package com.wei.idea;

/**
 * @author wei
 * @date 2019/6/23-23:23
 */
public class  Service {
    public synchronized   void service1()
    {
        for (int i = 20; i >10; i--) {
            System.out.println("service1"+i);
        }

    }
    public static synchronized void service2()
    {
        for (int i = 9; i >3; i--) {
            System.out.println("service2"+i);
        }
    }
}
