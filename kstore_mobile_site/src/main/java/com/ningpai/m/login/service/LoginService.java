/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.login.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 登录Service
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年8月19日 上午10:31:41
 * @version 0.0.1
 */
public interface LoginService {

    /**
     * 验证用户
     * 
     * @param username
     *            登录名
     * @param password
     *            密码
     * @return 0密码错误 1成功 2账号不存在
     */
    int checkCustomerExists(HttpServletRequest request, HttpServletResponse response, String username, String password, String code) throws UnsupportedEncodingException;
    
    /**
     * 是否记住用户
     * 
     * @param request
     * @return
     */
    public ModelAndView checkCookie(HttpServletRequest request, HttpServletResponse response, String url);
    
    
}
