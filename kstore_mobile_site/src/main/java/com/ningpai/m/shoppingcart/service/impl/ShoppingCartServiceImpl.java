/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.shoppingcart.service.impl;

import com.google.common.base.Predicate;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponRange;
import com.ningpai.coupon.bean.ParamIds;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightExpressAll;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.dao.ProductWareMapper;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.goods.vo.GoodsProductDetailVo;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.order.util.OrderUtil;
import com.ningpai.m.shoppingcart.bean.*;
import com.ningpai.m.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.shoppingcart.util.ShopCartValueUtil;
import com.ningpai.marketing.bean.*;
import com.ningpai.marketing.dao.FullbuyPresentScopeMapper;
import com.ningpai.marketing.dao.MarketingMapper;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.system.service.DistrictService;
import com.ningpai.util.KCollection;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author ggn
 */
@Service("ShoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

    /**
     * 日志
     */
    public static final MyLogger LOGGER = new MyLogger(ShoppingCartServiceImpl.class);

    private static final String CUSTOMERID = "customerId";
    private static final String NPSTORE_MID = "_npstore_mId";
    private static final String NPSTORE_SHOPCAR = "_npstore_shopcar";
    private static final String CODE001 = "110012";
    private static final String NPSTORE_SHOPSTATUS = "_npstore_shopstatus";
    private static final String DISTINCTID = "distinctId";
    private static final String MARKETINGID = "marketingId";
    private static final String GOODSID = "goodsId";
    private static final String STOCK = "stock";
    private static final String BOSSSUMPRICE = "bossSumPrice";
    private static final String SUMPRICE = "sumPrice";
    //BOSS促销优惠金额
    private static final String BOSSPREPRICE = "bossPrePrice";

    /**
     * 110012
     */
    private static final String PROINFO = "110012";
    /**
     * thirds
     */
    private static final String THIRDS = "thirds";
    /**
     * marketinglist
     */
    private static final String MARKETINGLIST = "marketinglist";
    /**
     * shoplist
     */
    private static final String SHOPLIST = "shoplist";
    @Resource(name = "MarketingService")
    private MarketingService marketService;

    @Resource(name = "HsiteGoodsProductService")
    private GoodsProductService goodsProductService;

    @Resource(name = "MarketingMapper")
    private MarketingMapper marketingMapper;

    @Resource(name = "FreightTemplateMapper")
    private FreightTemplateMapper freightTemplateMapper;

    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "GoodsGroupService")
    private GoodsGroupService goodsGroupService;

    @Resource(name = "HsiteGoodsProductMapper")
    private GoodsProductMapper goodsProductMapper;

    // 货品库存表
    @Resource(name = "ProductWareMapper")
    private ProductWareMapper productWareMapper;

    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;

    @Resource(name = "ShoppingCartMapper")
    private ShoppingCartMapper shoppingCartMapper;

    @Resource(name = "OrderService")
    private OrderService orderser;

    @Resource(name = "DefaultAddressService")
    private DefaultAddressService addressService;

    @Resource(name = "DistrictService")
    private DistrictService districtService;

    @Resource(name = "PreDiscountMarketingMapper")
    private PreDiscountMarketingMapper preDiscountMarketingMapper;

    @Resource(name = "FreightExpressMapper")
    private FreightExpressMapper freightExpressMapper;

    @Resource(name = "customerServiceM")
    private CustomerService customerService;

    @Resource(name = "RushCustomerMapper")
    private RushCustomerMapper rushCustomerMapper;

    /**
     * 满赠赠品范围
     */
    @Resource(name = "fullbuyPresentScopeMapper")
    private FullbuyPresentScopeMapper fullbuyPresentScopeMapper;


    /**
     * 会员积分接口
     */
    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;

    /**
     * 删除购物车商品
     *
     * @param shoppingCartId
     * @param goodsInfoId
     * @param shoppingCartId
     * @return int
     */
    @Override
    public int delShoppingCartById(Long shoppingCartId, Long goodsInfoId, HttpServletRequest request, HttpServletResponse response) {
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        if (customerId != null) {
            return shoppingCartMapper.delShoppingCartById(shoppingCartId);
        } else {
            // 调用cookie删除接
            try {
                delCookShopCar(goodsInfoId, request, response);
            } catch (UnsupportedEncodingException e) {
                Customer cust = (Customer) request.getSession().getAttribute("cust");
                OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
            }
            return 1;
        }
    }

    /**
     * 修改购买数量
     *
     * @param shoppingCartId
     * @param num
     * @return int
     */
    @Override
    public int changeShoppingCartById(Long shoppingCartId, Long num) {
        Long numNew = num;
        ShoppingCart sc = new ShoppingCart();
        sc.setShoppingCartId(shoppingCartId);
        if (numNew != null && numNew == 0L) {
            numNew = 1L;
        }
        sc.setGoodsNum(numNew);
        return shoppingCartMapper.changeShoppingCartById(sc);
    }

    /**
     * 修改优惠
     *
     * @param shoppingCartId      购物车id
     * @param marketingId         单品优惠分组id
     * @param marketingActivityId 活动优惠分组id
     * @return int
     */
    @Override
    public int changeShoppingCartMarket(Long shoppingCartId, Long marketingId, Long marketingActivityId) {
        Long marketingIdNew = marketingId;
        Long marketingActivityIdNew = marketingActivityId;
        ShoppingCart sc = new ShoppingCart();

        sc.setShoppingCartId(shoppingCartId);
        // 如果单品优惠id为0，则为不选中单品优惠
        if (marketingIdNew == 0) {
            marketingIdNew = null;
        }
        sc.setMarketingId(marketingIdNew);
        // 如果活动优惠id为0，则为不选活动优惠
        if (marketingActivityIdNew == 0) {
            marketingActivityIdNew = null;
        }
        sc.setMarketingActivityId(marketingActivityIdNew);
        return shoppingCartMapper.changeShoppingCartMarket(sc);
    }

    /**
     * 修改优惠
     *
     * @param shoppingCartId      购物车id
     * @param marketingId         单品优惠分组id
     * @param marketingActivityId 活动优惠分组id
     * @return int
     */
    @Override
    public int changeShoppingCartMarket(Long shoppingCartId, Long marketingId, Long marketingActivityId, HttpServletRequest request, HttpServletResponse response) {
        Long marketingIdNew = marketingId;
        Long marketingActivityIdNew = marketingActivityId;
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);

        // 判断用户是否登录
        if (customerId != null) {
            ShoppingCart sc = new ShoppingCart();

            sc.setShoppingCartId(shoppingCartId);
            // 如果单品优惠id为0，则为不选中单品优惠
            if (marketingIdNew != null && marketingIdNew == 0) {
                marketingIdNew = null;
            }
            sc.setMarketingId(marketingIdNew);
            // 如果活动优惠id为0，则为不选活动优惠
            if (marketingActivityIdNew != null && marketingActivityIdNew == 0) {
                marketingActivityIdNew = null;
            }
            sc.setMarketingActivityId(marketingActivityIdNew);
            return shoppingCartMapper.changeShoppingCartMarket(sc);
        } else {
            Cookie[] cookies = request.getCookies();

            StringBuilder newMid = new StringBuilder();

            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (cookie != null && NPSTORE_MID.equals(cookie.getName()) && cookie.getValue() != null && !"".equals(cookie.getValue())) {
                        String[] mIds = cookie.getValue().split("-");
                        // 取得所有cookie
                        for (String mId : mIds) {
                            String[] mid = mId.split("e");
                            // 判断是否该商品
                            if (mid[0] != null) {
                                if (mid[0].equals(shoppingCartId.toString())) {
                                    newMid.append(shoppingCartId);
                                    newMid.append("e");
                                    newMid.append(marketingIdNew);
                                    newMid.append("e");
                                    newMid.append(marketingActivityIdNew);
                                    newMid.append("e");
                                    newMid.append("1");
                                    newMid.append("-");
                                } else {
                                    newMid.append(mId);
                                    newMid.append("-");
                                }
                            }
                        }
                    }
                }
                Cookie cookie = new Cookie(NPSTORE_MID, newMid.toString());
                cookie.setMaxAge(15 * 24 * 3600);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return 0;
        }

    }

    /**
     * 查询购物车购买的商品信息
     *
     * @param request
     * @param box
     * @return List
     */
    @Override
    public List<ShoppingCart> searchByProduct(HttpServletRequest request, Long[] box) {
        List<Long> list = new ArrayList<Long>();
        if (box != null && box.length != 0) {
            for (Long bo : box) {
                list.add(bo);
            }
        }
        List<ShoppingCart> shoplist = shoppingCartMapper.shoppingCartListByIds(list);

        for (ShoppingCart cart : shoplist) {
            // 判断是否是套装
            if (cart.getFitId() == null) {
                // 如果是套装，设置商品
                cart.setGoodsDetailBean(goodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(), cart.getDistinctId()));

                // 如果是套装，设置优惠
                Marketing marketing = null;
                boolean isT = true;
                // 设置单品优惠
                if (cart.getMarketingId() != null && cart.getMarketingId() > 0) {
                    marketing = marketService.marketingDetail((cart).getMarketingId());
                    isT = false;
                }
                // 设置活动优惠
                if (cart.getMarketingActivityId() != null && cart.getMarketingActivityId() > 0) {
                    marketing = marketService.marketingDetailByActive(marketing, (cart).getMarketingActivityId(), isT);
                }
                cart.setMarketing(marketing);

            } else {
                // 进入此处标示该购物车为套装，执行套装方法
                GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());
                cart.setGoodsGroupVo(goodsGroupVo);
            }

        }

        return shoplist;

    }

    /**
     * KKK mobile 添加购物车
     *
     * @param shoppingCart
     * @return int
     * @throws UnsupportedEncodingException
     */
    @Override
    @Transactional
    public int addShoppingCart(ShoppingCart shoppingCart, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Long custId = (Long) request.getSession().getAttribute(CUSTOMERID);
        if (null != custId) {
            return addCart(shoppingCart, custId);
        } else {
            return addCart2Cookie(shoppingCart, request, response);
        }
    }

    /**
     * KKK mobile 添加套装到购物车
     *
     * @param shoppingCart
     * @return int
     * @throws UnsupportedEncodingException
     */
    @Override
    @Transactional
    public int addFit2Cart(ShoppingCart shoppingCart, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return addShoppingCart(shoppingCart, request, response);
    }

    private int addCart2Cookie(ShoppingCart shoppingCart, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String numStr = "";
        List<Cookie> cookies = KCollection.defaultList(request.getCookies());
        String cartStr = "";
        String mId = "";
        Long goodsInfoId = shoppingCart.getGoodsInfoId();
        Long districtId = shoppingCart.getDistinctId();
        for (Cookie cookie : cookies) {
            if (NPSTORE_SHOPCAR.equals(cookie.getName())) {
                cartStr = URLDecoder.decode(cookie.getValue(), ConstantUtil.UTF);

                final String goodsInfoIdStr = "," + goodsInfoId + "-";

                if (cartStr.contains(goodsInfoIdStr)) {
                    int beginIndex = cartStr.indexOf(goodsInfoIdStr);
                    numStr = cartStr.substring(beginIndex,
                            beginIndex + cartStr.substring(beginIndex, cartStr.length() - 1).indexOf("&")
                    );
                    numStr = numStr.substring(numStr.indexOf("-") + 1, numStr.length());

                    final String s2 = goodsInfoIdStr + numStr + "&" + districtId;
                    cartStr = cartStr.replace(s2 + "e", "");
                    if (cartStr.contains(s2)) {
                        cartStr = cartStr.replace(s2, "");

                    }
                }
            } else if (NPSTORE_MID.equals(cookie.getName())
                    && !"".equals(cookie.getValue())) {
                String[] mIds = cookie.getValue().split("-");
                // 取得所有cookie
                for (String mId1 : mIds) {
                    String[] mid = mId1.split("e");
                    // 判断是否该商品
                    if (mid[0] != null && "".equals(mIds[0]) && !mid[0].equals(goodsInfoId.toString())) {
                        mId = cookie.getValue();
                    }
                }
            }

        }
        long num = numStr.equals("") ? 0 : Long.valueOf(numStr);
        numStr = String.valueOf(num + shoppingCart.getGoodsNum());

        cartStr += "," + goodsInfoId + "-" + numStr + "&" + districtId + "e";
        if (shoppingCart.getFitId() == null) {
            GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailBeanByProductId(goodsInfoId, Long.parseLong("0"));
            Long cateId = goodsDetailBean.getProductVo().getGoods().getCatId();
            // 单品优惠id
            Long brandId = goodsDetailBean.getBrand().getBrandId();
            Long marketId = marketService.queryByCreatimeMarketings(goodsInfoId, 1L, cateId, brandId);

            // 查询抢购
            Long activeMarketId = marketService.queryByCreatimeMarketings(goodsInfoId, 5L, cateId,
                    brandId);

            if (activeMarketId == 0) {
                activeMarketId = marketService.queryByCreatimeMarketings(goodsInfoId, 2L, cateId,
                        brandId);
            }

            mId += "-" + goodsInfoId + "e" + marketId + "e" + activeMarketId + "e" + "0";
        }

        Cookie cook = new Cookie(NPSTORE_SHOPCAR, URLEncoder.encode(cartStr, ConstantUtil.UTF));
        cook.setMaxAge(15 * 24 * 3600);
        cook.setPath("/");
        response.addCookie(cook);
        Cookie cookie = new Cookie(NPSTORE_MID, mId);
        cookie.setMaxAge(15 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return 1;
    }

    private int addCart(ShoppingCart shoppingCart, Long custId) {
        int sum = shoppingCartMapper.selectSumByCustomerId(custId);
        if (sum >= 20) {
            return -1;
        }
        shoppingCart.setCustomerId(custId);
        shoppingCart.setDelFlag("0");
        shoppingCart.setShoppingCartTime(new Date());
        int count = shoppingCartMapper.selectCountByReady(shoppingCart);

        if (count == 0) {
            if (shoppingCart.getFitId() == null) {
                //goodsDetailBean = goodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(), Long.parseLong("0"));
//                GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailByProductId(shoppingCart.getGoodsInfoId(), Long.parseLong("0"));
                Long goodsInfoId = shoppingCart.getGoodsInfoId();
                GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailByProductId(goodsInfoId, shoppingCart.getDistinctId() != null ? shoppingCart.getDistinctId() : Long.parseLong("0"));
                if (goodsDetailBean != null) {
                    // 活动优惠id
                    Long brandId = goodsDetailBean.getBrand().getBrandId();
                    Long catId = goodsDetailBean.getProductVo().getGoods().getCatId();
                    List<Marketing> markList = marketService.selectMarketingByGoodsInfoId(goodsInfoId, brandId, catId);

                    // 单品折扣促销只能有一个
                    for (Marketing mark : markList) {
                        // 判断是否有单品优惠
                        if ("15".equals(mark.getCodexType())) {
                            Long marketId = mark.getMarketingId();
                            shoppingCart.setMarketingId(marketId);
                            break;
                        }
                    }

                    // 判断是否有活动优惠
                    Long activeMarketId = marketService.queryByCreatimeMarketingsWithPanicBuying(goodsInfoId, catId, brandId);
                    if (activeMarketId != 0) {
                        shoppingCart.setMarketingActivityId(activeMarketId);
                    }

                }

            }
            return shoppingCartMapper.addShoppingCart(shoppingCart);
        } else {

            ShoppingCart sc = shoppingCartMapper.selectShopingByParam(shoppingCart);
            // 限购
            if (shoppingCart.getFitId() == null) {
                if (limtShopping(sc, custId) == 0) {
                    return 0;
                }
            }

            if (sc.getGoodsNum() == 99) {
                return 0;
            }
            return shoppingCartMapper.updateShoppingCart(shoppingCart);

        }
    }

    /**
     * 分页购物
     */
    public int limtShopping(ShoppingCart shoppingCart, Long custId) {
        // 进入此处标示该购物车为商品，执行商品方法
        GoodsDetailBean gb = goodsProductService.queryDetailByProductId(shoppingCart.getGoodsInfoId(), shoppingCart.getDistinctId());
        if (gb == null) {
            return 0;
        }
        GoodsProductVo productVo = gb.getProductVo();

        if (productVo == null) {
            return 0;
        } else {
            // 限购
            Marketing mk = marketService.marketingDetail(marketService.queryByCreatimeMarketings(productVo.getGoodsInfoId(), 3L, productVo.getGoods().getCatId(), gb
                    .getBrand().getBrandId()));
            // 存在限购
            if (mk != null) {
                Long stock = mk.getLimitBuyMarketing().getLimitCount();
                // 最近是否购买过该商品
                Long num = orderser.selectGoodsInfoCount(shoppingCart.getGoodsInfoId(), custId, mk.getMarketingBegin());
                if (num != null && stock - num - shoppingCart.getGoodsNum() <= 0) {
                    return 0;
                }
            }
            return 1;
        }
    }

    /**
     * 加载cookie中的购物车信息
     *
     * @return 加载好的列表
     * @throws UnsupportedEncodingException
     */
    public List<ShopCarUtil> loadCookShopCar(HttpServletRequest request) throws UnsupportedEncodingException {
        List<ShopCarUtil> list = new ArrayList<>();
        List<Cookie> cookies = KCollection.defaultList(request.getCookies());
        boolean checkExists = false;
        for (Cookie cookie : cookies) {
            if (null != cookie && NPSTORE_SHOPCAR.equals(cookie.getName()) && cookie.getValue() != null && !"".equals(cookie.getValue())) {
                String oldCar = URLDecoder.decode(cookie.getValue(), ConstantUtil.UTF);
                oldCar = oldCar.substring(1, oldCar.length());
                oldCar = oldCar.substring(0, oldCar.length() - 1);
                String[] cars = oldCar.split("e,");
                for (String car1 : cars) {
                    String[] car = car1.split("-");
                    ShopCarUtil carUtil = new ShopCarUtil();
                    carUtil.setProductId(Long.parseLong(car[0]));

                    /* 如果符合套装的规则,说明是套装,否则就是货品 */
                    if (car[0].length() > 6 && CODE001.equals(car[0].substring(0, 6))) {
                        carUtil.setFitId(Long.parseLong(car[0].substring(6, car[0].length())));
                    } else {
                        for (Cookie cook : cookies) {
                            // 设置优惠
                            if (cook != null && NPSTORE_MID.equals(cook.getName()) && cook.getValue() != null && !"".equals(cook.getValue())) {
                                String[] mIds = cook.getValue().split("-");
                                // 取得所有cookie
                                for (int j = 0; j < mIds.length; j++) {
                                    String[] mid = mIds[j].split("e");
                                    // 判断是否该商品
                                    if (mid[0] != null && car[0].equals(mid[0])) {
                                        if (mid[1] != null && !"null".equals(mid[1])) {
                                            carUtil.setMarketId(Long.parseLong(mid[1]));
                                            carUtil.setMarketActiveId(Long.parseLong(mid[2]));
                                            carUtil.setStatus(Long.parseLong(mid[3]));
                                        }

                                    }
                                }
                            }
                        }
                    }

                    String[] car2 = car[1].split("&");
                    carUtil.setGoodsNum(Integer.parseInt(car2[0]));
                    if(!car2[1].equals("null")){
                        carUtil.setDistinctId(Long.parseLong(car2[1]));
                    }
                    for (ShopCarUtil aList : list) {
                        if (aList.getProductId().equals(carUtil.getProductId())) {
                            checkExists = true;
                        }
                    }
                    if (!checkExists) {
                        list.add(carUtil);
                        checkExists = false;
                    }

                }

            }

        }
        return list;
    }

    /**
     * 删除cookie中的购物车数据
     *
     * @param request  请求对象
     * @param response 相应对象
     * @return 删除的数量
     * @throws UnsupportedEncodingException
     */
    public int delCookShopCar(Long productId, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Integer count = 0;
        Cookie[] cookies = request.getCookies();
        String oldCar = "";
        String[] cars = null;
        String[] car = null;
        Cookie cook;
        String newMid = "";
        StringBuilder bufOldCar = new StringBuilder();
        StringBuilder bufNewMid = new StringBuilder();

        for (Cookie cookie : cookies) {
            if (null != cookie && NPSTORE_SHOPCAR.equals(cookie.getName())) {
                oldCar = URLDecoder.decode(cookie.getValue(), ConstantUtil.UTF);
                if (oldCar.contains("," + productId + "-")) {
                    oldCar = oldCar.substring(1, oldCar.length());
                    oldCar = oldCar.substring(0, oldCar.length() - 1);
                    cars = oldCar.split("e,");
                    oldCar = "";
                    for (int j = 0; j < cars.length; j++) {
                        car = cars[j].split("-");
                        if (!car[0].equals(productId.toString())) {
                            bufOldCar.append(oldCar);
                            bufOldCar.append(",");
                            bufOldCar.append(car[0]);
                            bufOldCar.append("-");
                            bufOldCar.append(car[1]);
                            bufOldCar.append("e");
                            oldCar += bufOldCar.toString();
                        }
                    }
                }
            }
            if (cookie != null && NPSTORE_MID.equals(cookie.getName()) && cookie.getValue() != null && !"".equals(cookie.getValue())) {
                String[] mIds = cookie.getValue().split("-");
                // 取得所有cookie
                for (int j = 0; j < mIds.length; j++) {
                    String[] mid = mIds[j].split("e");
                    // 判断是否该商品
                    if (mid[0] != null && !mid[0].equals(productId.toString())) {
                        bufNewMid.append(mIds[j]);
                        bufNewMid.append("-");
                        newMid += bufNewMid.toString();
                    }
                }
            }

        }
        cook = new Cookie(NPSTORE_SHOPCAR, URLEncoder.encode(oldCar, ConstantUtil.UTF));
        cook.setMaxAge(15 * 24 * 3600);
        cook.setPath("/");
        response.addCookie(cook);
        Cookie cookie = new Cookie(NPSTORE_MID, URLEncoder.encode(newMid, ConstantUtil.UTF));
        cookie.setMaxAge(15 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        return count;

    }

    /*
     * 
     * @see com.ningpai.site.shoppingcart.service.ShoppingCartService#
     * deleteShoppingCartByIds(javax.servlet.http .HttpServletRequest,
     * java.lang.Long[])
     */
    @Override
    public int deleteShoppingCartByIds(HttpServletRequest request, Long[] shoppingCartId) {
        List<Long> list = new ArrayList<Long>();
        if (shoppingCartId.length != 0) {
            for (Long bo : shoppingCartId) {
                list.add(bo);
            }
        }
        return shoppingCartMapper.deleteShoppingCartByIds(list);
    }

    /**
     * 从cook添加到购物车
     *
     * @param request
     * @return int
     */
    @Override
    public int loadCoodeShopping(HttpServletRequest request) {
        Long custId = (Long) request.getSession().getAttribute(CUSTOMERID);
        List<ShopCarUtil> list = null;
        try {
            list = loadCookShopCar(request);
        } catch (UnsupportedEncodingException e) {
            Customer cust = (Customer) request.getSession().getAttribute("cust");
            OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
        }
        if (list != null && !list.isEmpty()) {
            for (ShopCarUtil su : list) {
                ShoppingCart shoppingCart = new ShoppingCart();
                if (su.getFitId() == null) {
                    shoppingCart.setGoodsInfoId(su.getProductId());
                } else {
                    // 设置套装商品id
                    shoppingCart.setGoodsInfoId(Long.parseLong(CODE001 + su.getFitId()));
                    // 设置套装id
                    shoppingCart.setFitId(su.getFitId());
                }
                shoppingCart.setCustomerId(custId);
                shoppingCart.setDelFlag("0");
                shoppingCart.setShoppingCartTime(new Date());
                shoppingCart.setGoodsNum(Long.valueOf(su.getGoodsNum()));
                shoppingCart.setMarketingId(su.getMarketId());
                shoppingCart.setMarketingActivityId(su.getMarketActiveId());
                shoppingCart.setDistinctId(Long.valueOf(su.getDistinctId()));
                int count = shoppingCartMapper.selectCountByReady(shoppingCart);
                if (count == 0) {
                    shoppingCartMapper.addShoppingCart(shoppingCart);
                } else {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("customerId", custId);
                    List<ShoppingCart> scList = shoppingCartMapper.shoppingCart(paramMap);
                    for (ShoppingCart sc : scList) {
                        if (shoppingCart.getGoodsInfoId().toString().equals(sc.getGoodsInfoId().toString())) {
                            sc.setGoodsNum(1L);
                            shoppingCartMapper.updateShoppingCart(sc);
                        }
                    }
                }
            }

        }

        return 1;
    }

    /**
     * 修改要选中的订单优惠
     *
     * @param cart
     * @return 结果
     */
    @Override
    public int changeShoppingCartOrderMarket(ShoppingCart cart) {
        shoppingCartMapper.changeShoppingCartOrderMarket(cart);
        return 0;
    }

    @Override
    /**
     * 查询购物车内容
     * @return
     */
    public PageBean selectShoppingCart(HttpServletRequest request, ShoppingCartWareUtil cartWareUtil, PageBean pb, HttpServletResponse response) {
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        // 如果已经登录
        Long districtId = cartWareUtil.getDistrictId();
        if (customerId != null) {
            pb.setUrl("myshoppingcart");
            pb.setPageSize(20);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(CUSTOMERID, customerId);
            Integer totalCount = shoppingCartMapper.shoppingCartCount(paramMap);
            pb.setRows(totalCount);
            if (pb.getPageNo() > pb.getLastPageNo()) {
                pb.setPageNo(pb.getLastPageNo());
            }
            if (pb.getPageNo() == 0) {
                pb.setPageNo(1);
            }
            paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
            paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
            List<ShoppingCart> shoplist = shoppingCartMapper.shoppingCart(paramMap);
            for (ShoppingCart cart : shoplist) {
                // 进入此处标示该购物车为商品，执行商品方法
                GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(),
                        districtId);
                try {
                    if (goodsDetailBean == null) {
                        continue;
                    }
                    cart.setGoodsDetailBean(goodsDetailBean);
                    // 限购
                    Marketing marketing = marketService.marketingDetail(marketService.queryByCreatimeMarketings(goodsDetailBean.getProductVo().getGoodsInfoId(), 3L,
                            goodsDetailBean.getProductVo().getGoods().getCatId(), goodsDetailBean.getBrand().getBrandId()));
                    // 存在限购
                    if (marketing != null) {
                        Long stock = marketing.getLimitBuyMarketing().getLimitCount();
                        // 最近是否购买过该商品
                        Long num = orderser.selectGoodsInfoCount(goodsDetailBean.getProductVo().getGoodsInfoId(), customerId,
                                marketing.getMarketingBegin());
                        if (num != null) {
                            stock = stock - num;
                            if (stock < 0) {
                                stock = 0L;
                            }
                        }
                        if ((cart).getGoodsDetailBean().getProductVo().getGoodsInfoStock() - stock >= 0) {
                            // 如果存在限购，则把库存改为限购数量
                            goodsDetailBean.getProductVo().setGoodsInfoStock(stock);

                        }
                    }

                } catch (Exception e) {
                    LOGGER.error("", e);
                    // 如果商品已经从数据库中删除，则把该商品从购物车内删除
                    shoppingCartMapper.delShoppingCartById(cart.getShoppingCartId());
                }

                // 查询促销
                if (cart.getFitId() == null) {
                    cart.setMarketingList(marketService.selectMarketingByGoodsInfoId(goodsDetailBean
                            .getProductVo().getGoodsInfoId(), goodsDetailBean.getBrand().getBrandId(), goodsDetailBean.getProductVo().getGoods().getCatId()));
                }

                if (cart.getFitId() == null) {
                    Marketing marketing = null;
                    boolean isT = true;
                    // 设置单品优惠
                    if (cart.getMarketingId() != null) {
                        marketing = marketService.marketingDetail(cart.getMarketingId());
                        isT = false;
                    }
                    // 设置活动优惠
                    if (cart.getMarketingActivityId() != null) {
                        marketing = marketService.marketingDetailByActive(marketing, cart.getMarketingActivityId(), isT);
                    }
                    cart.setMarketing(marketing);
                }

            }
            pb.setList(new ArrayList<Object>(shoplist));

        } else {
            // 如果未登录状态获取购物车
            try {
                List<ShopCarUtil> list = loadCookShopCar(request);
                if (list != null && !list.isEmpty()) {
                    pb.setUrl("myshoppingcart");
                    pb.setPageSize(5);

                    Integer totalCount = 1;// list.size();

                    pb.setRows(totalCount);
                    if (pb.getPageNo() > pb.getLastPageNo()) {
                        pb.setPageNo(pb.getLastPageNo());
                    }
                    if (pb.getPageNo() == 0) {
                        pb.setPageNo(1);
                    }

                    List<ShoppingCart> shoplist = new ArrayList<>();
                    for (ShopCarUtil aList : list) {// list.size();
                        ShoppingCart sc = new ShoppingCart();
                        sc.setGoodsInfoId(aList.getProductId());
                        sc.setMarketingId(aList.getMarketId());
                        sc.setMarketingActivityId(aList.getMarketActiveId());
                        sc.setGoodsNum(Long.valueOf(aList.getGoodsNum()));
                        sc.setMarketing(null);
                        sc.setDistinctId(aList.getDistinctId());
                        shoplist.add(sc);
                    }

                    for (ShoppingCart cart : shoplist) {
                        Object sessionStatus = request.getSession().getAttribute(NPSTORE_SHOPSTATUS);
                        boolean bool = true;
                        if (sessionStatus != null) {

                            String[] status = (sessionStatus.toString()).split("-");
                            for (String str : status) {
                                String[] shoppingStatus = str.split("e");
                                if (shoppingStatus[1].equals(cart.getShoppingCartId().toString())) {
                                    cart.setShoppingStatus(shoppingStatus[0]);
                                    bool = false;
                                }
                            }
                        } else {
                            sessionStatus = "1" + "e" + cart.getShoppingCartId() + "-";
                            cart.setShoppingStatus("1");
                        }

                        if (bool) {
                            sessionStatus = "1" + "e" + cart.getShoppingCartId() + "-" + sessionStatus;
                            cart.setShoppingStatus("1");
                        }
                        request.getSession().setAttribute(NPSTORE_SHOPSTATUS, sessionStatus);
                        GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(),
                                districtId);
                        if (goodsDetailBean == null) {
                            continue;
                        }
                        cart.setGoodsDetailBean(goodsDetailBean);
                        // 限购
                        Marketing marketing = marketService.marketingDetail(marketService.queryByCreatimeMarketings(goodsDetailBean.getProductVo().getGoodsInfoId(), 3L,
                                goodsDetailBean.getProductVo().getGoods().getCatId(), goodsDetailBean.getBrand().getBrandId()));
                        // 存在限购
                        if (marketing != null) {
                            Long stock = marketing.getLimitBuyMarketing().getLimitCount();
                            // 最近是否购买过该商品
                            Long num = orderser.selectGoodsInfoCount(cart.getGoodsDetailBean().getProductVo().getGoodsInfoId(), null,
                                    marketing.getMarketingBegin());
                            if (num != null) {
                                stock = stock - num;
                                if (stock < 0) {
                                    stock = 0L;
                                }
                            }
                            if (cart.getGoodsDetailBean().getProductVo().getGoodsInfoStock() - stock >= 0) {
                                // 如果存在限购，则把库存改为限购数量
                                cart.getGoodsDetailBean().getProductVo().setGoodsInfoStock(stock);
                                cart.setMarketingList(marketService.selectMarketingByGoodsInfoId(cart
                                                .getGoodsDetailBean().getProductVo().getGoodsInfoId(), cart.getGoodsDetailBean().getBrand().getBrandId(),
                                        cart.getGoodsDetailBean().getProductVo().getGoods().getCatId()));
                            }
                        }

                        if (cart.getFitId() == null) {
                            Marketing market = null;
                            boolean isT = true;
                            // 设置单品优惠
                            if (cart.getMarketingId() != null) {
                                market = marketService.marketingDetail(cart.getMarketingId());
                                isT = false;
                            }
                            // 设置活动优惠
                            if (cart.getMarketingActivityId() != null) {
                                market = marketService.marketingDetailByActive(marketing, cart.getMarketingActivityId(), isT);
                            }
                            cart.setMarketing(market);
                        }
                    }

                    pb.setList(new ArrayList<Object>(shoplist));

                }

            } catch (UnsupportedEncodingException e) {
                Customer cust = (Customer) request.getSession().getAttribute("cust");
                OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
            }
        }
        return pb;
    }

    /**
     * 获取商品库存
     *
     * @deprecated
     */
    public void getGoodsStock(ShoppingCartWareUtil cartWareUtil, List<Object> shoplist, int i) {
        // 判断是否存在
        // 查询库存
        ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(
                ((ShoppingCart) shoplist.get(i)).getGoodsDetailBean().getProductVo().getGoodsInfoId(), cartWareUtil.getDistrictId());

        if (productWare != null) {
            // 设置商品库存
            ((ShoppingCart) shoplist.get(i)).getGoodsDetailBean().getProductVo().setGoodsInfoStock(productWare.getWareStock());
            ((ShoppingCart) shoplist.get(i)).getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(productWare.getWarePrice());
        } else {
            // 如果没有，则设置库存为0
            ((ShoppingCart) shoplist.get(i)).getGoodsDetailBean().getProductVo().setGoodsInfoStock(ShopCartValueUtil.WARECOUNT);
        }
    }



    /**
     * 更改商品选中状态
     *
     * @param shoppingId 购物车id
     * @param status     要修改的状态
     * @param request
     * @param response
     * @return
     */
    @Override
    public String changeShopStatus(Long shoppingId, String status, HttpServletRequest request, HttpServletResponse response) {
        // 获取当前选中状态的session
        Object obj = request.getSession().getAttribute(NPSTORE_SHOPSTATUS);
        String newStr = "";
        StringBuilder buf = new StringBuilder();

        // 判断session是否有值
        if (obj != null) {
            // 获取所有的购物车状态
            String[] strs = (obj.toString()).split("-");
            for (String str : strs) {
                // 根据标示分割字符，获取这个购物车商品内的id和状态;
                String[] shopStatus = str.split("e");
                if (shopStatus[1].equals(shoppingId.toString())) {
                    buf.append(status);
                    buf.append("e");
                    buf.append(shoppingId);
                    buf.append("-");
                    buf.append(newStr);
                } else {
                    buf.append(str);
                    buf.append("-");
                }
                newStr += buf.toString();
            }
        }
        request.getSession().setAttribute(NPSTORE_SHOPSTATUS, newStr);

        return status;
    }

    /**
     * 批量更改商品选中状态
     *
     * @param shoppingId 购物车id
     * @param status     要修改的状态
     * @param request
     * @param response
     * @return
     */
    @Override
    public String changeShopStatusByParam(Long[] shoppingId, String status, HttpServletRequest request, HttpServletResponse response) {
        String newStr = "";
        StringBuilder buf = new StringBuilder();
        for (Long id : shoppingId) {
            buf.append(status);
            buf.append("e");
            buf.append(id);
            buf.append("-");
            newStr += buf.toString();
        }
        request.getSession().setAttribute(NPSTORE_SHOPSTATUS, newStr);
        return status;
    }

    /**
     * 移除相同的促销
     *
     * @param shopplist
     * @return
     */
    public List<Marketing> marketingIdsListUtil(List<ShoppingCart> shopplist) {
        List<Marketing> list = new ArrayList<>();
        // 把所购买的所有促销全部放进list里面
        for (ShoppingCart cart : shopplist) {
            List<Marketing> marketingList = KCollection.defaultList(cart.getMarketingList());
            for (Marketing marketing : marketingList) {
                // 排除包邮促销和折扣促销
                if (!"15".equals(marketing.getCodexType()) && !"12".equals(marketing.getCodexType())) {
                    list.add(marketing);
                }
            }
        }


        // 移除重复的促销
        for (int q = 0; q < list.size() - 1; q++) {
            for (int p = list.size() - 1; p > q; p--) {
                if (list.get(p).getMarketingId().equals(list.get(q).getMarketingId())) {
                    list.remove(p);
                }
            }

        }

        return list;
    }

    /**
     * 得到可以使用的优惠券
     *
     * @param request
     * @param box
     * @return
     * fixme replace(request,customerId)
     */
    @Override
    public List<Coupon> getUsedCouponlist(HttpServletRequest request, Long[] box) {
        // 查询所有购物车商品
        List<ShoppingCart> cartList = KCollection.defaultList(shoppingCartMapper.shopCartListByIds(Arrays.asList(box)));
        List<ShoppingCart> noFitList = new ArrayList<>();
        List<ParamIds> infoIds = new LinkedList<>();
        // 去除套装,套装不允许使用优惠券
        for (ShoppingCart cart : cartList) {
            if (cart.getFitId() == null) {
                noFitList.add(cart);
            }
        }
        if (CollectionUtils.isEmpty(noFitList)) {
            return Collections.emptyList();
        }


        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        // 当前购物车所拥有的购物券
        List<Coupon> couponList = null;
        // 过滤购物券得到满足条件优惠券
        List<Coupon> userdCoupon = new ArrayList<>();

        // 商家分组后的中间变量金额
        BigDecimal sumprice = BigDecimal.ZERO;

        // 第三方id的map
        Map<Long, Object> thirdIdMap = new HashMap<>();
        Map<String, Object> para = new HashMap<>();
        for (ShoppingCart cart : noFitList) {

            thirdIdMap.put(cart.getThirdId(), "");
            GoodsDetailBean detailBean = goodsProductService.queryDetailByProductId(cart.getGoodsInfoId(),
                    cart.getDistinctId());
            // 查询商品详细
            // 查询购物车里选择的促销信息
            // noFitList.get(i).setMarketing(marketService.marketingDetail(noFitList.get(i).getMarketingActivityId
            // (), noFitList.get(i).getGoodsInfoId()));

            ParamIds p = new ParamIds();
            GoodsProductVo productVo = detailBean.getProductVo();
            p.setCouponRangeFkId(productVo.getGoodsInfoId());
            p.setCouponRangeType("2");
            infoIds.add(p);
            ParamIds p1 = new ParamIds();
            p1.setCouponRangeFkId(detailBean.getBrand().getBrandId());
            p1.setCouponRangeType("1");
            infoIds.add(p1);

            ParamIds p2 = new ParamIds();
            p2.setCouponRangeFkId(productVo.getGoods().getCatId());
            p2.setCouponRangeType("0");
            infoIds.add(p2);

            // 货品价格
            // 货品价格判断是否参与折扣
            BigDecimal goodsprice = productVo.getGoodsInfoPreferPrice();
            // 折扣促销
            if (cart.getMarketingId() != null && 0 != cart.getMarketingId()) {
                // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                Marketing mark = marketService.marketingDetail(cart.getMarketingId(), cart.getGoodsInfoId());
                if (mark != null) {
                    para.put(MARKETINGID, mark.getMarketingId());
                    para.put(GOODSID, cart.getGoodsInfoId());
                    PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                    if (premark != null) {
                        // 货品价格
                        goodsprice = premark.getDiscountInfo().multiply(goodsprice);
                        productVo.setGoodsInfoPreferPrice(goodsprice);
                    }

                }

            }

            cart.setMarketing(marketService.marketingDetailNew(cart.getMarketingActivityId(), cart.getGoodsInfoId(), cart.getGoodsNum(), productVo.getGoodsInfoPreferPrice()));

            detailBean.setProductVo(productVo);
            cart.setGoodsDetailBean(detailBean);

        }

        //直降  满件满折  会员折扣
        // 当前登录成功的会员
        setMarketIngPrice(noFitList, customerId);
        if (CollectionUtils.isNotEmpty(infoIds)) {
            couponList = couponService.selectCouponListByIds(infoIds, request);
        }

        if (CollectionUtils.isNotEmpty(couponList)) {

            for (Coupon cou : couponList) {

                List<CouponRange> ranList = cou.getCouponrangList();
                for (CouponRange ran : ranList) {
                    for (ShoppingCart cart : noFitList) {
                        // 货品价格
                        GoodsProductVo productVo = cart.getGoodsDetailBean().getProductVo();
                        BigDecimal gp = productVo.getGoodsInfoPreferPrice();
                        if (cou.getCouponId().equals(ran.getCouponId())) {
                            // 按分类
                            if ("0".equals(ran.getCouponRangeType()) && ran.getCouponRangeFkId().equals(productVo.getGoods().getCatId())) {
                                // 商家的总金额
                                sumprice = sumprice.add(gp.multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                            }
                            // 按品牌
                            if ("1".equals(ran.getCouponRangeType()) && ran.getCouponRangeFkId().equals(cart.getGoodsDetailBean().getBrand().getBrandId())) {
                                // 商家的总金额
                                sumprice = sumprice.add(gp.multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                            }
                            // 按货品
                            Long goosd = cart.getGoodsInfoId();
                            if ("2".equals(ran.getCouponRangeType()) && ran.getCouponRangeFkId().equals(goosd)) {
                                // 商家的总金额
                                sumprice = sumprice.add(gp.multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                            }
                        }
                    }


                }
                // 满减
                if ("2".equals(cou.getCouponRulesType())) {
                    if (!userdCoupon.contains(cou)) {
                        // 满多少减金额
                        BigDecimal priceflag = cou.getCouponFullReduction().getFullPrice();
                        if (priceflag.compareTo(sumprice) == -1) {
                            userdCoupon.add(cou);
                        }
                    }
                } else {
                    BigDecimal downPrice = cou.getCouponStraightDown().getDownPrice();
                    // 直降的优惠券没有限制都允许
                    if (!userdCoupon.contains(cou)) {
                        if (downPrice.compareTo(sumprice) == -1) {
                            userdCoupon.add(cou);
                        }
                    }
                }
                sumprice = BigDecimal.ZERO;
            }

        }
        return userdCoupon;
    }

    /**
     * KKK 确认订单(去付款)
     * @param businessId
     * @param shopdata
     * @param distinctId
     * @return
     */
    @Override
    public Map<String, Object> getPayorderThirdPriceMap(Long businessId, List<ShoppingCart> shopdata, Long distinctId) {
        Map<String, Object> paramMap = new HashMap<>();


        paramMap.put("stockInfo", new ArrayList<GoodsStockBean>());

        // 1表示不同地区存在库存 0则表示库存不足直接跳到购物车
        paramMap.put(STOCK, "1");
        List<ShoppingCart> shoplist = new ArrayList<>();

        for (ShoppingCart cart : shopdata) {
            if (businessId.equals(cart.getThirdId())) {
                shoplist.add(cart);
            }
        }
        // 原始总金额
        BigDecimal sumOldPrice = BigDecimal.ZERO;
        // 优惠金额
        BigDecimal prePrice = BigDecimal.ZERO;
        // 中间变量
        BigDecimal flag = BigDecimal.ZERO;
        // boss总金额
        BigDecimal bossSumPrice = BigDecimal.ZERO;
        // 抢购总金额
        BigDecimal rushSumPrice = BigDecimal.ZERO;
        //boos促销优惠金额
        BigDecimal bossPrePrice = BigDecimal.ZERO;
        //抢购的优惠金额
        BigDecimal rushPrePrice = BigDecimal.ZERO;
        BigDecimal taozhuan = BigDecimal.ZERO;

        ProductWare productWare;
        Map<String, Object> para = new HashMap<>();
        if (CollectionUtils.isNotEmpty(shoplist)) {
            BigDecimal goodsprice = BigDecimal.ZERO;
            BigDecimal totalprice = BigDecimal.ZERO;

            GoodsStockBean stockBean = null;

            for (ShoppingCart cart : shoplist) {

                if (cart.getFitId() == null) {

                    // 货品价格
                    GoodsProductVo productVo = cart.getGoodsDetailBean().getProductVo();
                    goodsprice = productVo.getGoodsInfoPreferPrice();
                    //存在已下架的货品
                    if ("0".equals(productVo.getGoodsInfoAdded())) {
                        paramMap.put(STOCK, "0");
                    }
                    // 不同地区的货品价格
                    if (businessId == 0) {
                        if (null != distinctId && distinctId > 0) {
                            productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(cart.getGoodsInfoId(), distinctId);
                            // 不同地区的价格不同
                            if (null != productWare) {
                                //goodsprice = productWare.getWarePrice();
                                if (productWare.getWareStock() - cart.getGoodsNum() < 0) {
                                    paramMap.put(STOCK, "0");
                                    stockBean = new GoodsStockBean();
                                    stockBean.setGoodsId(cart.getGoodsInfoId());
                                    stockBean.setStockNum(String.valueOf(productWare.getWareStock()));
                                    List<GoodsStockBean> list = (List) paramMap.get("stockInfo");
                                    list.add(stockBean);
                                }
                            } else {
                                paramMap.put(STOCK, "0");
                                stockBean = new GoodsStockBean();
                                stockBean.setGoodsId(cart.getGoodsInfoId());
                                stockBean.setStockNum("0");
                                List<GoodsStockBean> list = (List) paramMap.get("stockInfo");
                                list.add(stockBean);
                            }
                        }
                    } else {
                        if (productVo.getGoodsInfoStock() - cart.getGoodsNum() < 0) {
                            // 第三方货存不足
                            paramMap.put("stock", "0");
                            stockBean = new GoodsStockBean();
                            stockBean.setGoodsId(cart.getGoodsInfoId());
                            stockBean.setStockNum(String.valueOf(productVo.getGoodsInfoStock()));
                            List<GoodsStockBean> list = (List) paramMap.get("stockInfo");
                            list.add(stockBean);
                        }
                    }
                    // 是否抹掉分角
                    String discountFlag = "";
                    // 折扣促销
                    if (cart.getMarketingId() != null && 0 != cart.getMarketingId()) {
                        // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                        Marketing mark = marketService.marketingDetail(cart.getMarketingId(), cart.getGoodsInfoId());
                        if (mark != null) {
                            para.put("marketingId", mark.getMarketingId());
                            para.put("goodsId", cart.getGoodsInfoId());
                            PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                            if (premark != null) {
                                discountFlag = premark.getDiscountFlag();
                            }
                        }

                    }

                    DecimalFormat myformat;
                    // 抹掉分
                    if ("1".equals(discountFlag)) {
                        myformat = new DecimalFormat("0.0");
                    } else if ("2".equals(discountFlag)) {
                        myformat = new DecimalFormat("0");
                    } else {
                        myformat = new DecimalFormat("0.00");
                    }
                    // 不四舍五入
                    myformat.setRoundingMode(RoundingMode.FLOOR);
                    goodsprice = BigDecimal.valueOf(Double.valueOf(myformat.format(goodsprice)));
                    // 处理货品的价格()
                    productVo.setGoodsInfoPreferPrice(goodsprice);
                    // 货品购买件数
                    Long goodssum = cart.getGoodsNum();
                    // 计算boss价格页面计算用
                    if (cart.getThirdId() == 0) {
                        bossSumPrice = bossSumPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    }
                    // 计算原始总金额
                    sumOldPrice = sumOldPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    flag = flag.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                }
                else{
                    // 如果商品是套装
                    GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());
                    if (null != goodsGroupVo) {

                        cart.setGoodsGroupVo(goodsGroupVo);
                        // 该套装下所有的商品
                        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = cart.getGoodsGroupVo().getProductList();
                        // 获取此套装下的所有货品
                        for (int j = 0; j < goodsGroupReleProductVos.size(); j++) {
                            GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailBeanByProductId(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoId(),
                                    distinctId);
                            goodsGroupReleProductVos.get(j).getProductDetail().setGoodsInfoPreferPrice(goodsDetailBean.getProductVo().getGoodsInfoPreferPrice());
                            // 原总金额加上套装优惠前费用
                            sumOldPrice = sumOldPrice.add(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoPreferPrice()
                                    .multiply(BigDecimal.valueOf(cart.getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVos.get(j).getProductNum())));
                            flag = flag.add(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoPreferPrice()
                                    .multiply(BigDecimal.valueOf(cart.getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVos.get(j).getProductNum())));
                        }

                        // 得到套装优惠费用
                        taozhuan = taozhuan.add(BigDecimal.valueOf(cart.getGoodsNum()).multiply(goodsGroupVo.getGroupPreferamount()));
                        bossSumPrice = sumOldPrice.subtract(taozhuan);
                    }
                }

            }

            BigDecimal marketflag = BigDecimal.ZERO;
            List<ShoppingCart> cartList = null;
            // 根据第三方id分组得到新的购物车集合
            cartList = new ArrayList<>();
            for (ShoppingCart sc : shoplist) {
                if (sc.getFitId() == null) {
                    cartList.add(sc);

                }
            }

            // 促销分组
            Map<Long, Object> markMap = new HashMap<>();
            for (ShoppingCart sc : cartList) {
                if (sc.getThirdId().equals(businessId) && sc.getMarketingActivityId() != null && !new Long(0).equals(sc.getMarketingActivityId())) {
                    if (sc.getMarketing() != null) {
                        markMap.put(sc.getMarketingActivityId(), sc.getMarketing());
                    } else {
                        if (sc.getMarketingList() != null) {
                            for (int i = 0; i < sc.getMarketingList().size(); i++) {
                                if (sc.getMarketingActivityId().equals(sc.getMarketingList().get(i).getMarketingId())) {
                                    markMap.put(sc.getMarketingActivityId(), sc.getMarketingList().get(i));
                                }
                            }
                        }
                    }
                }
            }
            // 各个同一商家的促销总价
            Map<Long, Object> priceMap = new HashMap<>();
            for (Long obd : markMap.keySet()) {
                // 计算各个商家不同促销的总价格
                for (ShoppingCart car : cartList) {
                    if (obd.equals(car.getMarketingActivityId()) && car.getThirdId().equals(businessId)) {
                        if (null != distinctId && distinctId > 0) {

                            totalprice = totalprice.add(BigDecimal.valueOf(car.getGoodsNum()).multiply(car.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()));

                        }
                        priceMap.put(obd, totalprice);
                    }
                }
                // 循环结束促销价格置零
                totalprice = BigDecimal.ZERO;
            }
            for (Long ob : markMap.keySet()) {
                Marketing mark = (Marketing) markMap.get(ob);

                if (mark != null) {

                    for (Long markId : priceMap.keySet()) {
                        if (markId.equals(ob)) {
                            // map 封装各个商家促销的价格
                            totalprice = (BigDecimal) priceMap.get(markId);
                            // 满减
                            if (CollectionUtils.isNotEmpty(mark.getFullbuyReduceMarketings())) {
                                for (FullbuyReduceMarketing fpm : mark.getFullbuyReduceMarketings()) {
                                    // 达到满减条件
                                    if (fpm.getFullPrice().compareTo(totalprice) == -1 || fpm.getFullPrice().compareTo(totalprice) == 0) {
                                        prePrice = fpm.getReducePrice();
                                        // 把最大值付给marketfalg
                                        if (prePrice.compareTo(marketflag) == 1 || prePrice.compareTo(marketflag) == 0) {
                                            marketflag = prePrice;
                                        }
                                    }
                                }
                                if (businessId == 0) {
                                    bossPrePrice = bossPrePrice.add(marketflag);
                                }
                                prePrice = marketflag;
                                marketflag = BigDecimal.ZERO;
                            }
                            // 满折促销
                            if (CollectionUtils.isNotEmpty(mark.getFullbuyDiscountMarketings())) {

                                for (FullbuyDiscountMarketing fdm : mark.getFullbuyDiscountMarketings()) {
                                    if (fdm.getFullPrice().compareTo(totalprice) == -1 || fdm.getFullPrice().compareTo(totalprice) == 0) {
                                        prePrice = (BigDecimal.valueOf(1).subtract(fdm.getFullbuyDiscount())).multiply(totalprice);
                                        // 把最大值付给marketfalg
                                        if (prePrice.compareTo(marketflag) == 1 || prePrice.compareTo(marketflag) == 0) {
                                            marketflag = prePrice;
                                        }
                                    }
                                }
                                if (businessId == 0) {
                                    bossPrePrice = bossPrePrice.add(marketflag);
                                }
                                prePrice = marketflag;
                                marketflag = BigDecimal.ZERO;
                            }

                            // 实际付款金额
                            flag = flag.subtract(prePrice);
                            prePrice = BigDecimal.ZERO;
                        }
                    }

                }
            }

            for (ShoppingCart cart : cartList) {
                if (cart.getMarketingActivityId() != null && cart.getMarketingActivityId() != 0) {
                    // 直降
                    Marketing mark = marketService.marketingDetail(cart.getMarketingActivityId(), cart.getGoodsInfoId());
                    Long goodsNum = cart.getGoodsNum();
                    if ("1".equals(mark.getCodexType())) {
                        flag = flag.subtract(mark.getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(goodsNum)));
                        if (businessId == 0) {
                            bossPrePrice = bossPrePrice.add(mark.getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(goodsNum)));
                        }
                    }
                    // 抢购
                    if ("11".equals(mark.getCodexType())) {
                        BigDecimal rushDiscount = mark.getRushs().get(0).getRushDiscount();
                        GoodsProductVo productVo = cart.getGoodsDetailBean().getProductVo();
                        BigDecimal multiply = (BigDecimal.ONE.subtract(rushDiscount))
                                .multiply(productVo.getGoodsInfoPreferPrice())
                                .multiply(BigDecimal.valueOf(goodsNum));
                        flag = flag.subtract(multiply
                        );
                        //计算BOSS商品抢购促销优惠金额
                        if (businessId == 0) {
                            bossPrePrice = bossPrePrice.add(multiply);
                            rushPrePrice = rushPrePrice.add(multiply);
                            // 货品购买件数
                            // 计算抢购价格页面计算用
                            if (cart.getThirdId() == 0) {
                                rushSumPrice = rushSumPrice.add(productVo.getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(goodsNum)));
                            }
                        }
                    }
                }
            }

        }
        BigDecimal sumPrice = flag.subtract(taozhuan);
        paramMap.put(SUMPRICE, sumPrice);

        paramMap.put("sumOldPrice", sumOldPrice);
        //没有抢购的优惠金额
        paramMap.put("rushPrePrice", bossPrePrice.subtract(rushPrePrice));
        //没有抢购的总boss金额计算会员折扣
        paramMap.put("rushSumPrice", bossSumPrice.subtract(rushSumPrice));
        paramMap.put(BOSSSUMPRICE, bossSumPrice);
        paramMap.put(BOSSPREPRICE, bossPrePrice);
        return paramMap;
    }


    /**
     * 设置促销后的价格 和会员折扣后的价格 以便于计算优惠券条件
     *
     * @param shoplist
     */
    private void setMarketIngPrice(List<ShoppingCart> shoplist, Long customerId) {
        if (CollectionUtils.isNotEmpty(shoplist)) {
            //满足条件的促销集合 促销id,对应促销的商品总价
            Map<Long, BigDecimal> fullplist = new HashMap<>();
            //满足条件的满减 误差集合 有用于设置误差值  促销id,对应促销的包含误差的优惠价格
            Map<Long, BigDecimal> fullerrlist = new HashMap<>();
            //满足条件的满减 正确的优惠价格
            Map<Long, BigDecimal> fullcorlist = new HashMap<>();

            //满减满折促销范围集合  促销id,参加促销的商品总价
            for (ShoppingCart sc : shoplist) {
                if (null != sc.getMarketing()) {
                    // 货品价格
                    BigDecimal gp = sc.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                    //促销  如果是满减满折  把商品总价计算出来
                    if ("5".equals(sc.getMarketing().getCodexType()) || "8".equals(sc.getMarketing().getCodexType())) {
                        Long marketingId = sc.getMarketing().getMarketingId();
                        BigDecimal sumprice = gp.multiply(new BigDecimal(sc.getGoodsNum()));
                        if (fullplist.containsKey(marketingId)) {
                            fullplist.put(marketingId, fullplist.get(marketingId).add(sumprice));
                        } else {
                            fullplist.put(marketingId, sumprice);
                        }
                    }
                }
            }

            //此处判断 满减满折是否满足条件
            for (ShoppingCart sc : shoplist) {
                if (null != sc.getMarketing()) {
                    //满减促销
                    if ("5".equals(sc.getMarketing().getCodexType()) && CollectionUtils.isNotEmpty(sc.getMarketing().getFullbuyReduceMarketings())) {
                        List<FullbuyReduceMarketing> frlist = sc.getMarketing().getFullbuyReduceMarketings();
                        List<FullbuyReduceMarketing> newlist = new ArrayList<>();
                        //对多级满减进行 从高到低排序
                        for (FullbuyReduceMarketing full : frlist) {
                            if (newlist.size() == 0) {
                                newlist.add(full);
                            } else if (newlist.size() == 1) {
                                if (newlist.get(0).getFullPrice().compareTo(full.getFullPrice()) == 1) {
                                    newlist.add(full);
                                } else {
                                    newlist.add(0, full);
                                }
                            } else {
                                if (newlist.get(0).getFullPrice().compareTo(full.getFullPrice()) == -1) {
                                    newlist.add(0, full);
                                } else if (newlist.get(1).getFullPrice().compareTo(full.getFullPrice()) == -1) {
                                    newlist.add(1, full);
                                } else {
                                    newlist.add(full);
                                }
                            }
                        }

                        //如果总金额 满足满金额赠 则将促销存入对象中
                        for (FullbuyReduceMarketing aNewlist : newlist) {
                            if (!(fullplist.get(sc.getMarketing().getMarketingId()).compareTo(aNewlist.getFullPrice()) == -1)) {
                                sc.getMarketing().setFullbuyReduceMarketing(aNewlist);
                                break;
                            }
                        }
                    }
                    //满折促销
                    if ("8".equals(sc.getMarketing().getCodexType()) && CollectionUtils.isNotEmpty(sc.getMarketing().getFullbuyDiscountMarketings())) {
                        List<FullbuyDiscountMarketing> frlist = sc.getMarketing().getFullbuyDiscountMarketings();
                        List<FullbuyDiscountMarketing> newlist = new ArrayList<>();
                        //排序
                        for (FullbuyDiscountMarketing full : frlist) {
                            if (newlist.size() == 0) {
                                newlist.add(full);
                            } else if (newlist.size() == 1) {
                                if (newlist.get(0).getFullPrice().compareTo(full.getFullPrice()) == 1) {
                                    newlist.add(full);
                                } else {
                                    newlist.add(0, full);
                                }
                            } else {
                                if (newlist.get(0).getFullPrice().compareTo(full.getFullPrice()) == -1) {
                                    newlist.add(0, full);
                                } else if (newlist.get(1).getFullPrice().compareTo(full.getFullPrice()) == -1) {
                                    newlist.add(1, full);
                                } else {
                                    newlist.add(full);
                                }
                            }
                        }

                        //如果总金额 满足满金额赠 则将促销存入对象中
                        for (FullbuyDiscountMarketing aNewlist : newlist) {
                            if (!(fullplist.get(sc.getMarketing().getMarketingId()).compareTo(aNewlist.getFullPrice()) == -1)) {
                                sc.getMarketing().setFullbuyDiscountMarketing(aNewlist);
                                break;
                            }
                        }
                    }
                }
            }


            for (ShoppingCart cart : shoplist) {
                if (cart.getMarketing() != null && cart.getMarketing().getProductReduceMoney() != null) {
                    GoodsProductVo productVo = cart.getGoodsDetailBean().getProductVo();
                    BigDecimal allPrice = productVo.getGoodsInfoPreferPrice().multiply(new BigDecimal(cart.getGoodsNum()));
                    BigDecimal count = new BigDecimal(cart.getGoodsNum());
                    //如果是满足条件的满减促销
                    if ("5".equals(cart.getMarketing().getCodexType()) && null != cart.getMarketing().getFullbuyReduceMarketing()) {
                        for (Long marketingId : fullplist.keySet()) {
                            if (marketingId.equals(cart.getMarketing().getMarketingId())) {
                                BigDecimal reduprice = cart.getMarketing().getFullbuyReduceMarketing().getReducePrice();
                                BigDecimal erroprice = reduprice.multiply(allPrice.divide(fullplist.get(marketingId), 4, BigDecimal.ROUND_HALF_UP));
                                //设置优惠价格
                                productVo.setGoodsInfoPreferPrice((allPrice.subtract(erroprice)).divide(count, 4, BigDecimal.ROUND_HALF_UP));

                                //将满减的促销含有误差的价格 和 实际优惠的价格 取出
                                Iterator errit = fullerrlist.keySet().iterator();
                                if (errit.hasNext()) {
                                    Long errmarkid = (Long) errit.next();
                                    if (errmarkid.equals(marketingId)) {
                                        fullerrlist.put(marketingId, erroprice.add(fullerrlist.get(errmarkid)));
                                    }
                                } else {
                                    fullerrlist.put(marketingId, erroprice);
                                }
                                fullcorlist.put(marketingId, reduprice);

                            }
                        }
                        //如果是满足条件的满折促销
                    } else if ("8".equals(cart.getMarketing().getCodexType()) && null != cart.getMarketing().getFullbuyDiscountMarketing()) {
                        for (Long o : fullplist.keySet()) {
                            if (o.equals(cart.getMarketing().getMarketingId())) {
                                productVo.setGoodsInfoPreferPrice(allPrice
                                        .multiply(cart.getMarketing().getFullbuyDiscountMarketing().getFullbuyDiscount())
                                        .divide(count, 4, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                        //满减满折 没有满足条件
                    } else {
                        BigDecimal one = productVo.getGoodsInfoPreferPrice();
                        productVo.setGoodsInfoPreferPrice(one.subtract(cart.getMarketing().getProductReduceMoney()));
                    }
                    cart.getGoodsDetailBean().setProductVo(productVo);

                }
            }


            for (ShoppingCart sc : shoplist) {
                if (sc.getMarketing() != null) {
                    Iterator it = fullerrlist.keySet().iterator();
                    while (it.hasNext()) {
                        Long marketing = (Long) it.next();
                        if (sc.getMarketing().getMarketingId().equals(marketing)) {
                            //误差值
                            BigDecimal errorprice = fullerrlist.get(marketing).subtract(fullcorlist.get(marketing));
                            BigDecimal allPrice = sc.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(new BigDecimal(sc.getGoodsNum()));
                            //如果当前价格 比误差大
                            if ((allPrice.add(errorprice)).compareTo(BigDecimal.ZERO) == 1) {
                                // 把误差淹没在 此订单商品中
                                sc.getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice((allPrice.add(errorprice).divide(new BigDecimal(sc.getGoodsNum()), 4, BigDecimal.ROUND_HALF_UP)));
                                break;
                            }
                        }
                    }
                }
            }

            CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId(customerId);
            //查询会员折扣
            BigDecimal pointDiscount = customerPointServiceMapper.getCustomerDiscountByPoint(Integer.parseInt(customerPoint.getPointSum().toString()));
            //会员折扣后的价格
            for (ShoppingCart sc : shoplist) {
                BigDecimal oldPrice = sc.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                sc.getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(oldPrice.multiply(pointDiscount));
            }

        }


    }


    /**
     * 得到各个商家的金额
     * KKK 去结算
     *
     * @param businessId
     * @param shopdata
     * @return
     */
    @Override
    public Map<String, Object> getEveryThirdPriceMap(Long businessId, List<ShoppingCart> shopdata, Long distinctId) {
        Map<String, Object> paramMap = new HashMap<>();



        // 1表示不同地区存在库存 0则表示库存不足直接跳到购物车
        paramMap.put(STOCK, "1");
        List<ShoppingCart> shoplist = new ArrayList<>();

        for (ShoppingCart aShopdata : shopdata) {
            if (businessId.equals(aShopdata.getThirdId())) {
                shoplist.add(aShopdata);
            }
        }

        // 原始总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(0);
        // 套装优惠金额
        BigDecimal taozhuan = BigDecimal.ZERO;
        // 优惠金额
        BigDecimal prePrice = BigDecimal.valueOf(0);
        // 中间变量
        BigDecimal flag = BigDecimal.ZERO;
        // boss总金额
        BigDecimal bossSumPrice = BigDecimal.ZERO;
        // 抢购总金额
        BigDecimal rushSumPrice = BigDecimal.ZERO;
        //boos促销优惠金额
        BigDecimal bossPrePrice = BigDecimal.ZERO;
        //抢购的优惠金额
        BigDecimal rushPrePrice = BigDecimal.ZERO;
        ProductWare productWare;
        Map<String, Object> para = new HashMap<>();
        Long goodssum = 0L;
        BigDecimal goodsprice = BigDecimal.ZERO;
        BigDecimal totalprice = BigDecimal.ZERO;

        GoodsStockBean stockBean;
        ArrayList<GoodsStockBean> stockList = new ArrayList<>();

        for (ShoppingCart cart : shoplist) {

            Long goodsNum = cart.getGoodsNum();

            if (cart.getFitId() == null) {
                GoodsProductVo productVo = cart.getGoodsDetailBean().getProductVo();
                // 货品价格
                goodsprice = productVo.getGoodsInfoPreferPrice();
                //存在已下架的货品
                if ("0".equals(productVo.getGoodsInfoAdded())) {
                    paramMap.put(STOCK, "0");
                }
                // 不同地区的货品价格
                if (businessId == 0) {
                    if (null != distinctId && distinctId > 0) {
//                        productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(cart.getGoodsInfoId(), distinctId);
                        productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(cart.getGoodsInfoId(), cart.getDistinctId());
                        // 不同地区的价格不同
                        if (null != productWare) {
                            goodsprice = productWare.getWarePrice();
                            if (productWare.getWareStock() - goodsNum < 0) {
                                paramMap.put(STOCK, "0");
                                stockBean = new GoodsStockBean();
                                stockBean.setGoodsId(cart.getGoodsInfoId());
                                stockBean.setStockNum(String.valueOf(productWare.getWareStock()));
                                stockList.add(stockBean);
                            }
                        } else {
                            paramMap.put(STOCK, "0");
                            stockBean = new GoodsStockBean();
                            stockBean.setGoodsId(cart.getGoodsInfoId());
                            stockBean.setStockNum("0");
                            stockList.add(stockBean);
                        }
                    }
                } else {
                    if (productVo.getGoodsInfoStock() - goodsNum < 0) {
                        // 第三方货存不足
                        paramMap.put("stock", "0");
                        stockBean = new GoodsStockBean();
                        stockBean.setGoodsId(cart.getGoodsInfoId());
                        stockBean.setStockNum(String.valueOf(productVo.getGoodsInfoStock()));
                        stockList.add(stockBean);
                    }
                }
                paramMap.put("stockInfo", stockList);

                // 是否抹掉分角
                String discountFlag = "";
                // 折扣促销
                Long marketingId = cart.getMarketingId();
                if (marketingId != null && 0 != marketingId) {
                    // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                    Marketing mark = marketService.marketingDetail(marketingId, cart.getGoodsInfoId());
                    if (mark != null) {
                        para.put("marketingId", mark.getMarketingId());
                        para.put("goodsId", cart.getGoodsInfoId());
                        PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                        if (premark != null) {
                            // 货品价格
                            goodsprice = goodsprice.multiply(premark.getDiscountInfo());
                            discountFlag = premark.getDiscountFlag();
                        }
                    }

                }

                DecimalFormat myformat;
                // 抹掉分
                if ("1".equals(discountFlag)) {
                    myformat = new DecimalFormat("0.0");
                } else if ("2".equals(discountFlag)) {
                    myformat = new DecimalFormat("0");
                } else {
                    myformat = new DecimalFormat("0.00");
                }
                // 不四舍五入
                myformat.setRoundingMode(RoundingMode.FLOOR);
                goodsprice = BigDecimal.valueOf(Double.valueOf(myformat.format(goodsprice)));
                // 处理货品的价格()
                productVo.setGoodsInfoPreferPrice(goodsprice);
                // 货品购买件数
                // 计算boss价格页面计算用
                if (cart.getThirdId() == 0) {
                    bossSumPrice = bossSumPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodsNum)));
                }
                // 计算原始总金额
                sumOldPrice = sumOldPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodsNum)));
                flag = flag.add(goodsprice.multiply(BigDecimal.valueOf(goodsNum)));
            }
            else {
                GoodsGroupVo goodsGroupVo = cart.getGoodsGroupVo();
                if (null != goodsGroupVo) {
                    // 该套装下所有的商品
                    List<GoodsGroupReleProductVo> goodsGroupReleProductVos = cart.getGoodsGroupVo().getProductList();
                    // 获取此套装下的所有货品
                    for (int j = 0; j < goodsGroupReleProductVos.size(); j++) {
                        GoodsProductVo productVo = goodsProductService.findProductById(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoId(),
                                cart.getDistinctId());
                        if(productVo == null){
                            cart.setAvailable(false);
                            break;
                        }
                        BigDecimal preferPrice =productVo.getGoodsInfoPreferPrice();
                        goodsGroupReleProductVos.get(j).getProductDetail().setGoodsInfoPreferPrice(preferPrice);
                        // 原总金额加上套装优惠前费用
                        Long productNum = goodsGroupReleProductVos.get(j).getProductNum();
                        BigDecimal oldPrice = preferPrice
                                .multiply(BigDecimal.valueOf(cart.getGoodsNum())).multiply(BigDecimal.valueOf(productNum));

                        sumOldPrice = sumOldPrice.add(oldPrice);
                        flag = flag.add(oldPrice);
                    }

                    // 得到套装优惠费用
                    taozhuan = taozhuan.add(BigDecimal.valueOf(cart.getGoodsNum()).multiply(goodsGroupVo.getGroupPreferamount()));
                    bossSumPrice = sumOldPrice.subtract(taozhuan);
                }
            }

        }

        BigDecimal marketflag = BigDecimal.ZERO;
        // 根据第三方id分组得到新的购物车集合
        List<ShoppingCart> cartList = new ArrayList<>();
        for (ShoppingCart sc : shoplist) {
            if (sc.getFitId() == null) {
                cartList.add(sc);

            }
        }

        // 促销分组
        Map<Long, Object> markMap = new HashMap<>();
        for (ShoppingCart sc : cartList) {
            if (sc.getThirdId().equals(businessId) && sc.getMarketingActivityId() != null && !new Long(0).equals(sc.getMarketingActivityId())) {
                if (sc.getMarketing() != null) {
                    markMap.put(sc.getMarketingActivityId(), sc.getMarketing());
                } else {
                    if (sc.getMarketingList() != null) {
                        for (int i = 0; i < sc.getMarketingList().size(); i++) {
                            if (sc.getMarketingActivityId().equals(sc.getMarketingList().get(i).getMarketingId())) {
                                markMap.put(sc.getMarketingActivityId(), sc.getMarketingList().get(i));
                            }
                        }
                    }
                }
            }
        }
        // 各个同一商家的促销总价
        Map<Long, Object> priceMap = new HashMap<>();
        for (Long obd : markMap.keySet()) {
            // 计算各个商家不同促销的总价格
            for (ShoppingCart car : cartList) {
                if (obd.equals(car.getMarketingActivityId()) && car.getThirdId().equals(businessId)) {
                    if (null != distinctId && distinctId > 0) {

                        totalprice = totalprice.add(BigDecimal.valueOf(car.getGoodsNum()).multiply(car.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()));

                    }
                    priceMap.put(obd, totalprice);
                }
            }
            // 循环结束促销价格置零
            totalprice = BigDecimal.ZERO;
        }
        for (Long ob : markMap.keySet()) {
            Marketing mark = (Marketing) markMap.get(ob);

            if (mark != null) {

                for (Long markId : priceMap.keySet()) {
                    if (markId.equals(ob)) {
                        // map 封装各个商家促销的价格
                        totalprice = (BigDecimal) priceMap.get(markId);
                        // 满减
                        if (CollectionUtils.isNotEmpty(mark.getFullbuyReduceMarketings())) {
                            for (FullbuyReduceMarketing fpm : mark.getFullbuyReduceMarketings()) {
                                // 达到满减条件
                                if (fpm.getFullPrice().compareTo(totalprice) == -1 || fpm.getFullPrice().compareTo(totalprice) == 0) {
                                    prePrice = fpm.getReducePrice();
                                    // 把最大值付给marketfalg
                                    if (prePrice.compareTo(marketflag) == 1 || prePrice.compareTo(marketflag) == 0) {
                                        marketflag = prePrice;
                                    }
                                }
                            }
                            if (businessId == 0) {
                                bossPrePrice = bossPrePrice.add(marketflag);
                            }
                            prePrice = marketflag;
                            marketflag = BigDecimal.ZERO;
                        }
                        // 满折促销
                        if (CollectionUtils.isNotEmpty(mark.getFullbuyDiscountMarketings())) {

                            for (FullbuyDiscountMarketing fdm : mark.getFullbuyDiscountMarketings()) {
                                if (fdm.getFullPrice().compareTo(totalprice) == -1 || fdm.getFullPrice().compareTo(totalprice) == 0) {
                                    prePrice = (BigDecimal.valueOf(1).subtract(fdm.getFullbuyDiscount())).multiply(totalprice);
                                    // 把最大值付给marketfalg
                                    if (prePrice.compareTo(marketflag) == 1 || prePrice.compareTo(marketflag) == 0) {
                                        marketflag = prePrice;
                                    }
                                }
                            }
                            if (businessId == 0) {
                                bossPrePrice = bossPrePrice.add(marketflag);
                            }
                            prePrice = marketflag;
                            marketflag = BigDecimal.ZERO;
                        }

                        // 实际付款金额
                        flag = flag.subtract(prePrice);
                        prePrice = BigDecimal.ZERO;
                    }
                }

            }
        }

        for (ShoppingCart cart : cartList) {
            Long marketingActivityId = cart.getMarketingActivityId();
            if (marketingActivityId != null && marketingActivityId != 0) {
                // 直降
                Marketing mark = marketService.marketingDetail(marketingActivityId, cart.getGoodsInfoId());
                if ("1".equals(mark.getCodexType())) {
                    flag = flag.subtract(mark.getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                    if (businessId == 0) {
                        bossPrePrice = bossPrePrice.add(mark.getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                    }
                }
                // 抢购
                if ("11".equals(mark.getCodexType())) {
                    BigDecimal prePrice1 = (BigDecimal.ONE.subtract(mark.getRushs().get(0).getRushDiscount()))
                            .multiply(cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice())
                            .multiply(BigDecimal.valueOf(cart.getGoodsNum()));
                    flag = flag.subtract(prePrice1);
                    //计算BOSS商品抢购促销优惠金额
                    if (businessId == 0) {
                        bossPrePrice = bossPrePrice
                                .add(prePrice1);
                        rushPrePrice = rushPrePrice
                                .add(prePrice1);
                        // 货品购买件数
                        goodssum = cart.getGoodsNum();
                        // 计算抢购价格页面计算用
                        if (cart.getThirdId() == 0) {
                            rushSumPrice = rushSumPrice.add(cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(goodssum)));
                        }
                    }
                }
            }
        }

        // 交易总金额
        BigDecimal sumPrice = flag.subtract(taozhuan);

        paramMap.put("sumPrice", sumPrice);
        paramMap.put("sumOldPrice", sumOldPrice);
        //没有抢购的优惠金额
        paramMap.put("rushPrePrice", bossPrePrice.subtract(rushPrePrice));
        //没有抢购的总boss金额计算会员折扣
        paramMap.put("rushSumPrice", bossSumPrice.subtract(rushSumPrice));
        paramMap.put(BOSSSUMPRICE, bossSumPrice);
        paramMap.put(BOSSPREPRICE, bossPrePrice);
        return paramMap;
    }

    /**
     * 新购物车结算
     *
     * @param request
     * @param box
     * @return
     * fixme replace(request,customerId)
     */
    @Override
    public Map<String, Object> newsubOrder(HttpServletRequest request, Long[] box, CustomerAddress customerAddress, String[] boxgift) {
        if (customerAddress == null || customerAddress.getInfoCounty() == null) {
            customerAddress = new CustomerAddress();
            customerAddress.setInfoCounty("774");
            customerAddress.setInfoCity("74");
        }
        Map<String, Object> cartMap = new HashMap<>();
        //购物车信息
        List<ShoppingCart>  shoplist = shoppingCartMapper.shopCartListByIds(Arrays.asList(box));

        ShoppingCartWareUtil wareUtil = new ShoppingCartWareUtil();
        wareUtil.setDistrictId(Long.parseLong(customerAddress.getInfoCounty()));
        //查询购物车内商品的详细信息
        shoppingCartDetail(shoplist, wareUtil);

        // 判断购物车中的商品 是否有达到限购的 达到了限购 则直接返回
        if (isPanicBuyingLimit(shoplist)) {
            cartMap.put("buyingLimitError","buyingLimit");
            return cartMap;
        }

        //填充购物车已选中赠品信息
        this.fillShoppingCartGifts(shoplist, boxgift, wareUtil.getDistrictId());

        cartMap.put(SHOPLIST, shoplist);

        // 查询购物车有哪些商家的商品
        List<StoreTemp> storeList = shoppingCartMapper.selectStoreTempByshopcartIds(Arrays.asList(box));
        /**
         *  为了兼容mobile的套装。
         *  wqt
         *  2016-09-10
         */
        for (ShoppingCart cart : shoplist) {
            if(cart.getFitId()!= null){
                StoreTemp storeTemp = new StoreTemp();
                storeTemp.setThirdId(0L);
                storeTemp.setThirdName("boss");
                storeList.add(storeTemp);
                break;
            }
        }
        // 把套装中重复的第三方id去掉
        for (int j = 0; j < storeList.size(); j++) {
            for (int k = storeList.size() - 1; k > j; k--) {
                if (storeList.get(k).getThirdId().equals(storeList.get(j).getThirdId())) {
                    storeList.remove(j);
                }

            }
            // 为购物车的第三方id重新排序,boss商城排前面
            if (storeList.get(j).getThirdId() == 0) {
                StoreTemp ste = storeList.get(0);
                storeList.set(0, storeList.get(j));
                storeList.set(j, ste);
            }
        }
        List<FreightTemplate> freight = new ArrayList<>();
        // 循环得到快递方式
        for (StoreTemp st : storeList) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("freightIsDefault", "1");
            paramMap.put("freightThirdId", st.getThirdId());
            FreightTemplate ft = freightTemplateMapper.selectFreightTemplateSubOrder(paramMap);
            if (null != ft) {
                freight.add(ft);
            }

        }

        // 查询所有参与的促销
        List<Marketing> marketinglist = marketingIdsListUtil(shoplist);
        // 获取积分消费规则
        PointSet pointSet = this.couponService.selectPointSet();
        // 根据会员ID获取积分对象
        Long customerId = (Long) request.getSession().getAttribute(CUSTOMERID);
        CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId(customerId);

        if (null != customerPoint && null != pointSet) {
            // 保存当前登录会员总积分
            cartMap.put("customerPoint", customerPoint.getPointSum() - customerPointServiceMapper.getCustomerReducePoint(customerId + ""));
            // 保存积分兑换金额的比例 （消费规则）
            cartMap.put("pointSet", pointSet.getConsumption());
            //新版移动端提交订单时查看后台积分设置状态  houyichang修改bug添加
            cartMap.put("isOpen", pointSet.getIsOpen());
        }

        List<Coupon> couponList = getUsedCouponlist(request, box);
        if (CollectionUtils.isNotEmpty(freight)) {
            cartMap.put("frightlist", freight);
        }

        // 订单总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(0);
        // 订单总金额
        BigDecimal sumPrice = BigDecimal.valueOf(0);
        for (StoreTemp st : storeList) {
            Map<String, Object> price = getEveryThirdPriceMap(st.getThirdId(), shoplist, Long.parseLong(customerAddress.getInfoCounty()));
            //存在库存不足的商品
            if ("0".equals(price.get("stock"))) {
                cartMap.put(STOCK, "0");
                cartMap.put("stockInfo", price.get("stockInfo"));
                return cartMap;
            }
            st.setSumPrice((BigDecimal) price.get("sumPrice"));
            sumOldPrice = ((BigDecimal) price.get("sumOldPrice")).add(sumOldPrice);
            sumPrice = ((BigDecimal) price.get("sumPrice")).add(sumPrice);
        }
        BigDecimal youhui = sumOldPrice.subtract(sumPrice);
        cartMap.put("storeList", storeList);
        //订单总金额
        cartMap.put("sumOldPrice", sumOldPrice);
        cartMap.put("sumPrice", sumPrice);
        cartMap.put("youhui", youhui);
        //查询会员折扣
        BigDecimal pointDiscount = customerPointServiceMapper.getCustomerDiscountByPoint(Integer.parseInt(customerPoint.getPointSum().toString()));
        //查询boss 商品的总价 和 所有商品的总价格
        Map<String, Object> bossPrice = getEveryThirdPriceMap(0L, shoplist, Long.parseLong(customerAddress.getInfoCounty()));
        //boss商品总价减去促销优惠后金额
        BigDecimal bossZhekSum = ((BigDecimal) bossPrice.get("bossSumPrice")).subtract((BigDecimal) bossPrice.get(BOSSPREPRICE));
        BigDecimal bossrushSum = ((BigDecimal) bossPrice.get("rushSumPrice")).subtract((BigDecimal) bossPrice.get("rushPrePrice"));
        BigDecimal zhek = bossZhekSum.subtract(pointDiscount.multiply(bossZhekSum));
        BigDecimal rush = bossrushSum.subtract(pointDiscount.multiply(bossrushSum));



        BigDecimal bossyouhui = BigDecimal.valueOf(Double.valueOf(bossPrice.get(BOSSPREPRICE).toString()));
        //优惠后 含会员折扣后
        cartMap.put("bossPrice", ((BigDecimal) bossPrice.get("bossSumPrice")).subtract(zhek));
        cartMap.put("couponlist", couponList);
        //boss商品会员折扣优惠
        cartMap.put("zheKPrice", rush);
        //促销优惠价格
        cartMap.put(THIRDS, storeList);
        cartMap.put(MARKETINGLIST, marketinglist);
        cartMap.put("bossyouhui", bossyouhui);
        //计算运费
        Map<String, Object> fPrice = getNewExpressPrice(Long.parseLong(customerAddress.getInfoCity()), Arrays.asList(box), Long.parseLong(customerAddress.getInfoCounty()));
        cartMap.put("fPrice", fPrice);
        cartMap.put("allPrice", pointDiscount.multiply(bossrushSum));

        return cartMap;
    }


    /**
     * 判断购物车中的商品 是否有商品达到了抢购的限制
     *
     * @param shoppingCarts 购物车中的商品
     * @return 否则判断当前用户的抢购的商品是否达到了自己的购买限制 如果达到了
     * 则返回true  否则返回false
     */
    private boolean isPanicBuyingLimit(List<ShoppingCart> shoppingCarts) {

        // 如果购物车中的商品是空的 则不做处理 返回false
        if (CollectionUtils.isEmpty(shoppingCarts)) {
            LOGGER.info("如果购物车中的商品是空的 则不做处理 返回false");
            return false;
        }

        for (ShoppingCart shoppingCart : shoppingCarts) {
            // 如果商品 没有促销 则跳过这个商品
            if (null == shoppingCart.getMarketingActivityId() || 0 == shoppingCart.getMarketingActivityId() || CollectionUtils.isEmpty(shoppingCart.getMarketingList())) {
                continue;
            }

            for (Marketing marketing : shoppingCart.getMarketingList()) {
                // 如果当前的促销 不是抢购促销 则跳过这个促销
                if (!"11".equals(marketing.getCodexType())) {
                    continue;
                }

                // 当前促销是抢购促销
                if (shoppingCart.getMarketingActivityId().equals(marketing.getMarketingId())) {
                    // 首先判断  用户购买的数量是否 超过了当前抢购的限购数 则返回true
                    if (shoppingCart.getGoodsNum() > marketing.getRushs().get(0).getRushLimitation()) {
                        LOGGER.info("用户购买的数量超过了当前抢购的限购数");
                        return true;
                    }

                    // 判断 用户已经购买的该抢购的商品 加上当前用户购买的数量 是否超过了当前抢购的限购数
                    Integer alerdyBuyNum = getUserAlreadyByNum(marketing.getRushs().get(0).getRushId(), shoppingCart.getCustomerId(), shoppingCart.getGoodsDetailBean().getProductVo().getGoodsInfoId());
                    if ((alerdyBuyNum + shoppingCart.getGoodsNum()) > marketing.getRushs().get(0).getRushLimitation()) {
                        LOGGER.info("用户已经购买的该抢购的商品 加上当前用户购买的数量 超过了当前抢购的限购数");

                        return true;
                    }

                }
            }
        }

        return false;
    }


    /**
     * 根据所选择的商品进入订单确认查询
     *
     * @param request
     * @param box
     * @return List
     */
    @Override
    public Map<String, Object> subOrder(HttpServletRequest request, Long[] box, Long[] marketingId, Long[] thirdId, ShoppingCartWareUtil wareUtil) {

        // 用来存放数据
        Map<String, Object> paramMap = new HashMap<>();

        List<Long> list = new ArrayList<>();
        List<ParamIds> infoIds = new ArrayList<>();
        // 购物车id数量不为0
        Collections.addAll(list, box);
        List<ShoppingCart> shoplist = searchByProduct(request, box);
        // 存放第三方店铺标示
        List<Long> thirdIds = new ArrayList<>();

        // 添加第三方id
        if (shoplist != null && !shoplist.isEmpty()) {
            for (ShoppingCart sc : shoplist) {
                if (sc.getFitId() == null) {
                    thirdIds.add(sc.getGoodsDetailBean().getProductVo().getThirdId());
                } else {
                    if (sc.getGoodsGroupVo().getIsThird() != null) {
                        thirdIds.add(Long.parseLong(sc.getGoodsGroupVo().getIsThird()));
                    } else {
                        thirdIds.add((long) 0);
                    }
                }
                if (sc.getFitId() == null) {
                    // 判断是否包邮
                    Long baoyou = marketService.queryByCreatimeMarketings(sc.getGoodsInfoId(), 6L, sc.getGoodsDetailBean().getProductVo().getGoods().getCatId(), sc
                            .getGoodsDetailBean().getBrand().getBrandId());
                    if (baoyou != 0) {
                        sc.setIsBaoyou("1");
                    } else {
                        sc.setIsBaoyou("0");
                    }
                }
            }
            // 去除重复第三方id
            for (int q = 0; q < thirdIds.size() - 1; q++) {
                for (int p = thirdIds.size() - 1; p > q; p--) {
                    if (thirdIds.get(p).equals(thirdIds.get(q))) {
                        thirdIds.remove(p);
                    }
                }

            }
        }
        // 记录店铺名称
        Map<Object, String> thirdName = new HashMap<>();
        List<Long> goodsInfoIds = new ArrayList<>();
        for (ShoppingCart cart : shoplist) {
            if (cart.getFitId() != null) {

                // 该套装下所有的商品
                List<GoodsGroupReleProductVo> goodsGroupReleProductVos = cart.getGoodsGroupVo().getProductList();

                // 设置套装商品库存
                // 套装下商品
                for (GoodsGroupReleProductVo relProduct : goodsGroupReleProductVos) {
                    Map<String, Long> map = new HashMap<>();
                    // 根据商品id和地区id取得商品库存欣喜
                    ProductWare productWare = productWareMapper.queryProductWareByProductIdAndDistinctId(map);
                    // 判断是否存在
                    if (productWare != null) {
                        // 设置库存
                        relProduct.getProductDetail().setGoodsInfoStock(productWare.getWareStock());
                        // 设置该库存下商品价格
                        relProduct.getProductDetail().setGoodsInfoPreferPrice(productWare.getWarePrice());

                    } else {
                        // 设置库存为0
                        relProduct.getProductDetail().setGoodsInfoStock((long) 0);
                        // 设置该套装不可用
                        cart.getGoodsGroupVo().setGroupDelflag("1");
                    }
                }
                // 重新设置list
                cart.getGoodsGroupVo().setProductList(goodsGroupReleProductVos);
                // 设置套装商品库存end

            } else {
                cart.setGoodsDetailBean(goodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(), cart.getDistinctId()));
                ParamIds p = new ParamIds();
                GoodsDetailBean goodsDetailBean = cart.getGoodsDetailBean();
                GoodsProductVo productVo = goodsDetailBean.getProductVo();
                p.setCouponRangeFkId(productVo.getGoodsInfoId());
                p.setCouponRangeType("2");
                infoIds.add(p);
                for (Long thirdId1 : thirdIds) {
                    if (productVo.getThirdId().equals(thirdId1)) {
                        thirdName.put(thirdId1, productVo.getThirdName());
                    }
                }
                goodsInfoIds.add(productVo.getGoodsInfoId());
                ParamIds p1 = new ParamIds();
                p1.setCouponRangeFkId(goodsDetailBean.getBrand().getBrandId());
                p1.setCouponRangeType("1");
                infoIds.add(p1);

                ParamIds p2 = new ParamIds();
                p2.setCouponRangeFkId(productVo.getGoods().getCatId());
                p2.setCouponRangeType("0");
                infoIds.add(p2);
                // 设置订单优惠
                // 设置选中的订单优惠
                if (cart.getOrderMarketingId() != null && cart.getOrderMarketingId() > 0) {
                    cart.setOrderMarket(marketService.marketingDetail(cart.getOrderMarketingId()));
                }
            }
        }
        List<OrderUtil> orderMarketings = new ArrayList<>();
        // 根据每个商家加入商家信息
        for (int i = 0; i < thirdIds.size(); i++) {
            OrderUtil orderUtil = new OrderUtil();
            Object obj = thirdIds.get(i);
            if (obj == null) {
                obj = 0;
            }
            // 存放商家信息
            orderUtil.setThirdId(Long.parseLong(obj.toString()));
            if ("0".equals(obj.toString())) {
                orderUtil.setInfoRealname("BOSS");
            } else {
                orderUtil.setInfoRealname(thirdName.get(thirdIds.get(i)));
            }
            // 判断商品数量是否大于0，不包含套装
            if (!goodsInfoIds.isEmpty()) {

                List<Marketing> marketings = marketService.queryOrderMarketingByGoodsId(goodsInfoIds, thirdIds.get(i));
                orderUtil.setMarketings(marketings);
                if (marketings != null) {
                    if (marketingId == null && thirdId != null) {
                        orderUtil.setMarketing(null);
                    } else {
                        // 加入所有的订单信息
                        // 判断是否有选中订单优惠
                        if (marketingId != null && thirdId != null) {
                            if (marketingId[i] != 0) {
                                orderUtil.setMarketing(marketService.marketingDetail(marketingId[i]));
                            } else {
                                orderUtil.setMarketing(null);
                            }
                        } else {
                            if (!marketings.isEmpty()) {
                                orderUtil.setMarketing(marketService.marketingDetail(marketings.get(0).getMarketingId()));
                            }
                        }

                    }
                }
            }

            orderMarketings.add(orderUtil);
        }
        List<Coupon> couponList = null;
        if (!infoIds.isEmpty()) {
            couponList = couponService.selectCouponListByIds(infoIds, request);
        }
        paramMap.put(SHOPLIST, shoplist);
        paramMap.put("couponlist", couponList);
        // 商品的店铺标示
        paramMap.put("thirdIds", thirdIds);
        paramMap.put("orderMarketings", orderMarketings);
        // 根据会员ID获取积分对象
        CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId((Long) request.getSession().getAttribute(CUSTOMERID));
        // 获取积分消费规则
        PointSet pointSet = this.couponService.selectPointSet();
        if (null != customerPoint && null != pointSet) {
            // 保存当前登录会员总积分
            paramMap.put("customerPoint", customerPoint.getPointSum());
            // 保存积分兑换金额的比例 （消费规则）
            paramMap.put("pointSet", pointSet.getConsumption());
        }

        return paramMap;
    }

    /**
     * 查询最后加入的商品id
     *
     * @param response
     * @param request
     * @return
     */
    @Override
    public Long selectLastId(ShoppingCart shoppingCart, HttpServletResponse response, HttpServletRequest request) {
        try {
            ShoppingCart sc = shoppingCartMapper.selectShopingByParam(shoppingCart);
            if (sc != null) {
                shoppingCartMapper.delShoppingCartById(sc.getShoppingCartId());
            }
            long count = addShoppingCart(shoppingCart, request, response);
            if (count == 0) {
                return count;
            }
        } catch (UnsupportedEncodingException e) {
            Customer cust = (Customer) request.getSession().getAttribute("cust");
            OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
        }
        return shoppingCartMapper.selectLastId(shoppingCart);
    }

    /**
     * 限购
     *
     * @param goodsDetailBean 商品
     * @return
     */
    @Override
    public GoodsDetailBean forPurchasing(GoodsDetailBean goodsDetailBean, Long customerId) {
        Marketing marketing = marketService.marketingDetail(marketService.queryByCreatimeMarketings(goodsDetailBean.getProductVo().getGoodsInfoId(), 3L, goodsDetailBean
                .getProductVo().getGoods().getCatId(), goodsDetailBean.getBrand().getBrandId()));
        // 存在限购
        if (marketing != null) {
            Long stock = marketing.getLimitBuyMarketing().getLimitCount();

            // 最近是否购买过该商品
            Long num = orderser.selectGoodsInfoCount(goodsDetailBean.getProductVo().getGoodsInfoId(), customerId, marketing.getMarketingBegin());
            if (num != null) {
                stock = stock - num;
                if (stock < 0) {
                    stock = 0L;
                }
            }
            if (goodsDetailBean.getProductVo().getGoodsInfoStock() - stock >= 0) {
                // 如果存在限购，则把库存改为限购数量
                goodsDetailBean.getProductVo().setGoodsInfoStock(stock);
            }
        }
        return goodsDetailBean;
    }

    /**
     * 计算全国价格
     *
     * @param motheds
     * @param fe
     * @param num
     * @param weight
     * @return BigDecimal
     */
    public BigDecimal computeFreight(String motheds, FreightExpress fe, Integer num, BigDecimal weight) {
        BigDecimal price = new BigDecimal(0);
        if (num == 0) {
            return price;
        }
        // 计件
        if ("0".equals(motheds)) {
            // 如果购买的数量在首件之内
            if (num < Integer.parseInt(fe.getExpressStart().toString())) {
                // 价格 = 首件价格 + （ 总件 -首件）*续件价格
                int a = 0;
                BigDecimal temp = fe.getExpressPostageplus().multiply(new BigDecimal(a));
                price = fe.getExpressPostage().add(temp);
            } else {
                // 价格 = 首件价格 + （ 总件 -首件）*续件价格
                int a = num - Integer.parseInt(fe.getExpressStart().toString());
                BigDecimal temp = fe.getExpressPostageplus().multiply((new BigDecimal(a)).divide(new BigDecimal(fe.getExpressPlusN1()), 0, BigDecimal.ROUND_UP));
                price = fe.getExpressPostage().add(temp);
            }

            return price;
        } else {
            // 如果购买重量大于或者等与会
            if (weight.compareTo(new BigDecimal(fe.getExpressStart())) == -1) {
                // 计重 价格= 首重价格 + （总重-首重）*续重价格
                BigDecimal a = new BigDecimal(0);
                BigDecimal temp = fe.getExpressPostageplus().multiply(a);
                price = fe.getExpressPostage().add(temp);
            } else {
                // 计重 价格= 首重价格 + （总重-首重）*续重价格
                BigDecimal a = weight.subtract(new BigDecimal(fe.getExpressStart()));
                BigDecimal temp = fe.getExpressPostageplus().multiply(a.divide(new BigDecimal(fe.getExpressPlusN1()), 0, BigDecimal.ROUND_UP));
                price = fe.getExpressPostage().add(temp);
            }

            return price;
        }
    }

    /**
     * 计算其他地区价格
     *
     * @param motheds
     * @param frall
     * @param num
     * @param weight
     * @return BigDecimal
     */
    public BigDecimal computeFreightAll(String motheds, FreightExpressAll frall, Integer num, BigDecimal weight) {
        BigDecimal price = new BigDecimal(0);
        if (num == 0) {
            return price;
        }
        // 计件
        if ("0".equals(motheds)) {
            // 如果购买的数量在首件之内
            if (num < Integer.parseInt(frall.getExpressStart().toString())) {
                // 价格 = 首件价格 + （ 总件 -首件）*续件价格
                int a = 0;
                BigDecimal temp = frall.getExpressPostageplus().multiply(new BigDecimal(a));
                price = frall.getExpressPostage().add(temp);
            } else {
                // 价格 = 首件价格 + （ 总件 -首件）*续件价格
                int a = num - Integer.parseInt(frall.getExpressStart().toString());
                BigDecimal temp = frall.getExpressPostageplus().multiply((new BigDecimal(a)).divide(new BigDecimal(frall.getExpressPlusN1()), 0, BigDecimal.ROUND_UP));
                price = frall.getExpressPostage().add(temp);
            }

            return price;
        } else {
            // 如果购买重量大于或者等与会
            if (weight.compareTo(new BigDecimal(frall.getExpressStart())) == -1) {
                // 计重 价格= 首重价格 + （总重-首重）*续重价格
                BigDecimal a = new BigDecimal(0);
                BigDecimal temp = frall.getExpressPostageplus().multiply(a);
                price = frall.getExpressPostage().add(temp);
            } else {
                // 计重 价格= 首重价格 + （总重-首重）*续重价格
                BigDecimal a = weight.subtract(new BigDecimal(frall.getExpressStart()));
                BigDecimal temp = frall.getExpressPostageplus().multiply(a.divide(new BigDecimal(frall.getExpressPlusN1()), 0, BigDecimal.ROUND_UP));
                price = frall.getExpressPostage().add(temp);
            }

            return price;
        }

    }

    /**
     * 得到各个商家的运费
     *
     * @param thirdId
     * @param cityId
     * @param cartList
     * @return
     */

    @Override
    public BigDecimal calExpressPriceByThirdId(Long thirdId, Long cityId, List<ShoppingCart> cartList) {
        // 查询物流模板信息 根据thirdId 查询默认的模板
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("freightIsDefault", "1");
        paramMap.put("freightThirdId", thirdId);
        BigDecimal freightmoney = BigDecimal.ZERO;
        Integer goodsnum = 0;
        BigDecimal goodsweight = BigDecimal.valueOf(0);

        for (ShoppingCart sc : cartList) {
            if (sc.getThirdId().equals(thirdId)) {
                // 判断是否是套装
                if (sc.getFitId() == null) {
                    // 如果是普通商品，执行普通商品的方法
                    GoodsProductVo goodsProduct = goodsProductService.queryProductByProductId(sc.getGoodsInfoId());
                    goodsweight = goodsweight.add(goodsProduct.getGoodsInfoWeight().multiply(new BigDecimal(sc.getGoodsNum())));
                    goodsnum += Integer.parseInt(sc.getGoodsNum().toString());

                } else {
                    // 套装运费计算
                    // 获取此套装下的所有货品
                    List<GoodsProductVo> goodsProducts = goodsProductMapper.queryDetailByGroupId(sc.getFitId());
                    // 遍历套装下的商品
                    for (GoodsProductVo productVO : goodsProducts) {

                        goodsweight = goodsweight.add(productVO.getGoodsInfoWeight().multiply(new BigDecimal(sc.getGoodsNum())));
                        goodsnum += Integer.parseInt(sc.getGoodsNum().toString());

                    }

                }
            }

        }
        // 获取默认模板
        FreightTemplate ft = freightTemplateMapper.selectFreightTemplateSubOrder(paramMap);

        if (ft != null) {
            // 查询默认全国设置
            List<FreightExpress> fe = freightExpressMapper.selectTemplateExpress(ft.getFreightTemplateId());

            // 如果全国设置不为空
            if (fe != null && !fe.isEmpty()) {
                for (int i = 0; i < fe.size(); i++) {

                    // 获取其他地区设置
                    List<FreightExpressAll> fall = fe.get(i).getFreightExpressAll();
                    // 其他地区设置不为空
                    if (fall != null && !fall.isEmpty()) {
                        for (int j = 0; j < fall.size(); j++) {
                            // 获取其他地区设置
                            String area = fall.get(j).getExpressArea();
                            String[] cityIds = area.split(",");
                            // 标识
                            int flag = 0;
                            // 判断是否存在此城市单独设置
                            for (String ciId : cityIds) {
                                if (ciId.equals(cityId.toString())) {
                                    flag = 1;
                                    break;
                                } else {
                                    continue;
                                }

                            }
                            // 有设置其他城市运费，并且包含城市的收货城市
                            if (flag == 1) {
                                freightmoney = computeFreightAll(ft.getFreightMethods(), fall.get(j), goodsnum, goodsweight);
                                break;
                                // 有设置其他城市运费，没有城市的收货城市
                            } else {
                                // 如果上述判断没有返回return
                                // 那么去默认全国设置
                                freightmoney = computeFreight(ft.getFreightMethods(), fe.get(i), goodsnum, goodsweight);
                                continue;
                            }

                        }
                    } else {
                        // 如果默认其他地区没有
                        freightmoney = computeFreight(ft.getFreightMethods(), fe.get(i), goodsnum, goodsweight);
                    }

                }

            }

        }

        return freightmoney;
    }

    /**
     * 参数是需要运费购物车id 第三方id根据第三方来分组得到各个商家的运费-
     *
     * @param cityId
     * @return
     */

    @Override
    public Map<String, Object> getNewExpressPrice(Long cityId, List<Long> cartIds, Long cityCountryId) {

        List<ShoppingCart> shopdata = shoppingCartMapper.shopCartListByIds(cartIds);
        // List<ShoppingCart> shopthird = new ArrayList<>();
        Map<Long, Object> thirdMap = new HashMap<>();
        BigDecimal freightmoney = BigDecimal.ZERO;
        // boss运费为上门自提用
        BigDecimal bossfreight = BigDecimal.ZERO;
        // 得到没有包邮或者没有达到条件的购物车集合
        List<ShoppingCart> cartList = getNobaoyouShoppingcarts(shopdata, cityCountryId);
        if (CollectionUtils.isNotEmpty(cartList)) {

            // 商家id集合
            for (ShoppingCart sh : cartList) {
                if (sh.getThirdId() != null) {
                    thirdMap.put(sh.getThirdId(), "");

                } else {
                    if (sh.getFitId() != null) {
                        // 如果商品是套装
                        GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(sh.getFitId());
                        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = goodsGroupVo.getProductList();
                        // 获取此套装下的所有货品
                        List<GoodsProductVo> goodsProducts = goodsProductMapper.queryDetailByGroupId(sh.getFitId());
                        sh.setThirdId(goodsGroupVo.getThirdId());
                        thirdMap.put(goodsGroupVo.getThirdId(), "");
                        if (sh.getThirdId() == 0) {
                            for(GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos){
                                GoodsDetailBean goodsDetailBean = goodsProductService.queryDetailBeanByProductId(goodsGroupReleProductVo.getProductId(),cityCountryId);
                                goodsDetailBean.getProductVo().setGoodsInfoPreferPrice(goodsDetailBean.getProductVo().getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum())));
                            }
//                        }

                        }
                    }
                }
            }
            for (Long thirdId : thirdMap.keySet()) {
                freightmoney = freightmoney.add(calExpressPriceByThirdId(thirdId, cityId, cartList));
                if (thirdId == 0) {
                    bossfreight = bossfreight.add(calExpressPriceByThirdId(thirdId, cityId, cartList));
                }
            }

        }
        Map<String, Object> freightMap = new HashMap<>();
        // 总运费
        freightMap.put("freightmoney", freightmoney);
        // boss平台运费
        freightMap.put("bossfreight", bossfreight);
        return freightMap;

    }

    /**
     * 根据购物车里面的货品查询是否存在包邮的促销活动 返回list集合,不包邮的购物车
     *
     * @param cartList
     * @return
     * @author jiaodongzhi
     */
    @Override
    public List<ShoppingCart> getNobaoyouShoppingcarts(List<ShoppingCart> cartList, Long distinctId) {
        // 不包邮购物车或者参与包邮活动但是没有达到条件
        List<ShoppingCart> nobaoyou = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(cartList)) {
            Map<Long, Object> markMap = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            // 参与包邮,生成新的购物车
            List<ShoppingCart> baoyou = new ArrayList<>();
            for (int i = 0; i < cartList.size(); i++) {
                Marketing marketing = null;
                if (cartList.get(i) != null && cartList.get(i).getGoodsInfoId() != null) {
                    // 从购物车里得到商品id,和包邮促销类型重新从数据库查询,防止当前(包邮促销)已经过期;
                    map.put(GOODSID, cartList.get(i).getGoodsInfoId());
                    map.put("codeType", "12");
                    List<Marketing> marketingList = marketingMapper.queryMarketingByGoodIdAndtypeList(map);
                    if (marketingList != null && !marketingList.isEmpty()) {
                        marketing = marketingList.get(0);
                    }
                    // 包邮
                    if (null != marketing) {
                        cartList.get(i).setMarketing(marketing);
                        baoyou.add(cartList.get(i));
                        // 促销分组
                        markMap.put(marketing.getMarketingId(), cartList.get(i).getThirdId());
                    } else {
                        // 购物车里的包邮促销过期或者没有参与包邮促销
                        nobaoyou.add(cartList.get(i));
                    }

                }
            }
            // 存放第三方id
            Map<Long, String> groups = new HashMap<Long, String>();
            // 根据第三方分组购物车 boss商品第三方id为0
            List<List<ShoppingCart>> shopThirdList = new ArrayList<>();
            Long thirdId = null;
            if (CollectionUtils.isNotEmpty(baoyou)) {
                for (ShoppingCart pd : baoyou) {
                    thirdId = pd.getThirdId();
                    groups.put(thirdId, "");
                }
                List<ShoppingCart> scart = null;
                if (groups != null && !groups.isEmpty()) {
                    for (Long rawTypeId : groups.keySet()) {
                        scart = new ArrayList<>();
                        for (ShoppingCart sc : baoyou) {

                            if (rawTypeId.equals(sc.getThirdId())) {
                                // 根据thirdId分组
                                scart.add(sc);
                            }
                        }
                        shopThirdList.add(scart);
                    }
                }
                BigDecimal aftermoney = BigDecimal.valueOf(0);
                Long countgoods = 0L;
                List<ShoppingCart> shop = new ArrayList<>();
                for (int m = 0; m < shopThirdList.size(); m++) {
                    // 按third分组判断是否包邮
                    baoyou = shopThirdList.get(m);

                    for (Long markId : markMap.keySet()) {
                        ShoppingCart sc = new ShoppingCart();

                        for (int j = 0; j < baoyou.size(); j++) {
                            if (markId.equals(baoyou.get(j).getMarketing().getMarketingId()) && baoyou.get(j).getThirdId().toString().equals(markMap.get(markId).toString())) {
                                GoodsProduct goodsProduct = goodsProductMapper.queryByGoodsInfoDetail(baoyou.get(j).getGoodsInfoId());
                                BigDecimal goodsMoney = goodsProduct.getGoodsInfoPreferPrice();
                                // 货品价格中间变量
                                BigDecimal goodspriceflag = goodsProduct.getGoodsInfoPreferPrice();

                                if (0 == baoyou.get(j).getThirdId()) {
                                    /* 根据选择的地区查询库存及价格信息 */
                                    ProductWare productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(baoyou.get(j).getGoodsInfoId(), distinctId);
                                    if (null != productWare) {
                                        goodsMoney = productWare.getWarePrice();
                                        goodspriceflag = productWare.getWarePrice();
                                    }

                                }

                                Map<String, Object> mapGoods = new HashMap<String, Object>();

                                // 折扣促销
                                if (baoyou.get(j).getMarketingId() != null && 0 != baoyou.get(j).getMarketingId()) {
                                    // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                                    Marketing mark = marketingMapper.marketingDetail(baoyou.get(j).getMarketingId());
                                    // 参与折扣促销
                                    if (null != mark) {
                                        mapGoods.put(MARKETINGID, mark.getMarketingId());
                                        mapGoods.put(GOODSID, baoyou.get(j).getGoodsInfoId());
                                        // 折扣促销详细信息
                                        PreDiscountMarketing preDiscountMarketing = preDiscountMarketingMapper.selectByMarketId(mapGoods);
                                        if (null != preDiscountMarketing && preDiscountMarketing.getDiscountPrice() != null) {
                                            DecimalFormat myformat = null;
                                            // 抹掉分
                                            if ("1".equals(preDiscountMarketing.getDiscountFlag())) {
                                                myformat = new DecimalFormat("0.0");
                                            } else if ("2".equals(preDiscountMarketing.getDiscountFlag())) {
                                                myformat = new DecimalFormat("0");
                                            } else {
                                                myformat = new DecimalFormat("0.00");
                                            }
                                            // 不四舍五入
                                            myformat.setRoundingMode(RoundingMode.FLOOR);
                                            goodsMoney = BigDecimal.valueOf(Double.valueOf(myformat.format(goodspriceflag.multiply(preDiscountMarketing.getDiscountInfo()))));

                                        }
                                    }
                                }
                                countgoods = baoyou.get(j).getGoodsNum();
                                // 购物车里商品的总价格
                                aftermoney = aftermoney.add(goodsMoney.multiply(BigDecimal.valueOf(countgoods)));
                                sc.setMarketgoodsPrice(aftermoney);
                                sc.setThirdId(baoyou.get(j).getThirdId());
                                sc.setMarketing(baoyou.get(j).getMarketing());
                                shop.add(sc);

                            }

                        }

                        // 分组后的价格
                        for (int k = 0; k < baoyou.size(); k++) {
                            // 计算第三方分组后以及参与包邮分组
                            for (ShoppingCart scra : shop) {
                                // 分组后的包邮促销id和原有的包邮促销id一致
                                // 否则会导致只要有一个没有达到包邮促的条件,后面的包邮促销都会加载到没有包邮里面
                                if (scra.getMarketing().getMarketingId().equals(baoyou.get(k).getMarketing().getMarketingId())
                                        && markId.equals(scra.getMarketing().getMarketingId()) && scra.getThirdId().toString().equals(markMap.get(markId).toString())
                                        && scra.getThirdId().equals(baoyou.get(k).getThirdId())
                                        && scra.getMarketgoodsPrice().compareTo(baoyou.get(k).getMarketing().getShippingMoney()) == -1) {
                                    // 参与包邮但没有达到条件包邮
                                    nobaoyou.add(baoyou.get(k));
                                }
                            }
                        }
                        aftermoney = BigDecimal.ZERO;
                    }

                }

            }
        }
        return new ArrayList<>(new HashSet<>(nobaoyou));

    }

    /**
     * 新产品使用购物车
     *
     * @param customerId
     * @param districtId
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> shopCartMap(Long customerId, Long districtId, HttpServletRequest request) {
        Map<String, Object> cartMap = new HashMap<>();
        List<ShoppingCart> shoplist = new ArrayList<>();
        List<Marketing> marketinglist = new ArrayList<>();


        // 如果用户登录了
        if (customerId != null) {
            // 查询所有购物车商品
            shoplist = shoppingCartMapper.shoppingCartMini(customerId);

            //满足促销处理
            this.checkAndSetLegalFullbuyPresent(shoplist, marketinglist);

        } else {
            try {
                // 获取cookie中的数据
                List<ShopCarUtil> list = loadCookShopCar(request);
                // 定义购物车List
                for (ShopCarUtil aList : list) {
                    // 封装数据
                    ShoppingCart sc = new ShoppingCart();
                    sc.setGoodsInfoId(aList.getProductId());
                    sc.setShoppingCartId(aList.getProductId());
                    sc.setMarketingId(aList.getMarketId());
                    sc.setMarketingActivityId(aList.getMarketActiveId());
                    sc.setGoodsNum(Long.valueOf(aList.getGoodsNum()));
                    sc.setMarketing(null);
                    sc.setFitId(aList.getFitId());
                    sc.setDistinctId(aList.getDistinctId());
                    shoplist.add(sc);
                }


            } catch (UnsupportedEncodingException e) {
                LOGGER.error("新购物车错误", e);
            }

        }
        // 查询购物车商品详细
        shoplist = selectShoppingCartDetail(shoplist, districtId);
        // 查询所有参与的促销
        marketinglist = marketingIdsListUtil(shoplist);
        cartMap.put(SHOPLIST, shoplist);
        cartMap.put(MARKETINGLIST, marketinglist);

        return cartMap;
    }

    /**
     * 如果商品 有限购的话 设置商品的限购数量
     *
     * @param shoplist 购物车中的商品
     */
    private void setGoodsPanicBuyingNum(List<ShoppingCart> shoplist) {

        // 如果购物车或者优惠列表为空 则直接返回
        if (CollectionUtils.isEmpty(shoplist)) {
            return;
        }
        // 遍历购物车中的商品  找出优惠是抢购的 然后设置限购数量
        for (ShoppingCart shoppingCart : shoplist) {
            // 如果当前商品没有优惠 则直接跳过 进入下一次循环
            Long marketingActivityId = shoppingCart.getMarketingActivityId();
            if (null == marketingActivityId || 0 == marketingActivityId) {
                continue;
            }

            // 如果当前商品的优惠列表为空 则直接跳过 进入下一次循环
            if (CollectionUtils.isEmpty(shoppingCart.getMarketingList())) {
                continue;
            }

            // 遍历优惠 找出当前商品的优惠
            for (Marketing marketing : shoppingCart.getMarketingList()) {
                // 如果当前优惠不是抢购优惠 则直接跳过
                if (!"11".equals(marketing.getCodexType())) {
                    continue;
                }
                if (marketingActivityId.equals(marketing.getMarketingId())) {

                    // 用户已经购买当前抢购的数量
                    Integer aleryBuyNum = getUserAlreadyByNum(marketing.getRushs().get(0).getRushId(), shoppingCart.getCustomerId(), shoppingCart.getGoodsDetailBean().getProductVo().getGoodsInfoId());

                    // 限购数量等于  当前抢购的限购数量减去用户已经购买的数量
                    long limitNum = marketing.getRushs().get(0).getRushLimitation() - aleryBuyNum;
                    if (limitNum < 0) {
                        limitNum = 0;
                    }

                    // 设置限购的数量规则。。 库存和限购数量哪个少就拿哪个
                    long stockNum = shoppingCart.getGoodsDetailBean().getProductVo().getGoodsInfoStock();
                    shoppingCart.getGoodsDetailBean().getProductVo().setGoodsInfoStock(stockNum > limitNum ? limitNum : stockNum);
                }

            }

        }
    }


    /**
     * 获得用户已经购买当前抢购的数量
     *
     * @param rushId      抢购id
     * @param customerId  用户id
     * @param goodsInfoId 货品id
     * @return 返回用户已经购买当前抢购的数量
     */
    private Integer getUserAlreadyByNum(Long rushId, Long customerId, Long goodsInfoId) {
        Map<String, Object> rushMap = new HashMap<>();
        rushMap.put("rushId", rushId);
        rushMap.put("customerId", customerId);
        rushMap.put("goodsInfoId", goodsInfoId);
        // 查询用户已经购买当前抢购的数量
        Integer result = rushCustomerMapper.selectByParamMap(rushMap);

        if (null == result) {
            result = 0;
        }
        return result;
    }


    /**
     * 查询ShoppingCart 每个商品的详细信息
     *
     * @param shoppingCartList
     * @param districtId
     */
    public List<ShoppingCart> selectShoppingCartDetail(List<ShoppingCart> shoppingCartList, Long districtId) {
        for (ShoppingCart cart : shoppingCartList) {
            // 如果商品不是套装
            if (cart.getFitId() == null) {
                // 查询商品详细
                /*cart.setGoodsDetailBean(
                        goodsProductService.queryDetailByProductId(cart.getGoodsInfoId(), districtId));*/
                cart.setGoodsDetailBean(
                        goodsProductService.queryDetailByProductId(cart.getGoodsInfoId(), cart.getDistinctId()));
                List<Marketing> marketings = new ArrayList<>();
                GoodsDetailBean goodsDetailBean = cart.getGoodsDetailBean();

                if (goodsDetailBean != null && goodsDetailBean.getProductVo() != null) {
                    // 查询商品参加的促销信息
                    marketings = marketService.selectMarketingByGoodsInfoId(cart.getGoodsInfoId(), goodsDetailBean.getBrand()
                            .getBrandId(), goodsDetailBean.getProductVo().getGoods().getCatId());
                }
                // 赋值
                cart.setMarketingList(marketings);

                // 判断选择的促销是否过期或删除

                for (Marketing mark : marketings) {
                    List<PreDiscountMarketing> discountMarketings = mark.getPreDiscountMarketings();
                    if (discountMarketings == null) {
                        continue;
                    }
                    for (PreDiscountMarketing premark : discountMarketings) {
                        // 参与了折扣
                        if (premark.getGoodsId().equals(cart.getGoodsInfoId())) {
                            String discountFlag;// 抹掉分角标识
                            discountFlag = premark.getDiscountFlag();
                            DecimalFormat myformat;

                            // 抹掉分
                            if ("1".equals(discountFlag)) {
                                myformat = new DecimalFormat("0.0");
                            } else if ("2".equals(discountFlag)) {
                                myformat = new DecimalFormat("0");
                            } else {
                                myformat = new DecimalFormat("0.00");
                            }
                            myformat.setRoundingMode(RoundingMode.FLOOR);
                            BigDecimal goodsPrice = goodsDetailBean.getProductVo().getGoodsInfoPreferPrice()
                                    .multiply(premark.getDiscountInfo());
                            goodsPrice = BigDecimal.valueOf(Double.valueOf(myformat.format(goodsPrice)));
                            // 不四舍五入
                            goodsDetailBean.getProductVo().setGoodsInfoPreferPrice(goodsPrice);
                        }

                    }

                    //填充满赠促销已选中赠品信息
                    //this.fillMarketPresents(mark, cart, cartWareUtil.getDistrictId() );

                }
                int marketingNum = 0;// 记录其他促销
                int marketingActivityNum = 0;// 记录折扣促销

                for (Marketing marketing : marketings) {
                    // 判断折扣促销是否过期或删除
                    if (cart.getMarketingId() != null && cart.getMarketingId().equals(marketing.getMarketingId())) {
                        marketingNum += 1;

                    }
                    // 判断其他促销促销是否过期或删除
                    if (cart.getMarketingActivityId() != null
                            && cart.getMarketingActivityId().equals(marketing.getMarketingId())) {
                        marketingActivityNum += 1;
                    }

                }
                // 折扣促销过期或删除
                if (marketingNum == 0) {
                    cart.setMarketingId(null);
                }
                // 其他促销促销过期或删除
                if (marketingActivityNum == 0) {
                    cart.setMarketingActivityId(null);
                }
            } else {
                // 如果商品是套装
                cart.setThirdId(0L);
                Long stock = null;
                GoodsGroupVo groupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());
                // 该套装下所有的商品
                List<GoodsGroupReleProductVo> goodsGroupReleProductVos = groupVo.getProductList();
                BigDecimal totalPrice = BigDecimal.ZERO;

                for (int j = 0; j < goodsGroupReleProductVos.size(); j++) {

                    GoodsGroupReleProductVo relProduct = goodsGroupReleProductVos.get(j);
                    GoodsProductDetailVo relProductDetail = relProduct.getProductDetail();
                    if (relProductDetail != null) {
                        GoodsProductVo productVo =
                                goodsProductService.findProductById(relProductDetail.getGoodsInfoId(),
                                        districtId);
                        if (productVo == null) {
                            cart.setAvailable(false);
                            break;
                        }
                        BigDecimal before = BigDecimal.ZERO;
                        for (ShoppingCart sp : shoppingCartList) {
                            if (sp.getGoodsInfoId().intValue() == productVo.getGoodsInfoId().intValue()) {
                                //购物车里已有套装里的商品
                                before = BigDecimal.valueOf(sp.getGoodsNum());
                            } else {
                                before = BigDecimal.ZERO;
                            }
                        }

                        BigDecimal goodstock = BigDecimal.valueOf(productVo.getGoodsInfoStock());
                        goodstock = goodstock.subtract(before);
                        BigDecimal num = BigDecimal.valueOf(relProduct.getProductNum());
                        goodstock = goodstock.divide(num, BigDecimal.ROUND_FLOOR);
                        if (stock == null) {
                            stock = goodstock.longValue();
                        } else {
                            if (stock > goodstock.longValue()) {
                                stock = goodstock.longValue();
                            }
                        }
                        // 重新设置套装下商品库存
                        groupVo.getProductList().get(j).getProductDetail()
                                .setGoodsInfoStock(productVo.getGoodsInfoStock());
                        totalPrice = totalPrice.add(
                                productVo.getGoodsInfoPreferPrice()
                                        .multiply(BigDecimal.valueOf(relProduct.getProductNum()))
                        );

                    }
                }
                /**
                 * 单个套装的总价 = 商品总价格-套装优惠价格
                 */
                groupVo.setPrice(totalPrice.subtract(groupVo.getGroupPreferamount()));
                groupVo.setStock(stock);
                // 重新设置list
                groupVo.setProductList(goodsGroupReleProductVos);
                // 设置套装商品库存end
                cart.setGoodsGroupVo(groupVo);
            }
        }
