package com.ningpai.m.deposit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.common.util.alipaymobile.config.AlipayConfig;
import com.ningpai.common.util.alipaymobile.util.AlipaySubmit;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.m.customer.vo.CustomerAllInfo;
import com.ningpai.m.deposit.bean.DepositInfo;
import com.ningpai.m.deposit.bean.DepositInfoCons;
import com.ningpai.m.deposit.bean.TradeInfo;
import com.ningpai.m.deposit.mapper.DepositInfoMapper;
import com.ningpai.m.deposit.service.DepositInfoService;
import com.ningpai.m.deposit.service.TradeInfoService;
import com.ningpai.m.order.controller.OrderMController;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.order.bean.Order;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.bean.Receivables;
import com.ningpai.system.service.PayService;
import com.ningpai.system.service.ReceivablesService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PropertieUtil;
import com.ningpai.utils.SecurityUtil;
import com.ningpai.wxpay.utils.GetWxOrderno;
import com.ningpai.wxpay.utils.RequestHandlerUtil;
import com.ningpai.wxpay.utils.Sha1Util;
import com.ningpai.wxpay.utils.TenpayUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 预存款信息service实现
 * Created by chenpeng on 2016/10/9.
 */
@Service("depositInfoServiceM")
public class DepositInfoServiceImpl implements DepositInfoService {

    @Resource(name = "customerMapperM")
    private CustomerMapper customerMapper;

    @Resource(name = "DepositInfoMapperM")
    private DepositInfoMapper depositInfoMapper;

    @Resource(name = "tradeInfoServiceM")
    private TradeInfoService tradeInfoService;

    @Resource(name = "payService")
    private PayService payService;

    @Resource(name = "receivablesService")
    private ReceivablesService receivablesService;

    private static final String SUCCESS_RETURN_CODE = "SUCCESS";

    @Resource(name = "OrderMService")
    private OrderMService siteOrderService;

    private static final String OUT_TRADE_NO = "out_trade_no";

    private static final MyLogger LOGGER = new MyLogger(DepositInfoServiceImpl.class);

    /**
     * 验证手机(设置支付密码时)
     *
     * @param customerId 用户id
     * @return true:已验证 false:未验证
     */
    @Override
    public boolean checkMobileValidation(Long customerId) {
        CustomerAllInfo customerAllInfo = customerMapper.selectByPrimaryKey(customerId);
        if (customerAllInfo != null){
            if (StringUtils.isNotEmpty(customerAllInfo.getInfoMobile())
                    && StringUtils.isNotEmpty(customerAllInfo.getIsMobile())){
                return true;
            }
        }
        return false;
    }

    /**
     * 支付密码设置验证
     *
     * @param request
     * @param code
     * @return
     */
    @Override
    public int validatePayCode(HttpServletRequest request, String code) {
        if(request.getSession().getAttribute("payCode") == null){
            return 0;
        }
        if (code.equals((int) request.getSession().getAttribute("payCode") + "")) {
            return 1;
        }
        return 0;
    }

    /**
     * 支付密码设置
     *
     * @param request
     * @param code     手机验证码
     * @param password 新密码
     * @return 0失败 1成功
     */
    @Override
    public int setPayPassword(HttpServletRequest request, String password) {
        Long customerId = (Long)request.getSession().getAttribute("customerId");
        CustomerAllInfo customerInfo = customerMapper.selectByPrimaryKey(customerId);
        //加密处理
        String securityPassword = SecurityUtil.getStoreLogpwd(customerInfo.getUniqueCode(), password, customerInfo.getSaltVal());
        DepositInfo depositInfo = new DepositInfo();
        depositInfo.setPayPassword(securityPassword);
        depositInfo.setCustomerId(customerId);
        //更新支付密码
        return depositInfoMapper.updateDepositInfo(depositInfo);
    }

