/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.auth.service;


import com.ningpai.customer.bean.Customer;

public interface ThirdManagerService {
    /**
     * 查询第三方用户信息
     *
     * @param cid
     *            用户编号
     * @return Customer {@link com.ningpai.customer.bean.Customer}
     */
    Customer selectCustByCid(Long cid);
}
