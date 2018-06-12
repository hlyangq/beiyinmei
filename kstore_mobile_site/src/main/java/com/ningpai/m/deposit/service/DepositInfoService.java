package com.ningpai.m.deposit.service;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.order.bean.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 预存款信息service
 * Created by chenpeng on 2016/10/9.
 */
public interface DepositInfoService {

    /**
     * 验证手机(设置支付密码时)
     * @param customerId
     * @return
     */
    boolean checkMobileValidation(Long customerId);

    /**
     * 支付密码设置验证
     * @param request
     * @param code
     * @return
     */
    int validatePayCode(HttpServletRequest request, String code);

    /**
     * 支付密码设置
     * @param request
     * @param code 手机验证码
     * @param password 新密码
     * @return
     */
    int setPayPassword(HttpServletRequest request,String password);

    /**
     * 查询用户预付款信息
     * @param customerId 用户id
     * @return
     */
    DepositInfo queryDepositInfo(Long customerId);

    /**
     * 获取getBrandWCPayRequest参数
     * @param customerId 用户id
     * @param orderPrice 交易价格
     * @param orderType 交易类型
     * @return getBrandWCPayRequest参数
     */
    Map<String, Object> getBrandWCPayRequest(HttpServletRequest request, HttpServletResponse response,Long customerId, BigDecimal orderPrice, String orderType);

    /**
     * 微信充值通知处理
     * @param outTradeNo 交易单号
     * @param appId appid
     * @param resultCode 返回状态码
     * @param mId 商户号
     * @return success:成功 fail:失败
     */
    boolean weixinRechargeNotify(String outTradeNo,String appId, String resultCode, String mId);

    /**
     * 验证支付密码
     * @param payPassword 支付密码
     * @param customerId 用户id
     * @param type 0:支付 1:提现
     * @return
     */
    JSONObject checkPayPassword(String payPassword, Long customerId, String type, BigDecimal price);

    /**
     * 预存款支付
     * @param order 待支付订单
     * @param customerId 用户id
     * @return
     */
    JSONObject depositPay(Order order, Long customerId,String payPassword);

    /**
     * 验证预存款是否可用
     * @param customerId 用户id
     * @return
     */
    JSONObject checkDepositPay(Long customerId,BigDecimal orderPrice,String type);

    /**
     * 支付宝充值
     * @param rechargePrice 充值金额
     * @param customerId 用户id
     * @return
     */
    String zhifubaoRecharge(BigDecimal rechargePrice,Long customerId);

    /**
     * 微信充值通知处理
     * @param outTradeNo 交易单号
     * @return success:成功 fail:失败
     */
    boolean zhifubaoRechargeNotify(String outTradeNo);

    /**
     * 提现功能是否可用验证
     * @param customerId 用户id
     * @return
     */
    JSONObject checkWithdraw(Long customerId);


}
