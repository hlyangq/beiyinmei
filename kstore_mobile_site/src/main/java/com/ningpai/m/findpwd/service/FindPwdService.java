/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.findpwd.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 手机端忘记密码Service
 * 
 * @author NINGPAI-zhangwenchang
 * @since 2015年10月19日
 * @version 0.0.1
 */
public interface FindPwdService {
    /**
     * 修改密码
     * @return
     */
    Object forGetPwd(HttpServletRequest request,HttpServletResponse response, String code, String mobile, String newPassword);
    
    /**
     * 验证用户
     * 
     * @param username
     *            登录名
     */
    int checkCustomerExists(HttpServletRequest request, HttpServletResponse response, String username) throws UnsupportedEncodingException;
}
