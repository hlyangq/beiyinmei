package com.ningpai.m.backorder.service;

import java.util.Map;

import com.ningpai.util.PageBean;


/***
 * 退货记录
 * @author qiyuanyuan
 *
 */
public interface ReturnGoodsService {
    
    /***
     * 保存退单物流信息
     * @param wlname  物流名称
     * @param wlno    物流单号
     * @param orderNo  订单号
     */
    int saveBackOrderGeneral(String wlname,String wlno,String orderNo);
    

    /**
     * 新增一条退货记录 ，更改交易表状态退单金额，更新商品信息表
     * @param returnGoods
     * @return
     */
    Boolean saveReturnGoodsDetail(Long orderId,String returnYuanyin,Long id); 
    
    /**
     * 查询当前会员的退单信息
     *
     * @param paramMap
     *            查询订单条件
     * @return
     */
    PageBean queryAllBackOrders(Map<String, Object> paramMap, PageBean pb);

    /**
     * 根据customerId查询退款退货订单数量
     * */
    int queryCancleOrderCount(Long customerId);
 }
