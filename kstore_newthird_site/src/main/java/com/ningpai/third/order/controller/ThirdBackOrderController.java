/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.order.controller;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.service.DepositInfoService;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.manager.bean.Manager;
import com.ningpai.manager.mapper.ManagerMapper;
import com.ningpai.order.bean.*;
import com.ningpai.order.service.*;
import com.ningpai.third.goods.service.ThirdGoodsService;
import com.ningpai.third.goods.util.ThirdValueBean;
import com.ningpai.third.logger.util.OperateLogUtil;
import com.ningpai.third.order.util.OrderValueUtil;
import com.ningpai.third.util.MenuOperationUtil;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>退单Controller</p>
 * @author NINGPAI-LIH
 * @since 2014-5月20日
 * @version 2.0
 */
@Controller
public class ThirdBackOrderController {
    private static final MyLogger LOGGER = new MyLogger(ThirdBackOrderController.class);

    // 退单service
    private BackOrderService backOrderService;
    // 订单service
    private OrderService orderService;

    // 第三方商品Service接口
    @Resource
    private ThirdGoodsService thirdGoodsService;

    private static final String BACKORDERGENERAL = "backOrderGeneral";
    /**
     * 订单优惠劵
     */

    @Resource(name = "OrderCouponService")
    private OrderCouponService orderCouponService;
    // 退单操作日志
    @Resource(name = "BackOrderLogService")
    private BackOrderLogService backOrderLogService;
    @Resource(name = "backGoodsService")
    private BackGoodsService backGoodsService;
    @Autowired
    private DepositInfoService depositInfoService;
    @Autowired
    private TradeInfoService tradeInfoService;
    @Autowired
    private ManagerMapper managerMapper;
    /**
     * 查询换购单或者退单
     *
     * @param bkorder
     *            要查询的退单参数
     * @param pageBean
     *            分页参数
     * @param request
     * @param n
     *            所属位置
     * @param l
     *            所属位置
     * @param tabStatus
     *            5、全部退货单 0、已审核 1、审核通过 2、拒绝订单 3、已退款订单 4、退款完成
     * @return 退单页面
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/queryThirdBackOrderList")
    public ModelAndView queryBackOrderList(String startTime, String endTime, BackOrder bkorder,PageBean pageBean, HttpServletResponse response,HttpServletRequest request, String n, String l, String tabStatus) throws UnsupportedEncodingException {
        //post 提交中文转码
        if(null != bkorder.getShippingPerson()){
            String str = new String(bkorder.getShippingPerson().getBytes("ISO8859-1"), ConstantUtil.UTF);
            bkorder.setShippingPerson(str);
        }

        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        Map<String, Object> map = new HashMap<String, Object>();
        // 如果没有选中，则默认是全部退货单列表
        if (tabStatus != null && !"5".equals(tabStatus)) {
                bkorder.setBackCheck(tabStatus);
        }
        //设置商家ID
        bkorder.setBusinessId((Long) request.getSession().getAttribute("thirdId"));
        //退单信息列表
        map.put("pb", backOrderService.backOrderList(pageBean, bkorder, startTime, endTime));
        //装载退单信息
        map.put("bkorder", bkorder);
        // 获取前台地址
        map.put("bset", this.thirdGoodsService.bsetUrl());
        // 设置目前选中的为 5、全部退货单 0、已审核 1、审核通过 2、拒绝订单 3、已退款订单 4、退款完成
        map.put("tabStatus", tabStatus);
        //设置跳转的路径
        pageBean.setUrl("queryThirdBackOrderList");
        try {
            return new ModelAndView(OrderValueUtil.THIRDBACKORDERLISTS, "map", map);
        } finally {
            map = null;
        }
    }

    /**
     * 更新退单状态
     * @param bkorder 退单信息
     * @return
     */
    @RequestMapping("/queryThirdBackOrderList2")
    public ModelAndView updateBackOrderList(BackOrder bkorder) {
        //设置要跳转的控制器
        return new ModelAndView("queryThirdBackOrderList.htm");
    }






