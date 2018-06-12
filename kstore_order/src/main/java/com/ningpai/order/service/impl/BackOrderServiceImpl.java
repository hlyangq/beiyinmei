/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.order.service.impl;

import com.ningpai.common.util.DateUtil;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerConsume;
import com.ningpai.customer.bean.CustomerInfo;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerConsumeMapper;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.customer.dao.IntegralSetMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.customer.service.PointLevelServiceMapper;
import com.ningpai.gift.service.GiftService;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.dao.GoodsProductMapper;
import com.ningpai.goods.service.GoodsProductService;
import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.*;
import com.ningpai.order.dao.*;
import com.ningpai.order.service.BackOrderService;
import com.ningpai.other.bean.IntegralSet;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.PointSetService;
import com.ningpai.util.MapUtil;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 退单信息列表
 *
 * @author ggn
 *
 */
@Service("BackOrderService")
public class BackOrderServiceImpl implements BackOrderService {

    // 静态变量
    private static final Logger LOGGER = Logger.getLogger(BackOrderServiceImpl.class);
    // spring注解
    private BackOrderMapper backOrderMapper;
    // 货品信息Mapper
    private GoodsProductMapper goodsProductMapper;
    // spring注解
    private CustomerPointMapper customerPointMapper;
    // spring注解
    private IntegralSetMapper integralSetmapper;
    // spring注解
    private CustomerInfoMapper customerInfoMapper;
    // 消费记录
    private CustomerConsumeMapper customerConsumeMapper;

    private GoodsProductService goodsProductService;

    private OrderMapper orderMapper;

    private OrderGoodsMapper orderGoodsMapper;

    private OrderMarketingMapper orderMarketingMapper;

    private MarketingService marketingService;

    private OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper;

    private CouponService couponService;

    private OrderGoodsInfoGiftMapper orderGoodsInfoGiftMapper;

    private GiftService giftService;

    private OrderCouponMapper orderCouponMapper;

    private OrderGiftMapper orderGiftMapper;

    private OrderExpressMapper orderExpressMapper;

    private PointLevelServiceMapper pointLevelServiceMapper;
    //查看积分设置是否开启
    @Resource(name = "pointSetService")
    private PointSetService pointSetService;

    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;
    
    /**
     * 退单信息列表
     *
     * @param pageBean
     * @param bkOrder
     * @param startTime
     * @param endTime
     * @return PageBean
     */
    public PageBean backOrderList(PageBean pageBean, BackOrder bkOrder, String startTime, String endTime) {
        bkOrder.setBackDelFlag("0");
        // 分装实体类属性
        Map<String, Object> paramMap = MapUtil.getParamsMap(bkOrder);
        if (startTime != null && !"".equals(startTime)) {
            bkOrder.setBackTime(DateUtil.stringToDate(startTime, null));
            paramMap.put("startTime", startTime);
        }
        if (endTime != null && !"".equals(endTime)) {
            paramMap.put("endTime", endTime);
        }
        if(bkOrder.getBusinessId()!=null&&bkOrder.getBusinessId().intValue()==0){
            // 查询平台退单总数
            pageBean.setRows(backOrderMapper.searchBackOrderCountnew(paramMap));
        }else{
            // 查询第三方退单总数
            pageBean.setRows(backOrderMapper.searchBackOrderThirdCount(paramMap));
        }
        // 计算分页
        Integer no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
        no = no == 0 ? 1 : no;
        if (pageBean.getPageNo() >= no) {
            pageBean.setPageNo(no);
            pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
        }
        pageBean.setObjectBean(bkOrder);
        // 查询条件封装
        paramMap.put("start", pageBean.getStartRowNum());
        paramMap.put("number", pageBean.getEndRowNum());
        try {
            List<Object> backorders;
            if(bkOrder.getBusinessId()!=null&&bkOrder.getBusinessId().intValue()==0){
                // 获取数据库的平台退单集合
                backorders = backOrderMapper.searchBackOrderListnew(paramMap);
            }else{
                // 获取数据库的第三方退单集合
                backorders = backOrderMapper.searchBackOrderLisThird(paramMap);
            }
            if (null != backorders && !backorders.isEmpty()) {
                for (int i = 0; i < backorders.size(); i++) {
                    // 获取单个的退单对象
                    BackOrder backOrders = (BackOrder) backorders.get(i);
                    if (!"".equals(backOrders.getBackGoodsIdAndSum())) {
                        // 获取退单对象 下面的退单的 商品Id
                        String[] strs = backOrders.getBackGoodsIdAndSum().split("-");
                        // 遍历退单对象下面的商品Id
                        for (int j = 0; j < strs.length; j++) {
                            String strss = strs[j];
                            // 获取第J个商品的Id
                            Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                            // 根据ID获取单个商品的详细信息
                            GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                            // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                            String counts ="";
                            if (strss.lastIndexOf(",") == strss.indexOf(",")){
                                //老数据
                                counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                            }else {
                                counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                            }
                            // 装载商品数量数据
                            orderProductVo.setGoodsCount(Long.valueOf(counts));
                            // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                            BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                            BigDecimal count = new BigDecimal(counts);
                            // 计算总价格
                            BigDecimal sum = price.multiply(count);
                            // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                            orderProductVo.setGoodsInfoPreferPrice(sum);
                            // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                            backOrders.getOrderGoodslistVo().add(orderProductVo);
                        }
                    }
                }
            }
            pageBean.setList(backorders);
        } finally {
            paramMap = null;
        }
        return pageBean;
    }

    /**
     * 退货的时候  获得减去的积分
     *
     * @param getPoint   订单完成赠送的积分
     * @param customerId 用户id
     * @return 返回退货的时候  获得减去的积分
     */
    private int getReducePoint(int getPoint, Long customerId) {
        // 获得用户的总积分
        int allPoint = getCustomerAllPoint(customerId);

        //  如果用户当前的总积分 减去这个订单的赠送积分小于0  则返回用户的总积分 否则返回订单的赠送积分
        if (allPoint - getPoint < 0) {
            return allPoint;
        }

        return getPoint;
    }

