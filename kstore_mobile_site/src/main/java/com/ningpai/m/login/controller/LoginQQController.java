package com.ningpai.m.login.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ningpai.util.MyLogger;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import com.qq.connect.QQConnectException;

/**
 * DCreated by wtp on 2016/5/11.
 */
@Controller
public class LoginQQController {

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(LoginQQController.class);

    private AuthService authService;

    /**
     * qq回调
     *
     * @param request
     * @param response
     */
    @RequestMapping("loginmqq")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = request.getParameter("preurl");
        Auth auth = authService.findAuthByAuthType("12");
        if (auth != null) {
            String appID = auth.getAuthClientId();
            String redirectURI = URLEncoder.encode(auth.getAuthRedirectUri() + "?myurl=" + preUrl, "utf-8");
            String url = "https://graph.qq.com/oauth2.0/authorize?client_id="
                    + appID
                    + "&redirect_uri="
                    + redirectURI
                    + "&response_type=code&state=test&scope=get_user_info,add_topic,add_one_blog,add_album,upload_pic,list_album,add_share,check_page_fans,add_t,add_pic_t,del_t,get_repost_list,get_info,get_other_info,get_fanslist,get_idollist,add_idol,del_ido,get_tenpay_addr";
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                LOGGER.error("QQ回调错误" + e);
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

}
