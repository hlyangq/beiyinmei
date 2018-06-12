/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.controller;

import com.ningpai.comment.bean.Comment;
import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.m.backorder.service.ReturnGoodsService;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.service.CustomerOrderService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.customer.vo.OrderInfoBean;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.order.service.OrderCouponService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.SystemsSet;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IsBackOrderService;
import com.ningpai.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 手机端会员订单控制器
 * 
 * @author qiyuanyuan
 * 
 */
@Controller
public class CustomerOrderControllerM {

    private static  final String PARAMSTRING="paramString";
    // spring注解 会员订单service
    @Resource(name = "customerOrderServiceM")
    private CustomerOrderService customerOrderService;

    @Resource(name = "commentServiceMapper")
    private CommentServiceMapper commentServiceMapper;

    @Resource(name = "OrderCouponService")
    private OrderCouponService orderCouponService;
    
    @Resource(name="MReturnGoodsService")
    private ReturnGoodsService returnGoodsService;

    @Resource(name = "SeoService")
    private SeoService seoService;

    @Resource(name="IsBackOrderService")
    private IsBackOrderService isbackOrderService;

    @Resource(name="OrderMService")
    private OrderMService orderMService;

    @Resource(name = "OrderMapper")
    private OrderMapper orderMapper;

    @Resource(name = "SynOrderService")
    private SynOrderService synOrderService;

    @Resource(name = "RushCustomerMapper")
    private RushCustomerMapper rushCustomerMapper;

    @Resource(name = "customerAddressServiceM")
    private CustomerAddressService addressService;

    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;
    /**
     * 跳转我的订单页面
     * 
     * @param request
     * @return ModelAndview
     */
    @RequestMapping("/myorder")
    public ModelAndView queryAllOrders(HttpServletRequest request, PageBean pb, String date, String type, String paramString) {
        Map<String, Object> paramMap = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView mav = null;
        Long customerId = null;
        try {
            // 检查用户是否登录
            if (LoginUtil.checkLoginStatus(request)) {
                paramMap = new HashMap<String, Object>();
                customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
                paramMap.put(CustomerConstants.CUSTOMERID, customerId);
                paramMap.put(CustomerConstants.DATE, date);
                paramMap.put(CustomerConstants.TYPE, type);
                paramMap.put(PARAMSTRING, paramString);
                pb.setUrl("/customer/myorder");
                resultMap.put(CustomerConstants.TYPE, type);
                resultMap.put(PARAMSTRING, paramString);
                resultMap.put(CustomerConstants.DATE, date);
                // 获取退款说明
                SystemsSet systemsSet = isbackOrderService.getIsBackOrder();
                // 是否允许退单
                resultMap.put("isBackOrder", systemsSet.getIsBackOrder());
                if(type != null && "6".equals(type)){
                    resultMap.put(CustomerConstants.PB, returnGoodsService.queryAllBackOrders(paramMap, pb));
                    mav = new ModelAndView(CustomerConstants.BACKORDER).addAllObjects(resultMap);
                }else {
                    resultMap.put(CustomerConstants.PB, customerOrderService.queryAllMyOrders(paramMap, pb));
                    mav = new ModelAndView(CustomerConstants.MYORDER).addAllObjects(resultMap);
                }
            } else {
                // 没登录的用户跳转到登录页面
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
                mav.addObject("url","/customer/myorder.html");
            }
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
            paramMap = null;
        }
    }
    
    
    /**
     * 跳转我的订单页面
     * 
     * @param request
     * @return ModelAndview
     */
    @RequestMapping("/allmyorder")
    @ResponseBody
    public Map<String, Object> queryAllMyOrders(HttpServletRequest request, PageBean pb, String date, String type, String paramString) {
        Map<String, Object> paramMap = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 检查用户是否登录
        if (LoginUtil.checkLoginStatus(request)) {
            paramMap = new HashMap<String, Object>();
            Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
            paramMap.put(CustomerConstants.CUSTOMERID, customerId);
            paramMap.put(CustomerConstants.DATE, date);
            paramMap.put(CustomerConstants.TYPE, type);
            paramMap.put(PARAMSTRING, paramString);
            // 获取退款说明
            SystemsSet systemsSet = isbackOrderService.getIsBackOrder();
            // 是否允许退单
            resultMap.put("isBackOrder", systemsSet.getIsBackOrder());
            resultMap.put(CustomerConstants.TYPE, type);
            if(type != null && "6".equals(type)){
                resultMap.put(CustomerConstants.PB, returnGoodsService.queryAllBackOrders(paramMap, pb));
             }else{
               resultMap.put(CustomerConstants.PB, customerOrderService.queryAllMyOrders(paramMap, pb));
             }
        }
        return resultMap;
    }
    

