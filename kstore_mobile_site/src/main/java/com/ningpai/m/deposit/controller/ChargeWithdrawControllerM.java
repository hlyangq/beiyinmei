package com.ningpai.m.deposit.controller;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.deposit.bean.ChargeWithdraw;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.bean.DepositInfoCons;
import com.ningpai.m.deposit.service.ChargeWithdrawService;
import com.ningpai.m.deposit.service.DepositInfoService;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现controller
 * Created by chenpeng on 2016/10/14.
 */
@Controller
public class ChargeWithdrawControllerM {

    @Resource(name = "SeoService")
    private SeoService seoService;

    @Resource(name = "chargeWithdrawServiceM")
    private ChargeWithdrawService chargeWithdrawService;

    @Resource(name = "depositInfoServiceM")
    private DepositInfoService depositInfoService;

    /**
     * 预存款提现页面
     * @param request
     * @return 预存款提现页面
     */
    @RequestMapping("/tochargewithdraw")
    public ModelAndView toChargeWithdraw(HttpServletRequest request){
        ModelAndView mav = null;
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            //获取预存款交易记录信息
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
            DepositInfo depositInfo = depositInfoService.queryDepositInfo(customerId);
            if(depositInfo != null && depositInfo.getPasswordErrorCount()>=DepositInfoCons.ERROR_THRESHOLD){
                mav = new ModelAndView(CustomerConstants.MYPREDEPOSITS)
                        .addObject("deposit", depositInfo)
                        .addObject("error_msg", "预存款账户锁定，无法提现");
            }else {
                mav = new ModelAndView(CustomerConstants.MYDEPOSITSCASH)
                        .addObject("deposit", depositInfoService.queryDepositInfo(customerId))
                        .addObject("bankList", chargeWithdrawService.selectBankInfoList());
            }

        }else {
            mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
        return  seoService.getCurrSeo(mav);
    }

    /**
     * 提现申请
     * @param request
     * @param chargeWithdraw 申请单
     * @return
     */
    @RequestMapping("/subchargewithdraw")
    @ResponseBody
    public JSONObject subWithdraw(HttpServletRequest request, ChargeWithdraw chargeWithdraw){
        JSONObject resultJson = new JSONObject();
        if (LoginUtil.checkLoginStatus(request)) {
            String payPassword = request.getParameter("subPassword");
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
            //支付密码验证
            resultJson = depositInfoService.checkPayPassword(payPassword, customerId,"1", chargeWithdraw.getAmount());
            if (StringUtils.equals(DepositInfoCons.FAIL, resultJson.get(DepositInfoCons.RETURN_CODE).toString())){
                return resultJson;
            }
            resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.SUCCESS);
            //提现申请
            chargeWithdraw.setCustomerId(customerId);
            resultJson.put("tradeId", chargeWithdrawService.insertWithdraw(chargeWithdraw)) ;
        }

        return resultJson;

    }

    /**
     * 跳转提现页面验证
     * @param request
     * @param chargeWithdraw 申请单
     * @return
     */
    @RequestMapping("/checkwithdraw")
    @ResponseBody
    public JSONObject checkWithdraw(HttpServletRequest request){
        JSONObject resultJson = new JSONObject();
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
            resultJson = depositInfoService.checkWithdraw(customerId);
        }
        return resultJson;

    }

}
