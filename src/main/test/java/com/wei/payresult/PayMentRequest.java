package com.wei.payresult;

/**
 * @author wei
 * @date 2019/7/6-13:32
 */
public class PayMentRequest {
     private int payMethod;//支付方法
     private int totalFee;//金额
     private String resultUrl;//回掉地址

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    @Override
    public String toString() {
        return "PayMentRequest{" +
                "payMethod=" + payMethod +
                ", totalFee=" + totalFee +
                ", resultUrl='" + resultUrl + '\'' +
                '}';
    }
}
