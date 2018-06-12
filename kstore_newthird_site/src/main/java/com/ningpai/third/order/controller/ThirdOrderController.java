/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.order.controller;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.Customer;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.order.bean.*;
import com.ningpai.order.service.OrderCouponService;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.system.bean.LogisticsSingle;
import com.ningpai.system.service.LogisticsSingleService;
import com.ningpai.third.auth.bean.ThirdPage;
import com.ningpai.third.auth.service.ThirdAuthorityPageService;
import com.ningpai.third.goods.service.ThirdGoodsService;
import com.ningpai.third.goods.util.ThirdValueBean;
import com.ningpai.third.logger.util.OperateLogUtil;
import com.ningpai.third.order.service.ThirdOrderService;
import com.ningpai.third.order.util.OrderValueUtil;
import com.ningpai.third.seller.bean.Express;
import com.ningpai.third.seller.service.ExpressInfoService;
import com.ningpai.third.seller.service.SellerService;
import com.ningpai.third.util.MenuOperationUtil;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 第三方订单列表Controller
 * </p>
 * 
 * @author NINGPAI-LIH
 * @since 2014年5月14日下午 4：04:01
 * @version 2.0
 */
@Controller
public class ThirdOrderController {
    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(ThirdOrderController.class);
    private static final String THIRDID = "thirdId";
    private static final String ORDER = "order";
    private static final String THIRDORDERDELIVERYS_HTML = "thirdorderdeliverys.html";
    private static final String STATUS = "status";
    private static final String CUSTOMERID = "customerId";

    // 第三方订单serveice
    private ThirdOrderService orderService;

    // 订单service
    private OrderService bossOrderService;

    // 第三方商品Service接口
    @Resource
    private ThirdGoodsService thirdGoodsService;

    // 根据店铺ID查询物流信息
    private ExpressInfoService expressInfoService;

    // 物流单模板
    @Resource(name = "LogisticsSingleService")
    private LogisticsSingleService logisticsSingleService;

    /**
     * 商家信息Service
     */
    @Resource(name = "sellerService")
    private SellerService sellerService;
    /**
     * 订单优惠劵
     */
    @Resource(name = "OrderCouponService")
    private OrderCouponService orderCouponService;

