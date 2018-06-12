/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.m.order.service.impl;

import com.ningpai.common.kuaidi.KuaiDiUtil;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponRange;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.dao.ExpressInfoMapper;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.freighttemplate.dao.SysLogisticsCompanyMapper;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.util.CalcStockUtil;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.m.common.bean.Sms;
import com.ningpai.m.common.dao.SmsMapper;
import com.ningpai.m.common.util.SmsPost;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.service.CustomerService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.directshop.bean.DirectShop;
import com.ningpai.m.directshop.service.DirectShopService;
import com.ningpai.m.goods.dao.GoodsProductMapper;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.m.order.bean.OrderAddress;
import com.ningpai.m.order.service.OrderMService;
import com.ningpai.m.order.util.OrderContainerUtil;
import com.ningpai.m.shoppingcart.bean.ShoppingCart;
import com.ningpai.m.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.*;
import com.ningpai.marketing.dao.FullbuyPresentScopeMapper;
import com.ningpai.marketing.dao.GrouponMapper;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.*;
import com.ningpai.order.dao.*;
import com.ningpai.order.service.OrderCouponService;
import com.ningpai.order.service.OrderService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.Pay;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IExpressConfBiz;
import com.ningpai.util.MyLogger;
import com.ningpai.util.UtilDate;
import com.ningpai.wxpay.utils.GetWxOrderno;
import com.ningpai.wxpay.utils.RequestHandlerUtil;
import com.ningpai.wxpay.utils.Sha1Util;
import com.ningpai.wxpay.utils.TenpayUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单支付service实现
 *
 * @author NINGPAI-LIH
 */
@Service("OrderMService")
public class OrderMServiceImpl extends BasicSqlSupport implements OrderMService {

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(OrderMServiceImpl.class);

    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String CUSTOMERID = "customerId";

    @Resource(name = "customerAddressServiceM")
    private CustomerAddressService addressService;

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService shoppingCartService;

    @Resource(name = "HsiteGoodsProductMapper")
    private GoodsProductMapper goodsProductMapper;

    @Resource(name = "FreightTemplateMapper")
    private FreightTemplateMapper freightTemplateMapper;

    @Resource(name = "OrderContainerRelationMapper")
    private OrderContainerRelationMapper relationMapper;

    @Resource(name = "expressConfBizImpl")
    private IExpressConfBiz iExpressConfBiz;

    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;
    @Resource(name = "ShoppingCartMapper")
    private ShoppingCartMapper shoppingCartMapper;

    @Resource(name = "GoodsProductService")
    private com.ningpai.goods.service.GoodsProductService goodsProductService;

    @Resource(name = "OrderService")
    private OrderService orderService;

    @Resource(name = "OrderExpressMapper")
    private OrderExpressMapper orderExpressMapper;

    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;

    @Resource(name = "OrderGoodsInfoCouponMapper")
    private OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper;

    @Resource(name = "OrderGoodsInfoGiftMapper")
    private OrderGoodsInfoGiftMapper orderGoodsInfoGiftMapper;

    @Resource(name = "SysLogisticsCompanyMapper")
    private SysLogisticsCompanyMapper sysLogisticsCompanyMapper;

    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    @Resource(name = "expressInfoMapperThird")
    private ExpressInfoMapper expressInfoMapperThird;
    @Resource(name = "customerServiceM")
    private CustomerService customerService;

    // 自提点
    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;
    @Resource(name = "MarketingService")
    private MarketingService marketService;
    @Resource(name = "GoodsGroupService")
    private GoodsGroupService goodsGroupService;

    @Resource(name = "HsiteGoodsProductService")
    private GoodsProductService siteGoodsProductService;
    @Resource(name = "FreightExpressMapper")
    private FreightExpressMapper freightExpressMapper;
    @Resource(name = "MDirectShopService")
    private DirectShopService directShopService;
    @Resource(name = "basicSetService")
    private BasicSetService basicService;

    @Resource(name = "smsMapperM")
    private SmsMapper mapper;

    @Resource(name = "RushCustomerMapper")
    private RushCustomerMapper rushCustomerMapper;

    @Autowired
    private ShoppingCartAddressService shoppingCartAddressService;

    @Resource(name = "OrderMapper")
    private OrderMapper orderMapper;


    /**
     * 会员积分接口
     */
    @Resource(name = "customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;

    @Resource(name = "GrouponMapper")
    private GrouponMapper grouponMapper;

    @Resource(name = "PreDiscountMarketingMapper")
    private PreDiscountMarketingMapper preDiscountMarketingMapper;

    /**
     * 满赠赠品范围
     */
    @Resource(name = "fullbuyPresentScopeMapper")
    private FullbuyPresentScopeMapper fullbuyPresentScopeMapper;

    @Resource(name = "OrderCouponService")
    private OrderCouponService orderCouponService;


    /**
     * 获取第三方订单价格
     */
    public Order getThirdOrderPrice(Long thirdId, String codeNo, Long amount, Long customerId, String orderCode, Long cityId, Long distinctId, List<ShoppingCart> shoplist,
                                    boolean typeIdflag) {

        Order order = new Order();
        Map<String, Object> map = shoppingCartService.getPayorderThirdPriceMap(thirdId, shoplist, distinctId);
        String stock = map.get("stock").toString();
        // 判断库存
        // 有商品存在无货返回
        if ("0".equals(stock)) {
            return null;
        }

        // 订单交易总金额
        BigDecimal sumPrice = BigDecimal.valueOf(Double.valueOf(map.get("sumPrice").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 原总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(Double.valueOf(map.get("sumOldPrice").toString()));
        // boss交易价格
        BigDecimal bossSumPrice = BigDecimal.valueOf(Double.valueOf(map.get("bossSumPrice").toString()));
        // 总优惠金额
        BigDecimal prePrice = sumOldPrice.subtract(sumPrice);


        order.setPromotionsPrice(prePrice);

        /**
         * 计算会员折扣
         */
        if (0 == thirdId) {
            // 去除抢购的boss总金额
            BigDecimal noRushSumPrice = BigDecimal.valueOf(Double.valueOf(map.get("rushSumPrice").toString()));
            // 去除抢购的boss总优惠金额
            BigDecimal noRushPrePrice = BigDecimal.valueOf(Double.valueOf(map.get("rushPrePrice").toString()));

            CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId(customerId);
            //查询会员折扣
            BigDecimal pointDiscount = customerPointServiceMapper.getCustomerDiscountByPoint(Integer.parseInt(customerPoint.getPointSum().toString()));
            // 订单交易总金额
//            BigDecimal marketSumPrice = sumPrice;
            //会员折扣优惠的价格  此处先将不含抢购的优惠价减掉  再算出会员折扣后的优惠价
            BigDecimal discountPrice = noRushSumPrice.subtract(noRushPrePrice)
                    .multiply(BigDecimal.ONE.subtract(pointDiscount))
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN);

            sumPrice = sumPrice.subtract(discountPrice);
            prePrice = prePrice.add(discountPrice);
            // KKK  设置会员折扣
            order.setDiscountPrice(discountPrice);
        }

        if (codeNo != null && !"".equals(codeNo)) {
            Coupon coupon = couponService.selectCouponByCodeNo(codeNo);

            if (coupon != null && thirdId.equals(coupon.getBusinessId())) {
                // 直降
                if ("1".equals(coupon.getCouponRulesType())) {
                    // 计算交易价格减去金额
                    sumPrice = sumPrice.subtract(coupon.getCouponStraightDown().getDownPrice());
                    if (sumPrice.compareTo(BigDecimal.ZERO) == -1 || sumPrice.compareTo(BigDecimal.ZERO) == 0) {
                        sumPrice = sumPrice.add(coupon.getCouponStraightDown().getDownPrice());
                        prePrice = prePrice.add(sumPrice.subtract(BigDecimal.valueOf(0.01)));
                        sumPrice = BigDecimal.valueOf(0.01);
                    } else {
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponStraightDown().getDownPrice());
                    }
                    order.setCouponPrice(coupon.getCouponStraightDown().getDownPrice());

                }
                // 满减
                if ("2".equals(coupon.getCouponRulesType())) {
                    // 计算交易价格减去金额
                    sumPrice = sumPrice.subtract(coupon.getCouponFullReduction().getReductionPrice());

                    if (sumPrice.compareTo(BigDecimal.ZERO) == -1 || sumPrice.compareTo(BigDecimal.ZERO) == 0) {
                        sumPrice = sumPrice.add(coupon.getCouponFullReduction().getReductionPrice());
                        prePrice = prePrice.add(sumPrice.subtract(BigDecimal.valueOf(0.01)));
                        sumPrice = BigDecimal.valueOf(0.01);
                    } else {
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponFullReduction().getReductionPrice());
                    }
                    order.setCouponPrice(coupon.getCouponFullReduction().getReductionPrice());
                }
                // 订单使用的优惠券
                order.setCouponNo(codeNo);

            }
        }
        // 计算运费
        BigDecimal expressPrice = BigDecimal.ZERO;
        if (typeIdflag) {
            List<ShoppingCart> nobaoyou = shoppingCartService.getNobaoyouShoppingcarts(shoplist, distinctId);
            expressPrice = shoppingCartService.calExpressPriceByThirdId(thirdId, cityId, nobaoyou);
        }
        // 计算交易价格加上运费
        sumPrice = sumPrice.add(expressPrice);
        /* 积分兑换订单金额 */
        if (0 == thirdId && null != amount && amount != 0) {
            // 获得当前用户的积分信息

            // 积分兑换规则
            PointSet pointSet = this.couponService.selectPointSet();
            // 根据积分兑换规则 计算积分兑换金额

            Double bossjifen = Double.valueOf(bossSumPrice.divide(pointSet.getConsumption()).multiply(BigDecimal.TEN).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());

            if (bossjifen < amount) {
                return null;
            }

            // 转换类型
            BigDecimal zhuanhuan = new BigDecimal(amount);
            // 根据积分兑换规则 计算积分兑换金额
            BigDecimal disparityPrice = zhuanhuan.multiply(pointSet.getConsumption());
            disparityPrice = disparityPrice.divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            // 对兑换处的价格进行四舍五入
            BigDecimal jiFenDuiHuan = disparityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            // 计算最后订单金额
            sumPrice = sumPrice.subtract(jiFenDuiHuan);
            // 对兑换处的价格进行四舍五入
            sumPrice = sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
            //订单的积分兑换金额
            order.setJfPrice(jiFenDuiHuan);
            // 修改会员的积分
            this.updatePoint(customerId, amount);
            this.insertExchangeCusmomer(customerId, orderCode, amount, disparityPrice, pointSet.getConsumption());

        }


        if (sumPrice.compareTo(BigDecimal.ZERO) == -1 || sumPrice.compareTo(BigDecimal.ZERO) == 0) {
            sumPrice = BigDecimal.valueOf(0.01);
        }

        order.setOrderPrice(sumPrice);
        order.setOrderOldPrice(sumOldPrice);
        order.setOrderPrePrice(prePrice);
        order.setExpressPrice(expressPrice);

        return order;
    }