    /**
     * 查询用户预付款信息
     *
     * @param customerId 用户id
     * @return
     */
    @Override
    public DepositInfo queryDepositInfo(Long customerId) {
        DepositInfo depositInfo = depositInfoMapper.selectDepositByCustId(customerId);
        if(depositInfo == null){
            depositInfo = new DepositInfo();
            depositInfo.setCustomerId(customerId);
            depositInfo.setFreezePreDeposit(BigDecimal.ZERO);
            depositInfo.setPreDeposit(BigDecimal.ZERO);
            depositInfo.setPasswordErrorCount(0);
            depositInfoMapper.insertSelective(depositInfo);
            depositInfo = depositInfoMapper.selectDepositByCustId(customerId);
        }
        return depositInfo;
    }

    /**
     * 获取getBrandWCPayRequest参数
     *
     * @param customerId 用户id
     * @param orderPrice 交易价格
     * @param orderType  交易类型
     * @return getBrandWCPayRequest参数
     */
    @Override
    public Map<String, Object> getBrandWCPayRequest(HttpServletRequest request, HttpServletResponse response,Long customerId, BigDecimal orderPrice, String orderType) {
        String payType = "0";
        if(orderPrice.compareTo(new BigDecimal(1000000)) > 0){
            return null;
        }
        DepositInfo depositInfo = depositInfoMapper.selectDepositByCustId(customerId);
        //生成预付款交易记录
        TradeInfo tradeInfo = tradeInfoService.insertSelective(customerId, orderPrice, orderType, payType, "在线充值-微信",depositInfo);
        //微信支付信息
        Pay pay = payService.selectWxPay();
        //获取支付信息
        if (tradeInfo == null){
            return null;
        }
        //获取getBrandWCPayRequest参数
        return brandWCPayRequest(request, response, tradeInfo, pay);
    }

    /**
     * 微信参数
     * @param request
     * @param response
     * @param tradeInfo
     * @param pay
     * @return
     */
    private Map<String, Object> brandWCPayRequest(HttpServletRequest request, HttpServletResponse response,TradeInfo tradeInfo,Pay pay  ){
        // 网页授权后获取传递的参数
        String userId = request.getParameter("userId");
        if(request.getSession().getAttribute("openid") == null){
            return null;
        }
        String openId = request.getSession().getAttribute("openid").toString();
//        String openId = "";
        // 金额转化为分为单位
        String finalmoney = String.format("%.2f", tradeInfo.getOrderPrice());
        finalmoney = finalmoney.replace(".", "");
        int intMoney = Integer.parseInt(finalmoney);
        // 总金额以分为单位，不带小数点
        int totalFee = intMoney;
        // 商户相关资料
        String appid = pay.getApiKey();
        String appsecret = pay.getSecretKey();
        String partner = pay.getPartner();
        String partnerkey = pay.getPartnerKey();

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        // 随机数
        String nonceStr = strReq;

        // 商户号
        String mchId = partner;
        // 子商户号 非必输
        // String sub_mch_id="";

        // 商品描述
        // String body = describe;

        String body = "预存款充值 ";
        // 附加数据
        String attach = userId;
        // 商户订单号
        String outTradeNo = tradeInfo.getOrderCode();

        // 订单生成的机器 IP
        String spbillCreateIp = request.getRemoteAddr();
//        String spbillCreateIp ="123.12.12.123";
        // 订 单 生 成 时 间 非必输
        // 订单失效时间 非必输
        // 商品标记 非必输
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notifyUrl = pay.getBackUrl();
        notifyUrl = notifyUrl.substring(0, notifyUrl.lastIndexOf("/")) + "/wxrechargepaysuc.htm";

        String tradeType = "JSAPI";

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mchId);
        // packageParams.put("device_info", device_info);    // no-required
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", outTradeNo);

//        packageParams.put("attach", attach);


        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", String.valueOf(totalFee));
        packageParams.put("spbill_create_ip", spbillCreateIp);
        packageParams.put("notify_url", notifyUrl);

        packageParams.put("trade_type", tradeType);
        packageParams.put("openid", openId);