    /**
     * 获得用户当前的总积分
     *
     * @param customerId 用户id
     * @return 获得用户当前的总积分
     */
    private int getCustomerAllPoint(Long customerId) {
        return customerPointServiceMapper.getCustomerAllPoint(customerId + "")
                - customerPointServiceMapper.getCustomerReducePoint(customerId + "");
    }



    /**
     * 当用户退单,后台同意后将把订单完成的积分奖励扣除掉 会员积分列表将增加一条扣除积分信息 会员消费列表将增加一条订单退掉的信息.
     *
     * @param orderId
     * @return int
     */
    @Override
    public int reducePointOrderBack(Long orderId) {
        Order order = orderMapper.orderDetail(orderId);
        BackOrder backOrder = backOrderMapper.selectBackOrder(order.getOrderCode());
        BigDecimal rate = backOrder.getBackPrice().divide(order.getOrderPrice(),0);
        Long customerId = order.getCustomerId();
        BigDecimal orderPrice = order.getOrderPrice();
        //查找积分设置信息 ,为了查询积分开关
        PointSet pointSet = this.pointSetService.findPointSet();

        int reduce =0;
        CustomerInfo info = customerInfoMapper.selectCustInfoById(customerId);

        int allpoint = info.getInfoPointSum();

        //第三方商家退货没有积分返还,后台积分开关关闭时不返还
        if(order.getBusinessId()==0 && "1".equals(pointSet.getIsOpen())){
            if(order.getOrderIntegral() != null && order.getOrderIntegral().intValue() != 0 ){
                int point = (BigDecimal.valueOf(order.getOrderIntegral())).multiply(rate).intValue();
                allpoint += point;
                CustomerPoint cusPoint = new CustomerPoint();
                cusPoint.setPointDetail("退单返还使用积分");
                cusPoint.setPoint(point);
                cusPoint.setPointType("1");
                cusPoint.setDelFlag("0");
                cusPoint.setCreateTime(new Date());
                cusPoint.setCustomerId(order.getCustomerId());
                // 扣除后的积分保存到数据库
                customerPointMapper.insertSelective(cusPoint);
            }

            //扣除订单消费系统赠送的积分
            if(null != order.getOrderGetPoint() && order.getOrderGetPoint() > 0 ){
                reduce = order.getOrderGetPoint().intValue();
                CustomerPoint cusPoint = new CustomerPoint();
                cusPoint.setPointDetail("扣除订单消费系统赠送积分");
                cusPoint.setPoint(getReducePoint(order.getOrderGetPoint().intValue(), customerId));
                cusPoint.setPointType("0");
                cusPoint.setDelFlag("0");
                cusPoint.setCreateTime(new Date());
                cusPoint.setCustomerId(order.getCustomerId());
                // 扣除后的积分保存到数据库
                customerPointMapper.insertSelective(cusPoint);
            }
//            CustomerInfo info = customerInfoMapper.selectCustInfoById(customerId);
            // 当前会员总积分
//            int allpoint = info.getInfoPointSum();
            allpoint = allpoint - reduce < 0 ? 0 : allpoint - reduce;
            // 扣除后的积分重新判断会员级别
            for (CustomerPointLevel level : pointLevelServiceMapper.selectAllPointLevel()) {
                String[] points = level.getPointNeed().split("~");
                if (Integer.valueOf(points[0]) <= allpoint && allpoint <= Integer.valueOf(points[1])) {
                    info.setPointLevelName(level.getPointLevelName());
                }
            }
            if(null != order.getOrderIntegral()) {
                allpoint += order.getOrderIntegral().intValue();
            }
            info.setInfoPointSum(allpoint);
            info.setCustomerId(order.getCustomerId());
            customerInfoMapper.updateInfoByCustId(info);
        }

        // 添加取消订单后的消费记录
        CustomerConsume cc;
        try {
            cc = new CustomerConsume();
            // 从订单中取出付款方式1.在线支付2.货到付款
            if (order.getPayId() == 1) {
                cc.setPayType("1");
            }
            if (order.getPayId() == 2) {
                cc.setPayType("2");
            }
            cc.setCustomerId(customerId);
            cc.setBalanceNum(orderPrice);
            cc.setBalanceRemark("退单完成消费减少");
            cc.setBalanceType("3");
            cc.setCreateTime(new Date());
            cc.setDelFlag("0");
            cc.setOrderNo(order.getOrderCode());
            customerConsumeMapper.insertSelective(cc);
        } finally {
            cc = null;
        }
        return 0;
    }

    /**
     * 售中退款返还积分
     *
     * @param orderId
     * @return
     */
    @Override
    public int reducePointOrderBackNew(Long orderId) {
        Order order = orderMapper.orderDetail(orderId);
        BackOrder backOrder = backOrderMapper.selectBackOrder(order.getOrderCode());
        BigDecimal rate = backOrder.getBackPrice().divide(order.getOrderPrice(),0);
        Long customerId = order.getCustomerId();
        BigDecimal orderPrice = order.getOrderPrice();
        //第三方商家退货没有积分返还
        if(order.getBusinessId()==0){
            CustomerInfo info = customerInfoMapper.selectCustInfoById(customerId);
            // 当前会员总积分
            int allpoint = info.getInfoPointSum();
            //回退订单使用积分
            if(null != order.getOrderIntegral() && order.getOrderIntegral().intValue() != 0) {
                int point = (BigDecimal.valueOf(order.getOrderIntegral())).multiply(rate).intValue();
                allpoint += point;
                CustomerPoint cusPoint = new CustomerPoint();
                cusPoint.setPointDetail("退单返还使用积分");
                cusPoint.setPoint(point);
                cusPoint.setPointType("1");
                cusPoint.setDelFlag("0");
                cusPoint.setCreateTime(new Date());
                cusPoint.setCustomerId(order.getCustomerId());
                // 扣除后的积分保存到数据库
                customerPointMapper.insertSelective(cusPoint);
            }
            // 扣除后的积分重新判断会员级别
            for (CustomerPointLevel level : pointLevelServiceMapper.selectAllPointLevel()) {
                String[] points = level.getPointNeed().split("~");
                if (Integer.valueOf(points[0]) <= allpoint && allpoint <= Integer.valueOf(points[1])) {
                    info.setPointLevelName(level.getPointLevelName());
                }
            }
            info.setInfoPointSum(allpoint);
            info.setCustomerId(order.getCustomerId());
            customerInfoMapper.updateInfoByCustId(info);
        }

        // 添加取消订单后的消费记录
//        CustomerConsume cc;
//        try {
//            cc = new CustomerConsume();
//            // 从订单中取出付款方式1.在线支付2.货到付款
//            if (order.getPayId() == 1) {
//                cc.setPayType("1");
//            }
//            if (order.getPayId() == 2) {
//                cc.setPayType("2");
//            }
//            cc.setCustomerId(customerId);
//            cc.setBalanceNum(orderPrice);
//            cc.setBalanceRemark("退单完成消费减少");
//            cc.setBalanceType("3");
//            cc.setCreateTime(new Date());
//            cc.setDelFlag("0");
//            cc.setOrderNo(order.getOrderCode());
//            customerConsumeMapper.insertSelective(cc);
//        } finally {
//            cc = null;
//        }
        return 0;
    }

