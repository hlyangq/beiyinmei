package com.ningpai.site.customer.deposit.pay;

/**
 * Created by Yang on 2016/9/21.
 */
public class PayContext {
    private PayStrategy payStrategy;
    public PayContext(PayStrategy strategy){
        this.payStrategy = strategy;
    }
    public String pay(){
        return payStrategy.pay();
    }
}
