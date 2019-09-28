package com.wei.payresult;

import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wei
 * @date 2019/7/6-15:38
 */
public class TransactionSubmitExecutor {
    private final ExecutorService single = Executors.newSingleThreadExecutor();

    AsynPaymentService asynPaymentService = new AsynPaymentService();

    RedissonClient redissonClient = null;


    public void init() {
        redissonClient = RedisUtils.getInstance().getRedisson();
        /*single.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        });*/
        //lambda
        // single.execute(() -> {
        //     while (true){
        //         PayMentRequest request=unqueueTask();
        //         String doPay = asynPaymentService.doPay(request);
        //         RedisUtils.getInstance().getRedisson().getQueue("result_notify").add(doPay);
        //     }
        // });
    }
    //取队列
    public  PayMentRequest unqueueTask(){
        PayMentRequest request=null;
        do {
            request=(PayMentRequest)redissonClient.getQueue("pay_queue").poll();
            if (request==null){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("redis queue poll"+request);
                return  request;
            }
        } while (request==null);
        return request;
    }

    public static void main(String[] args) {
         new TransactionSubmitExecutor().init();
    }
}
