package com.ningpai.customer.controller;

import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.service.DepositInfoService;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.customer.util.DateUtil;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 预存款Controller
 * Created by CHENLI on 2016/9/18.
 */
@Controller
public class DepositInfoController {
    private static final String PAGEBEAN = "pageBean";
    /**
     * 日志
     */
    public static final MyLogger LOGGER = new MyLogger(DepositInfoController.class);

    @Autowired
    private DepositInfoService depositInfoService;
    @Autowired
    private TradeInfoService tradeInfoService;


    /**
     * 会员资金列表
     *
     * @param depositInfo
     * @param pageBean
     * @return
     */
    @RequestMapping("/initDepositInfoList")
    public ModelAndView selectDepositInfoList(HttpServletRequest request,DepositInfoVo depositInfo, PageBean pageBean) {
        Map<String, Object> result = null;
        Integer pageNoInt = 1;
        String pageNostr = null;
        try {
            pageNostr = request.getParameter("pageNos");
            if(pageNostr!=null && pageNostr!=""){
                pageNoInt = Integer.valueOf(pageNostr);
            }
            if(pageNoInt > 1){
                pageBean.setPageNo(pageNoInt);
            }
            //查询平台总金额
            result = depositInfoService.selectTotalDesposit();
            pageBean.setUrl(CustomerConstantStr.INITDEPOSITINFOLIST);
            LOGGER.info("查询会员资金！");
            pageBean = depositInfoService.selectDepositInfoList(depositInfo, pageBean);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return new ModelAndView(CustomerConstantStr.DEPOSITINFOJSP, PAGEBEAN, pageBean).addObject("result", result);
    }

    /**
     * 根据会员ID查询会员的资金明细
     * @param depositInfoVo
     * @param pageBean
     * @return
     */
    @RequestMapping("/selectDepositInfoById")
    public ModelAndView selectDepositInfoById(HttpServletRequest request, DepositInfoVo depositInfoVo, PageBean pageBean) {
        Long customerId = depositInfoVo.getCustomerId();
        String startTime = null;
        String endTime = null;
        int pageNos = 1;
        String pageNostr = null;
        DepositInfo depositInfo = null;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = request.getParameter("startTime");
            endTime = request.getParameter("endTime");
            depositInfoVo.setStartDate(startTime == null || "".equals(startTime) ? null : formatDate.parse(startTime));
            depositInfoVo.setEndDate(endTime == null || "".equals(endTime) ? null : DateUtil.getLastTime(formatDate.parse(endTime)));

            pageNostr = request.getParameter("pageNos");
            if(pageNostr!=null && pageNostr!=""){
                pageNos = Integer.valueOf(pageNostr);
            }
            //查询该会员下资产总额
            depositInfo = depositInfoService.selectDepositInfoById(customerId);
            pageBean = tradeInfoService.selectTradeInfoList(depositInfoVo,pageBean);
        }catch (Exception e){
            LOGGER.error("查看会员资金明细：",e);
        }
        return new ModelAndView(CustomerConstantStr.DEPOSITINFOCUSTOMER,PAGEBEAN,pageBean).addObject("depositInfo",depositInfo).addObject("pageNos",pageNos).addObject("startTime", startTime).addObject("endTime", endTime);
    }

    /**
     * 预存款明细
     * @param depositInfoVo
     * @param pageBean
     * @return
     */
    @RequestMapping("/selectDepositInfoDetail")
    public ModelAndView selectDepositInfoDetail(HttpServletRequest request,DepositInfoVo depositInfoVo, PageBean pageBean){
        Map<String, Object> result = null;
        String startTime = null;
        String endTime = null;
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = request.getParameter("startTime");
            endTime = request.getParameter("endTime");
            depositInfoVo.setStartDate(startTime == null || "".equals(startTime) ? null : formatDate.parse(startTime));
            depositInfoVo.setEndDate(endTime == null || "".equals(endTime) ? null : DateUtil.getLastTime(formatDate.parse(endTime)));
            //查询平台总金额
            result = depositInfoService.selectTotalDesposit();
            pageBean.setUrl(CustomerConstantStr.SELECTDEPOSITINFODETAIL);
            LOGGER.info("查询预存款明细！");
            pageBean = tradeInfoService.selectTradeInfoList(depositInfoVo,pageBean);
        }catch (Exception e){
            LOGGER.error("预存款明细:",e);
        }
        return new ModelAndView(CustomerConstantStr.DEPOSITINFODETAIL,PAGEBEAN,pageBean).addObject("result", result).addObject("startTime", startTime).addObject("endTime", endTime);
    }
}
