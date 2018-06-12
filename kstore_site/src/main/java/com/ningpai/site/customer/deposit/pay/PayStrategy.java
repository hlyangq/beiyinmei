package com.ningpai.site.customer.deposit.pay;

import java.util.Map;

/**
 * Created by Yang on 2016/9/21.
 */
public interface PayStrategy {
    String pay();
    void withParamMap(Map<String,String> paramMap);
}