    /**
     * 从订单里把退货成功的信息货品删除
     *
     * @param backId
     *            退单Id
     * @return int
     */
    public int deleteBackGoods(Long backId) {
        // 保存更新后的状态
        int result = 0;
        // 获取单个的退单对象
        BackOrder backOrders = backOrderMapper.selectbackOrderOne(backId);
        // 保存退单的总金额
        BigDecimal backOrderSumPrice = new BigDecimal("0.00");
        // 根据获取当前退单的订单信息
        Order orders = backOrderMapper.selectOrderOne(backOrders.getOrderCode());
        // 如果没有指定要退款的金额 就把商品的总金额加进去
        if (null == orders.getBackPrice() && !"".equals(backOrders.getBackGoodsIdAndSum())) {
                // 获取退单对象 下面的退单的 商品Id
                String[] strs = backOrders.getBackGoodsIdAndSum().split("-");
                // 遍历退单对象下面的商品Id
                for (int j = 0; j < strs.length; j++) {
                    String strss = strs[j];
                    // 获取第J个商品的Id
                    Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                    // 根据ID获取单个商品的详细信息
                    GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                    // 退货商品的价格
                    backOrderSumPrice = backOrderSumPrice.add(orderProductVo.getGoodsInfoPreferPrice());
                    // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                    String counts ="";
                    if (strss.lastIndexOf(",") == strss.indexOf(",")){
                        //老数据
                        counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                    }else {
                        counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                    }
                    // 装载商品数量数据
                    orderProductVo.setGoodsCount(Long.valueOf(counts));
                    // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                    BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                    BigDecimal count = new BigDecimal(counts);
                    // 计算总价格
                    BigDecimal sum = price.multiply(count);
                    // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                    orderProductVo.setGoodsInfoPreferPrice(sum);
                    // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                    backOrders.getOrderGoodslistVo().add(orderProductVo);
                }

                orders.setBackPrice(backOrderSumPrice);
                result = backOrderMapper.updateOrder(orders);
        }
        return result;
    }

    /**
     * 平台同意退单了 就把退单的商品状态更改为支持退单
     *
     * @param backId
     *            退单Id zhanghl
     * @return 修改np_order_goods 表中的商品为退货商品
     */
    public int setBackGoodsStatus(Long backId) {
        // 记录返回的结果
        int result = 0;
        // 获取单个的退单对象
        BackOrder backOrders = backOrderMapper.selectbackOrderOne(backId);
        // 根据获取当前退单的订单信息
        Order orders = backOrderMapper.selectOrderOne(backOrders.getOrderCode());
        if (!"".equals(backOrders.getBackGoodsIdAndSum())) {
            // 获取退单对象 下面的退单的 商品Id
            String[] strs = backOrders.getBackGoodsIdAndSum().split("-");
            // 遍历退单对象下面的商品Id
            for (int j = 0; j < strs.length; j++) {
                String strss = strs[j];
                // 获取第J个商品的Id
                Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                String counts ="";
                //订单商品id
                Long orderGoodsId = null;
                if (strss.lastIndexOf(",") == strss.indexOf(",")){
                    //老数据
                    counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                }else {
                    counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                    orderGoodsId = Long.valueOf(strss.substring(strss.lastIndexOf(",") +1));
                }

                Map<String, Object> param = new HashMap<String, Object>();
                param.put("orderId", orders.getOrderId());
                param.put("backOrderCode", backOrders.getBackOrderCode());
                param.put("goodsInfoId", goodsId);
                //老数据
                if (orderGoodsId == null){
                    result = orderGoodsMapper.updateOrderGoodsBack(param);
                }else {
                    param.put("orderGoodsId", orderGoodsId);
                    result = orderGoodsMapper.updateOrderGoodsBackNew(param);
                }
            }
        }
        return result;
    }

    /**
     * 修改退单审核状态
     *
     * @param backId
     * @param backCheck
     * @return int
     */
    public int modifyBackOrderByCheck(Long backId, String backCheck) {
        // 如果是退单的确认收货
        int count = 0;
        BackOrder backOrder;

        if ("4".equals(backCheck)) {
            // 设置退货的价格
            this.deleteBackGoods(backId);
            // 删除订单中 退货成功的商品
            this.setBackGoodsStatus(backId);
        }
        if (backId != null) {
            backOrder = new BackOrder();
            backOrder.setBackOrderId(backId);
            backOrder.setBackCheck(backCheck);
            count = backOrderMapper.updateByPrimaryKeySelective(backOrder);
        }

        return count;
    }

