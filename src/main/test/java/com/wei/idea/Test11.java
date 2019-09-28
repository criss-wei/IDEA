package com.wei.idea;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author wei
 * @date 2019/9/1-21:28
 */
public class Test11 {

    public static void main(String[] args) {
        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread thread=new Thread(()-> {
            String alyt="iaa";
           String aly="we"+alyt;
            System.out.println(aly.hashCode());
        });
        thread.start();
        String aly="weiaa";
        System.out.println(aly.hashCode());
    }
}
