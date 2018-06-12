/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.weixin.controller;

import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.service.ThreePartService;
import com.ningpai.m.weixin.util.WeiXinUtil;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.AuthService;
import com.ningpai.system.service.PayService;
import com.ningpai.util.MyLogger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 微信回调
 *
 * @author NINGPAI-zhangqiang
 * @since 2014年8月26日 下午4:14:20
 * @version 0.0.1
 */
@Controller
public class AfterWeiXinController {

    private static final String REDIRECT = "redirect:/main.html";
    private static final String SECRET = "&secret=";
    private static final String URLINFO1 = "&grant_type=authorization_code";
    private static final String UTF8 = "utf-8";
    private static final String OPENID = "openid";
    private static final String ACCESSTOKEN = "access_token";
    private static final String URLINFO2 = "https://api.weixin.qq.com/sns/userinfo?access_token=";
    private static final String URLOPENID = "&openid=";
    private static final String URLLANG = "&lang=zh_CN";
    private static final String URLINFO3 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    private static final String CODE = "&code=";

    private static MyLogger LOGGER = new MyLogger(AfterWeiXinController.class);

    private AuthService authService;

    private CustomerMapper customerMapper;

    private CustomerServiceMapper customerServiceMapper;

    private CustomerService customerService;

    private ThreePartService threePartService;

    @Resource(name = "payService")
    private PayService payService;

