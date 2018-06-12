/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.site.shoppingcart.controller;

import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.marketing.bean.Marketing;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.marketing.service.RushCustomerService;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.service.OrderService;
import com.ningpai.site.customer.controller.CustomerController;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.shoppingcart.bean.MiniShoppingCart;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.ShoppingCartConstants;

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
import java.util.*;

/**
 * 购物车控制器
 *
 * @author NINGPAI-LIH
 * @since 2014年11月4日15:54:30
 */
@Controller
public class ShoppingCartController {

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(CustomerController.class);

    /**
     * 购物车路径
     */
    private static final String REDIRECT = "myshoppingcart.html";

    private static final String LOGIN = "../login.html";
    /**
     * 站点信息service
     */
    @Resource(name = "basicSetService")
    private BasicSetService basicSetService;

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    @Resource(name = "MarketingService")
    private MarketingService marketingService;

    @Resource(name = "rushCustomerService")
    private RushCustomerService rushCustomerService;

    @Resource(name = "TopAndBottomService")
    private TopAndBottomService topAndBottomService;

    private GoodsProductService productService;

    @Resource(name="OrderService")
    private OrderService orderService;

    @Resource(name="HsiteGoodsProductService")
    private GoodsProductService goodsProductService;


    /**
     * spring 注解 会员service
     */
    private CustomerServiceInterface customerServiceInterface;

    @Autowired
    private ShoppingCartAddressService shoppingCartAddressService;

    /**
     * 查询购物车
     *
     * @return ModelAndView
     */
    @RequestMapping("myshoppingcart")
    public ModelAndView shoppingCart(HttpServletRequest request, PageBean pageBean, HttpServletResponse response) {
        request.getSession().setAttribute("tok", UUID.randomUUID().toString());
            // 获取地址信息
        ShoppingCartWareUtil wareUtil = shoppingCartAddressService.loadAreaFromRequest(request);
            Map<String, Object> shopMap = shoppingCartService.shopCartMap(request, wareUtil, response);

        ModelAndView mav = new ModelAndView(ShoppingCartConstants.SHOPPINGCARTLIST)
                    .addObject("cartMap", shopMap)
                    .addObject("pro", marketingService.selectAll())
                    .addObject("wareUtil", wareUtil).addObject("sx", request.getSession().getAttribute("tok").toString()).addObject("indexUrl",getIndexViewUrl(request));
            // 站点信息
            mav.addObject("basicSet", basicSetService.findBasicSet());
            mav.addObject("list", request.getParameter("list"));
            response.setHeader("Cache-Control","no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma","no-cache");
            return topAndBottomService.getSimpleTopAndBottom(mav);
    }