//        shoppingCartList
        return newArrayList(filter(shoppingCartList, new Predicate<ShoppingCart>() {
            public boolean apply(ShoppingCart cart) {
                return cart.isAvailable();
            }
        }));
    }




    /**
     * 查询ShoppingCart 每个商品的详细信息 不计算折扣
     *  KKK 计算套装价格
     * @param shoppingCartList
     * @param cartWareUtil
     */
    public void shoppingCartDetail(List<ShoppingCart> shoppingCartList, ShoppingCartWareUtil cartWareUtil) {

        for (ShoppingCart cart : shoppingCartList) {
            // 如果商品不是套装
            Long districtId = cartWareUtil.getDistrictId();
            if (cart.getFitId() == null) {
                // 查询商品详细
                cart.setGoodsDetailBean(
                        goodsProductService.queryDetailByProductId(cart.getGoodsInfoId(), districtId));
                List<Marketing> marketings = new ArrayList<>();
                GoodsDetailBean goodsDetailBean = cart.getGoodsDetailBean();
                if (goodsDetailBean != null && goodsDetailBean.getProductVo() != null) {

                    // 查询商品参加的促销信息
                    marketings = marketService.selectMarketingByGoodsInfoId(cart.getGoodsInfoId(), goodsDetailBean.getBrand()
                            .getBrandId(), goodsDetailBean.getProductVo().getGoods().getCatId());
                }
                // 赋值
                cart.setMarketingList(marketings);


                int marketingNum = 0;// 记录其他促销
                int marketingActivityNum = 0;// 记录折扣促销

                for (Marketing marketing : marketings) {
                    // 判断折扣促销是否过期或删除
                    if (cart.getMarketingId() != null && cart.getMarketingId().equals(marketing.getMarketingId())) {
                        marketingNum += 1;

                    }
                    // 判断其他促销促销是否过期或删除
                    if (cart.getMarketingActivityId() != null
                            && cart.getMarketingActivityId().equals(marketing.getMarketingId())) {
                        marketingActivityNum += 1;
                    }

                }
                // 折扣促销过期或删除
                if (marketingNum == 0) {
                    cart.setMarketingId(null);
                }
                // 其他促销促销过期或删除
                if (marketingActivityNum == 0) {
                    cart.setMarketingActivityId(null);
                }
            }
            else {
                Long stock = null;
                BigDecimal totalPrice = BigDecimal.ZERO;

                // 如果商品是套装
                GoodsGroupVo groupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());

                // 给套装购物车塞上套装的第三方id
                cart.setThirdId(groupVo.getThirdId());
                // 该套装下所有的商品
                List<GoodsGroupReleProductVo> goodsGroupReleProductVos = groupVo.getProductList();
                for (int j = 0; j < goodsGroupReleProductVos.size(); j++) {

                    GoodsGroupReleProductVo relProduct = goodsGroupReleProductVos.get(j);
                    GoodsProductDetailVo relProductDetail = relProduct.getProductDetail();
                    GoodsProductVo productVo = goodsProductService.findProductById(relProductDetail.getGoodsInfoId(),
                            districtId);

                    if (productVo == null) {
                        cart.setAvailable(false);
                        break;
                    }

                    relProductDetail.setGoodsInfoPreferPrice(productVo.getGoodsInfoPreferPrice());
                    BigDecimal goodstock = BigDecimal.valueOf(productVo.getGoodsInfoStock());
                    BigDecimal num = BigDecimal.valueOf(relProduct.getProductNum());
                    goodstock = goodstock.divide(num, BigDecimal.ROUND_HALF_EVEN);
                    if (stock == null) {
                        stock = goodstock.longValue();
                    } else {
                        if (stock > goodstock.longValue()) {
                            stock = goodstock.longValue();
                        }
                    }
                    // 重新设置套装下商品库存
                    groupVo.getProductList().get(j).getProductDetail().setGoodsInfoStock(productVo.getGoodsInfoStock());
                    totalPrice = totalPrice.add(
                            productVo.getGoodsInfoPreferPrice()
                                    .multiply(BigDecimal.valueOf(relProduct.getProductNum()))
                    );
                }
                /**
                 * 单个套装的总价 = 商品总价格-套装优惠价格
                 */
                groupVo.setPrice(totalPrice.subtract(groupVo.getGroupPreferamount()));
                groupVo.setStock(stock);
                // 重新设置list
                groupVo.setProductList(goodsGroupReleProductVos);
                cart.setGoodsGroupVo(groupVo);
                // 设置套装商品库存end

            }
        }


    }


    /**
     * 判断购物车商品是否存在删除或下架的情况
     *
     * @param box 购物车id集合
     * @return true:商品数据正常,false:商品数据不正常
     */
    @Override
    public boolean checkHasErrorProduct(Long[] box) {
        //查询未删除的购物车信息
        List<ShoppingCart> shoppingCarts = this.shopCartListByIds(Arrays.asList(box));
        if (CollectionUtils.isEmpty(shoppingCarts))
            return false;
        else {
            if (shoppingCarts.size() != box.length)
                return false;
        }
        for (ShoppingCart shoppingCart : shoppingCarts) {
            //判断货品数据是否下架或删除
            if(shoppingCart.getFitId() != null){// KKK 套装
                return true;
            }
            else if (goodsProductService.queryProductByGoodsInfoId(shoppingCart.getGoodsInfoId()) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Integer countCart(Long customerId) {
        return shoppingCartMapper.countByCustomerId(customerId);
    }

    @Override
    public Integer countCartInCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        List<ShopCarUtil> list;
        try {
            // 获取cookie中的数据
            list = loadCookShopCar(request);
        } catch (UnsupportedEncodingException e) {
            return 0;
        }

        int count = 0;
        // 定义购物车List
        for (ShopCarUtil cart : list) {
            count += cart.getGoodsNum();
        }
        return count;
    }

    public List<ShoppingCart> shopCartListByIds(List<Long> list) {

        return shoppingCartMapper.shopCartListByIds(list);
    }

    /**
     * 填充购物车满赠促销已选中的赠品信息
     *
     * @param mark         满赠促销
     * @param shoppingCart 购物车
     * @param districtId   地区id
     */
    private void fillMarketPresents(Marketing mark, ShoppingCart shoppingCart, Long districtId) {
        if (mark.getFullbuyPresentMarketings() != null && CollectionUtils.isNotEmpty(mark.getFullbuyPresentMarketings())) {
            String presentScopeIds = shoppingCart.getPresentScopeIds();
            //赠品范围ids
            String[] scopeIds = presentScopeIds.split(",");
            List<GoodsProduct> goodsProducts = new ArrayList<>();
            //根据赠品范围ids查询赠品范围list
            List<FullbuyPresentScope> fullbuyPresentScopes = fullbuyPresentScopeMapper.queryByScopeIds(Arrays.asList(scopeIds));
            for (int i = 0; i < fullbuyPresentScopes.size(); i++) {
                //根据赠品id查询赠品信息(货品)
                GoodsProduct goodsProduct = goodsProductMapper.queryByGoodsInfoDetail(fullbuyPresentScopes.get(i).getScopeId());
                // 设置boss商品分仓库存
                this.setProductWareStock(goodsProduct, districtId);
                //赠品的赠送数量
                goodsProduct.setScopeNum(fullbuyPresentScopes.get(i).getScopeNum());
                goodsProducts.add(goodsProduct);
            }

            // 填充选中的赠品(即货品)
            mark.setPresentGoodsProducts(goodsProducts);
        }
    }

    /**
     * 根据地区id设置货品的分仓库存
     *
     * @param goodsProduct 货品
     * @param districtId   地区id
     */
    private void setProductWareStock(GoodsProduct goodsProduct, Long districtId) {
        if (goodsProduct == null)
            return;

        //设置boss货品的分仓库存
        if ("0".equals(goodsProduct.getIsThird()) && null != districtId && districtId > 0) {
                                        /* 根据选择的地区查询库存及价格信息 */
            ProductWare productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(goodsProduct.getGoodsInfoId(), districtId);
                                        /* 如果查询到的关联信息不能为空,就替换bean的价格和库存为查询到的关联记录 */
            if (null != productWare) {
                //分仓库存
                goodsProduct.setGoodsInfoStock(productWare.getWareStock());
            } else {
                //分仓库存
                goodsProduct.setGoodsInfoStock(0L);
            }
        }
    }

    /**
     * 判断并设置符合条件的满赠促销规则级别
     *
     * @param shoplist      购物车list
     * @param marketinglist 促销list
     */
    private void checkAndSetLegalFullbuyPresent(List<ShoppingCart> shoplist, List<Marketing> marketinglist) {

        //参数校验
        if (CollectionUtils.isEmpty(shoplist) && CollectionUtils.isEmpty(marketinglist)) {
            return;
        }

        for (int i = 0; i < marketinglist.size(); i++) {
            Marketing marketing = marketinglist.get(i);
            List<FullbuyPresentMarketing> fullbuyPresentMarketings = marketing.getFullbuyPresentMarketings();
            //满赠促销
            if ("6".equals(marketing.getCodexType())) {
                //参加满赠促销商品总价
                BigDecimal fullbuyPresentPrice = BigDecimal.ZERO;
                //参加满赠促销商品总数量
                Long fullbuyPresentNum = 0L;

                for (ShoppingCart shoppingCart : shoplist) {
                    //参加了满赠促销的货品
                    if (shoppingCart.getMarketingActivityId() == marketing.getMarketingId()) {
                        //商品小计累加
                        fullbuyPresentPrice = fullbuyPresentPrice.add(shoppingCart.getGoodsPrice().multiply(BigDecimal.valueOf(shoppingCart.getGoodsNum())));
                        //商品数量累加
                        fullbuyPresentNum = fullbuyPresentNum + shoppingCart.getGoodsNum();
                    }
                }

                if (CollectionUtils.isNotEmpty(fullbuyPresentMarketings)) {
                    marketing.setFullbuyPresentMarketing(null);
                    //满赠促销规则倒须
                    for (FullbuyPresentMarketing fullbuyPresentMarketing : fullbuyPresentMarketings) {
                        //满金额
                        if ("0".equals(fullbuyPresentMarketing.getPresentType())) {
                            //商品小计大于等于满赠满多少钱
                            if (fullbuyPresentPrice.compareTo(fullbuyPresentMarketing.getFullPrice()) >= 0) {
                                //填充满赠促销符合条件的满赠级别
                                marketing.setFullbuyPresentMarketing(fullbuyPresentMarketing);
                                break;
                            }
                        } else if ("1".equals(fullbuyPresentMarketing.getPresentType())) {
                            //满件数
                            if (fullbuyPresentNum >= fullbuyPresentMarketing.getFullPrice().longValue()) {
                                //填充满赠促销符合条件的满赠级别
                                marketing.setFullbuyPresentMarketing(fullbuyPresentMarketing);
                                break;
                            }
                        }
                    }
                }
            }

        }
    }


    /**
     * 填充购物车赠品信息
     *
     * @param shoppingCarts 提交订单的购物车集合
     * @param boxgift       购物车和赠品关联信息
     */
    private void fillShoppingCartGifts(List<ShoppingCart> shoppingCarts, String[] boxgift, Long districtId) {

        //参数校验
        if (CollectionUtils.isEmpty(shoppingCarts) || boxgift == null || boxgift.length == 0) {
            return;
        }

        List<String> gifts = Arrays.asList(boxgift);
        for (int i = 0; i < shoppingCarts.size(); i++) {
            ShoppingCart shoppingCart = shoppingCarts.get(i);
            for (int j = 0; j < gifts.size(); j++) {
                String gift = gifts.get(j);
                if (StringUtils.isNotBlank(gift) && gift.indexOf(":") > 0) {
                    //购物车和赠品关联信息里的购物车id
                    Long shoppingCartId = Long.valueOf(gift.substring(0, gift.indexOf(":")));
                    //购物车和赠品关联信息里的赠品范围ids
                    String presentScopeIds = gift.substring(gift.indexOf(":") + 1);
                    if (shoppingCart.getShoppingCartId().intValue() == shoppingCartId.intValue()) {
                        //赠品范围ids
                        String[] scopeIds = StringUtils.split(presentScopeIds, "a");
//                        String[] scopeIds = presentScopeIds.split("a");
                        LOGGER.info(scopeIds.length + "=============scopeIds=======");
                        List<GoodsProduct> goodsProducts = new ArrayList<>();
                        //根据赠品范围ids查询赠品范围list
                        List<FullbuyPresentScope> fullbuyPresentScopes = fullbuyPresentScopeMapper.queryByScopeIds(Arrays.asList(scopeIds));
                        for (int k = 0; k < fullbuyPresentScopes.size(); k++) {
                            //根据赠品id查询赠品信息(货品)
                            GoodsProduct goodsProduct = goodsProductMapper.queryByGoodsInfoDetail(fullbuyPresentScopes.get(k).getScopeId());
                            // 设置boss商品分仓库存
                            this.setProductWareStock(goodsProduct, districtId);
                            //赠品的赠送数量
                            goodsProduct.setScopeNum(fullbuyPresentScopes.get(k).getScopeNum());
                            //赠品范围主键id
                            goodsProduct.setPresentScopeId(fullbuyPresentScopes.get(k).getPresentScopeId());
                            goodsProducts.add(goodsProduct);
                        }
                        shoppingCart.setPresentGoodsProducts(goodsProducts);

                    }
                }
            }
        }
    }
}
