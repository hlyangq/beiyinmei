package com.ningpai.m.threepart.controller;

import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 新浪登陆
 * 
 * @author ggn
 * 
 */
@Controller
public class LoginSinaController {

    private AuthService authService;

    @RequestMapping("loginsina")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String preUrl = request.getParameter("preurl");
        Auth auth = authService.findAuthByAuthType("13");
        String appID = auth.getAuthClientId();
        String redirectURI = URLEncoder.encode(auth.getAuthRedirectUri() + "?myurl=" + preUrl, "utf-8");
        String url = "https://open.weibo.cn/oauth2/authorize?client_id=" + appID + "&response_type=code&redirect_uri=" + redirectURI;
        response.sendRedirect(url);
        return null;
    }

    public AuthService getAuthService() {
        return authService;
    }

    @Resource(name = "authService")
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

}
