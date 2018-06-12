package com.ningpai.m.deposit.controller;

import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.deposit.service.TradeInfoService;
import com.ningpai.m.deposit.service.impl.TradeInfoServiceImpl;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.util.PageBean;
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
 * 预存款controller
 * Created by chenpeng on 2016/10/8.
 */
@Controller
public class TradeInfoControllerM {

    @Resource(name = "SeoService")
    private SeoService seoService;

    /**
     * 预存款service实现
     */
    @Resource(name = "tradeInfoServiceM")
    private TradeInfoService tradeInfoService;

    /**
     * 会员中心-预存款交易明细
     * @param request
     * @param response
     * @param pageBean
     * @param type 查看方式（0全部:1:收入2:支出）
     * @return
     */
    @RequestMapping("/customerTradeInfo")
    public ModelAndView customerTradeInfo(HttpServletRequest request, HttpServletResponse response, PageBean pageBean, String type){
        ModelAndView mav = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            map.put("customerId", customerId);
            //获取预存款交易记录信息
            mav = new ModelAndView(CustomerConstants.CUSTOMERTREADINFO).addObject("pb", tradeInfoService.queryTradeInfo(pageBean, map));
        }else {
            mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
        return  seoService.getCurrSeo(mav);
    }

    /**
     * ajax加载会员中心-预存款交易明细
     * @param request
     * @param response
     * @param pageBean
     * @param type 查看方式（0全部:1:收入2:支出）
     * @return
     */
    @RequestMapping("/getCustomerTradeInfo")
    @ResponseBody
    public Map<String, Object> getCustomerTradeInfo(HttpServletRequest request, HttpServletResponse response, PageBean pageBean, String type){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            map.put("customerId", customerId);
            //获取预存款交易记录信息
            map.put("pb", tradeInfoService.queryTradeInfo(pageBean, map));
        }
        return  map;
    }


    /**
     * 会员中心-提现交易明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/towithdrawrecords")
    public ModelAndView customerWithdrawTradeInfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = null;
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            mav = new ModelAndView(CustomerConstants.CASHRECORDS);
        }else {
            mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
        return  seoService.getCurrSeo(mav);
    }

    /**
     * ajax加载会员中心-提现交易明细
     * @param request
     * @param response
     * @param pageBean
     * @param type 查看方式（0全部1待审核2待确认3已打回4已完成）
     * @return
     */
    @RequestMapping("/getwithdrawrecords")
    @ResponseBody
    public Map<String, Object> getCustomerWithdrawTradeInfo(HttpServletRequest request, HttpServletResponse response, PageBean pageBean, String type){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            map.put("customerId", customerId);
            //获取预存款提现交易记录信息
            map.put("pb", tradeInfoService.queryWithdraw(pageBean, map));
        }
        return  map;
    }

    /**
     * 会员中心-提现交易明细
     * @param request
     * @param response
     * @param tradeId
     * @return
     */
    @RequestMapping("/towtradeinfodetail")
    public ModelAndView withdrawTradeInfoDetail(HttpServletRequest request, HttpServletResponse response, Long tradeId){
        ModelAndView mav = null;
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            mav = new ModelAndView(CustomerConstants.CASHRECORDSDETAIL)
                    .addObject("tradeDetail", tradeInfoService.queryByIdAndCusId(tradeId, customerId));
        }else {
            mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
        return  seoService.getCurrSeo(mav);
    }

    /**
     * 修改提现状态（取消申请/确认收款）
     * @param request
     * @param response
     * @param tradeInfoId 交易记录id
     * @param type 修改类型（0取消申请1确认收款）
     * @return true：成功 false: 失败
     */
    @RequestMapping("/updatetradeinfostatus")
    @ResponseBody
    public boolean updateTradeInfoStatus(HttpServletRequest request, Long  tradeInfoId, String type){
        //是否登陆验证
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            return tradeInfoService.updateTradeInfoStatus(customerId,tradeInfoId,type);
        }
        return  false;
    }
}