        RequestHandlerUtil reqHandler = new RequestHandlerUtil(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        //签名
        String sign = reqHandler.createSign(packageParams);
        /*String xml = "<xml>"
                + "<appid>" + appid + "</appid>"
                + "<mch_id>" + mchId + "</mch_id>"
                + "<nonce_str>" + nonceStr + "</nonce_str>"
                + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                +
                // 金额，这里写的1 分到时修改
                "<total_fee>" + totalFee + "</total_fee>"
                +
                "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>"
                + "<notify_url>" + notifyUrl + "</notify_url>"
                + "<trade_type>" + tradeType + "</trade_type>"
                + "<openid>"+openId +"</openid>"
                + "</xml>";*/
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mchId + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                + "<total_fee>" + totalFee + "</total_fee>"
                + "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>" + "<notify_url>" + notifyUrl + "</notify_url>" + "<trade_type>" + tradeType + "</trade_type>"
                + "<openid>" + openId + "</openid>" + "</xml>";
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//        String createOrderURL = "https://api.mch.weixin.qq.com/sandbox/pay/unifiedorder";
        String prepayId = "";
        try {
            prepayId = GetWxOrderno.getPayNo(createOrderURL, xml);
            if ("".equals(prepayId)) {
                request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                response.sendRedirect("error_page.jsp");
            }
        } catch (Exception e1) {
//            LOGGER.info(e1);
            prepayId = null;
        }
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonceStr;
        String prepayId2 = "prepay_id=" + prepayId;
        String packages = prepayId2;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appid2);
        map.put("timeStamp", timestamp);
        map.put("nonceStr", nonceStr2);
        map.put("package", packages);
        map.put("sign", finalsign);

