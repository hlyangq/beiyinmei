/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.shoppingcart.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.shoppingcart.bean.ShopCarUtil;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.util.PageBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ggn
 * 购物车service
 */
public interface ShoppingCartService {

    

    /**
     * 删除购物车商品
     * @param shoppingCartId
     * @return int
     */
    int delShoppingCartById(Long shoppingCartId,Long goodsInfoId,HttpServletRequest request,HttpServletResponse response);
    /**
     * 修改购买数量
     * @param shoppingCartId
     * @param num
     * @return  int
     */
    int changeShoppingCartById(Long shoppingCartId, Long num);

    /**
     * 修改优惠
     * @param shoppingCartId 购物车id
     * @param marketingId 单品优惠分组id
     * @param marketingActivityId 活动优惠分组id
     * @return int
     */ 
    int changeShoppingCartMarket(Long shoppingCartId, Long marketingId,Long marketingActivityId);
    
    /**
     * 修改优惠
     * @param shoppingCartId 购物车id
     * @param marketingId 单品优惠分组id
     * @param marketingActivityId 活动优惠分组id
     * @return int
     */ 
    int changeShoppingCartMarket(Long shoppingCartId, Long marketingId,Long marketingActivityId,HttpServletRequest request,HttpServletResponse response);

    /**
     * 得到可以使用的优惠券
     * @param request
     * @param box
     * @return
     */
    List<Coupon> getUsedCouponlist(HttpServletRequest request, Long[] box);

    /**
     * 得到各个商家的金额
     * @param businessId
     * @param shopdata
     * @return
     */
    Map<String, Object> getPayorderThirdPriceMap(Long businessId, List<ShoppingCart> shopdata,Long distinctId);

    /**
     * 得到各个商家的金额
     * @param businessId
     * @param shopdata
     * @return
     */
    Map<String, Object> getEveryThirdPriceMap(Long businessId, List<ShoppingCart> shopdata,Long distinctId);

    /**
     * 新购物车结算
     *
     * @param request
     * @param box
     * @return
     */
    Map<String, Object> newsubOrder(HttpServletRequest request, Long[] box, CustomerAddress customerAddress,String[] boxgift);

    /**
     * 根据所选择的商品进入订单确认查询
     * @param request
     * @param box
     * @return List
     */
    Map<String,Object> subOrder(HttpServletRequest request, Long[] box,Long[] marketingId,Long[] thirdId,ShoppingCartWareUtil wareUtil);
    /**
     * 添加购物车
     * @param shoppingCart
     * @return int
     * @throws UnsupportedEncodingException 
     */
    int addShoppingCart(ShoppingCart shoppingCart,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException;
    /**
     * 查询购物车购买的商品信息
     * @param request
     * @param box
     * @return List
     */
     List<ShoppingCart> searchByProduct(HttpServletRequest request, Long[] box);
     /**
      * 删除已经购买商品
      * @param request
      * @param shoppingCartId
      * @return int
      */
     int deleteShoppingCartByIds(HttpServletRequest request,
            Long[] shoppingCartId);

    @Transactional
    int addFit2Cart(ShoppingCart shoppingCart, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;

    /**
      * 加载cookie中的购物车信息
      * @return 加载好的列表
     * @throws UnsupportedEncodingException 
      */
     List<ShopCarUtil> loadCookShopCar(HttpServletRequest request) throws UnsupportedEncodingException;

     /**
      * 从cookie添加到购物车
      * @param request
      * @return int
      */
     int loadCoodeShopping(HttpServletRequest request);

     /**
      * 删除cookie中的购物车数据
      * @param request 请求对象
      * @param response 相应对象
      * @return 删除的数量
     * @throws UnsupportedEncodingException 
      */
     int delCookShopCar(Long productId,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException;
     
     /**
      * 修改要选中的订单优惠
      * @param cart 
      * @return 结果
      */
     int changeShoppingCartOrderMarket(ShoppingCart cart);
     
     /**
      * 查询购物车内容
      * @return
      */
     PageBean selectShoppingCart(HttpServletRequest request,ShoppingCartWareUtil cartWareUtil,PageBean pageBean,HttpServletResponse response);

     
     /**
      * 更改商品选中状态
      * @param shoppingId 购物车id
      * @param status 要修改的状态
      * @param request
      * @param response
      * @return
      */
     String changeShopStatus(Long shoppingId,String status,HttpServletRequest request,HttpServletResponse response);
     /**
      * 批量更改商品选中状态
      * @param shoppingId 购物车id
      * @param status 要修改的状态
      * @param request
      * @param response
      * @return
      */
     String changeShopStatusByParam(Long[] shoppingId,String status,HttpServletRequest request,HttpServletResponse response);
     
     /**
      * 查询最后加入的商品id
      * @param response
      * @param request
      * @return
      */
     Long selectLastId(ShoppingCart shoppingCart,HttpServletResponse response,HttpServletRequest request);
     
     /**
      * 限购
      * @param goodsDetailBean 商品
      * @return
      */
     GoodsDetailBean forPurchasing(GoodsDetailBean goodsDetailBean,Long customerId);

    /**
     * 得到各个商家的运费
     * @param thirdId
     * @param cityId
     * @param cartList
     * @return
     */
    BigDecimal calExpressPriceByThirdId(Long thirdId, Long cityId, List<ShoppingCart> cartList);

    /**
     *  参数是需要运费购物车id
     * 第三方id根据第三方来分组得到各个商家的运费-
     * @param cityId
     * @param cartIds
     * @return
     */
    Map<String ,Object>  getNewExpressPrice(Long cityId, List<Long> cartIds,Long cityInfoId);

    /**
     * 根据购物车里面的货品查询是否存在包邮的促销活动
     * 返回list集合,不包邮的购物车
     * @param cartList
     * @return
     */
    List<ShoppingCart> getNobaoyouShoppingcarts(List<ShoppingCart> cartList,Long disId);

    /**
     * 新产品使用购物车
     *
     * @param request
     * @param districtId
     * @return Map
     */
    Map<String, Object> shopCartMap(Long customerId,
                                    Long districtId, HttpServletRequest request);


    /**
     * 判断购物车商品是否存在删除或下架的情况
     * @param box   购物车id集合
     * @return true:商品数据正常,false:商品数据不正常
     */
    boolean checkHasErrorProduct(Long[] box);

    Integer countCart(Long customerId);

    Integer countCartInCookie();
}


