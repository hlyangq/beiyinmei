package com.ningpai.site.customer.deposit.bean;

import java.math.BigDecimal;

/**
 * Created by Yang on 2016/9/28.
 */
public class TradeConst {

    //交易类型 0在线充值 1订单退款 2线下提现 3订单消费
    public static final String TYPE_ONLINE_RECHARGE = "0";
    public static final String TYPE_ORDER_REFUND = "1";
    public static final String TYPE_WITHDRAW = "2";
    public static final String TYPE_ORDER_CONSUME = "3";
    public static final String TYPE_ORDER_STATUS_DONE = "4";
    public static final String TYPE_ORDER_STATUS_RECHARGING = "5";
    public static final String TYPE_ORDER_STATUS_RECHARGE_SUCCESS = "6";
    public static final String TYPE_ORDER_STATUS_RECHARGE_FAIL = "7";
    public static final String TYPE_ORDER_DELETE_FLAG = "0";

    public static final String TYPE_SOURCE_REFUND = "订单退款";
    public static final String TYPE_SOURCE_CONSUME = "订单消费";
    //单次最大
    public static final BigDecimal MAX_PER_RECHARGE = new BigDecimal("1000000");

}
