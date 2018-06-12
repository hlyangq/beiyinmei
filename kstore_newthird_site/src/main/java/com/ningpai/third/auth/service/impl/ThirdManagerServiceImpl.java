/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.auth.service.impl;

import com.ningpai.customer.bean.Customer;
import com.ningpai.third.auth.mapper.ThirdManagerMapper;
import com.ningpai.third.auth.service.ThirdManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("thirdManagerService")
public class ThirdManagerServiceImpl implements ThirdManagerService {


    /**
     * 第三方管理员底层 接口
     */
    @Resource(name = "thirdManagerMapper")
    private ThirdManagerMapper thirdManagerMapper;

    /**
     * 根据ID获取会员信息
     * @param cid
     *            用户编号
     * @return
     */
    public Customer selectCustByCid(Long cid) {
        return thirdManagerMapper.selectCustByCid(cid);
    }

}
