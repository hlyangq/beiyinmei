/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.site.order.service.impl;

import com.ningpai.common.bean.Sms;
import com.ningpai.common.dao.SmsMapper;
import com.ningpai.common.kuaidi.KuaiDiUtil;
import com.ningpai.common.util.SmsPost;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.bean.CouponRange;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.customer.bean.CustomerPoint;
import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerPointLevelMapper;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.customer.dao.IntegralSetMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.freighttemplate.bean.Express;
import com.ningpai.freighttemplate.bean.FreightExpress;
import com.ningpai.freighttemplate.bean.FreightTemplate;
import com.ningpai.freighttemplate.dao.ExpressInfoMapper;
import com.ningpai.freighttemplate.dao.FreightExpressMapper;
import com.ningpai.freighttemplate.dao.FreightTemplateMapper;
import com.ningpai.freighttemplate.dao.SysLogisticsCompanyMapper;
import com.ningpai.freighttemplate.service.FreightTemplateService;
import com.ningpai.gift.bean.Gift;
import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.bean.WareHouse;
import com.ningpai.goods.dao.ProductWareMapper;
import com.ningpai.goods.service.GoodsGroupService;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.goods.util.CalcStockUtil;
import com.ningpai.goods.vo.GoodsGroupReleProductVo;
import com.ningpai.goods.vo.GoodsGroupVo;
import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.*;
import com.ningpai.marketing.dao.FullbuyPresentScopeMapper;
import com.ningpai.marketing.dao.GrouponMapper;
import com.ningpai.marketing.dao.PreDiscountMarketingMapper;
import com.ningpai.marketing.dao.RushCustomerMapper;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.order.bean.*;
import com.ningpai.order.dao.*;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.other.bean.IntegralSet;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.util.OrderContainerUtil;
import com.ningpai.site.customer.vo.CustomerAllInfo;
import com.ningpai.site.directshop.bean.DirectShop;
import com.ningpai.site.directshop.service.DirectShopService;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.dao.GoodsProductMapper;
import com.ningpai.site.goods.service.GoodsProductService;
import com.ningpai.site.goods.vo.GoodsProductVo;
import com.ningpai.site.login.service.LoginService;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.site.shoppingcart.bean.ShoppingCart;
import com.ningpai.site.shoppingcart.dao.ShoppingCartMapper;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.system.address.bean.ShoppingCartWareUtil;
import com.ningpai.system.address.service.ShoppingCartAddressService;
import com.ningpai.system.bean.DeliveryPoint;
import com.ningpai.system.bean.PointSet;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.IExpressConfBiz;
import com.ningpai.system.service.PointSetService;
import com.ningpai.system.service.impl.ExpressConfBizImpl;
import com.ningpai.util.MyLogger;
import com.ningpai.util.UtilDate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ggn
 */
@Service("SiteOrderService")
public class SiteOrderServiceImpl extends BasicSqlSupport implements SiteOrderService {

    private static final String SUMOLDPRICE = "sumOldPrice";
    private static final String SUMPRICE = "sumPrice";
    private static final String STOCK = "stock";
    private static final String CUSTOMERID = "customerId";
    private static final String IS_TEMP_CUST = "is_temp_cust";
    private static final String PASSWORD = "000000";
    private static final String NOTRUSHSUMPRICE = "notRushSumPrice";
    //查看积分设置是否开启
    @Resource(name = "pointSetService")
    private PointSetService pointSetService;

    @Resource(name = "integralSetMapper")
    private IntegralSetMapper integralSetmapper;

    private ShoppingCartService shoppingCartService;

    private ProductWareMapper productWareMapper;

    private OrderContainerRelationMapper relationMapper;

    private IExpressConfBiz iExpressConfBiz;

    private GoodsProductService siteGoodsProductService;

    private ProductWareService productWareService;

    @Resource(name = "FreightTemplateMapper")
    private FreightTemplateMapper freightTemplateMapper;

    private com.ningpai.goods.service.GoodsProductService goodsProductService;

    @Resource(name = "FreightTemplateService")
    private FreightTemplateService freightTemplateService;

    @Resource(name = "SysLogisticsCompanyMapper")
    private SysLogisticsCompanyMapper sysLogisticsCompanyMapper;

    private CustomerPointMapper customerPointMapper;

    @Resource(name = "FreightExpressMapper")
    private FreightExpressMapper freightExpressMapper;

    @Resource(name = "ShoppingCartMapper")
    private ShoppingCartMapper shoppingCartMapper;


    @Resource(name = "GrouponMapper")
    private GrouponMapper grouponMapper;

    @Resource(name = "PreDiscountMarketingMapper")
    private PreDiscountMarketingMapper preDiscountMarketingMapper;

    @Resource(name = "expressInfoMapperThird")
    private ExpressInfoMapper expressInfoMapperThird;

    @Resource(name = "RushCustomerMapper")
    private RushCustomerMapper rushCustomerMapper;

    @Autowired
    private ShoppingCartAddressService shoppingCartAddressService;



    /**
     * 自提点
     */
    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;

    @Resource(name = "loginServiceSite")
    private LoginService loginService;

    private CouponService couponService;

    private CouponNoService couponNoService;

    private OrderGoodsInfoGiftMapper orderGoodsInfoGiftMapper;

    private OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper;

    private OrderGoodsMapper orderGoodsMapper;

    private OrderExpressMapper orderExpressMapper;

    private OrderService orderService;

    private CustomerServiceInterface customerServiceInterface;

    private ExpressConfBizImpl expressConfBizImpl;

    @Resource(name = "smsMapperSite")
    private SmsMapper mapper;

    @Resource(name = "SynOrderService")
    private SynOrderService synOrderService;

    @Resource(name = "MarketingService")
    private MarketingService marketService;

    @Resource(name = "customerPointLevelMapper")
    private CustomerPointLevelMapper customerPointLevelMapper;

    /**
     * 赠品接口
     */
    @Resource(name="fullbuyPresentScopeMapper")
    private FullbuyPresentScopeMapper fullbuyPresentScopeMapper;


    /**
     * 会员积分接口
     */
    @Resource(name="customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;

    @Resource(name = "GoodsGroupService")
    private GoodsGroupService goodsGroupService;
    @Resource(name = "HsiteGoodsProductMapper")
    private GoodsProductMapper goodsProductMapper;

    @Resource(name = "basicSetService")
    private BasicSetService basicService;

    @Resource(name = "SiteDirectShopService")
    private DirectShopService directShopService;

    public CustomerPointMapper getCustomerPointMapper() {
        return customerPointMapper;
    }

    @Resource(name = "customerPointMapper")
    public void setCustomerPointMapper(CustomerPointMapper customerPointMapper) {
        this.customerPointMapper = customerPointMapper;
    }
    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(SiteOrderServiceImpl.class);

    /**
     * 删除单个退单信息
     *
     * @param backOrderId
     *            退单Id
     * @param customerId
     *            当前会员ID
     * @return
     */
    @Override
    public int deleteBackOrderById(Long backOrderId, Long customerId) {
        return orderService.deleteBackOrderById(backOrderId, customerId);
    }

    /**
     * 得到总的优惠金额,以及总金额
     *
     * @param shopCartIds
     * @param distinctId
     * @return
     */
    @Override
    public Map<String, Object> getEveryparamMap(Long[] shopCartIds, Long distinctId) {

        List<ShoppingCart> shoplist = shoppingCartMapper.shopCartListByIds(Arrays.asList(shopCartIds));
        Map<String, Object> paramMap = new HashMap<>();
        for (ShoppingCart sp : shoplist) {
            if (sp.getFitId() != null) {
                // 如果商品是套装
                GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(sp.getFitId());
                sp.setThirdId(goodsGroupVo.getThirdId());
            }
        }

        // 交易总金额
        BigDecimal sumPrice = BigDecimal.valueOf(0);
        // 原始总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(0);
        // 中间变量
        BigDecimal flag = BigDecimal.ZERO;
        // 优惠金额
        BigDecimal prePrice = BigDecimal.valueOf(0);
        // boss总金额
        BigDecimal bossSumPrice = BigDecimal.ZERO;
        // 套装优惠金额
        BigDecimal taozhuan = BigDecimal.ZERO;
        //boss优惠金额-chenp-2015/12/12
        BigDecimal bossPrePrice = BigDecimal.ZERO;
        //抢购boss商品总价
        BigDecimal rushSumPrice = BigDecimal.ZERO;
        //抢购boss商品优惠价格
        BigDecimal rushPrePrice = BigDecimal.ZERO;
        // 第三方id的map
        Map<Long, Object> thirdIdMap = new HashMap<>();
        Map<String, Object> para = new HashMap<>();
        if (CollectionUtils.isNotEmpty(shoplist)) {
            for (int i = 0; i < shoplist.size(); i++) {
                if (shoplist.get(i).getFitId() == null) {

                    thirdIdMap.put(shoplist.get(i).getThirdId(), "");
                    // 查询商品详细
                    shoplist.get(i).setGoodsDetailBean(siteGoodsProductService.querySimpleDetailBeanWithWareByProductId(((ShoppingCart) shoplist.get(i)).getGoodsInfoId(),distinctId));
                    // 查询购物车里选择的促销信息
                    shoplist.get(i).setMarketing(marketService.marketingDetail(shoplist.get(i).getMarketingActivityId(), shoplist.get(i).getGoodsInfoId()));
                } else {
                    // 如果商品是套装
                    GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(((ShoppingCart) shoplist.get(i)).getFitId());
                    if (null != goodsGroupVo) {

                        shoplist.get(i).setGoodsGroupVo(goodsGroupVo);
                        // 该套装下所有的商品
                        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = ((ShoppingCart) shoplist.get(i)).getGoodsGroupVo().getProductList();
                        // 获取此套装下的所有货品
                        for (int j = 0; j < goodsGroupReleProductVos.size(); j++) {
                            GoodsDetailBean goodsDetailBean = siteGoodsProductService.queryDetailBeanByProductId(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoId(),
                                    distinctId);
                            goodsGroupReleProductVos.get(j).getProductDetail().setGoodsInfoPreferPrice(goodsDetailBean.getProductVo().getGoodsInfoPreferPrice());
                            // 原总金额加上套装优惠前费用
                            sumOldPrice = sumOldPrice.add(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoPreferPrice()
                                    .multiply(BigDecimal.valueOf(shoplist.get(i).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVos.get(j).getProductNum())));
                            flag = flag.add(goodsGroupReleProductVos.get(j).getProductDetail().getGoodsInfoPreferPrice()
                                    .multiply(BigDecimal.valueOf(shoplist.get(i).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVos.get(j).getProductNum())));
                        }

                        // 得到套装优惠费用
                        taozhuan = taozhuan.add(BigDecimal.valueOf(shoplist.get(i).getGoodsNum()).multiply(goodsGroupVo.getGroupPreferamount()));
                        bossSumPrice = sumOldPrice.subtract(taozhuan);
                    }
                }
            }
            Long goodssum = 0L;
            BigDecimal goodsprice = BigDecimal.ZERO;
            BigDecimal totalprice = BigDecimal.ZERO;
            String discountFlag = "";
            for (int v = 0; v < shoplist.size(); v++) {
                if (shoplist.get(v).getFitId() == null) {

                    // 货品价格
                    goodsprice = shoplist.get(v).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();

                    //价格中间变量
                    BigDecimal   goodsPriceflag= shoplist.get(v).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                    DecimalFormat myformat = null;
                    myformat = new DecimalFormat("0.00");
                    //单该货品同时参与了团购和折扣时,优先级团购优先
                    if(shoplist.get(v).getGoodsGroupId()!=null){
                        // 从购物车里得到促销id重新从数据库查询,防止当前(团购促销)已经过期;
                        Marketing mark = marketService.querySimpleMarketingById(shoplist.get(v).getGoodsGroupId());
                        if(mark!=null) {
                            Groupon groupon = grouponMapper.selectByMarketId(mark.getMarketingId());
                            if (groupon != null) {
                                goodsprice = goodsPriceflag.multiply(groupon.getGrouponDiscount());
                            }
                        }
                    }
                    // 折扣促销
                    if (shoplist.get(v).getGoodsGroupId()==null&&shoplist.get(v).getMarketingId() != null && 0 != shoplist.get(v).getMarketingId()) {

                        // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                        Marketing mark = marketService.querySimpleMarketingById(shoplist.get(v).getMarketingId());
                        if (mark != null) {
                            para.put("marketingId", mark.getMarketingId());
                            para.put("goodsId", shoplist.get(v).getGoodsInfoId());
                            PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                            if (premark != null) {
                                // 货品价格
                                goodsprice = goodsPriceflag.multiply(premark.getDiscountInfo());
                                discountFlag = premark.getDiscountFlag();
                            }

                        }
                        // 抹掉分
                        if ("1".equals(discountFlag)) {
                            myformat = new DecimalFormat("0.0");
                        } else if ("2".equals(discountFlag)) {
                            myformat = new DecimalFormat("0");
                        } else {
                            myformat = new DecimalFormat("0.00");
                        }

                    }
                    // 不四舍五入
                    myformat.setRoundingMode(RoundingMode.FLOOR);
                    goodsprice = BigDecimal.valueOf(Double.valueOf(myformat.format(goodsprice)));
                    shoplist.get(v).getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(goodsprice);
                    // 货品购买件数
                    goodssum = shoplist.get(v).getGoodsNum();
                    // 计算boss价格页面计算用
                    if (shoplist.get(v).getThirdId() == 0) {
                        bossSumPrice = bossSumPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                        if (shoplist.get(v).getMarketing() != null && shoplist.get(v).getMarketing().getRushs() != null &&! shoplist.get(v).getMarketing().getRushs().isEmpty()) {
                            rushSumPrice = rushSumPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                        }
                    }
                    // 计算原始总金额
                    sumOldPrice = sumOldPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    flag = flag.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                }

            }
            goodssum = 0L;
            goodsprice = BigDecimal.ZERO;
            totalprice = BigDecimal.ZERO;
            BigDecimal marketflag = BigDecimal.ZERO;
            List<ShoppingCart> cartList = null;
            for (Long thirdId : thirdIdMap.keySet()) {
                // 根据第三方id分组得到新的购物车集合
                cartList = new ArrayList<>();
                for (ShoppingCart sc : shoplist) {
                    if (sc.getFitId() == null && thirdId.equals(sc.getThirdId())) {
                        cartList.add(sc);
                    }
                }
                // 促销分组
                Map<Long, Object> markMap = new HashMap<>();
                for (ShoppingCart sc : cartList) {
                    if (sc.getThirdId().equals(thirdId) && sc.getMarketingActivityId() != null) {
                        markMap.put(sc.getMarketingActivityId(), sc.getMarketing());
                    }
                }
                // 各个同一商家的促销总价
                Map<Long, Object> priceMap = new HashMap<>();
                for (Long obd : markMap.keySet()) {
                    // 计算各个商家不同促销的总价格
                    for (ShoppingCart car : cartList) {
                        if (obd.equals(car.getMarketingActivityId()) && car.getThirdId().equals(thirdId)) {
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
                                    prePrice = marketflag;
                                    marketflag = BigDecimal.ZERO;
                                }
                                //计算BOSS商品满减和满折促销优惠金额
                                if(thirdId == 0){
                                    bossPrePrice = bossPrePrice.add(prePrice);
                                }
                                // 实际付款金额
                                flag = flag.subtract(prePrice);
                                prePrice = BigDecimal.ZERO;
                                // totalprice置为0
                                totalprice = BigDecimal.ZERO;
                            }
                        }
                    }
                }

                for (int k = 0; k < cartList.size(); k++) {
                    // 直降
                    if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getPriceOffMarketing() != null) {
                        // sumOldPrice =
                        // sumOldPrice.subtract(cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue
                        // ().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                        //查看优惠是不是出现负数
                        BigDecimal srcPrice = cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum()));
                        BigDecimal desPrice = cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum()));
                        if(desPrice.compareTo(srcPrice) > 0){
                            flag = flag.subtract(srcPrice.subtract(BigDecimal.valueOf(0.01)));
                        }else{
                            flag = flag.subtract(desPrice);
                        }
                        //flag = flag.subtract(cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                        //计算BOSS商品直降促销优惠金额
                        if (thirdId == 0) {
                            //折扣后钱出现负数的情况
                            if(desPrice.compareTo(srcPrice) > 0){
                                bossPrePrice = bossPrePrice.add(srcPrice.subtract(BigDecimal.valueOf(0.01)));
                            }else{
                                bossPrePrice = bossPrePrice.add(desPrice);
                            }
                            //bossPrePrice = bossPrePrice.add(cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                        }
                    }
                    //团购
