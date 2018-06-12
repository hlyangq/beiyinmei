package com.ningpai.m.weixin.util;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.UtilDate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 微信客户端凭证处理类
 *
 * Created by chenpeng on 16/3/28.
 */
public class WxClientCredential {

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(WxClientCredential.class);

    private static final String EX_JSAPITICKET = "Sedding jsapiTicket request failed!";

    private static final String EX_JSAPIACCESSTOKEN = "Sedding jsapiAccessToken request failed!";

    private static final String CURR_ACCESSTOKEN_INFO = "  当前公众号唯一凭证accessToken: ";
    private static final String CURR_EXPIRESIN_INFO = "  当前凭证有效时间,单位 秒expiresIn: ";
    private static final String CURR_JSAPITICKET_INFO = "  当前临时票据jsapiTicket: ";


    /**
     * 公众号唯一凭证
     */
    private String accessToken;

    /**
     * 凭证有效时间,单位:秒
     */
    private int expiresIn;

    /**
     * 票据的获取时间
     */
    private Date createTimeTicket;

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    /**
     * 公众号用于调用微信JS接口的临时票据
     */
    private String jsapiTicket;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getCreateTimeTicket() {
        return createTimeTicket;
    }

    public void setCreateTimeTicket(Date createTimeTicket) {
        this.createTimeTicket = createTimeTicket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * 提前刷新时间,单位:秒
     */
    private int advanceTime = 600;

    public int getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(int advanceTime) {
        this.advanceTime = advanceTime;
    }

    /**
     * 第三方接口服务
     */
    @Resource(name = "authService")
    private AuthService authService;

    /**
     * 初始化获取微信jsapi_ticket
     */
    public void init(){
        getWxAccessToken();
        getWxJsapiTicket();
    }


    /**
     * 获取微信access_token
     */
    public void getWxAccessToken(){
        Auth auth = authService.findAuthByAuthType("9");
        if (auth != null && auth.getAuthClientId() != null && auth.getAuthClientSecret() != null){
            //获取access_token的uri
            String getAccessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+auth.getAuthClientId()+"&secret="+ auth.getAuthClientSecret();
            GetMethod getMethod = new GetMethod(getAccessTokenURL);
            HttpClient httpTokenClient = new HttpClient();
            getMethod.getParams().setContentCharset(ConstantUtil.UTF);
            String tokenRespons = "";
            try {
                httpTokenClient.executeMethod(getMethod);
                tokenRespons = getMethod.getResponseBodyAsString();
                //解析获得access_token
                accessToken = WeiXinUtil.getWxAcessToken(tokenRespons);
                //解析获得_access_token的有效期
                expiresIn = WeiXinUtil.getWxAccessTokenExpiresIn(tokenRespons);
                //打印信息
                LOGGER.info(CURR_ACCESSTOKEN_INFO + accessToken + CURR_EXPIRESIN_INFO + expiresIn);
            }catch (Exception e){
                LOGGER.info(EX_JSAPIACCESSTOKEN);
            }
        }
    }

    /**
     * 获取微信jsapi_ticket
     */
    public void getWxJsapiTicket(){
        //获取jsapi_ticket的uri
        String getJsapiTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        PostMethod postMethod = new PostMethod(getJsapiTicket);
        HttpClient httpTicketClient = new HttpClient();
        postMethod.getParams().setContentCharset(ConstantUtil.UTF);
        String ticketResponse = "";
        try{
            httpTicketClient.executeMethod(postMethod);
            ticketResponse = postMethod.getResponseBodyAsString();
            //解析获得jsapi_ticket
            jsapiTicket = WeiXinUtil.getWxJsapiTicket(ticketResponse);
            //jsapi_ticket的获取时间
            createTimeTicket = new Date();
            //打印信息
            LOGGER.info(CURR_JSAPITICKET_INFO + jsapiTicket );
        }catch (Exception e){
            LOGGER.info(EX_JSAPITICKET);
        }
    }

    /**
     * 获取微信config接口注入权限验证配置信息
     *
     * @param url 当前网页的URL, 不包含#及其后面部分
     * @return  权限验证配置信息
     */
    public Map<String, String> getWxConfigInfo(String url) throws Exception{
        Auth auth = authService.findAuthByAuthType("9");
        //如果调用的时候jsapi_ticket过了有效期,则刷新jsapi_ticket
        if (UtilDate.intervalSecondToNow(createTimeTicket) > (expiresIn - advanceTime) ){
            //初始化获取微信jsapi_ticket
            init();
        }
        //生成签名map
        Map<String, String> configMap = WxSign.sign(jsapiTicket, url);
        configMap.put("appId", auth.getAuthClientId());
        return configMap;
    }


}
