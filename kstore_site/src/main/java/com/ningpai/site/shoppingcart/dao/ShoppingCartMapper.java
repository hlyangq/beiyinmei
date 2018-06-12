/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.site.shoppingcart.dao;

import java.util.List;
import java.util.Map;

import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.bean.StoreTemp;

/**
 * @author ggn
 * 
 */
public interface ShoppingCartMapper {
    /**
     * 查询我的购物车
     *
     * @param {@link java.lang.Long}
     * @return List
     */
    List<Object> shoppingCart(Map<String, Object> paramMap);

    /**
     *
     * @param shoppingCartId
     * @return
     */
    List<Object> selectById(Long shoppingCartId);

    /**
     * 根据模板id分组查询得到分组商品重量,以及数量
     *
     * @param list
     * @return
     */
    List<ShoppingCart> shopCartListOrderByFreightIds(List<Long> list);

    /**
     * 删除
     * 
     * @param paramMap
     * @return int
     */
    int delShoppingCartById(Map<String, Object> paramMap);

    /**
     * 修改购买数量
     * 
     * @param sc
     * @return int
     */
    int changeShoppingCartById(ShoppingCart sc);

    /**
     * 修改优惠信息
     * 
     * @param sc
     * @return int
     */
    int changeShoppingCartMarket(ShoppingCart sc);

    /**
     * 修改优惠信息
     *
     * @param sc
     * @return int
     */
    int changeShoppingCartMarketId(ShoppingCart sc);

    /**
     * 查询购物车商品 根据List里的ID
     * 
     * @param list
     * @return List
     */
    List<ShoppingCart> shoppingCartListByIds(List<Long> list);

    /**
     * 加入购物车
     * 
     * @param shoppingCart
     * @return int
     */
    int addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 删除所购买商品
     * 
     * @param list
     * @return int
     */
    int deleteShoppingCartByIds(List<Long> list);

    /**
     * MINI
     * 
     * @param customerId
     * @return
     */
    List<ShoppingCart> shoppingCartMini(Long customerId);

    /**
     * 根据购物车id促销id分组查询
     * 
     * @param list
     * @return
     */
    List<ShoppingCart> shopCartListOrderByMarkettingId(List<Long> list);

    /**
     * 查询购物车总数
     * 
     * @param paramMap
     * @return Long
     */
    int shoppingCartCount(Map<String, Object> paramMap);

    /**
     * 查询是否已经加入购物车商品
     * 
     * @param shoppingCart
     * @return int
     */
    int selectCountByReady(ShoppingCart shoppingCart);

    /**
     * 修改购物车
     * 
     * @param shoppingCart
     * @return int
     */
    int updateShoppingCart(ShoppingCart shoppingCart);

    /**
     * 查询我已经加入购物车的数量
     * 
     * @param customerId
     * @return int
     */
    int selectSumByCustomerId(Long customerId);

    /**
     * 修改选中的订单优惠
     * 
     * @param shoppingCart
     *            要修改的参数
     * @author NINGPAI-LIH
     */
    int changeShoppingCartOrderMarket(ShoppingCart shoppingCart);

    /**
     * 根据地区id查询省名称
     * 
     * @param districtId
     *            地区id
     * @return 省名称和地区id
     */
    String selectPNameByParam(Long districtId);

    /**
     * 查询加入的购物车id
     * 
     * @param shoppingCart
     * @return
     */
    Long selectLastId(ShoppingCart shoppingCart);

    /**
     * 根据商品id和客户id查询购物车内容
     * 
     * @param shoppingCart
     * @return
     */
    ShoppingCart selectShopingByParam(ShoppingCart shoppingCart);

    /**
     * 查询购物车
     * 
     * @param list
     * @return List
     */
    List<ShoppingCart> shopCartListByIds(List<Long> list);

    /**
     * 查询购物车里信息
     * 
     * @param customerId
     * @return List
     */
    List<ShoppingCart> selectShoppingCartListByCustomerId(Long customerId);

    /**
     * 查询购物车里的商家
     * 
     * @param customerId
     * @return List
     */
    List<StoreTemp> selectStoreTemp(Long customerId);

    /**
     * 查询购物车里的商家
     *
     * @param shopcartIds
     * @return List
     */
    List<StoreTemp> selectStoreTempByshopcartIds(List<Long> shopcartIds);

    /**
     * 根据用户id修改购车仓库
     *
     * */
    int updShoppingCartHouseByCstId(Map<String,Object> map);

    /**
     * 查询用户 对应的购物车id的数量
     * @param id 购物车id
     * @param customerId 用户id
     * @return 返回数量
     */
    int queryShoppingCateCount(Long id, Long customerId);
}