    /**
     * 获取微信用户Code
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws org.apache.commons.httpclient.HttpException
     */
    @RequestMapping("getwxtoken")
    protected ModelAndView getWXToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView(REDIRECT);
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = URLINFO3 + auth.getAuthClientId() + SECRET + auth.getAuthClientSecret() + CODE + code + URLINFO1;
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset(UTF8);
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = URLINFO2 + resultMap.get(ACCESSTOKEN) + URLOPENID + resultMap.get(OPENID) + URLLANG;
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset(UTF8);
                res = "";
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo, clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("获取token失败!", e, request);
                    return new ModelAndView(REDIRECT);
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException("发送获取token请求失败!", e, request);
                return new ModelAndView(REDIRECT);
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("获取微信设置失败!", e, request);
                return new ModelAndView(REDIRECT);
            }
        }
        return new ModelAndView("redirect:/addcouponpx.htm");
    }

    private ModelAndView throwNullPointer(HttpServletRequest request) {
        try {
            // 获取数据失败 throw NullPointerException ...
            throw new NullPointerException();
        } catch (Exception e) {
            // 获取token失败
            OperaLogUtil.addOperaException("获取token失败!", e, request);
            return new ModelAndView(REDIRECT);
        }
    }

    /**
     * 获取微信用户Code
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws org.apache.commons.httpclient.HttpException
     */
    @RequestMapping("getwxtoken1")
    protected ModelAndView getWXToken1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView(REDIRECT);
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = URLINFO3 + auth.getAuthClientId() + SECRET + auth.getAuthClientSecret() + CODE + code + URLINFO1;
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset(UTF8);
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = URLINFO2 + resultMap.get(ACCESSTOKEN) + URLOPENID + resultMap.get(OPENID) + URLLANG;
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset(UTF8);
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo, clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("Getting userinfo failed!", e, request);
                    return new ModelAndView(REDIRECT);
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException("Sending getwxtoken request failed!", e, request);
                return new ModelAndView(REDIRECT);
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("Getting WEIXIN set failed!", e, request);
                return new ModelAndView(REDIRECT);
            }
        }
        return new ModelAndView(REDIRECT);
    }

    /**
     * 获取微信用户Code
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     * @throws org.apache.commons.httpclient.HttpException
     */
    @RequestMapping("getwxtoken2")
    protected ModelAndView getWXToken2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code == null) {
            return new ModelAndView(REDIRECT);
        }
        // 获取微信接口AppId...
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = URLINFO3 + auth.getAuthClientId() + SECRET + auth.getAuthClientSecret() + CODE + code + URLINFO1;
            GetMethod getTokenMethod = new GetMethod(url);
            HttpClient clientToken = new HttpClient();
            getTokenMethod.getParams().setContentCharset(UTF8);
            String res = "";
            Map<String, String> resultMap = null;
            try {
                // 获取token
                clientToken.executeMethod(getTokenMethod);
                res = getTokenMethod.getResponseBodyAsString();
                resultMap = WeiXinUtil.getWeiToken(res);
                if (resultMap == null) {
                    return throwNullPointer(request);
                }
                url = URLINFO2 + resultMap.get(ACCESSTOKEN) + URLOPENID + resultMap.get(OPENID) + URLLANG;
                GetMethod getTokenInfo = new GetMethod(url);
                HttpClient clientInfo = new HttpClient();
                getTokenInfo.getParams().setContentCharset(UTF8);
                try {
                    getWXLogin(request, code, resultMap, getTokenInfo, clientInfo);
                } catch (Exception e) {
                    // 获取token失败
                    OperaLogUtil.addOperaException("Getting userinfo failed!", e, request);
                    return new ModelAndView(REDIRECT);
                }

            } catch (Exception e) {
                // 发送获取token请求失败
                OperaLogUtil.addOperaException("Sending getwxtoken request failed!", e, request);
                return new ModelAndView(REDIRECT);
            }
        } else {
            try {
                // 微信接口数据未获取 throw NullPointerException...
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException("Getting WEIXIN set failed!", e, request);
                return new ModelAndView(REDIRECT);
            }
        }
        return new ModelAndView("redirect:/" + request.getSession().getAttribute("otherPayUrl"));
    }

    private void getWXLogin(HttpServletRequest request, String code, Map<String, String> resultMap, GetMethod getTokenInfo, HttpClient clientInfo) throws IOException {
        String res;
        Map<String, String> userMap;
        clientInfo.executeMethod(getTokenInfo);
        res = getTokenInfo.getResponseBodyAsString();
        // 成功获取微信用户信息 下面开始解析用户信息
        userMap = WeiXinUtil.getWeiXinInfo(res);
        // 验证存在 用户已存在 直接登录 用户不存在 则执行注册流程
        ThreePart tp = threePartService.selectThreePart(resultMap.get(OPENID));

        System.out.print("openIdwei :+++++++++++++++++++++++++++++++++"+resultMap.get(OPENID));
//        if (tp != null) {
//            // 获取用户信息
//            if (this.customerService == null) {
//                this.customerService = this.getCustomerService();
//            }
//            Customer cus = this.customerService.selectByPrimaryKey(tp.getThreePartMemberId());
//            request.getSession().setAttribute("cust", cus);
//            request.getSession().setAttribute("customerId", cus.getCustomerId());
//            request.getSession().setAttribute("isWx", "1");
//        } else {
//            // 注册
//            CustomerAllInfo allInfo = new CustomerAllInfo();
//            allInfo.setLoginIp(IPAddress.getIpAddr(request));
//            allInfo.setCustomerUsername(resultMap.get(OPENID));
//            allInfo.setCustomerPassword("");
//            allInfo.setCustomerNickname(userMap.get("nickname"));
//            allInfo.setInfoGender(userMap.get("sex"));
//            allInfo.setCustomerImg(userMap.get("headimgurl").toString());
//            // 将微信用户添加到会员列表中
//            if (customerServiceMapper.addCustomer(allInfo) == 1) {
//                Map<String, Object> paramMap = new HashMap<String, Object>();
//                paramMap.put("username", resultMap.get(OPENID));
//                paramMap.put("password", "");
//                Customer customer = customerMapper.selectCustomerByNamePwd(paramMap);
//                tp = new ThreePart();
//                tp.setThreePartUid(resultMap.get(OPENID));
//                tp.setThreePartToken(resultMap.get(ACCESSTOKEN));
//                tp.setThreePartMemberId(customer.getCustomerId());
//                threePartService.insertThreePart(tp);
//                Customer cus = customerService.selectByPrimaryKey(tp.getThreePartMemberId());
//                request.getSession().setAttribute("cust", cus);
//                request.getSession().setAttribute("customerId", cus.getCustomerId());
//                request.getSession().setAttribute("isWx", "1");
//            }
//        }
        request.getSession().setAttribute("isWx", "1");
        Pay pay = payService.findByPayId(37L);
        request.getSession().setAttribute("accessToken", resultMap.get(ACCESSTOKEN));
        request.getSession().setAttribute("appid", pay.getApiKey());
        request.getSession().setAttribute("state", request.getParameter("state"));
        // 微信授权code
        request.getSession().setAttribute("code", code);
        request.getSession().setAttribute(OPENID, resultMap.get(OPENID));
    }

    public AuthService getAuthService() {
        return authService;
    }

    @Resource(name = "authService")
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    @Resource(name = "customerServiceM")
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    @Resource(name = "customerServiceMapper")
    public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }

    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    @Resource(name = "customerMapper")
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public ThreePartService getThreePartService() {
        return threePartService;
    }

    @Resource(name = "threePartServiceM")
    public void setThreePartService(ThreePartService threePartService) {
        this.threePartService = threePartService;
    }

    /**
     * 通过code获取网页静默授权access_token
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("getwxaccesstoken")
    protected void getwxaccesstoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取code
        String code = request.getParameter("code");
        if (code != null) {
            // 获取微信接口AppId...
            Auth auth = authService.findAuthByAuthType("9");
            if (auth != null) {
                //获取openid的url
                String url = URLINFO3 + auth.getAuthClientId() + SECRET + auth.getAuthClientSecret() + CODE + code + URLINFO1;
                GetMethod getTokenMethod = new GetMethod(url);
                HttpClient clientToken = new HttpClient();
                getTokenMethod.getParams().setContentCharset(UTF8);
                String res = "";
                Map<String, String> resultMap = null;
                try {
                    // 获取token
                    clientToken.executeMethod(getTokenMethod);
                    res = getTokenMethod.getResponseBodyAsString();
                    resultMap = WeiXinUtil.getWeiToken(res);
                    if (resultMap != null && resultMap.get(OPENID) != null) {
                        request.getSession().setAttribute(OPENID, resultMap.get(OPENID));
                        LOGGER.info("微信分享进入商城获取的openid:"+resultMap.get(OPENID));
                    }

                } catch (Exception e) {
                    // 发送获取token请求失败
                    OperaLogUtil.addOperaException("Sending getwxaccesstoken request failed!", e, request);
                }
            }
        }
        LOGGER.info("微信分享进入商城获取的jumpUrl:"+request.getSession().getAttribute("jumpUrl"));
        response.sendRedirect((String)request.getSession().getAttribute("jumpUrl"));
    }
}
