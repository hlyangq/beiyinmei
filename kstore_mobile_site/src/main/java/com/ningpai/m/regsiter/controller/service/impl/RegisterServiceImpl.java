/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.regsiter.controller.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ningpai.customer.dao.CustomerAddressMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.regsiter.controller.service.RegisterService;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;

/**
 * 手机端注册service实现类
 * @see com.ningpai.m.login.service.LoginService
 * @author QIANMI-ZHANGWENCHANG
 */
@Service("registerServiceM")
public class RegisterServiceImpl implements RegisterService {

    // spring注解 会员Mapper
    private CustomerMapper customerMapper;

    private CustomerAddressMapper addressMapper;

    private CustomerPointServiceMapper customerPointServiceMapper;

    private ShoppingCartService shoppingCartService;

    private static final String UTYPE = "uType";

    /*
     * 
     * 验证用户
     * @see
     * com.ningpai.m.login.service.LoginService#checkCustomerExists(javax.servlet
     * .http.HttpServletRequest, java.lang.String, java.lang.String)
     */
    @Override
    public int checkCustomerExists(HttpServletRequest request, HttpServletResponse response, String username, String password) throws UnsupportedEncodingException {
        Map<String, Object> paramMap = null;
        String nameEmp = username.trim();
        paramMap = new HashMap<String, Object>();
        if (nameEmp.indexOf("@") != -1) {
            paramMap.put(UTYPE, "email");
        } else if (Pattern.compile("^0?(13|15|17|18|14)[0-9]{9}$").matcher(nameEmp).find()) {
            paramMap.put(UTYPE, "mobile");
        } else {
            paramMap.put(UTYPE, "username");
        }
        paramMap.put("username", username);
        Customer customerN = customerMapper.selectCustomerByNamePwdAndType(paramMap);
        if (customerN != null) {
            //手机号已存在
            return 1;
        } else {
            // 用户名不存在
            return 2;
        }
    }

    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    @Resource(name = "customerMapperM")
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public CustomerAddressMapper getAddressMapper() {
        return addressMapper;
    }

    @Resource(name = "customerAddressMapper")
    public void setAddressMapper(CustomerAddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }

    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    @Resource(name = "ShoppingCartService")
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

   
    /**
     * 查询cookie中信息
     * 
     * @param request
     * @return
     */
    public String getCookie(HttpServletRequest request, String param) {
        String str = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(param)) {
                    str = cookie.getValue();
                }
            }
        }
        return str;
    }
}
