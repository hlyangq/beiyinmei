/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ningpai.customer.bean.CustomerFollow;
import com.ningpai.customer.dao.CustomerFollowMapper;
import com.ningpai.manager.base.BasicSqlSupport;

/**
 * @see com.ningpai.customer.dao.CustomerFollowMapper
 * @author NINGPAI-zhangqiang
 * @since 2014年4月1日 下午6:00:34
 * @version 0.0.1
 */
@Repository("customerFollowMapper")
public class CustomerFollowMapperImpl extends BasicSqlSupport implements
        CustomerFollowMapper {
    /*
     * 
     * 
     * @see
     * com.ningpai.customer.dao.CustomerFollowMapper#selectCustFollowByCustId
     * (long)
     */
    @Override
    public List<CustomerFollow> selectCustFollowByCustId(long customerId) {
        return this
                .selectList(
                        "com.ningpai.customer.dao.CustomerFollowMapper.selectCustFollowByCustId",
                        customerId);
    }

    @Override
    public List<CustomerFollow> selectSendId(Long goodsInfoId) {

        return this.selectList(
                "com.ningpai.customer.dao.CustomerFollowMapper.selectSendId",
                goodsInfoId);
    }

}
