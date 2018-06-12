/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.m.customer.mapper.CustomerMapper;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.OrderMapper;
import org.springframework.stereotype.Service;

import com.ningpai.comment.bean.Comment;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.m.customer.mapper.CustomerOrderMapper;
import com.ningpai.m.customer.service.CustomerOrderService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.customer.vo.GoodsBean;
import com.ningpai.other.util.IPAddress;
import com.ningpai.util.PageBean;

/**
 * @see com.ningpai.m.customer.service.CustomerOrderService
 * @author qiyuanyuahn
 */
@Service("customerOrderServiceM")
public class CustomerOrderServiceImpl implements CustomerOrderService {
    // spring注解
    private CustomerOrderMapper customerOrderMapper;

    private CustomerPointServiceMapper customerPointServiceMapper;

    @Resource(name="customerMapperM")
    private CustomerMapper customerMapper;

    @Resource(name="customerPointMapper")
    private CustomerPointMapper customerPointMapper;



    /**
     * 订单接口
     */
    @Resource(name = "OrderMapper")
    private OrderMapper orderMapper;

    /*
     *
     *
     * @see com.ningpai.m.customer.service.CustomerOrderService#queryAllMyOrders(java.util.Map, com.ningpai.util.PageBean)
     */
    @Override
    public PageBean queryAllMyOrders(Map<String, Object> paramMap, PageBean pb) {
        Long count = customerOrderMapper.searchTotalCount(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        pb.setPageSize(4);
        paramMap.put(CustomerConstants.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstants.ENDNUM, pb.getEndRowNum());
        pb.setList(customerOrderMapper.queryAllMyOrders(paramMap));
        return pb;
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.service.CustomerOrderService#queryOrderByCustIdAndOrderId(java.lang.Long, java.lang.Long)
     */
    @Override
    public Object queryOrderByCustIdAndOrderId(Long orderId, Long customerId) {
       //定义map
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            //用户id
            paramMap.put("customerId", customerId);
            //订单id
            paramMap.put(ConstantUtil.ORDERID, orderId);
            //执行查询订单操作
            return customerOrderMapper.queryOrderByParamMap(paramMap);
        } finally {
            paramMap = null;
        }
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.service.CustomerOrderService#selectNotice(java.lang.Long)
     */
    @Override
    public Map<String, Object> selectNotice(Long customerId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(CustomerConstants.CUSTOMERID, customerId);
            paramMap.put("flag", 0);
            resultMap.put("pendingNum", customerOrderMapper.selectpendingOrderNotice(paramMap));
            paramMap.put("flag", 1);
            resultMap.put("commentNum", customerOrderMapper.selectpendingOrderNotice(paramMap));
            return resultMap;
        } finally {
            resultMap = null;
            paramMap = null;
        }
    }

    /*
     *
     *
     * @see com.ningpai.m.customer.service.CustomerOrderService#cancelOrder(java.lang.Long, java.lang.String)
     */
    @Override
    public int cancelOrder(Long orderId, String reason) {
        // 执行返回的结果
        int result = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(ConstantUtil.ORDERID, orderId);
            paramMap.put("reason", reason);
            result = customerOrderMapper.cancelOrder(paramMap);
            if(result == 1){
                Order order = orderMapper.orderDetail(orderId);
                if(order.getOrderIntegral()!=null &&!"".equals(order.getOrderIntegral())){
                    CustomerPoint point = new CustomerPoint();
                    point.setPointDetail("订单取消返回积分");
                    point.setPoint(order.getOrderIntegral().intValue());
                    point.setPointType("1");
                    point.setDelFlag("0");
                    point.setCreateTime(new Date());
                    point.setCustomerId(order.getCustomerId());
                    // 返回后的积分保存到数据库
                    customerMapper.insertSelective(point);
                    // 更新会员的积分
                    if(order.getCustomerId() !=null && !"".equals(order.getCustomerId())){
                        Map<String, Object> pointparamMap = new HashMap<String, Object>();
                        pointparamMap.put("customerId",order.getCustomerId());
                        //查询会员积分
                        com.ningpai.m.customer.bean.CustomerPoint customerPoint = customerMapper.selectCustomerPointByCustomerId(pointparamMap);
                        //判断会员积分是否为空
                        if(customerPoint !=null){
                            if(customerPoint.getPointSum() !=null && !"".equals(customerPoint.getPointSum())){
                                customerPoint.setPointSum(customerPoint.getPointSum()+order.getOrderIntegral());
                                result = customerMapper.updateCustomerPoint(customerPoint);
                            }
                        }
                    }
                }
            }
        } finally {
            paramMap = null;
        }
        return result;
    }

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerOrderService#comfirmofGoods(java.lang.Long)
     */
    @Override
    public int comfirmofGoods(Long orderId) {
        return customerOrderMapper.comfirmofGoods(orderId);
    }

    /*
     * 
     * 
     * @see com.ningpai.m.customer.service.CustomerOrderService#addGoodsComment(com.ningpai.comment.bean.Comment)
     */
    @Override
    public int addGoodsComment(Comment comment) {
        return customerOrderMapper.addGoodsComment(comment);
    }

