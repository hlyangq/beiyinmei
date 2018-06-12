package com.ningpai.site.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.alipay.config.AlipayConfig;
import com.ningpai.common.util.alipay.util.AlipayNotify;
import com.ningpai.common.util.alipay.util.UtilDate;
import com.ningpai.deposit.bean.Deposit;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.site.customer.deposit.service.SiteDepositService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.site.order.service.IPayService;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.util.MD5Util;
import com.ningpai.site.util.PayUtil;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.bean.Receivables;
import com.ningpai.system.service.PayService;
import com.ningpai.system.service.PaymentService;
import com.ningpai.system.service.ReceivablesService;
import com.ningpai.util.MyLogger;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 支付Controller
 *
 * @author lih
 * @version 2.0
 * @since 2015/8/11 16:21
 */
@Controller
public class PaySiteController {

    /**
     * 单比订单或者多笔订单生成不同的收款单信息 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(PaySiteController.class);

    private static final String LOGGERINFO1 = "支付请求失败";
    private static final String UTF_8 = "UTF-8";
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String OUT_TRADE_NO = "out_trade_no";
    private static final String TRADE_STATUS = "trade_status";
    private static final String ORDER_PAYSUCCESS = "order/paysuccess";
    private static final String ORDER = "order";
    private static final String PRICE = "price";
    private static final String SUCCESS = "success";
    private static final String UNKNOWN = "unknown";

    @Resource(name = "receivablesService")
    private ReceivablesService receivablesService;

    @Resource(name = "payService")
    private PayService payService;

    @Resource(name = "PaymentService")
    PaymentService paymentService;

    @Resource(name = "SiteOrderService")
    private SiteOrderService siteOrderService;

    @Resource(name = "OrderService")
    private OrderService orderser;

    @Resource(name = "customerServiceSite")
    private CustomerServiceInterface customerServiceInterface;

    @Resource(name = "TopAndBottomService")
    private TopAndBottomService topAndBottomService;

    @Resource(name = "IPayService")
    private IPayService iPayService;

    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;

    @Resource(name = "GoodsProductService")
    private GoodsProductService goodsProductService;

    @Resource(name = "SynOrderService")
    private SynOrderService synOrderService;

    @Autowired
    private SiteDepositService depositService;
    /**
     * 根据支付的订单的数量 来设置订单的属性
     *
     * @param order 订单对象
     * @return
     */
    public void getReceivables(Pay p, HttpServletRequest request, Order order) {

        /* 根据订单号去查询是否有记录 */
        Receivables receivables1 = this.receivablesService.queryByOrderCode(order.getOrderCode());
        if (null == receivables1) {
            /* 添加收款单信息 */
            Receivables receivables = new Receivables();
            Random random = new Random();
            int code = random.nextInt(10001);
            String codes = "2015" + code;
            // 收款单编号 产生的随机数
            receivables.setCashRegisterCode(codes);
            // 收款单IP
            receivables.setPayIp(getIpAddr(request));
            // 会员ID
            receivables.setCustomerId(order.getCustomerUsername());
            // 收款账号
            receivables.setPayAccount(p.getPayAccount());
            // 给收款单信息设置IP地址
            // receivables.setPayIp(InetAddress.getLocalHost().getHostAddress());
            // 设置生成时间
            receivables.setPayTime(new Date());
            // 设置付款时间
            receivables.setReceivablesTime(new Date());
            // 给收款单信息设置支付方式
            receivables.setPayMode(p.getPayName());
            // 设置支付类型
            receivables.setPayType("在线支付");
            // 给收款单信息设置付款金额
            receivables.setPayMoney(order.getOrderPrice());
            receivables.setOrderCode(order.getOrderCode());
            // 给付款单信息设置是否支付成功
            receivables.setPayStatus("1");
            // 执行为收款单信息添加信息操作
            this.receivablesService.addReceivables(receivables);
        }else{
            // 收款单IP
            receivables1.setPayIp(getIpAddr(request));
            // 会员ID
            receivables1.setCustomerId(order.getCustomerUsername());
            // 收款账号
            receivables1.setPayAccount(p.getPayAccount());
            // 给收款单信息设置IP地址
            // receivables.setPayIp(InetAddress.getLocalHost().getHostAddress());
            // 设置生成时间
            receivables1.setPayTime(new Date());
            // 设置付款时间
            receivables1.setReceivablesTime(new Date());
            // 给收款单信息设置支付方式
            receivables1.setPayMode(p.getPayName());
            // 设置支付类型
            receivables1.setPayType("在线支付");
            // 给收款单信息设置付款金额
            receivables1.setPayMoney(order.getOrderPrice());
            receivables1.setOrderCode(order.getOrderCode());
            // 给付款单信息设置是否支付成功
            receivables1.setPayStatus("1");
            // 执行为收款单信息添加信息操作
            this.receivablesService.upDateReceivables(receivables1);
        }
    }

