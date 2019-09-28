package com.wei.payresult;

/**
 * @author wei
 * @date 2019/7/6-13:34
 */
public class AsynPaymentService {
    //组装请求参数 加签 校验
    public String submitPay(PayMentRequest request){
        System.out.println("组装请求 发起http请求");
        //给异步队列
        RedisUtils.getInstance().getRedisson().getQueue("pay_queue").add(request);
        return "processing";
    }
    //第三方支付逻辑处理
    public String doPay(PayMentRequest request){
        System.out.println("开始支付请求"+request);
        try {
            Thread.sleep(2000);//模拟支付过程耗时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public static void main(String[] args) {
        PayMentRequest request=new PayMentRequest();
        request.setTotalFee(10000);
        request.setResultUrl("http://weialy.com/payNotify");
        new AsynPaymentService().submitPay(request);
    }
}
