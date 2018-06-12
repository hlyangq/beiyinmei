/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.order.controller;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.customer.service.DepositInfoService;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.manager.bean.Manager;
import com.ningpai.manager.mapper.ManagerMapper;
import com.ningpai.order.bean.*;
import com.ningpai.order.service.*;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * 
 * @author zhanghailong
 *
 */
@Controller
public class BackOrderController {

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(BackOrderController.class);

    private static final String BACKORDERLIST_HTM = "backorderlist.htm";
    private static final String BACKORDER = "backorder";
    private static final String ORDERLOGS = "orderLogs";
    private static final String ORDERID = "orderId";
    private static final String BACKORDERLOGS = "backOrderLogs";
    private static final String BACKORDERGENERAL = "backOrderGeneral";
    private static final String ADMIN = "admin";

    private BackOrderService backOrderService;

    private OrderService orderService;

    // 退单操作日志
    private BackOrderLogService backOrderLogService;

    // 订单操作记录
    private OrderLogService orderLogService;
    /**
     * 订单优惠劵
     */

    @Resource(name = "OrderCouponService")
    private OrderCouponService orderCouponService;

    @Autowired
    private DepositInfoService depositInfoService;
    @Autowired
    private TradeInfoService tradeInfoService;
    @Autowired
    private ManagerMapper managerMapper;

    /**
     * boss -订单列表-退单列表
     * 
     * @param bkorder
     *            退单信息
     * @param pageBean
     * @return ModelAndView
     */
    @RequestMapping("/backorderlist")
    public ModelAndView backOrderList(HttpServletRequest request, BackOrder bkorder, PageBean pageBean, String startTime, String endTime) {
        // 查询所有平台的订单
        bkorder.setBusinessId(0L);
        MenuSession.sessionMenu(request);
        // 查询退单列表
        pageBean.setUrl(OrderConstants.INITBACKORDER);
        return new ModelAndView(OrderConstants.BACKORDERLIST, "pageBean", backOrderService.backOrderList(pageBean, bkorder, startTime, endTime))
                .addObject("businessId", 0);
    }

    /**
     * boss 商铺订单-退单列表
     * 
     * @param bkorder
     *            退单信息
     * @param pageBean
     * @return ModelAndView
     */
    @RequestMapping("/backorderlististhird")
    public ModelAndView backOrderLististhird(HttpServletRequest request,String status, BackOrder bkorder, PageBean pageBean, String startTime, String endTime) {
        String statusNew = status;
        if (StringUtils.isEmpty(statusNew)) {
            statusNew = "1";
        }
        pageBean.setUrl(OrderConstants.INITBACKORDERISTHIRD);
        return new ModelAndView(OrderConstants.BACKORDERLIST, "pageBean", backOrderService.backOrderList(pageBean, bkorder, startTime, endTime)).addObject("businessId", 1);
    }

    /**
     * 修改退单信息的审核状态
     * 
     * @param backOrder
     * @return
     */
    @RequestMapping(value = "modifyBackOrder")
    public ModelAndView modifyBackOrderByCheck(BackOrder backOrder, HttpServletRequest request) {
        // 操作日志
        orderLogService.insertSelective(null, backOrder.getOrderId(), request.getSession().getAttribute(CustomerConstantStr.NAME).toString(), "5");
        // 修改订单状态
        int count = backOrderService.modifyBackBeanCheck(backOrder);
        if (count > 0) {
            return new ModelAndView(new RedirectView(BACKORDERLIST_HTM));
        } else {
            return null;
        }
    }

    /**
     * 修改退单信息的审核状态
     * 
     * @param backOrder
     * @return
     */
    @RequestMapping(value = "modifyBackOrderisthird")
    public ModelAndView modifyBackOrderByCheckisthird(BackOrder backOrder, HttpServletRequest request) {
        // 操作日志
        orderLogService.insertSelective(null, backOrder.getOrderId(), request.getSession().getAttribute(CustomerConstantStr.NAME).toString(), "5");
        // 修改状态
        int count = backOrderService.modifyBackBeanCheck(backOrder);
        if (count > 0) {
            return new ModelAndView(new RedirectView("backorderlististhird.htm"));
        } else {
            return null;
        }
    }

