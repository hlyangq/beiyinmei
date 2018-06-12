/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.customer.service;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.bean.RegisterPoint;
import com.ningpai.other.bean.IntegralSet;
import com.ningpai.util.PageBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员积分接口
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年3月20日 上午11:20:22
 */
public interface CustomerPointServiceMapper {

    /**
     * 根据条件查询会员推广积分记录
     *
     * @param point    积分条件
     * @param pageBean 分页PageBean
     * @return pageBean {@link com.ningpai.util.PageBean}
     */
    PageBean queryregisterpoint(RegisterPoint point, PageBean pageBean);

    /**
     * 不同状态赠送积分的数量
     *
     * @return
     */
    IntegralSet findPointSet();

    /**
     * 保存推荐出注册信息
     *
     * @param point
     * @return
     */
    int insertRegisterPoint(RegisterPoint point);

    /**
     * 根据会员ID查询会员对象
     *
     * @param cusId
     * @return
     */
    Customer selectCusById(Long cusId);

    /**
     * 查询所有会员积分记录
     * <p/>
     * 查询参数 {@link java.util.Map}
     *
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectAllCustomerPoint(PageBean pageBean);

    /**
     * 删除会员积分记录
     *
     * @param parameterValues 会员积分记录编号
     * @return 0 失败 1 成功
     */
    int deleteCustomerPoint(String[] parameterValues);

    /**
     * 按条件查找积分记录
     *
     * @param point    积分条件
     * @param pageBean 分页PageBean
     * @return pageBean {@link com.ningpai.util.PageBean}
     */
    PageBean selectCustPointByCustPoint(CustomerPoint point, PageBean pageBean);

    /**
     * 根据类型添加积分
     * <p/>
     * 会员编号
     *
     * @param type 0注册 1登录 2邮箱验证 3手机验证 4发表评论 5推荐用户
     * @return
     */
    int addIntegralByType(Long customerId, String type);

    /**
     * 添加或扣除会员积分
     *
     * @param customerId
     * @param customerpoint
     * @param mgUserName 管理员
     * @return
     */
    int saveCustomerPoint(Long customerId, CustomerPoint customerpoint,String mgUserName);

    /**
     * 根据类型添加积分
     * <p/>
     * 会员编号
     *
     * @param type       0注册 1登录 2邮箱验证 3手机验证 4发表评论 5推荐用户 6 消费增加积分
     * @param orderPrice 订单支付金额
     * @param orderId    订单id
     * @return
     */
    int addIntegralByType(Long customerId, String type, Double orderPrice, String orderId);

    /**
     * 修改注册送积分
     *
     * @param psetLogin
     * @return
     */
    int updateIntegralById(Integer psetLogin);

    /**
     * 获得会员的总积分(注意 不减去用户 用掉的积分  只算用户获得的积分)
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    Integer getCustomerAllPoint(String customerId);

    /**
     * 获得会员的总积分(注意 不减去用户 用掉的积分  只算用户获得的积分)
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    Integer getCustomerAllPointSimple(String customerId);

    /**
     * 获得会员总的用掉的积分
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    Integer getCustomerReducePoint(String customerId);

    /**
     * 根据会员总积分计算会员的折扣
     *
     * @param point 会员总积分
     * @return 返回 总积分对应的折扣
     */
    BigDecimal getCustomerDiscountByPoint(int point);


    /**
     * 根据会员 总积分获得会员等级
     * @param point
     * @return 返回会员等级
     */
    CustomerPointLevel getCustomerPointLevelByPoint(int point);


    /**
     * 根据会员 总积分获得会员等级
     * @param point 总积分
     * @param customerPointLevels  会员等级
     * @return 返回会员等级
     */
    CustomerPointLevel getCustomerPointLevelByPoint(int point, List<CustomerPointLevel> customerPointLevels);

    /**
     * 获取最大会员等级的积分
     *
     * @return 返回最大会员等级的积分
     */
    Long getMaxCustomerPointLevelPoint();

    /**
     * 删除所有的会员等级
     * @return 失败返回0
     */
    int deleteAllCustomerPointLevel();

    /**
     * 查询排序后的会员等级
     *
     * @return 返回排序后的会员等级
     */
    public List<CustomerPointLevel> getAllCustomerPointLevelSort();
}
