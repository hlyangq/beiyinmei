/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.goods.vo.GoodsProductVo;
import org.springframework.stereotype.Repository;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.BackOrderGeneral;
import com.ningpai.order.bean.Order;
import com.ningpai.order.dao.BackOrderMapper;

/**
 * @author ggn
 * 
 */
@Repository("BackOrderMapper")
public class BackOrderMapperImpl extends BasicSqlSupport implements
        BackOrderMapper {

    /**
     * 根据订单编号 查询退单记录数
     *
     * @param orderCode
     * @return
     */
    @Override
    public int selectBackOrderRecodCount(String orderCode) {
        return this.selectOne("com.ningpai.web.dao.BackOrderMapper.selectBackOrderRecodCount", orderCode);
    }

    /**
     * 根据订单编号 查询退单记录
     *
     * @param orderCode
     * @return
     */
    @Override
    public BackOrder selectBackOrder(String orderCode) {
        return this.selectOne("com.ningpai.web.dao.BackOrderMapper.selectBackOrder", orderCode);
    }

    /**
     * 查询退单列表
     * @param paramMap
     * @return
     */
    public int searchBackOrderCount(Map<String, Object> paramMap) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.selectBackOrderCount",
                paramMap);
    }

    /**
     * 查询订单总数
     * @param paramMap
     * @return
     */
    public List<Object> searchBackOrderList(Map<String, Object> paramMap) {
        return this.selectList(
                "com.ningpai.web.dao.BackOrderMapper.searchBackOrderList",
                paramMap);
    }

    /**
     * 查询退单明细
     * @param backOrderId
     * @return
     */
    public BackOrder selectBackOrderDetail(Long backOrderId) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.selectBackOrderDetail",
                backOrderId);
    }

    /**
     * 根据店铺ID差选店铺下面的退单数据的数量
     * @param paramMap
     * @return
     */
    @Override
    public int searchBackOrderThirdCount(Map<String, Object> paramMap) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.searchBackOrderThirdCount",
                        paramMap);
    }

    /**
     * 根据店铺信息查询当前店铺下面的退单信息
     * @param paramMap
     * @return
     */
    @Override
    public List<Object> searchBackOrderLisThird(Map<String, Object> paramMap) {
        return this.selectList(
                "com.ningpai.web.dao.BackOrderMapper.searchBackOrderLisThird",
                paramMap);
    }

    /**
     * 查询退单明细
     * @param backOrderId
     * @return
     */
    public BackOrder selectBackOrderDetail_new(Long backOrderId) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.selectBackOrderDetailnew",
                backOrderId);
    }

    /**
     * 按主键修改订单审核
     * @param backOrder
     * @return
     */
    public int updateByPrimaryKeySelective(BackOrder backOrder) {
        return this
                .update("com.ningpai.web.dao.BackOrderMapper.updateByPrimaryKeySelective",
                        backOrder);
    }

    /**
     * 按主键修改第三方订单审核
     * @param backOrder
     * @return
     */
    public int updateByThirdId(BackOrder backOrder) {
        return this
                .update("com.ningpai.web.dao.BackOrderMapper.updateByThirdId",
                        backOrder);
    }

    /**
     * 查询退单的数量
     * @param map
     *            封装参数 {@link java.util.Map}
     * @return
     */
    public int queryBackOrderCount(Map<String, Object> map) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.queryBackOrderCount", map);
    }

    /***
     * 订单号查询订单那信息
     * 
     * @return
     */
    @Override
    public Order selectOrderOne(String orderCode) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.selectOrderOne",
                        orderCode);
    }

    /***
     * 更改交易记录(Order)的状态
     * 
     * @param order
     * @return
     */
    @Override
    public int updateOrder(Order order) {
        return this.update("com.ningpai.web.dao.BackOrderMapper.updateOrder",
                order);
    }

    /**
     *
     * 根据id获取退单对象
     */
    @Override
    public BackOrder selectbackOrderOne(Long backOrderId) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.selectbackOrderOne",
                backOrderId);
    }

    /**
     * 删除订单里面退货成功的 货品
     * @param goodsInfoId
     *            货品Id
     * @return
     */
    @Override
    public int deleteBackGoodsById(Long goodsInfoId) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.deleteBackGoodsById",
                goodsInfoId);
    }

    /**
     * 根据Id获取单个商品信息
     * @param goodsInfoId
     * @return
     */
    @Override
    public GoodsProductVo selectGoodsById(Long goodsInfoId) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsInfoId", goodsInfoId);
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.selectGoodsById", map);
    }

    /**
     * 获取退单数据总条数
     * @param paramMap
     * @return
     */
    @Override
    public int searchBackOrderCountnew(Map<String, Object> paramMap) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.searchBackOrderCountnew",
                paramMap);
    }

    /**
     * 查询所有退单
     * @param paramMap
     * @return
     */
    @Override
    public List<Object> searchBackOrderListnew(Map<String, Object> paramMap) {
        return this.selectList(
                "com.ningpai.web.dao.BackOrderMapper.searchBackOrderListnew",
                paramMap);
    }

    /***
     * 根据退单ID 获取退单物流信息
     * 
     * @param backOrderId
     * @return
     */
    @Override
    public BackOrderGeneral selectGeneralByBackOrderId(Long backOrderId) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.selectGeneralByBackOrderId",
                        backOrderId);
    }

    /**
     *
     * @param map
     *            封装参数 {@link java.util.Map}
     * @return
     */
    @Override
    public int queryBackOrderCountBuy(Map<String, Object> map) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.queryBackOrderCountBuy",
                map);
    }

    /**
     * 插入退单信息
     * 
     * @param backOrder
     * @return
     */
    @Override
    public int insertBackOrderInfo(BackOrder backOrder) {
        return this.insert(
                "com.ningpai.web.dao.BackOrderMapper.insertSelective",
                backOrder);
    }

    /**
     * 获取退单信息根据订单编号
     * 
     * @param orderNo
     * @return
     */
    @Override
    public BackOrder queryBackOrderByOrderCode(String orderNo) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.queryBackOrderByOrderCode",
                        orderNo);
    }

    /**
     * 获取退单信息根据订单编号和退货/退款标志
     *
     * @param map
     * @return
     */
    @Override
    public BackOrder queryBackOrderByOrderCodeAndIsback(Map<String, Object> map) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.queryBackOrderByOrderCodeAndIsback",
                        map);
    }

    @Override
    public List<BackOrder> queryBackOrderByBusinessId(Map<String, Object> map) {
        return this.selectList(
                "com.ningpai.web.dao.BackOrderMapper.queryBackOrderByBusinessId",
                map);
    }

    @Override
    public List<Object> queryReportBackOrderByBusinessId(Map<String, Object> map) {
        return this.selectList(
                "com.ningpai.web.dao.BackOrderMapper.queryReportBackOrderByBusinessId",
                map);
    }

    @Override
    public int queryReportBackOrderByBusinessIdCount(Map<String, Object> paramMap) {
        return this.selectOne("com.ningpai.web.dao.BackOrderMapper.queryReportBackOrderByBusinessIdCount", paramMap);
    }

    /**
     * 获取退单信息根据订单编号根据退单id
     * 
     * @return
     */
    @Override
    public BackOrder selectBackOrderByBackOrderId(Long backOrderId) {
        return this
                .selectOne(
                        "com.ningpai.web.dao.BackOrderMapper.selectBackOrderByBackOrderId",
                        backOrderId);
    }

    /**
     * 根据订单id查询实际的退款金额
     *
     * @param orderId 订单id
     * @return 返回实际的退款金额
     */
    @Override
    public int queryActualBackPrice(Long orderId) {
        Integer result = this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.queryActualBackPrice",
                orderId);

        if (null == result) {
            return 0;
        }
        return result;
    }

    /**
     * 根据退单号查询物流
     * 
     * @param backOrderId
     * @return
     */
    @Override
    public BackOrderGeneral queryBackOrderGeneral(Long backOrderId) {
        return this.selectOne(
                "com.ningpai.web.dao.BackOrderMapper.queryBackOrderGeneral",
                backOrderId);
    }

    /**
     * 根据主键ID和退单状态更新退单信息
     *
     * @param backOrder
     * @return
     */
    @Override
    public int updateByPrimaryKeySelectiveNew(BackOrder backOrder) {
        return this.update("com.ningpai.web.dao.BackOrderMapper.updateByPrimaryKeySelectiveNew",
                backOrder);
    }

    /**
     * 后台修改退单金额，更新退单表
     * @param backOrder 退单实体
     * @return 修改结果
     */
    @Override
    public int updateBackOrderReducePrice(BackOrder backOrder) {
        return this.update("com.ningpai.web.dao.BackOrderMapper.updateByPrimaryKeySelective",
                backOrder);
    }
}