    /**
     * 新流程订单提交
     *
     * @param duiHuanJiFen
     * @param invoiceType
     * @param invoiceTitle
     * @param request
     * @param shoppingCartId
     * @param typeId
     * @param orderAddress
     * @param deliveryPointId
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    @Transactional
    public List<Order> newsubmitOrder(Long duiHuanJiFen, String invoiceType, String invoiceTitle, HttpServletRequest request, Long[] shoppingCartId, Long typeId,
                                      OrderAddress orderAddress, Long deliveryPointId, String[] presentScopeId) throws UnsupportedEncodingException {
        // 优惠劵码
        String codeNo = request.getParameter("codeNo");
        Long custAddress;
        if (StringUtils.isNotEmpty(request.getParameter("addressId"))) {
            custAddress = Long.parseLong(request.getParameter("addressId"));
        } else {
            return Collections.emptyList();
        }
        Long chPay = 1L;
        if (StringUtils.isNotEmpty(request.getParameter("ch_pay"))) {
            chPay = Long.valueOf(request.getParameter("ch_pay"));
        }
        // 第三方支付方式目前写死只支持在线支付
        Long chPaythird = 1L;
        // 当前登录成功的会员
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        CustomerAddress ca = null;
        Long distinctId = null;

        // 查询收货地址
        if (customerId != null) {

            ca = addressService.queryDefaultAddr(customerId);
            if (ca != null && ca.getDistrict() != null) {
                distinctId = ca.getDistrict().getDistrictId();
            }
        }

        // 仓库信息
        List<CalcStockUtil> calcStockUtils = new ArrayList<>();
        // 获取所有所有选中商品信息
        List<ShoppingCart> cartlist = shoppingCartMapper.shopCartListByIds(Arrays.asList(shoppingCartId));

        if (CollectionUtils.isEmpty(cartlist)) {
            return Collections.emptyList();
        }
        Map<Long, ShoppingCart> thirdIdMap = new HashMap<>();
        for (ShoppingCart cart : cartlist) {
            if (cart.getFitId() == null) {
                thirdIdMap.put(cart.getThirdId(), cart);
            } else {

                // 如果商品是套装
                GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());
                if (null != goodsGroupVo) {

                    thirdIdMap.put(goodsGroupVo.getThirdId(), cart);
                    cart.setGoodsGroupVo(goodsGroupVo);
                    cart.setThirdId(goodsGroupVo.getThirdId());
                }
            }
        }

        // 主订单号
        String orderOldCode = UtilDate.mathString(new Date());
        List<Order> maps = new ArrayList<>();
        Map<String, Object> para = new HashMap<>();
        ShoppingCartWareUtil cartWareUtil = shoppingCartAddressService.loadAreaFromRequest(request);

        for (Long thirdId : thirdIdMap.keySet()) {

            // 查询购买的商品
            List<OrderGoods> oglist = new ArrayList<>();
            for (ShoppingCart cart : cartlist) {
                // cartItem.goodsNum
                Long cartNum = cart.getGoodsNum();

                if (cart.getFitId() != null) {
                    List<OrderGoods> groupOrderGoodslist = new ArrayList<>();

                    if (thirdId.equals(cart.getGoodsGroupVo().getThirdId())) {


                        //套装商品除最后一件商品的总价
                        // 获取此套装下的所有货品
                        List<GoodsProductVo> goodsProducts = goodsProductMapper.queryDetailByGroupId(cart.getFitId());

                        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = cart.getGoodsGroupVo().getProductList();
                        //循环goodsProduct，货品数量为套装中该货品数量
                        for (GoodsProductVo vo : goodsProducts) {
                            for (GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos) {
                                if (goodsGroupReleProductVo.getProductId().intValue() == vo.getGoodsInfoId().intValue()) {
                                    vo.setGroupProductNum((BigDecimal.valueOf(cartNum).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum()))));
                                }
                            }
                        }

                        //套装商品总的价格
                        BigDecimal groupProductAllPrice = BigDecimal.ZERO;

                        for (int j = 0; j < goodsProducts.size(); j++) {
                            // 查询库存
                            GoodsProductVo goodsProduct = goodsProducts.get(j);
                            ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(goodsProduct.getGoodsInfoId(), distinctId);
                            if (productWare != null && productWare.getWareStock() - cartNum <= 0) {
                                // 设置商品库存
                                return Collections.emptyList();

                            }
                            long ogNum = 0;
                            OrderGoods og = new OrderGoods();
                            for (GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos) {
                                if (goodsGroupReleProductVo.getProductId().intValue() == goodsProduct.getGoodsInfoId().intValue()) {
                                    //套装中某件货品数量=创建套装时的件数*购买套装数量
                                    Long relProductNum = goodsGroupReleProductVo.getProductNum();
                                    ogNum = (BigDecimal.valueOf(cartNum)
                                            .multiply(BigDecimal.valueOf(relProductNum)))
                                            .longValue();
                                    og.setGoodsInfoNum(ogNum);
                                }
                            }

                            //计算套装商品总价
                            BigDecimal warePrice;
                            if (productWare != null) {
                                warePrice = productWare.getWarePrice();
                            } else {
                                warePrice = goodsProduct.getGoodsInfoPreferPrice();
                            }
                            og.setGoodsInfoOldPrice(warePrice);
                            groupProductAllPrice = groupProductAllPrice
                                    .add(warePrice.multiply(goodsProduct.getGroupProductNum()));

                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            calcStockUtil.setIsThird(goodsProduct.getIsThird());
                            calcStockUtil.setDistinctId(cartWareUtil.getDistrictId());
                            // 商品id
                            calcStockUtil.setProductId(goodsProduct.getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock((int) ogNum);
                            calcStockUtils.add(calcStockUtil);

                            og.setGoodsInfoId(goodsProducts.get(j).getGoodsInfoId());
                            og.setGoodsId(goodsProducts.get(j).getGoodsId());
                            //商品组合标记
                            og.setIsGroup("1");

                            og.setDistinctId(cartWareUtil.getDistrictId());
                            og.setBuyTime(new Date());
                            og.setEvaluateFlag("0");
                            og.setDelFlag("0");


                            groupOrderGoodslist.add(og);

                        }
                        BigDecimal allPriceExceptLast = BigDecimal.ZERO;

                        /**
                         * KKK 特惠组合 优惠分摊
                         */
                        BigDecimal groupPreferamount = cart.getGoodsGroupVo().getGroupPreferamount()
                                .multiply(BigDecimal.valueOf(cartNum));
                        for (int i = 0; i < groupOrderGoodslist.size(); i++) {
                            OrderGoods og = groupOrderGoodslist.get(i);
                                Long ogNum = og.getGoodsInfoNum();
                                // 套装优惠价格
                                boolean isLastItem = i == (goodsProducts.size() - 1);
                                if (isLastItem) {
                                    //套装货品最后一件优惠价格=总优惠价格-前面所有货品总价（防止不整除出现少0.01）
                                    og.setProductGroupPrice(groupPreferamount.subtract(allPriceExceptLast));
                                } else {
                                    BigDecimal rate = og.getGoodsInfoOldPrice()
                                            .multiply(BigDecimal.valueOf(ogNum))
                                            .divide(groupProductAllPrice, 4, BigDecimal.ROUND_HALF_EVEN);
                                    og.setProductGroupPrice(groupPreferamount.multiply(rate));
                                    //套装商品除了最后一个商品的总价（套装价格分摊不整除情况）
                                    allPriceExceptLast = allPriceExceptLast.add(og.getProductGroupPrice());
                                }
                                // 最终售价
//                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice().subtract(og.getGoodsCouponPrice().divide(BigDecimal.valueOf(ogNum), 2, BigDecimal.ROUND_HALF_EVEN)));
                                og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                                // 小计金额
                                og.setGoodsInfoSumPrice(og.getGoodsInfoPrice().multiply(BigDecimal.valueOf(ogNum)));
                        }

                    }
                oglist.addAll(groupOrderGoodslist);
                } else {

                    // 查询商品详细
                    cart.setGoodsDetailBean(siteGoodsProductService.queryDetailBeanByProductId(cart.getGoodsInfoId(),
                            cart.getDistinctId()));
                    // 判断购买商品是否属于此商家
                    if (thirdId.equals(cart.getGoodsDetailBean().getProductVo().getThirdId())) {

                        if (thirdId == 0 && distinctId != null) {
                            // 查询库存
                            ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(cart.getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId(), distinctId);
                            if (productWare != null) {
                                // 设置商品库存
                                cart.getGoodsDetailBean().getProductVo().setGoodsInfoStock(productWare.getWareStock());
                                cart.getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(productWare.getWarePrice());
                                if (productWare.getWareStock() - cartNum < 0) {
                                    return Collections.emptyList();
                                }

                            }
                        } else {
                            //商家的货品 判断
                            if (cart.getGoodsDetailBean().getProductVo().getGoodsInfoStock() - cartNum < 0) {
                                return Collections.emptyList();
                            }
                        }


                        BigDecimal goodsprice = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                        DecimalFormat myformat = new DecimalFormat("0.00");
                        // 货品价格中间变量
                        BigDecimal goodspriceflag = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();

                        //单该货品同时参与了团购和折扣时,优先级团购优先
                        if (cart.getGoodsGroupId() != null) {
                            // 从购物车里得到促销id重新从数据库查询,防止当前(团购促销)已经过期;
                            Marketing mark = marketService.querySimpleMarketingById(cart.getGoodsGroupId());
                            if (mark != null) {
                                Groupon groupon = grouponMapper.selectByMarketId(mark.getMarketingId());
                                if (groupon != null) {

                                    goodsprice = goodspriceflag.multiply(groupon.getGrouponDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                                }
                            }
                        }
                        // 折扣促销
                        if (cart.getGoodsGroupId() == null && cart.getMarketingId() != null && 0 != cart.getMarketingId()) {
                            // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                            Marketing mark = marketService.marketingDetail(cart.getMarketingId(), cart.getGoodsInfoId());
                            if (mark != null && null != mark.getPreDiscountMarketing()) {
                                para.put("marketingId", mark.getMarketingId());
                                para.put("goodsId", cart.getGoodsInfoId());
                                PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                                if (premark != null) {
                                    // 货品价格
                                    goodsprice = goodspriceflag.multiply(premark.getDiscountInfo());
                                    // 是否抹掉分角
                                    String discountFlag = premark.getDiscountFlag();
                                    // 抹掉分
                                    if ("1".equals(discountFlag)) {
                                        myformat = new DecimalFormat("0.0");
                                    } else if ("2".equals(discountFlag)) {
                                        myformat = new DecimalFormat("0");
                                    } else {
                                        myformat = new DecimalFormat("0.00");
                                    }
                                }
                            }

                        }

                        //不四舍五入
                        myformat.setRoundingMode(RoundingMode.FLOOR);
                        goodsprice = BigDecimal.valueOf(Double.valueOf(myformat.format(goodsprice)));
                        //单该货品同时参与了团购和折扣时,优先级团购优先
                        if (cart.getMarketing() != null && cart.getMarketing().getGroupon() != null) {
                            goodsprice = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(cart.getMarketing().getGroupon().getGrouponDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                        // 初始化订单商品信息
                        OrderGoods og = new OrderGoods();
                        // 货品价格
                        og.setGoodsInfoOldPrice(cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice());
                        cart.getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(goodsprice);

                        // 设置数量
                        og.setGoodsInfoNum(cartNum);
                        // 设置删除标记
                        og.setDelFlag("0");
                        // 设置购买时间
                        og.setBuyTime(new Date());
                        // 设置收货地区
                        og.setDistinctId(distinctId);
                        og.setEvaluateFlag("0");
                        // 设置商品原价
//                            og.setGoodsInfoOldPrice(cartlist.get(i).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice());
                        // 设置货品Id
                        og.setGoodsInfoId(cart.getGoodsDetailBean().getProductVo().getGoodsInfoId());
                        // 设置商品ID
                        og.setGoodsId(cart.getGoodsDetailBean().getProductVo().getGoodsId());
                        // 订单货品与参与的折扣绑定关系
                        og.setGoodsMarketingId(cart.getMarketingId());
                        // 订单货品参与的其他促销绑定关系
                        og.setGoodsActiveMarketingId(cart.getMarketingActivityId());
                        //货品参与团购促销绑定关系
                        og.setGoodsGroupMarketingId(cart.getGoodsGroupId());

                        og.setGoodsInfoPrice(goodsprice);

                        // 设置货品总价格（数量*价格）
                        og.setGoodsInfoSumPrice(og.getGoodsInfoPrice().multiply(BigDecimal.valueOf(cart.getGoodsNum())));
                        cart.setMarketing(marketService.marketingDetailNew(cart.getMarketingActivityId(), cart.getGoodsInfoId(), cart.getGoodsNum(), og.getGoodsInfoPrice()));
                        // 获取促销信息
                        Marketing market = cart.getMarketing();
                        if (market != null) {
                            og.setMarketing(market);
                        }
                        CalcStockUtil calcStockUtil = new CalcStockUtil();
                        calcStockUtil.setIsThird(cart.getGoodsDetailBean().getProductVo().getIsThird());
                        calcStockUtil.setDistinctId(distinctId);
                        // 商品id
                        calcStockUtil.setProductId(cart.getGoodsDetailBean().getProductVo().getGoodsInfoId());
                        // 减去库存
                        calcStockUtil.setStock(Integer.parseInt(og.getGoodsInfoNum().toString()));
                        calcStockUtils.add(calcStockUtil);

                        if (market != null) {
                            //当为抢购时
                            if (market.getRushs() != null && !market.getRushs().isEmpty()) {
                                og.setMarketing(market);
                            }
                        }
                        //设置品牌 为后面查询优惠券
                        og.setBrandId(cart.getGoodsDetailBean().getProductVo().getGoods().getBrandId());
                        //设置分类
                        og.setCatId(cart.getGoodsDetailBean().getProductVo().getGoods().getCatId());
                        oglist.add(og);

                    }
                }
            }

            Order order = new Order();

            // 封装物流信息
            OrderExpress oe = new OrderExpress();
            // 当为在线支付且是上门自提时运费是没有的
            boolean typeIdflag;
            if (typeId != null && typeId == 1 && "0".equals(thirdId.toString()) && chPay != null && chPay == 1) {
                typeIdflag = false;
                // 上门自提
                oe.setExpressTypeId(1L);
                // 上门自提地址
                DeliveryPoint point = deliveryPointService.getDeliveryPoint(deliveryPointId);
                order.setShoppingAddrId(deliveryPointId);
                order.setShippingProvince(point.getTemp1());
                order.setShippingCity(point.getTemp2());
                order.setShippingCounty(point.getTemp3());
                order.setShippingAddress(point.getAddress());
                order.setShippingPerson(point.getName());
                // 设置配送方式
                order.setOrderExpressType("1");

                // 查询物流模板信息 根据thirdId 查询默认的模板
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("freightIsDefault", "1");
                paramMap.put("freightThirdId", thirdId);
                // 获取默认模板
                FreightTemplate ft = freightTemplateMapper.selectFreightTemplateSubOrder(paramMap);
                if (ft == null) {
                    return Collections.emptyList();
                }
                // 配送方式名称
                oe.setExpressTypeName(ft.getFreightTemplateName());

                // 获取物流信息
                FreightExpress fe = freightExpressMapper.selectTemplateExpress(ft.getFreightTemplateId()).get(0);
                if (fe != null) {
                    Long comId;
                    String comName;
                    if (thirdId.equals(0L)) {
                        fe.setSysLogisticsCompany(sysLogisticsCompanyMapper.selectCompanyById(fe.getLogComId()));
                        comId = fe.getSysLogisticsCompany().getLogComId();
                        comName = fe.getSysLogisticsCompany().getName();
                    } else {
                        comId = expressInfoMapperThird.selectByshoreExpId(fe.getLogComId()).getShoreExpId();
                        comName = expressInfoMapperThird.selectByshoreExpId(fe.getLogComId()).getExpName();
                    }
                    oe.setExpressId(comId);
                    oe.setExpressName(comName);
                }

            } else {

                typeIdflag = true;
                // 设置配送方式
                order.setOrderExpressType("0");
                if (orderAddress.getAddressName() == null || orderAddress.getAddressPhone() == null || orderAddress.getAddressDetail() == null
                        || orderAddress.getAddressDetailInfo() == null) {

                    order.setShoppingAddrId(custAddress);
                    order.setShippingProvince(ca.getProvince().getProvinceName());
                    order.setShippingCity(ca.getCity().getCityName());
                    order.setShippingCounty(ca.getDistrict().getDistrictName());
                    order.setShippingAddress(ca.getAddressDetail());
                    order.setShippingPerson(ca.getAddressName());
                    order.setShippingPhone(ca.getAddressPhone());
                    order.setShippingMobile(ca.getAddressMoblie());

                    order.setShippingPostcode(ca.getAddressZip());
                } else {
                    // 微信收货地址
                    orderAddress.setProviceFirstStageName(new String(orderAddress.getProviceFirstStageName().getBytes(ISO_8859_1), ConstantUtil.UTF));
                    orderAddress.setAddressCitySecondStageName(new String(orderAddress.getAddressCitySecondStageName().getBytes(ISO_8859_1), ConstantUtil.UTF));
                    order.setShippingProvince(orderAddress.getProviceFirstStageName().substring(0, orderAddress.getProviceFirstStageName().length() - 1));
                    order.setShippingCity(orderAddress.getAddressCitySecondStageName().substring(0, orderAddress.getAddressCitySecondStageName().length() - 1));
                    order.setShippingCounty(new String(orderAddress.getAddressCountiesThirdStageName().getBytes(ISO_8859_1), ConstantUtil.UTF));
                    order.setShippingAddress(new String(orderAddress.getAddressDetailInfo().getBytes(ISO_8859_1), ConstantUtil.UTF));
                    order.setShippingMobile(new String(orderAddress.getAddressPhone().getBytes(ISO_8859_1), ConstantUtil.UTF));
                    order.setShippingPerson(new String(orderAddress.getAddressName().getBytes(ISO_8859_1), ConstantUtil.UTF));
                }

                // 查询物流模板信息 根据thirdId 查询默认的模板
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("freightIsDefault", "1");
                paramMap.put("freightThirdId", thirdId);
                // 获取默认模板
                FreightTemplate ft = freightTemplateMapper.selectFreightTemplateSubOrder(paramMap);
                if (ft == null) {
                    return Collections.emptyList();
                }
                // 配送方式名称
                oe.setExpressTypeName(ft.getFreightTemplateName());

                // 获取物流信息
                FreightExpress fe = freightExpressMapper.selectTemplateExpress(ft.getFreightTemplateId()).get(0);
                if (fe != null) {
                    Long comId;
                    String comName;
                    if (thirdId.equals(0L)) {
                        fe.setSysLogisticsCompany(sysLogisticsCompanyMapper.selectCompanyById(fe.getLogComId()));
                        comId = fe.getSysLogisticsCompany().getLogComId();
                        comName = fe.getSysLogisticsCompany().getName();
                    } else {
                        comId = expressInfoMapperThird.selectByshoreExpId(fe.getLogComId()).getShoreExpId();
                        comName = expressInfoMapperThird.selectByshoreExpId(fe.getLogComId()).getExpName();
                    }
                    oe.setExpressId(comId);
                    oe.setExpressName(comName);
                }
                oe.setDelFlag("0");
                // 配送方式 0 快递配送 1 上门自提
                oe.setExpressTypeId(0L);

            }

            // 随机数
            int randomNum = (int) (Math.random() * 100);
            // 子订单号
            String orderCode = UtilDate.mathString(new Date()) + randomNum;
            order.setOrderCode(orderCode);
            order.setOrderOldCode(orderOldCode);
            if (thirdId == 0) {
                // 赠品处理
                this.presentOrderGood(presentScopeId, distinctId, oglist, calcStockUtils);
            }


            // 插入订单商品
            order.setOrderGoodsList(oglist);

            // 发票信息String invoiceType,String invoiceTitle,
            if (!"0".equals(invoiceType)) {
                // 普通发票
                order.setInvoiceType("1");
                // 发票内容
                order.setInvoiceContent(invoiceType);
                if (invoiceTitle != null) {
                    // 发票抬头
                    order.setInvoiceTitle(invoiceTitle);
                }
            } else {
                order.setInvoiceType("0");
            }

            // order.setInvoiceContent(invoiceContent);
            order.setDelFlag("0");

            // 设置为手机订单 ，如果是微信，则设置为微信订单
            order.setOrderMType("2");

            // 订单状态
            order.setOrderStatus("0");

            Order orderpirce = getThirdOrderPrice(thirdId, codeNo, duiHuanJiFen, customerId, orderCode, ca.getCity().getCityId(), ca.getDistrict().getDistrictId(), cartlist,
                    typeIdflag);
            if (orderpirce == null) {
                return Collections.emptyList();
            }
            // 设置优惠卷金额
            order.setCouponPrice(orderpirce.getCouponPrice());
            // 设置会员折扣金额
            order.setDiscountPrice(orderpirce.getDiscountPrice());
            // 设置促销金额
            order.setPromotionsPrice(orderpirce.getPromotionsPrice());
            //优惠券
            order.setCouponNo(codeNo);
            // 订单兑换积分
            order.setOrderIntegral(duiHuanJiFen);
            order.setJfPrice(orderpirce.getJfPrice());
            // 总金额
            order.setOrderPrice(orderpirce.getOrderPrice());
            // 原始总额
            order.setOrderOldPrice(orderpirce.getOrderOldPrice());
            // 总优惠金额
            order.setOrderPrePrice(orderpirce.getOrderPrePrice());
            // 运费
            order.setExpressPrice(orderpirce.getExpressPrice());
            order.setBusinessId(Long.valueOf(thirdId.toString()));
            if (thirdId == 0) {
                //查询直营店开启状态
                String status = basicService.getStoreStatus();
                //判断是否开启
                if (status.equals("0")) {
                    List<DirectShop> directShops = null;
                    if (typeId != null && typeId == 1 && "0".equals(thirdId.toString()) && chPay != null && chPay.equals(1L)) {// 上门自提地址
                        DeliveryPoint point = deliveryPointService.getDeliveryPoint(deliveryPointId);
                        directShops = directShopService.queryDirectShopList(point.getDistrictId());
                    } else {
                        //快递配送查询
                        directShops = directShopService.queryDirectShopList(ca.getDistrict().getDistrictId());

                    }

                    //判断是否为空
                    if (directShops != null && directShops.size() > 0) {
                        Random random = new Random();
                        //获取随机数
                        int rNum = random.nextInt(directShops.size());
                        //获取直营店订单id
                        //重新设置订单商家id
                        order.setBusinessId(directShops.get(rNum).getDirectShopId());
                        //设置为直营店订单
                        order.setDirectType("1");
                    }
                }

            }
            // 如果为2，表示货到付款
            if (thirdId == 0) {
                if (chPay == 2) {
                    // 货到付款
                    order.setOrderLinePay("0");
                } else {

                    // 在线支付
                    order.setOrderLinePay("1");
                }
                // 支付方式
                order.setPayId(chPay);
            } else {
                // 第三方支付方式
                order.setPayId(chPaythird);
            }
            order.setCustomerId(customerId);
            order.setCreateTime(new Date());

            // 插入订单主表
            int f = orderService.insertOrder(order);
            // 插叙返回的ID
            if (f == 1) {

                Long orderId = orderService.selectLastId();

                // 修改优惠券已经使用
                couponNoService.updateCodeIsUse(codeNo, orderCode);
                order.setOrderId(orderId);
                maps.add(order);
                // 插入物流信息
                oe.setOrderId(orderId);
                orderExpressMapper.insertOrderExpress(oe);
                // 循环设置货品级联ID信息
                if (oglist != null && !oglist.isEmpty()) {

                    //满足条件的促销集合 促销id,对应促销的商品总价
                    Map<Long, BigDecimal> fullplist = new HashMap<>();
                    //满足条件的满减 误差集合 有用于设置误差值  促销id,对应促销的包含误差的优惠价格
                    Map<Long, BigDecimal> fullerrlist = new HashMap<>();
                    //满足条件的满减 正确的优惠价格
                    Map<Long, BigDecimal> fullcorlist = new HashMap<>();
                    //查询满足条件的满件满折
                    setFullBuy(oglist, fullplist);

                    BigDecimal modifyPrice = BigDecimal.ZERO;
                    if (order.getModifyPrice() != null) {
                        modifyPrice = order.getModifyPrice();
                    }
                    BigDecimal rate = order.getOrderPrice().divide(order.getOrderPrice().add(modifyPrice), 2);
                    for (OrderGoods og : oglist) {
                        og.setOrderId(orderId);
                        //存在抢购促销时
                        if (og.getMarketing() != null && og.getMarketing().getRushs() != null && !og.getMarketing().getRushs().isEmpty()) {
                            RushCustomer rushCustomer = new RushCustomer();
                            rushCustomer.setOrderId(orderId);
                            rushCustomer.setRushId(og.getMarketing().getRushs().get(0).getRushId());
                            rushCustomer.setGoodsInfoId(og.getGoodsInfoId());
                            rushCustomer.setCustomerId(customerId);
                            rushCustomer.setGoodsNum(Integer.valueOf(og.getGoodsInfoNum().toString()));
                            rushCustomerMapper.insertCustomerRush(rushCustomer);
                            og.setGoodsBackPrice(og.getGoodsInfoPrice().multiply(og.getMarketing().getRushs().get(0).getRushDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(rate));
                            og.setGoodsCouponPrice(og.getGoodsInfoPrice().multiply(og.getMarketing().getRushs().get(0).getRushDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        } else {
                            if (og.getMarketing() != null && og.getMarketing().getProductReduceMoney() != null) {
                                //如果是满足条件的满减促销
                                if ("5".equals(og.getMarketing().getCodexType()) && null != og.getMarketing().getFullbuyReduceMarketing()) {
                                    for (Long marketingId : fullplist.keySet()) {
                                        if (marketingId.equals(og.getMarketing().getMarketingId())) {
                                            BigDecimal reduprice = og.getMarketing().getFullbuyReduceMarketing().getReducePrice();
                                            BigDecimal erroprice = reduprice.multiply(og.getGoodsInfoSumPrice().divide(fullplist.get(marketingId), 4, BigDecimal.ROUND_HALF_UP));
                                            //设置优惠价格
                                            og.setGoodsCouponPrice(erroprice);

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
                                    if (og.getGoodsCouponPrice() == null) {
                                        og.setGoodsCouponPrice(BigDecimal.ZERO);
                                    }
                                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()).multiply(rate));
                                    //如果是满足条件的满折促销
                                } else if ("8".equals(og.getMarketing().getCodexType()) && null != og.getMarketing().getFullbuyDiscountMarketing()) {
                                    for (Object o : fullplist.keySet()) {
                                        if (o.equals(og.getMarketing().getMarketingId())) {
                                            og.setGoodsCouponPrice(og.getGoodsInfoSumPrice().multiply(BigDecimal.ONE.subtract(og.getMarketing().getFullbuyDiscountMarketing().getFullbuyDiscount())).setScale(2, BigDecimal.ROUND_HALF_UP));
                                        }
                                    }
                                    if (og.getGoodsCouponPrice() == null) {
                                        og.setGoodsCouponPrice(BigDecimal.ZERO);
                                    }
                                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()).multiply(rate));
                                    //满减满折 没有满足条件
                                } else if ("8".equals(og.getMarketing().getCodexType()) || "5".equals(og.getMarketing().getCodexType())) {
                                    if (og.getProductGroupPrice() != null) {
                                        og.setGoodsCouponPrice(og.getProductGroupPrice());
                                    } else {
                                        og.setGoodsCouponPrice(BigDecimal.ZERO);
                                    }
                                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));
                                } else {
                                    og.setGoodsCouponPrice(og.getMarketing().getProductReduceMoney());
                                    if (og.getGoodsCouponPrice() == null) {
                                        if (og.getProductGroupPrice() != null) {
                                            og.setGoodsCouponPrice(og.getProductGroupPrice());
                                        } else {
                                            og.setGoodsCouponPrice(BigDecimal.ZERO);
                                        }
                                    }
                                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()).multiply(rate));
                                }
                            } else {
                                if (og.getProductGroupPrice() != null) {
                                    og.setGoodsCouponPrice(og.getProductGroupPrice());
                                } else {
                                    og.setGoodsCouponPrice(BigDecimal.ZERO);
                                }
                                og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));
                            }
                        }
                    }

                    setErrorPrice(oglist, BigDecimal.ZERO, BigDecimal.ZERO, "1", fullcorlist, fullerrlist, null);
                    //设置 促销后的backprice  会员折扣-优惠券-积分
                    setBackPrice(oglist, order);
