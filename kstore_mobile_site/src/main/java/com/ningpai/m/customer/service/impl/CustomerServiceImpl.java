/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.customer.bean.CustomerPointLevel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.common.bean.Sms;
import com.ningpai.m.common.dao.SmsMapper;
import com.ningpai.m.common.util.SmsPost;
import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.utils.SecurityUtil;

/**
 * @see com.ningpai.m.customer.service.CustomerService
 * @author NINGPAI-zhangqiang
 * @since 2014年8月20日 上午10:58:38
 * @version 0.0.1
 */
@Service("customerServiceM")
public class CustomerServiceImpl implements CustomerService {
    private static final String UTYPE = "uType";

    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(CustomerServiceImpl.class);

    // spring 注解
    private CustomerMapper customerMapper;
    private SmsMapper mapper;
    private CustomerPointServiceMapper customerPointServiceMapper;

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerService#selectByPrimaryKey(java.lang.Long)
     */
    @Override
    public CustomerAllInfo selectByPrimaryKey(Long customerId) {
        CustomerAllInfo customerAllInfo = customerMapper.selectByPrimaryKey(customerId);
        if (null != customerAllInfo) {
            customerAllInfo.setInfoPointSum(customerPointServiceMapper.getCustomerAllPoint(customerId + ""));
            CustomerPointLevel customerPointLevel = customerPointServiceMapper.getCustomerPointLevelByPoint(customerAllInfo.getInfoPointSum());
            if (null != customerPointLevel) {
                customerAllInfo.setPointLevelName(customerPointLevel.getPointLevelName());
            }
        }
        return customerAllInfo;
    }

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerService#sendPost(javax.servlet.http.HttpServletRequest, java.lang.String)
     */
    @Override
    public int sendPost(HttpServletRequest request, String moblie) {
        Sms sms = mapper.selectSms();
        if (sms == null) {
            return 0;
        }
        sms.setSendSim(moblie);
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        request.getSession().setAttribute("mcCode", num);
        request.getSession().setAttribute("userMobile", moblie);
        sms.setMsgContext(((Integer) num).toString());
        try {
            if (SmsPost.sendPost(sms)) {
                return 1;
            }
            return 0;
        } catch (IOException e) {
            LOGGER.error("",e);
            return 0;
        }
    }

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerService#getMCode(javax.servlet.http.HttpServletRequest, java.lang.String)
     */
    @Override
    public int getMCode(HttpServletRequest request, String code) {
        if (code.equals((int) request.getSession().getAttribute("mcCode") + "")) {
            return 1;
        }
        return 0;
    }

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerService#updateCusomerPwd(javax.servlet.http.HttpServletRequest, java.lang.String)
     */
    @Override
    public int updateCusomerPwd(HttpServletRequest request, String userKey) {
        CustomerAllInfo allInfo = new CustomerAllInfo();
        allInfo.setInfoMobile((String) request.getSession().getAttribute("userMobile"));
        allInfo.setCustomerPassword(userKey);
        int result = customerMapper.updateCusomerPwd(allInfo);
        if (result == 1) {
            request.getSession().setAttribute("muFlag", "1");
        }
        return result;
    }
    