    /**
     * 根据订单编号查询订单信息
     * 
     * @param orderId
     *            订单编号
     * @return ModelAndView
     */
    @RequestMapping("/orderdetails")
    public ModelAndView queryOrderByOrderId(HttpServletRequest request, Long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView mav = null;
        try {
            if (LoginUtil.checkLoginStatus(request)) {
                Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
                OrderInfoBean orderInfoBean = (OrderInfoBean)customerOrderService.queryOrderByCustIdAndOrderId(orderId, customerId);
                resultMap.put("order", orderInfoBean);
                Long expressType = orderInfoBean.getOrderExpressType();
                if(1L==expressType){//自提,查用户收货地址和自提地址，否则的话，orderInfoBean里面的信息就是收货地址
                    DeliveryPoint deliveryPoint = deliveryPointService.getDeliveryPoint(orderInfoBean.getAddressId());
                    resultMap.put("deliveryPoint",deliveryPoint);
                    CustomerAddress customerAddress = null;
                    if (customerId != null) {
                        customerAddress = addressService.queryDefaultAddr(customerId);
                    }
                    if (null == customerAddress) {
                        customerAddress = addressService.selectByCIdFirst(customerId);
                    }
                    resultMap.put("shippingAddress",customerAddress);
                }
                mav = new ModelAndView(CustomerConstants.ORDERDETAIL).addAllObjects(resultMap);
            } else {
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
            resultMap = null;
        }
    }

    
    /**
     * 跳转会员中心--评论页
     * 
     * @param orderId 
     *         订单id
     * @return mav
     */
    @RequestMapping("/tocomment")
    public ModelAndView toShare(HttpServletRequest request, Long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView mav = null;
        Long customerId = null;
        try {
            if (LoginUtil.checkLoginStatus(request)) {
                customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
                resultMap.put("order", customerOrderService.queryOrderByCustIdAndOrderId(orderId, customerId));

                mav = new ModelAndView("/customer/order_comment_list").addAllObjects(resultMap);
            } else {
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
            resultMap = null;
        }
    }

    /**
     * 待评价商品详情页
     * @param goodsId
     *             货品id
     * @param orderId
     *              订单id
     * @return mav
     */
    @RequestMapping("/commentgoods")
    public ModelAndView commentGoods(HttpServletRequest request,Long goodsId,Long orderId,Long commentId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView mav = null;
        Long customerId = null;
        try {
            if (LoginUtil.checkLoginStatus(request)) {
                customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
                resultMap.put("good", customerOrderService.selectByOrderIdAndGoodsId(customerId, goodsId, orderId,commentId));
                resultMap.put("cusId",customerId);
                mav = new ModelAndView("/customer/order_comment").addAllObjects(resultMap);
            } else {
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
            resultMap = null;
        }
    }
    
    
    /**
     * 商品评论
     * 
     * @param request
     * @return {@link ModelAndView}
     */
    @RequestMapping("/comment")
    public ModelAndView toViewComment(HttpServletRequest request, PageBean pageBean) {
        pageBean.setUrl("customer/comment");
        ModelAndView mav = null;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(CustomerConstants.PB, commentServiceMapper.queryCustComment((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID), pageBean));
            if (LoginUtil.checkLoginStatus(request)) {
                mav = new ModelAndView(CustomerConstants.COMMENT);
                mav.addAllObjects(resultMap);
            } else {
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            return seoService.getCurrSeo(mav);
        } finally {
            resultMap = null;
        }
    }

    /**
     * 添加商品评论
     * 
     * @param request
     *              请求
     * @param comment  
     *              评论对象
     * @return int
     */
    @RequestMapping("/addgoodscomment")
    @ResponseBody
    public int addGoodsComment(HttpServletRequest request, @Valid Comment comment, Long orderId) {
       //初始化f值
        int f =0;
        //判断是否登陆
        if (LoginUtil.checkLoginStatus(request)) {
            //执行添加评论操作
            f =customerOrderService.addGoodsComment(request, comment, orderId);
        } 
        //返回f
        return f;
    }

    /**
     * 取消订单
     * 
     * @param request
     * @param pb
     *            页面数据
     * @param orderId
     *            订单编号
     * @return ModelAndView
     */
    @RequestMapping("/cancelorder")
    public ModelAndView cancelOrder(HttpServletRequest request, PageBean pb, Long orderId, String reason) {
        ModelAndView mav = null;
        if(reason != null && !"".equals(reason)){
            if (!Pattern.compile("[^\\<\\>]+$").matcher(reason).find()) {
                throw new IllegalArgumentException();
            }
        }
        try {
            // 检查用户是否登录
            if (LoginUtil.checkLoginStatus(request)) {
                customerOrderService.cancelOrder(orderId, reason);
                orderCouponService.modifyCouponStatusNew(orderId);
                //删除抢购的订单选中数量
                rushCustomerMapper.updateRushcustomerByOrderId(orderId);
                // 同步订单到Ｅ点宝
                synOrderService.SynOrder(orderId);
                // 控制跳转
                mav = new ModelAndView("redirect:/customer/myorder.html");
            } else {
                // 没登录的用户跳转到登录页面
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            // 跳转订单页面
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
        }
    }

    /**
     * 确认订单
     * 
     * @param request
     * @param pb
     *            页面数据
     * @param orderId
     *            订单编号
     * @return ModelAndView
     */
    @RequestMapping("/comfirmofgooods")
    public ModelAndView comfirmofGoods(HttpServletRequest request, PageBean pb, Long orderId) {
        ModelAndView mav = null;
        try {
            // 检查用户是否登录
            if (LoginUtil.checkLoginStatus(request)) {
                Order order = orderMService.getPayOrder(orderId);
                //订单选择货到付款，确认收货时 加上payTime（后台首页统计，货到付款的也应该算）
                if(order != null && order.getPayTime()==null){
                    // 支付时间
                    order.setPayTime(new Date());
                }
                order.setGetGoodsTime(new Date());
                order.setOrderStatus("3");
                //设置该订单曾经已完成过
                order.setFinishSturts("1");
                //确认收货
                orderMService.comfirmOrder(order, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
//                orderMapper.updateOrderStatusByorderIdFortask(order);
//                orderCouponService.modifyCouponByOrderId(orderId, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                mav = new ModelAndView("redirect:/customer/myorder.html");
            } else {
                // 没登录的用户跳转到登录页面
                mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
            }
            // 跳转订单页面
            return seoService.getCurrSeo(mav);
        } finally {
            mav = null;
        }
    }
}
