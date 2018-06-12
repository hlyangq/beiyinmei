package com.ningpai.m.threepart.controller;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.m.customer.bean.Customer;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.threepart.util.SinaMessage;
import com.ningpai.m.weixin.bean.ThreePart;
import com.ningpai.m.weixin.service.ThreePartService;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.other.util.IPAddress;
import com.ningpai.system.bean.Auth;
import com.ningpai.system.service.AuthService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 新浪登陆回调
 * 
 * @author ggn
 * 
 */
@Controller
public class AfterSinaLoginController {

    @Resource(name = "authService")
    private AuthService authService;

    @Resource(name = "customerServiceM")
    private CustomerService customerService;

    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    @Resource(name = "threePartServiceM")
    private ThreePartService threePartService;

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    /**
     * sina回调函数
     * 
     * @throws java.io.IOException
     * @throws org.apache.commons.httpclient.HttpException
     */
    @RequestMapping("afterloginsina")
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response, String[] args) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        String preurl = request.getParameter("myurl");
        Auth auth = authService.findAuthByAuthType("13");
        if (auth != null) {
            String appID = auth.getAuthClientId();
            String appKEY = auth.getAuthClientSecret();
            String redirectURI = auth.getAuthRedirectUri();
            String code = request.getParameter("code");
            String url = "https://api.weibo.com/oauth2/access_token?client_id=" + appID + "&client_secret=" + appKEY + "&grant_type=authorization_code&redirect_uri=" + redirectURI
                    + "&code=" + code;
            String userInfo;
            PostMethod getMethod = new PostMethod(url);
            HttpClient client = new HttpClient();
            client.executeMethod(getMethod);
            userInfo = getMethod.getResponseBodyAsString();
            JSONObject jsonData = JSONObject.fromObject(userInfo);
            if (jsonData == null) {
                return new ModelAndView(new RedirectView("404.html"));
            }
            if (jsonData.get("access_token") == null) {
                return new ModelAndView("redirect:/");
            }
            String accessToken = jsonData.get("access_token").toString();
            String uid = jsonData.get("uid").toString();
            ThreePart tp = threePartService.selectThreePart(uid);
            if (tp != null) {
                // 获取用户信息
                CustomerAllInfo cus = customerServiceMapper.selectByPrimaryKey(tp.getThreePartMemberId());
                request.getSession().setAttribute("cust", cus);
                request.getSession().setAttribute("isThirdLogin", "1");
                request.getSession().setAttribute("customerId", cus.getCustomerId());
            } else {
                // 注册流程
                // 获取QQ信息
                Map<String, String> userData = SinaMessage.getSinaMessage(accessToken, uid);
                CustomerAllInfo allInfo = new CustomerAllInfo();
                allInfo.setLoginIp(IPAddress.getIpAddr(request));
                allInfo.setPointLevelId(2L);
                allInfo.setCustomerUsername(userData.get("screen_name"));
                allInfo.setCustomerPassword("");
                allInfo.setCustomerNickname(userData.get("screen_name"));
                allInfo.setInfoGender("m".equals(userData.get("gender")) ? "1" : "2");
                allInfo.setCustomerImg(userData.get("headimg").toString());
                int f = customerServiceMapper.addCustomer(allInfo);
                if (f == 1) {
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("username", userData.get("screen_name"));
                    paramMap.put("password", "");
                    Customer customer = customerService.selectCustomerByNamePwd(paramMap);
                    tp = new ThreePart();
                    tp.setThreePartUid(uid);
                    tp.setThreePartToken(accessToken);
                    tp.setThreePartMemberId(customer.getCustomerId());
                    threePartService.insertThreePart(tp);
                    CustomerAllInfo cus = customerServiceMapper.selectByPrimaryKey(tp.getThreePartMemberId());
                    request.getSession().setAttribute("cust", cus);
                    request.getSession().setAttribute("isThirdLogin", "1");
                    request.getSession().setAttribute("customerId", cus.getCustomerId());
                }
            }

            // 将cookie中的购物车商品加载到当前登入用户下面
            shoppingCartService.loadCoodeShopping(request);

            // 删除cookie中购物车的商品
            Cookie cook = new Cookie("_npstore_shopcar", null);
            cook.setMaxAge(-1);
            cook.setPath("/");
            response.addCookie(cook);
        }

        // 如果是从购物车页面跳转过来的  则又跳转回购物车页面
        if (StringUtils.isNotEmpty(preurl) && preurl.indexOf("myshoppingcart") != -1) {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/myshoppingcart.html"));
        } else {
            return new ModelAndView("redirect:/");
        }
    }
}
