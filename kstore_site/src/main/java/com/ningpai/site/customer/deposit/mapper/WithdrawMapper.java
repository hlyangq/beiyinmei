package com.ningpai.site.customer.deposit.mapper;

import com.ningpai.site.customer.deposit.bean.Withdraw;

import java.util.List;
import java.util.Map;

/**
 * 套现记录
 */
public interface WithdrawMapper {

    /**
     * 条件查询取现
     * @param paramMap
     * @return
     */
    List<Withdraw> withdrawList(Map<String, Object> paramMap);
}