    @Override
    public int addGoodsComment(HttpServletRequest request, Comment comment, Long orderId) {
        int flag = 0;
        // 添加评论 返回评论编号
        comment.setIsConsult("0");
        comment.setPublishTime(new Date());
        comment.setIsDisplay("1");
        comment.setDelFlag("0");
        comment.setPublishIp(IPAddress.getIpAddr(request));
        int score = Integer.parseInt(comment.getCommentScore().toString());
        if (score < 2) {
            comment.setCommentType("2");
        } else if (2 <= score && score <= 3) {
            comment.setCommentType("1");
        } else {
            comment.setCommentType("0");
        }


        //根据评论的货品id  设置评论的第三方id
        setThirdIdForComment(comment);

        flag = customerOrderMapper.addGoodsComment(comment);
        // 添加评论积分
        customerPointServiceMapper.addIntegralByType(comment.getCustomerId(), "4");
        // 修改订单商品为已评价 并更新评论编号
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setCommentId(comment.getCommentId());
        goodsBean.setEvaluateFlag("1");
        goodsBean.setOrderId(orderId);
        goodsBean.setGoodsId(comment.getGoodsId());
        customerOrderMapper.updateOrderGoods(goodsBean);
        return flag;
    }

    /**
     * 根据评论的货品id  设置评论的第三方id
     *
     * @param comment 评论实体
     */
    private void setThirdIdForComment(Comment comment) {
        comment.setThirdId(customerOrderMapper.queryThirdIdByGoodsInfoId(comment.getGoodsId()));
    }

    /*
     * 根据订单id和商品id查询待评价商品
     * @see com.ningpai.m.customer.service.CustomerOrderService#selectByOrderIdAndGoodsId(java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    public GoodsBean selectByOrderIdAndGoodsId(Long customerId, Long goodsId,
            Long orderId,Long commentId) {
        //定义Map
        Map<String, Object> paramMap =  new HashMap<String,Object>();
        //参数 用户id
        paramMap.put("customerId", customerId);
        //参数 货品id
        paramMap.put("goodsInfoId", goodsId);
        //参数 订单id
        paramMap.put("orderId", orderId);
        //参数 订单id
        paramMap.put("commentId", commentId);
        //执行查询待评价商品操作
        return customerOrderMapper.selectByOrderIdAndGoodsId(paramMap);
    }
    
    /*
     * 根据订单id查询订单货品
     * @see com.ningpai.m.customer.service.CustomerOrderService#selectByOrderId(java.lang.Long)
     */
    @Override
    public List<GoodsBean> selectByOrderId(Long orderId) {
        return customerOrderMapper.selectByOrderId(orderId);
    }
    
    /*
     * 根据订单类型查询订单数量
     * @see com.ningpai.m.customer.service.CustomerOrderService#searchOrderCountByCount(java.lang.String)
     */
    @Override
    public Long searchOrderCountByCount(String orderType) {
        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("type", orderType);
        return customerOrderMapper.searchTotalCount(paramMap);
    }

    /**
     * 查询所有待付款订单的数量
     *
     * @param type
     * @author houyichang 2015/12/26
     */
    @Override
    public int queryNoMoneyOrderCount(String paramString,String type,Long customerId) {
        //申明一个map集合
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //对参数进行赋值，由于该方法是用之前的查询总量方法，所以paramString参数暂时在此方法内无效，可无视
        paramMap.put("paramString",paramString);
        paramMap.put("type",type);
        paramMap.put("customerId",customerId);
        paramMap.put("date",null);
        //查询总量并返回
        int count = customerOrderMapper.searchTotalCount(paramMap).intValue();
        return count;
    }

    /**
     * 查询所有待收货订单的总量
     *
     * @param type
     * @author houyichang 2015/12/26
     */
    @Override
    public int queryNoShowHuoOrderCount(String paramString,String type,Long customerId) {
        //申明一个map集合
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //对参数进行赋值，由于该方法是用之前的查询总量方法，所以paramString参数暂时在此方法内无效，可无视
        paramMap.put("paramString",paramString);
        paramMap.put("type",type);
        paramMap.put("customerId",customerId);
        paramMap.put("date",null);
        //返回总量
        int count = customerOrderMapper.searchTotalCount(paramMap).intValue();
        return count;
    }

    /**
     * 查询所有待评价订单的总量
     *
     * @param type
     * @author houyichang 2015/12/26
     */
    @Override
    public int queryNoCommentOrderCount(String paramString,String type,Long customerId) {
        //申明一个Map集合
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //对参数进行赋值，由于该方法是用之前的查询总量方法，所以paramString参数暂时在此方法内无效，可无视
        paramMap.put("paramString",paramString);
        paramMap.put("type",type);
        paramMap.put("customerId",customerId);
        paramMap.put("date",null);
        //返回订单总量
        int count = customerOrderMapper.searchTotalCount(paramMap).intValue();
        return count;
    }

    /**
     * 查询所有退款退货订单的总量
     *
     * @param type
     * @author houyichang 2015/12/26
     */
    @Override
    public int queryCancleOrderCount(String paramString,String type,Long customerId) {
        //申明一个map集合
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //对参数进行赋值，由于该方法是用之前的查询总量方法，所以paramString参数暂时在此方法内无效，可无视
        paramMap.put("paramString",paramString);
        paramMap.put("type",type);
        paramMap.put("customerId",customerId);
        //返回订单总量
        int count = customerOrderMapper.searchTotalCount(paramMap).intValue();
        return count;
    }
    
    public CustomerOrderMapper getCustomerOrderMapper() {
        return customerOrderMapper;
    }

    @Resource(name = "customerOrderMapperM")
    public void setCustomerOrderMapper(CustomerOrderMapper customerOrderMapper) {
        this.customerOrderMapper = customerOrderMapper;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }
}
