/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.weixin.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 微信Util
 *
 * @author NINGPAI-zhangqiang
 * @since 2014年8月26日 下午5:20:57
 * @version 0.0.1
 */
public final class WeiXinUtil {

    private static final String ACCESSTOKEN = "access_token";
    /**
     * 凭证有效时间,单位:秒
     */
    private static final String EXPIRESIN = "expires_in";

    /**
     * 临时票据
     */
    private static final String TICKET = "ticket";

    private WeiXinUtil() {

    }

    /**
     * 处理微信端返回的Json数据包,
     *
     * @param res
     *            微信返回Json数据
     * @return Map<String,String> 处理后Map
     */
    public static Map<String, String> getWeiXinJosn(String res) {
        Map<String, String> paramMap = null;
        try {
            if (res.indexOf(ACCESSTOKEN) != -1) {
                paramMap = new HashMap<String, String>();
                if (res != null && !"".equals(res)) {
                    String[] temp = res.split(",");
                    for (int i = 0; i < temp.length; i++) {
                        String[] obj = temp[i].split(":");
                        paramMap.put(obj[0], obj[1]);
                    }
                }
            }
            return paramMap;
        } finally {
            paramMap = null;
        }
    }

    /**
     * 获取微信用户信息
     *
     * @param res
     *            微信返回Json包
     * @return Map<String,String> 处理后Map
     */
    public static Map<String, String> getWeiXinInfo(String res) {
        Map<String, String> userData = new HashMap<String, String>();
        JSONObject jsonData = JSONObject.fromObject(res);
        userData.put("nickname", jsonData.getString("nickname").toString());
        userData.put("sex", jsonData.getString("sex").toString());
        userData.put("headimgurl", jsonData.getString("headimgurl").toString());
        return userData;
    }

    /**
     * 获取微信token openid信息
     *
     * @param res
     *            微信返回Json包
     * @return Map<String,String> 处理后Map
     */
    public static Map<String, String> getWeiToken(String res) {
        Map<String, String> userData = new HashMap<String, String>();
        JSONObject jsonData = JSONObject.fromObject(res);
        userData.put(ACCESSTOKEN, jsonData.getString(ACCESSTOKEN).toString());
        userData.put("openid", jsonData.getString("openid").toString());
        return userData;
    }

    /**
     * 获取微信token信息
     *
     * @param res
     *            微信返回Json包
     * @return Map<String,String> 处理后Map
     */
    public static String getWxAcessToken(String res) {
        JSONObject jsonData = JSONObject.fromObject(res);
        return jsonData.getString(ACCESSTOKEN).toString();
    }

    /**
     *
     * @param res
     * @return
     */
    public static Map<String, String> getWxSendResponse(String res) {
        Map<String, String> userData = new HashMap<String, String>();
        JSONObject jsonData = JSONObject.fromObject(res);
        userData.put("errmsg", jsonData.getString("errmsg").toString());
        userData.put("errcode", jsonData.getString("errcode").toString());
        return userData;
    }

    /**
     * 解析凭证有效时间
     * @param res 响应信息
     * @return 凭证有效时间
     */
    public static int getWxAccessTokenExpiresIn(String res){
        JSONObject jsonObject = JSONObject.fromObject(res);
        return jsonObject.getInt(EXPIRESIN);
    }

    /**
     * 解析临时票据
     * @param res 响应信息
     * @return 临时票据
     */
    public static String getWxJsapiTicket(String res){
        JSONObject jsonObject = JSONObject.fromObject(res);
        return jsonObject.getString(TICKET);
    }

}
