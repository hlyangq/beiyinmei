/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.mapper.impl;

import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.bean.CustomerPoint;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.manager.base.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @see com.ningpai.m.customer.mapper.CustomerMapper
 * @author qiyanyuan
 *
 */
@Repository("customerMapperM")
public class CustomerMapperImpl extends BasicSqlSupport implements CustomerMapper {

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#updateByPrimaryKeySelective (com.ningpai.customer.bean.Customer)
     */
    @Override
    public int updateByPrimaryKeySelective(Customer record) {
        return this.update("com.ningpai.m.customer.mapper.CustomerMapper.updateByPrimaryKeySelective", record);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#checkExistsByCustNameAndType (java.util.Map)
     */
    @Override
    public Long checkExistsByCustNameAndType(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.checkExistsByCustNameAndType", paramMap);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#selectCustomerByNamePwdAndType (java.util.Map)
     */
    @Override
    public Customer selectCustomerByNamePwdAndType(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.selectCustomerByNamePwdAndType", paramMap);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#selectByPrimaryKey(java. lang.Long)
     */
    @Override
    public CustomerAllInfo selectByPrimaryKey(Long customerId) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.selectByPrimaryKey", customerId);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#selectCaptcha(java.lang. Long)
     */
    @Override
    public Customer selectCaptcha(Long customerId) {
        return this.selectOne("com.ningpai.customer.dao.CustomerMapper.selectCaptcha", customerId);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#updateSmsCaptcha(com.ningpai .customer.bean.Customer)
     */
    @Override
    public int updateSmsCaptcha(Customer customer) {
        return this.update("com.ningpai.customer.dao.CustomerMapper.updateSmsCaptcha", customer);
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.mapper.CustomerMapper#updateCusomerPwd(com.ningpai .m.customer.vo.CustomerAllInfo)
     */
    @Override
    public int updateCusomerPwd(CustomerAllInfo allInfo) {
        return this.update("com.ningpai.m.customer.mapper.CustomerMapper.updateCusomerPwd", allInfo);
    }
    /*
     * 修改用户信息
     * (non-Javadoc)
     * @see com.ningpai.m.customer.mapper.CustomerMapper#updateCustomerInfo(com.ningpai.m.customer.vo.CustomerAllInfo)
     */
    @Override
    public int updateCustomerInfo(CustomerAllInfo allInfo) {
        return this.update("com.ningpai.m.customer.mapper.CustomerMapper.updateByInfoSelective", allInfo);
    }

    /*
     * 积分数量
     * @see com.ningpai.m.customer.mapper.CustomerMapper#queryPointMCount(java.util.Map)
     */
    @Override
    public Long queryPointMCount(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.msite.customer.dao.CustomerPointMapper.queryPointRcCount", paramMap);
    }

    /*
     * 积分列表
     * @see com.ningpai.m.customer.mapper.CustomerMapper#queryAllPointMList(java.util.Map)
     */
    @Override
    public List<Object> queryAllPointMList(Map<String, Object> paramMap) {
        return this.selectList("com.ningpai.msite.customer.dao.CustomerPointMapper.queryAllPointRc", paramMap);
    }
    /**
     * 根据会员编号查询相应的会员折扣
     * @param customerId 会员编号
     * @return BigDecimal 会员折扣
     */
    @Override
    public BigDecimal queryCustomerPointDiscountByCustId(Long customerId) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.queryCustomerPointDiscountByCustId", customerId);
    }


    /**
     * 更新登陆相关信息 :customerPassword,uniqueCode,saltVal
     *
     * @param customer
     * @return
     */
    @Override
    public int updatePwdInfo(com.ningpai.customer.bean.Customer customer) {
        return this.update("com.ningpai.m.customer.mapper.CustomerMapper.updatePwdInfo", customer);
    }

    /**
     * 根据用户名和类型查询用户信息
     *
     * @param paramMap {uType:username|mobile|email,username:username}
     * @return
     */
    @Override
    public Customer selectCustomerByCustNameAndType(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.selectCustomerByCustNameAndType", paramMap);
    }

    /**
     * 根据订单 会员编号查找订单信息
     *
     * @param paramMap
     *            查询编号
     * @return OrderInfoBean
     */
    @Override
    public OrderInfoBean queryOrderByParamMap(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.m.customer.mapper.CustomerMapper.queryOrderByCustIdAndOid", paramMap);
    }

    /***
     * 根据会员ID获取会员对象
     * @param paramMap
     *           查询参数
     * @return 对象
     */
    @Override
    public CustomerPoint selectCustomerPointByCustomerId(Map<String,Object> paramMap) {
        return this.selectOne("com.ningpai.msite.customer.dao.CustomerPointMapper.selectCustomerPointByCustomerId",paramMap);
    }

    /**
     * 更新会员积分
     * @param customerPoint
     *          会员积分
     * @return int
     */
    @Override
    public int updateCustomerPoint(CustomerPoint customerPoint) {
        return this.update("com.ningpai.msite.customer.dao.CustomerPointMapper.updateCustomerPoint",customerPoint);
    }

    /*
      *
      *
      * @see
      * com.ningpai.customer.dao.CustomerPointMapper#insertSelective(com.ningpai
      * .customer.bean.CustomerPoint)
      */
    @Override
    public int insertSelective(com.ningpai.customer.bean.CustomerPoint record) {
        return this.insert(
                "com.ningpai.customer.dao.CustomerPointMapper.insertSelective",
                record);
    }

    /**
     * 根据会员名和密码验证用户
     * @param paramMap
     *            验证条件
     * @return Customer实体
     */
    @Override
    public Customer selectCustomerByNamePwd(Map<String, Object> paramMap) {
        return this
                .selectOne(
                        "com.ningpai.m.customer.mapper.CustomerMapper.selectCustomerByNamePwd",
                        paramMap);
    }
}