    /**
     * 新后台退单详情
     * 
     * @param backId
     * @param orderId
     * @return
     */
    @RequestMapping("/newbackdetail")
    public ModelAndView newBackDetail(String backId, String orderId, String businessId) {
        // 获取退单日志信息
        List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(Long.valueOf(backId));
        // 查询退单金额
        // Order order = orderService.getPayOrder(Long.valueOf(orderId));
        // 查询物流
        BackOrderGeneral backOrderGeneral = backOrderService.queryBackOrderGeneral(Long.valueOf(backId));
        return new ModelAndView("jsp/order/backorderdetail")
        // 查询明细
                .addObject(BACKORDER, backOrderService.backdetail(Long.valueOf(backId), Long.valueOf(orderId)))
                // 查询日志
                .addObject(ORDERLOGS, orderLogService.selectOrderLogByOrderId(Long.valueOf(orderId))).addObject(ORDERID, orderId).addObject(BACKORDERLOGS, backOrderLogs)
                // .addObject("backPrice",order.getBackPrice())
                .addObject(BACKORDERGENERAL, backOrderGeneral).addObject("businessId", businessId);
    }

    /**
     * 新后台退款详情
     * 
     * @param backId
     * @param orderId
     * @return
     */
    @RequestMapping("/newbackprice")
    public ModelAndView newbackprice(String backId, String orderId, String businessId) {
        // 获取退单日志信息
        List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(Long.valueOf(backId));
        // 查询退单金额
        // Order order = orderService.getPayOrder(Long.valueOf(orderId));
        // 查询物流
        BackOrderGeneral backOrderGeneral = backOrderService.queryBackOrderGeneral(Long.valueOf(backId));
        return new ModelAndView("jsp/order/backorderprice")
        // 查询详细
                .addObject(BACKORDER, backOrderService.backdetail(Long.valueOf(backId), Long.valueOf(orderId)))
                // 查询日志
                .addObject(ORDERLOGS, orderLogService.selectOrderLogByOrderId(Long.valueOf(orderId))).addObject(ORDERID, orderId).addObject(BACKORDERLOGS, backOrderLogs)
                // .addObject("backPrice",order.getBackPrice())
                .addObject(BACKORDERGENERAL, backOrderGeneral).addObject("businessId",businessId);
    }

    /**
     * 退单详细信息
     * 
     * @param backOrderId
     * @return ModelAndView
     */
    @RequestMapping("/backorderdetail")
    public ModelAndView backOrderDetail(Long backOrderId, Long orderId) {
        // 获取退单日志信息
        List<BackOrderLog> backOrderLogs = backOrderLogService.queryByBackId(backOrderId);
        // 查询退单金额
        Order order = orderService.getPayOrder(orderId);
        // 查询物流
        BackOrderGeneral backOrderGeneral = backOrderService.queryBackOrderGeneral(backOrderId);
        return new ModelAndView(OrderConstants.NEWBACKORDERDETAIL)
                // 查询详细
                .addObject(BACKORDER, backOrderService.backdetail(backOrderId, orderId))
                // 查询日志
                .addObject(ORDERLOGS, orderLogService.selectOrderLogByOrderId(orderId)).addObject(ORDERID, orderId).addObject(BACKORDERLOGS, backOrderLogs)
                .addObject("backPrice", order.getBackPrice()).addObject(BACKORDERGENERAL, backOrderGeneral);
    }

    /**
     * 退单详细信息
     *
     * @param backOrderId
     * @return ModelAndView
     */
    @RequestMapping("/backorderdetailajax")
    @ResponseBody
    public BackOrder backOrderDetailajax(Long backOrderId) {
        return backOrderService.detail(backOrderId);
    }

    /**
     * 异步查询退单日志
     *
     * @param backOrderId
     * @return ModelAndView
     */
    @RequestMapping("/backorderlog")
    @ResponseBody
    public List<BackOrderLog> backorderlog(Long backOrderId) {
        return backOrderLogService.queryByBackId(backOrderId);
    }

    /**
     * 申请退单时上传图片
     * 
     * @param request
     * @param resp
     */
    @RequestMapping("back/upload")
    public void uploadBackImg(MultipartHttpServletRequest request, HttpServletResponse resp, Long orderId) throws IOException {
        PrintWriter out = resp.getWriter();
        String msg = null;
        MultipartFile file = request.getFile("uploadDocument");
        file.getOriginalFilename();
        // 检查文件大小
        if (file.getSize() > CustomerConstantStr.FOUR * CustomerConstantStr.NUM1024 * CustomerConstantStr.NUM1024) {
            msg = "101";
        } else if (!checkExtendsName(file.getOriginalFilename())) {
            msg = "102";
        } else {
            msg = UploadUtil.uploadFileByWidth(request.getFile("uploadDocument"), request).get("oldimg") + "," + orderId;
        }
        out.append("<script>parent.callback('" + msg + "');</script>");
    }