    /**
     * 根据会员编号查询相应的会员积分明细
     * 
     * @param customerId
     *            查询条件
     * @param pb
     * @return
     */
    @Override
    public PageBean selectAllCustomerPoint(Long customerId, PageBean pb,Long date,String type) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(CustomerConstants.CUSTOMERID, customerId);
        paramMap.put(CustomerConstants.DATE, date);
        paramMap.put("pointType", type);
        int count = customerMapper.queryPointMCount(paramMap).intValue();
        if(count>0){
            pb.setRows(count); 
        }else{
            pb.setRows(0);
        }
        //设置每页显示的数目
        pb.setPageSize(15);
        paramMap.put(CustomerConstants.STARTNUM, 0);
        paramMap.put(CustomerConstants.ENDNUM,count);
        // 查询所有积分记录
        pb.setList(customerMapper.queryAllPointMList(paramMap));
        return pb;
    }

    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    @Resource(name = "customerMapperM")
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public SmsMapper getMapper() {
        return mapper;
    }

    @Resource(name = "smsMapperM")
    public void setMapper(SmsMapper mapper) {
        this.mapper = mapper;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }
    /*
     * 修改会员
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.CustomerService#updateCustomer(com.ningpai.m.customer.bean.Customer)
     */
    @Override
    public int updateCustomer(Customer customer) {
        return this.customerMapper.updateByPrimaryKeySelective(customer);
    }
    /*
     * 修改会员信息
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.CustomerService#updateCustomerInfo(com.ningpai.m.customer.vo.CustomerAllInfo)
     */
    @Override
    public int updateCustomerInfo(CustomerAllInfo customer) {
        return this.customerMapper.updateCustomerInfo(customer);
    }
    /*
     * 修改密码
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.CustomerService#modifyPassword(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String)
     */
    @Override
    public int modifyPassword(HttpServletRequest request, HttpServletResponse response, String password,
            String newPassword) {
        CustomerAllInfo customerInfo = customerMapper.selectByPrimaryKey((Long)request.getSession().getAttribute("customerId"));
       //根据规则加密密码
       String encodePwd = SecurityUtil.getStoreLogpwd(customerInfo.getUniqueCode(), password, customerInfo.getSaltVal());
        //验证输入的老密码是否正确
        if (!encodePwd.equals(customerInfo.getCustomerPassword())) {
            //当前密码错误
            return 2;
        }else{
            String newPwd = SecurityUtil.getStoreLogpwd(customerInfo.getUniqueCode(), newPassword, customerInfo.getSaltVal());
            Customer cus= new Customer();
            cus.setCustomerId(customerInfo.getCustomerId());
            cus.setCustomerPassword(newPwd);
            // 修改cookie中记录的密码
            Cookie cookiePassword = new Cookie("_kstore_newMobile_password", newPwd);
            // 设置同一服务器内共享cookie
            cookiePassword.setMaxAge(-1);
            cookiePassword.setPath("/");
            response.addCookie(cookiePassword);
            //修改密码，1成功，0失败
            return customerMapper.updateByPrimaryKeySelective(cus);
        }
}
    /**
     * 根据会员编号查询相应的会员折扣
     * @param customerId 会员编号
     * @return BigDecimal 会员折扣
     */
    @Override
    public BigDecimal queryCustomerPointDiscountByCustId(Long customerId) {
        return customerMapper.queryCustomerPointDiscountByCustId(customerId);
    }

    /**
     * 根据订单编号查找订单信息
     *
     * @param orderId    订单编号
     * @param customerId
     * @return
     */
    @Override
    public OrderInfoBean queryOrderByCustIdAndOrderId(Long orderId, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("customerId", customerId);
            paramMap.put(ConstantUtil.ORDERID, orderId);
            // 根据订单 会员编号查找订单信息
            return customerMapper.queryOrderByParamMap(paramMap);
        } finally {
            paramMap = null;
        }
    }

    /*
     * 安全验证身份
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.CustomerService#checkIdentity(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)
     */
    @Override
    public int checkIdentity(HttpServletRequest request, String valiCode,
            String password) {
        // 验证码
        String patchca = (String) request.getSession().getAttribute("PATCHCA");
        if (valiCode != null && !("").equals(valiCode)
                && !patchca.equals(valiCode)) {
            // 验证码错误
            return 2;
        }
        Long customerId = (Long) request.getSession()
        .getAttribute("customerId");
        CustomerAllInfo customerInfo = customerMapper.selectByPrimaryKey(customerId);
        String newPwd = SecurityUtil.getStoreLogpwd(customerInfo.getUniqueCode(), password, customerInfo.getSaltVal());
        if (!newPwd.equals(customerInfo.getCustomerPassword())) {
           //密码错误
            return 3;
        }
        //验证通过
        return 1;
    }

    /**
     * 根据会员名和密码验证用户
     *
     * (java.util.Map)
     */
    @Override
    public Customer selectCustomerByNamePwd(Map<String, Object> paramMap) {
        return customerMapper.selectCustomerByNamePwd(paramMap);
    }

    /**
     * 发送手机验证码
     *
     * @param request
     * @param customerId
     * @return 1 失败 0网络连接超时 -1没过90秒
     */
    @Override
    public int sendPayCode(HttpServletRequest request, Long customerId) {

        CustomerAllInfo customerAllInfo = customerMapper.selectByPrimaryKey(customerId);
        if(customerAllInfo == null || StringUtils.isEmpty(customerAllInfo.getInfoMobile())){
            return 0;
        }
        Sms sms = mapper.selectSms();
        if (sms == null) {
            return 0;
        }
        sms.setSendSim(customerAllInfo.getInfoMobile());
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println(num+"===========================");
        request.getSession().setAttribute("payCode", num);
        request.getSession().setAttribute("userMobile", customerAllInfo.getInfoMobile());
        sms.setMsgContext(((Integer) num).toString());
        try {
            if (SmsPost.sendPost(sms)) {
                return 1;
            }
            return 0;
        } catch (IOException e) {
            LOGGER.error("",e);
            return 0;
        }
    }
}