//                            BigDecimal allbackPrice = BigDecimal.ZERO;
//                            BigDecimal orderPrice = order.getOrderPrice().subtract(order.getExpressPrice());
//                            for(OrderGoods og:oglist) {
//                                //非抢购
//                                if (!(null != og.getMarketing() && og.getMarketing().getCodexType().equals("11"))) {
//                                    allbackPrice = allbackPrice.plus(og.getGoodsBackPrice());
//                                }
//                            }
//                            //最后计算误差
//                            setErrorPrice(oglist,allbackPrice,orderPrice,"5",null,null,null);

                    for (OrderGoods og : oglist) {
                        orderGoodsMapper.insertOrderGoodsInfo(og);
                        // 获取货品级联ID
                        Long orderGoodsId = orderGoodsMapper.selectLastId();
                        if ("1".equals(og.getHaveCouponStatus())) {
                            List<OrderGoodsInfoCoupon> clist = og.getOrderGoodsInfoCouponList();
                                for (OrderGoodsInfoCoupon aClist : clist) {
                                    aClist.setOrderGoodsId(orderGoodsId);
                                }
                                // 批量插入订单商品的赠送优惠券信息
                                orderGoodsInfoCouponMapper.insertOrderInfoCoupon(clist);
                        }

                        if ("1".equals(og.getHaveGiftStatus())) {
                            List<OrderGoodsInfoGift> glist = og.getOrderGoodsInfoGiftList();
                            for (OrderGoodsInfoGift aGlist : glist) {
                                aGlist.setOrderGoodsId(orderGoodsId);
                            }
                                // 批量插入订单商品的赠送赠品信息
                                orderGoodsInfoGiftMapper.insertOrderInfoGift(glist);
                        }
                    }

                }
            }

        }

        // 修改所有购买商品为已经删除
        shoppingCartService.deleteShoppingCartByIds(request, shoppingCartId);
        goodsProductService.minStock(calcStockUtils);
        return maps;

    }


    /**
     * @param customerId     会员ID
     * @param orderCode      订单单号
     * @param duiHuanJiFen   兑换的积分
     * @param disparityPrice 兑换的几个
     * @param consumption    积分兑换规则
     * @return
     */
    public int insertExchangeCusmomer(Long customerId, String orderCode, Long duiHuanJiFen, BigDecimal disparityPrice, BigDecimal consumption) {
        int result = 0; // 保存执行的结果
        // 新增积分兑换信息
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(CUSTOMERID, customerId);
            map.put("exchangePoint", duiHuanJiFen);
            map.put("exchangeTime", new Date());
            map.put("orderCode", orderCode);
            map.put("disparityPrice", disparityPrice);
            map.put("consumption", consumption);
            result = siteGoodsProductService.insertExchangeCusmomer(map);
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return result;
    }

    /***
     * 修改会员总积分
     *
     * @param customerId
     * @param point
     * @return
     */
    public int updatePoint(Long customerId, Long point) {
        int result = 0; // 保存执行的结果
        // 根据会员ID获取积分对象
        CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId(customerId);
        if (null != customerPoint) {
            // 更新当前登录会员的总积分
            customerPoint.setPointSum(customerPoint.getPointSum() - point);
            // 更新积分对象
            result = couponService.updateCustomerPoint(customerPoint);
            //添加积分使用记录
            couponService.addIntegralUseRecord(customerId, point.intValue(), "订单使用积分");
        }
        return result;
    }

    /**
     * 确认支付
     *
     * @param orderId
     * @return orderId
     */
    @Override
    public int payOrder(Long orderId) {
        return orderService.payOrder(orderId);
    }

    /**
     * 查询订单包裹表
     *
     * @param orderId 订单id
     * @return 订单所属包裹的运单号
     */
    public List<OrderContainerUtil> getExpressNo(Long orderId) {
        List<OrderContainerUtil> containerUtils = new ArrayList<OrderContainerUtil>();
        // 返回运送方式
        List<OrderContainerRelation> expressList = relationMapper.getExpressNo(orderId);
        // 订单所属的配送方式
        OrderExpress express = orderExpressMapper.selectOrderExpress(orderId);
        String kuaiDiName = iExpressConfBiz.queryKuaidi100CodeByExpressId(express.getExpressId());
        // 订单包裹和物流信息
        List<OrderContainerRelation> containerRelations = orderService.queryContainerRalation(orderId);
        OrderExpress orderExpress = orderExpressMapper.selectOrderExpress(orderId);
        // 订单所属的配送信息
        for (int i = 0; i < expressList.size(); i++) {
            String kuaidi = KuaiDiUtil.execLookKuaiDi(kuaiDiName, expressList.get(i).getExpressNo());
            // 订单单个包裹
            OrderContainerUtil containerUtil = new OrderContainerUtil();
            containerUtil.setContainerRelations(containerRelations.get(i));
            containerUtil.setExpressName(orderExpress.getExpressName());
            containerUtil.setExpress(kuaidi);
            containerUtils.add(containerUtil);
        }

        return containerUtils;

    }

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return Order
     */
    @Override
    public Order getPayOrder(Long orderId) {
        return orderService.getPayOrder(orderId);
    }

    /**
     * 查询订单根据COde
     *
     * @param orderCode
     * @return Order
     */
    @Override
    public Order getPayOrderByCode(String orderCode) {
        return orderService.getPayOrderByCode(orderCode);
    }

    /**
     * 微信支付工具类
     *
     * @param request
     * @param response
     * @param order
     * @param pay
     * @param goodsName
     * @return
     */
    @Override
    public Map<String, Object> getWXUrl(HttpServletRequest request, HttpServletResponse response, Order order, Pay pay, String goodsName) {
        // 网页授权后获取传递的参数
        String userId = request.getParameter("userId");
        // 金额转化为分为单位
        String finalmoney = String.format("%.2f", order.getOrderPrice());
        finalmoney = finalmoney.replace(".", "");

        // 商户相关资料
        String appid = pay.getApiKey();
        String appsecret = pay.getSecretKey();
        String partner = pay.getPartner();
        String partnerkey = pay.getPartnerKey();
        String openId = "";
        openId = request.getSession().getAttribute("openid").toString();

        // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;

        // 商户号
        String mchId = partner;
        // 子商户号 非必输
        // String sub_mch_id="";
        // 随机数
        String nonceStr = strReq;
        // 商品描述
        // String body = describe;

        // 商品描述根据情况修改
        String body = goodsName;
        // 附加数据
        String attach = userId;
        // 商户订单号
        String outTradeNo = order.getOrderCode();
        int intMoney = Integer.parseInt(finalmoney);

        // 总金额以分为单位，不带小数点
        int totalFee = intMoney;
        // 订单生成的机器 IP
        String spbillCreateIp = request.getRemoteAddr();
        // 订 单 生 成 时 间 非必输
        // 订单失效时间 非必输
        // 商品标记 非必输
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notifyUrl = pay.getBackUrl();

        String tradeType = "JSAPI";
        String openid = openId;
        // 非必输

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mchId);
        // packageParams.put("device_info", device_info);    // no-required
        packageParams.put("nonce_str", nonceStr);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", outTradeNo);

        packageParams.put("attach", attach);


        // 这里写的金额为1 分到时修改
        packageParams.put("total_fee", String.valueOf(totalFee));
        packageParams.put("spbill_create_ip", spbillCreateIp);
        packageParams.put("notify_url", notifyUrl);

        packageParams.put("trade_type", tradeType);
        packageParams.put("openid", openid);

        RequestHandlerUtil reqHandler = new RequestHandlerUtil(request, response);
        reqHandler.init(appid, appsecret, partnerkey);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mchId + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                +
                // 金额，这里写的1 分到时修改
                "<total_fee>" + totalFee + "</total_fee>"
                +
                // "<total_fee>"+finalmoney+"</total_fee>"+
                "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>" + "<notify_url>" + notifyUrl + "</notify_url>" + "<trade_type>" + tradeType + "</trade_type>"
                + "<openid>" + openid + "</openid>" + "</xml>";
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String prepayId = "";
        try {
            prepayId = GetWxOrderno.getPayNo(createOrderURL, xml);
            if ("".equals(prepayId)) {
                request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
                response.sendRedirect("error_page.jsp");
            }
        } catch (Exception e1) {
            LOGGER.info(e1);
            prepayId = null;
        }
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String appid2 = appid;
        String timestamp = Sha1Util.getTimeStamp();
        String nonceStr2 = nonceStr;
        String prepayId2 = "prepay_id=" + prepayId;
        String packages = prepayId2;
        finalpackage.put("appId", appid2);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonceStr2);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appId", appid2);
        map.put("timeStamp", timestamp);
        map.put("nonceStr", nonceStr2);
        map.put("package", packages);
        map.put("sign", finalsign);
        return map;
    }

    /**
     * 查询主订单下的所有子订单
     *
     * @param orderCode 主订单编号
     * @return List<Order>
     */
    @Override
    public List<Order> queryOrderByOrderOldCode(String orderCode) {
        return orderService.getPayOrderByOldCode(orderCode);
    }

    /**
     * 直营店订单支付成功短信通知
     *
     * @param order
     * @return
     */
    @Override
    public boolean paySuccessSendSms(Order order) {
        String status = basicService.getStoreStatus();
        // 判断是否开启
        if (("0").equals(status)) {
            //判断是否是直营店订单
            if (order.getDirectType() != null && order.getDirectType().equals("1")) {
                DirectShop directShop = directShopService.selectInfoById(order.getBusinessId());
                //发送短信
                Sms sms = mapper.selectSms();
                //手机号
                sms.setSendSim(directShop.getDirectShopTel());
                //短信内容
                sms.setMsgContext("您有一笔新订单，单号为：" + order.getOrderCode());
                try {
                    // 短信发送
                    SmsPost.sendPostOrder(sms);
                } catch (IOException e) {
                    LOGGER.error("", e);
                }
            }
        }
        return true;
    }

    /**
     * 赠品订单商品处理
     *
     * @param presentScopeId 赠品范围id
     * @param distinctId     地区id
     * @param orderGoodses   订单商品集合
     * @param stockUtils     库存管理集合
     */
    private void presentOrderGood(String[] presentScopeId, Long distinctId, List<OrderGoods> orderGoodses, List<CalcStockUtil> stockUtils) {
        //参数校验
        if (ArrayUtils.isNotEmpty(presentScopeId) && distinctId != null && CollectionUtils.isNotEmpty(orderGoodses) && CollectionUtils.isNotEmpty(stockUtils)) {
            //根据赠品范围ids查询赠品范围list
            List<FullbuyPresentScope> fullbuyPresentScopes = fullbuyPresentScopeMapper.queryByScopeIds(Arrays.asList(presentScopeId));
            for (int k = 0; k < fullbuyPresentScopes.size(); k++) {
                FullbuyPresentScope presentScope = fullbuyPresentScopes.get(k);
                // 初始化订单商品信息
                OrderGoods og = new OrderGoods();
                // 查询库存
                ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(presentScope.getScopeId(), distinctId);
                if (productWare != null) {
                    //赠完为止
                    if (productWare.getWareStock() - presentScope.getScopeNum() < 0) {
                        // 设置库存数量小于赠送数量,则实际赠送数量未剩余库存数
                        og.setGoodsInfoNum(productWare.getWareStock());
                    } else {
                        og.setGoodsInfoNum(fullbuyPresentScopes.get(k).getScopeNum());
                    }

                }


                //根据赠品id查询赠品信息(货品)
                GoodsProduct goodsProduct = goodsProductMapper.queryByGoodsInfoDetail(fullbuyPresentScopes.get(k).getScopeId());

                // 设置删除标记
                og.setDelFlag("0");
                // 设置购买时间
                og.setBuyTime(new Date());
                // 设置收货地区
                og.setDistinctId(distinctId);
                og.setEvaluateFlag("0");
                // 设置商品原价
                og.setGoodsInfoOldPrice(new BigDecimal(0.00));
                // 设置货品Id
                og.setGoodsInfoId(fullbuyPresentScopes.get(k).getScopeId());
                // 设置商品ID
                og.setGoodsId(goodsProduct.getGoodsId());
                // 货品价格
                og.setGoodsInfoPrice(new BigDecimal(0.00));

                // 设置货品总价格（数量*价格）
                og.setGoodsInfoSumPrice(new BigDecimal(0.00));
                //赠品商品
                og.setIsPresent("1");
                orderGoodses.add(og);
                CalcStockUtil calcStockUtil = new CalcStockUtil();
                calcStockUtil.setIsThird("0");
                calcStockUtil.setDistinctId(distinctId);
                // 商品id
                calcStockUtil.setProductId(fullbuyPresentScopes.get(k).getScopeId());
                // 减去库存
                calcStockUtil.setStock(Integer.parseInt(og.getGoodsInfoNum().toString()));
                stockUtils.add(calcStockUtil);

            }
        }
    }

    /**
     * 计算促销后的退货价格  折扣-优惠券-积分
     *
     * @param oglist
     * @param order
     */
    private void setBackPrice(List<OrderGoods> oglist, Order order) {

        //此处是 去除促销后的 实际花费总价
        BigDecimal totalPrice = BigDecimal.ZERO;

        //计算当前总价
        for (OrderGoods og : oglist) {
            //非抢购
            if (noRush(og) ) {
                BigDecimal backPrice = og.getGoodsBackPrice();
                totalPrice = totalPrice.add(backPrice);
            }
        }

        //计算会员折扣后的优惠价格
        if (null != order.getDiscountPrice() && order.getDiscountPrice().compareTo(BigDecimal.ZERO) == 1 && order.getBusinessId().intValue() == 0) {
            //订单使用的会员折扣价格
            BigDecimal discountprice = order.getDiscountPrice();
            //累加按照比例算出的会员折扣优惠价  防止四舍五入 导致的误差较大
            BigDecimal discountbackprice = BigDecimal.ZERO;
            for (OrderGoods og : oglist) {
                //非抢购
                if (noRush(og)) {
                    BigDecimal couponPrice = og.getGoodsCouponPrice();
                    BigDecimal backPrice = og.getGoodsBackPrice();
                    BigDecimal goodsInfoSumPrice = og.getGoodsInfoSumPrice();

                    BigDecimal rate = backPrice.divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP);
                    /**
                     * orderGoods 分摊到的会员折扣的金额
                     */
                    BigDecimal goodsDiscountPrice = discountprice.multiply(rate);

                    BigDecimal goodsCouponPrice = couponPrice.add(goodsDiscountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                    discountbackprice = discountbackprice.add(goodsDiscountPrice);

                    og.setGoodsCouponPrice(goodsCouponPrice);
                    og.setGoodsBackPrice(goodsInfoSumPrice.subtract(goodsCouponPrice));
                }
            }
            //计算误差
            setErrorPrice(oglist, discountbackprice, discountprice, "2", null, null, null);
        }


        //计算优惠券后的优惠价格
        //查询优惠券信息
        if (null != order.getCouponNo() && null != order.getCouponPrice() && order.getCouponPrice().compareTo(BigDecimal.ZERO) == 1) {
            Coupon coupon = couponService.selectCouponByCodeNo(order.getCouponNo());
            //使用优惠券的商品总价
            BigDecimal allcoupon = BigDecimal.ZERO;
            //参与优惠券的商品集合
            List<Long> goodsinfolist = new ArrayList<>();
            if (null != coupon && CollectionUtils.isNotEmpty(coupon.getCouponrangList())) {
                for (CouponRange cr : coupon.getCouponrangList()) {
                    for (OrderGoods og : oglist) {
                        //非抢购和组合商品
                        if (noRush(og) && null == og.getIsGroup()) {
                            //分类优惠券
                            if (null != og.getCatId() && "0".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getCatId())) {
                                allcoupon = allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                            //品牌优惠券
                            if (null != og.getBrandId() && "1".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getBrandId())) {
                                allcoupon = allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                            //sku优惠券
                            if (null != og.getGoodsInfoId() && "2".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getGoodsInfoId())) {
                                allcoupon = allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                        }
                    }

                }
            }
            BigDecimal errPirce = BigDecimal.ZERO;
            BigDecimal ordercouPrice = order.getCouponPrice();
            for (OrderGoods og : oglist) {
                //非抢购和组合商品
                if (noRush(og) && null == og.getIsGroup()) {
                    BigDecimal couponprice = og.getGoodsCouponPrice();
                    BigDecimal backprice = og.getGoodsBackPrice();
                    if (CollectionUtils.isNotEmpty(goodsinfolist)) {
                        if (goodsinfolist.contains(og.getGoodsInfoId())) {
                            og.setGoodsCouponPrice(couponprice.add(order.getCouponPrice().multiply(backprice).divide(allcoupon, 4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP)));
                            errPirce = errPirce.add(order.getCouponPrice().multiply(backprice).divide(allcoupon, 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));

                }
            }
            //计算误差
            setErrorPrice(oglist, errPirce, ordercouPrice, "3", null, null, goodsinfolist);
        }

        //此处 设置参加优惠券后的  优惠价格
        totalPrice = BigDecimal.ZERO;
        //计算当前总价
        for (OrderGoods og : oglist) {
            BigDecimal backprice = og.getGoodsBackPrice();
            //非抢购
            if (noRush(og)) {
                totalPrice = totalPrice.add(backprice);
            }
        }

        //计算积分的backprice
        if (null != order.getJfPrice() && order.getJfPrice().compareTo(BigDecimal.ZERO) == 1 && order.getBusinessId().intValue() == 0) {
            BigDecimal errPirce = BigDecimal.ZERO;
            if (null != order.getExpressPrice()) {
                totalPrice = totalPrice.add(order.getExpressPrice());
            }
            BigDecimal jifenPrice = order.getJfPrice();
            for (OrderGoods og : oglist) {
                //非抢购
                if (noRush(og)) {
                    BigDecimal backprice = og.getGoodsBackPrice();
                    if (totalPrice.compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal couponPrice = og.getGoodsCouponPrice().add(order.getJfPrice().multiply(backprice).divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP));


                        errPirce = errPirce.add(order.getJfPrice().multiply(backprice).divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP));
                        og.setGoodsCouponPrice(couponPrice);
                        og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(couponPrice));
                    }
                }
            }
            //errpirce 加上 运费所占积分优惠比
            errPirce = errPirce.add(order.getJfPrice().multiply(order.getExpressPrice()).divide(totalPrice, 4, BigDecimal.ROUND_HALF_UP));
            //计算误差
            setErrorPrice(oglist, errPirce, jifenPrice, "4", null, null, null);
        }

    }

    private boolean noRush(OrderGoods og) {
        return !(null != og.getMarketing() && og.getMarketing().getCodexType().equals("11"));
    }


    /**
     * 设置满足调价的满减促销和满折促销
     *
     * @param oglist
     */
    private void setFullBuy(List<OrderGoods> oglist, Map<Long, BigDecimal> fullplist) {
        //重新计算满减满折订单的退货价格 包含优惠券订单
        //1、计算促销后价格  2、计算会员折扣后价格  3、计算优惠券后价格   4、计算积分后价格
        //满减满折促销范围集合  促销id,参加促销的商品总价
        for (OrderGoods og : oglist) {
            if (null != og.getMarketing()) {
                //促销  如果是满减满折  把商品总价计算出来
                if ("5".equals(og.getMarketing().getCodexType()) || "8".equals(og.getMarketing().getCodexType())) {
                    Long marketingId = og.getMarketing().getMarketingId();
                    BigDecimal sumprice = og.getGoodsInfoSumPrice();
                    if (fullplist.containsKey(marketingId)) {
                        fullplist.put(marketingId, fullplist.get(marketingId).add(sumprice));
                    } else {
                        fullplist.put(marketingId, sumprice);
                    }
                }
            }
        }

        //此处判断 满减满折是否满足条件
        for (OrderGoods og : oglist) {
            if (null != og.getMarketing()) {
                //满减促销
                if ("5".equals(og.getMarketing().getCodexType()) && CollectionUtils.isNotEmpty(og.getMarketing().getFullbuyReduceMarketings())) {
                    List<FullbuyReduceMarketing> frlist = og.getMarketing().getFullbuyReduceMarketings();
                    List<FullbuyReduceMarketing> newlist = new ArrayList<FullbuyReduceMarketing>();
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
                    for (int i = 0; i < newlist.size(); i++) {
                        if (!(fullplist.get(og.getMarketing().getMarketingId()).compareTo(newlist.get(i).getFullPrice()) == -1)) {
                            og.getMarketing().setFullbuyReduceMarketing(newlist.get(i));
                            break;
                        }
                    }
                }
                //满折促销
                if ("8".equals(og.getMarketing().getCodexType()) && CollectionUtils.isNotEmpty(og.getMarketing().getFullbuyDiscountMarketings())) {
                    List<FullbuyDiscountMarketing> frlist = og.getMarketing().getFullbuyDiscountMarketings();
                    List<FullbuyDiscountMarketing> newlist = new ArrayList<FullbuyDiscountMarketing>();
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
                    for (int i = 0; i < newlist.size(); i++) {
                        if (!(fullplist.get(og.getMarketing().getMarketingId()).compareTo(newlist.get(i).getFullPrice()) == -1)) {
                            og.getMarketing().setFullbuyDiscountMarketing(newlist.get(i));
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 计算误差
     *
     * @param oglist        订单商品集合
     * @param errPrice      有误差的某一促销优惠总价
     * @param correctPrice  某一促销订单实际优惠总价
     * @param type          误差类型  1:满减   2：会员折扣  3：优惠券  4：积分  5：最终
     * @param fullcorplist  满减促销的map
     * @param fullerrplist  满减促销的map
     * @param goodsinfolist 优惠券商品集合
     */
    private void setErrorPrice(List<OrderGoods> oglist, BigDecimal errPrice, BigDecimal correctPrice, String type, Map<Long, BigDecimal> fullcorplist, Map<Long, BigDecimal> fullerrplist, List<Long> goodsinfolist) {

        for (OrderGoods og : oglist) {
            //非抢购
            if (noRush(og)) {
                Boolean isOK = false;
                if ("1".equals(type) && null == og.getIsGroup()) {
                    Iterator it = fullerrplist.keySet().iterator();
                    if (it.hasNext()) {
                        Long marketing = (Long) it.next();
                        if (og.getMarketing().getMarketingId().equals(marketing)) {
                            //误差值
                            BigDecimal errorprice = fullerrplist.get(marketing).subtract(fullcorplist.get(marketing));
                            //如果当前价格 比误差大
                            if ((og.getGoodsBackPrice().add(errorprice)).compareTo(BigDecimal.ZERO) == 1) {
                                // 把误差淹没在 此订单商品中
                                og.setGoodsCouponPrice(og.getGoodsCouponPrice().subtract(errorprice));
                                og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));
                                break;
                            }
                        }
                    }
                } else if ("3".equals(type) && null == og.getIsGroup()) {
                    if (CollectionUtils.isNotEmpty(goodsinfolist)) {
                        if (goodsinfolist.contains(og.getGoodsInfoId())) {
                            isOK = true;
                        }
                    }
                } else {
                    isOK = true;
                }
                if (isOK) {
                    //误差值
                    BigDecimal errorprice = errPrice.subtract(correctPrice);
                    //如果当前价格 比误差大
                    if ((og.getGoodsBackPrice().add(errorprice)).compareTo(BigDecimal.ZERO) == 1) {
                        // 把误差淹没在 此订单商品中
                        og.setGoodsCouponPrice(og.getGoodsCouponPrice().subtract(errorprice));
                        og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));
                        break;
                    }
                }
            }
        }
    }

    /**
     * 确认支付
     *
     * @param orderId 订单id
     * @param payId   支付id
     * @return orderId
     */
    @Override
    public int paySuccessUpdateOrder(Long orderId, String status, Long payId) {
        Map<String, Object> map =  new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("payId", payId);
        map.put("status", status);
        return orderService.updateOrderStatusAndPayId(map);
    }

    /**
     * 确认收货更新状态
     *
     * @param order 订单信息
     * @param customerId 会员id
     * @return
     */
    @Override
    @Transactional
    public int comfirmOrder(Order order, Long customerId) {
        order.setCustomerId(customerId);
        int status = orderMapper.comfirmOrder(order);
        LOGGER.info("OrderMService comfirmOrder status:"+status);
        if (status>0){
            //根据订单id赠送优惠劵信息/积分／消费信息
            orderCouponService.modifyCouponByOrderId(order.getOrderId(), customerId);
        }
        return status;
    }
}