    /**
     * 记录退款申请信息
     * 
     * @param backOrder
     * @return
     */
    @RequestMapping("back/keepBackOrderInfoprice")
    public ModelAndView keepBackOrderInfoprice(HttpServletRequest request, BackOrder backOrder) {


        // 判断该订单是否存在退单记录 如果存在 则直接返回我的订单列表
        if (backOrderService.isBackOrderRecordExist(backOrder.getOrderCode())) {
            LOGGER.error("orderCode :" + backOrder.getOrderCode() + "alerdy exist BackOrderRecord");
            return new ModelAndView(new RedirectView("../customer/myorder.html"));
        }

        // 记录退单信息到退单表
        backOrder.setBusinessId(orderService.getPayOrder(backOrder.getOrderId()).getBusinessId());
        backOrder.setBackOrderCode(this.createBackOrderNo());
        backOrder.setBackCheck("6");
        backOrder.setBackTime(new Date());
        int count = backOrderService.insertBackOrderInfo(backOrder);
        // 记录订单商品退货信息
        // 更新订单状态为15("已提交退款审核")
        orderService.updateStatusBackById(backOrder.getOrderId(), "15", null);
        // 记录退单操作日志
        if (count > 0) {
            BackOrderLog backOrderLog = new BackOrderLog();
            backOrderLog.setBackLogPerson("customer");
            backOrderLog.setBackLogTime(new Date());
            backOrderLog.setBackLogStatus("1");
            backOrderLog.setBackOrderId(backOrder.getBackOrderId());
            backOrderLogService.insert(backOrderLog);
        }
        return new ModelAndView(new RedirectView("../customer/myorder.html"));
    }

    /**
     * 记录退货申请信息
     * 
     * @param backOrder
     * @return
     */
    @RequestMapping("back/keepBackOrderInfo")
    public ModelAndView keepBackOrderInfo(HttpServletRequest request, BackOrder backOrder) {
        //根据订单编号去查询是否有退单记录
        BackOrder isBackOrder = backOrderService.isBackOrder(backOrder.getOrderCode());
        // 记录退单信息到退单表
        backOrder.setBusinessId(orderService.getPayOrder(backOrder.getOrderId()).getBusinessId());
        //设置退单状态
        backOrder.setBackCheck("0");
        //记录退货时间
        backOrder.setBackTime(new Date());

        //获取订单信息
        Order order = orderService.getPayOrder(backOrder.getOrderId());
        if ("".equals(backOrder.getBackGoodsIdAndSum())) {
            //计算去除运费的金额
            order.setBackPrice(order.getOrderPrice().subtract(order.getExpressPrice()));
            //获取订单商品
            List<OrderGoods> og = orderService.queryOrderGoods(order.getOrderId());
            StringBuilder goodsInfo = new StringBuilder();
            //拼接商品信息
            for (int i = 0; i < og.size(); i++) {
                if (i > 0) {
                    goodsInfo.append("-");
                }
                //记录订单商品id以及数量
                goodsInfo.append(og.get(i).getGoodsInfoId() + "," + og.get(i).getGoodsInfoNum() + "," + og.get(i).getOrderGoodsId());
            }
            //设置退货的商品id和数量
            backOrder.setBackGoodsIdAndSum(goodsInfo.toString());
        }
        /**
         * 如果该订单下有退单记录就去修改该条记录
         * 否则就新增一条退货记录
         *
         * 此方法代码跟原有代码有所差别，在此说明一下之所以这么改的原因
         * 订单首次售中申请退款，后台拒绝退款走发货流程
         * 用户申请退款失败以后确认收货，然后申请退货
         * 申请退货时退单列表会有一条记录，原有的是不允许用户申请，直接返回订单页面
         * 现改成允许用户申请退货，在此查看到如果退单列表有记录时就修改该条记录，没有就新增一条退货申请记录
         * houyichang 2015/12/28
         *
         * */
        int count = 0;
        if (null != isBackOrder) {
            //修改该订单状态为申请退货
            backOrder.setIsBack("1");
            backOrder.setBackOrderId(isBackOrder.getBackOrderId());
            backOrder.setBackOrderCode(isBackOrder.getBackOrderCode());
            count = this.backOrderService.modifyBackBeanCheck(backOrder);
        }else {
            backOrder.setBackOrderCode(this.createBackOrderNo());
            count = backOrderService.insertBackOrderInfo(backOrder);
        }

        // 更新订单状态为14("已提交退货审核")
        orderService.updateStatusBackById(backOrder.getOrderId(), "14", null);
        if(count > 0) {
            // 记录退单操作日志
            BackOrderLog backOrderLog = new BackOrderLog();
            backOrderLog.setBackLogPerson("customer");
            backOrderLog.setBackLogTime(new Date());
            backOrderLog.setBackLogStatus("1");
            backOrderLog.setBackOrderId(backOrder.getBackOrderId());
            backOrderLogService.insert(backOrderLog);
        }
        return new ModelAndView(new RedirectView("../customer/myorder.html"));
    }

