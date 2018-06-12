package com.ningpai.site.customer.deposit.pay;

import com.ningpai.system.bean.Pay;

import java.util.Map;

/**
 * Created by Yang on 2016/9/21.
 */
public abstract class AbstractPayStrategy implements PayStrategy {

    private Pay pay;
    public void setPay(Pay p){
        pay = p;
    }
    public Pay getPay() {
        return pay;
    }

    private Map<String, String> paramMap = null;

    public AbstractPayStrategy(Pay p){
        pay = p;
    }

    @Override
    public void withParamMap(Map<String, String> param) {
        this.paramMap = param;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