    /**
     * 修改退单信息
     *
     * @param backOrder
     * @return int
     */
    public int modifyBackBeanCheck(BackOrder backOrder) {
        // 保存更新数据返回的状态
        int result = 0;
        if (null != backOrder) {
            if ("1".equals(backOrder.getBackDelFlag())) {
                result = backOrderMapper.updateByPrimaryKeySelective(backOrder);
                return result;
            } else if (this.updateOrderStatus(backOrder) == 1 && backOrderMapper.updateByPrimaryKeySelective(backOrder) == 1) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 修改第三方后台退单信息
     *
     * @param backOrder
     * @return int
     */
    public int modifyThirdBackBeanCheck(BackOrder backOrder) {
        // 保存更新数据返回的状态
        int result = 0;
        if (null != backOrder) {
            if ("1".equals(backOrder.getBackDelFlag())) {
                result = backOrderMapper.updateByThirdId(backOrder);
                return result;
            } else if (this.updateOrderStatus(backOrder) == 1 && backOrderMapper.updateByThirdId(backOrder) == 1) {

                return 1;
            }
        }
        return 0;
    }

    /**
     * 修改交易表的交易状态 0=7:未审核 1=8：审核通过 2=9：审核未通过 4=11:退单成功
     *
     * @return int
     */
    public int updateOrderStatus(BackOrder backOrder) {
        int result;
        Order order;
        BackOrder backDer;
        // 表链接获取交易表ID
        try {
            backDer = backOrderMapper.selectbackOrderOne(backOrder.getBackOrderId());
            order = backOrderMapper.selectOrderOne(backDer.getOrderCode());
            if ("0".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("7"); // 7退货审核中8已退货9退货审核未通过)
            } else if ("1".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("8"); // 7退货审核中8已退货9退货审核未通过)
            } else if ("2".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("9"); // 7退货审核中8已退货9退货审核未通过)
            } else if ("4".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("11"); // 7退货审核中8已退货9退货审核未通过)
            } else if ("7".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("13"); // 拒绝退款
            }else if ("10".equals(backOrder.getBackCheck())) {
                order.setOrderStatus("18"); // 退款成功
            }

            result = backOrderMapper.updateOrder(order);
        } catch (Exception e) {
            result = 0;
            LOGGER.error("修改退单状态！", e);
        }
        return result;
    }

    /**
     * 查询BackOrderdetail
     *
     * @param backOrderId
     * @return BackOrder
     */
    public BackOrder detail(Long backOrderId) {
        // 根据Id获取单个退单详细信息
        BackOrder bo = backOrderMapper.selectBackOrderDetail_new(backOrderId);
        // 查询订单详细信息
        Order order = orderMapper.getPayOrderByCode(bo.getOrderCode());
        Long orderId = order.getOrderId();
        //订单货品
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectBackGoodsList(order.getOrderId());
        if (!"".equals(bo.getBackGoodsIdAndSum())) {
            // 获取退单对象 下面的退单的 商品Id
            String[] strs = bo.getBackGoodsIdAndSum().split("-");
            // 遍历退单对象下面的商品Id
            for (int j = 0; j < strs.length; j++) {
                String strss = strs[j];
                // 获取第J个商品的Id
                Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                // 根据ID获取单个商品的详细信息
                GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                if(orderGoodsList.size() >0 && orderGoodsList != null){
                    for(int i = 0; i < orderGoodsList.size(); i++){
                        if(orderProductVo.getGoodsInfoId().equals(orderGoodsList.get(i).getGoodsInfoId())){
                            orderProductVo.setGoodsInfoPreferPrice(orderGoodsList.get(i).getGoodsInfoPrice());
                        }
                    }
                }
                // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                Long counts;

                //退单数量
                if (strss.lastIndexOf(",") == strss.indexOf(",")){
                    //老数据
                    counts = Long.valueOf(strss.substring(strss.indexOf(",") + 1, strss.length()));
                }else {
                    counts = Long.valueOf(strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(",")));
                }
                // 装载商品数量数据
                orderProductVo.setGoodsCount(counts);
                // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                BigDecimal count = new BigDecimal(counts);
                // 计算总价格
                BigDecimal sum = price.multiply(count);
                // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                orderProductVo.setGoodsInfoCostPrice(sum);
                // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                bo.getOrderGoodslistVo().add(orderProductVo);
            }
        }
        if (bo != null) {
            // 查询退货商品信息
            // List<OrderGoods> orderGoodsList =
            // orderGoodsMapper.selectBackGoodsList(bo.getBackOrderCode());
            // 查询订单物流信息
            order.setOrderExpress(orderExpressMapper.selectOrderExpress(orderId));
            // 查询订单商品参与的商品促销信息
            if (orderGoodsList != null && !orderGoodsList.isEmpty()) {
                for (int i = 0; i < orderGoodsList.size(); i++) {
                    OrderGoods orderGoods = orderGoodsList.get(i);
                    // 查询货品详细信息
                    orderGoods.setGoodsProductVo(goodsProductService.queryViewVoByProductId(orderGoods.getGoodsInfoId()));
                    // 判断货品是否参加了商品促销
                    if (orderGoods.getGoodsMarketingId() != null && !"".equals(orderGoods.getGoodsMarketingId().toString())) {
                        // 查询促销信息
                        orderGoods.setMarketing(marketingService.marketingDetail(orderGoods.getGoodsMarketingId()));

                        // 判断是否有赠送优惠券
                        if (orderGoods.getHaveCouponStatus() != null && "1".equals(orderGoods.getHaveCouponStatus())) {
                            // 有送优惠券
                            // 查询赠送的优惠券信息
                            List<OrderGoodsInfoCoupon> orderGoodsInfoCouponList = orderGoodsInfoCouponMapper.selectOrderGoodsInfoCoupon(orderGoods.getOrderGoodsId());
                            // 查询优惠券详细
                            if (orderGoodsInfoCouponList != null && !orderGoodsInfoCouponList.isEmpty()) {
                                for (int j = 0; j < orderGoodsInfoCouponList.size(); j++) {
                                    orderGoodsInfoCouponList.get(j).setCoupon(couponService.searchCouponById(orderGoodsInfoCouponList.get(j).getCouponId()));
                                }
                                orderGoods.setOrderGoodsInfoCouponList(orderGoodsInfoCouponList);
                            }

                        }
                        // 判断是否赠送赠品
                        if (orderGoods.getHaveGiftStatus() != null && "1".equals(orderGoods.getHaveGiftStatus())) {
                            // 有送赠品
                            // 查询赠送的赠品
                            List<OrderGoodsInfoGift> orderGoodsInfoGiftList = orderGoodsInfoGiftMapper.selectOrderGoodsInfoGift(orderGoods.getOrderGoodsId());
                            // 查询赠品详细信息
                            if (orderGoodsInfoGiftList != null && !orderGoodsInfoGiftList.isEmpty()) {
                                for (int j = 0; j < orderGoodsInfoGiftList.size(); j++) {
                                    orderGoodsInfoGiftList.get(j).setGift(giftService.selectGiftDetailById(orderGoodsInfoGiftList.get(j).getGiftId()));
                                }
                                orderGoods.setOrderGoodsInfoGiftList(orderGoodsInfoGiftList);
                            }

                        }
                    }
                }
                order.setOrderGoodsList(orderGoodsList);
            }

            // 查询订单促销信息
            List<OrderMarketing> orderMarketingList = orderMarketingMapper.selectOrderMarketingList(orderId);
            // 查询订单商品促销的是否有赠品和优惠券
            if (orderMarketingList != null && !orderMarketingList.isEmpty()) {
                for (int i = 0; i < orderMarketingList.size(); i++) {
                    OrderMarketing orderMarketing = orderMarketingList.get(i);
                    // 查询订单促销详细信息
                    orderMarketing.setMarketing(marketingService.marketingDetail(orderMarketing.getMarketingId()));
                    // 判断订单促销是否送优惠券
                    if (orderMarketing.getHaveCouponStatus() != null && "1".equals(orderMarketing.getHaveCouponStatus())) {
                        // 有 查询赠送优惠券
                        List<OrderCoupon> orderCouponList = orderCouponMapper.selectOrderCoupon(orderMarketing.getOrderMarketingId());
                        // 查询优惠券详细
                        if (orderCouponList != null && !orderCouponList.isEmpty()) {
                            for (int j = 0; j < orderCouponList.size(); j++) {
                                orderCouponList.get(j).setCoupon(couponService.searchCouponById(orderCouponList.get(j).getCouponId()));
                            }
                            orderMarketing.setOrderCouponList(orderCouponList);
                        }

                    }

                    // 判断是否有赠品
                    if (orderMarketing.getHaveGiftStatus() != null && "1".equals(orderMarketing.getHaveGiftStatus())) {
                        // 有 查询赠送的赠品信息
                        List<OrderGift> orderGiftList = orderGiftMapper.selectOrderGiftList(orderMarketing.getOrderMarketingId());
                        // 查询赠品详细信息
                        if (orderGiftList != null && !orderGiftList.isEmpty()) {
                            for (int j = 0; j < orderGiftList.size(); j++) {
                                orderGiftList.get(j).setGift(giftService.selectGiftDetailById(orderGiftList.get(j).getGiftId()));
                            }
                            orderMarketing.setOrderGiftList(orderGiftList);
                        }
                    }
                }
                order.setOrderMarketingList(orderMarketingList);
            }

            bo.setOrder(order);
            // 装在退单对应的物流信息 //根据退单ID查询物流信息
            BackOrderGeneral general = backOrderMapper.selectGeneralByBackOrderId(bo.getBackOrderId());
            if (null != general) {
                bo.setOgisticsName(general.getOgisticsName());// 物流名称
                bo.setCreatTime(general.getCreatTime());// 发货时间
                bo.setOgisticsNo(general.getOgisticsNo());// 物流单号
            }

        }

        return bo;
    }
    /**
     * 查询退单信息根据退单编号（包括订单信息）
     *
     * @param backOrderId
     * @return BackOrder
     */
    @Override
    public BackOrder selectBackOrderByBackOrderId(Long backOrderId) {
       return backOrderMapper.selectBackOrderDetail(backOrderId);
    }

    /**
     * 获取退单详细信息
     *
     * @param backOrderId
     * @return BackOrder
     */
    public BackOrder backdetail(Long backOrderId, Long orderId) {
        BackOrder bo = backOrderMapper.selectBackOrderByBackOrderId(backOrderId);

        // 根据订单id查询实际退款金额
        bo.setActualBackPrice(new BigDecimal(backOrderMapper.queryActualBackPrice(orderId)));
        // 处理数据
        if (!"".equals(bo.getBackGoodsIdAndSum())) {
            // 获取退单对象 下面的退单的 商品Id
            String[] strs = bo.getBackGoodsIdAndSum().split("-");
            // 遍历退单对象下面的商品Id
            for (int j = 0; j < strs.length; j++) {
                String strss = strs[j];
                // 获取第J个商品的Id
                Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                // 根据ID获取单个商品的详细信息
                GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                String counts ="";
                if (strss.lastIndexOf(",") == strss.indexOf(",")){
                    //老数据
                    counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                }else {
                    counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                }
                // 装载商品数量数据
                orderProductVo.setGoodsCount(Long.valueOf(counts));
                // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                BigDecimal count = new BigDecimal(counts);
                // 计算总价格
                BigDecimal sum = price.multiply(count);
                // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                orderProductVo.setGoodsInfoPreferPrice(sum);
                // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                bo.getOrderGoodslistVo().add(orderProductVo);
            }
        }

        List imglist = new ArrayList();
        if (bo.getUploadDocuments() != null) {
            String[] imgs = bo.getUploadDocuments().split(",");
            for (int i = 0; i < imgs.length; i++) {
                imglist.add(imgs[i]);
            }
            bo.setImgs(imglist);
        }
        // 查询退货商品详细信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("backOrderCode", bo.getBackOrderCode());
        List<OrderGoods> goodslist = orderGoodsMapper.queryOrderGoodsByOrderIdAndBackCode(map);
        for (int j = 0; j < goodslist.size(); j++) {
            GoodsProduct goodsProduct = goodsProductMapper.queryProductByGoodsId(goodslist.get(j).getGoodsInfoId());
            goodslist.get(j).setGoodsImg(goodsProduct.getGoodsInfoImgId());
            goodslist.get(j).setGoodsName(goodsProduct.getGoodsInfoName());
            goodslist.get(j).setGoodsCode(goodsProduct.getGoodsInfoItemNo());
        }
        bo.setOrderGoodsList(goodslist);
        bo.setBackOrderGoodsList(orderGoodsMapper.selectOrderGoodsList(orderId));

        removeOrderList(bo.getBackOrderGoodsList(),bo.getBackGoodsIdAndSum());
        return bo;
    }


    /**
     * 删除多余的商品
     *
     * @param orderGoodses 订单里面一共的商品
     * @param acutalId     真实退货的商品
     */
    private void removeOrderList(List<OrderGoods> orderGoodses, String acutalId) {
        // 获得真正退货的货品id
        List<Long> ids = getActalIds(acutalId);
        List<Long> orderGoodsIds = getOrderGoodsIds(acutalId);


        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 待删除集合
        List<OrderGoods> removeOrderGoods = new ArrayList<>();

        for (OrderGoods orderGoods : orderGoodses) {
            // 真实退货id中不包含列表中的货品id  则放入待删除列表
            if (!orderGoodsIds.contains(orderGoods.getOrderGoodsId()))
            {
                removeOrderGoods.add(orderGoods);
            }
        }

        orderGoodses.removeAll(removeOrderGoods);
    }


    /**
     * 获得真实退货的货品id
     *
     * @param acutalId 货品id
     * @return 返回真实退货的货品id
     */
    private List<Long> getActalIds(String acutalId) {

        List<Long> ids = new ArrayList<>();

        try {
            String[] idAndSums = acutalId.split("-");

            for (String idAndSum : idAndSums) {

                if (StringUtils.isNotEmpty(idAndSum)) {
                    String[] id = idAndSum.split(",");
                    ids.add(Long.parseLong(id[0]));
                }
            }

            return ids;
        } catch (Exception e) {
            LOGGER.error("GetActalIds fail...");
        }

        return ids;
    }

    /**
     * 获得真实退货的货品id
     *
     * @param acutalId 货品id
     * @return 返回真实退货的货品id
     */
    private List<Long> getOrderGoodsIds(String acutalId) {

        List<Long> ids = new ArrayList<>();

        try {
            String[] idAndSums = acutalId.split("-");

            for (String idAndSum : idAndSums) {

                if (StringUtils.isNotEmpty(idAndSum)) {
                    String[] id = idAndSum.split(",");
                    ids.add(Long.parseLong(id[2]));
                }
            }

            return ids;
        } catch (Exception e) {
            LOGGER.error("getOrderGoodsIds fail...");
        }

        return ids;
    }

    /**
     * 查询第三方退单数量(已买)
     *
     * @param thirdId
     * @return int
     */
    public int queryBackOrderCount(Long thirdId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("thirdId", thirdId);
            return this.backOrderMapper.queryBackOrderCount(map);
        } finally {
            map = null;
        }
    }

