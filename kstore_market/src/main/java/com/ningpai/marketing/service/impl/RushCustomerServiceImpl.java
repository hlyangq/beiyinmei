/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.marketing.service.impl;

import com.ningpai.marketing.bean.RushCustomer;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.marketing.service.RushCustomerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 每个账户限购
 */
@Service("rushCustomerService")
public class RushCustomerServiceImpl implements RushCustomerService {

    /**
     * 日志
     */
    private static final Logger DEBUG = Logger.getLogger(RushCustomerServiceImpl.class);

    @Resource(name = "RushCustomerMapper")
    private RushCustomerMapper rushCustomerMapper;
    /**
     * 查询当前会员可以购买的数量
     * @param map
     * @return
     */
    public Integer selectByParamMap(Map<String, Object> map){
        return rushCustomerMapper.selectByParamMap(map);
    }

}
