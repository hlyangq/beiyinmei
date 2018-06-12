package com.ningpai.site.customer.deposit.bean;

/**
 * Created by Yang on 2016/9/28.
 */
public class DepositConst {
    //支付试错次数
    public static int ERROR_TRY_COUNT = 3;
    //余额支付的payId
    public static String PAY_ID = "5";
    public static String PIN_ERROR = "支付密码错误,还有{0}次机会";
    public static String REACH_ERROR_COUNT_THRESHOLD = "预存款账户锁定，无法支付，将于次日00:00自动解锁，请选择其他支付方式";
    public static String CANNOT_WITHDRAW = "预存款账户锁定，无法提现，将于次日00:00自动解锁";
    public static String LOCKED = "支付锁定";
    public static String INSUFFICIENCE_DEPOSIT = "预存款不足，请使用其他支付方式";
    public static String FROZNE_DEPOSIT_OUT_OF_RANGE = "冻结预存款超出限额";
    public static String NOT_SET_DEPOSIT_PAY_PASSWORD = "您还没有设置支付密码，暂时无法使用预存款支付";
    public static String INVALID_PRICE = "输入金额不合法";
    public static String OK = "OK";
}
