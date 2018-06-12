/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.site.order.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import com.ningpai.system.bean.Receivables;
import com.ningpai.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ningpai.common.safe.CSRFTokenManager;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.site.customer.bean.OrderNotice;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.Order;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.service.OrderNoticeService;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.ExpressConf;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.impl.ExpressConfBizImpl;
import com.ningpai.thirdaudit.bean.StoreInfo;
import com.ningpai.util.MyLogger;
import com.ningpai.util.ShoppingCartConstants;

/**
 * 订单控制器
 *
 * @author NINGPAI-LIH
 * @version 1.0
 * @since 2014年4月14日 下午5:35:48
 */
@Controller
public class SiteOrderController {

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(SiteOrderController.class);

    private static final String CUSTOMERID = "customerId";
    private static final String LOGIN = "../login.html";
    private static final String MYSHOPPINGCART = "../myshoppingcart.html";
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String PAYLIST = "payList";
    private static final String PRICE = "price";
    private static final String GOODSNOEXIT = "/goods/no_exit";

    /**
     * 跳转到确认订单页
     */
    BigDecimal result = new BigDecimal(0);

    static int i = 0;

    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;

    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "FreightTemplateService")
    private FreightTemplateService freightTemplateService;

    @Resource(name = "GoodsProductService")
    private GoodsProductService goodsProductService;

    @Resource(name = "MarketingService")
    private MarketingService marketingService;

    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper addressService;

    @Resource(name = "expressConfBizImpl")
    private IExpressConfBiz iExpressConfBiz;

    @Resource(name = "payService")
    private PayService payService;

    @Resource(name = "PaymentService")
    private PaymentService paymentService;

    @Resource(name = "expressConfBizImpl")
    private ExpressConfBizImpl expressConfBizImpl;

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    @Resource(name = "SiteOrderService")
    private SiteOrderService siteOrderService;

    @Resource(name = "customerServiceSite")
    private CustomerServiceInterface customerServiceInterface;

    @Resource(name = "TopAndBottomService")
    private TopAndBottomService topAndBottomService;

    @Resource(name = "orderNoticeServiceSite")
    private OrderNoticeService orderNoticeService;

    @Resource(name = "receivablesService")
    private ReceivablesService receivablesService;

    @Autowired
    private ShoppingCartAddressService shoppingCartAddressService;

    /**
     * ajax 得到可用的自提点
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajaxgetSince")
    @ResponseBody
    public List<DeliveryPoint> ajaxgetSince(HttpServletRequest request, Long addressId) {
        // 获取用户ID
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);

        CustomerAddress customerAddress = null;
        //查询收货地址
        if (addressId != null)
            customerAddress = addressService.selectByCusIdAndAddrId(customerId, addressId);
        else
            customerAddress = addressService.queryDefaultAddr(customerId);
        List<DeliveryPoint> dps = null;

        ExpressConf exp = iExpressConfBiz.getExpressConfByUsedField();

        // 自提点是否启用条件
        if (null != exp && customerAddress != null) {
            // 收货地址当前城市所有的自提点
            dps = deliveryPointService.selectDeliveryPointBycityId(Long.valueOf(customerAddress.getInfoCity()));
        }
        return dps;
    }

    /**
     * 新订单
     *  KKK 去结算
     * @param box
     * @param request
     * @param response
     * @return ModelAndView
     */
    @RequestMapping("/newsuborder")
    public ModelAndView newsubOrder(Long[] box, HttpServletRequest request, HttpServletResponse response,String presentScopeIds) {
        ModelAndView mav = null;
        request.getSession().setAttribute("tok", UUID.randomUUID().toString());
        // 获取用户ID
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        if (customerId == null) {
            return new ModelAndView(new RedirectView(LOGIN));
        }
        if (box == null || box.length == 0) {
            return new ModelAndView(new RedirectView(LOGIN));
        }


        /**
         * 校验 购物车中的购物车id 是否是当前用户的
         */
        if (!validateShopCart(box, customerId)) {
            LOGGER.error("box is error...");
            return new ModelAndView(new RedirectView(MYSHOPPINGCART));
        }

        //判断购物车商品数据是否存在下架或者删除异常信息
        boolean isNormal = shoppingCartService.checkHasErrorProduct(box);
        //存在异常信息则跳转异常页面
        if (!isNormal) {
            mav = new ModelAndView(GOODSNOEXIT);
            return topAndBottomService.getSimpleTopAndBottom(mav);
        }

        // 获取地址信息
        ShoppingCartWareUtil wareUtil = shoppingCartAddressService.loadAreaFromRequest(request);
        // 积分兑换规则
        PointSet pointSet = this.couponService.selectPointSet();
        Map<String, Object> shopMap = shoppingCartService.subshopCartMap(request, box,wareUtil, pointSet);
        mav = new ModelAndView(ShoppingCartConstants.NEWSUBORDER)
                .addObject("cartMap", shopMap)
                .addObject("pro", marketingService.selectAll()).addObject("wareUtil", wareUtil)
                .addObject("sx", request.getSession().getAttribute("tok").toString())
                .addObject(PAYLIST, paymentService.selectAllForSite())
                .addObject("pointSet", pointSet)
                .addObject("presentScopeIds", presentScopeIds);


        return topAndBottomService.getSimpleTopAndBottom(mav);

    }

    /**
     * 校验 购物车 主要是校验 购物车里面的购物车id 是否是用户自己的
     *
     * @param box        购物车id
     * @param customerId 用户id
     * @return 如果是自己的返回true  否则返回false
     */
    private boolean validateShopCart(Long[] box, Long customerId) {
        return shoppingCartService.validateShopCart(box, customerId);
    }


    /**
     * 输入使用优惠券
     */
    @RequestMapping("/getUsedCouponBycodeNo")
    @ResponseBody
    public int getUsedCouponBycodeNo(Long[] box, HttpServletRequest request, HttpServletResponse response, String codeNo, Long distinctId) {
        return shoppingCartService.getUsedCouponBycodeNo(request, box, codeNo, distinctId);
    }

    /**
     * 提交订单
     *
     * @param box
     * @param request
     * @param marketingId
     * @param thirdId
     * @param csrfToken
     * @param response
     * @param isTemp
     * @return ModelAndView
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/suborder")
    public ModelAndView subOrder(Long[] box, HttpServletRequest request, Long[] marketingId, Long[] thirdId, String csrfToken, HttpServletResponse response, String isTemp) {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        ShoppingCartWareUtil wareUtil = shoppingCartAddressService.loadAreaFromRequest(request);
        if (box == null) {
            return new ModelAndView(new RedirectView(LOGIN));
        }
        if (customerId == null && isTemp == null) {
            return new ModelAndView(new RedirectView(LOGIN)).addObject("isTemp", "1").addObject("box", box);
        } else if (isTemp != null && "1".equals(isTemp)) {
            shoppingCartService.insertShoppingCart(box, request, wareUtil);
            // 删除cookie
            Cookie cook = new Cookie("_npstore_shopcar", null);
            cook.setMaxAge(-1);
            cook.setPath("/");
            response.addCookie(cook);
        }
        Map<String, Object> map = shoppingCartService.subOrder(request, box, marketingId, thirdId, wareUtil, customerId);
        List<ShoppingCart> shoplist = (List<ShoppingCart>) map.get("shoplist");
        result = (BigDecimal) map.get("result");
        List<StoreInfo> slist = new ArrayList<StoreInfo>();

        if (shoplist != null && !shoplist.isEmpty()) {
            for (int i = 0; i < shoplist.size(); i++) {
                if (shoplist.get(i).getFitId() == null) {
                    if (slist == null || slist.isEmpty()) {
                        StoreInfo si = new StoreInfo();
                        si.setStoreId(shoplist.get(i).getGoodsDetailBean().getProductVo().getThirdId());
                        si.setStoreName(shoplist.get(i).getGoodsDetailBean().getProductVo().getThirdName());
                        slist.add(si);
                    }
                    int p = 0;
                    for (int j = 0; j < slist.size(); j++) {
                        if (slist.get(j).getStoreId().equals(shoplist.get(i).getGoodsDetailBean().getProductVo().getThirdId())) {
                            p = 1;
                        }
                    }
                    if (p == 0) {
                        StoreInfo si = new StoreInfo();
                        si.setStoreId(shoplist.get(i).getGoodsDetailBean().getProductVo().getThirdId());
                        si.setStoreName(shoplist.get(i).getGoodsDetailBean().getProductVo().getThirdName());
                        slist.add(si);
                    }
                }
            }

        }

        if (shoplist == null || shoplist.isEmpty()) {
            return new ModelAndView(new RedirectView(MYSHOPPINGCART));
        }
        // 新订单结算页
        ModelAndView mav = new ModelAndView("order/newsuborder").addObject("map", map)
                .addObject("sx", request.getSession().getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME)).addObject(PAYLIST, paymentService.selectAllForSite());

        mav.addObject("frightlist", freightTemplateService.selectFreightTemplateDefault());
        return topAndBottomService.getSimpleTopAndBottom(mav).addObject("storelist", slist);
    }

    /**
     * 查询订单优惠
     *
     * @return int
     */
    @RequestMapping("/selectordermarketingbygoodsids")
    @ResponseBody
    public List<Marketing> selectordermarketingbygoodsids(Long[] goodsIds) {
        return new ArrayList<Marketing>();
    }

    /**
     * 修改优惠
     *
     * @param shoppingCartId
     * @param orderMarketId
     * @return int
     */
    @RequestMapping("/changeshoppingcartordermarkets")
    @ResponseBody
    public int changeshoppingcartordermarkets(Long shoppingCartId, Long orderMarketId) {
        return 0;
    }

    /**
     * KKK 提交订单
     *
     * @param request
     * @param shoppingCartId
     * @param customerRemark
     * @param chInvoiceTitle
     * @param chInvoiceContent
     * @return
     * @throws IOException
     */
    @RequestMapping("/submitOrder")
    public ModelAndView newsubmitOrder(HttpServletRequest request, Long[] shoppingCartId, String customerRemark, String chInvoiceTitle, String chInvoiceContent,String presentScopeIds) throws IOException {
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null || shoppingCartId == null || shoppingCartId.length == 0) {
            return new ModelAndView(new RedirectView(LOGIN));
        }
        String reg = "[`~<>]";
        if (chInvoiceTitle != null) {
            Pattern p = Pattern.compile(reg);
            Matcher invoiceTitleMathcer = p.matcher(chInvoiceTitle);
//            if (invoiceTitleMathcer.find()) {
//                throw new RuntimeException("发票抬头含有特殊字符！");
//            }
        }
        List<Order> orders = new ArrayList<>();
        // 判断订单留言长度是否符合条件
        if (!customerRemark.matches(reg) || customerRemark.length() < 40) {
            orders = siteOrderService.newsubmitOrder(request, shoppingCartId, customerRemark, chInvoiceTitle, chInvoiceContent);
            if(!CollectionUtils.isEmpty(orders)){
                //将第一个的商品名称带出来
                Order order = siteOrderService.getPayOrder(orders.get(0).getOrderId());
                orders.set(0,order);
            }
        } else {
//            throw new RuntimeException("客户留言格式不正确！");
        }
        if (orders.isEmpty() || orders.size() <= 0) {
            return new ModelAndView(new RedirectView(MYSHOPPINGCART));
        }
        Map<String, Object> paramMap = new HashMap<>();
        // 订单信息
        paramMap.put("orders", orders);
        // 付款信息
        paramMap.put(PAYLIST, payService.queryAllPaySet());

        //订单通知消息
        OrderNotice record = new OrderNotice();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Order order : orders) {
            record.setCustomerId(customerId);
            record.setOrderId(order.getOrderId());
            record.setNoticeTitle("订单通知");
            record.setNoticeContent("您的订单 " + order.getOrderCode() + " 已经提交成功，我们将及时为您处理。");
            record.setIsRead("0");
            totalPrice = totalPrice.add(order.getOrderPrice());
            this.orderNoticeService.addNotice(record);
        }
        paramMap.put("totalPrice",totalPrice);
        //ModelAndView mav = new ModelAndView("order/subordersucc")
        //        .addObject("map", paramMap);
        ModelAndView mav = new ModelAndView("order/newpayorder")
                .addObject("map", paramMap);
        return topAndBottomService.getSimpleTopAndBottom(mav);
    }


    /**
     * 去付款
     *
     * @param orderId
     * @return ModelAndView
     */
    @RequestMapping("gopayorder")
    public ModelAndView gopayorder(Long orderId, HttpServletRequest request) {
        // 获得用户id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 获取单个订单对象
        Order order = siteOrderService.getPayOrder(orderId);
        if (order != null && customerId != null && customerId.equals(order.getCustomerId())) {
            LOGGER.info("给订单号为：【" + order.getOrderCode() + "】的订单付款");
        } else {
            order = null;
        }

        ModelAndView mav = new ModelAndView("order/orderpay").addObject("order", order).addObject(PAYLIST, payService.queryAllPaySet());

        return topAndBottomService.getSimpleTopAndBottom(mav);
    }

    /**
     * 银联 异步回调
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/unionpaynotifysuccess")
    public void unionpayNotifySuccess(HttpServletRequest req, HttpServletResponse resp) {

        try {
            req.setCharacterEncoding(ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("支付回调，字符转换错误" + e);
        }
        Map<String, String> respParam = getAllRequestParam(req);
        if ("00".equals(respParam.get("respCode"))) {
            // 交易成功
            String orderCode = respParam.get(ConstantUtil.ORDERID);
            LOGGER.info("=================银联支付===========" + orderCode);
            // 重新将session登入
            Order or = siteOrderService.getPayOrderByCode(orderCode);
            LOGGER.info("=================银联支付===========" + or.getOrderId());
            siteOrderService.payOrder(or.getOrderId());
            // 更新还款单状态
            Receivables receivables = this.receivablesService.queryByOrderCode(orderCode);
            this.receivablesService.updatePayStatus(receivables);

        }
    }

    /**
     * 银联 同步回调
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/unionpaysuccess")
    public ModelAndView unionPaySuccess(HttpServletRequest req, HttpServletResponse resp) {

        try {
            req.setCharacterEncoding(ISO_8859_1);
        } catch (UnsupportedEncodingException e1) {
            LOGGER.error("支付前台通知错误" + e1);
        }

        Map<String, String> respParam = getAllRequestParam(req);
        if ("00".equals(respParam.get("respCode"))) {
            // 交易成功
            String orderCode = respParam.get(ConstantUtil.ORDERID);
            LOGGER.info("=================银联支付===========" + orderCode);
            // 重新将session登入
            Order or = siteOrderService.getPayOrderByCode(orderCode);
            List<Order> orderList = new ArrayList<Order>();
            orderList.add(or);
            LOGGER.info("=================银联支付===========" + orderCode);
            CustomerAllInfo ca = customerServiceInterface.selectByPrimaryKey(or.getCustomerId());
            req.getSession().setAttribute("cust", ca);
            req.getSession().setAttribute(CustomerConstantStr.CUSTOMERID, ca.getCustomerId());
            // 更新订单状态
            siteOrderService.payOrder(or.getOrderId());
            // 更新还款单状态
            Receivables receivables = this.receivablesService.queryByOrderCode(orderCode);
            this.receivablesService.updatePayStatus(receivables);
            // 支付的订单价格
            BigDecimal price = new BigDecimal(0);
            price = price.add(or.getOrderPrice());
            Map<String, Object> map = siteOrderService.queryGoodsProduceByOrderId(or.getOrderId(), null);
            ModelAndView mav = new ModelAndView("order/paysuccess").addObject("order", orderList).addObject("gs", map.get("list")).addObject(PRICE, price);
            return topAndBottomService.getSimpleTopAndBottom(mav);

        } else {
            try {
                resp.getWriter().write("交易失败");
            } catch (IOException e) {
                LOGGER.error("支付回调失败" + e);
            }
            return null;
        }
    }

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
            }
        }
        return res;
    }

    /**
     * 构造HTTP POST交易表单的方法示例
     *
     * @param action  表单提交地址
     * @param hiddens 以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public static String LOGGER(String action, Map<String, String> hiddens) {
        StringBuilder sf = new StringBuilder();
        sf.append("<form id = \"sform\" action=\"" + action + "\" method=\"post\">");
        if (null != hiddens && !hiddens.isEmpty()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
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
     * 立即加入购物车
     *
     * @param goodsNum  购买货品的数量
     * @param productId 货品ID
     * @param request   请求对象
     * @return true 添加成功 false 添加失败
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addproducttoshopmcarl")
    public ModelAndView addProductToShopCarL(Long goodsNum, Long productId, HttpServletRequest request, Long distinctId, HttpServletResponse response, Long fitId)
            throws UnsupportedEncodingException {
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            // 设置地址
            shoppingCart.setDistinctId(null == distinctId ? 1103L : distinctId);
            // 设置商品id
            shoppingCart.setGoodsInfoId(productId);
            // 设置购买数量
            shoppingCart.setGoodsNum(goodsNum);
            Long custId = (Long) request.getSession().getAttribute(CUSTOMERID);
            if (custId == null) {
                return new ModelAndView(new RedirectView(LOGIN));
            }
            shoppingCart.setCustomerId(custId);
            // 添加到购物车并返回购物车id
            Long shopping = shoppingCartService.selectLastId(shoppingCart, response, request);
            if (null != productId) {
                // 获取单个货品对象
                GoodsProductVo goodsProductVo = goodsProductService.queryByPrimaryId(productId);

                LOGGER.info("把货品【" + goodsProductVo.getGoodsInfoName() + "】加入购物车！");
            }
            return topAndBottomService.getSimpleTopAndBottom(new ModelAndView(new RedirectView("suborder.html")).addObject("box", shopping));
        } finally {
            shoppingCart = null;
        }
    }

    /**
     * 换一组
     *
     * @param orderId 订单id
     * @param sataus  当前选中的索引
     * @return
     */
    @RequestMapping("/changetgoods")
    @ResponseBody
    public Map<String, Object> changeTGoods(Long orderId, long sataus) {
        return siteOrderService.queryGoodsProduceByOrderId(orderId, sataus);
    }

    /**
     * 判断是否是已存在的手机号码
     *
     * @param mobel   订单id
     * @param request 当前选中的索引
     * @return
     */
    @RequestMapping("/isshowmobelexist")
    @ResponseBody
    public long isShowMobelExist(String mobel, HttpServletRequest request) {
        LOGGER.info("判断手机号【" + mobel + "】是否存在！");
        return shoppingCartService.isSowMobel(mobel, request);
    }

    /**
     * 查询所有的支付方式
     *
     * @return 查询到的支付方式的列表
     */
    @RequestMapping("/queryAllPaySet")
    @ResponseBody
    public List<Object> queryAllPaySet() {
        return this.payService.queryAllPaySet();
    }

    /**
     * 查询所有的配送方式
     *
     * @return 查询到的配送方式列表
     */
    @RequestMapping("/queryAllExpress")
    @ResponseBody
    public Map<String, Object> queryAllExpress() {
        return this.expressConfBizImpl.queryAllExpress();
    }

    /**
     * 根据地址编号删除收货地址
     *
     * @param addressId 地质ID {@link Long}
     * @return 删除是否成功 true 删除成功 false 删除失败
     */
    @RequestMapping("/delCustAddressById")
    @ResponseBody
    public boolean delCustAddress(Long addressId) {
        return this.customerServiceInterface.deleteCustAddress(addressId) > 0 ? true : false;
    }

    /**
     * 根据地址编号设置为默认收货地址
     *
     * @param addressId 地质ID {@link Long}
     * @return 删除是否成功 true 删除成功 false 删除失败
     */
    @RequestMapping("/modifyDefaultAddressForOrder")
    @ResponseBody
    public boolean modifyDefaultAddressForOrder(HttpServletRequest request, String addressId) {
        Long customerId = getLoginUserId(request);
        return this.customerServiceInterface.modifyIsDefaultAddress(request, customerId.toString(), addressId) > 0 ? true : false;
    }

    /**
     * 更新会员收货地址
     *
     * @param address 待更新的地址实体 {@link CustomerAddress}
     * @return 更新是否成功 true 成功 false失败
     */
    @RequestMapping("/modiCustAddress")
    @ResponseBody
    public boolean modiCustAddress(CustomerAddress address, HttpServletRequest request) {
        return this.customerServiceInterface.modifyCustAddress(address) > 0 ? true : false;
    }

    /**
     * 根据收货地址判断该地区仓库是否有库存
     *
     * @return
     */
    @RequestMapping("/checkWareByCity")
    @ResponseBody
    public Map<String, Object> checkWareByCity(HttpServletRequest request, Long[] shoppingCartId, Long distinctId) {
        return siteOrderService.checkProduct(request, shoppingCartId, distinctId);
    }

    /**
     * 获取当前登录的用户的ID
     *
     * @param request
     * @return
     */
    private Long getLoginUserId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
    }
}