    /**
     * 确认付款
     *
     * @param orderCount 当前支付的订单数量
     * @param orderId    需要支付的订单Id
     * @param request
     * @param payId
     * @param response
     * @throws UnsupportedEncodingException
     * @throws UnknownHostException
     */
    @RequestMapping("/payorder")
    public void payOrder(Long orderCount, Long orderId, HttpServletRequest request, Long payId, HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取用户id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 判断用户id是否为空
        if (customerId == null) {
            return;
        }
        // 返回订单商品的商品名称，取第一个
        String goodsName = orderser.queryGoodsInfoName(orderId);
        // 获取订单信息
        Order order = siteOrderService.getPayOrder(orderId);

        // 判断订单 是否是当前用户的订单
        if (!customerId.equals(order.getCustomerId())) {
            try {
                response.getWriter().write("what are you doing ?????");
                response.getWriter().close();
            } catch (Exception e) {
                LOGGER.error("write error", e);
            }
            return;
        }

        // 支付的实际金额, 如果有多笔子订单则为多笔子订单金额之和
        BigDecimal payTotalPrice = order.getOrderPrice();

        // 查询使用的支付信息
        Pay p = payService.findByPayId(payId);
        if (p != null) {
            // 多笔订单 循环生成多笔付款单信息
            if (null != orderCount && 1 < orderCount) {
                // 子订单的总金额
                BigDecimal totalPrice = BigDecimal.ZERO;
                // 根据主订单号查询所有的子订单信息
                List<Order> orderslist = siteOrderService.getPayOrderByOldCode(order.getOrderOldCode());
                for (int i = 0; i < orderslist.size(); i++) {
                    // 创建单个订单对象
                    Order childOrder = orderslist.get(i);
                    // 如果是在线支付
                    if ("1".equals(childOrder.getOrderLinePay())) {
                        // 单笔订单
                        this.getReceivables(p, request, childOrder);
                    }
                    totalPrice = totalPrice.add(childOrder.getOrderPrice());
                }
                payTotalPrice = totalPrice;
            } else {
                // 单笔订单 生成单笔的付款单信息
                this.getReceivables(p, request, order);
            }

            // 支付宝支付
            if ("1".equals(p.getPayType())) {
                // 获取支付用的信息
                String sHtmlText = iPayService.getAlipay(p, order, goodsName, orderCount, payTotalPrice);
                // 设置编码集
                response.setContentType("text/html;charset=UTF-8");
                // 设置编码格式
                response.setCharacterEncoding(ConstantUtil.UTF);

                try {
                    // 写入
                    response.getWriter().write(sHtmlText);
                    // 获取单个订单对象
                    // Order orders = siteOrderService.getPayOrder(orderId);
                    // 订单日志记录
                    LOGGER.info("给订单号为：【" + order.getOrderCode() + "】的订单付款成功");
                } catch (IOException e) {
                    LOGGER.error(LOGGERINFO1 + e);
                }
            } else if ("2".equals(p.getPayType())) {
                // 字符集编码
                String encoding = ConstantUtil.UTF;
                /**
                 * 初始化证书
                 */
                SDKConfig.getConfig().loadPropertiesFromSrc();
                /**
                 * 组装请求报文
                 */
                String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
                Map<String, String> data = new HashMap<String, String>();
                // 版本号
                data.put("version", "5.0.0");
                // 字符集编码
                data.put("encoding", ConstantUtil.UTF);
                // 签名方法 01 RSA
                data.put("signMethod", "01");
                // 交易类型
                data.put("txnType", "01");
                // 交易子类型 01:自助消费 02:订购 03:分期付款
                data.put("txnSubType", "01");
                // 业务类型
                data.put("bizType", "000201");
                //渠道类型
                data.put("channelType", "07");
                // 页面跳转同步通知页面路径
                String returnUrl = p.getBackUrl();
                //前台通知地址
                data.put("frontUrl", returnUrl);
                // 服务器异步通知页面路径
                String notifyUrl = p.getPayUrl() + "/unionpaynotifysuccess.html";
                // 商户/收单后台接收地址
                data.put("backUrl", notifyUrl);
                // 接入类型 商户:0 收单:1
                data.put("accessType", "0");
                // 商户号码
                data.put("merId", p.getPayAccount());
                // 总订单号
                data.put(ConstantUtil.ORDERID, order.getOrderCode());
                // 订单发送时间
                data.put("txnTime", UtilDate.getOrderNum());
                // 交易金额 分
                data.put("txnAmt", payTotalPrice.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
                // 交易币种
                data.put("currencyCode", "156");

                /**
                 * 签名
                 */
                Map<String, String> submitFromData = signData(data);
                /**
                 * 创建表单
                 */
                String html = createHtml(requestUrl, submitFromData);
                response.setContentType("text/html;charset=UTF-8");
                response.setCharacterEncoding(ConstantUtil.UTF);

                try {
                    response.getWriter().write(html);
                } catch (IOException e) {
                    LOGGER.error(LOGGERINFO1 + e);
                }
            } else if ("4".equals(p.getPayType())) {
                PayUtil pay = new PayUtil();
                // 单次支付的订单大于在一笔以上
                if (null != orderCount && 1 < orderCount) {
                    order = this.checkOrder(order);
                    // 支付成功回调地址
                    pay.setCallBackUrl(p.getPayUrl() + "qmpaysussmulti.htm");
                    pay.setNotifyUrl(p.getPayUrl() + "qmpaysussymulti.htm");
                } else {
                    // 支付成功回调地址
                    pay.setCallBackUrl(p.getBackUrl());
                    pay.setNotifyUrl(p.getPayUrl() + "qmpaysucasy.htm");
                }

                // 订单编号
                pay.setOrderNo(order.getOrderCode());
                // 购买方编号
                pay.setBuyerUserId(p.getPayAccount());
                // 收款方编号
                pay.setSellerUserId(p.getPayAccount());
                // 收款方应得金额
                pay.setSettleAmount(order.getOrderPrice());
                // 商品名称
                pay.setProductName(goodsName);
                // 上级编号
                pay.setAdminUserId(p.getSecretKey());

                // ip地址
                try {
                    pay.setFromIp(InetAddress.getLocalHost().getHostAddress());
                } catch (UnknownHostException e) {
                    LOGGER.error("", e);
                    LOGGER.info(e);
                }
                // 订单渠道(取值为: OF(欧飞订单) ,QM(千米订单) ,YX(云销订单))
                pay.setTradeChannel("SYY");
                // 订单详情链接
                pay.setGoodsUrl(p.getPayUrl() + "customer/detail-" + order.getOrderId() + ".html");
                // 结算类型 (取值为:1实时结算 0后结算)
                pay.setSettleType("1");
                // 支付金额
                pay.setAmount(order.getOrderPrice());

                // 系统编号(重要)
                pay.setSysId("901");
                // css样式URL
                pay.setCssStyle("style-orange.css");
                String key = p.getApiKey();
                String s = pay.getAdminUserId() + pay.getAmount() + pay.getBuyerUserId() + pay.getCallBackUrl() + pay.getCssStyle() + pay.getFromIp() + pay.getGoodsUrl()
                        + pay.getNotifyUrl() + pay.getOrderNo() + pay.getProductName() + pay.getSellerUserId() + pay.getSettleAmount() + pay.getSettleType() + pay.getSysId()
                        + pay.getTradeChannel() + key;
                // 加密链接
                String sign = MD5Util.md5Hex(MD5Util.getContentBytes(s, UTF_8));
                Map<String, String> sParaTemp = new HashMap<String, String>();
                sParaTemp.put("orderNo", pay.getOrderNo());
                sParaTemp.put("buyerUserId", pay.getBuyerUserId());
                sParaTemp.put("sellerUserId", pay.getSellerUserId());
                sParaTemp.put("settleAmount", pay.getSettleAmount().toString());
                sParaTemp.put("productName", pay.getProductName());
                sParaTemp.put("adminUserId", pay.getAdminUserId());
                sParaTemp.put("callBackUrl", pay.getCallBackUrl());
                sParaTemp.put("fromIp", pay.getFromIp());
                sParaTemp.put("tradeChannel", pay.getTradeChannel());
                sParaTemp.put("goodsUrl", pay.getGoodsUrl());
                sParaTemp.put("settleType", pay.getSettleType());
                sParaTemp.put("amount", pay.getAmount().toString());
                sParaTemp.put("sysId", pay.getSysId());
                sParaTemp.put("cssStyle", pay.getCssStyle());
                sParaTemp.put("notifyUrl", pay.getNotifyUrl());
                /**
                 * 签名
                 */
                sParaTemp.put("sign", sign);
                // String requestUrl="";
                String html = createHtml("https://cashier.qianmi.com/home/payment", sParaTemp);
                response.setContentType("text/html;charset=UTF-8");
                request.setCharacterEncoding(UTF_8);
                response.setCharacterEncoding(ConstantUtil.UTF);

                try {
                    response.getWriter().write(html);
                } catch (IOException e) {
                    LOGGER.error("" + LOGGERINFO1, e);
                }
            }
            else if("5".equals(p.getPayType())){//在线支付，预存款
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");

                JSONObject retjson = new JSONObject();
                BigDecimal ttotalFee = payTotalPrice;
                String torderCode = order.getOrderCode();
                Long torderId = order.getOrderId();
                String status = order.getOrderStatus();
                if(!"0".equals(status)){//非未付款情况下，退出流程
                    retjson.put("retcode","-1");
                    retjson.put("msg","订单状态不是未付款");
                    try {
                        response.getWriter().println(retjson);
                        return;
                    } catch (IOException e) {
                        LOGGER.error("" + LOGGERINFO1, e);
                    }
                }
                //前端的返回json
                JSONObject json = new JSONObject();
                //前端输入支付密码
                String payPassword = request.getParameter("payPassword");
                //queryMap
                Map<String,Object> queryMap = new HashMap<>();
                queryMap.put("customerId",customerId);
                Deposit deposit = depositService.getDeposit(queryMap);

                JSONObject payjson = depositService.pay(payTotalPrice,deposit,payPassword,torderId,torderCode);
                try {
                    response.getWriter().println(payjson);
                } catch (IOException e) {
                    LOGGER.error("" + LOGGERINFO1, e);
                }
            }
        }
    }

    @RequestMapping("/checkdepositpay")
    @ResponseBody
    public JSONObject preCheckDepositPay(Long orderCount, Long orderId, HttpServletRequest request, Long payId, HttpServletResponse response){
        JSONObject retjson = new JSONObject();
        // 获取用户id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 判断用户id是否为空
        if (customerId == null) {
            retjson.put("retcode","-1");
            retjson.put("msg","请先登录");
            return retjson;
        }

        // 获取订单信息
        Order order = siteOrderService.getPayOrder(orderId);
        BigDecimal payTotalPrice = order.getOrderPrice();
        // 判断订单 是否是当前用户的订单
        if (!customerId.equals(order.getCustomerId())) {
            retjson.put("retcode","-1");
            retjson.put("msg","what are you doing ?????");
            return retjson;
        }

        BigDecimal ttotalFee = payTotalPrice;
        String torderCode = order.getOrderCode();
        Long torderId = order.getOrderId();
        String payPassword = request.getParameter("payPassword");
        //queryMap
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("customerId",customerId);
        Deposit deposit = depositService.getDeposit(queryMap);
        int errorCount = deposit.getPasswordErrorCount();
        String tpayPassword = deposit.getPayPassword();
        JSONObject payjson = depositService.preCheck(payTotalPrice,deposit,payPassword,torderId,torderCode);
        return payjson;
    }

    @RequestMapping("/depositpaysuccess")
    public ModelAndView depositPaysuccess(Long orderId, HttpServletRequest request){
        Order order = siteOrderService.getPayOrder(orderId);
        List<Order> orderslist = siteOrderService.getPayOrderByOldCode(order.getOrderOldCode());
        BigDecimal totalPrice = BigDecimal.ZERO;
        int i = 0;
        if(!CollectionUtils.isEmpty(orderslist)){//多笔订单
            i = orderslist.size();
        }
        Order tempOrder = null;
        for(int k=0;k<i;k++){
            tempOrder = orderslist.get(k);
            totalPrice = totalPrice.add(tempOrder.getOrderPrice());
        }
        ModelAndView mav = new ModelAndView("order/depositpaysuccess");
        mav.addObject("order",order);
        mav.addObject("totalPrice",totalPrice);
        return topAndBottomService.getTopAndBottom(mav);
    }


    private Order checkOrder(Order order) {
        // 用来存储订单的总价格
        BigDecimal orderSumPrice = new BigDecimal(0.00);
        // 飞快验证主订单号
        if (null != order.getOrderOldCode()) {
            // 根据订单那的主订单号 查询主订单号下面所有的小订单信息
            List<Order> orders = siteOrderService.getPayOrderByOldCode(order.getOrderOldCode());
            // 如果需要支付的订单数大于1
            if (null != orders && orders.size() >= 1) {
                for (int i = 0; i < orders.size(); i++) {
                    // 获取单个的子订单信息
                    Order childOrder = orders.get(i);
                    // 如果订单的支付方式是货到付款 就不计算价格
                    if (!"0".equals(childOrder.getOrderLinePay())) {
                        // 把子订单的信息进行相加
                        orderSumPrice = orderSumPrice.add(childOrder.getOrderPrice());
                    }
                }
                // 把子订单的价格相加重新赋值给order对象 用于支付宝支付 不需持久化到数据库
                order.setOrderPrice(orderSumPrice);
                // 把主订单号赋值给订单单号 也是用于支付宝支付 不需要持久化到数据库
                order.setOrderCode(order.getOrderOldCode());
            }
        }
        return order;
    }

    /**
     * 构造HTTP POST交易表单的方法示例
     *
     * @param action  表单提交地址
     * @param hiddens 以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public static String createHtml(String action, Map<String, String> hiddens) {
        StringBuilder sf = new StringBuilder();
        sf.append("<form id = \"sform\" action=\"" + action + "\" method=\"post\">");
        if (null != hiddens && !hiddens.isEmpty()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.sform.submit();");
        sf.append("</script>");
        return sf.toString();
    }

    /**
     * 支付宝同步回调
     *
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("/paysucccess")
    public ModelAndView paySuccess(HttpServletRequest request) {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);

        // 设置商户号
        AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        AlipayConfig.key = p.getSecretKey();

        AlipayConfig.seller_email = p.getPayAccount();

        // 获取该订单的会员信息
        CustomerAllInfo ca = null;
        //
        Map<String, Object> map = null;
        // 主订单号下面的所有订单信息
        List<Order> orderslist = null;
        // 保存订单对象集合
        List<Order> orderList = new ArrayList<Order>();
        // 支付的订单价格
        BigDecimal price = new BigDecimal(0);

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                StringBuilder buf = new StringBuilder();
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
            }
            params.put(name, valueStr);
        }

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

        try {
            // 商户订单号
            String outTradeNo = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), UTF_8);
            // 支付宝交易号
            // String tradeNo = new
            // String(request.getParameter("trade_no").getBytes(ISO_8859_1),UTF_8);

            // 交易状态
            String tradeStatus = new String(request.getParameter(TRADE_STATUS).getBytes(ISO_8859_1), UTF_8);

            // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            // 计算得出通知验证结果
            boolean verifyResult = AlipayNotify.verify(params);
            //System.out.println(verifyResult + "====== 验证结果=============");
            // if(verify_result){//验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码

            // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                Order orderOne = siteOrderService.getPayOrderByCode(outTradeNo);
                if (null != orderOne) {
                    // 计算订单的总价格
                    price = price.add(orderOne.getOrderPrice());
                    // 获取该订单的会员信息
                    ca = customerServiceInterface.selectByPrimaryKey(orderOne.getCustomerId());
                    request.getSession().setAttribute("cust", ca);
                    request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
                    // 根据订单编号订单下所有商品并随机返回一个
                    map = siteOrderService.queryGoodsProduceByOrderId(orderOne.getOrderId(), null);
                    orderList.add(orderOne);
                } else {
                    // 根据主订单号查询所有的子订单信息
                    orderslist = siteOrderService.getPayOrderByOldCode(outTradeNo);
                    // 同时支付的订单 如果大于1笔
                    if (null != orderslist) {
                        for (int i = 0; i < orderslist.size(); i++) {
                            // 创建单个订单对象
                            Order childOrder = orderslist.get(i);
                            // 计算订单的总价格
                            price = price.add(childOrder.getOrderPrice());
                            // 只需要保存一次会员信息 因为都时同一个会员提交的订单
                            if (null == ca) {
                                // 获取该订单的会员信息
                                ca = customerServiceInterface.selectByPrimaryKey(childOrder.getCustomerId());
                                request.getSession().setAttribute("cust", ca);
                                request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
                            }
                            if (null == map) {
                                // 根据订单编号订单下所有商品并随机返回一个
                                map = siteOrderService.queryGoodsProduceByOrderId(childOrder.getOrderId(), null);
                            }
                            // 把订单对象装入要返回到页面的集合
                            orderList.add(childOrder);

                        }
                    }
                }
            }

            // 该页面可做页面美工编辑
            //System.out.println("验证成功<br />");
            // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            // ////////////////////////////////////////////////////////////////////////////////////////
            // }else{
            // 该页面可做页面美工编辑
            // System.out.println("验证失败");
            // }
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("" + e);
        }

        ModelAndView mav = new ModelAndView(ORDER_PAYSUCCESS).addObject(ORDER, orderList).addObject("gs", map.get("list")).addObject(PRICE, price);
        return topAndBottomService.getSimpleTopAndBottom(mav);

    }

    /**
     * 支付宝异步回调
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws java.io.UnsupportedEncodingException
     */
    @RequestMapping("/paysucccessyb")
    public void paySuccessyb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);

