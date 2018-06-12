/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.marketing.service;



import java.util.Map;

/**
 * 每个账户限购
 */
public interface RushCustomerService {

    /**
     * 查询当前会员可以购买的数量
     * @param map
     * @return
     */
    Integer selectByParamMap(Map<String, Object> map);

}
