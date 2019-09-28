package com.wei.idea;

import java.util.concurrent.*;

/**
 * @author wei
 * @date 2019/9/8-22:35
 */
public class FutureDemo implements Callable<String> {
    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return "wei";
    }

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        FutureDemo futureDemo=new FutureDemo();
        Future<String> result= executorService.submit(futureDemo);
        System.out.println("main");
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