        // 设置   商户号
        AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        AlipayConfig.key = p.getSecretKey();

        AlipayConfig.seller_email = p.getPayAccount();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
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
        // 商户订单号
        String outTradeNo = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), ConstantUtil.UTF);
        // 支付宝交易号
        // 交易状态
        String tradeStatus = new String(request.getParameter(TRADE_STATUS).getBytes(ISO_8859_1), ConstantUtil.UTF);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        if (AlipayNotify.verify(params)) {// 验证成功
            chechOrderStatus(tradeStatus, outTradeNo);
            sendSucess(response, SUCCESS);
        } else {// 验证失败
            sendSucess(response, "fail");
        }

    }

    /**
     * 支付宝回调
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws java.io.UnsupportedEncodingException
     */
    @RequestMapping("/paysucccessybmulti")
    public void paySuccessybmulti(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取支付宝信息
        Pay p = payService.findByPayId(25L);

        // 设置商户号
        AlipayConfig.partner = p.getApiKey();
        // 设置商户秘钥
        AlipayConfig.key = p.getSecretKey();

        AlipayConfig.seller_email = p.getPayAccount();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
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
        // 商户订单号
        String outTradeNo = new String(request.getParameter(OUT_TRADE_NO).getBytes(ISO_8859_1), ConstantUtil.UTF);
        // 支付宝交易号
        // 交易状态
        String tradeStatus = new String(request.getParameter(TRADE_STATUS).getBytes(ISO_8859_1), ConstantUtil.UTF);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        if (AlipayNotify.verify(params)) {// 验证成功
            // ////////////////////////////////////////////////////////////////////////////////////////
            // 请在这里加上商户的业务逻辑程序代码
            /**
             * 根据主订单号查询所有的子订单信息 如果能查到值证明传过来的是主订单号 如果没值证明传过来的是子订单号
             */
            List<Order> orderslist = siteOrderService.getPayOrderByOldCode(outTradeNo);
            // 多笔订单支付
            if (null != orderslist && orderslist.size() > 0) {
                for (int i = 0; i < orderslist.size(); i++) {
                    // 多笔订单支付
                    Order childOrder = orderslist.get(i);
                    // 更改订单那的支付状态
                    chechOrderStatus(tradeStatus, childOrder.getOrderCode());
                }
            }
            // ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——


            sendSucess(response, SUCCESS);

            // ////////////////////////////////////////////////////////////////////////////////////////
        } else {// 验证失败
            sendSucess(response, "fail");
        }

    }

    /**
     * @param tradeStatus 支付宝交易号 交易状态
     * @param outTradeNo  支付宝 支付成功 返回的订单号
     * @return
     */
    public void chechOrderStatus(String tradeStatus, String outTradeNo) {

        // ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
        Receivables receivables = this.receivablesService.queryByOrderCode(outTradeNo);
        if ("TRADE_FINISHED".equals(tradeStatus)) {
            // 判断该笔订单是否在商户网站中已经做过处理
            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            // 如果有做过处理，不执行商户的业务程序
            // 重新将session登入
            Order or = siteOrderService.getPayOrderByCode(outTradeNo);
            if ("0".equals(or.getOrderStatus())) {
                siteOrderService.payOrder(or.getOrderId());
                if (null != receivables) {
                    // 修改订单支付状态为支付成功
                    receivables.setReceivablesTime(new Date());
                    this.receivablesService.updatePayStatus(receivables);
                    //直营店订单短信通知
                    siteOrderService.paySuccessSendSms(or);
                    // 同步E店宝
                    synOrderService.SynOrder(or.getOrderId());
                }
            }
            // 注意：
            // 该种交易状态只在两种情况下出现
            // 1、开通了普通即时到账，买家付款成功后。
            // 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
        } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
            // 判断该笔订单是否在商户网站中已经做过处理
            // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            // 如果有做过处理，
            Order or = siteOrderService.getPayOrderByCode(outTradeNo);
            if ("0".equals(or.getOrderStatus())) {
                siteOrderService.payOrder(or.getOrderId());
                if (null != receivables) {
                    // 修改订单支付状态为支付成功
                    receivables.setReceivablesTime(new Date());
                    this.receivablesService.updatePayStatus(receivables);
                    //直营店订单短信通知
                    siteOrderService.paySuccessSendSms(or);
                    // 同步E店宝
                    synOrderService.SynOrder(or.getOrderId());
                }
            }
            // 注意：
            // 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
        }
    }

    /**
     * 千米收银台页面回调
     *
     * @param request 请求
     * @return
     */
    @RequestMapping("qmpaysuc")
    public ModelAndView qmpaysuc(HttpServletRequest request) {
        // 订单号
        String orderOldCode = request.getParameter("orderno");
        List<Order> orderslist = new ArrayList<Order>();
        // 重新将session登入
        Order or = siteOrderService.getPayOrderByCode(orderOldCode);
        orderslist.add(or);
        CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(or.getCustomerId());
        request.getSession().setAttribute("cust", ca);
        request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
        Map<String, Object> map = siteOrderService.queryGoodsProduceByOrderId(or.getOrderId(), null);
        ModelAndView mav = new ModelAndView(ORDER_PAYSUCCESS).addObject(ORDER, orderslist).addObject("gs", map.get("list")).addObject(PRICE, or.getOrderPrice());
        return topAndBottomService.getSimpleTopAndBottom(mav);
    }

    /**
     * 千米收银台页面回调（多笔订单））
     *
     * @param request 请求
     * @return
     */
    @RequestMapping("qmpaysussmulti")
    public ModelAndView qmpaysussmulti(HttpServletRequest request) {
        // 订单号
        String orderOldCode = request.getParameter("orderno");
        List<Order> orderslist = siteOrderService.getPayOrderByOldCode(orderOldCode);
        BigDecimal price = new BigDecimal(0);
        for (int i = 0; i < orderslist.size(); i++) {
            price = price.add(orderslist.get(i).getOrderPrice());
        }
        // 重新将session登入
        CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(orderslist.get(0).getCustomerId());
        request.getSession().setAttribute("cust", ca);
        request.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
        Map<String, Object> map = siteOrderService.queryGoodsProduceByOrderId(orderslist.get(0).getOrderId(), null);
        ModelAndView mav = new ModelAndView(ORDER_PAYSUCCESS).addObject(ORDER, orderslist).addObject("gs", map.get("list")).addObject(PRICE, price);
        return topAndBottomService.getSimpleTopAndBottom(mav);
    }

    /**
     * 千米收银台
     *
     * @returnz
     */
    @RequestMapping("qmpaysucasy")
    @ResponseBody
    public String qmpaysucasy(String orderNo, String sign) {
        // 获取key
        Pay pay = payService.findByPayId(39L);
        // 获取加密参数
        String date = orderNo + pay.getApiKey();
        // 进行加密
        String s = MD5Util.md5Hex(date);
        // 返回结果
        String suc = "";
        if (s.equals(sign)) {
            Order or = siteOrderService.getPayOrderByCode(orderNo);
            Receivables receivables = this.receivablesService.queryByOrderCode(or.getOrderCode());
            // 更新订单状态
            if ("0".equals(or.getOrderStatus())) {
                siteOrderService.payOrder(or.getOrderId());
                if (null != receivables) {
                    receivables.setReceivablesTime(new Date());
                    this.receivablesService.updatePayStatus(receivables);
                }
            }
            suc = SUCCESS;
        }
        return suc;
    }

    /**
     * 千米收银台(多笔订单)
     *
     * @returnz
     */
    @RequestMapping("qmpaysussymulti")
    @ResponseBody
    public String qmpaysussymulti(String orderNo, String sign) {
        // 获取key
        Pay pay = payService.findByPayId(39L);
        // 获取加密参数
        String date = orderNo + pay.getApiKey();
        // 进行加密
        String s = MD5Util.md5Hex(date);
        // 返回结果
        String suc = "";
        if (s.equals(sign)) {
            List<Order> orderslist = siteOrderService.getPayOrderByOldCode(orderNo);
            // 多笔订单支付
            if (null != orderslist && orderslist.size() >= 1) {
                for (int i = 0; i < orderslist.size(); i++) {
                    // 多笔订单支付
                    Receivables receivables = this.receivablesService.queryByOrderCode(orderslist.get(i).getOrderCode());
                    siteOrderService.payOrder(orderslist.get(i).getOrderId());
                    if (null != receivables) {
                        receivables.setReceivablesTime(new Date());
                        this.receivablesService.updatePayStatus(receivables);
                    }
                }
            }
            suc = SUCCESS;
        }
        return suc;
    }

    /**
     * 发送成功
     *
     * @param response
     * @param msg
     * @throws IOException
     */
    public void sendSucess(HttpServletResponse response, String msg) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * java main方法 数据提交 　　 对数据进行签名
     *
     * @param contentData
     * @return　签名后的map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> signData(Map<String, ?> contentData) {
        Map.Entry<String, String> obj = null;
        Map<String, String> submitFromData = new HashMap<String, String>();
        for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Map.Entry<String, String>) it.next();
            String value = obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                // 对value值进行去除前后空处理
                submitFromData.put(obj.getKey(), value.trim());
                System.out
                        .println(obj.getKey() + "-->" + String.valueOf(value));
            }
        }
        /**
         * 签名
         */
        if (null != submitFromData) {
            SDKUtil.sign(submitFromData, "UTF-8");
        }
        return submitFromData;
    }
}