//                    if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getGroupon() != null) {
//                        flag = flag.subtract((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getGroupon().getGrouponDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
//
//                    }
                    //抢购
                    if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getRushs() != null &&! cartList.get(k).getMarketing().getRushs().isEmpty()) {
                        flag = flag.subtract((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getRushs().get(0).getRushDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                        //计算BOSS商品抢购促销优惠金额
                        if(thirdId == 0){
                            bossPrePrice = bossPrePrice
                                    .add((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getRushs().get(0).getRushDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                            rushPrePrice = rushPrePrice
                                    .add((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getRushs().get(0).getRushDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                        }
                    }
                }

            }
        }
        sumPrice = flag.subtract(taozhuan);

        paramMap.put("shoplist", shoplist);
        paramMap.put(SUMOLDPRICE, sumOldPrice);
        paramMap.put("bossSumPrice", bossSumPrice);
        paramMap.put(SUMPRICE, sumPrice);
        paramMap.put("thirdIdMap", thirdIdMap);
        paramMap.put("bossPrePrice", bossPrePrice);
        //boss商品总计减去boss促销优惠价格
        paramMap.put("cusZhekouPrice", (bossSumPrice.subtract(rushSumPrice)).subtract(bossPrePrice.subtract(rushPrePrice)));
        return paramMap;
    }

    /**
     * 得到各个商家的金额
     *
     * @param businessId
     * @param shopdata
     * @param distinctId
     * @return
     */
    @Override
    public Map<String, Object> getEveryThirdPriceMap(Long businessId, List<ShoppingCart> shopdata, Long distinctId) {
        Map<String, Object> paramMap = new HashMap<>();
        // 1表示不同地区存在库存 0则表示库存不足直接跳到购物车
        paramMap.put(STOCK, "1");
        List<ShoppingCart> shoplist = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(shopdata)) {

            for (int i = 0; i < shopdata.size(); i++) {
                if (businessId.equals(shopdata.get(i).getThirdId())) {
                    shoplist.add(shopdata.get(i));
                }
            }
        }
        // 交易总金额
        BigDecimal sumPrice = BigDecimal.valueOf(0);
        // 原始总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(0);
        // 优惠金额
        BigDecimal prePrice = BigDecimal.valueOf(0);
        // 中间变量
        BigDecimal flag = BigDecimal.ZERO;
        // 抢购中间变量
        BigDecimal rushFlag = BigDecimal.ZERO;
        // boss总金额
        BigDecimal bossSumPrice = BigDecimal.ZERO;
        // 套装优惠金额
        BigDecimal taozhuan = BigDecimal.ZERO;
        // 不包含抢购商品boss总金额
        BigDecimal notRushSumPrice = BigDecimal.ZERO;
        ProductWare productWare;
        Map<String, Object> para = new HashMap<>();
        if (CollectionUtils.isNotEmpty(shoplist)) {
            Long goodssum = 0L;
            BigDecimal goodsprice = BigDecimal.ZERO;
            BigDecimal totalprice = BigDecimal.ZERO;

            for (int v = 0; v < shoplist.size(); v++) {

                if (shoplist.get(v).getFitId() != null) {
                    // 如果商品是套装
                    GoodsGroupVo goodsGroupVo = shoplist.get(v).getGoodsGroupVo();
                    if (null != goodsGroupVo) {

                        shoplist.get(v).setGoodsGroupVo(goodsGroupVo);
                        // 获取此套装下的所有货品
                        List<GoodsProductVo> goodsProducts = siteGoodsProductService.queryDetailByGroupId(shoplist.get(v).getFitId());
                        for (int j = 0; j < goodsProducts.size(); j++) {
                            if (goodsGroupVo.getThirdId() == 0 && null != distinctId && distinctId > 0) {
                                productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(goodsProducts.get(j).getGoodsInfoId(), distinctId);
                                // 不同地区的价格不同
                                if (null != productWare && productWare.getWareStock() < 0) {
                                    paramMap.put(STOCK, "0");
                                }
                            }
                            List<GoodsGroupReleProductVo> goodsGroupReleProductVos = shoplist.get(v).getGoodsGroupVo().getProductList();
                            for(GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos){
                                if(goodsProducts.get(j).getGoodsInfoId().intValue() == goodsGroupReleProductVo.getProductId()){
                                    if (goodsGroupVo.getThirdId() == 0 && null != distinctId && distinctId > 0) {
                                        productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(goodsProducts.get(j).getGoodsInfoId(), distinctId);
                                        // 不同地区的价格不同
                                        if (null != productWare) {
                                            // 原总金额加上套装优惠前费用
                                            sumOldPrice = sumOldPrice.add(productWare.getWarePrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum())));
                                            flag = flag.add(productWare.getWarePrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum())));
                                        }else{
                                            // 原总金额加上套装优惠前费用
                                            sumOldPrice = sumOldPrice.add(goodsProducts.get(j).getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum())));
                                            flag = flag.add(goodsProducts.get(j).getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum())));
                                        }
                                    }

                                }
                            }
                            // 原总金额加上套装优惠前费用
//                            sumOldPrice = sumOldPrice.add(goodsProducts.get(j).getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())));
//                            flag = flag.add(goodsProducts.get(j).getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())));
                            if (shoplist.get(v).getMarketing() != null && shoplist.get(v).getMarketing().getRushs() != null &&! shoplist.get(v).getMarketing().getRushs().isEmpty()) {
                                //抢购商品
                                rushFlag = rushFlag.add(goodsProducts.get(j).getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(shoplist.get(v).getGoodsNum())));
                            }
                        }

                        // 得到套装优惠费用
                        taozhuan = taozhuan.add(BigDecimal.valueOf(shoplist.get(v).getGoodsNum()).multiply(goodsGroupVo.getGroupPreferamount()));
                    }
                }
                if (shoplist.get(v).getFitId() == null) {

                    // 货品价格
                    goodsprice = shoplist.get(v).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                    // 货品购买件数
                    goodssum = shoplist.get(v).getGoodsNum();
                    // 计算boss价格页面计算用
                    if (shoplist.get(v).getThirdId() == 0) {
                        bossSumPrice = bossSumPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    }
                    // 计算原始总金额
                    sumOldPrice = sumOldPrice.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    flag = flag.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
                    if (shoplist.get(v).getMarketing() != null && shoplist.get(v).getMarketing().getRushs() != null &&! shoplist.get(v).getMarketing().getRushs().isEmpty()) {
                        //抢购商品
                        rushFlag = rushFlag.add(goodsprice.multiply(BigDecimal.valueOf(goodssum)));
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
                if (sc.getThirdId().equals(businessId) && sc.getMarketingActivityId() != null) {
                    markMap.put(sc.getMarketingActivityId(), sc.getMarketing());
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
                                            mark.setFullbuyReduceMarketing(fpm);
                                        }
                                    }
                                }
                                //购物车项中满减重新赋值（满足条件的最佳满减）
                                for (int i = 0; i <shopdata.size() ; i++) {
                                    ShoppingCart scart = shopdata.get(i);
                                    if (scart.getMarketingActivityId() != null && scart.getMarketingActivityId().intValue() == markId.intValue()){
                                        scart.getMarketing().setFullbuyReduceMarketing(mark.getFullbuyReduceMarketing());
                                    }
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
                                            mark.setFullbuyDiscountMarketing(fdm);
                                        }
                                    }
                                }
                                //购物车项中满折重新赋值（满足条件的最佳满折）
                                for (int i = 0; i <shopdata.size() ; i++) {
                                    ShoppingCart scart = shopdata.get(i);
                                    if (scart.getMarketingActivityId() != null && scart.getMarketingActivityId().intValue() == markId.intValue()){
                                        scart.getMarketing().setFullbuyDiscountMarketing(mark.getFullbuyDiscountMarketing());
                                    }
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

            for (int k = 0; k < cartList.size(); k++) {
                // 直降
                if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getPriceOffMarketing() != null) {
                    // sumOldPrice =
                    // sumOldPrice.subtract(cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue
                    // ().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));

                    //订单出现负数情况
                    BigDecimal srcPrice = cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum()));
                    BigDecimal desPrice = cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum()));
                    if(desPrice.compareTo(srcPrice) > 0){
                        flag = flag.subtract(srcPrice.subtract(BigDecimal.valueOf(0.01)));
                    }else{
                        flag = flag.subtract(desPrice);
                    }
                    //flag = flag.subtract(cartList.get(k).getMarketing().getPriceOffMarketing().getOffValue().multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));

                }

                //团购
