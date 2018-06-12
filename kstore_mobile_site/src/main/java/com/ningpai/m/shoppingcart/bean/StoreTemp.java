package com.ningpai.m.shoppingcart.bean;

import java.math.BigDecimal;

/**
 * 商家临时类
 * */
public class StoreTemp {

    /**
    *thirdId
     */
    private Long thirdId;
    /**
    *商家名称
     */
    private String thirdName;
    /**
     * 商家订单金额
     */
    private BigDecimal sumPrice;


    public void setSumPrice(BigDecimal sumPrice){
        this.sumPrice=sumPrice;
    }
    public BigDecimal getSumPrice(){
        return this.sumPrice;
    }

    public Long getThirdId() {
        return thirdId;
    }

    public void setThirdId(Long thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

}