    /**
     * 更新退单状态
     *
     * @param backOrder
     */
    @RequestMapping("/updateBackOrderStatus")
    public ModelAndView updateThirdBackOrderSta(BackOrder backOrder, HttpServletRequest request,String sign) {
        String backInfo = "";
        //操作人
        backOrder.setAuthorName(((Customer) request.getSession().getAttribute("cust")).getCustomerUsername());
        //店铺编号
        backOrder.setBusinessId((Long)request.getSession().getAttribute("thirdId"));

        //退款记录
        BackOrderLog backOrderLog=new BackOrderLog();
        //退款操作人
        backOrderLog.setBackLogPerson(backOrder.getAuthorName());
        //时间
        backOrderLog.setBackLogTime(new Date());
        //订单号
        backOrderLog.setBackOrderId(backOrder.getBackOrderId());

        //查询退单详细
        BackOrder bOrder=backOrderService.selectBackOrderByBackOrderId(backOrder.getBackOrderId());
        Order order = orderService.getPayOrderByCode(bOrder.getOrderCode());
        //退款退货标志
        //拒绝退款
        if(sign != null && ((sign.equals("2") && backOrder.getBackCheck().equals("7")) || (sign.equals("3") && backOrder.getBackCheck().equals("7")))){
            backOrderLog.setBackLogStatus("9");
            backInfo = "拒绝退款";
        //退款中同意退款或退单中最后一步退款成功
        }else if(sign != null && ((sign.equals("2") && backOrder.getBackCheck().equals("10")) || (sign.equals("3") && backOrder.getBackCheck().equals("10")) || (sign.equals("3") && backOrder.getBackCheck().equals("4")))){
            //同意退款
            backOrderLog.setBackLogStatus("8");
            backInfo = "同意退款";
            //支付类型是预存款支付，退款或退单
            updateDepositInfo(order, bOrder);
        }else if(sign != null && sign.equals("1") && backOrder.getBackCheck().equals("2")){
            //退货审核不通过
            backOrderLog.setBackLogStatus("3");
            backInfo = "退货审核不通过";
        }else if(sign != null && sign.equals("1") && backOrder.getBackCheck().equals("1")){
            //退货审核通过
            backOrderLog.setBackLogStatus("2");
            backInfo = "退货审核通过";
            //支付类型是预存款支付，退款或退单
            updateDepositInfo(order, bOrder);
        }

        //记录退款记录
        backOrderLogService.insert(backOrderLog);
        //查询后台修改退单价格之前的BackOrder
        BackOrder backOrderPre = backOrderService.selectBackOrderByBackOrderId(backOrder.getBackOrderId());
        //修改退单状态
        int count = this.backOrderService.modifyThirdBackBeanCheck(backOrder);

        /**
         * 该方法之前是所有状态的都会走退库存操作
         * 在此改成只有退款成功以及退货后确认收货以后在退库存 修改人 houyichang
         *
         * 状态为10 的时候代表售中退款成功
         * 状态为4的时候为售后退货商家确认收货
         * 其他状态一概不退库存以及优惠券
         * */
        if("10".equals(bOrder.getBackCheck())) {
            // 根据订单id查询到优惠劵劵码，修改优惠劵  返还库存
            orderCouponService.modifyCouponStatus(bOrder.getOrderId());
            backInfo = "退款成功";
        }else if("4".equals(bOrder.getBackCheck())){
            List<OrderGoods> orderGoodses = orderService.queryOrderGoods(order.getOrderId());
            BigDecimal all = BigDecimal.ZERO;
            for(OrderGoods og : orderGoodses){
                all = all.add(og.getGoodsBackPrice());
            }
            List<Long> goodsInfoIds = new ArrayList<>();
            String[] ids = bOrder.getBackGoodsIdAndSum().split("-");
            for(String goodsInfoId : ids){
                String id = goodsInfoId.split(",")[0];
                goodsInfoIds.add(Long.parseLong(goodsInfoId.split(",")[0]));
            }
            for(Long goodsInfoId:goodsInfoIds){
                BackGoods backGoods = backOrderService.selectOrdersetBackGodds(goodsInfoId, order.getOrderId());
                if(backOrderPre.getBackPrice() != backOrder.getBackPrice()){
                    backGoods.setBackGoodsPrice(backGoods.getBackGoodsPrice().subtract(backGoods.getBackGoodsPrice().multiply(backOrderPre.getBackPrice().subtract(backOrder.getBackPrice())).divide(all, 2)));
                }
                backGoods.setBusinessId(order.getBusinessId());
                backGoods.setDelFlag("0");
                backGoods.setBackSturts("0");
                backGoodsService.insertSelective(backGoods);
            }
            orderCouponService.modifyStock(bOrder.getOrderId());
            backInfo = "确认收货，退货结束";
        }
        if(count == 1 && bOrder != null && bOrder.getOrderId()!=null){
            backOrderService.reducePointOrderBack(bOrder.getOrderId());
        }
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "退货操作", "退货操作，订单编号【" + order.getOrderCode() + "】状态改为"+backInfo+"-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("退货操作");
            }
        }
        return new ModelAndView(new RedirectView("queryThirdBackOrderList.htm"));
    }

    /**
     * 查看退单详情
     *
     * @param backOrderId
     *            退单ID {@link Long}
     */
    @RequestMapping("/toExamBackOrder")
    public ModelAndView toExamBackOrder(HttpServletRequest request, HttpServletResponse response,Long backOrderId) {
        // 商家Id
        Long thirdId = (Long) request.getSession().getAttribute("thirdId");
        // 根据订单编号查找退单信息
        BackOrder bOrder =  this.backOrderService.detail(backOrderId);
        if(thirdId !=null && bOrder.getBusinessId() != null){
            if(!thirdId.equals(bOrder.getBusinessId())){
                return new ModelAndView(new RedirectView(request.getContextPath() + "/sellerinfo.html"));
            }
        }
        List imglist = new ArrayList();
        if(bOrder.getUploadDocuments() != null) {
            String[] imgs = bOrder.getUploadDocuments().split(",");
            for(int i = 0; i < imgs.length; i++) {
                imglist.add(imgs[i]);
            }
        }
        Long backOrderIdNew = Long.valueOf(backOrderId);
        // 查询物流
        BackOrderGeneral backOrderGeneral = backOrderService.queryBackOrderGeneral(backOrderIdNew);
        // 获取退单日志信息
        List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(bOrder.getBackOrderId());
        return new ModelAndView(OrderValueUtil.THIRDBACKORDERDETAIL)
                .addObject("backorder", bOrder)
                .addObject("imglist", imglist)
                .addObject("backOrderLogs",backOrderLogs)
                .addObject("order",orderService.payOrder(bOrder.getOrderId()))
                .addObject(BACKORDERGENERAL, backOrderGeneral);
    }
    /**
     * 退款金额验证
     * @param backOrderId
     *          退款单ID
     * @return  Object
     *          退款单对象
     */
    @RequestMapping("/checkpriceback")
    @ResponseBody
    public Object checkBackPrice(Long backOrderId){
        //根据退单ID获取退单对象
        return backOrderService.selectBackOrderByBackOrderId(backOrderId);
    }

    public BackOrderService getBackOrderService() {
        return backOrderService;
    }

    @Resource(name = "BackOrderService")
    public void setBackOrderService(BackOrderService backOrderService) {
        this.backOrderService = backOrderService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    @Resource(name = "OrderService")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 退单或退款情况下：
     * 修改会员预存款信息并插入一条交易记录
     * @param order
     * @param backOrder
     */
    public void updateDepositInfo(Order order,BackOrder backOrder){
        if (order.getPayId().intValue() == 5) {
            //用户ID
            Long customerId = order.getCustomerId();

            //退单后的预存款
            BigDecimal preDepositPrice;
            //查询该客户的预存款
            DepositInfo depositInfo = depositInfoService.selectDepositByCustId(customerId);
            preDepositPrice = depositInfo.getPreDeposit();
            if (preDepositPrice == null || preDepositPrice.intValue() == 0){
                preDepositPrice = order.getOrderPrePrice();
            }else{
                preDepositPrice = preDepositPrice.add(order.getOrderPrice());
            }

            //修改该客户的预存款
            depositInfo.setPreDeposit(preDepositPrice);
            if (depositInfo.getFreezePreDeposit() != null && (depositInfo.getFreezePreDeposit()).intValue() != 0){
                preDepositPrice = preDepositPrice.add(depositInfo.getFreezePreDeposit());
            }
            //插入一条交易记录
            TradeInfo tradeInfo = getTradeInfo(customerId,backOrder,order,preDepositPrice);
            tradeInfoService.insertTradeInfo(tradeInfo);
            depositInfoService.updateDeposit(depositInfo);
        }
    }

    /**
     * 构建交易记录信息
     * @param customerId
     * @param backOrder
     * @param order
     * @param currentPrice
     * @return
     */
    public TradeInfo getTradeInfo(Long customerId,BackOrder backOrder,Order order,BigDecimal currentPrice){
        TradeInfo tradeInfo  = new TradeInfo();
        tradeInfo.setCustomerId(customerId);
        tradeInfo.setOrderCode(backOrder.getOrderCode());
        //交易类型 0在线充值 1订单退款 2线下提现 3订单消费
        tradeInfo.setOrderType("1");
        //提现状态 0【提现】待审核 1【提现】已打回 2【提现】已通过待打款 3【提现】已打款待确认 4【提现】已完成 5未支付 6充值成功 8已取消
        tradeInfo.setOrderStatus("4");
        //当前预存款
        tradeInfo.setCurrentPrice(currentPrice);
        tradeInfo.setOrderPrice(backOrder.getBackPrice());
        tradeInfo.setCreateTime(new Date());
        tradeInfo.setUpdateTime(new Date());
        tradeInfo.setDelFlag("0");
        /*String username = request.getSession().getAttribute("name").toString();
        Manager manager = managerMapper.selectByName(username);
        if (manager != null){
            tradeInfo.setCreatePerson(manager.getId());
        }*/
        tradeInfo.setTradeRemark("退款订单:"+order.getOrderCode()+",退款单号:"+backOrder.getBackOrderCode());
        return tradeInfo;
    }

}