    /**
     * 插入退单信息
     *
     * @param backOrder
     * @return int
     */
    public int insertBackOrderInfo(BackOrder backOrder) {
        return this.backOrderMapper.insertBackOrderInfo(backOrder);
    }

    /**
     * 根据订单编号查找退单信息
     *
     * @param orderNo
     * @return
     */
    public BackOrder queryBackOrderByOrderCode(String orderNo) {
        BackOrder backOrders = this.backOrderMapper.queryBackOrderByOrderCode(orderNo);
        if (!"".equals(backOrders.getBackGoodsIdAndSum())) {
            // 保存退单的总金额
            BigDecimal backOrderSumPrice = new BigDecimal("0.00");
            // 获取退单对象 下面的退单的 商品Id
            String[] strs = backOrders.getBackGoodsIdAndSum().split("-");
            // 遍历退单对象下面的商品Id
            for (int j = 0; j < strs.length; j++) {
                String strss = strs[j];
                // 获取第J个商品的Id
                Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                // 根据ID获取单个商品的详细信息
                GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                // 退货商品的价格
                backOrderSumPrice = backOrderSumPrice.add(orderProductVo.getGoodsInfoPreferPrice());
                // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                String counts ="";
                if (strss.lastIndexOf(",") == strss.indexOf(",")){
                    //老数据
                    counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                }else {
                    counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                }
                // 装载商品数量数据
                orderProductVo.setGoodsCount(Long.valueOf(counts));
                // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                BigDecimal count = new BigDecimal(counts);
                // 计算总价格
                BigDecimal sum = price.multiply(count);
                // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                orderProductVo.setGoodsInfoPreferPrice(sum);
                // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                backOrders.getOrderGoodslistVo().add(orderProductVo);
            }
        }
        return backOrders;
    }