    /**
     * 退单操作控制器
     * 
     * @return
     */
    @RequestMapping("savebackorderdetail")
    public ModelAndView saveBackOrderDetail(HttpServletRequest request, HttpServletResponse response, BackOrderLog backOrderLog, Long orderId, BigDecimal backPrice) {
        backOrderLog.setBackLogPerson(ADMIN);
        backOrderLog.setBackLogTime(new Date());
        backOrderLogService.insert(backOrderLog);
        Order order = orderService.getPayOrder(orderId);
        BackOrder backOrder = backOrderService.queryBackOrderByOrderCode(order.getOrderCode());
        if (Long.valueOf(backOrderLog.getBackLogStatus()) == 2) {
            if (Long.valueOf(backOrder.getIsBack()) == 1) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 8 同意退货
                orderService.updateStatusBackById(orderId, "8", backPrice);
                // 改变退单列表状态为 9 待客户填写物流地址
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "9");
            }
            if (Long.valueOf(backOrder.getIsBack()) == 2) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 12 同意退款
                orderService.updateStatusBackById(orderId, "12", backPrice);
                // 改变退单列表状态为 3 确认收货
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
            }
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 3) {
            // 如果退单操作日志为 admin 审核不通过，则改变订单状态为 9 拒绝退货
            orderService.updateStatusBackById(orderId, "9", backPrice);
            // 改变退单列表状态为 2 拒绝退货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "2");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 5) {
            // 如果退单操作日志为 admin 确认收货,则改变订单状态为 10 确认收货
            orderService.updateStatusBackById(orderId, "10", backPrice);
            // 改变退单列表状态为 3 确认收货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 6) {
            // 如果退单操作日志为 admin 收货失败，则改变订单状态为 16 收货失败
            orderService.updateStatusBackById(orderId, "16", backPrice);
            // 改变退单列表状态为 8 收货失败
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "8");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 7) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 17 已退款 并记录退款金额
            orderService.updateStatusBackById(orderId, "17", backPrice);
            // 改变退单列表状态为 4 退单结束
            int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "4");
            // 退单结束后把成功消费的积分奖励扣除掉,增加取消订单的积分记录,以及消费记录
            if (cont == 1) {
                // 退款成功后扣积分
                backOrderService.reducePointOrderBack(orderId);
                // 退款成功后退回库存
                backOrderService.addStockOrderBack(orderId, backOrderLog.getBackOrderId());
            }
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append("<script>parent.call_back();</script>");
        } catch (IOException e) {
            LOGGER.error("新增一条退货操作日志",e);
        }
        return null;
    }

    /**
     * 新后台保存退款详情信息
     * 
     * @param request
     * @param response
     * @param backOrderLog
     * @param orderId
     * @param backPrice
     * @return
     */
    @RequestMapping("/newsavebackorderprice")
    public ModelAndView newsaveBackOrderprice(HttpServletRequest request, HttpServletResponse response, BackOrderLog backOrderLog, Long orderId, BigDecimal backPrice) {
        backOrderLog.setBackLogPerson(ADMIN);
        backOrderLog.setBackLogTime(new Date());
        backOrderLogService.insert(backOrderLog);
        Order order = orderService.getPayOrder(orderId);
        BackOrder backOrder = backOrderService.queryBackOrderByOrderCode(order.getOrderCode());
        if (Long.valueOf(backOrderLog.getBackLogStatus()) == 2) {
            if (Long.valueOf(backOrder.getIsBack()) == 1) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 8 同意退货
                orderService.updateStatusBackById(orderId, "8", backPrice);
                // 改变退单列表状态为 9 待客户填写物流地址
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "9");
            }
            if (Long.valueOf(backOrder.getIsBack()) == 2) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 12 同意退款
                orderService.updateStatusBackById(orderId, "12", backPrice);
                // 改变退单列表状态为 3 确认收货
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
            }
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 3) {
            // 如果退单操作日志为 admin 审核不通过，则改变订单状态为 9 拒绝退货
            orderService.updateStatusBackById(orderId, "9", backPrice);
            // 改变退单列表状态为 2 拒绝退货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "2");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 5) {
            // 如果退单操作日志为 admin 确认收货,则改变订单状态为 10 确认收货
            orderService.updateStatusBackById(orderId, "10", backPrice);
            // 改变退单列表状态为 3 确认收货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 6) {
            // 如果退单操作日志为 admin 收货失败，则改变订单状态为 16 收货失败
            orderService.updateStatusBackById(orderId, "16", backPrice);
            // 改变退单列表状态为 8 收货失败
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "8");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 7 || Long.valueOf(backOrderLog.getBackLogStatus()) == 8) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 17 已退款 并记录退款金额
            orderService.updateStatusBackById(orderId, "17", backPrice);
            // 改变退单列表状态为 4 退单结束
            int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "4");
            // 退单结束后把成功消费的积分奖励扣除掉,增加取消订单的积分记录,以及消费记录
            if (cont == 1) {
                backOrderService.reducePointOrderBack(orderId);
            }
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 9) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 13 拒绝退款
            orderService.updateStatusBackById(orderId, "13", backPrice);
            // 改变退单列表状态为 4 退单结束
            int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "7");
            if (cont == 1) {
                backOrderService.reducePointOrderBack(orderId);
            }
        }
        return new ModelAndView(new RedirectView(BACKORDERLIST_HTM));
    }

    /**
     * 新后台保存退货详情信息
     * 
     * @param request
     * @param response
     * @param backOrderLog
     * @param orderId
     * @param backPrice
     * @return
     */
    @RequestMapping("/newsavebackorderdetail")
    public ModelAndView newsaveBackOrderDetail(HttpServletRequest request, HttpServletResponse response, BackOrderLog backOrderLog, Long orderId, BigDecimal backPrice) throws IOException {
        PrintWriter out = null;
        BigDecimal backPriceNew = backPrice;
        backOrderLog.setBackLogPerson(ADMIN);
        backOrderLog.setBackLogTime(new Date());
        backOrderLogService.insert(backOrderLog);
        Order order = orderService.getPayOrder(orderId);
        if(backPriceNew==null){
            backPriceNew=order.getOrderPrice();
        }
        BackOrder backOrder = backOrderService.queryBackOrderByOrderCode(order.getOrderCode());

        /**
         * BackLogStatus等于8 是确认退款
         * BackLogStatus等于7 是确认退货
         * BackLogStatus等于9 是拒绝退款
         * houyichang测试发现下面注释有问题在此补上，其他状态暂时还未测到，后续有谁测试到了其他状态请补充上
         *
         * **/


        if (Long.valueOf(backOrderLog.getBackLogStatus()) == 2) {
            if (Long.valueOf(backOrder.getIsBack()) == 1) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 8 同意退货
                orderService.updateStatusBackById(orderId, "8", backPriceNew);
                // 改变退单列表状态为 9 待客户填写物流地址
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "9");
            }
            if (Long.valueOf(backOrder.getIsBack()) == 2) {
                // 如果退单操作日志为 admin 审核通过，则改变订单状态为 12 同意退款
                orderService.updateStatusBackById(orderId, "12", backPriceNew);
                // 改变退单列表状态为 3 确认收货
                backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
            }
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 3) {
            // 如果退单操作日志为 admin 审核不通过，则改变订单状态为 9 拒绝退货
            orderService.updateStatusBackById(orderId, "9", backPriceNew);
            // 改变退单列表状态为 2 拒绝退货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "2");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 5) {
            // 如果退单操作日志为 admin 确认收货,则改变订单状态为 10 确认收货
            orderService.updateStatusBackById(orderId, "10", backPriceNew);
            // 改变退单列表状态为 3 确认收货
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "3");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 6) {
            // 如果退单操作日志为 admin 收货失败，则改变订单状态为 16 收货失败
            orderService.updateStatusBackById(orderId, "16", backPriceNew);
            // 改变退单列表状态为 8 收货失败
            backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "8");
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 7) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 17 已退款 并记录退款金额
            orderService.updateStatusBackById(orderId, "17", backPriceNew);
            BackOrder bo = new BackOrder();
            bo.setBackOrderId(backOrder.getBackOrderId());
            bo.setBackPrice(backPriceNew);
            //修改订单退款金额时修改退单表金额
            backOrderService.updateBackOrderReducePrice(bo);
            // 改变退单列表状态为 4 退单结束
            //int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "4");
            int cont = backOrderService.modifyBackOrderByCheckNew(backOrderLog.getBackOrderId(), "4");
            // 退单结束后把成功消费的积分奖励扣除掉,增加取消订单的积分记录,以及消费记录
            if (cont == 1) {
                backOrderService.reducePointOrderBack(orderId);
                //根据订单id查询到优惠劵劵码，修改优惠劵  返还库存
                orderCouponService.modifyCouponStatus(orderId);
            }
            //支付类型是预存款支付，退款或退单
            updateDepositInfo(order, backPrice, backOrder, request);
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 8) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 18 退款成功
            orderService.updateStatusBackById(orderId, "18", backPriceNew);
            // 改变退单列表状态为 10 退单成功