//                if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getGroupon() != null) {
//                    flag = flag.subtract((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getGroupon().getGrouponDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
//
//                }
                //抢购
                if (cartList.get(k).getMarketing() != null && cartList.get(k).getMarketing().getRushs() != null &&! cartList.get(k).getMarketing().getRushs().isEmpty()) {
                    flag = flag.subtract((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getRushs().get(0).getRushDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                    rushFlag = rushFlag.subtract((BigDecimal.ONE.subtract(cartList.get(k).getMarketing().getRushs().get(0).getRushDiscount())).multiply(cartList.get(k).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice()).multiply(BigDecimal.valueOf(cartList.get(k).getGoodsNum())));
                }
            }

        }
        sumPrice = flag.subtract(taozhuan);
        //单个商品座促销时的价格小于0，变成0.01
        if(sumPrice.compareTo(new BigDecimal(0))<0){
            sumPrice = BigDecimal.valueOf(0.01);
        }
        notRushSumPrice = sumPrice.subtract(rushFlag);
        paramMap.put(SUMOLDPRICE, sumOldPrice);
        paramMap.put(SUMPRICE, sumPrice);
        paramMap.put(NOTRUSHSUMPRICE, notRushSumPrice);
        return paramMap;
    }

    /**
     * 获取第三方订单金额
     *
     * @param thirdId
     * @param codeNo
     * @param amount
     * @param customerId
     * @param orderCode
     * @param cityId
     * @param distinctId
     * @param shoplist
     * @param typeIdflag
     * @return
     */
    public Order getThirdOrderPrice(Long thirdId, String codeNo, Long amount, Long customerId, String orderCode, Long cityId, Long distinctId, List<ShoppingCart> shoplist,
                                    boolean typeIdflag) {

        Order order = new Order();
        Map<String, Object> map = getEveryThirdPriceMap(thirdId, shoplist, distinctId);
        // 订单交易总金额
        BigDecimal sumPrice = BigDecimal.valueOf(Double.valueOf(map.get(SUMPRICE).toString()));
        //不含抢购商品交易总金额
        BigDecimal notRushSumPrice = BigDecimal.valueOf(Double.valueOf(map.get(NOTRUSHSUMPRICE).toString()));
        // 原总金额
        BigDecimal sumOldPrice = BigDecimal.valueOf(Double.valueOf(map.get(SUMOLDPRICE).toString()));
        // 总优惠金额
        BigDecimal prePrice = sumOldPrice.subtract(sumPrice);
        order.setPromotionsPrice(prePrice);
        CustomerAllInfo cus = (CustomerAllInfo)customerServiceInterface.queryCustomerById(customerId);
        // 会员折扣
        BigDecimal discountPrice = customerPointServiceMapper.getCustomerDiscountByPoint(cus.getInfoPointSum()).multiply(notRushSumPrice);
        discountPrice = notRushSumPrice.subtract(discountPrice);
        // 判断库存
        String stock = map.get(STOCK).toString();
        // 有商品存在无货返回
        if ("0".equals(stock)) {
            return null;
        }
        if (codeNo != null && !"".equals(codeNo)) {
            Coupon coupon = couponService.selectCouponByCodeNo(codeNo);
            boolean couponFlag = shoppingCartService.checkCouponIsOk(customerId,shoplist,coupon,distinctId);
            if(couponFlag == true){
                if (coupon != null && thirdId.equals(coupon.getBusinessId())) {
                    // 直降
                    if ("1".equals(coupon.getCouponRulesType())) {
                        // 计算交易价格减去金额
                        sumPrice = sumPrice.subtract(coupon.getCouponStraightDown().getDownPrice());
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponStraightDown().getDownPrice());
                        //订单使用的优惠券券码
                        order.setCouponNo(codeNo);

                        order.setCouponPrice(coupon.getCouponStraightDown().getDownPrice());

                    }
                    // 满减
                    if ("2".equals(coupon.getCouponRulesType())) {
                        // 计算交易价格减去金额
                        sumPrice = sumPrice.subtract(coupon.getCouponFullReduction().getReductionPrice());
                        // 优惠金额+金额
                        prePrice = prePrice.add(coupon.getCouponFullReduction().getReductionPrice());
                        //订单使用的优惠券券码
                        order.setCouponNo(codeNo);

                        order.setCouponPrice(coupon.getCouponFullReduction().getReductionPrice());
                    }
                }
            }
        }
        // boss商品兑换积分

        // boss商品兑换积分
        BigDecimal expressPrice = BigDecimal.ZERO;
        // 在线支付没有运费
        // 得到没有包邮或者没有达到条件的购物车集合
        List<ShoppingCart> cartList = shoppingCartService.getNobaoyouShoppingcarts(shoplist, distinctId);
        // 计算运费
        expressPrice = shoppingCartService.getEverythirdExpressPrice(thirdId, cityId, cartList);
        /* 积分兑换订单金额 */
        if (0 == thirdId && null != amount && amount != 0) {
            // 获得当前用户的积分信息
            CustomerPoint customerPoint = couponService.selectCustomerPointByCustomerId(customerId);

            customerPoint.setPointSum(customerPoint.getPointSum() - customerPointServiceMapper.getCustomerReducePoint(customerId + ""));
            // 积分兑换规则
            PointSet pointSet = this.couponService.selectPointSet();

            Double bossjifen = Double.valueOf(sumPrice.add(expressPrice).divide(pointSet.getConsumption()).multiply(BigDecimal.TEN).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
            // 判断当前积分是否够兑换
            if (bossjifen < amount || customerPoint.getPointSum() < amount) {
                return null;
            }
            if (null != pointSet && "1".equals(pointSet.getIsOpen())) {
                // 转换类型
                BigDecimal zhuanhuan = new BigDecimal(amount);
                // 根据积分兑换规则 计算积分兑换金额
                BigDecimal disparityPrice = zhuanhuan.multiply(pointSet.getConsumption());
                //
                disparityPrice = disparityPrice.divide(new BigDecimal(10));
                // 对兑换处的价格进行四舍五入
                BigDecimal jiFenDuiHuan = disparityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                // 计算最后订单金额
                sumPrice = sumPrice.subtract(jiFenDuiHuan);
                // 对兑换处的价格进行四舍五入
                sumPrice = sumPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                // 修改会员的积分
                this.updatePoint(customerId, amount);
                //积分兑换金额
                order.setJfPrice(jiFenDuiHuan);
                this.insertExchangeCusmomer(customerId, orderCode, amount, disparityPrice, pointSet.getConsumption());
            }
        }
        order.setOrderPrePrice(prePrice);
        // 计算交易价格加上运费
        sumPrice = sumPrice.add(expressPrice);
        if (0 == thirdId) {
            // 只有boss的商品存在会员折扣 实际付款减去会员折扣的钱
            sumPrice = sumPrice.subtract(discountPrice);
            // 订单优惠金额 加上会员折扣的金额
            order.setOrderPrePrice(prePrice.add(discountPrice));
        }
        if (sumPrice.compareTo(BigDecimal.valueOf(0.02)) == -1) {
            sumPrice = BigDecimal.valueOf(0.01);
        }
        order.setOrderPrice(sumPrice);
        order.setOrderOldPrice(sumOldPrice);

        order.setExpressPrice(expressPrice);

        order.setDiscountPrice(discountPrice);

        return order;
    }

    /**
     * 对多级满赠集合进行排序 从高到底
     * @param fplist
     * @return
     */
    public List<FullbuyPresentMarketing> sortFullList(List<FullbuyPresentMarketing> fplist){
        List<FullbuyPresentMarketing> fplistnew = new ArrayList<FullbuyPresentMarketing>();
        for(FullbuyPresentMarketing full:fplist){
            if(fplistnew.size()==0){
                fplistnew.add(full);
            }else if(fplistnew.size()==1){
                if(fplistnew.get(0).getFullPrice().compareTo(full.getFullPrice())==1){
                    fplistnew.add(full);
                }else{
                    fplistnew.add(0,full);
                }
            }else{
                if(fplistnew.get(0).getFullPrice().compareTo(full.getFullPrice())==-1){
                    fplistnew.add(0,full);
                }else if(fplistnew.get(1).getFullPrice().compareTo(full.getFullPrice())==-1){
                    fplistnew.add(1,full);
                }else{
                    fplistnew.add(full);
                }
            }
        }

        return fplistnew;
    }


    /**
     * 验证满赠促销  并查询赠品信息
     * @param shoplist
     * @param cartWareUtil
     * @return
     */
    public List<GoodsProduct> getFullList(List<ShoppingCart> shoplist,ShoppingCartWareUtil cartWareUtil,String presentScopeIds){

        List<GoodsProduct> goodslist = new ArrayList<GoodsProduct>();
        //已选择购物车中商品总价（用于判断是否满足满金额赠）
        Map<Long,BigDecimal> allPrices = new HashMap<Long,BigDecimal>();
        //满数量赠 购买总数
        Map<Long,Long> allcount = new HashMap<Long,Long>();
        //查询出的促销信息
        Map<Long,Marketing> allmark = new HashMap<Long,Marketing>();

        if (shoplist != null && !shoplist.isEmpty()) {
            for (int i = 0; i < shoplist.size(); i++) {
                // 查询商品详细
                shoplist.get(i).setGoodsDetailBean(siteGoodsProductService.querySimpleDetailBeanByProductId(((ShoppingCart) shoplist.get(i)).getGoodsInfoId()));
                //购物车商品所参加的促销id
                Long marketingId = shoplist.get(i).getMarketingActivityId();
                if(null!=marketingId){
                    //查询促销id是否为满赠促销
                    Marketing mk = this.marketService.marketingDetail(marketingId);
                    if(null!=mk){
                        //如果促销是满赠促销
                        if(mk.getCodexType().equals("6")){
                            //数量
                            Long count = shoplist.get(i).getGoodsNum();
                            if(null!=mk.getFullbuyPresentMarketings() && mk.getFullbuyPresentMarketings().size()>0){
                                //满金额赠
                                if(mk.getFullbuyPresentMarketings().get(0).getPresentType().equals("0")) {
                                    //计算商品总价
                                    //单价
                                    BigDecimal price = shoplist.get(i).getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();

                                    //如果已经存在的满金额赠促销  则进行价格累加  否则新增一个
                                    if(allPrices.containsKey(marketingId)){
                                        allPrices.put(marketingId,allPrices.get(marketingId).add(price.multiply(new BigDecimal(count))));
                                    }else{
                                        allPrices.put(marketingId,price.multiply(new BigDecimal(count)));
                                    }
                                    //将符合条件的促销 存入map  便于后面使用
                                    allmark.put(marketingId,mk);
                                }else{//满数量赠  如果已经存在的满数量赠促销  则进行数量累加  否则新增一个
                                    if(allcount.containsKey(marketingId)){
                                        allcount.put(marketingId,allcount.get(marketingId)+count);
                                    }else{
                                        allcount.put(marketingId,count);
                                    }
                                    //将符合条件的促销 存入map  便于后面使用
                                    allmark.put(marketingId,mk);
                                }
                            }

                        }
                    }
                }
            }
        }

        //此处是判断 满赠是否满足条件的 如满足  把赠品查出来
        //促销id集合（满足满赠条件的）
        List<Long> marketlist = new ArrayList<Long>();
        Map<Long,FullbuyPresentMarketing> markmap = new HashMap<Long,FullbuyPresentMarketing>();
        //满金额赠 键集合
        Iterator fullpricekey = allPrices.keySet().iterator();
        while(fullpricekey.hasNext()){
            //查询促销
            Long fpk  = (Long)fullpricekey.next();
            //从之前查出的促销中 取出  不用再次查询数据库
            Marketing mk = allmark.get(fpk);
            if(null!=mk && null!=mk.getFullbuyPresentMarketings()){
                List<FullbuyPresentMarketing> fplist = mk.getFullbuyPresentMarketings();
                List<FullbuyPresentMarketing> fplistnew = this.sortFullList(fplist);
                //如果总金额 满足满金额赠 则将促销id存入集合中
                for(int i=0;i<fplistnew.size();i++){
                    //如果总数量 满足满数量赠 则将促销id存入集合中
                    if(!(allPrices.get(fpk).compareTo(fplistnew.get(i).getFullPrice())==-1)){
                        marketlist.add(fpk);
                        markmap.put(fpk,fplistnew.get(i));
                        break;
                    }
                }
            }
        }

        //满数量赠 键集合
        Iterator fullcountkey = allcount.keySet().iterator();
        while(fullcountkey.hasNext()){
            //查询促销
            Long fpk = (Long)fullcountkey.next();
            //从之前查出的促销中 取出  不用再次查询数据库
            Marketing mk = allmark.get(fpk);
            if(null!=mk && null!=mk.getFullbuyPresentMarketings()){
                List<FullbuyPresentMarketing> fplist = mk.getFullbuyPresentMarketings();
                List<FullbuyPresentMarketing> fplistnew = this.sortFullList(fplist);
                for(int i=0;i<fplistnew.size();i++){
                    //如果总数量 满足满数量赠 则将促销id存入集合中
                    if(!(allcount.get(fpk).compareTo(fplistnew.get(i).getFullPrice().longValue())==-1)){
                        marketlist.add(fpk);
                        markmap.put(fpk,fplistnew.get(i));
                        break;
                    }
                }

            }
        }


        //定义集合  存放促销id 赠品集合及对应数量
//        List<FullbuyPresentMarketing> fulllist = new ArrayList<FullbuyPresentMarketing>();

        //拆分presentScopeIds 满赠赠品  将赠品对应的插入购物车商品的  赠品字段中 shopid:scopeid,scopeid..;...
        if(null!=presentScopeIds && !presentScopeIds.equals("")){
            //拆封后的数组
            String [] presents = presentScopeIds.split(";");
            if(presents.length>0){
                for(int i=0;i<presents.length;i++){
                    if(presents[i].indexOf(":")!=-1){
                        //购物车id
                        Long shopId = Long.valueOf(presents[i].split(":")[0]);
                        //对应的赠品id组合
                        String scopeIds = presents[i].split(":")[1];
                        for(ShoppingCart shop:shoplist){
                            if(shop.getShoppingCartId().equals(shopId)){
                                //将对应的赠品id组合放入购物车字段中
                                shop.setPresentScopeIds(scopeIds);
                            }
                        }
                    }
                }

            }
        }

        //遍历购物车商品  将满足促销的赠品存入赠品集合中
        for(ShoppingCart shop:shoplist){
            if(null!=shop.getMarketingActivityId()){
                if(marketlist.contains(shop.getMarketingActivityId())){
                    //定义赠品id集合
                    List<String>  goodlist = new ArrayList<String>();
                    if(null!=shop.getPresentScopeIds()){
                        //将赠品存入 集合中
                        goodlist.addAll(Arrays.asList(shop.getPresentScopeIds().split(",")));
                        //移除这个促销id  防止重复添加
                        marketlist.remove(shop.getMarketingActivityId());
                    }

                    FullbuyPresentMarketing fpms = markmap.get(shop.getMarketingActivityId());
                    List<FullbuyPresentScope> fpss = fpms.getFullbuyPresentScopes();
                    List<Long> goodsinfos = new ArrayList<Long>();
                    //将促销中赠品id拿出来
                    if(null!=fpss){
                        for(FullbuyPresentScope good:fpss){
                            goodsinfos.add(good.getPresentScopeId());
                        }
                    }

                    //此处判断是否为null或空  如果是 则是立即购买 购物车选择不赠scopeid为0
                    if(null==presentScopeIds || presentScopeIds.equals("")){
                        //赠送规则
                        String mode = fpms.getPresentMode();
                        if(goodsinfos.size()>0){
                            if(mode.equals("0")){//满赠 则把全部赠品添加到goodlist中
                                for(Long gi:goodsinfos){
                                    goodlist.add(gi.toString());
                                }
                            }else{//选择一个  把第一个赠品添加到goodlsit中
                                goodlist.add(goodsinfos.get(0).toString());
                            }
                        }

                    }
                    //去除goodlist中重复的值 防止从页面传过来的赠品id有重复值
                    HashSet h = new HashSet(goodlist);
                    goodlist.clear();
                    goodlist.addAll(h);

                    //判断 选择的赠品是否属于 促销中的赠品
                    for(int i=0;i<goodlist.size();i++){
                        if(!goodsinfos.contains(Long.valueOf(goodlist.get(i)))){
                            goodlist.remove(i);
                            i--;
                        }
                    }

                    //遍历赠品id集合  查询赠品信息存入 赠品list中
//                    List<FullbuyPresentScope> fullscope = new ArrayList<FullbuyPresentScope>();
                    for(String infoid:goodlist){
                        //定义赠品对象
                        FullbuyPresentScope fps = new FullbuyPresentScope();
                        //赠品信息
                        FullbuyPresentScope fpt = this.fullbuyPresentScopeMapper.selectByPrimaryKey(Long.valueOf(infoid));
                        if(null!=fpt){
                            //货品信息
                            GoodsProductVo vo = siteGoodsProductService.queryByProductIdForPresent(fpt.getScopeId(),cartWareUtil.getDistrictId());
                            GoodsProduct gp = new GoodsProduct();
                            if(null!=vo  && null!=vo.getGoodsInfoStock()){
                                gp.setThirdId(vo.getThirdId());
                                gp.setGoodsInfoStock(0L);
                                gp.setFullMarketId(shop.getMarketingActivityId());
                                gp.setGoodsId(vo.getGoodsId());
                                gp.setGoodsInfoId(vo.getGoodsInfoId());
                                for(int j=0;j<fpss.size();j++){
                                    if(fpss.get(j).getScopeId().equals(fpt.getScopeId())){
                                        //数量 默认为1
                                        fps.setScopeNum(fpss.get(j).getScopeNum());
//                                        if(fpss.get(j).getScopeNum()>vo.getGoodsInfoStock()){
//                                            gp.setGoodsInfoStock(vo.getGoodsInfoStock());
//                                        }else{
//                                            gp.setGoodsInfoStock(fpss.get(j).getScopeNum());
//                                        }
                                        gp.setGoodsInfoStock(fpss.get(j).getScopeNum());
                                    }
                                }
                                goodslist.add(gp);
                            }
                        }
                    }
                }
            }
        }

        return goodslist;
    }


    /**
     * 提交订单(新)
     *
     * @param request
     * @param shoppingCartId
     * @param customerRemark
     * @param chInvoiceTitle
     * @param chInvoiceContent
     * @return
     * @throws IOException
     */
    @Override
    @Transactional
    public List<Order> newsubmitOrder(HttpServletRequest request, Long[] shoppingCartId, String customerRemark, String chInvoiceTitle, String chInvoiceContent) throws IOException {

        Long custAddress = Long.parseLong(request.getParameter("custAddress"));
        String payflag = request.getParameter("ch_pay");
        String thirdpayflag = request.getParameter("ch_paythird");
        String deliflag = request.getParameter("deliveryPointId");
        String typeflag = request.getParameter("typeId");
        // 平台支付方式初始化
        Long chPay = 1L;
        // 第三方支付方式初始化,目前只支持在线支付
        Long chPaythird = 1L;
        // 配送方式0:默认模板配送,1:上门自提
        Long typeId = null;
        if (StringUtils.isNotEmpty(typeflag)) {
            typeId = Long.valueOf(typeflag);
        }
        Long deliveryPointId = null;
        if (StringUtils.isNotEmpty(payflag)) {
            chPay = Long.valueOf(payflag);
        }
        if (StringUtils.isNotEmpty(deliflag)) {
            deliveryPointId = Long.valueOf(deliflag);
        }

        if (StringUtils.isNotEmpty(thirdpayflag)) {
            chPaythird = Long.valueOf(thirdpayflag);
        }
        String chVoinceType = request.getParameter("ch_voinceType");
        // 优惠劵码
        String codeNo = request.getParameter("codeNo");
        Long amount = 0L;
        // 兑换积分
        if (StringUtils.isNotEmpty(request.getParameter("amount"))) {
            amount = Long.parseLong(request.getParameter("amount"));
        }
        //安全问题防止积分为负数
        if(amount.intValue() < 0){
            amount = 0L;
        }
        // 获取所有所有选中商品信息
        List<ShoppingCart> shoplist = shoppingCartMapper.shopCartListByIds(Arrays.asList(shoppingCartId));
        if (CollectionUtils.isEmpty(shoplist)) {
            return Collections.emptyList();
        }

        // 当前登录成功的会员
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        //根据用户id和地址id查询用户地址
        CustomerAddress ca = customerServiceInterface.selectByAddrIdAndCusId(customerId, custAddress);

        Long distinctId = null;
        if (ca != null && ca.getDistrict() != null) {
            distinctId = ca.getDistrict().getDistrictId();
        }else {
            //地址信息异常
            LOGGER.info("用户customerId:"+customerId+"          地址信息异常:custAddress="+custAddress);
            return Collections.emptyList();
        }
        Map<Long, Object> thirdIdMap = new HashMap<>();
        for (ShoppingCart cart : shoplist) {
            if (cart.getFitId() == null) {
                thirdIdMap.put(cart.getThirdId(), "");
            } else {

                // 如果商品是套装
                GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(cart.getFitId());
                if (null != goodsGroupVo) {

                    thirdIdMap.put(goodsGroupVo.getThirdId(), "");
                    cart.setGoodsGroupVo(goodsGroupVo);
                    cart.setThirdId(goodsGroupVo.getThirdId());
                }
            }
        }

        ShoppingCartWareUtil cartWareUtil = shoppingCartAddressService.loadAreaFromRequest(request);


        //验证满赠促销
        //如果满足满赠促销  查询满赠商品信息
        String presentScopeIds = request.getParameter("presentScopeIds");
        List<GoodsProduct> fulllist = this.getFullList(shoplist,cartWareUtil,presentScopeIds);

        Map<String, Object> para = new HashMap<>();
        // 仓库信息
        List<CalcStockUtil> calcStockUtils = new ArrayList<>();

        // 主订单号
        String orderOldCode = UtilDate.mathString(new Date());

        // 随机数
        int number = 10;
        // 查询折扣map
        // Map<String, Object> marketMap = new HashMap<>();
        List<Order> maps = new ArrayList<>();
        // 循环商家ID
        for (Long thirdId : thirdIdMap.keySet()) {
            // 查询购买的商品
            List<OrderGoods> oglist = new ArrayList<>();
            // 申明Order
            Order order = new Order();
                // 循环所购买的商品
            for (ShoppingCart cart : shoplist) {
                if (cart.getFitId() != null) {
                    if (thirdId.equals(cart.getGoodsGroupVo().getThirdId())) {
                        OrderGoods og = null;
                        //套装商品总的价格
                        BigDecimal groupProductAllPrice = BigDecimal.ZERO;
                        //套装商品除最后一件商品的总价
                        BigDecimal allPriceExceptLast = BigDecimal.ZERO;
                        // 获取此套装下的所有货品
                        List<GoodsProductVo> goodsProducts = siteGoodsProductService.queryDetailByGroupId(cart.getFitId());

                        List<GoodsGroupReleProductVo> goodsGroupReleProductVos = cart.getGoodsGroupVo().getProductList();
                        //循环goodsProduct，货品数量为套装中该货品数量
                        for (int x = 0; x < goodsProducts.size(); x++) {
                            for (GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos) {
                                if (goodsGroupReleProductVo.getProductId().intValue() == goodsProducts.get(x).getGoodsInfoId().intValue()) {
                                    goodsProducts.get(x).setGroupProductNum((BigDecimal.valueOf(cart.getGoodsNum()).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum()))));
                                }
                            }
                        }
                        for (int j = 0; j < goodsProducts.size(); j++) {
                            // 查询库存
                            ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(goodsProducts.get(j).getGoodsInfoId(), distinctId);
                            Long cartNum = cart.getGoodsNum();
                            if (productWare != null && productWare.getWareStock() - cartNum <= 0) {
                                // 设置商品库存
                                return Collections.emptyList();

                            }
                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            og = new OrderGoods();
                            for (GoodsGroupReleProductVo goodsGroupReleProductVo : goodsGroupReleProductVos) {
                                if (goodsGroupReleProductVo.getProductId().intValue() == goodsProducts.get(j).getGoodsInfoId().intValue()) {
                                    //套装中某件货品数量=创建套装时的件数*购买套装数量
                                    og.setGoodsInfoNum((BigDecimal.valueOf(cartNum).multiply(BigDecimal.valueOf(goodsGroupReleProductVo.getProductNum()))).longValue());
                                }
                            }
                            //计算套装商品总价
                            if (productWare != null) {
                                og.setGoodsInfoOldPrice(productWare.getWarePrice());
                                for (int m = 0; m < goodsProducts.size(); m++) {
                                    for (int k = 0; k < goodsProducts.get(m).getProductWares().size(); k++) {
                                        if (productWare.getWareId().intValue() == goodsProducts.get(m).getProductWares().get(k).getWareId().intValue()) {
                                            groupProductAllPrice = groupProductAllPrice.add(goodsProducts.get(m).getProductWares().get(k).getWarePrice().multiply(goodsProducts.get(m).getGroupProductNum()));
                                        }
                                    }
                                }
                            } else {
                                og.setGoodsInfoOldPrice(goodsProducts.get(j).getGoodsInfoPreferPrice());
                                groupProductAllPrice = groupProductAllPrice.add(goodsProducts.get(j).getGoodsInfoPreferPrice());
                            }
                            //套装优惠总数分摊到单间货品身上的钱数（退货用，中间量，不保存数据库）
                            BigDecimal groupPreferamount = cart.getGoodsGroupVo().getGroupPreferamount()
                                    .multiply(BigDecimal.valueOf(cartNum));
                            if (j == (goodsProducts.size() - 1)) {
                                //套装货品最后一件优惠价格=总优惠价格-前面所有货品总价（防止不整除出现少0.01）
                                og.setProductGroupPrice(groupPreferamount.subtract(allPriceExceptLast));
                            } else {
                                og.setProductGroupPrice(og.getGoodsInfoOldPrice().multiply(BigDecimal.valueOf(og.getGoodsInfoNum())).multiply(groupPreferamount).divide(groupProductAllPrice, 2, BigDecimal.ROUND_HALF_EVEN));
                                //套装商品除了最后一个商品的总价（套装价格分摊不整除情况）
                                allPriceExceptLast = allPriceExceptLast.add(og.getProductGroupPrice());
                            }
                            groupProductAllPrice = BigDecimal.ZERO;
                            calcStockUtil.setIsThird(goodsProducts.get(j).getIsThird());
                            calcStockUtil.setDistinctId(cartWareUtil.getDistrictId());
                            calcStockUtil.setIsThird(goodsProducts.get(j).getIsThird());
                            calcStockUtil.setDistinctId(cartWareUtil.getDistrictId());
                            // 商品id
                            calcStockUtil.setProductId(goodsProducts.get(j).getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock(Integer.parseInt(og.getGoodsInfoNum().toString()));
                            calcStockUtils.add(calcStockUtil);
                            og.setDelFlag("0");
                            //商品组合标记
                            og.setIsGroup("1");
                            og.setDistinctId(cartWareUtil.getDistrictId());
                            og.setBuyTime(new Date());
                            og.setEvaluateFlag("0");
                            og.setGoodsInfoPrice(BigDecimal.valueOf(0));
                            og.setGoodsInfoId(goodsProducts.get(j).getGoodsInfoId());
                            og.setGoodsId(goodsProducts.get(j).getGoodsId());
                            // 最终售价
                            og.setGoodsInfoPrice(og.getGoodsInfoOldPrice());
                            // 小计金额
                            og.setGoodsInfoSumPrice(og.getGoodsInfoPrice().multiply(BigDecimal.valueOf(og.getGoodsInfoNum())));
                            oglist.add(og);

                        }

                    }
                } else {
                    // 查询商品详细
                    cart.setGoodsDetailBean(siteGoodsProductService.querySimpleDetailBeanByProductId(((ShoppingCart) cart).getGoodsInfoId()));
                    // 判断购买商品是否属于此商家
                    if (thirdId.equals(cart.getGoodsDetailBean().getProductVo().getThirdId())) {

                        if (thirdId == 0 && distinctId != null) {
                            // 查询库存
                            ProductWare productWare = productWareService.queryProductWareByProductIdAndDistinctId(cart.getGoodsDetailBean().getProductVo()
                                    .getGoodsInfoId(), distinctId);
                            if (productWare != null) {
                                // 设置商品库存
                                ((ShoppingCart) cart).getGoodsDetailBean().getProductVo().setGoodsInfoStock(productWare.getWareStock());
                                ((ShoppingCart) cart).getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(productWare.getWarePrice());
                                if (productWare.getWareStock() - cart.getGoodsNum() < 0) {
                                    return Collections.emptyList();
                                }

                            }
                        } else {
                            //商家的货品 判断
                            if (cart.getGoodsDetailBean().getProductVo().getGoodsInfoStock() - cart.getGoodsNum() < 0) {
                                return Collections.emptyList();
                            }
                        }


                        BigDecimal goodsprice = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();
                        // 是否抹掉分角
                        String discountFlag = "";
                        DecimalFormat myformat = null;
                        myformat = new DecimalFormat("0.00");
                        // 货品价格中间变量
                        BigDecimal goodspriceflag = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice();

                        //单该货品同时参与了团购和折扣时,优先级团购优先
                        if (cart.getGoodsGroupId() != null) {
                            // 从购物车里得到促销id重新从数据库查询,防止当前(团购促销)已经过期;
                            Marketing mark = marketService.querySimpleMarketingById(cart.getGoodsGroupId());
                            if (mark != null) {
                                Groupon groupon = grouponMapper.selectByMarketId(mark.getMarketingId());
                                if (groupon != null) {

                                    goodsprice = goodspriceflag.multiply(groupon.getGrouponDiscount());
                                }
                            }
                        }
                        // 折扣促销
                        if (cart.getGoodsGroupId() == null && cart.getMarketingId() != null && 0 != cart.getMarketingId()) {
                            // 从购物车里得到促销id重新从数据库查询,防止当前(折扣促销)已经过期;
                            Marketing mark = marketService.marketingDetail(cart.getMarketingId(), cart.getGoodsInfoId());
                            if (mark != null) {
                                para.put("marketingId", mark.getMarketingId());
                                para.put("goodsId", cart.getGoodsInfoId());
                                PreDiscountMarketing premark = preDiscountMarketingMapper.selectByMarketId(para);
                                if (premark != null) {
                                    // 货品价格
                                    goodsprice = goodspriceflag.multiply(premark.getDiscountInfo());
                                    discountFlag = premark.getDiscountFlag();
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
                            goodsprice = cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice().multiply(cart.getMarketing().getGroupon().getGrouponDiscount());
                        }
                        cart.getGoodsDetailBean().getProductVo().setGoodsInfoPreferPrice(goodsprice);
                        // 初始化订单商品信息
                        OrderGoods og = new OrderGoods();
                        og.setBrandId(cart.getGoodsDetailBean().getProductVo().getGoods().getBrandId());
                        // 设置数量
                        og.setGoodsInfoNum(cart.getGoodsNum());
                        // 设置删除标记
                        og.setDelFlag("0");
                        // 设置购买时间
                        og.setBuyTime(new Date());
                        // 设置收货地区
                        og.setDistinctId(cartWareUtil.getDistrictId());
                        og.setEvaluateFlag("0");
                        // 设置商品原价
                        og.setGoodsInfoOldPrice(cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice());
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
                        // 货品价格
                        og.setGoodsInfoOldPrice(cart.getGoodsDetailBean().getProductVo().getGoodsInfoPreferPrice());
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
                        calcStockUtil.setDistinctId(cartWareUtil.getDistrictId());
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
                        oglist.add(og);
                    }
                }
            }

                WareHouse ware = productWareMapper.findWare(ca.getDistrict().getDistrictId());
                // 封装物流信息
                OrderExpress oe = new OrderExpress();
                // 当为在线支付且是上门自提时运费是没有的
                boolean typeIdflag = true;
                // 查询物流模板信息 根据thirdId 查询默认的模板
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("freightIsDefault", "1");
                paramMap.put("freightThirdId", thirdId);
                // 获取默认模板
                FreightTemplate ft = freightTemplateMapper.selectFreightTemplateSubOrder(paramMap);
                Long comId = null;
                String comName = "";
                // 获取物流信息
                FreightExpress fe = freightExpressMapper.selectTemplateExpress(ft.getFreightTemplateId()).get(0);
                if (thirdId.equals(0L)) {

                    fe.setSysLogisticsCompany(sysLogisticsCompanyMapper.selectCompanyById(fe.getLogComId()));
                    comId = fe.getSysLogisticsCompany().getLogComId();
                    comName = fe.getSysLogisticsCompany().getName();
                } else {
                    Express express = expressInfoMapperThird.selectByshoreExpId(fe.getLogComId());
                    if (null != express) {
                        comId = express.getShoreExpId();
                        comName = express.getExpName();
                    }

                }
                if (fe != null) {
                    oe.setExpressId(comId);
                    oe.setExpressName(comName);
                }

                if (typeId != null && typeId == 1 && "0".equals(thirdId.toString()) && chPay != null && chPay.equals(1L)) {
                    typeIdflag = true;
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
                    order.setShippingCountyId(point.getDistrictId());
                    // 设置配送方式
                    order.setOrderExpressType("1");
                    order.setExpressPrice(null);
                } else {
                    // 设置配送方式
                    order.setOrderExpressType("0");
                    typeIdflag = true;
                    // 用户默认收货地址ID
                    order.setShoppingAddrId(custAddress);
                    // 省
                    order.setShippingProvince(ca.getProvince().getProvinceName());
                    // 市
                    order.setShippingCity(ca.getCity().getCityName());
                    // 区
                    order.setShippingCounty(ca.getDistrict().getDistrictName());
                    // 详细地址
                    order.setShippingAddress(ca.getAddressDetail());
                    // 区ID
                    order.setShippingCountyId(ca.getDistrict().getDistrictId());

                    // 配送方式名称
                    oe.setExpressTypeName(ft.getFreightTemplateName());

                    // 配送方式 0 快递配送 1 上门自提
                    oe.setExpressTypeId(0L);
                }
                oe.setDelFlag("0");
                if (ware != null) {
                    // 收获地址信息
                    order.setWareName(ware.getWareName());
                    // 库存ID
                    order.setWareId(ware.getWareId());
                } else {
                    if("0".equals(thirdId.toString())) {
                        // 返回购物车
                        return Collections.emptyList();
                    }
                }

                // 收货人
                order.setShippingPerson(ca.getAddressName());
                // 电话
                order.setShippingPhone(ca.getAddressPhone());
                // 手机
                order.setShippingMobile(ca.getAddressMoblie());
                // 备注
                order.setCustomerRemark(customerRemark);
                // 邮编
                order.setShippingPostcode(ca.getAddressZip());
                // 临时账号
                isTempCust(request, ca);
                // 子订单号
                String orderCode = UtilDate.mathString(new Date()) + (number++);
                // 插入订单促销id
                order.setOrderCode(orderCode);
                order.setOrderOldCode(orderOldCode);

                // 发票信息
                order.setInvoiceType(chVoinceType);
                // 如果为1，普通发票，设置发票抬头和内容
                if ("1".equals(chVoinceType)) {
                    // 发票抬头
                    order.setInvoiceTitle(chInvoiceTitle);
                    // 发票内容
                    order.setInvoiceContent(chInvoiceContent);
                }
                order.setDelFlag("0");

                // 订单状态
                order.setOrderStatus("0");

                Order orderpirce = getThirdOrderPrice(thirdId, codeNo, amount, customerId, orderCode, ca.getCity().getCityId(), ca.getDistrict().getDistrictId(), shoplist,
                        typeIdflag);
                if (orderpirce == null) {
                    return Collections.emptyList();
                }
                //积分兑换金额
                order.setJfPrice(orderpirce.getJfPrice());
                //使用积分
            if (0 == thirdId && null != amount && amount != 0) {
                order.setOrderIntegral(amount);
            }
                // 订单总金额
                order.setOrderPrice(orderpirce.getOrderPrice());
                // 原始总额
                order.setOrderOldPrice(orderpirce.getOrderOldPrice());
                // 总优惠金额
                order.setOrderPrePrice(orderpirce.getOrderPrePrice());

                // 会员折扣价格
                order.setDiscountPrice(orderpirce.getDiscountPrice());

                // 会员优惠价格
                order.setCouponPrice(orderpirce.getCouponPrice());

                // 促销价格
                order.setPromotionsPrice(orderpirce.getPromotionsPrice());


                // 运费
                order.setExpressPrice(orderpirce.getExpressPrice());
                // 如果为2，表示货到付款
                // 如果为2,未付款（货到付款）
                if (thirdId == 0) {
                    // 支付方式
                    order.setPayId(chPay);
                    if (chPay == 2) {
                        // 货到付款
                        order.setOrderLinePay("0");
                    } else {
                        // 在线支付
                        order.setOrderLinePay("1");
                    }

                } else {
                    // 第三方支付方式
                    order.setPayId(chPaythird);
                    // 目前只支持在线支付
                    order.setOrderLinePay("1");
                }

                order.setBusinessId(thirdId);
                // 判断是否是平台商品
                if (thirdId == 0) {
                    // 查询直营店开启状态
                    String status = basicService.getStoreStatus();
                    // 判断是否开启
                    if (("0").equals(status)) {
                        List<DirectShop> directShops = null;
                        if (typeId != null && typeId == 1 && "0".equals(thirdId.toString()) && chPay != null && chPay.equals(1L)) {// 上门自提地址
                            DeliveryPoint point = deliveryPointService.getDeliveryPoint(deliveryPointId);
                            directShops = directShopService.queryDirectShopList(point.getDistrictId());
                        } else {
                            // 快递配送查询
                            directShops = directShopService.queryDirectShopList(ca.getDistrict().getDistrictId());

                        }

                        // 判断是否为空
                        if (!directShops.isEmpty() && directShops.size() > 0) {

                            Random random = new Random();
                            // 获取随机数
                            int rNum = random.nextInt(directShops.size());
                            //发送短信
                            // 获取直营店订单id
                            // 重新设置订单商家id
                            order.setBusinessId(directShops.get(rNum).getDirectShopId());
                            // 设置为直营店订单
                            order.setDirectType("1");
                        }
                    }

                }
                // 订单使用的优惠券
                if(codeNo != null && orderpirce.getCouponNo() != null){
                    order.setCouponNo(codeNo);
                }
                order.setCustomerId((Long) request.getSession().getAttribute(CUSTOMERID));
                order.setCreateTime(new Date());
                //通过积分规则给订单添加消费获得积分
                PointSet pointSet = this.pointSetService.findPointSet();
                //积分开启可添加
                if("1".equals(pointSet.getIsOpen())){
                    IntegralSet inte = integralSetmapper.findPointSet();
                    BigDecimal orderPrice = order.getOrderPrice();
                    //每消费100元可获得inte.getExchange()点积分
                    Long getpoint = orderPrice.divide(new BigDecimal(100)).multiply(new BigDecimal(inte.getExchange())).longValue();
                    order.setOrderGetPoint(getpoint);
                }
                // 插入订单主表
                int f = orderService.insertOrder(order);
                // 插叙返回的ID
                if (f == 1) {
                    // 获取刚刚插入的订单ID
                    Long orderId = orderService.selectLastId();
                    // 修改优惠券已经使用
                    if(codeNo != null && orderpirce.getCouponNo() != null){
                        couponNoService.updateCodeIsUse(codeNo, orderCode);
                    }
                    order.setOrderId(orderId);
                    maps.add(order);
                    // 插入物流信息
                    oe.setOrderId(orderId);
                    orderExpressMapper.insertOrderExpress(oe);
                    //增加前台个人中心我的积分中积分记录
                    if(order.getOrderIntegral() != null && order.getOrderIntegral().intValue() != 0 ){
                        CustomerPoint customerPoint = new CustomerPoint();
                        customerPoint.setPointDetail("订单使用积分");
                        customerPoint.setPoint(order.getOrderIntegral().intValue());
                        customerPoint.setPointType("0");
                        customerPoint.setDelFlag("0");
                        customerPoint.setCreateTime(new Date());
                        customerPoint.setCustomerId(order.getCustomerId());
                        // 扣除后的积分保存到数据库
                        customerPointMapper.insertSelective(customerPoint);
                    }
                    // 循环设置货品级联ID信息
                    if (oglist != null && !oglist.isEmpty()) {
                        //key满减促销id,value满减的金额
                        Map<Long,BigDecimal> marketIdLists = new HashMap();
                        //key满减促销id,value满减的总金额
                        Map<Long,BigDecimal> marketLists = new HashMap();
                        for (int k = 0; k < oglist.size(); k++) {
                            if(oglist.get(k).getMarketing()!=null && "5".equals(oglist.get(k).getMarketing().getCodexType()) && null != oglist.get(k).getMarketing().getFullbuyReduceMarketing()){
                                marketIdLists.put(oglist.get(k).getMarketing().getMarketingId(), oglist.get(k).getMarketing().getFullbuyReduceMarketing().getReducePrice());
                            }
                        }
                        Iterator marketIdListsKey = marketIdLists.entrySet().iterator();
                        while(marketIdListsKey.hasNext()){
                            Map.Entry entry = (Map.Entry) marketIdListsKey.next();
                            Long marketId = (Long) entry.getKey();
                            BigDecimal totalPrice = BigDecimal.ZERO;
                            for (int p = 0; p < oglist.size(); p++) {
                                if(null != oglist.get(p).getMarketing() && oglist.get(p).getMarketing().getMarketingId().intValue() == marketId.intValue()){
                                    totalPrice = totalPrice.add(oglist.get(p).getGoodsInfoSumPrice());
                                }
                            }
                            marketLists.put(marketId,totalPrice);
                        }
                        for (int i = 0; i < oglist.size(); i++) {
                            oglist.get(i).setOrderId(orderId);
                            //抢购促销
                            if(oglist.get(i).getMarketing()!=null&& oglist.get(i).getMarketing().getRushs()!=null&&!oglist.get(i).getMarketing().getRushs().isEmpty()){
                                RushCustomer rushCustomer=new RushCustomer();
                                rushCustomer.setOrderId(orderId);
                                rushCustomer.setRushId(oglist.get(i).getMarketing().getRushs().get(0).getRushId());
                                rushCustomer.setGoodsInfoId(oglist.get(i).getGoodsInfoId());
                                rushCustomer.setCustomerId(customerId);
                                rushCustomer.setGoodsNum(Integer.valueOf(oglist.get(i).getGoodsInfoNum().toString()));
                                //rushCustomerMapper.insertCustomerRush(rushCustomer);
                                //货品退货时可退价格
                                oglist.get(i).setGoodsCouponPrice(oglist.get(i).getGoodsInfoSumPrice().multiply(BigDecimal.ONE.subtract(oglist.get(i).getMarketing().getRushs().get(0).getRushDiscount())));
                            }else{
                                //订单商品为参加促销商品
                                if(oglist.get(i).getMarketing()!=null && oglist.get(i).getMarketing().getProductReduceMoney()!=null){
                                    //满减
                                    if("5".equals(oglist.get(i).getMarketing().getCodexType()) && null != oglist.get(i).getMarketing().getFullbuyReduceMarketing()){
                                        Iterator idAndTotalRuducePriceKey = marketLists.keySet().iterator();
                                        while(idAndTotalRuducePriceKey.hasNext()){
                                            if(idAndTotalRuducePriceKey.next().equals(oglist.get(i).getMarketing().getMarketingId())){
                                                oglist.get(i).setGoodsCouponPrice(oglist.get(i).getMarketing().getFullbuyReduceMarketing().getReducePrice().multiply(oglist.get(i).getGoodsInfoSumPrice()).divide(marketLists.get(oglist.get(i).getMarketing().getMarketingId()), 4, BigDecimal.ROUND_HALF_UP));
                                            }
                                        }
                                    }else if("8".equals(oglist.get(i).getMarketing().getCodexType()) && null != oglist.get(i).getMarketing().getFullbuyDiscountMarketing()){
                                        //满折
                                        oglist.get(i).setGoodsCouponPrice(oglist.get(i).getGoodsInfoSumPrice().multiply(BigDecimal.ONE.subtract(oglist.get(i).getMarketing().getFullbuyDiscountMarketing().getFullbuyDiscount())));
                                    }else if("5".equals(oglist.get(i).getMarketing().getCodexType()) || "8".equals(oglist.get(i).getMarketing().getCodexType())){
                                        //其他没满足条件的满减满折，为正常商品
                                        if(oglist.get(i).getProductGroupPrice() != null){
                                            oglist.get(i).setGoodsCouponPrice(oglist.get(i).getProductGroupPrice());
                                        }else{
                                            oglist.get(i).setGoodsCouponPrice(BigDecimal.ZERO);
                                        }
                                    }else{
                                        //其他促销
                                        BigDecimal reduceMoney = oglist.get(i).getMarketing().getProductReduceMoney();
                                        BigDecimal sumMoney = oglist.get(i).getGoodsInfoSumPrice();
                                        //促销的价格大于原价
                                        if(reduceMoney.compareTo(sumMoney)>0){

                                        }else{

                                        }
                                        oglist.get(i).setGoodsCouponPrice(oglist.get(i).getMarketing().getProductReduceMoney());
                                        if(oglist.get(i).getGoodsCouponPrice() == null){
                                            if(oglist.get(i).getProductGroupPrice() != null){
                                                oglist.get(i).setGoodsCouponPrice(oglist.get(i).getProductGroupPrice());
                                            }else{
                                                oglist.get(i).setGoodsCouponPrice(BigDecimal.ZERO);
                                            }
                                        }
                                    }
                                }else{
                                    //订单中未参加促销的商品
                                    if(oglist.get(i).getProductGroupPrice() != null){
                                        oglist.get(i).setGoodsCouponPrice(oglist.get(i).getProductGroupPrice());
                                    }else{
                                        oglist.get(i).setGoodsCouponPrice(BigDecimal.ZERO);
                                    }
                                }
                            }
                            oglist.get(i).setGoodsBackPrice(oglist.get(i).getGoodsInfoSumPrice().subtract(oglist.get(i).getGoodsCouponPrice()));

                            BigDecimal baclPrice = oglist.get(i).getGoodsBackPrice();
                            //====订单单个商品的的退单金额，小于0时的处理
                            if(baclPrice.compareTo(BigDecimal.ZERO)<0){
                                oglist.get(i).setGoodsBackPrice(new BigDecimal(0.01));
                            }
                            //商品类型   0：普通商品
                            oglist.get(i).setIsPresent("0");
                        }
                        this.setBackPrice(oglist,order);
                        for (int l = 0; l < oglist.size(); l++) {
                            // 插入货品
                            //=======================
                            BigDecimal baclPrice = oglist.get(l).getGoodsBackPrice();
                            //====订单单个商品的的退单金额，小于0时的处理
                            if(baclPrice.compareTo(BigDecimal.ZERO)<0){
                                oglist.get(l).setGoodsBackPrice(new BigDecimal(0.01));
                            }
                            orderGoodsMapper.insertOrderGoodsInfo(oglist.get(l));
                        }
                        //设置赠品为订单商品  价格为0
                        for(GoodsProduct goods:fulllist){
                            CalcStockUtil calcStockUtil = new CalcStockUtil();
                            calcStockUtil.setIsThird(goods.getThirdId().toString());
                            calcStockUtil.setDistinctId(cartWareUtil.getDistrictId());
                            // 商品id
                            calcStockUtil.setProductId(goods.getGoodsInfoId());
                            // 减去库存
                            calcStockUtil.setStock(Integer.parseInt(goods.getGoodsInfoStock().toString()));
                            calcStockUtils.add(calcStockUtil);

                            if(goods.getThirdId().equals(thirdId)){
                                OrderGoods og = new OrderGoods();
                                og.setOrderId(orderId);
                                // 设置数量
                                og.setGoodsInfoNum(goods.getGoodsInfoStock());
                                // 设置删除标记
                                og.setDelFlag("0");
                                // 设置购买时间
                                og.setBuyTime(new Date());
                                // 设置收货地区
                                og.setDistinctId(cartWareUtil.getDistrictId());
                                og.setEvaluateFlag("0");
                                // 设置商品原价
                                og.setGoodsInfoOldPrice(BigDecimal.ZERO);
                                // 设置货品Id
                                og.setGoodsInfoId(goods.getGoodsInfoId());
                                // 设置商品ID
                                og.setGoodsId(goods.getGoodsId());
                                // 订单货品参与的其他促销绑定关系
                                og.setGoodsActiveMarketingId(goods.getFullMarketId());
                                // 货品价格
                                og.setGoodsInfoOldPrice(BigDecimal.ZERO);
                                og.setGoodsInfoPrice(BigDecimal.ZERO);
                                og.setGoodsInfoSumPrice(BigDecimal.ZERO);
                                og.setGoodsCouponPrice(BigDecimal.ZERO);
                                og.setGoodsBackPrice(BigDecimal.ZERO);
                                //商品类型   1：赠品
                                og.setIsPresent("1");

                                orderGoodsMapper.insertOrderGoodsInfo(og);

                            }
                        }
                    }
                    if ("0".equals(order.getOrderLinePay())) {
                        synOrderService.SynOrder(orderId);
                    }
                }


        }

        // 修改所有购买商品为已经删除
        shoppingCartService.deleteShoppingCartByIds(request, shoppingCartId);
        request.getSession().setAttribute(IS_TEMP_CUST, "1");
        try {
            goodsProductService.minStock(calcStockUtils);

            return maps;
        } finally {
            calcStockUtils = null;
            shoplist = null;
            maps = null;
            fulllist = null;
        }

    }

    /**
     * 计算促销后的退货价格  折扣-优惠券-积分
     * @param oglist
     * @param order
     */
    private void setBackPrice(List<OrderGoods> oglist,Order order){

        //此处是 去除促销后的 实际花费总价
        BigDecimal allprice = BigDecimal.ZERO;

        //计算当前总价
        for(OrderGoods og:oglist){
            BigDecimal backprice = og.getGoodsBackPrice();
            //非抢购
            if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))){
                allprice=allprice.add(backprice);
            }
        }

        //计算会员折扣后的优惠价格
        if(null!=order.getDiscountPrice() && order.getDiscountPrice().compareTo(BigDecimal.ZERO)==1 && order.getBusinessId().intValue() == 0){
            //订单使用的会员折扣价格
            BigDecimal discountprice = order.getDiscountPrice();
            //累加按照比例算出的会员折扣优惠价  防止四舍五入 导致的误差较大
            BigDecimal discountbackprice = BigDecimal.ZERO;
            for(OrderGoods og:oglist){
                //非抢购
                if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))){
                    BigDecimal couponprice = og.getGoodsCouponPrice();
                    BigDecimal backprice = og.getGoodsBackPrice();
                    og.setGoodsCouponPrice(couponprice.add(discountprice.multiply(backprice).divide(allprice,4,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP)));
                    discountbackprice = discountbackprice.add(discountprice.multiply(backprice).divide(allprice, 4, BigDecimal.ROUND_HALF_UP));
                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));

                    //=======================
                    BigDecimal baclPrice = og.getGoodsBackPrice();
                    //====订单单个商品的的退单金额，小于0时的处理
                    if(baclPrice.compareTo(BigDecimal.ZERO)<0){
                        og.setGoodsBackPrice(new BigDecimal(0.01));
                    }
                }
            }
            //计算误差
            setErrorPrice(oglist,discountbackprice,discountprice,"2",null,null,null);
        }


        //计算优惠券后的优惠价格
        //查询优惠券信息
        if(null!=order.getCouponNo() && null!=order.getCouponPrice() && order.getCouponPrice().compareTo(BigDecimal.ZERO)==1){
            Coupon coupon = couponService.selectCouponByCodeNo(order.getCouponNo());
            //使用优惠券的商品总价
            BigDecimal allcoupon = BigDecimal.ZERO;
            //参与优惠券的商品集合
            List<Long> goodsinfolist = new ArrayList<Long>();
            if(null!=coupon && CollectionUtils.isNotEmpty(coupon.getCouponrangList())){
                for(CouponRange cr:coupon.getCouponrangList()){
                    for(OrderGoods og:oglist){
                        //非抢购和组合商品
                        if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))  && null==og.getIsGroup()){
                            //分类优惠券
                            if(null!=og.getCatId() && "0".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getCatId())){
                                allcoupon=allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                            //品牌优惠券
                            if(null!=og.getBrandId() && "1".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getBrandId())){
                                allcoupon=allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                            //sku优惠券
                            if(null!=og.getGoodsInfoId() && "2".equals(cr.getCouponRangeType()) && cr.getCouponRangeFkId().equals(og.getGoodsInfoId())){
                                allcoupon=allcoupon.add(og.getGoodsBackPrice());
                                goodsinfolist.add(og.getGoodsInfoId());
                            }
                        }
                    }

                }
            }
            BigDecimal errPirce = BigDecimal.ZERO;
            BigDecimal ordercouPrice = order.getCouponPrice();

            for(OrderGoods og:oglist){
                //非抢购和组合商品
                if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11")) && null==og.getIsGroup()){
                    BigDecimal couponprice = og.getGoodsCouponPrice();
                    BigDecimal backprice = og.getGoodsBackPrice();
                    if(CollectionUtils.isNotEmpty(goodsinfolist)){
                        if(goodsinfolist.contains(og.getGoodsInfoId())){
                            og.setGoodsCouponPrice(couponprice.add(order.getCouponPrice().multiply(backprice).divide(allcoupon, 4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP)));
                            errPirce = errPirce.add(order.getCouponPrice().multiply(backprice).divide(allcoupon, 4, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                    og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));
                    //=======================
                    BigDecimal baclPrice = og.getGoodsBackPrice();
                    //====订单单个商品的的退单金额，小于0时的处理
                    if(baclPrice.compareTo(BigDecimal.ZERO)<0){
                        og.setGoodsBackPrice(new BigDecimal(0.01));
                    }
                }
            }
            //计算误差
            setErrorPrice(oglist,errPirce,ordercouPrice,"3",null,null,goodsinfolist);
        }
        //此处 设置参加优惠券后的  优惠价格
        allprice = BigDecimal.ZERO;
        BigDecimal expressPrice = BigDecimal.ZERO;
        //计算当前总价
        for(OrderGoods og:oglist){
            BigDecimal backprice = og.getGoodsBackPrice();
            //非抢购
            if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))){
                allprice=allprice.add(backprice);
            }
        }
        if(null != order.getExpressPrice()){
            expressPrice = order.getExpressPrice();
        }
        allprice = allprice.add(expressPrice);
        //计算积分的backprice
        if(null!=order.getJfPrice() && order.getJfPrice().compareTo(BigDecimal.ZERO)==1 && order.getBusinessId().intValue() == 0){
            BigDecimal errPirce = BigDecimal.ZERO;
            BigDecimal jifenPrice  = order.getJfPrice();
            BigDecimal expPrice = BigDecimal.ZERO;
            for(OrderGoods og:oglist){
                //非抢购
                if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))){
                    BigDecimal backprice = og.getGoodsBackPrice();
                    BigDecimal couponprice = og.getGoodsCouponPrice();
                    if(allprice.compareTo(BigDecimal.ZERO)==1){
                        og.setGoodsCouponPrice(couponprice.add(order.getJfPrice().multiply(backprice).divide(allprice, 4, BigDecimal.ROUND_HALF_UP)));
                        errPirce = errPirce.add(order.getJfPrice().multiply(backprice).divide(allprice, 4, BigDecimal.ROUND_HALF_UP));
                        og.setGoodsBackPrice(og.getGoodsInfoSumPrice().subtract(og.getGoodsCouponPrice()));

                        //=======================
                        BigDecimal baclPrice = og.getGoodsBackPrice();
                        //====订单单个商品的的退单金额，小于0时的处理
                        if(baclPrice.compareTo(BigDecimal.ZERO)<0){
                            og.setGoodsBackPrice(new BigDecimal(0.01));
                        }
                    }
                }
            }
            if(null != order.getExpressPrice()){
                expPrice = order.getExpressPrice();
            }
            errPirce = errPirce.add(order.getJfPrice().multiply(expPrice).divide(allprice, 4, BigDecimal.ROUND_HALF_UP));
            //计算误差
            setErrorPrice(oglist,errPirce,jifenPrice,"4",null,null,null);
        }

    }

    /**
     * 计算误差
     * @param oglist 订单商品集合
     * @param errPrice 有误差的某一促销优惠总价
     * @param correctPrice 某一促销订单实际优惠总价
     * @param type 误差类型  1:满减   2：会员折扣  3：优惠券  4：积分  5：最终
     * @param fullcorplist  满减促销的map
     * @param fullerrplist  满减促销的map
     * @param goodsinfolist  优惠券商品集合
     */
    private void setErrorPrice(List<OrderGoods> oglist,BigDecimal errPrice,BigDecimal correctPrice,String type,Map<Long,BigDecimal> fullcorplist,Map<Long,BigDecimal> fullerrplist,List<Long> goodsinfolist){

        for(OrderGoods og:oglist){
            //非抢购
            if(!(null!=og.getMarketing() && og.getMarketing().getCodexType().equals("11"))) {
                Boolean isOK = false;
                if ("1".equals(type) && null==og.getIsGroup()) {
                    Iterator it = fullerrplist.keySet().iterator();
                    if(it.hasNext()){
                        Long marketing  = (Long)it.next();
                        if(og.getMarketing().getMarketingId().equals(marketing)){
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
                } else if ("3".equals(type)  && null==og.getIsGroup()) {
                    if (CollectionUtils.isNotEmpty(goodsinfolist)) {
                        if (goodsinfolist.contains(og.getGoodsInfoId())) {
                            isOK = true;
                        }
                    }
                }else {
                    isOK = true;
                }
                if(isOK){
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
     * @param customerId
     *            会员ID
     * @param orderCode
     *            订单单号
     * @param duiHuanJiFen
     *            兑换的积分
     * @param disparityPrice
     *            兑换的几个
     * @param consumption
     *            积分兑换规则
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
            LOGGER.error("新增积分兑换记录错误:" + e);
        }
        return result;
    }

    /**
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
        }
        return result;
    }

    /**
     * 查询购物车购买的商品信息
     *
     * @param request
     * @param shoppingCartId
     * @return
     */
    private List<ShoppingCart> extracted(HttpServletRequest request, Long[] shoppingCartId) {
        return shoppingCartService.searchByProduct(request, shoppingCartId);
    }

    /**
     * 判断是否是临时用户
     *
     * @param request
     * @param ca
     * @throws IOException
     */
    public void isTempCust(HttpServletRequest request, CustomerAddress ca) throws IOException {
        Object isTempCust = request.getSession().getAttribute(IS_TEMP_CUST);
        // 判断是否是临时用户
        if (isTempCust != null && "0".equals(isTempCust)) {
            if (loginService.checkCustomerExists(request, ca.getAddressMoblie(), PASSWORD) == 2) {
                CustomerAllInfo user = new CustomerAllInfo();
                user.setCustomerUsername(ca.getAddressMoblie());
                user.setCustomerNickname(ca.getAddressMoblie());
                user.setCustomerId((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
                user.setInfoMobile(ca.getAddressMoblie());
                user.setIsTempCust("0");
                // 修改会员的手机号为当前手机
                customerServiceInterface.updateByPrimaryKey(user);
                // 绑定手机
                customerServiceInterface.updateCustInfoByPrimaryKey(user);
            } else if (loginService.checkCustomerExists(request, ca.getAddressMoblie(), PASSWORD) == 1) {
                loginService.checkCustomerExists(request, ca.getAddressMoblie(), PASSWORD);
            }
        }
    }

    /**
     * 确认支付
     *
     * @param orderId
     * @return
     */
    @Override
    public int payOrder(Long orderId) {
        return orderService.payOrder(orderId);
    }

    /**
     * 查询订单包裹表
     *
     * @param orderId
     *            订单id
     * @return
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
        // 订单单个包裹
        OrderContainerUtil order = null;
        OrderExpress orderExpress = orderExpressMapper.selectOrderExpress(orderId);
        // 订单所属的配送信息
        for (int i = 0; i < expressList.size(); i++) {
            String kuaidi = KuaiDiUtil.execLookKuaiDi(kuaiDiName, expressList.get(i).getExpressNo());
            order = new OrderContainerUtil();
            order.setContainerRelations(containerRelations.get(i));
            order.setExpressName(orderExpress.getExpressName());
            order.setExpress(kuaidi);
            containerUtils.add(order);
        }

        try {
            return containerUtils;
        } finally {
            order = null;
            containerUtils = null;
            expressList = null;
            kuaiDiName = null;
            express = null;
        }
    }

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public Order getPayOrder(Long orderId) {
        return orderService.getPayOrder(orderId);
    }

    /**
     * 查询订单根据COde
     *
     * @param orderCode
     * @return
     */
    @Override
    public Order getPayOrderByCode(String orderCode) {
        return orderService.getPayOrderByCode(orderCode);
    }

    /**
     * 根据订单编号订单下所有商品并随机返回一个
     *
     * @param orderId
     * @param status
     *            当前已经选中的索引位置
     * @return
     */
    @Override
    public Map<String, Object> queryGoodsProduceByOrderId(Long orderId, Long status) {
        Map<String, Object> map = new HashMap<String, Object>();
        int rodomId = 0;
        List<OrderGoods> orders = orderService.queryOrderGoods(orderId);
        if (status == null) {
            rodomId = 0;
        } else {
            if (status == (orders.size() - 1)) {
                rodomId = 0;
            } else if (orders.size() > 1) {
                rodomId++;
            }
        }
        map.put("list", siteGoodsProductService.queryTopSalesByProductId(orders.get(rodomId).getGoodsInfoId(), 6));
        map.put("status", rodomId);
        return map;
    }

    /**
     * 根据总单编号查询订单信息
     *
     *
     * @param orderOldCode
     * @return
     */
    @Override
    public List<Order> getPayOrderByOldCode(String orderOldCode) {
        return orderService.getPayOrderByOldCode(orderOldCode);
    }

    public CouponNoService getCouponNoService() {
        return couponNoService;
    }

    @Resource(name = "CouponNoService")
    public void setCouponNoService(CouponNoService couponNoService) {
        this.couponNoService = couponNoService;
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

    public OrderGoodsInfoCouponMapper getOrderGoodsInfoCouponMapper() {
        return orderGoodsInfoCouponMapper;
    }

    @Resource(name = "OrderGoodsInfoCouponMapper")
    public void setOrderGoodsInfoCouponMapper(OrderGoodsInfoCouponMapper orderGoodsInfoCouponMapper) {
        this.orderGoodsInfoCouponMapper = orderGoodsInfoCouponMapper;
    }

    public OrderGoodsMapper getOrderGoodsMapper() {
        return orderGoodsMapper;
    }

    @Resource(name = "OrderGoodsMapper")
    public void setOrderGoodsMapper(OrderGoodsMapper orderGoodsMapper) {
        this.orderGoodsMapper = orderGoodsMapper;
    }

    public OrderExpressMapper getOrderExpressMapper() {
        return orderExpressMapper;
    }

    @Resource(name = "OrderExpressMapper")
    public void setOrderExpressMapper(OrderExpressMapper orderExpressMapper) {
        this.orderExpressMapper = orderExpressMapper;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    @Resource(name = "OrderService")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public CustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    @Resource(name = "customerServiceSite")
    public void setCustomerServiceInterface(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }

    public ProductWareMapper getProductWareMapper() {
        return productWareMapper;
    }

    @Resource(name = "ProductWareMapper")
    public void setProductWareMapper(ProductWareMapper productWareMapper) {
        this.productWareMapper = productWareMapper;
    }

    public ExpressConfBizImpl getExpressConfBizImpl() {
        return expressConfBizImpl;
    }

    @Resource(name = "expressConfBizImpl")
    public void setExpressConfBizImpl(ExpressConfBizImpl expressConfBizImpl) {
        this.expressConfBizImpl = expressConfBizImpl;
    }

    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    @Resource(name = "ShoppingCartService")
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public OrderContainerRelationMapper getRelationMapper() {
        return relationMapper;
    }

    @Resource(name = "OrderContainerRelationMapper")
    public void setRelationMapper(OrderContainerRelationMapper relationMapper) {
        this.relationMapper = relationMapper;
    }

    public IExpressConfBiz getiExpressConfBiz() {
        return iExpressConfBiz;
    }

    @Resource(name = "expressConfBizImpl")
    public void setiExpressConfBiz(IExpressConfBiz iExpressConfBiz) {
        this.iExpressConfBiz = iExpressConfBiz;
    }

    public com.ningpai.site.goods.service.GoodsProductService getSiteGoodsProductService() {
        return siteGoodsProductService;
    }

    @Resource(name = "HsiteGoodsProductService")
    public void setSiteGoodsProductService(com.ningpai.site.goods.service.GoodsProductService siteGoodsProductService) {
        this.siteGoodsProductService = siteGoodsProductService;
    }

    public ProductWareService getProductWareService() {
        return productWareService;
    }

    @Resource(name = "ProductWareService")
    public void setProductWareService(ProductWareService productWareService) {
        this.productWareService = productWareService;
    }

    public com.ningpai.goods.service.GoodsProductService getGoodsProductService() {
        return goodsProductService;
    }

    @Resource(name = "GoodsProductService")
    public void setGoodsProductService(com.ningpai.goods.service.GoodsProductService goodsProductService) {
        this.goodsProductService = goodsProductService;
    }

    /**
     * 得到详细的购物车集合
     *
     * @param cartId
     * @return
     */
    public List<ShoppingCart> getDetailShoppingcart(Long[] cartId) {
        List<ShoppingCart> shoplist = shoppingCartMapper.shopCartListByIds(Arrays.asList(cartId));
        for (int i = 0; i < shoplist.size(); i++) {
            if (shoplist.get(i).getFitId() == null) {

                // 查询商品详细
                shoplist.get(i).setGoodsDetailBean(
                        siteGoodsProductService.queryDetailBeanByProductId(((ShoppingCart) shoplist.get(i)).getGoodsInfoId(), ((ShoppingCart) shoplist.get(i)).getDistinctId()));
                // 查询购物车里选择的促销信息
                shoplist.get(i).setMarketing(marketService.marketingDetail(shoplist.get(i).getMarketingActivityId(), shoplist.get(i).getGoodsInfoId()));
            } else {

                // 如果商品是套装
                GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(((ShoppingCart) shoplist.get(i)).getFitId());
                if (null != goodsGroupVo) {
                    shoplist.get(i).setGoodsGroupVo(goodsGroupVo);
                    shoplist.get(i).setThirdId(goodsGroupVo.getThirdId());
                }
            }
        }
        return shoplist;
    }

    /**
     * 查询收货地区仓库是否有库存
     *
     * @param request
     * @param shoppingCartId
     * @param distinctId
     * @return
     */
    @Override
    public Map<String, Object> checkProduct(HttpServletRequest request, Long[] shoppingCartId, Long distinctId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 库存
        List<Long> warestockList = new ArrayList<Long>();
        // productId
        List<Long> productIdList = new ArrayList<Long>();
        // 获取所有所有选中商品信息
        List<ShoppingCart> cartlist1 = getDetailShoppingcart(shoppingCartId);

        // 货品仓库信息
        ProductWare product = new ProductWare();
        if (cartlist1 != null && !cartlist1.isEmpty()) {
            for (ShoppingCart sc : cartlist1) {
                // 判断商品是否是套装
                if (sc.getFitId() == null) {
                    if (0 == sc.getThirdId()) {
                        product = productWareService.queryProductWareByProductIdAndDistinctId(sc.getGoodsInfoId(), distinctId);
                        productIdList.add(sc.getGoodsInfoId());
                        if (product != null) {
                            warestockList.add(product.getWareStock());
                        } else {
                            warestockList.add(0L);
                        }
                    } else {
                        productIdList.add(sc.getGoodsInfoId());
                        // 第三方货品
                        GoodsProductVo goodsvo = goodsProductMapper.queryDetailByProductId(sc.getGoodsInfoId());
                        if (null != goodsvo) {
                            warestockList.add(goodsvo.getGoodsInfoStock());
                        } else {
                            warestockList.add(0L);
                        }
                    }
                } else {

                    // 套装商品
                    GoodsGroupVo goodsGroupVo = goodsGroupService.queryVoByPrimaryKey(sc.getFitId());
                    // 遍历套装下的商品
                    for (int j = 0; j < goodsGroupVo.getProductList().size(); j++) {
                        product = productWareService.queryProductWareByProductIdAndDistinctId(goodsGroupVo.getProductList().get(j).getProductId(), distinctId);
                        productIdList.add(goodsGroupVo.getProductList().get(j).getProductId());
                        if (product != null) {
                            warestockList.add(product.getWareStock());
                        } else {
                            warestockList.add(0L);
                        }
                    }

                }

            }
            map.put("warestockList", warestockList);
            map.put("productIdList", productIdList);
        }

        return map;
    }

    /**
     * 定时任务
     */
    @Override
    @Scheduled(cron = "59 59 23 ? * *")
    public void receiptConfirmation() {
        try {
            // 得到所有已发货的订单信息
            orderService.receiptConfirmation();
        } catch (Exception e) {
            LOGGER.error("定时任务自动收货失败", e);
        }
    }

    /**
     * 支付成功发送短信通知直营店
     *
     * @param order
     * @return
     */
    @Override
    public boolean paySuccessSendSms(Order order) {
        String status = basicService.getStoreStatus();
        // 判断是否开启
        if (("0").equals(status)) {
            DirectShop directShop = null;
            List<DirectShop> directShops = null;
            //判断是否是直营店订单
            if (order.getDirectType() != null && order.getDirectType().equals("1")) {
                directShop = directShopService.selectInfoById(order.getBusinessId());
                //发送短信
                Sms sms = mapper.selectSms();
                //手机号
                sms.setSendSim(directShop.getDirectShopTel());
                //短信内容
                sms.setMsgContext("您有一笔新订单，单号为："+order.getOrderCode());
                try {
                    // 短信发送
                    SmsPost.sendPostOrder(sms);
                } catch (IOException e) {
                    LOGGER.error("",e);
                }
            }
        }
        return true;
    }

    /**
     * 根据 会员id,订单id 查询订单信息
     *
     * @param customerId 会员id
     * @param orderId    订单id
     * @return 订单信息
     */
    @Override
    public Order queryOrderByCustomerIdAndOrderId(Long customerId, Long orderId) {
        return orderService.queryOrderByCustomerIdAndOrderId(customerId, orderId);
    }

    /**
     * 根据订单id, 订单商品id 查询订单商品信息
     *
     * @param orderId      订单id
     * @param orderGoodsId 订单商品id
     * @return
     */
    @Override
    public OrderGoods queryOrderGoodsByOrderIdAndOrderGoodsId(Long orderId, Long orderGoodsId) {
        return orderService.queryOrderGoodsByOrderIdAndOrderGoodsId(orderId, orderGoodsId);
    }

    /**
     * 根据会员id和订单id查询订单信息
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return Order 订单详细
     */
    @Override
    public Order queryOrderDetailByIds(Long orderId, Long customerId) {
        return orderService.queryOrderDetailByMap(orderId, customerId);
    }
}