    /**
     * 根据订单编号和退货退款标志查找退单信息
     *
     * @param orderNo 订单编号
     * @param isBack  退货退款标志，1退货，2退款
     * @return
     */
    public BackOrder queryBackOrderByOrderCodeAndIsback(String orderNo,String isBack) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo",orderNo);
        map.put("isBack",isBack);
        BackOrder backOrders = this.backOrderMapper.queryBackOrderByOrderCodeAndIsback(map);
        if (!"".equals(backOrders.getBackGoodsIdAndSum())) {
            // 保存退单的总金额
            BigDecimal backOrderSumPrice = new BigDecimal("0.00");
            // 获取退单对象 下面的退单的 商品Id
            String[] strs = backOrders.getBackGoodsIdAndSum().split("-");
            // 遍历退单对象下面的商品Id
            for (int j = 0; j < strs.length; j++) {
                String strss = strs[j];
                // 获取第J个商品的Id
                Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                // 根据ID获取单个商品的详细信息
                GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                // 退货商品的价格
                backOrderSumPrice = backOrderSumPrice.add(orderProductVo.getGoodsInfoPreferPrice());
                // 退货的商品数量 随意找的货品字段装的数据 只用于前台显示
                String counts ="";
                if (strss.lastIndexOf(",") == strss.indexOf(",")){
                    //老数据
                    counts = strss.substring(strss.indexOf(",") + 1, strss.length());
                }else {
                    counts = strss.substring(strss.indexOf(",") + 1, strss.lastIndexOf(","));
                }
                // 装载商品数量数据
                orderProductVo.setGoodsCount(Long.valueOf(counts));
                // 退货的单个商品价格 随意找的货品字段装的数据 只用于前台显示
                BigDecimal price = orderProductVo.getGoodsInfoPreferPrice();
                BigDecimal count = new BigDecimal(counts);
                // 计算总价格
                BigDecimal sum = price.multiply(count);
                // 装载单类形 退单货品的总价 随意找的货品字段装的数据 只用于前台显示
                orderProductVo.setGoodsInfoPreferPrice(sum);
                // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                backOrders.getOrderGoodslistVo().add(orderProductVo);
            }
        }
        return backOrders;
    }

    /**
     * 根据退单号查询物流
     *
     * @param backOrderId
     * @return BackOrderGeneral
     */
    public BackOrderGeneral queryBackOrderGeneral(Long backOrderId) {
        return backOrderMapper.queryBackOrderGeneral(backOrderId);
    }

    /**
     * 退款成功后退回库存
     *
     * @param orderId
     * @param backOrderId
     * @return Integer
     */
    public Integer addStockOrderBack(Long orderId, Long backOrderId) {
        // 根据订单id查询仓库id
        Order order = orderMapper.orderDetail(orderId);
        // 根据订单id查询货品个数
        Integer count = 0;
        List<OrderGoods> orderGoods = orderGoodsMapper.selectBackGoodsList(orderId);
        for (int i = 0; i < orderGoods.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productId", orderGoods.get(i).getGoodsInfoId());
            map.put("stock", orderGoods.get(i).getGoodsInfoNum());
            map.put("distinctId", order.getWareId());
            count += goodsProductMapper.addBaseStock(map);
        }
        return count;
    }

    /**
     * 判断该订单是否已经存在退单记录 如果存在 则返回true  否则返回flase
     *
     * @param orderCode 订单编号
     * @return
     */
    @Override
    public boolean isBackOrderRecordExist(String orderCode) {

        return backOrderMapper.selectBackOrderRecodCount(orderCode) == 0 ? false : true;
    }

    /**
     * 判断该订单是否已经存在退单记录 如果存在 则返回true  否则返回flase
     *
     * @param orderCode 订单编号
     * @return
     */
    @Override
    public BackOrder isBackOrder(String orderCode) {
        return this.backOrderMapper.selectBackOrder(orderCode);
    }

    /**
     * 查询退单的数量（已买）
     *
     * @param customerId
     * @return int
     */
    @Override
    public int queryBackOrderCountBuy(Long customerId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("customerId", customerId);
            return this.backOrderMapper.queryBackOrderCountBuy(map);
        } finally {
            map = null;
        }
    }

    /**
     * 按主键修改订单审核
     * 
     * @param backOrder
     * @return int
     */
    public int modifyBackOrderByCheck(BackOrder backOrder) {
        return backOrderMapper.updateByPrimaryKeySelective(backOrder);
    }

    public GoodsProductService getGoodsProductService() {
        return goodsProductService;
    }

    @Resource(name = "GoodsProductService")
    public void setGoodsProductService(GoodsProductService goodsProductService) {
        this.goodsProductService = goodsProductService;
    }

    public BackOrderMapper getBackOrderMapper() {
        return backOrderMapper;
    }

    @Resource(name = "BackOrderMapper")
    public void setBackOrderMapper(BackOrderMapper backOrderMapper) {
        this.backOrderMapper = backOrderMapper;
    }

    public OrderExpressMapper getOrderExpressMapper() {
        return orderExpressMapper;
    }

    @Resource(name = "OrderExpressMapper")
    public void setOrderExpressMapper(OrderExpressMapper orderExpressMapper) {
        this.orderExpressMapper = orderExpressMapper;
    }

    public OrderGiftMapper getOrderGiftMapper() {
        return orderGiftMapper;
    }

    @Resource(name = "OrderGiftMapper")
    public void setOrderGiftMapper(OrderGiftMapper orderGiftMapper) {
        this.orderGiftMapper = orderGiftMapper;
    }

    public OrderCouponMapper getOrderCouponMapper() {
        return orderCouponMapper;
    }

    @Resource(name = "OrderCouponMapper")
    public void setOrderCouponMapper(OrderCouponMapper orderCouponMapper) {
        this.orderCouponMapper = orderCouponMapper;
    }

    public OrderGoodsMapper getOrderGoodsMapper() {
        return orderGoodsMapper;
    }

    @Resource(name = "OrderGoodsMapper")
    public void setOrderGoodsMapper(OrderGoodsMapper orderGoodsMapper) {
        this.orderGoodsMapper = orderGoodsMapper;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    @Resource(name = "OrderMapper")
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public OrderMarketingMapper getOrderMarketingMapper() {
        return orderMarketingMapper;
    }

    @Resource(name = "OrderMarketingMapper")
    public void setOrderMarketingMapper(OrderMarketingMapper orderMarketingMapper) {
        this.orderMarketingMapper = orderMarketingMapper;
    }

    public MarketingService getMarketingService() {
        return marketingService;
    }

    @Resource(name = "MarketingService")
    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public OrderGoodsInfoCouponMapper getOrderGoodsInfoCouponMapper() {
        return orderGoodsInfoCouponMapper;
    }

    @Resource(name = "OrderGoodsInfoCouponMapper")
    public void setOrderGoodsInfoCouponMapper(OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper) {
        this.orderGoodsInfoCouponMapper = orderGoodsInfoCouponMapper;
    }

    public CouponService getCouponService() {
        return couponService;
    }

    @Resource(name = "CouponService")
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    public OrderGoodsInfoGiftMapper getOrderGoodsInfoGiftMapper() {
        return orderGoodsInfoGiftMapper;
    }

    @Resource(name = "OrderGoodsInfoGiftMapper")
    public void setOrderGoodsInfoGiftMapper(OrderGoodsInfoGiftMapper orderGoodsInfoGiftMapper) {
        this.orderGoodsInfoGiftMapper = orderGoodsInfoGiftMapper;
    }

    public GiftService getGiftService() {
        return giftService;
    }

    @Resource(name = "GiftService")
    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    public CustomerConsumeMapper getCustomerConsumeMapper() {
        return customerConsumeMapper;
    }

    // Spring注入
    @Resource(name = "customerConsumeMapper")
    public void setCustomerConsumeMapper(CustomerConsumeMapper customerConsumeMapper) {
        this.customerConsumeMapper = customerConsumeMapper;
    }

    public GoodsProductMapper getGoodsProductMapper() {
        return goodsProductMapper;
    }

    public CustomerPointMapper getCustomerPointMapper() {
        return customerPointMapper;
    }

    public PointLevelServiceMapper getPointLevelServiceMapper() {
        return pointLevelServiceMapper;
    }

    // Spring注入
    @Resource(name = "pointLevelServiceMapper")
    public void setPointLevelServiceMapper(PointLevelServiceMapper pointLevelServiceMapper) {
        this.pointLevelServiceMapper = pointLevelServiceMapper;
    }

    // Spring注入
    @Resource(name = "customerPointMapper")
    public void setCustomerPointMapper(CustomerPointMapper customerPointMapper) {
        this.customerPointMapper = customerPointMapper;
    }

    public IntegralSetMapper getIntegralSetmapper() {
        return integralSetmapper;
    }

    // Spring注入
    @Resource(name = "integralSetMapper")
    public void setIntegralSetmapper(IntegralSetMapper integralSetmapper) {
        this.integralSetmapper = integralSetmapper;
    }

    public CustomerInfoMapper getCustomerInfoMapper() {
        return customerInfoMapper;
    }

    // Spring注入
    @Resource(name = "customerInfoMapper")
    public void setCustomerInfoMapper(CustomerInfoMapper customerInfoMapper) {
        this.customerInfoMapper = customerInfoMapper;
    }

    @Resource(name = "GoodsProductMapper")
    public void setGoodsProductMapper(GoodsProductMapper goodsProductMapper) {
        this.goodsProductMapper = goodsProductMapper;
    }

    /**
     * 根据退单状态和主键ID修改退单审核状态
     *
     * @param backId    主键ID
     * @param backCheck 退单状态
     * @return
     */
    @Override
    public int modifyBackOrderByCheckNew(Long backId, String backCheck) {
        // 如果是退单的确认收货
        int count = 0;
        BackOrder backOrder;

        if ("4".equals(backCheck)) {
            // 设置退货的价格
            this.deleteBackGoods(backId);
            // 删除订单中 退货成功的商品
            this.setBackGoodsStatus(backId);
        }
        if (backId != null) {
            backOrder = new BackOrder();
            backOrder.setBackOrderId(backId);
            backOrder.setBackCheck(backCheck);
            count = backOrderMapper.updateByPrimaryKeySelectiveNew(backOrder);
        }

        return count;
    }

    /**
     * 判断积分是否够扣除
     *
     * @param orderId 订单id
     * @return 如果够扣除返回0  不够返回1
     */
    @Override
    public int isPointEnough(String orderId) {

        if (StringUtils.isEmpty(orderId)) {
            return 0;
        }

        //查找积分设置信息 ,为了查询积分开关
        PointSet pointSet = this.pointSetService.findPointSet();

        // 根据订单id 查询订单信息
        Order order = orderMapper.querySimpleOrder(Long.parseLong(orderId));

        if (null == pointSet || null == order) {
            return 0;
        }

        // 如果该订单 没有赠送积分 则直接返回0
        if (!order.isGivePoint()) {
            return 0;
        }

        // 判断是否需要扣除积分 不需要 扣除积分 直接返回0
        if (!isNeedReduce(order, pointSet)) {
            return 0;
        }

        // 判断积分是否够扣除  够扣除 返回0
        if (isReducePointEnough(order, pointSet)) {
            return 0;
        }

        // 不够扣除返回1
        return 1;
    }

    @Override
    public BackGoods selectOrdersetBackGodds(Long goodsinfoId, Long OrderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsinfoId",goodsinfoId);
        map.put("orderId",OrderId);
        OrderGoods og = this.orderGoodsMapper.selectOrdersetBackGodds(map);
        BackGoods bgoods = new BackGoods();
        bgoods.setOrderId(og.getOrderId());
        bgoods.setBackOrderId(og.getBackOrderId());
        bgoods.setGoodsInfoId(og.getGoodsInfoId());
        bgoods.setBackGoodsTime(new Date());
        bgoods.setCatId(og.getCatId());
        bgoods.setBackGoodsPrice(og.getGoodsBackPrice());
        return bgoods;
    }

    /**
     * 后台修改退单金额，更新退单表
     * @param backOrder 退单实体
     * @return 修改结果
     */
    public int updateBackOrderReducePrice(BackOrder backOrder){
        return backOrderMapper.updateBackOrderReducePrice(backOrder);
    }

    /**
     * 判断积分是否扣扣除
     *
     * @param order    订单
     * @param pointSet 积分设置
     * @return 积分够扣除 返回true  不够返回false
     */
    private boolean isReducePointEnough(Order order, PointSet pointSet) {

        // 获得总的积分
        int allPoint = getCustomerAllPoint(order.getCustomerId());

        // 目前总的积分 要算上该笔订单退货时返还的积分
        if (order.getOrderIntegral() != null && order.getOrderIntegral().intValue() != 0) {
            allPoint += order.getOrderIntegral().intValue();
        }

        // 该笔订单 赠送的积分
        int getPoint = order.getOrderGetPoint().intValue();

        // 如果用户当前的总积分 比该笔订单赠送的积分大 则返回true
        return allPoint - getPoint >= 0;
    }


    /**
     * 判断是否需要扣除积分
     * @param order 订单
     * @param pointSet 积分设置
     * @return 需要扣积分 返回true  不需要返回false
     */
    private boolean isNeedReduce(Order order, PointSet pointSet) {
        return order.getBusinessId()==0 && "1".equals(pointSet.getIsOpen());
    }

}
