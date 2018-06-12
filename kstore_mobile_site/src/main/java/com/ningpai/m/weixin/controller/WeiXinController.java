/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.weixin.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.m.weixin.util.WxClientCredential;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信接入
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月26日 下午3:33:51
 * @version 0.0.1
 */
@Controller
public class WeiXinController {

    private static final String WEIXIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
    private static final String REDIRECT_URI = "&redirect_uri=";
    private static final String WECHAT_REDIRECT = "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
    private static final String LOGGERINFO1 = "Sending getwxcode request failed!";
    private static final String LOGGERINFO2 = "Getting WEIXIN set failed!";
    private static final String WECHAT_BASEREDIRECT = "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    private static final String LOGGERINFO3 = "Getting queryWxConfig request failed!";
    /**
     * 公众号唯一标识
     */
    private static final String APPID = "appId";
    /**
     * 当前页面的URL
     */
    private static final String CURURL = "curUrl";

    private AuthService authService;

    /**
     * 微信客户端凭证处理类
     */
    @Resource(name = "wxClientCredential")
    private WxClientCredential wxClientCredential;

    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getwxcode")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = WEIXIN_URL + auth.getAuthClientId() + REDIRECT_URI + auth.getAuthRedirectUri() + WECHAT_REDIRECT;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                OperaLogUtil.addOperaException(LOGGERINFO1, e, request);
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException(LOGGERINFO2, e, request);
            }
        }

    }

    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getwxcode1")
    protected void doGet1(HttpServletRequest request, HttpServletResponse response) {
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = WEIXIN_URL + auth.getAuthClientId() + REDIRECT_URI + auth.getAuthRedirectUri() + WECHAT_REDIRECT;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                OperaLogUtil.addOperaException(LOGGERINFO1, e, request);
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException(LOGGERINFO2, e, request);
            }
        }

    }

    /**
     * 获取微信用户Code
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getwxcode2")
    protected void doGet2(HttpServletRequest request, HttpServletResponse response) {
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null) {
            String url = WEIXIN_URL + auth.getAuthClientId() + REDIRECT_URI + auth.getAuthRedirectUri() + WECHAT_REDIRECT;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                OperaLogUtil.addOperaException(LOGGERINFO1, e, request);
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException(LOGGERINFO2, e, request);
            }
        }

    }

    /**
     * 获取微信用户Code -- 转发登录
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getwxcode3")
    protected void doGet2(HttpServletRequest request, HttpServletResponse response, String toUrl) {
        Auth auth = authService.findAuthByAuthType("9");
        request.getSession().setAttribute("otherPayUrl", toUrl);
        if (auth != null) {
            String url = WEIXIN_URL + auth.getAuthClientId() + REDIRECT_URI + auth.getAuthRedirectUri() + WECHAT_REDIRECT;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                OperaLogUtil.addOperaException(LOGGERINFO1, e, request);
            }
        } else {
            try {
                throw new NullPointerException();
            } catch (Exception e) {
                OperaLogUtil.addOperaException(LOGGERINFO2, e, request);
            }
        }

    }

    public AuthService getAuthService() {
        return authService;
    }

    @Resource(name = "authService")
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 查询微信config接口注入权限验证配置
     *
     * @return {"authClientId":"公众号的唯一标识","timestamp":"生成签名的时间戳"
     *              ,"nonceStr":"生成签名的随机串","signature":"签名"}
     * @author  chenp
     */
    @RequestMapping("queryWxConfig")
    @ResponseBody
    protected JSONObject queryWxConfig(HttpServletRequest request){
        //查询微信授权信息
        Auth auth = authService.findAuthByAuthType("9");
        String jsonString ="";
        //获取微信接口注入权限验证配置
        try{
            Map<String, String> configMap = wxClientCredential.getWxConfigInfo(request.getParameter(CURURL));
            configMap.put(APPID, auth.getAuthClientId());
            jsonString = JSONObject.toJSONString(configMap);
        }catch (Exception e){
            OperaLogUtil.addOperaException(LOGGERINFO3, e, request);
        }
        return JSONObject.parseObject(jsonString);
    }

}
