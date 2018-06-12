/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.site.shoppingcart.service;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.shoppingcart.bean.MiniShoppingCart;
import com.ningpai.site.shoppingcart.bean.ShopCarUtil;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.bean.PointSet;
import com.ningpai.util.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 购物车service
 * 
 * @author NINGPAI-LIH
 * @since 2014年11月4日15:54:30
 * 
 */
public interface ShoppingCartService {

    /***
     * 修改会员积分
     * 
     * @param customerId
     * @param point
     * @return
     */
    int updatePoint(Long customerId, Long point);

    /**
     * 查询购物车
     * 
     * @param request
     * @return List
     */
    PageBean shoppingCart(PageBean pageBean, HttpServletRequest request);

    /**
     * mini购物车
     * 
     * @param request
     * @return MiniShoppingCart
     */
    MiniShoppingCart miniShoppingCart(HttpServletRequest request);

    /**
     * 删除购物车商品
     * 
     * @param shoppingCartId
     * @return int
     */
    int delShoppingCartById(Long shoppingCartId, Long goodsInfoId,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * 修改购买数量
     * 
     * @param shoppingCartId
     * @param num
     * @return int
     */
    int changeShoppingCartById(Long shoppingCartId, Long num);

    /**
     * 修改优惠
     * 
     * @param shoppingCartId
     *            购物车id
     * @param marketingId
     *            单品优惠分组id
     * @param marketingActivityId
     *            活动优惠分组id
     * @return int
     */
    int changeShoppingCartMarket(Long shoppingCartId, Long marketingId,
            Long marketingActivityId);

    /**
     * 修改优惠
     * 
     * @param shoppingCartId
     *            购物车id
     * @param marketingId
     *            单品优惠分组id
     * @param marketingActivityId
     *            活动优惠分组id
     * @return int
     */
    int changeShoppingCartMarket(Long shoppingCartId, Long marketingId,
            Long marketingActivityId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * 根据购物车里面的货品查询是否存在包邮的促销活动 返回list集合,不包邮的购物车
     * 
     * @param cartList
     * @return
     */
    List<ShoppingCart> getNobaoyouShoppingcarts(List<ShoppingCart> cartList,Long distinctId);

    /**
     * 得到各个商家的运费
     * 
     * @param thirdId
     * @param cityId
     * @param cartList
     * @return
     */
    BigDecimal getEverythirdExpressPrice(Long thirdId, Long cityId,
            List<ShoppingCart> cartList);

    /**
     * 计算运费-不同地区的货品价格以及库存
     * 
     * @param cityId
     * @param cartIds
     * @return
     */
    Map<String, Object> getNewExpressPrice(HttpServletRequest request,Long addressId, Long cityId,
            Long[] cartIds);

    /**
     * 根据所选择的商品进入订单确认查询
     * 
     * @param request
     * @param box
     * @return List
     */
    Map<String, Object> subOrder(HttpServletRequest request, Long[] box,
            Long[] marketingId, Long[] thirdId, ShoppingCartWareUtil wareUtil,
            Long customerId);

    /**
     * 添加购物车
     * 
     * @param shoppingCart
     * @return int
     * @throws UnsupportedEncodingException
     */
    int addShoppingCart(ShoppingCart shoppingCart, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 查询购物车购买的商品信息
     * 
     * @param request
     * @param box
     * @return List
     */
    List<ShoppingCart> searchByProduct(HttpServletRequest request, Long[] box);

    /**
     * 删除已经购买商品
     * 
     * @param request
     * @param shoppingCartId
     * @return int
     */
    int deleteShoppingCartByIds(HttpServletRequest request,
            Long[] shoppingCartId);

    /**
     * 加载cookie中的购物车信息
     * 
     * @return 加载好的列表
     * @throws UnsupportedEncodingException
     */
    List<ShopCarUtil> loadCookShopCar(HttpServletRequest request)
            throws UnsupportedEncodingException;

    /**
     * 从cook添加到购物车
     * 
     * @param request
     * @return int
     */
    int loadCoodeShopping(HttpServletRequest request);

    /**
     * 删除cookie中的购物车数据
     * 
     * @param request
     *            请求对象
     * @param response
     *            相应对象
     * @return 删除的数量
     * @throws UnsupportedEncodingException
     */
    int delCookShopCar(Long productId, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 修改要选中的订单优惠
     * 
     * @param cart
     * @return 结果
     */
    int changeShoppingCartOrderMarket(ShoppingCart cart);

    /**
     * 查询购物车内容
     * 
     * @return
     */
    PageBean selectShoppingCart(HttpServletRequest request,
            ShoppingCartWareUtil cartWareUtil, PageBean pageBean,
            HttpServletResponse response);

    /**
     * 删除优惠分组下单个商品并添加其他商品到购物车内
     *
     * @return 结果
     */
    int delGoodsGroupByS(Long shoppingCartId, Long goodsInfoId, Long fitId,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * 更改商品选中状态
     * 
     * @param shoppingId
     *            购物车id
     * @param status
     *            要修改的状态
     * @param request
     * @param response
     * @return
     */
    String changeShopStatus(Long shoppingId, String status,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * 批量更改商品选中状态
     * 
     * @param shoppingId
     *            购物车id
     * @param status
     *            要修改的状态
     * @param request
     * @param response
     * @return
     */
    String changeShopStatusByParam(Long[] shoppingId, String status,
            HttpServletRequest request, HttpServletResponse response);

    /**
     * 立即加入购物车
     * 
     * @param shoppingCart
     * @param response
     * @param request
     * @return
     */
    Long selectLastId(ShoppingCart shoppingCart, HttpServletResponse response,
            HttpServletRequest request);

    /**
     * 一键下单
     * 
     * @param wareUtil
     * @return购物车id
     */
    Long[] insertShoppingCart(Long[] box, HttpServletRequest request,
            ShoppingCartWareUtil wareUtil);

    /**
     * 限购
     * 
     * @param goodsDetailBean
     *            商品
     * @return
     */
    GoodsDetailBean forPurchasing(GoodsDetailBean goodsDetailBean,
            Long customerId);

    /**
     * 判断手机号是否是用户名或者已绑定
     * 
     * @param mobel
     *            手机号
     * @param request
     * @return
     */
    public long isSowMobel(String mobel, HttpServletRequest request);

    /**
     * 查询购物商品信息
     * 
     * @param list
     * @return List
     */
    List<ShoppingCart> shopCartListByIds(List<Long> list);

    /**
     * 新商品结算页面
     * 
     * @param request
     * @param box
     * @return
     */
    Map<String, Object> subshopCartMap(HttpServletRequest request, Long[] box,ShoppingCartWareUtil wareUtil,PointSet pointSet);

    /**
     * 根据券码检验验证码
     * @param request
     * @param box
     * @param codeNo
     * @param distinctId
     *          地区id
     * @return
     */
    int getUsedCouponBycodeNo(HttpServletRequest request, Long[] box,String codeNo,Long distinctId);
    /**
     * 新产品使用购物车
     * 
     * @param request
     * @param wareUtil
     * @param response
     * @return Map
     */
    Map<String, Object> shopCartMap(HttpServletRequest request,
            ShoppingCartWareUtil wareUtil, HttpServletResponse response);

    /**
     * 查询购物车里信息
     *
     * @param customerId
     * @return List
     */
    List<ShoppingCart> selectShoppingCartListByCustomerId(Long customerId);

    /**
     * 验证使用的优惠券是否可用
     * @param customerId
     *          用户id
     * @param shoplists
     *          订单购物车项集合
     * @param coupon
     *          优惠券
     * @return(false:不可使用,true:可以使用)
     */
    boolean checkCouponIsOk(Long customerId, List<ShoppingCart> shoplists, Coupon coupon,Long distinctId);

    /**
     * 判断购物车商品是否存在删除或下架的情况
     * @param box   购物车id集合
     * @return true:商品数据正常,false:商品数据不正常
     */
    boolean checkHasErrorProduct(Long[] box);


    /**
     * 校验 购物车 主要是校验 购物车里面的购物车id 是否是用户自己的
     *
     * @param box        购车id
     * @param customerId 用户id
     * @return 如果是自己的返回true  否则返回false
     */
    boolean validateShopCart(Long[] box, Long customerId);
}
