/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.regsiter.controller.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录Service
 * 
 * @author NINGPAI-zhangwenchang
 */
public interface RegisterService {

    /**
     * 验证用户
     * 
     * @param username
     *            登录名
     * @param password
     *            密码
     * @return 0密码错误 1成功 2账号不存在
     */
    int checkCustomerExists(HttpServletRequest request, HttpServletResponse response, String username, String password) throws UnsupportedEncodingException;
    
}
