package com.ningpai.common.util;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.redis.RedisAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登陆验证拦截器
 * 
 * @author qiyuanyuan
 *
 */
@Controller
public class SystemInterceptor extends HandlerInterceptorAdapter {

    private static final String BASEURL = "baseUrl";

    /**
     * 用户信息
     * */
    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;


    /**
     * 注入redis 缓存
     */
    @Resource
    private RedisAdapter redisAdapter;

    /**
     * 在请求处理前拦截URL 进行相应处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取uri
        String uri1 = request.getServletPath();
        if (uri1.indexOf("uploadgallery") == -1) {
            // 获取基本地址
            String baseUrl = (String) request.getSession().getAttribute(BASEURL);
            if (baseUrl == null || "".equals(baseUrl)) {
                // 置空baseUrl
                request.getSession().setAttribute(BASEURL, "");
            }
        }
        String[] noFilterUrls = new String[] {
                "/securitycenter.htm",//账户安全
                "/myinfo",//账户信息
                "/queryMyConsume",//消费记录
                "/queryMyCoupon",//我的优惠券
                "/refundlist",//取消订单记录
        };

        String uri = request.getServletPath();
        boolean beFilter = false;
        boolean isDecorateFilter = false;
        // 查看是否拦截
        for (String s : noFilterUrls) {
            if (uri.indexOf(s) != -1) {
                beFilter = true;
                break;
            }
        }
        // 获取path
         String path = request.getContextPath();
        // 获取session
        HttpSession session = request.getSession();
        // 如果要拦截
        if (beFilter && uri1.indexOf("uploadgallery") == -1) {
            // Customer customer = (Customer) session.getAttribute("/customer");
            // 获取用户id
            Long customerId = (Long) session.getAttribute("customerId");
            // 获取装修公司id
            Long decorateId = (Long) session.getAttribute("decorateId");
            //根据获取的用户id查询会员记录数
            int custCount = customerServiceMapper.selectCustCount(customerId);
            // 如果用户id为空
            if (customerId == null || custCount <= 0) {
                response.sendRedirect(path + "/login.html"); // 返回提示页面
                return false;
                // 如果是装修公司中心 并且未登录 跳转到首页
            } else if (isDecorateFilter && decorateId == null) {
                response.sendRedirect(path + "/index.html"); // 返回提示页面
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
