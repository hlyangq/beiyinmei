package com.ningpai.site.customer.deposit.controller;

import com.ningpai.common.util.BaseSiteController;
import com.ningpai.customer.bean.Customer;
import com.ningpai.deposit.bean.Deposit;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.site.customer.deposit.service.SiteDepositService;
import com.ningpai.site.customer.deposit.service.TradeService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.util.PageBean;
import com.ningpai.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yang on 2016/9/19.
 */
@Controller
public class DepositController extends BaseSiteController {

    @Autowired
    TopAndBottomService topAndBottomService;

    @Autowired
    private SiteDepositService depositService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private CustomerServiceInterface customerServiceInterface;

    @RequestMapping("/depositlist")
    @ResponseBody
    public List<Deposit> depositList(HttpServletRequest request) {

        // 获取用户id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 判断用户id是否为空
        if (customerId == null) {
            return null;
        }
        //组织参数map
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);
        List<Deposit> result = depositService.depositList(paramMap);
        return result;
    }

    /**
     * 页面，我的预存款
     *
     * @return
     */
    @RequestMapping("/deposit/mydeposit")
    public ModelAndView myDeposit(HttpServletRequest request,PageBean pb) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            ModelAndView mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return topAndBottomService.getTopAndBottom(mav);
        }
        String time = request.getParameter("time") == null ? "" : request.getParameter("time");/* 保证time不空，进入到mapper中的choose */
        String type = request.getParameter("type") == null ? "" : request.getParameter("type");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId);

        Deposit deposit = depositService.getDeposit(paramMap);

        paramMap.put("time", time);
        //查询分页信息
        //queryParam.put("startRowNum",startRowNum);
        String[][] types = new String[][] {
            {"0", "1"},//收入
            {"2", "3"},//支出
        };
        //无效数字保护
        if(!("0".equals(type)||"1".equals(type))){
            type = "";
        }
        if(StringUtils.isNumeric(type)){
            paramMap.put("types", types[Integer.valueOf(type)]);
        }
        //pb.setPageSize(2);
        PageBean pageBean = tradeService.pageTrade(paramMap, pb);
        pageBean.setUrl("customer/mydeposit");
        ModelAndView mav = new ModelAndView("deposit/mydeposit");
        mav.addObject("customerId", customerId);
        mav.addObject("deposit", deposit);
        mav.addObject("pwdExist", StringUtils.isNotBlank(deposit.getPayPassword()));
        mav.addObject("pb", pageBean);
        mav.addObject("type", type);
        mav.addObject("time", time);

        return withTopAndBottom(mav);
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping("/deposit/changepaypasswordview")
    public ModelAndView changePayPasswordView(HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            ModelAndView mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return topAndBottomService.getTopAndBottom(mav);
        }
        ModelAndView mav = new ModelAndView("deposit/setPasswordStep1");
        mav.addObject(CustomerConstantStr.CUSTOMER, customerServiceInterface.queryCustomerById(customerId));
        return withTopAndBottom(mav);
    }

    @RequestMapping("/deposit/sendvalidatecode")
    @ResponseBody
    public int sendValidateCode(HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        CustomerAllInfo customer = (CustomerAllInfo) customerServiceInterface.queryCustomerById(customerId);
        String mobile = customer.getInfoMobile();
        // 根据session中的customerId查询用户信息
        CustomerAllInfo user = customerServiceInterface.selectByPrimaryKey(customerId);
        if (user == null) {
            return -1;
        }
        // 发送手机验证码
        int result = customerServiceInterface.sendPost(request, user.getInfoMobile());
        return result;
    }

    @RequestMapping("/deposit/validatecode")
    public ModelAndView validateCode(HttpServletRequest request, String code) {
        ModelAndView mav = new ModelAndView();
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return topAndBottomService.getTopAndBottom(mav);
        }

        //@return 0不同 1相同
        int result = customerServiceInterface.getMCode(request, code);
        if (result == 1) {
            mav = new ModelAndView("deposit/setPasswordStep2");
            request.getSession().setAttribute("mobileCode", code);
        } else {
            mav = new ModelAndView("deposit/setPasswordStep1");
            mav.addObject("msg", "验证码不正确");
            mav.addObject("retcode", "-1");
            mav.addObject(CustomerConstantStr.CUSTOMER, customerServiceInterface.queryCustomerById(customerId));
        }
        return withTopAndBottom(mav);
    }

    /**
     * 设置支付密码
     *
     * @param request
     * @return
     */
    @RequestMapping("/deposit/resetpaypwd")
    public ModelAndView resetPwd(HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {//未登录直接到登陆页面
            ModelAndView mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return withTopAndBottom(mav);
        }

        ModelAndView mav = null;
        String code = (String) request.getSession().getAttribute("mobileCode");
        //空
        if (StringUtils.isBlank(code)) {
            mav = new ModelAndView("deposit/setPasswordStep1");
            mav.addObject(CustomerConstantStr.CUSTOMER, customerServiceInterface.queryCustomerById(customerId));
            return withTopAndBottom(mav);
        }
        //验证结果
        /*
        int val = customerServiceInterface.getMCode(request, code);
        if(val != 1){
            mav = new ModelAndView("deposit/setPasswordStep1");
            mav.addObject(CustomerConstantStr.CUSTOMER, customerServiceInterface.queryCustomerById(customerId));
            return topAndBottomService.getTopAndBottom(mav);
        }else{

        }
        */
        mav = new ModelAndView("deposit/setPasswordStep3");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        if (StringUtils.isBlank(password)) {
            mav.addObject("msg", "密码为空");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }
        if (StringUtils.isBlank(repassword)) {
            mav.addObject("msg", "确认密码为空");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }
        if (!StringUtils.equals(password, repassword)) {
            mav.addObject("msg", "两次密码不一致");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        Customer customer = customerServiceInterface.queryCustomerById(customerId);
        String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), password, customer.getSaltVal());

        params.put("payPassword", encodePwd);

        int result = depositService.resetPayPassword(params);
        if (result == 1) {
            mav.addObject("msg", "恭喜您，密码重置成功！");
            mav.addObject("retcode", "1");
        } else {
            mav.addObject("retcode", "-1");
        }
        //删除掉这个session内容
        request.getSession().removeAttribute("mobileCode");
        return withTopAndBottom(mav);
    }


    /**
     * 初始密码设置页
     * @param request
     * @return
     */
    @RequestMapping("/deposit/setpaypasswordview")
    public ModelAndView setPayPasswordView(HttpServletRequest request) {
        ModelAndView mav = null;
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return topAndBottomService.getTopAndBottom(mav);
        }
        mav = new ModelAndView("deposit/setPayPassword");
        mav.addObject(CustomerConstantStr.CUSTOMER, customerServiceInterface.queryCustomerById(customerId));
        return withTopAndBottom(mav);
    }

    /**
     * 设置支付密码
     *
     * @param request
     * @return
     */
    @RequestMapping("/deposit/setpaypassword")
    public ModelAndView setPayPassword(HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {//未登录直接到登陆页面
            ModelAndView mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return withTopAndBottom(mav);
        }

        Customer customer = customerServiceInterface.queryCustomerById(customerId);

        ModelAndView mav = new ModelAndView("deposit/setPayPassword");
        mav.addObject(CustomerConstantStr.CUSTOMER, customer);

        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        if (StringUtils.isBlank(password)) {
            mav.addObject("msg", "密码为空");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }
        if (StringUtils.isBlank(repassword)) {
            mav.addObject("msg", "确认密码为空");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }
        if (!StringUtils.equals(password, repassword)) {
            mav.addObject("msg", "两次密码不一致");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }

        String code = request.getParameter("mobileCode");
        //空
        if (StringUtils.isBlank(code)) {
            mav.addObject("msg", "请填写验证码");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }

        //验证结果
        int val = customerServiceInterface.getMCode(request, code);
        if(val != 1){
            mav.addObject("msg", "验证码不正确");
            mav.addObject("retcode", "-1");
            return withTopAndBottom(mav);
        }

        mav.setViewName("deposit/setPayPassword_result");

        String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), password, customer.getSaltVal());
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("payPassword", encodePwd);
        int result = depositService.resetPayPassword(params);
        if (result == 1) {
            mav.addObject("msg", "恭喜您，密码设置成功！");
            mav.addObject("retcode", "1");
        } else {
            mav.addObject("msg", "很抱歉，密码设置失败！");
            mav.addObject("retcode", "-1");
        }
        return withTopAndBottom(mav);
    }

    private ModelAndView withTopAndBottom(ModelAndView mav) {
        return topAndBottomService.getTopAndBottom(mav);
    }

}
