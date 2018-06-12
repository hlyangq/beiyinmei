package com.ningpai.m.deposit.controller;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.common.util.alipaymobile.util.AlipayNotify;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.service.DepositInfoService;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.service.PayService;
import com.ningpai.util.MyLogger;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 预存款信息controller
 * Created by chenpeng on 2016/10/9.
 */
@Controller
public class DepositInfoControllerM{

    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String UTF_8 = "UTF-8";
    private static final String SUCCESS = "SUCCESS";

    @Resource(name = "depositInfoServiceM")
    private DepositInfoService depositInfoService;

    @Resource(name = "SeoService")
    private SeoService seoService;

    @Resource(name = "customerServiceM")
    private CustomerService customerService;

    @Resource(name = "payService")
    private PayService payService;

    private static final String OUT_TRADE_NO = "out_trade_no";
    private static final MyLogger LOGGER = new MyLogger(DepositInfoControllerM.class);

    /**
     * 获取手机验证结果
     * @param request
     * @return true:已验证 false:为验证
     */
    @RequestMapping("/mobilevalidation")
    @ResponseBody
    public boolean getMobileValidationResult(HttpServletRequest request){
        boolean result = false;
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            //手机验证
            result = depositInfoService.checkMobileValidation(customerId);
        }
        return result;
    }

    /**
     * 跳转支付密码设置页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/topaypassword")
    public ModelAndView toPayPassword(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = null;
        boolean result = false;
        if (LoginUtil.checkLoginStatus(request)) {
            modelAndView = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            //手机验证
            result = depositInfoService.checkMobileValidation(customerId);
            if (result){
//                DepositInfo depositInfo = depositInfoService.queryDepositInfo(customerId);
                //是否设置了支付密码
//                if(StringUtils.isNotEmpty(depositInfo.getPayPassword()) ){
                    modelAndView = new ModelAndView(CustomerConstants.MODIFYPAYPASSWORD);
                    modelAndView.addObject(
                            "custInfo",
                            customerService.selectByPrimaryKey(
                                    (Long) request.getSession().getAttribute(
                                            CustomerConstants.CUSTOMERID)));
//                }else{
//                    modelAndView = new ModelAndView(CustomerConstants.PAYPASSWORD);
//                }
            }
        }

        return seoService.getCurrSeo(modelAndView);
    }


    /**
     * 发送手机验证码
     *
     * @throws IOException
     */
    @RequestMapping("/sendpaycode")
    @ResponseBody
    public int sendPayCode(HttpServletRequest request) throws IOException {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        return customerService.sendPayCode(request, customerId);
    }

    /**
     * 支付密码设置验证
     *
     * @return 0不同 1相同
     * @throws IOException
     */
    @RequestMapping("/validatepaycode")
    @ResponseBody
    public int validatePayCode(HttpServletRequest request, String code)
            throws IOException {
        return depositInfoService.validatePayCode(request, code);
    }

    /**
     * 提交支付密码设置
     * @param request
     * @param code 手机验证码
     * @param password 支付密码
     * @return 0失败 1成功
     */
    @RequestMapping("/submitpayset")
    @ResponseBody
    public int submitPaySet(HttpServletRequest request, String password){
        return depositInfoService.setPayPassword(request, password);
    }

    /**
     * 查看我的预存款
     * @param request
     * @return
     */
    @RequestMapping("/showmypredeposits")
    public ModelAndView showMyPredeposits(HttpServletRequest request){
        ModelAndView modelAndView = null;
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
                modelAndView = new ModelAndView(CustomerConstants.MYPREDEPOSITS)
                        .addObject("deposit", depositInfoService.queryDepositInfo(customerId));
        }else {
            modelAndView = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }

        return seoService.getCurrSeo(modelAndView);
    }

    /**
     * 跳转充值页面
     * @param request
     * @return
     */
    @RequestMapping("/mypredepositsrecharge")
    public ModelAndView myPredepositsRecharge(HttpServletRequest request){
        ModelAndView modelAndView = null;
        if (LoginUtil.checkLoginStatus(request)) {
            modelAndView = new ModelAndView(CustomerConstants.MYPREDEPOSITSRECHARGE)
                    .addObject("payments", payService.selectMobilePay());
        }else {
            modelAndView = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }

        return seoService.getCurrSeo(modelAndView);
    }

    /**
     * 微信充值
     * @param request
     * @param response
     * @param orderPrice
     * @param orderType
     * @return
     */
    @RequestMapping("/wxrecharge")
    @ResponseBody
    public Map<String, Object> wxReharge(HttpServletRequest request,HttpServletResponse response,BigDecimal orderPrice){
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            return depositInfoService.getBrandWCPayRequest(request, response,customerId, orderPrice, "0");
        }
        return null;

    }


    /**
     * 微信充值回调
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/wxrechargepaysuc")
    public void wxpaySuc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        // xml解析
        Document document;
        try {
            document = DocumentHelper.parseText(sb.toString());
            Element root = document.getRootElement();
            // 微信号
            String appId = "";
            // 订单号
            String outTradeNo = "";

            // 返回结果
            String resultCode = "";
            // 商户编号
            String mId = "";

            String returnMsg = "";

            List<Element> elements = root.elements();
            for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
                Element element = it.next();
                if ("appid".equals(element.getName())) {
                    appId = element.getText();
                } else if ("bank_type".equals(element.getName())) {
                    element.getText();
                } else if ("mch_id".equals(element.getName())) {
                    mId = element.getText();
                } else if ("result_code".equals(element.getName())) {
                    resultCode = element.getText();
                } else if (OUT_TRADE_NO.equals(element.getName())) {
                    outTradeNo = element.getText();
                } else if ("FAIL".equals(resultCode) && "return_msg".equals(element.getName())) {
                    returnMsg = element.getText();
                }

            }

            boolean result = depositInfoService.weixinRechargeNotify(outTradeNo, appId, resultCode, mId);
            if (result) {
                sendSucess(response, "SUCCESS");
            } else {
                sendSucess(response, returnMsg);
            }
        } catch (DocumentException e1) {
            LOGGER.error("微信支付错误：" + e1);
            sendSucess(response, "FAIL");
        }
    }

        /**
         * 成功回调
         * */
    public void sendSucess(HttpServletResponse response, String msg) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }

    /**
     * 验证预存款是否可用
     * @param request
     * @return
     */
    @RequestMapping("/checkDepositPay")
    @ResponseBody
    public JSONObject checkDepositPay(HttpServletRequest request,BigDecimal orderPrice,String type){
        // 用户编号
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        return depositInfoService.checkDepositPay(customerId,orderPrice,type);
    }

    /**
     * 支付密码下一步
     * @param request
     * @param code 验证码
     * @return
     */
    @RequestMapping("/tonextpassword")
    public ModelAndView toNextPassword(HttpServletRequest request,String code){
        ModelAndView modelAndView = null;
        if (LoginUtil.checkLoginStatus(request)) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            //验证手机短信验证码
            int result = depositInfoService.validatePayCode(request, code);
            if (result == 1){
                modelAndView = new ModelAndView(CustomerConstants.NEXTPAYPASSWORD);
            }else {
                modelAndView = new ModelAndView(CustomerConstants.MODIFYPAYPASSWORD)
                        .addObject(
                                "custInfo",
                                customerService.selectByPrimaryKey(customerId))
                                        .addObject("error_msg", "手机验证码错误");
            }
        }else {
            modelAndView = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }

        return seoService.getCurrSeo(modelAndView);
    }

    /**
     * 支付宝充值
     * @param request
     * @param rechargePrice
     * @return
     */
    @RequestMapping("/zhifubaopecharge")
    public ModelAndView zhifubaoRecharge(HttpServletRequest request,BigDecimal rechargePrice){
        ModelAndView modelAndView = new ModelAndView();
        if (LoginUtil.checkLoginStatus(request) && rechargePrice.compareTo(new BigDecimal(1000000))<=0) {
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            modelAndView.addObject("sHtmlText", depositInfoService.zhifubaoRecharge(rechargePrice, customerId));
            modelAndView.setViewName("order/netbank");
        }else {
            modelAndView = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }

        return seoService.getCurrSeo(modelAndView);
    }


    /**
     * 同步充值成功
     *
     * @param request
     * @param agr1
     * @return ModelAndView
     */
    @RequestMapping("/synrechargesucccess")
    public ModelAndView synRechargeSucccess(HttpServletRequest request) {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);

        // 设置商户号
        com.ningpai.common.util.alipay.config.AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        com.ningpai.common.util.alipay.config.AlipayConfig.key = p.getSecretKey();

        com.ningpai.common.util.alipay.config.AlipayConfig.seller_email = p.getPayAccount();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == values.length - 1) {
                    valueStr.append(values[i]);
                } else {
                    valueStr.append(values[i]);
                    valueStr.append(",");
                }
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes(ISO_8859_1), "gbk");
            params.put(name, valueStr.toString());
        }

        // 订单号
        String orderCode = null;
        try {
            orderCode = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), UTF_8);
            // 支付宝交易号

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取支付宝交易号错误：" + e);
        }

        // 计算得出通知验证结果
        boolean verifyResult = AlipayNotify.verify(params);
        // 重新将session登入
        if (verifyResult) {// 验证成功

            // 更新订单状态
            depositInfoService.zhifubaoRechargeNotify(orderCode);

        }
        ModelAndView mav = new ModelAndView("order/recharg_success");

        return seoService.getCurrSeo(mav);

    }

    @RequestMapping("/asynrechargesucccess")
    public void asynrechargesucccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);

        // 设置商户号
        com.ningpai.common.util.alipay.config.AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        com.ningpai.common.util.alipay.config.AlipayConfig.key = p.getSecretKey();

        com.ningpai.common.util.alipay.config.AlipayConfig.seller_email = p.getPayAccount();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == values.length - 1) {
                    valueStr.append(values[i]);
                } else {
                    valueStr.append(values[i]);
                    valueStr.append(",");
                }
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes(ISO_8859_1), "gbk");
            params.put(name, valueStr.toString());
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        String outTradeNo = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), UTF_8);

        // 交易状态
        String tradeStatus = new String(request.getParameter("trade_status").getBytes(ISO_8859_1), UTF_8);
        // RSA签名解密
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

        if (AlipayNotify.verify(params)) {// 验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码

            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if ("TRADE_FINISHED".equals(tradeStatus)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                depositInfoService.zhifubaoRechargeNotify(outTradeNo);

                // System.out.println(SUCCESS); // 请不要修改或删除
                // 注意：
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                depositInfoService.zhifubaoRechargeNotify(outTradeNo);
                sendSucess(response, SUCCESS);
                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知
            }

            // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            // System.out.println(SUCCESS); // 请不要修改或删除

            // ////////////////////////////////////////////////////////////////////////////////////////
        } else {// 验证失败
            System.out.println("fail");
        }

    }
}
