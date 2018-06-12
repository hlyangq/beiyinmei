/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.login.service.impl;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.dao.CustomerAddressMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.other.util.IPAddress;
import com.ningpai.site.customer.mapper.CustomerMapper;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.util.UtilDate;
import com.ningpai.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * #see com.ningpai.site.login.service.LoginService
 *
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年4月15日 下午4:02:38
 */
@Service("loginServiceSite")
public class LoginServiceImpl implements LoginService {

    private static final String UTYPE = "uType";

    // spring注解 会员Mapper
    @Resource(name = "customerMapperSite")
    private CustomerMapper customerMapper;
    
    private CustomerAddress address;
    
    @Resource(name = "customerAddressMapper")
    private CustomerAddressMapper addressMapper;
    
    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;
    
    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    /**
     *
     */
    @SuppressWarnings("static-access")
    @Override
    public int checkCustomerExists(HttpServletRequest request, String username, String password) {
        // 判断用户名和密码是否为空
        if (username == null || password == null) {
            return 0;
        }
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
//        Long existsFlag = customerMapper.checkExistsByCustNameAndType(paramMap);
        //根据用户名查询用户信息
        Customer customer = customerMapper.selectCustomerByCustNameAndType(paramMap);
        if (customer != null) {
//            paramMap.put("password", password);
//            customer = customerMapper.selectCustomerByNamePwdAndType(paramMap);
            //验证用户密码
            //根据规则加密密码
            String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), password, customer.getSaltVal());
            //验证密码是否正确
            if (encodePwd.equals(customer.getCustomerPassword())) {
                // 判断用户是否被冻结
                if ("1".equals(customer.getIsFlag())) {
                    return 3;
                }
                // 增加登录积分
                if (customer.getLoginTime() == null || !UtilDate.todayFormatString(new Date()).equals(UtilDate.todayFormatString(customer.getLoginTime()))) {
                    customerPointServiceMapper.addIntegralByType(customer.getCustomerId(), "1");
                }
                customer.setCustomerPassword(null);
                // 设置登录key
                UUID uuid = UUID.randomUUID();
                customer.setLoginKey(uuid.toString());
                // 设置登录时间
                customer.setLoginTime(new Date());
                // 设置登录Ip
                customer.setLoginIp(IPAddress.getIpAddr(request));
                if (customer.getAeadTime() == null) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(new Date());
                    calendar.add(calendar.DATE, -1);
                    customer.setAeadTime(calendar.getTime());
                }
                customerMapper.updateByPrimaryKeySelective(customer);
                request.getSession().setAttribute("cust", customer);
                request.getSession().setAttribute("customerId", customer.getCustomerId());

                // 保存默认收获地址
                address = addressMapper.selectDefaultAddr(customer.getCustomerId());
                request.getSession().setAttribute("address", address);
                // 密码正确
                shoppingCartService.loadCoodeShopping(request);
                return 1;
            } else {
                // 密码错误
                return 0;
            }
        } else {
            // 用户名不存在
            return 2;
        }
    }
}
