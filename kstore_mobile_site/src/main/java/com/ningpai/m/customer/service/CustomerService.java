/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service;

import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.util.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 手机端会员Service
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月20日 上午10:48:15
 * @version 0.0.1
 */
public interface CustomerService {
    /**
     * 查询会员基本信息
     * 
     * @param customerId
     *            会员编号 {@link Long}
     * @return CustomerAllInfo
     */
    CustomerAllInfo selectByPrimaryKey(Long customerId);

    /**
     * 发送手机验证码
     * 
     * @param request
     * @param moblie
     *            目标手机
     * @return 1 失败 0网络连接超时 -1没过90秒
     */
    int sendPost(HttpServletRequest request, String moblie);

    /**
     * 验证手机验证码
     * 
     * @param request
     * @return 0失败 1成功 -1失效
     */
    int getMCode(HttpServletRequest request, String code);

    /**
     * 修改密码
     * 
     * @param request
     * @param userKey
     *            手机用户新密码
     * @return 0 修改失败 1修改成功
     */
    int updateCusomerPwd(HttpServletRequest request, String userKey);
    /**
     * 修改会员
     * @param customer
     * @return
     */
    int updateCustomer(Customer customer);
    /**
     * 修改会员信息
     * @param customerInfo
     * @return
     */
    int updateCustomerInfo(CustomerAllInfo customerInfo);

    
    /**
     * 根据会员编号查询相应的会员积分明细
     * 
     * @param customerId
     *            查询条件
     * @return PageBean
     */
    PageBean selectAllCustomerPoint(Long customerId, PageBean pb,Long date,String type);
    
    /**
     * 修改密码
     * @param request
     * @param password 老密码
     * @param newPassword 新密码
     * @return
     */
    int modifyPassword(HttpServletRequest request, HttpServletResponse response, String password, String newPassword);

    /**
     * 根据会员编号查询相应的会员折扣
     * @param customerId 会员编号
     * @return BigDecimal 会员折扣
     */
    BigDecimal queryCustomerPointDiscountByCustId(Long customerId);
    /**
     * 根据订单编号查找订单信息
     * 
     * @param orderId
     *            订单编号
     * @return OrderInfoBean
     */
    OrderInfoBean queryOrderByCustIdAndOrderId(Long orderId, Long customerId);
    
    /**
     * 安全验证身份
     * 
     * @param request
     * @return 0失败 1成功 -1失效
     */
    int checkIdentity(HttpServletRequest request, String valiCode, String password);

    /**
     * 根据会员名和密码验证用户
     *
     * @param paramMap
     *            验证条件
     * @return null 密码错误 Customer 验证成功
     */
    Customer selectCustomerByNamePwd(Map<String, Object> paramMap);

    /**
     * 发送手机验证码
     *
     * @param request
     * @param long
     *            用户id
     * @return 1 失败 0网络连接超时 -1没过90秒
     */
    int sendPayCode(HttpServletRequest request, Long customerId);


}