//            int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "10");
            int cont = backOrderService.modifyBackOrderByCheckNew(backOrderLog.getBackOrderId(), "10");
            // 退单结束后增加消费记录
            if (cont == 1) {
                backOrderService.reducePointOrderBackNew(orderId);
                // 根据订单id查询到优惠劵劵码，修改优惠劵  返还库存
                orderCouponService.modifyCouponStatus(orderId);
            }
            //支付类型是预存款支付，退款或退单
            updateDepositInfo(order, backPrice, backOrder, request);
        } else if (Long.valueOf(backOrderLog.getBackLogStatus()) == 9) {
            // 如果退单操作日志为 admin 退款，则改变订单状态为 13 拒绝退款
            orderService.updateStatusBackById(orderId, "13", backPriceNew);
            // 改变退单列表状态为 7 拒绝退款
//            int cont = backOrderService.modifyBackOrderByCheck(backOrderLog.getBackOrderId(), "7");
            int cont = backOrderService.modifyBackOrderByCheckNew(backOrderLog.getBackOrderId(), "7");
            if (cont == 1) {
                //houyichang修改  原因：拒绝退款时不该调用此方法扣除积分
//                backOrderService.reducePointOrderBack(orderId);
            }
        }
        out = response.getWriter();
        out.append("<script>parent.location.reload();</script>");
        out.flush();
        out.close();
