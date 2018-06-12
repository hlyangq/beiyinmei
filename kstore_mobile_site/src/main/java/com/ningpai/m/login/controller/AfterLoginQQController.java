package com.ningpai.m.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.util.QQMessage;
import com.ningpai.m.util.StringUtil;
import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.service.ThreePartService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.util.IPAddress;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import com.ningpai.util.MyLogger;

/**
 * Created by wtp on 2016/5/11.
 */
@Controller
public class AfterLoginQQController extends HttpServlet {

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(AfterLoginQQController.class);

    private static final String LOGGERINFO1 = "QQ登陆错误";

    private static final long serialVersionUID = 1L;

    @Resource(name="authService")
    private AuthService authService;


    @Resource(name="customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    @Resource(name="threePartServiceM")
    private ThreePartService threePartService;

    @Resource(name="ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    @Resource(name="customerMapper")
    private CustomerMapper customerMapper;


    /**
     * QQ登录
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     *             afterloginqq
     */
    @RequestMapping("afterloginqq")
    public ModelAndView afterLoginQQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        String preurl = request.getParameter("myurl");


            Auth auth = authService.findAuthByAuthType("12");
            if (auth != null) {
                String appID = auth.getAuthClientId();
                String appKEY = auth.getAuthClientSecret();
                String redirectURI = auth.getAuthRedirectUri();
                String code = request.getParameter("code");

                String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + appID + "&client_secret=" + appKEY + "&redirect_uri=" + redirectURI
                        + "&code=" + code;
                String userInfo;
                GetMethod getMethod = new GetMethod(url);
                HttpClient client = new HttpClient();
                Map<String, String> pmap = null;
                client.executeMethod(getMethod);
                userInfo = getMethod.getResponseBodyAsString();
                pmap = StringUtil.formatString(userInfo);

                if (pmap == null) {
                    return new ModelAndView(new RedirectView("404.html"));
                }

                String accessToken = pmap.get("access_token");

                /*
                 * OpenID openIDObj = new OpenID(accessToken); String openid =
                 * openIDObj.getUserOpenID();
                 */
                String url1 = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
                String s = "";
                GetMethod getMethod1 = new GetMethod(url1);
                HttpClient client1 = new HttpClient();
                client1.executeMethod(getMethod1);
                s = getMethod1.getResponseBodyAsString();

                String openid = StringUtil.getOpenId(s);

                ThreePart tp = threePartService.selectThreePart(openid);
                if (tp != null) {
                    // 获取用户信息
                    CustomerAllInfo cus = customerServiceMapper.selectByPrimaryKey(tp.getThreePartMemberId());
                    request.getSession().setAttribute("cust", cus);
                    request.getSession().setAttribute("customerId", cus.getCustomerId());
                    request.getSession().setAttribute("isThirdLogin", "1");
                } else {
                    // 注册流程
                    // 获取QQ信息
                    Map<String, String> userData = QQMessage.getQQMessage(appID, accessToken, openid);
                    CustomerAllInfo allInfo = new CustomerAllInfo();
                    allInfo.setLoginIp(IPAddress.getIpAddr(request));
                    allInfo.setPointLevelId(2L);
                    allInfo.setCustomerUsername(userData.get("nickname"));
                    allInfo.setCustomerPassword("");
                    allInfo.setCustomerNickname(userData.get("nickname"));
                    allInfo.setInfoGender("男".equals(userData.get("gender")) ? "1" : "2");
                    allInfo.setCustomerImg(userData.get("headimg").toString());
                    int f = customerServiceMapper.addCustomer(allInfo);
                    if (f == 1) {
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("username", userData.get("nickname"));
                        paramMap.put("password", "");
                        Customer customer = customerMapper.selectCustomerByNamePwd(paramMap);
                        tp = new ThreePart();
                        tp.setThreePartUid(openid);
                        tp.setThreePartToken(accessToken);
                        tp.setThreePartMemberId(customer.getCustomerId());
                        threePartService.insertThreePart(tp);
                        CustomerAllInfo cus = customerServiceMapper.selectByPrimaryKey(tp.getThreePartMemberId());
                        request.getSession().setAttribute("cust", cus);
                        request.getSession().setAttribute("customerId", cus.getCustomerId());
                        request.getSession().setAttribute("isThirdLogin", "1");
                    }
                }

                // 将cookie中的购物车商品加载到当前登入用户下面
                shoppingCartService.loadCoodeShopping(request);

                // 删除cookie中购物车的商品
                Cookie cookieShopCart = new Cookie("_npstore_shopcar", null);
                cookieShopCart.setMaxAge(-1);
                cookieShopCart.setPath("/");
                response.addCookie(cookieShopCart);
            }



        // 如果是从购物车页面跳转过来的  则又跳转回购物车页面
        if (StringUtils.isNotEmpty(preurl) && preurl.indexOf("myshoppingcart") != -1) {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/myshoppingcart.html"));
        } else {
            return new ModelAndView("redirect:/");
        }


    }

    public AuthService getAuthService() {
        return authService;
    }

    @Resource(name = "authService")
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }


    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    @Resource(name = "customerServiceMapper")
    public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }



}