        return map;
    }

    /**
     * 微信充值通知处理
     *
     * @param outTradeNo 交易单号
     * @param appId      appid
     * @param resultCode 返回状态码
     * @param mId        商户号
     * @return success:成功 fail:失败
     */
    @Override
    @Transactional
    public boolean weixinRechargeNotify(String outTradeNo, String appId, String resultCode, String mId) {
        //微信支付信息
        Pay pay = payService.selectWxPay();
        //交易记录
        TradeInfo tradeInfo = tradeInfoService.selectByTradeNo(outTradeNo);
        if (SUCCESS_RETURN_CODE.equals(resultCode) && appId.equals(pay.getApiKey()) && mId.equals(pay.getPartner())
                && "5".equals(tradeInfo.getOrderStatus())) {
            tradeInfo.setOrderStatus("6");
            //充值成功时，当前余额
            tradeInfo.setCurrentPrice(tradeInfo.getCurrentPrice().add(tradeInfo.getOrderPrice()));
            //更新交易状态
            tradeInfoService.updateTradeInfo(tradeInfo);
            DepositInfo depositInfo1 = depositInfoMapper.selectDepositByCustId(tradeInfo.getCustomerId());
            //更新预存款信息
            DepositInfo depositInfo = new DepositInfo();
            depositInfo.setCustomerId(tradeInfo.getCustomerId());
            depositInfo.setPreDeposit(tradeInfo.getOrderPrice().add(depositInfo1.getPreDeposit()));
            depositInfoMapper.updateDepositInfo(depositInfo);
            LOGGER.info("=================================微信充值回调成功 单号："+ outTradeNo);
            return true;
        }
        LOGGER.info("=================================微信充值回调失败 单号："+ outTradeNo);
        return false;
    }

    /**
     * 验证支付密码
     *
     * @param payPassword 支付密码
     * @param customerId  用户id
     * @param type 0:支付 1:提现
     * @return
     */
    @Override
    @Transactional
    public JSONObject checkPayPassword(String payPassword, Long customerId, String type,BigDecimal price) {
        JSONObject resultJson = new JSONObject();
        DepositInfo depositInfo =  depositInfoMapper.selectDepositByCustId(customerId);
        String password = depositInfo.getPayPassword();
        int errorCount = depositInfo.getPasswordErrorCount() != null ? depositInfo.getPasswordErrorCount() :0;
        //您还没有设置支付密码
        if (StringUtils.isEmpty(password)){
            if("0".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_SET_PAYPASSWORD0);
            }else if ("1".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_SET_PAYPASSWORD1);
            }

            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.FAIL);
            resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.PASS_FAIL_CODE);
        }else if(errorCount >= DepositInfoCons.ERROR_THRESHOLD){
            //预存款账户锁定,无法支付
            if("0".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.REACHE_ERROR_THRESHOLD_TIPS);
            }else if ("1".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.REACHE_ERROR_THRESHOLD_TIPS1);
            }

            resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.FAIL);
            resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.FROZEN_FAIL_CODE);
        }else if(price != null && (depositInfo.getPreDeposit().compareTo(price) <0 || price.signum() <= 0)){
                resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.FAIL);
                resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.BALANCE_FAIL_CODE);
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.DEPOSIT_LESS);
        }else {
            CustomerAllInfo customerAllInfo = customerMapper.selectByPrimaryKey(customerId);
            String encrypPassword = SecurityUtil.getStoreLogpwd(customerAllInfo.getUniqueCode(), payPassword, customerAllInfo.getSaltVal());
            if (!StringUtils.equals(encrypPassword, password)){
                resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.FAIL);
                errorCount++;
                if (errorCount>= DepositInfoCons.ERROR_THRESHOLD){
                    //预存款账户锁定,无法支付
                    resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.REACHE_ERROR_THRESHOLD_TIPS);
                    resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.FROZEN_FAIL_CODE);
                }else {
                    int leftCount = DepositInfoCons.ERROR_THRESHOLD - errorCount;
                    if (leftCount > 0 ){
                        resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_REACHE_ERROR_THRESHOLD_TIPS + leftCount +"次机会");
                    }
                }
                //更新预存款错误信息
                DepositInfo depositInfo1 = new DepositInfo();
                depositInfo1.setCustomerId(customerId);
                depositInfo1.setPasswordErrorCount(errorCount);
                depositInfo1.setPasswordTime(new Date());
                depositInfoMapper.updateDepositInfo(depositInfo1);
            }else {
                resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.SUCCESS);
            }
        }
        return resultJson;
    }

    /**
     * 预存款支付
     *
     * @param order      待支付订单
     * @param customerId 用户id
     * @return
     */
    @Override
    @Transactional
    public JSONObject depositPay(Order order, Long customerId,String payPassword) {
        JSONObject resultJson = new JSONObject();
        DepositInfo depositInfo =  depositInfoMapper.selectDepositByCustId(customerId);

        //验证支付密码
        resultJson = this.checkPayPassword(payPassword, customerId, "0", null);
        if (StringUtils.equals(DepositInfoCons.FAIL, resultJson.get(DepositInfoCons.RETURN_CODE).toString())){
            return resultJson;
        }
        //预存款金额小于支付金额
        if (depositInfo.getPreDeposit().compareTo(order.getOrderPrice()) < 0){
            resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.DEPOSIT_LESS);
            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.FAIL);
        }else {
            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.SUCCESS);

            //更新订单状态
            Order or = siteOrderService.getPayOrderByCode(order.getOrderCode());
            if (or == null){
                List<Order> orders= siteOrderService.queryOrderByOrderOldCode(order.getOrderCode());
                for (int i = 0; i<orders.size(); i++){
                    //查询订单对应的收款单
                    Receivables receivables = this.receivablesService.queryByOrderCode(orders.get(i).getOrderCode());
                    if ("0".equals(orders.get(i).getOrderStatus())) {
                        // 更新订单状态
                        int result = siteOrderService.paySuccessUpdateOrder(orders.get(i).getOrderId(),"1",order.getPayId());
//                        int result =  siteOrderService.payOrder(orders.get(i).getOrderId());
                        if (null != receivables && result >0) {
                            //支付成功更新预存款相关信息
                            updateDepositInfo(depositInfo, orders.get(i), customerId);
                            // 修改收款单订单支付状态为支付成功
                            receivables.setReceivablesTime(new Date());
                            this.receivablesService.updatePayStatus(receivables);
                            //支付成功直营店订单短信通知
                            siteOrderService.paySuccessSendSms(orders.get(i));
                        }
                    }
                }
            }else{
                //查询订单对应的收款单
                Receivables receivables = this.receivablesService.queryByOrderCode(or.getOrderCode());
                if ("0".equals(or.getOrderStatus())) {
                    // 更新订单状态
                    int result = siteOrderService.paySuccessUpdateOrder(order.getOrderId(), "1", order.getPayId());
//                    int result = siteOrderService.payOrder(or.getOrderId());
                    if (null != receivables && result >0) {
                        //支付成功更新预存款相关信息
                        updateDepositInfo(depositInfo, order, customerId);
                        // 修改收款单订单支付状态为支付成功
                        receivables.setReceivablesTime(new Date());
                        this.receivablesService.updatePayStatus(receivables);
                        //支付成功直营店订单短信通知
                        siteOrderService.paySuccessSendSms(or);
                    }
                }
            }
        }
        return resultJson;
    }

    /**
     * 验证预存款是否可用
     *
     * @param customerId 用户id
     * @return
     */
    @Override
    public JSONObject checkDepositPay(Long customerId,BigDecimal orderPrice,String type) {
        JSONObject resultJson = new JSONObject();
        DepositInfo depositInfo = this.queryDepositInfo(customerId);
//        DepositInfo depositInfo =  depositInfoMapper.selectDepositByCustId(customerId);
        if (depositInfo.getPasswordErrorCount() != null && depositInfo.getPasswordErrorCount() >= DepositInfoCons.ERROR_THRESHOLD){
            //预存款账户锁定,无法支付
            if("0".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.REACHE_ERROR_THRESHOLD_TIPS);
            }else if("1".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.REACHE_ERROR_THRESHOLD_TIPS1);
            }
            resultJson.put(DepositInfoCons.RETURN_CODE, DepositInfoCons.FAIL);
            resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.FROZEN_FAIL_CODE);
            return resultJson;
        }else if(StringUtils.isEmpty(depositInfo.getPayPassword())){
            //您还没有设置支付密码
            if("0".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_SET_PAYPASSWORD0);
            }else if("1".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_SET_PAYPASSWORD1);
            }

            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.FAIL);
            resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.PASS_FAIL_CODE);
            return resultJson;
        }else if(depositInfo.getPreDeposit().compareTo(orderPrice) < 0){
            //预存款余额不足
            if("0".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_ENOUGH_PAYPASSWORD + depositInfo.getPreDeposit());
            }else if("1".equals(type)){
                resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_ENOUGH_PAYPASSWORD1);
            }

            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.FAIL);
            resultJson.put(DepositInfoCons.FAIL_CODE, DepositInfoCons.BALANCE_FAIL_CODE);
            return resultJson;
        }else {
            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.SUCCESS);
        }
        return resultJson;
    }

    /**
     * 支付宝充值
     * @param rechargePrice 充值金额
     * @param customerId 用户id
     * @return
     */
    @Override
    public String zhifubaoRecharge(BigDecimal rechargePrice,Long customerId) {
        //生成预付款交易记录
        DepositInfo depositInfo =  depositInfoMapper.selectDepositByCustId(customerId);
        TradeInfo tradeInfo = tradeInfoService.insertSelective(customerId, rechargePrice, "0", "0","在线充值-支付宝",depositInfo);
        Pay p = payService.findByPayId(25L);
        AlipayConfig.partner = p.getApiKey();
        AlipayConfig.seller_id = p.getApiKey();
        Properties properties = PropertieUtil.readPropertiesFile(DepositInfoServiceImpl.class.getClassLoader().getResourceAsStream("com/ningpai/web/config/alipay.properties"));
        // 本地karaf路径
        AlipayConfig.private_key = properties.getProperty("PRIVATE_KEY");

        // 支付类型
        String paymentType = "1";
        // 必填，不能修改
        // 服务器异步通知页面路径
        String notifyUrl = p.getPayComment()+"asynrechargesucccess.htm";
        // 需http://格式的完整路径，不能加?id=123这类自定义参数

        // 页面跳转同步通知页面路径
        String returnUrl = p.getPayComment()+"synrechargesucccess.htm";
        // 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

        // 商户网站订单系统中唯一订单号，必填

        // 订单名称
        String subject = "充值";
        // 必填
        DecimalFormat df=new DecimalFormat("0.00");
        // 充值金额
        String totalFee = df.format(rechargePrice.doubleValue()).toString();
        // 必填

        // 商品展示地址
        String showUrl = p.getPayUrl();
        // 必填，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

        // 订单描述
        String body = "手机网购订单";
        // 选填

        // 超时时间
        String itBPay = "";
        // 选填

        // 钱包token
        String externToken = "";
        // 选填

        // ////////////////////////////////////////////////////////////////////////////////

        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", paymentType);
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("return_url", returnUrl);
        sParaTemp.put("out_trade_no", tradeInfo.getOrderCode());
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", totalFee);
        sParaTemp.put("show_url", showUrl);
        sParaTemp.put("body", body);
        sParaTemp.put("it_b_pay", itBPay);
        sParaTemp.put("extern_token", externToken);

        // 建立请求
        return AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
    }

    /**
     * 微信充值通知处理
     *
     * @param outTradeNo 交易单号
     * @return success:成功 fail:失败
     */
    @Override
    public boolean zhifubaoRechargeNotify(String outTradeNo) {
        TradeInfo tradeInfo = tradeInfoService.selectByTradeNo(outTradeNo);
        if ("5".equals(tradeInfo.getOrderStatus())) {
            tradeInfo.setOrderStatus("6");
            DepositInfo depositInfo1 = depositInfoMapper.selectDepositByCustId(tradeInfo.getCustomerId());
            //充值成功当前余额
            tradeInfo.setCurrentPrice(depositInfo1.getFreezePreDeposit().add(depositInfo1.getPreDeposit()).add(tradeInfo.getOrderPrice()));
            //更新交易状态
            tradeInfoService.updateTradeInfo(tradeInfo);

            //更新预存款信息
            DepositInfo depositInfo = new DepositInfo();
            depositInfo.setCustomerId(tradeInfo.getCustomerId());
            depositInfo.setPreDeposit(tradeInfo.getOrderPrice().add(depositInfo1.getPreDeposit()));
            depositInfoMapper.updateDepositInfo(depositInfo);
            return true;
        }

        return false;
    }

    /**
     *
     * @param depositInfo
     * @param order
     * @param customerId
     */
    private void updateDepositInfo(DepositInfo depositInfo, Order order, Long customerId){
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setDelFlag("0");
        tradeInfo.setCreateTime(new Date());
        tradeInfo.setCurrentPrice(depositInfo.getPreDeposit().subtract(order.getOrderPrice()).add(depositInfo.getFreezePreDeposit()));
        tradeInfo.setOrderCode(order.getOrderCode());
        tradeInfo.setCustomerId(customerId);
        tradeInfo.setOrderPrice(order.getOrderPrice());
        tradeInfo.setCreatePerson(customerId);
        tradeInfo.setTradeRemark("订单编号:" + order.getOrderCode());
        tradeInfo.setOrderType("3");
        tradeInfoService.insertTradeInfo(tradeInfo);

        //更新预存款信息
        depositInfo.setPreDeposit(depositInfo.getPreDeposit().subtract(order.getOrderPrice()));
        depositInfoMapper.updateDepositInfo(depositInfo);
    }

    /**
     * 提现功能是否可用验证
     *
     * @param customerId 用户id
     * @return
     */
    @Override
    public JSONObject checkWithdraw(Long customerId) {
        JSONObject resultJson = new JSONObject();
        DepositInfo depositInfo =  depositInfoMapper.selectDepositByCustId(customerId);
        if(StringUtils.isEmpty(depositInfo.getPayPassword())){
            //您还没有设置支付密码
            resultJson.put(DepositInfoCons.RETURN_MSG, DepositInfoCons.NOT_SET_PAYPASSWORD1);
            resultJson.put(DepositInfoCons.RETURN_CODE,  DepositInfoCons.FAIL);
        }
        return resultJson;
    }
}
