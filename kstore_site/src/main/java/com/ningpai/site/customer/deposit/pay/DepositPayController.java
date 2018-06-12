package com.ningpai.site.customer.deposit.pay;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.alipay.config.AlipayConfig;
import com.ningpai.common.util.alipay.util.AlipayNotify;
import com.ningpai.deposit.bean.Deposit;
import com.ningpai.deposit.bean.Trade;
import com.ningpai.site.customer.deposit.bean.TradeConst;
import com.ningpai.site.customer.deposit.service.SiteDepositService;
import com.ningpai.site.customer.deposit.service.TradeService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.PayService;
import com.ningpai.util.UtilDate;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 支付
 */
@Controller
public class DepositPayController {

    private static Logger LOGGER = Logger.getLogger(DepositPayController.class);

    private static final String UTF_8 = "UTF-8";
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String OUT_TRADE_NO = "out_trade_no";
    private static final String TRADE_STATUS = "trade_status";
    private static final String SUCCESS = "success";
    private static final String FAIL = "success";

    @Resource(name = "payService")
    private PayService payService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private SiteDepositService depositService;

    @Resource(name = "customerServiceSite")
    private CustomerServiceInterface customerServiceInterface;

    @RequestMapping("/deposit/recharge")
    public void pay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        // 获取用户id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 判断用户id是否为空
        if (customerId == null) {
            response.getWriter().write("what are you doing ?????");
            return;
        }
        String _payId = request.getParameter("payId");
        Long payId = null;
        if(StringUtils.isNumeric(_payId)){
            payId = Long.valueOf(_payId);
        }
        Pay p = payService.findByPayId(payId);
        if(p == null){
            response.getWriter().write("请配置支付信息!");
            return;
        }

        //预存款
        String totalFee = request.getParameter("totalFee");
        BigDecimal test = null;
        try{
            test = new BigDecimal(totalFee);
        }catch (Exception ex){
            response.getWriter().write("请输入合法数字!");
            return;
        }

        if(test!=null){//负数
            if(test.compareTo(BigDecimal.ZERO)<0){
                response.getWriter().write("请输入合法数字!");
                return;
            }
        }
        //上限
        if(test.compareTo(TradeConst.MAX_PER_RECHARGE)>0){
            response.getWriter().write("充值大于上限!");
            return;
        }

        PayContext payContext = null;
        PayStrategy tPay = null;

        Map<String,String> paramMap = new HashMap<>();

        //Random random = new Random();
        int randomNum = new Random().nextInt(9000) + 1000;
        // 子订单号
        String orderCode = "R" + UtilDate.mathString(new Date()) + randomNum;

        BigDecimal totalPrice = new BigDecimal(totalFee);

        //做一个trade保存，转态设为初始值

        String remark = null;
        if ("1".equals(p.getPayType())) {// 支付宝支付
            remark = "在线充值-支付宝";
            tPay = new AliPay(p);
            String subject = customerId + "充值订单";
            String body = customerId + "预存款充值";
            //开始组装paramMap参数
            paramMap.put("out_trade_no", orderCode);
            paramMap.put("subject", subject);
            paramMap.put("total_fee", totalFee);
            paramMap.put("body", body);
            tPay.withParamMap(paramMap);

            payContext = new PayContext(tPay);
        }else if ("2".equals(p.getPayType())) {// 银联
            remark = "在线充值-银联";
            tPay = new UnionPay(p);
            // 总订单号
            paramMap.put(ConstantUtil.ORDERID, orderCode);
            // 交易金额 分
            BigDecimal payTotalPrice = new BigDecimal(totalFee);
            paramMap.put("txnAmt", payTotalPrice.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            tPay.withParamMap(paramMap);
            payContext = new PayContext(tPay);
        }else if ("4".equals(p.getPayType())) {//千米收银台
            remark = "在线充值-千米收银台";
            paramMap.put("orderCode",orderCode);
            paramMap.put("totalFee",totalFee);
            paramMap.put("productName",customerId + "预存款充值");

            tPay = new QianmiPay(p);
            tPay.withParamMap(paramMap);
            payContext = new PayContext(tPay);
        }

        saveTrade(customerId,orderCode,totalPrice,remark);
        String dom = null;
        if(payContext != null){
            dom = payContext.pay();
        }

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding(ISO_8859_1);
        response.setCharacterEncoding(ConstantUtil.UTF);
        response.getWriter().write(dom);
    }