    /**
     * 获得访问首页的url
     * @return 返回访问首页的url
     */
    private String getIndexViewUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }


    /**
     * 更改购物车选中状态
     *
     * @param shoppingId
     *            购物车id
     * @param status
     *            要修改的购物车状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/changeshopstatus")
    @ResponseBody
    public String changeShopStatus(Long shoppingId, String status, HttpServletRequest request, HttpServletResponse response) {

        return shoppingCartService.changeShopStatus(shoppingId, status, request, response);
    }

    /**
     * 更改购物车选中状态
     *
     * @param shoppingId
     *            购物车全部id
     * @param status
     *            要修改的购物车状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/changeshopstatusbyparams")
    @ResponseBody
    public String changeShopStatusByParams(Long[] shoppingId, String status, HttpServletRequest request, HttpServletResponse response) {
        return shoppingCartService.changeShopStatusByParam(shoppingId, status, request, response);
    }

    /**
     * 删除购物车商品
     *
     * @param request
     * @return int
     */
    @RequestMapping("delshoppingcartbyid")
    @ResponseBody
    public int delShoppingCartById(Long shoppingCartId, Long goodsInfoId, HttpServletRequest request, HttpServletResponse response) {
        // 当前登录会员id
        // Long customerId = (Long)
        // request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        return shoppingCartService.delShoppingCartById(shoppingCartId, goodsInfoId, request, response);
    }

    /**
     * 修改购物数量
     *
     * @param shoppingCartId
     * @param num
     * @return int
     */
    @RequestMapping("changeshoppingcartbyid")
    @ResponseBody
    public int changeShoppingCartById(HttpServletRequest request, Long shoppingCartId, Long num) {
        // 当前登录会员id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId != null) {
            // 当前登录成功的会员信息
            CustomerAllInfo customerAllInfo = customerServiceInterface.selectByPrimaryKey(customerId);
            // 操作日志
            OperaLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "修改购物数量", "修改购物数量-->用户名：" + customerAllInfo.getCustomerUsername());
            // 日志记录
            LOGGER.info("修改购物车ID为：" + shoppingCartId + "的购物数量！");
            return shoppingCartService.changeShoppingCartById(shoppingCartId, num);
        } else {
            return 0;
        }

    }

    /**
     * 修改优惠
     *
     * @param shoppingCartId
     * @param marketingId
     * @return int
     */
    @RequestMapping("changeshoppingcartmarket")
    @ResponseBody
    public int changeShoppingCartMarket(Long shoppingCartId, Long marketingActivityId, Long marketingId) {
        return -1;
    }

    /**
     * 修改商品优惠
     *
     * @param shoppingCartId
     * @param marketingActivityId
     * @param marketingId
     * @return
     */
    @RequestMapping("/changeshoppingcartmarts")
    public ModelAndView changeShoppingCartMarts(Long shoppingCartId, Long marketingActivityId, Long marketingId, HttpServletRequest request, HttpServletResponse response) {
        shoppingCartService.changeShoppingCartMarket(shoppingCartId, marketingId, marketingActivityId, request, response);
        return new ModelAndView(new RedirectView(REDIRECT+"?list="+request.getParameter("checkList")));
    }

    /**
     * 添加购物车的控制器
     *
     * @param goodsNum
     *            购买货品的数量
     * @param productId
     *            货品ID
     * @param request
     *            请求对象
     * @return true 添加成功 false 添加失败
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addProductToShopCar")
    @ResponseBody
    public boolean addProductToShopCar(Long goodsNum, Long productId, HttpServletRequest request, Long distinctId, HttpServletResponse response, Long fitId)
            throws UnsupportedEncodingException {
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            if(null == distinctId){
                shoppingCart.setDistinctId(1103L);
            }else{
                shoppingCart.setDistinctId(distinctId);
            }

            shoppingCart.setGoodsInfoId(productId);
            shoppingCart.setGoodsNum(goodsNum);
            return this.shoppingCartService.addShoppingCart(shoppingCart, request, response) >= 1 ? true : false;
        } finally {
            shoppingCart = null;
        }
    }


    /**
     * 添加购物车的控制器（新）
     *
     * @param goodsNum
     *            购买货品的数量
     * @param productId
     *            货品ID
     * @param request
     *            请求对象
     * @return true 添加成功 false 添加失败
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addProductToShopCarNew")
    @ResponseBody
    public int addProductToShopCarNew(Long goodsNum, Long productId, Long productStock, HttpServletRequest request, Long distinctId, HttpServletResponse response, Long fitId)
            throws UnsupportedEncodingException {
        ShoppingCart shoppingCart = new ShoppingCart();
        //抢购商品还可以购买的数量
        Integer num = 0;
        //抢购商品购物车里的数量
        Integer cartgoodsNum = 0;
        if(goodsNum.intValue() <= 0){
            return 0;
        }
        //查询商品促销信息
        List<Marketing> marketingList = marketingService.selectMarketingByGoodsInfoId(productId,null,null);
        //当商品是抢购促销时
        if(request.getSession().getAttribute("customerId") != null) {
            Long customerId = Long.valueOf(request.getSession().getAttribute("customerId").toString());
            if(marketingList != null && marketingList.size() == 1 && "11".equals(marketingList.get(0).getCodexType())){
                Map<String, Object> map = new HashMap<>();
                map.put("rushId", marketingList.get(0).getRushs().get(0).getRushId());
                map.put("customerId", customerId);
                map.put("goodsInfoId", productId);
                //抢购商品还可以购买的数量
                //num = rushCustomerService.selectByParamMap(map);
                //购物车改抢购商品的数量
                List<ShoppingCart> shoppingCarts = shoppingCartService.selectShoppingCartListByCustomerId(customerId);
                if(shoppingCarts != null && shoppingCarts.size() > 0){
                    for(ShoppingCart sc : shoppingCarts){
                        if(sc.getGoodsInfoId().intValue() == productId.intValue()){
                            //购物车该抢购商品的数量
                            cartgoodsNum = sc.getGoodsNum().intValue();
                        }
                    }
                }
                //总数量
                Integer allNum = num + cartgoodsNum + goodsNum.intValue();
                if(allNum > marketingList.get(0).getRushs().get(0).getRushLimitation()){
                    return -2;
                }
            }
        }

        try {
            if(null == distinctId){
                shoppingCart.setDistinctId(1103L);
            }else{
                shoppingCart.setDistinctId(distinctId);
            }

            shoppingCart.setGoodsInfoId(productId);
            shoppingCart.setGoodsNum(goodsNum);
            MiniShoppingCart result = shoppingCartService.miniShoppingCart(request);
            int shoppingcartNum = 0;
            if(result != null && result.getMiniGoodsList() != null){
                if(result.getMiniGoodsList().size() > 0){
                    for (int i = 0; i < result.getMiniGoodsList().size(); i++) {
                         if(result.getMiniGoodsList().get(i).getGoodsInfoId().intValue() == productId.intValue()){
                            //该货品购物车已经有的数量
                            shoppingcartNum +=  result.getMiniGoodsList().get(i).getBuNum().intValue();
                        }
                    }
                }
            }
            //没有库存
            if(productStock <= 0 || (goodsNum.intValue()+shoppingcartNum) > productStock){
                return -3;
            }else{
                return this.shoppingCartService.addShoppingCart(shoppingCart, request, response);
            }
        } finally {
            shoppingCart = null;
        }
    }

    /**
     *
     * @param request
     * @param response
     * @return  list
     */
    @RequestMapping("/againBuy")
    @ResponseBody
    public List<Object> againBuy(HttpServletRequest request,HttpServletResponse response,Long orderId){
        List<Object> productList = new ArrayList<Object>();
        // 查询订单商品信息
        List<OrderGoods> orderGoodsList =orderService.queryOrderGoodsAndProductInfo(orderId);
        for (OrderGoods orderGoods : orderGoodsList) {
            //判断是否是赠品  赠品不参加再次购买
            if((null==orderGoods.getIsPresent()) || (null!=orderGoods.getIsPresent() && orderGoods.getIsPresent().equals("0"))){
                GoodsProductVo productinfo = goodsProductService.querySimpleProductByProductId(orderGoods.getGoodsInfoId());
                //检查商品是否下架
                if(null!=productinfo && productinfo.getGoodsInfoAdded().equals("1")){
                    ShoppingCart shoppingCart = new ShoppingCart();
                    Long distinctId = orderService.queryRepositoryId(orderId);
                    if(null == distinctId){
                        shoppingCart.setDistinctId(1103L);
                    }else{
                        shoppingCart.setDistinctId(distinctId);
                    }
                    shoppingCart.setGoodsInfoId(productinfo.getGoodsInfoId());
                    shoppingCart.setGoodsNum(1L);
                    try {
                        shoppingCartService.addShoppingCart(shoppingCart, request, response);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else{
                    //已经下架的商品传到前台进行页面提示
                    productList.add(productinfo);
                }
            }

        }
        return productList;
    }

    /**
     * 购买优惠套装
     *
     * @param productIds
     *            套装内的货品ID数组 {@link Long}
     * @param groupId
     *            组合ID {@link Long}
     * @return 插入是否成功 1 成功 -1 未登录 0 失败
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/buyPackProduct")
    @ResponseBody
    public int buyPackPro(Long[] productIds, Long groupId, Long distinctId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Long custId = this.takeCustIdFromRequest(request);
        ShoppingCart shop = new ShoppingCart();
        shop.setDistinctId(null == distinctId ? 1103L : distinctId);
        if (null != custId) {
            shop.setFitId(groupId);
            if (null != productIds && productIds.length > 0) {
                for (int i = 0; i < productIds.length; i++) {
                    shop.setGoodsInfoId(productIds[i]);
                    shop.setGoodsNum(1L);
                    this.shoppingCartService.addShoppingCart(shop, request, response);
                }
            }
            return 1;
        } else {
            /* 未登录返回 */
            return -1;
        }
    }

    /**
     * KKK site 购买优惠套装
     *
     * @param fitId
     *            套装ID {@link Long}
    * @return 购买标记 {@link Integer} 0:失败 1:成功
    */
    @RequestMapping("/buyPrePackage")
    @ResponseBody
    public int buyaPrePackage(Long fitId, Long distinctId, HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart shoppingCart = new ShoppingCart();
        if (null != fitId && fitId > 0L) {
            shoppingCart.setGoodsInfoId(Long.parseLong("110012" + fitId));
            shoppingCart.setDistinctId(distinctId);
            shoppingCart.setGoodsNum(1L);
            shoppingCart.setFitId(fitId);
            try {
                return shoppingCartService.addShoppingCart(shoppingCart, request, response);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("添加购物车报错：" + e);
                return 0;
            }

        }
        return 0;
    }

    /**
     * 修改地址
     *
     * @param distinctId
     *            地址id
     * @param chAddress
     *            详细地址
     * @param chProvince
     *            省
     * @param chCity
     *            市
     * @param chDistinct
     *            区
     * @param request
     * @return ModelAndView
     */
    @RequestMapping("/updateprovince")
    public ModelAndView updateProvince(Long distinctId, String chAddress, String chProvince, String chCity, String chDistinct, HttpServletRequest request) {
        // 省
        request.getSession().setAttribute("chProvince", chProvince);
        // 详细地址
        request.getSession().setAttribute("chAddress", chAddress);
        // 市
        request.getSession().setAttribute("chCity", chCity);
        // 地址
        request.getSession().setAttribute("chDistinct", chDistinct);
        // 区id
        request.getSession().setAttribute("distinctId", distinctId);
        return new ModelAndView(new RedirectView(REDIRECT));
    }

    /**
     * 删除分组下单个商品并且为添加商品
     *
     * @param shoppingCartId
     *            购物车id
     * @param goodsInfoId
     *            商品id
     * @param fitId
     *            优惠分组id
     * @return
     */
    @RequestMapping("/delshoppingcatgoodsgroup")
    public ModelAndView delShoppingCatGoodsGroup(Long shoppingCartId, Long goodsInfoId, Long fitId, HttpServletRequest request, HttpServletResponse response) {
        shoppingCartService.delGoodsGroupByS(shoppingCartId, goodsInfoId, fitId, request, response);
        return new ModelAndView(new RedirectView(REDIRECT));
    }

    /**
     * 添加购物车,并跳转到购物车
     *
     * @param goodsNum
     *            购买货品的数量
     * @param productId
     *            货品ID
     * @param request
     *            请求对象
     * @return true 添加成功 false 添加失败
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addproducttoshoppingcar")
    public ModelAndView addProductToShoppingCar(Long goodsNum, Long productId, HttpServletRequest request, Long distinctId, HttpServletResponse response, Long fitId)
            throws UnsupportedEncodingException {
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            if(null == distinctId){
                shoppingCart.setDistinctId(1103L);
            }else{
                shoppingCart.setDistinctId(distinctId);
            }
            // 商品id
            shoppingCart.setGoodsInfoId(productId);
            // 商品数量
            shoppingCart.setGoodsNum(goodsNum);
            // 添加到购物车
            this.shoppingCartService.addShoppingCart(shoppingCart, request, response);
            return new ModelAndView(REDIRECT);
        } finally {
            shoppingCart = null;
        }
    }

    /**
     * 从请求中取出登陆的会员iD
     *
     * @param request
     *            请求对象
     * @return 拿出的会员Id
     */
    public final Long takeCustIdFromRequest(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("customerId");
    }
    /**
     * get方法
     * */
    public GoodsProductService getProductService(Long askId) {
        return productService;
    }
    /**
     * Spring 注入
     * */
    @Resource(name = "HsiteGoodsProductService")
    public void setProductService(GoodsProductService productService) {
        this.productService = productService;
    }

    /**
     * get方法
     * */
    public CustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    /**
     * spring 注解 会员service
     * 
     * @param customerServiceInterface
     */
    @Resource(name = "customerServiceSite")
    public void setCustomerServiceInterface(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }
}