    /**
     *优惠券Service
     * */
    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "thirdAuthorityPageService")
    private ThirdAuthorityPageService thirdManagerService;

    @Resource(name="SynOrderService")
    private SynOrderService synOrderService;

    /**
     * 分页查询订单列表
     * 
     * @param pb
     *            分页参数实体类
     * @param n
     *            所属位置
     * @param l
     *            所属位置
     * @param request
     * @param order
     *            参数实体类
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/queryThirdOrderList")
    public ModelAndView queryThirdOrderList(PageBean pb, String n, String l, HttpServletRequest request, Order order) throws UnsupportedEncodingException {
        // post提交中文转码
        if (null != order.getShippingPerson()) {
            String str = new String(order.getShippingPerson().getBytes("ISO8859-1"), ConstantUtil.UTF);
            order.setShippingPerson(str);
        }
        // 设置要跳转的订单列表
        pb.setUrl("queryThirdOrderList");
        // 填充导航/左侧菜单索引 用于页面控制css选中
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        // 设置第三方店铺签约id
        order.setBusinessId((Long) request.getSession().getAttribute(THIRDID));
        // 设置订单状态
        order.setDelFlag("0");
        Map<String, Object> map = new HashMap<String, Object>();
        // 装载订单对象
        map.put(ORDER, order);
        // 获取前台地址
        map.put("bset", this.thirdGoodsService.bsetUrl());

        // 设置pageBean属性
        // 分页查询第三方所有的订单
        map.put("pb", orderService.searchOrderList(pb, order));
        // 设置要跳转的路径
        pb.setUrl("queryThirdOrderList.htm?n=" + n + "&l=" + l);
        // 当前登录的会员ID
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        List<ThirdPage> thirdPages=thirdManagerService.queryMenuByManager(customerId);
        //防止从订单列表页面进入拣货列表页面  从而越权
        String isPage="";
        if(thirdPages!=null&&!thirdPages.isEmpty()){
            for(int j=0;j<thirdPages.size();j++){
                if(thirdPages.get(j).getMenuVos()!=null&&!thirdPages.get(j).getMenuVos().isEmpty()){
                    for(int i=0;i<thirdPages.get(j).getMenuVos().size();i++){
                        //防止从订单列表页面进入拣货列表页面
                        if("querythirdoutstock.html".equals(thirdPages.get(j).getMenuVos().get(i).getUrl())){
                            isPage="0";
                            break;
                        }
                    }
                }

            }
        }
        map.put("isPage",isPage);
        // 重定向到订单列表
        return new ModelAndView(OrderValueUtil.THIRDORDERLIST, "map", map);
    }

    /**
     * 查询订单明细
     * 
     * @param orderId
     *            要查询的订单id
     * @return
     */
    @RequestMapping("/searcharOrderByParam")
    @ResponseBody
    public Order searcharOrderByParam(Long orderId) {
        // 根据ID 获取单个的订单信息
        return orderService.searcharOrderByParam(orderId);
    }

    /**
     * 更新订单价格
     * 
     * @param order
     *            要修改的参数
     * @return
     */
    @RequestMapping("/updateThirdOrder")
    public ModelAndView updateThirdOrder(HttpServletRequest request, Order order) {
        order.setBusinessId((Long) request.getSession().getAttribute(THIRDID));
        Order orderPre = orderService.searcharOrderByParam(order.getOrderId());
        order.setModifyPrice(orderPre.getOrderPrice().subtract(order.getOrderPrice()));
        // 更新订单信息
        orderService.updateThirdOrder(order);
        List<OrderGoods> orderGoodses = orderService.queryOrderGoods(order.getOrderId());
        BigDecimal all = BigDecimal.ZERO;
        for(OrderGoods og : orderGoodses){
            all = all.add(og.getGoodsBackPrice());
        }
        for(OrderGoods orderGoods : orderGoodses){
            orderService.modifyGoodsBackPrice(orderGoods.getGoodsBackPrice().subtract(orderGoods.getGoodsBackPrice().multiply(order.getModifyPrice()).divide(all, 2)),order.getOrderId(),orderGoods.getGoodsInfoId());
        }
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "修改订单价格", "订单编号为【"+orderPre.getOrderCode()+"】的订单价格由【"+orderPre.getOrderPrice()+"】改为【" + order.getOrderPrice() + "】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("修改订单价格");
            }
        }
        // 跳转到订单首页
        return new ModelAndView(new RedirectView(OrderValueUtil.QUERYTHIRDORDERLIST));
    }

    /**
     * 订单作废
     * 
     * @param orderId
     *            要删除的单个订单ID
     * @return
     */
    @RequestMapping("/delThirdOrderByparam")
    public ModelAndView delThirdOrderByparam(HttpServletRequest request, Long orderId) {
        // 创建单个订单对象
        Order order = new Order();
        order.setBusinessId((Long) request.getSession().getAttribute(THIRDID));
        // 设置订单ID
        order.setOrderId(orderId);
        // 设置订单状态
        order.setOrderStatus(OrderValueUtil.ORDERSTATUS);
        // 根据ID更新单个订单的信息
        orderService.updateThirdOrder(order);
        // 根据订单id查询到优惠劵劵码，修改优惠劵
        orderCouponService.modifyCouponStatusNew(orderId);
        Order preorder = orderService.searcharOrderByParam(orderId);
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "取消订单", "取消订单，订单号【"+preorder.getOrderCode()+"】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("取消订单");
            }
        }
        // 重定向到订单列表控制器
        return new ModelAndView(new RedirectView(OrderValueUtil.QUERYTHIRDORDERLIST));
    }

    /**
     * 批量删除
     * 
     * @param thirdOrderId
     *            要批量删除的商品id
     * @return
     */
    @RequestMapping("/delThirdOrderByparams")
    public ModelAndView delThirdOrderByparams(Long[] thirdOrderId) {
        // 根据多个订单ID批量更新订单信息
        orderService.updateThirdOrderByParams(thirdOrderId);
        // 重定向到订单列表
        return new ModelAndView(new RedirectView(OrderValueUtil.QUERYTHIRDORDERLIST));
    }

    /**
     * 更新订单状态
     * 
     * @param orderId
     *            需要更新的订单 {@link Long}
     * @param orderStatus
     *            更新的状态ID {@link String}
     */
    @RequestMapping("/updateThirdOrderSta")
    public ModelAndView updateThirdOrderSta(HttpServletRequest request, Long orderId, String orderStatus) {
        // 商家ID
        Long thirdId = (Long) request.getSession().getAttribute(THIRDID);
        if (null != orderId && null != orderStatus) {
            // 根据ID更新单个的订单信息
            if (this.bossOrderService.modifyOrderByKey(orderId, thirdId, orderStatus) == 1)
            {
                synOrderService.SynOrder(orderId);
            }
            Order order = bossOrderService.getPayOrder(orderId);
            //添加操作日志
            if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
                Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
                if(cust.getCustomerUsername() != null){
                    // 操作日志
                    OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "修改订单状态", "修改订单编号为【"+order.getOrderCode()+"】的订单状态为：已付款待发货-->用户名：" + cust.getCustomerUsername());
                    LOGGER.info("修改订单状态");
                }
            }
        }
        // 重定向到订单列表页
        return new ModelAndView(new RedirectView(OrderValueUtil.QUERYTHIRDORDERLIST));
    }

    /**
     * 出库
     * 
     * @param orderId
     *            订单ID
     * @param request
     * @return
     */
    @RequestMapping("/thirdorderdeliverys")
    public ModelAndView thirdOrderDeliverys(Long orderId, HttpServletRequest request) {
        // 设置默认包裹
        bossOrderService.initContainerRelation(orderId);
        // 设置订单包裹商品和赠品
        List<OrderContainerRelation> relations = bossOrderService.queryContainerRalation(orderId);
        // 查询订单信息
        Order order = bossOrderService.getPayOrder(orderId);
        // 设置要跳转的页面 装载订单信息和出路装箱关系对象
        return new ModelAndView("order/printoutstock", ORDER, order).addObject("relations", relations);
    }

    /**
     * 新增装箱单
     * 
     * @param orderId
     *            订单id
     * @return ModelAndView
     */
    @RequestMapping("/thirdaddcontainer")
    public ModelAndView thirdAddContainer(Long orderId) {
        // 添加装箱单
        bossOrderService.addContainerRalation(orderId);
        // 返回到出库页面
        return new ModelAndView(new RedirectView(THIRDORDERDELIVERYS_HTML))
        // 订单ID
                .addObject(ConstantUtil.ORDERID, orderId);
    }

    /**
     * 更改包裹
     * 
     * @param container
     *            参数
     * @param orderId
     *            订单id
     * @return ModelAndView
     */
    @RequestMapping("/thirdupdatecontainer")
    public ModelAndView thirdUpdateContainer(OrderContainer container, Long orderId) {
        // 根据原包裹cId 获得此包裹中商品信息
        OrderContainer orderContainer = bossOrderService.queryOrderContainerById(container.getContainerId());
        // 根据货品Id 和包裹关联rId 查询商品信息
        OrderContainer newCon = bossOrderService.queryOrderContainerByGoodInfoId(container.getRelationId(), orderContainer.getGoodsInfoId());

        if (orderContainer.getGoodsNum() > container.getGoodsNum()) {
            // 修改原包裹内商品的数量
            // 修改数量
            orderContainer.setGoodsNum(orderContainer.getGoodsNum() - container.getGoodsNum());
            // 更改包裹
            bossOrderService.updateRelation(orderContainer);

            if (newCon != null) {
                // 添加数量
                newCon.setGoodsNum(newCon.getGoodsNum() + container.getGoodsNum());
                // 更改包裹
                bossOrderService.updateRelation(newCon);
            } else {
                // 插入新包裹中商品信息
                newCon = container;
                // 将cid设为null
                newCon.setContainerId(null);
                // 设置货品Id
                newCon.setGoodsInfoId(orderContainer.getGoodsInfoId());
                newCon.setContainerStatus("0");
                //判断赠品 设置赠品标识
                if(StringUtils.isNotEmpty(orderContainer.getContainerStatus())){
                    if(orderContainer.getContainerStatus().equals("1")){
                        newCon.setContainerStatus("1");
                    }
                }
                // 添加包裹商品
                bossOrderService.addRelation(newCon);
            }

        } else if (orderContainer.getGoodsNum().equals(container.getGoodsNum())) {
            if (newCon != null) {
                // 添加数量
                newCon.setGoodsNum(newCon.getGoodsNum() + container.getGoodsNum());
                // 修改
                bossOrderService.updateRelation(newCon);
                // 删除原包装中的此商品
                bossOrderService.delContainerByCId(container.getContainerId());
            } else {
                // 修改包裹
                bossOrderService.updateRelation(container);
            }
        }
        // 跳转到出库列表
        return new ModelAndView(new RedirectView(THIRDORDERDELIVERYS_HTML)).addObject(ConstantUtil.ORDERID, orderId);
    }

    /**
     * 去修改订单页面 根据ID获取单个的订单信息
     * 
     * @param orderId
     *            订单那ID
     * @return
     */
    @RequestMapping("goUpdateOrderPage")
    @ResponseBody
    public Order goUpdateOrderPage(Long orderId) {
        return bossOrderService.getPayOrder(orderId);
    }

    /**
     * 删除包裹
     * 
     * @param relationId
     *            包裹id
     * @param orderId
     *            订单id
     * @return ModelAndView
     */
    @RequestMapping("/thirddelrelationbyid")
    public ModelAndView thirdDelRelationById(Long relationId, Long orderId) {
        // 根据包裹ID 删除单个包裹对象
        bossOrderService.delRelationById(relationId);
        // 设置要跳转的路径
        return new ModelAndView(new RedirectView(THIRDORDERDELIVERYS_HTML))
        // 订单ID
                .addObject(ConstantUtil.ORDERID, orderId);
    }

    /**
     * 跳转到出库页面
     * 
     * @param orderId
     *            订单ID
     * @param status
     *            订单状态
     * @return ModelAndView
     */
    @RequestMapping("/tothirdordersendgoods")
    public ModelAndView toThirdOrderSendGoods(HttpServletRequest request, Long orderId, String status) {
        // 设置需要重定向的路径
        return new ModelAndView(OrderValueUtil.ORDERSENDGOODS)
        // 订单ID
                .addObject("orderId", orderId)
                // 订单状态
                .addObject(STATUS, status);
    }

    /**
     * 准备发货
     * 
     * @param orderId
     * @return ModelAndView
     */
    @RequestMapping("/thirdordersendgoods")
    @ResponseBody
    public Map<String, Object> thirdOrderSendGoods(HttpServletRequest request, Long orderId, String status) {
        Express expressList = new Express();
        // 设置订单包裹商品和赠品
        List<OrderContainerRelation> relations = bossOrderService.queryContainerRalation(orderId);
        // 查询订单信息
        Order order = bossOrderService.getPayOrder(orderId);
        // 查询物流信息
        OrderExpress express = bossOrderService.expressDetail(orderId);
        // 商家编号
        Long thirdId = (Long) request.getSession().getAttribute(THIRDID);
        // 物流模板信息
        LogisticsSingle logisticsSingle = logisticsSingleService.selectLogisticsSingle(thirdId, express.getExpressId());
        // 物流信息
        order.setOrderExpress(express);
        // 是否默认
        expressList.setIsDefault("1");
        // 店铺ID
        expressList.setStoreId(thirdId);
        // 查询该店铺下面所有的物流信息(之前是一个下拉列表给客户自己选择)
        // List<Express> expresses =
        // expressInfoService.selectByStoreIds(expressList);
        Map<String, Object> map = new HashMap<String, Object>();
        // 很据卖家编号查找店铺信息
        map.put("store", sellerService.selectByStoreId(thirdId));
        // 物流模板信息
        map.put("logisticsSingle", logisticsSingle);
        // 订单对象
        map.put(ORDER, order);
        // 包裹对象
        map.put("relations", relations);
        // 状态
        map.put(STATUS, status);
        // 物流对象
        map.put("express", express);
        return map;
    }

    /**
     * 发货
     * 
     * @param orderId
     *            订单id
     * @param relationIds
     *            包裹id数组
     * @param expressNo
     *            运单数组
     * @param request
     * @return
     */
    @RequestMapping("/thirdsendorder")
    public ModelAndView thirdSendOrder(Long orderId, Long[] relationIds, String[] expressNo, HttpServletRequest request) {
        // 更新运货单
        bossOrderService.updateSendOrderGoods(relationIds, expressNo, null);
        // 修改订单状态
        bossOrderService.modifyOrderByKey(orderId, "2");
        Order order = orderService.searcharOrderByParam(orderId);
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "订单发货操作", "订单发货操作，订单编号【"+order.getOrderCode()+"】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("订单发货操作");
            }
        }
        // 设置要跳转的路径
        return new ModelAndView(new RedirectView("thirdordersendgoods.html"))
        // 订单ID
                .addObject(ConstantUtil.ORDERID, orderId)
                // 订单状态
                .addObject(STATUS, "0");
    }

    /**
     * 查询商品订单详细
     * 
     * @param orderId
     *            订单ID {@link Long}
     */
    @RequestMapping("/toThirdOrderDetail")
    public ModelAndView toThirdOrderDetail(HttpServletRequest request, HttpServletResponse response,Long orderId) {
        // 商家Id
        Long thirdId = (Long) request.getSession().getAttribute(THIRDID);
        Order order = this.bossOrderService.orderDetail(orderId);
        if(thirdId !=null && order.getBusinessId() != null){
            if(!thirdId.equals(order.getBusinessId())){
                return new ModelAndView(new RedirectView(request.getContextPath() + "/sellerinfo.html"));
            }
        }
        //优惠券名称
        String couponName = "";
        //查询优惠券信息
        if(null != order){
            if(order.getCouponNo()==null || order.getCouponNo().isEmpty()){

            }else{
                Coupon coupon = couponService.selectCouponByCodeNo(order.getCouponNo());
                couponName = coupon.getCouponName();
            }
        }

        return new ModelAndView(OrderValueUtil.THIRDORDERDETAIL, ORDER,order).addObject("couponName",couponName);
    }

    /**
     * 发货
     * 
     * @param orderId
     *            订单id
     * @param relationIds
     *            包裹id数组
     * @param expressNo
     *            运单数组
     * @param request
     * @return
     */
    @RequestMapping("/thirdsubsendgoodsorder")
    public void thirdSubSendGoodsorder(HttpServletResponse response, String token, Long orderId, Long[] relationIds, String[] expressNo, HttpServletRequest request, int[] expressId) {
        PrintWriter pw;
        try {
            pw = response.getWriter();

            if (token == null || !"L".equals(token)) {
                pw.print("0");
            } else {
                if (bossOrderService.judgeStatus("2", orderId) == 0) {
                    pw.print("0");
                }
            }
            // 更新运货单
            bossOrderService.updateThirdSendOrderGoods(relationIds, expressNo, expressId);
            // 修改订单状态
            Long thirdId = (long) request.getSession().getAttribute(THIRDID);
            bossOrderService.sendOrderByP(orderId, "2", null, "2", thirdId);
            // 修改订单的出库状态
            bossOrderService.updateSetCargoStatusByOrderId(orderId, "3");
        } catch (IOException e) {
            Customer cust = (Customer) request.getSession().getAttribute("cust");
            OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
        }
    }

    /**
     * 分页查询第三方商家已购买订单列表
     * 
     * @param pb
     *            分页参数实体类
     * @param n
     *            所属位置
     * @param l
     *            所属位置
     * @param request
     * @param order
     *            参数实体类
     * @return wangtanpeng
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/buyThirdOrderList")
    public ModelAndView buyThirdOrderList(PageBean pb, String n, String l, HttpServletRequest request, Order order) throws UnsupportedEncodingException {
        // post提交中文转码
        if (null != order.getShippingPerson()) {
            String str = new String(order.getShippingPerson().getBytes("ISO8859-1"), ConstantUtil.UTF);
            order.setShippingPerson(str);
        }
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        // 设置第三方店铺签约id
        order.setBusinessId((Long) request.getSession().getAttribute(THIRDID));
        // 设置订单的状态
        order.setDelFlag("0");
        Map<String, Object> map = new HashMap<String, Object>();
        // 订单对象
        map.put(ORDER, order);
        // 设置pageBean属性
        map.put("pb", orderService.searchBuyOrderList(pb, order));
        return new ModelAndView(OrderValueUtil.PUYTHIRDORDERLIST, "map", map);
    }

    public ThirdOrderService getOrderService() {
        return orderService;
    }

    @Resource(name = "ThirdOrderService")
    public void setOrderService(ThirdOrderService orderService) {
        this.orderService = orderService;
    }

    public OrderService getBossOrderService() {
        return bossOrderService;
    }

    @Resource(name = "OrderService")
    public void setBossOrderService(OrderService bossOrderService) {
        this.bossOrderService = bossOrderService;
    }

    public ExpressInfoService getExpressInfoService() {
        return expressInfoService;
    }

    @Resource(name = "expressInfoService")
    public void setExpressInfoService(ExpressInfoService expressInfoService) {
        this.expressInfoService = expressInfoService;
    }
}