//        return new ModelAndView(new RedirectView(BACKORDERLIST_HTM));
        return null;
    }


    /**
     * 判断积分是否够扣除
     * @param orderId 订单id
     * @return 如果够扣除返回0  不够返回1
     */
    @RequestMapping("/ispointenough")
    @ResponseBody
    public int isPointEnough(String orderId) {
        try {
            return backOrderService.isPointEnough(orderId);
        } catch (Exception e) {
            LOGGER.error("ispointenough error", e);
        }

        return 0;
    }


    // 订单状态 0未付款 1已付款未发货 2已发货 3已经收货 4作废 7:申请退货 8：同意退货 9:拒绝退货 10:确认收货 11:订单结束
    // 12:同意退款13： 拒绝退款 14:已提交退货审核 15：已提交退款审核 16: 收货失败 17:已退款

    /**
     * 检查文件扩展名是否为图片
     *
     * @param fileName
     *            上传的文件的文件名
     * @return
     */
    private boolean checkExtendsName(String fileName) {
        if (fileName.indexOf(".") < 0) {
            return false;
        }
        String extend = fileName.substring(fileName.lastIndexOf(".") + 1);
        String[] extendNames = { "jpg", "jpeg", "bmp", "png", "gif" };
        for (String extendName : extendNames) {
            if (extend.equals(extendName)) {
                return true;
            }
        }
        return false;
    }

    /***
     * 生成退单单号 系统时间+随机六位数字
     * 
     * @return
     */
    public String createBackOrderNo() {
        String d = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        Random r = new Random();
        Double d1 = r.nextDouble();
        String s = d1 + "";
        s = d + s.substring(3, 3 + 6);
        return s;
    }

    public BackOrderLogService getBackOrderLogService() {
        return backOrderLogService;
    }

    @Resource(name = "BackOrderLogService")
    public void setBackOrderLogService(BackOrderLogService backOrderLogService) {
        this.backOrderLogService = backOrderLogService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    @Resource(name = "OrderService")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public BackOrderService getBackOrderService() {
        return backOrderService;
    }

    @Resource(name = "BackOrderService")
    public void setBackOrderService(BackOrderService backOrderService) {
        this.backOrderService = backOrderService;
    }

    public OrderLogService getOrderLogService() {
        return orderLogService;
    }

    @Resource(name = "OrderLogService")
    public void setOrderLogService(OrderLogService orderLogService) {
        this.orderLogService = orderLogService;
    }

    /**
     * @author chenp
     * 退款金额验证
     * @param backOrderId
     *          退款单ID
     * @return  Object
     *          退款单对象
     */
    @RequestMapping("/checkbackprice")
    @ResponseBody
    public Object checkBackPrice(Long backOrderId){
        //根据退单ID获取退单对象
        return backOrderService.selectBackOrderByBackOrderId(backOrderId);
    }

    /**
     * 退单或退款情况下：
     * 修改会员预存款信息并插入一条交易记录
     * @param order
     * @param backPrice
     * @param backOrder
     * @param request
     */
    public void updateDepositInfo(Order order,BigDecimal backPrice,BackOrder backOrder,HttpServletRequest request){
        if (order.getPayId().intValue() == 5) {
            //用户ID
            Long customerId = order.getCustomerId();

            //查询该客户的预存款
            DepositInfo depositInfo = depositInfoService.selectDepositByCustId(customerId);
            //退单后的预存款
            BigDecimal preDepositPrice = depositInfo.getPreDeposit();
            if(backPrice == null || backPrice.intValue() == 0){
                if(preDepositPrice == null || preDepositPrice.intValue() == 0){
                    preDepositPrice = order.getOrderPrice();
                }else{
                    preDepositPrice = preDepositPrice.add(order.getOrderPrice());
                }
            }else {
                if(preDepositPrice == null || preDepositPrice.intValue() == 0){
                    preDepositPrice = backPrice;
                }else{
                    preDepositPrice = preDepositPrice.add(backPrice);
                }
            }
            //修改该客户的预存款
            depositInfo.setPreDeposit(preDepositPrice);
            if (depositInfo.getFreezePreDeposit() != null && (depositInfo.getFreezePreDeposit()).intValue() != 0){
                preDepositPrice = preDepositPrice.add(depositInfo.getFreezePreDeposit());
            }
            //插入一条交易记录
            TradeInfo tradeInfo = getTradeInfo(customerId,backOrder,preDepositPrice,backPrice,order);
            tradeInfoService.insertTradeInfo(tradeInfo);
            depositInfoService.updateDeposit(depositInfo);
        }
    }

    /**
     * 构建交易记录信息
     * @param customerId
     * @param backOrder
     * @param currentPrice
     * @param order
     * @return
     */
    public TradeInfo getTradeInfo(Long customerId,BackOrder backOrder,BigDecimal currentPrice,BigDecimal backPrice,Order order){
        TradeInfo tradeInfo  = new TradeInfo();
        tradeInfo.setCustomerId(customerId);
        tradeInfo.setOrderCode(backOrder.getOrderCode());
        //交易类型 0在线充值 1订单退款 2线下提现 3订单消费
        tradeInfo.setOrderType("1");
        //提现状态 0【提现】待审核 1【提现】已打回 2【提现】已通过待打款 3【提现】已打款待确认 4【提现】已完成 5未支付 6充值成功 8已取消
        tradeInfo.setOrderStatus("4");
        //当前预存款
        tradeInfo.setCurrentPrice(currentPrice);
        BigDecimal orderPrice ;
        if(backPrice == null || backPrice.intValue() == 0){
            orderPrice = backOrder.getBackPrice();
        }else{
            orderPrice = backPrice;
        }
        tradeInfo.setOrderPrice(orderPrice);
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
