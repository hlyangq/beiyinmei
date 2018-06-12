package com.ningpai.m.util;

import com.ningpai.m.weixin.util.WxClientCredential;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import com.ningpai.util.MyLogger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 微信获取用户唯一标示openid的拦截器
 *
 * Created by chenpeng on 16/3/28.
 */
public class WxURLInterceptor extends HandlerInterceptorAdapter {

    //日志记录
    MyLogger LOGGER = new MyLogger(WxURLInterceptor.class);

    private static ExecutorService oneThread = Executors.newSingleThreadExecutor();

    private static final String WEIXIN_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
    private static final String REDIRECT_URI = "&redirect_uri=";
    private static final String WECHAT_BASEREDIRECT = "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    private static final String EX_WXPAGEAUTHOR = "获取微信静默授权异常";
    /**
     * 微信转发,分享等方式进到商城的参数标识
     */
    private static final String ISWX = "isWx";
    /**
     * 微信客户端凭证处理类
     */
    @Resource(name = "wxClientCredential")
    private WxClientCredential wxClientCredential;

    /**
     * 第三方授权service
     */
    @Resource(name = "authService")
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //通过微信转发,分享等方式进到商城的链接都含有isWx的参数标识
        if (request.getParameter(ISWX) != null){
            oneThread.execute(new WxPageAuthor(response));
            request.getSession().setAttribute("jumpUrl", request.getRequestURL() + "?" + request.getQueryString());
            LOGGER.info(request.getRequestURL() + "?" + request.getQueryString());
        }

        return true;
    }

    /**
     * 微信静默授权
     */
    private class WxPageAuthor implements Runnable{

        private HttpServletResponse response;
        public WxPageAuthor(HttpServletResponse response){
            this.response = response;
        }

        @Override
        public void run() {
            LOGGER.info("通过微信分享进到商品详情页run()+++++++++++++++++++++++++++");
            //获取微信授权信息
            Auth auth = authService.findAuthByAuthType("9");
            if (auth != null && auth.getAuthRedirectUri() != null && auth.getAuthClientId() != null) {
                //公众号唯一标识
                String authClientId = auth.getAuthClientId();
                String redirect_uri = null;
                //重定向uri
                String authRedirectUri = auth.getAuthRedirectUri();
                /*try {
                    redirect_uri = URLEncoder.encode(authRedirectUri.substring(0, authRedirectUri.lastIndexOf("/")) + "/getwxaccesstoken.htm" , "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                redirect_uri = authRedirectUri.substring(0, authRedirectUri.lastIndexOf("/")) + "/getwxaccesstoken.htm";
                //获取code uri
                String getAccessTokenURL = WEIXIN_URL + authClientId+ REDIRECT_URI
                        + redirect_uri + WECHAT_BASEREDIRECT;
                LOGGER.info("getAccessTokenURL+++++++++++++++++++++++++++" + getAccessTokenURL);
                try {
                    response.sendRedirect(getAccessTokenURL);
                }catch (IOException exception){
                    LOGGER.info(EX_WXPAGEAUTHOR);
                }

                /*GetMethod getMethod = new GetMethod(getAccessTokenURL);
                //模拟浏览器
                getMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64)" +
                        " AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 " +
                        "Safari/537.22");
                //这个必须设置 否则无法登录 还是尽量完全模拟浏览器的行为
                getMethod.setRequestHeader("Referer", "https://mp.weixin.qq.com");
                HttpClient httpTokenClient = new HttpClient();
                try{
                    httpTokenClient.executeMethod(getMethod);
                    getMethod.getStatusCode();
                    getMethod.getStatusText();
                    LOGGER.info("=====" + getMethod.getStatusCode());
                    LOGGER.info("=====" + getMethod.getStatusText());
                }catch (IOException e){
                    LOGGER.info(EX_WXPAGEAUTHOR);
                }*/
            }
        }
    }
}