    private int saveTrade(Long customerId,String orderCode,BigDecimal totalPrice,String remark){
        Map<String,Object> qParam = new HashMap<>();
        qParam.put("customerId",customerId);
        //查总账信息。获取可用的余额信息
        Deposit deposit = depositService.getDeposit(qParam);

        BigDecimal preDeposit = deposit.getPreDeposit();
        BigDecimal freezePreDeposit = deposit.getFreezePreDeposit();
        BigDecimal currentPrice = preDeposit.add(freezePreDeposit);
        Trade tTrade = new Trade();
        tTrade.setCustomerId(customerId);
        tTrade.setCreatePerson(customerId);
        tTrade.setCreateTime(new Date());
        tTrade.setOrderPrice(totalPrice);
        tTrade.setDelFlag("0");
        tTrade.setOrderCode(orderCode);
        tTrade.setOrderStatus("5");
        tTrade.setOrderType("0");
        tTrade.setTradeRemark(remark);
        //当前可用余额信息。
        tTrade.setCurrentPrice(currentPrice);
        int ret = tradeService.saveTrade(tTrade);
        return ret;
    }

    /**
     * 支付宝通知
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/deposit/alipaycallback")
    public void AliPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);
        // 设置   商户号
        AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        AlipayConfig.key = p.getSecretKey();
        AlipayConfig.seller_email = p.getPayAccount();

        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> paramMap = request.getParameterMap();
        for (Iterator<?> iter = paramMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) paramMap.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == values.length - 1) {
                    valueStr.append(values[i]);
                } else {
                    valueStr.append(values[i]).append(",");
                }
            }
            params.put(name, valueStr.toString());
        }
        String outTradeNo = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), UTF_8);
        //String tradeStatus = new String(request.getParameter(TRADE_STATUS).getBytes(ISO_8859_1), UTF_8);
        boolean verifyResult = AlipayNotify.verify(params);
        if(verifyResult){
            //根据outTradeNo，查询出trade，并将该trade状态修改为完成
            //Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
            Map<String,Object> qMap = new HashMap<>();
            qMap.put("orderCode",outTradeNo);
            Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
            if(trade == null){
                LOGGER.debug("没有查到充值订单信息");
                return;
            }
            Long customerId = trade.getCustomerId();
            Map<String,Object> param = new HashMap<>();
            param.put("orderCode",outTradeNo);
            param.put("status","6");//6数据库枚举，充值成功
            param.put("customerId",customerId);
            //tradeService.updateTradeStatusByOrderCode(param);
            //订单号不为空
            if (StringUtils.isBlank(outTradeNo)){
                LOGGER.info("==============银联回调订单号为空===========");
                return;
            }
            //保存
            tradeService.recharge(param);
        }else{

        }
    }

    /**
     * 银联通知
     * @param request
     * @param response
     */
    @RequestMapping("/deposit/unionpaycallback")
    public void unionPayCallback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException {

        LOGGER.debug("支付异步回调开始");
        try {
            request.setCharacterEncoding(ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("支付回调，字符转换错误" + e);
        }
        Map<String, String> respParam = getAllParam(request);
        LOGGER.debug("支付异步回调开始"+ respParam.toString());
        String encoding = request.getParameter(SDKConstants.param_encoding);
        Map<String, String> valideData = null;
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
            valideData = new HashMap<String, String>();
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = e.getKey();
                String value = e.getValue();
                value = new String(value.getBytes(ISO_8859_1), encoding);
                valideData.put(key, value);
            }
        }
        if (!SDKUtil.validate(valideData,encoding)) {
            LOGGER.info("==============银联异步回调验证出错===============");
            return;
        }
        if (!"00".equals(respParam.get("respCode"))){
            LOGGER.info("==============银联支付异步验证respCode出错===========");
            return;
        }
        String orderCode = respParam.get(ConstantUtil.ORDERID);
        Map<String,Object> qMap = new HashMap<>();
        qMap.put("orderCode",orderCode);
        Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
        if(trade == null){
            LOGGER.debug("没有查到充值订单信息");
            return;
        }
        Long customerId = trade.getCustomerId();
        Map<String,Object> param = new HashMap<>();
        param.put("orderCode",orderCode);
        param.put("status","6");//6数据库枚举，充值成功
        param.put("customerId",customerId);
        //订单号为空
        if (StringUtils.isBlank(orderCode)){
            LOGGER.info("==============银联异步回调订单号为空===========");
            return;
        }
        //保存
        tradeService.recharge(param);
        LOGGER.info("=================银联支付异步回调完成===========" + orderCode);
    }

    /**
     * 千米通知
     * @param request
     * @param response
     */
    @RequestMapping("/deposit/qianmipaycallback")
    public void qianmiPayCallback(HttpServletRequest request, HttpServletResponse response){
        String orderCode = request.getParameter("orderNo");//写错了orderno
        Map<String,Object> qMap = new HashMap<>();
        qMap.put("orderCode",orderCode);
        Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
        if(trade == null){
            LOGGER.debug("没有查到充值订单信息");
            return;
        }
        Long customerId = trade.getCustomerId();
        Map<String,Object> param = new HashMap<>();
        param.put("orderCode",orderCode);
        param.put("status","6");//6数据库枚举，充值成功
        param.put("customerId",customerId);
        //订单号为空
        if (StringUtils.isBlank(orderCode)){
            LOGGER.info("==============千米回调订单号为空===========");
            return;
        }
        //保存
        tradeService.recharge(param);
        LOGGER.info("=================千米回调支付===========" + orderCode);
    }

    /**
     * 银联获取所有参数
     * @param request
     * @return
     */
    public Map<String, String> getAllParam(final HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        while (temp.hasMoreElements()) {
            String en = (String) temp.nextElement();
            String value = request.getParameter(en);
            paramMap.put(en, value);
        }
        return paramMap;
    }

    //银联同步通知页面
    @RequestMapping("/deposit/synunionpayresult")
    public ModelAndView synUnionPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ModelAndView mav = null;
        String msg = null;
        try {
            request.setCharacterEncoding(ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("支付回调，字符转换错误" + e);
            mav = new ModelAndView("deposit/syncresult");
            msg = "支付回调，字符转换错误";
            mav.addObject("msg",msg);
            return mav;
        }
        Map<String, String> respParam = getAllParam(request);
        String encoding = request.getParameter(SDKConstants.param_encoding);
        Map<String, String> valideData = null;
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
            valideData = new HashMap<String, String>();
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = e.getKey();
                String value = e.getValue();
                value = new String(value.getBytes(ISO_8859_1), encoding);
                valideData.put(key, value);
            }
        }
        String orderCode = respParam.get(ConstantUtil.ORDERID);
        if (!SDKUtil.validate(valideData,encoding)) {
            LOGGER.info("==============银联同步回调验证出错===============");
            //Map<String,Object> tmap = new HashMap<>();
            //tmap.put("status",TradeConst.TYPE_ORDER_STATUS_RECHARGE_FAIL);
            //tmap.put("orderCode",orderCode);
            //tradeService.updateTradeStatusByOrderCode(tmap);
            msg = "银联充值失败";
            mav = new ModelAndView("deposit/syncresult");
            msg = "银联同步回调验证出错";
            mav.addObject("msg",msg);
            return mav;
        }
        if (!"00".equals(respParam.get("respCode"))){
            LOGGER.info("==============银联支付验证respCode出错===========");
            //Map<String,Object> tmap = new HashMap<>();
            //tmap.put("status",TradeConst.TYPE_ORDER_STATUS_RECHARGE_FAIL);
            //tmap.put("orderCode",orderCode);
            //tradeService.updateTradeStatusByOrderCode(tmap);
            msg = "银联充值失败";
            mav = new ModelAndView("deposit/syncresult");
            msg = "银联支付验证respCode出错";
            mav.addObject("msg",msg);
            return mav;
        }
        Map<String,Object> qMap = new HashMap<>();
        qMap.put("orderCode",orderCode);
        Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
        if(trade == null){
            LOGGER.debug("没有查到充值订单信息");
            mav = new ModelAndView("deposit/syncresult");
            msg = "没有查到充值订单信息";
            mav.addObject("msg",msg);
            return mav;
        }
        Long customerId = trade.getCustomerId();
        //同步回调视图,填充session
        CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(customerId);
        request.getSession().setAttribute("cust", ca);
        request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
        //提现状态 0待审核 1打回 2【充值】通过,【提现】待确认 3确认 4已完成 5充值中 6充值成功 7充值失败 8已取消
        String status = trade.getOrderStatus();
        //如果充值中状态，修改状态
        if(TradeConst.TYPE_ORDER_STATUS_RECHARGING.equals(status)){
            //状态为成功表示已经修改了，直接返回视图
            Map<String,Object> param = new HashMap<>();
            param.put("orderCode",orderCode);
            param.put("status","6");//6数据库枚举，充值成功
            param.put("customerId",customerId);
            //保存
            tradeService.recharge(param);
            msg = "充值成功";
        }else if(TradeConst.TYPE_ORDER_STATUS_RECHARGE_SUCCESS.equals(status)){
            //已经充值成功
            //mav = new ModelAndView("deposit/syncresult");
            //可能同步回调完成了
            msg = "充值成功";
        }else{
            msg = "其他订单状态";
        }
        mav = new ModelAndView("deposit/syncresult");
        mav.addObject("msg",msg);
        LOGGER.info("=================银联支付===========" + orderCode);
        return mav;
    }
    //阿里支付宝同步通知页面
    @RequestMapping("/deposit/synalipayresult")
    public ModelAndView synAliPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException{

        ModelAndView mav = null;
        String msg = null;
        Map<String, Object> map = null;
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator it = requestParams.keySet().iterator(); it.hasNext(); ) {
            String name = (String) it.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            StringBuilder buf = null;
            for (int i = 0; i < values.length; i++) {
                buf = new StringBuilder();
                if (i == values.length - 1) {
                    buf.append(values[i]);
                } else {
                    buf.append(values[i]);
                    buf.append(",");
                }
                valueStr += buf.toString();
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes(ISO_8859_1), UTF_8);
            } catch (UnsupportedEncodingException e) {
                LOGGER.info("支付失败" + e);
                mav = new ModelAndView("deposit/syncresult");
                msg = "支付宝同步回调编码出错。";
                mav.addObject("msg",msg);
                return mav;
            }
            params.put(name, valueStr);
        }
        Long customerId = null;
        String status = null;
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        try {
            // 商户订单号
            String orderCode = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), UTF_8);
            // 交易状态
            String tradeStatus = new String(request.getParameter(TRADE_STATUS).getBytes(ISO_8859_1), UTF_8);
            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
            boolean verifyResult = AlipayNotify.verify(params);

            Map<String,Object> qMap = new HashMap<>();
            qMap.put("orderCode",orderCode);
            Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
            if(trade == null){
                LOGGER.debug("没有查到充值订单信息");
                mav = new ModelAndView("deposit/syncresult");
                msg = "没有查到充值订单信息";
                mav.addObject("msg",msg);
                return mav;
            }

            customerId = trade.getCustomerId();
            status = trade.getOrderStatus();

            //如果充值中状态，修改状态
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
                //充值中
                if(TradeConst.TYPE_ORDER_STATUS_RECHARGING.equals(status)){
                    //状态为成功表示已经修改了，直接返回视图
                    Map<String,Object> param = new HashMap<>();
                    param.put("orderCode",orderCode);
                    param.put("status","6");//6数据库枚举，充值成功
                    param.put("customerId",customerId);
                    //保存
                    tradeService.recharge(param);
                    msg = "充值成功";
                }else if(TradeConst.TYPE_ORDER_STATUS_RECHARGE_SUCCESS.equals(status)){
                    //已经充值成功
                    //mav = new ModelAndView("deposit/syncresult");
                    //可能异步回调完成了
                    msg = "充值成功";
                }else{
                    msg = "其他订单状态";
                }
            }else{
                //Map<String,Object> tmap = new HashMap<>();
                //tmap.put("status",TradeConst.TYPE_ORDER_STATUS_RECHARGE_FAIL);
                //tmap.put("orderCode",orderCode);
                //改为失败状态
                //tradeService.updateTradeStatusByOrderCode(tmap);
                msg = "支付宝充值失败";
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("" + e);
            msg = "编码解析出错，充值失败";
        }
        //同步回调视图，填充session
        CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(customerId);
        request.getSession().setAttribute("cust", ca);
        request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());

        mav = new ModelAndView("deposit/syncresult");
        mav.addObject("msg",msg);
        return mav;
    }
    //千米同步通知页面
    @RequestMapping("/deposit/synqianmipayresult")
    public ModelAndView synQianmiPayCallback(HttpServletRequest request, HttpServletResponse response) throws IOException{

        ModelAndView mav = null;
        String msg = null;
        // 订单号
        String orderCode = request.getParameter("orderno");
        if(StringUtils.isBlank(orderCode)){
            LOGGER.debug("千米同步回调订单号为空");
            mav = new ModelAndView("deposit/syncresult");
            msg = "千米同步回调订单号为空";
            mav.addObject("msg",msg);
            return mav;
        }else{

        }
        Map<String,Object> qMap = new HashMap<>();
        qMap.put("orderCode",orderCode);
        Trade trade = tradeService.findByOrderCodeAndCustomerId(qMap);
        if(trade == null){
            LOGGER.debug("没有查到充值订单信息");
            mav = new ModelAndView("deposit/syncresult");
            msg = "没有查到充值订单信息";
            mav.addObject("msg",msg);
            return mav;
        }
        Long customerId = trade.getCustomerId();
        String status = trade.getOrderStatus();
        //如果充值中状态，修改状态
        if(TradeConst.TYPE_ORDER_STATUS_RECHARGING.equals(status)){
            //状态为成功表示已经修改了，直接返回视图
            Map<String,Object> param = new HashMap<>();
            param.put("orderCode",orderCode);
            param.put("status","6");//6数据库枚举，充值成功
            param.put("customerId",customerId);
            //保存
            tradeService.recharge(param);
            msg = "充值成功";
        }else if(TradeConst.TYPE_ORDER_STATUS_RECHARGE_SUCCESS.equals(status)){
            //可能同步回调完成了
            msg = "充值成功";
        }else{
            msg = "其他订单状态";
        }
        CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(customerId);
        request.getSession().setAttribute("cust", ca);
        request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
        mav = new ModelAndView("deposit/syncresult");
        mav.addObject("msg",msg);
        return mav;
    }

    //会刷信息到前端
    private void echo(HttpServletResponse response, String msg) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }
}