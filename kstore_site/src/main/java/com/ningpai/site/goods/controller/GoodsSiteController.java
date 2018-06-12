/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.site.goods.controller;

import com.ningpai.comment.bean.Comment;
import com.ningpai.comment.bean.CommentReplay;
import com.ningpai.comment.service.CommentReplayServiceMapper;
import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.NpCookieUtil;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.goods.bean.ArrivalNotice;
import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.bean.GoodsCate;
import com.ningpai.goods.dao.ArrivalNoticeMapper;
import com.ningpai.goods.pub.GoodsPub;
import com.ningpai.goods.service.GetOnOffService;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.service.GoodsOpenSpecService;
import com.ningpai.goods.service.WareHouseService;
import com.ningpai.goods.util.GoodsIndexConstant;
import com.ningpai.goods.util.SearchPageBean;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.other.util.LoginUtil;
import com.ningpai.site.customer.service.BrowserecordService;
import com.ningpai.site.customer.service.CustomerFollowService;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.goods.bean.GoodsDetailBean;
import com.ningpai.site.goods.service.*;
import com.ningpai.site.goods.util.GoodsSiteSearchBean;
import com.ningpai.site.goods.util.ValueUtil;
import com.ningpai.site.goods.vo.*;
import com.ningpai.site.shoppingcart.service.ShoppingCartService;
import com.ningpai.site.util.SearchCookieController;
import com.ningpai.system.bean.BasicSet;
import com.ningpai.system.bean.ShopKuaiShang;
import com.ningpai.system.service.*;
import com.ningpai.system.util.AddressUtil;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.StringCommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 商品控制器
 *
 * @author NINGPAI-YuanKangKang
 * @versions
 * @since 2014年4月8日 下午2:15:52
 */
@Controller
public final class GoodsSiteController {

    private static final String DISTINCTID = "distinctId";
    private static final String NOWCATE = "nowcate";
    private static final String HOTPRODUCT = "hotProduct";
    private static final String HOTSALES = "hotSales";
    private static final String PRICEHOTSALES = "pricehotSales";
    private static final String BRANDHOTSALES = "brandhotSales";
    private static final String FINALBUY = "finalBuy";
    private static final String PROVINCE = "province";
    private static final String CHPROVINCE = "chProvince";
    private static final String SEARCHBEAN = "searchBean";
    private static final String PARAMS = "params";
    private static final String CHADDRESS = "chAddress";
    private static final String CHCITY = "chCity";
    private static final String CHDISTINCT = "chDistinct";
    private static final String DETAILBEAN = "detailBean";
    private static final String LOGGERINFO = "查询货品【";
    private static final String CUSTOMERID = "customerId";
    private static final String GOODSNOEXIT = "/goods/no_exit";
    private static final String GOODSINFOID = "goodsInfoId";
    private static final String INFOMOBILE = "infoMobile";
    private static final String INFOEMAIL = "infoEmail";
    private static final String NOTICESTURTS = "noticeSturts";
    private static final String WAREID = "wareId";

    private GoodsCateService goodsCateService;
    private GoodsTypeService goodsTypeService;
    private GoodsService goodsService;
    private GoodsProductService productService;
    private MarketingService marketingService;
    private SiteGoodsAtteService goodsAtteService;
    private BrowserecordService browserecordService;
    private CommentServiceMapper commentServiceMapper;
    private BrowerService browerService;
    private CommentReplayServiceMapper commentReplayServiceMapper;
    private GoodsOpenSpecService goodsOpenSpecService;

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(GoodsSiteController.class);

    @Resource(name = "ShoppingCartService")
    private ShoppingCartService cartService;

    @Resource(name = "DefaultAddressService")
    private DefaultAddressService addressService;

    @Resource(name = "DistrictService")
    private DistrictService districtService;

    @Resource(name = "WareHouseService")
    private WareHouseService wareHouseService;

    /**
     * es搜索处理service
     **/
    @Resource(name = "GoodsElasticSearchService")
    private GoodsElasticSearchService goodsElasticSearchServivice;

    @Resource(name = "TopAndBottomService")
    private TopAndBottomService topAndBottomService;

    @Resource(name = "customerFollowServiceSite")
    private CustomerFollowService customerFollowService;

    @Resource(name = "goodsPub")
    private GoodsPub goodsPub;

    @Resource(name = "ArrivalNoticeMapper")
    private ArrivalNoticeMapper arrivalNoticeMapper;

    /**
     * 在线客服service
     */
    @Resource(name = "onLineServiceItemBizImpl")
    private IOnLineServiceItemBiz onLineServiceItemService;

    @Resource(name = "onLineServiceBizImpl")
    private IOnLineServiceBiz onLineServiceBiz;

    /**
     * spring 注解 会员service
     */
    private CustomerServiceInterface customerServiceInterface;

    /**
     * 第三方商品审核开关
     */
    @Resource(name = "GetOnOffService")
    private GetOnOffService getOnOffService;

    /**
     * 商品分类查询
     */
    @Resource(name = "HsiteGoodsCateService")
    private GoodsCateService cateService;

    /**
     * 站点设置服务层接口
     */
    @Resource(name = "basicSetService")
    private BasicSetService basicSetService;

    /**
     * 快商通
     */
    @Resource(name = "ShopKuaiShangService")
    private ShopKuaiShangService shopKuaiShangService;

    /**
     * 从请求中取出登陆的会员iD
     *
     * @param request 请求对象
     * @return 拿出的会员Id
     */
    public Long takeCustIdFromRequest(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute(CUSTOMERID);
    }

    /**
     * 根据分类查询商品列表
     *
     * @param cid 分类ID
     */
    public ModelAndView goodsList(Long cid, Long pid, PageBean pb, GoodsSiteSearchBean searchBean, String[] params, HttpServletRequest request) {
        Long cidNew = cid;
        pb.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询是否是1级分类
        int grade = goodsCateService.findCatGrade(cidNew);

        GoodsTypeVo typeVo = null;
        List<GoodsListScreenVo> screenVo = null;
        Long distinctId = null;
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        }
        // 查询父亲分类
        GoodsCateVo cateVo = goodsCateService.queryCateByCatId(cidNew);
        GoodsCateVo gcv = null;
        // 如果父亲分类层级为空这查询子分类
        if (cateVo.getCatGrade() == null) {
            gcv = goodsCateService.queryCateById(cidNew);
        } else {
            gcv = goodsCateService.queryCateById(pid);
        }
        // 如果该分类有子集合
        if (gcv != null && gcv.getCateVos() != null && !gcv.getCateVos().isEmpty() && gcv.getCateVos().get(0).getCateVos() != null
                && gcv.getCateVos().get(0).getCateVos().get(0) != null) {
            for (int j = 0; j < gcv.getCateVos().size(); j++) {
                for (int i = 0; i < gcv.getCateVos().get(j).getCateVos().size(); i++) {
                    String ppId = null;
                    if (gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId() != null) {
                        ppId = gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId().toString();
                    }

                    if (cidNew.toString().equals(ppId)) {
                        cidNew = gcv.getCateVos().get(j).getCateVos().get(i).getCatId();
                    }
                }
            }
        }

        if (cateVo.getCatGrade() == 1L || cateVo.getCatGrade() == 3L) {
            map.put(NOWCATE, goodsCateService.queryCateByCatId(cidNew));
        } else {
            cidNew = cateVo.getCatId();
            map.put(NOWCATE, goodsCateService.queryCateByCatId(cidNew));
        }
        if (grade == 1) {
            GoodsCate c = goodsCateService.findCid(pid);
            cidNew = c.getCatId();
        }
        typeVo = this.goodsTypeService.queryGoodsTypeByCatId(cidNew);
        screenVo = this.goodsService.calcScreenParam(params, typeVo);
        map.put("cate", gcv);
        map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
        map.put(HOTPRODUCT, productService.queryTopSalesByCatIds(cidNew, 4));
        map.put(HOTSALES, productService.queryHotSalesTopSix(cidNew, 6));
        map.put(FINALBUY, productService.browCatFinalBuyAndPrecent(cidNew, 4L));
        // 查询最近的浏览记录,需要传递当前登陆的会员ID
        map.put(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));

        if (goodsService == null) {
            goodsService = this.getGoodsService();
        }

        if (grade != 1) {
            if (null == distinctId) {
                Long dId = addressService.getDefaultIdService();
                if (dId == null) {
                    distinctId = addressService.getDefaultIdService();
                    if (distinctId == null) {
                        distinctId = 1103L;
                    }
                } else {
                    map.put("pb", this.goodsService.searchGoods(pb, searchBean, cidNew, params, dId.toString()));
                    map.put(DISTINCTID, dId);
                    map.put(PROVINCE, "江苏");
                }
            } else {
                map.put("pb", this.goodsService.searchGoods(pb, searchBean, cidNew, params, distinctId.toString()));
                map.put(DISTINCTID, distinctId);
                map.put(PROVINCE, request.getSession().getAttribute(CHPROVINCE).toString());
            }
        } else {
            if (null == distinctId) {
                Long dId = addressService.getDefaultIdService();
                if (dId == null) {
                    distinctId = addressService.getDefaultIdService();
                    if (distinctId == null) {
                        distinctId = 1103L;
                    }
                } else {
                    map.put("pb", this.goodsService.searchGood(pb, searchBean, pid, params, dId.toString()));
                    map.put(DISTINCTID, dId);
                    map.put(PROVINCE, "江苏");
                }
            } else {
                map.put("pb", this.goodsService.searchGood(pb, searchBean, pid, params, distinctId.toString()));
                map.put(DISTINCTID, distinctId);
                map.put(PROVINCE, request.getSession().getAttribute(CHPROVINCE).toString());
            }
        }
        map.put(SEARCHBEAN, searchBean);
        map.put(PARAMS, screenVo);
        ModelAndView mav = new ModelAndView("goods/list");
        mav.addObject(ValueUtil.MAP, map);

        // 日志记录
        LOGGER.info("根据分类【" + gcv.getCatName() + "】查询商品列表");

        return topAndBottomService.getTopAndBottom(mav);

    }

    /**
     * 商品列表页
     *
     * @param cid       分类ID
     * @param pid       父分类ID
     * @param request
     * @param response
     * @param pageBean  分页工具类
     * @param brands    选择的品牌
     * @param params    选择的扩展参数
     * @param sort      排序
     * @param showStock 是否显示有货
     * @return {@link ModelAndView}
     */
    @RequestMapping("/list")
    public ModelAndView esGoodsList(Long cid, Long pid, HttpServletRequest request, HttpServletResponse response, SearchPageBean<EsGoodsInfo> pageBean, String[] brands,
                                    String[] params, String sort, String showStock, String isThird, String inMarketing) {
        Long cid1 = cid;
        // 每页条数20条
        pageBean.setPageSize(16);
        // 索引
        String[] indices = new String[]{GoodsIndexConstant.PRODUCT_INDEX_NAME()};
        // 类型
        String[] types = new String[]{GoodsIndexConstant.PRODUCT_TYPE};
        // 地区
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        // 根据地区ID查询关联仓库ID
        Long wareId = productService.selectWareIdByDistinctId(distinctId);
        Long[] wares = wareId == null ? null : new Long[]{wareId};
        BasicSet basicSet = basicSetService.findBasicSet();
        showStock =  basicSet.getShowStock();
        // 商品搜索并获取结果, 第三方ID为空
        Map<String, Object> resultMap = goodsElasticSearchServivice.searchGoods(pageBean, wares, indices, types, null, brands, new String[]{String.valueOf(cid)}, params, sort,
                null, null, null, null, showStock, null, isThird, inMarketing);
        // 页面渲染数据
        ModelAndView model = new ModelAndView("goods/es_list");
        // 展示分类
        // 查询父亲分类
        // 查询是否是1级分类
        int grade = goodsCateService.findCatGrade(cid);
        GoodsCateVo cateVo = goodsCateService.queryCateByCatId(cid);
        GoodsCateVo gcv = null;
        // 如果父亲分类层级为空这查询子分类
        if (cateVo.getCatGrade() == null) {
            gcv = goodsCateService.queryCateById(cid);
        } else {
            gcv = goodsCateService.queryCateById(pid);
        }
        // 如果该分类有子集合
        if (gcv != null && gcv.getCateVos() != null && !gcv.getCateVos().isEmpty() && gcv.getCateVos().get(0).getCateVos() != null
                && !gcv.getCateVos().get(0).getCateVos().isEmpty() && gcv.getCateVos().get(0).getCateVos().get(0) != null) {
            for (int j = 0; j < gcv.getCateVos().size(); j++) {
                for (int i = 0; i < gcv.getCateVos().get(j).getCateVos().size(); i++) {
                    String ppId = null;
                    if (gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId() != null) {
                        ppId = gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId().toString();
                    }

                    if (cid.toString().equals(ppId)) {
                        cid1 = gcv.getCateVos().get(j).getCateVos().get(i).getCatId();
                    }
                }
            }
        }
        if (cateVo.getCatGrade() == 1L || cateVo.getCatGrade() == 3L) {
            model.addObject(NOWCATE, goodsCateService.queryCateByCatId(cid1));
        } else {
            cid1 = cateVo.getCatId();
            model.addObject(NOWCATE, goodsCateService.queryCateByCatId(cid1));
        }
        if (grade == 1) {
            GoodsCate c = goodsCateService.findCid(pid);
            if (c != null) {
                cid1 = c.getCatId();
            }
        }

        // 当前登录成功的会员ID
        Long custId = this.takeCustIdFromRequest(request);
        if (custId != null) {
            Map<String, Object> followMap = new HashMap<>();
            followMap.put(CUSTOMERID, custId);
            // 得到当前会员收藏货品的id集合
            List<String> goodsIds = customerFollowService.selectCustomerFollowForList(followMap);
            model.addObject("goodsIds", goodsIds);
        }

        model.addObject("cate", gcv);
        model.addObject("map", resultMap);
        model.addObject(HOTPRODUCT, productService.queryTopSalesByCatIds(cid, 4));
        //model.addObject(HOTSALES, productService.queryHotSalesTopSix(cid, 6));
        model.addObject(HOTSALES, productService.queryHotSalesTopSixRandom(gcv,cateVo,6));
       // model.addObject(FINALBUY, productService.browCatFinalBuyAndPrecent(cid, 4L));
        model.addObject(FINALBUY, productService.browCatFinalBuyAndPrecentRandom(gcv, cateVo, 4));
        // 已选的扩展属性
        model.addObject(PARAMS, params);
        // 已选品牌
        model.addObject("brands", brands);
        // 当前地区ID
        model.addObject(DISTINCTID, distinctId);
        // 排序
        model.addObject("sort", sort);
        // 是否显示有货
        model.addObject("showStock", showStock);
        // 显示商城自营商品
        model.addObject("isThird", isThird);
        // 查询的仓库
        model.addObject("wareId", wareId);
        // 在营销中的商品
        model.addObject("inMarketing", inMarketing);
        // 查询最近的浏览记录,需要传递当前登陆的会员ID
        model.addObject(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));
        // 非空验证 分类名称

        LOGGER.info("根据分类【" + gcv.getCatName() + "】查询商品列表");

        return topAndBottomService.getTopAndBottom(model);
    }

    /**
     * 商品详情页
     *
     * @param productId 货品ID
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/product")
    public ModelAndView goodsDetail(Long productId, Long distinctId, String chAddress, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {

        // 如果没有传产品id 则直接返回
        if (null == productId) {
            return topAndBottomService.getBottom(new ModelAndView(GOODSNOEXIT));
        }

        Long distinctId1 = distinctId;
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询货品信息
        GoodsProductVo good = productService.querySimpleProductByProductId(productId);
        // 货品被删除
        if (good == null) {
            return topAndBottomService.getBottom(new ModelAndView(GOODSNOEXIT));
        }
        // 如果商品是下架状态或者是被删除状态就跳往首页
        if (productService.queryProductByGoodsInfoId(productId) == 0) {
            return topAndBottomService.getBottom(new ModelAndView(GOODSNOEXIT));
        }

        if (null == distinctId1) {
            if (null != request.getSession().getAttribute(ValueUtil.ADDRESS) && null == request.getSession().getAttribute(CHADDRESS)) {
                distinctId1 = ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictId();
                map.put(CHADDRESS,
                        ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getProvince().getProvinceName()
                                + ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getCity().getCityName()
                                + ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictName());
                map.put(CHPROVINCE, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getProvince().getProvinceName());
                map.put(CHCITY, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getCity().getCityName());
                map.put(CHDISTINCT, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictName());
            } else {
                if (null == request.getSession().getAttribute(CHADDRESS)) {
                    Long dId = addressService.getDefaultIdService();
                    if (dId == null) {
                        dId = 1103L;
                        // 默认设置为南京建邺区仓库
                        distinctId1 = Long.valueOf(dId);
                        map.put(CHADDRESS, "江苏南京建邺区");
                        map.put(CHPROVINCE, "江苏");
                        map.put(CHCITY, "南京市");
                        map.put(CHDISTINCT, "建邺区");
                    } else {
                        distinctId1 = Long.valueOf(dId);
                        AddressUtil addressUtil = districtService.queryAddressNameByDistrictId(dId);
                        map.put(CHADDRESS, addressUtil.getProvinceName() + addressUtil.getCityName() + addressUtil.getDistrictName());
                        map.put(CHPROVINCE, addressUtil.getProvinceName());
                        map.put(CHCITY, addressUtil.getCityName());
                        map.put(CHDISTINCT, addressUtil.getDistrictName());
                    }

                } else {
                    /* 取session的数据 */
                    map.put(CHPROVINCE, request.getSession().getAttribute(CHPROVINCE));
                    map.put(CHADDRESS, request.getSession().getAttribute(CHADDRESS));
                    map.put(CHCITY, request.getSession().getAttribute(CHCITY));
                    map.put(CHDISTINCT, request.getSession().getAttribute(CHDISTINCT));
                    distinctId1 = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
                }
            }
        } else {
            /* 如果页面传过来的地区值不为null */
            map.put(CHADDRESS, chAddress);
            map.put(CHPROVINCE, request.getParameter(CHPROVINCE));
            map.put(CHCITY, request.getParameter(CHCITY));
            map.put(CHDISTINCT, request.getParameter(CHDISTINCT));
        }
        map.put(DISTINCTID, distinctId1);
        // 当前登录成功的会员ID
        Long custId = this.takeCustIdFromRequest(request);
        if (custId != null) {
            if (goodsAtteService.checkAtte(custId, productId)) {
                map.put("isFollow", "1");
            } else {
                map.put("isFollow", "0");
            }
        }
        /* 保存到session中 */
        request.getSession().setAttribute(CHPROVINCE, map.get(CHPROVINCE));
        request.getSession().setAttribute(CHADDRESS, map.get(CHADDRESS));
        request.getSession().setAttribute(CHCITY, map.get(CHCITY));
        request.getSession().setAttribute(CHDISTINCT, map.get(CHDISTINCT));
        request.getSession().setAttribute(DISTINCTID, map.get(DISTINCTID));
        response.setHeader("Cache-Control","no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma","no-cache");

        GoodsDetailBean detailBean = this.productService.queryDetailBeanByProductId(productId, distinctId1);
            if (null != detailBean.getProductVo()) {
                detailBean = cartService.forPurchasing(detailBean, (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
                map.put(DETAILBEAN, detailBean);
                // 客服
                map.put("QQs", onLineServiceItemService.selectItemsByType(0));
                // 查询在线客服设置
                map.put("customerService", onLineServiceBiz.selectSetting());
                ShopKuaiShang shopKuaiShang = shopKuaiShangService.selectInitone();
                if(shopKuaiShang !=null && shopKuaiShang.getKstEffectiveTerminal().contains("1")){
                    map.put("shopKuaiShang", shopKuaiShang);
                }else{
                    map.put("shopKuaiShang", new ShopKuaiShang());
                }
                map.put("openSpec", this.goodsOpenSpecService.queryOpenListByGoodsId(detailBean.getProductVo().getGoodsId()));
                /* 热销排行 */
//                map.put(HOTSALES, productService.queryHotSalesTopSix(detailBean.getProductVo().getGoods().getCatId(), 6));
                /* 热销排行-同一价位 */
//                map.put(PRICEHOTSALES,
//                        productService.queryHotSalesByCatIdandPrice(detailBean.getProductVo().getGoods().getCatId(), 6, detailBean.getProductVo().getGoodsInfoPreferPrice()));
                /* 热销排行-同一品牌 */
//                map.put(BRANDHOTSALES,
//                        productService.queryHotSalesByCatIdandBrand(detailBean.getProductVo().getGoods().getCatId(), 6, detailBean.getProductVo().getGoods().getBrandId()));
                /* 最终购买 */
//                map.put(FINALBUY, productService.browCatFinalBuyAndPrecent(detailBean.getProductVo().getGoods().getCatId(), 4L));
                /* 查询商品关联的标签 */
                map.put("tags", this.goodsPub.getGoodsReleTagService().queryreleListByProductId(detailBean.getProductVo().getGoodsInfoId()));

//                Map<String, Object> cateAndBrandmap = goodsCateService.queryGoodsCateAndBrandsByCatId(detailBean.getProductVo().getGoods().getCatId());
//                map.put("goodscates", cateAndBrandmap.get("goodscates"));
//                map.put("goodsBrands", cateAndBrandmap.get("goodsBrands"));
                // 保存浏览记录
                this.browerService.saveBrowerHis(request, response, productId);
                // 查询最近浏览记录,传递当前登陆的用户ID
                ModelAndView mav = new ModelAndView("goods/newdetail", ValueUtil.MAP, map);
                // 判断货品名称是否为空
                if (null != detailBean.getProductVo().getProductName()) {
                    // 日志记录
                    LOGGER.info("进入货品【" + detailBean.getProductVo().getProductName() + "】详情页");
                }
                return topAndBottomService.getTopAndBottom(mav);
            } else {
                return null;
            }
    }

    /**
     * 根据货品的catID 查询相关的分类及品牌信息
     *
     * @param catId 货品的分类ID
     * @return {"goodscates":相关分类信息,"goodsBrands":相关品牌信息}
     */
    @RequestMapping("/getRelativeCatAndBrandByProductCatId")
    @ResponseBody
    public Map<String, Object> getRelativeCatAndBrandByProductCatId(Long catId) {
        if (catId == null)
            return null;
//        Map<String, Object> cateAndBrandmap =
        return goodsCateService.queryGoodsCateAndBrandsByCatId(catId);
//        map.put("goodscates", cateAndBrandmap.get("goodscates"));
//        map.put("goodsBrands", cateAndBrandmap.get("goodsBrands"));
    }

    /**
     * 根据货品的catid 查询最终购买信息
     *
     * @param catId 分类ID
     * @return {@link ListFinalBuyVo} 最终购买信息
     */
    @RequestMapping("/getFinalBuyVosByProductCatId")
    @ResponseBody
    public List<ListFinalBuyVo> getFinalBuyVosByProductCatId(Long catId) {
        if (catId == null)
            return null;
        return productService.browCatFinalBuyAndPrecent(catId, 4L);
    }

    /**
     * 异步获取热销商品
     *
     * @param catId   分类ID
     * @param price   价格
     * @param brandId 品牌ID
     * @return
     */
    @RequestMapping("/getHotSalesRank")
    @ResponseBody
    public Map<String, Object> getHotSalesRank(Long catId, BigDecimal price, Long brandId) {
        Map<String, Object> map = new HashMap<>();
         /* 热销排行 */
        map.put(HOTSALES, productService.queryHotSalesTopSix(catId, 6));
                /* 热销排行-同一价位 */
        map.put(PRICEHOTSALES,
                productService.queryHotSalesByCatIdandPrice(catId, 6, price));
                /* 热销排行-同一品牌 */
        map.put(BRANDHOTSALES,
                productService.queryHotSalesByCatIdandBrand(catId, 6, brandId));
        return map;
    }


    /**
     * 根据商品ID查询所有的货品
     *
     * @param goodsId 商品ID
     * @return 查询到的货品列表
     */
    @RequestMapping("/queryProductListByGoodsId")
    @ResponseBody
    public List<Object> queryAllProductByGoodsId(Long goodsId) {
        return this.productService.queryAllProductListByGoodsId(goodsId);
    }

    /**
     * 保存货品关注
     *
     * @param productId  会员iD
     * @param productId  商品ID
     * @param districtId 收藏商品对应的区域ID
     */
    @RequestMapping("/saveAtte")
    @ResponseBody
    public int saveAtte(Long productId, Long districtId, HttpServletRequest request, BigDecimal goodsprice) {
        // 当前登录成功的会员ID
        Long custId = this.takeCustIdFromRequest(request);
        // 判断当前是否有会员登陆
        if (null != custId) {
            // 非空验证 货品ID 区域ID 1== 是否收藏过
            if (null != productId && null != districtId) {
                // 根据货品ID获取 单个的货品对象
                GoodsProductVo goodsProductVo = productService.queryProductByProductId(productId);
                // 当前登录成功的会员信息
                Customer customerAllInfo = customerServiceInterface.queryCustomerById(custId);
                AddressUtil addressUtil = districtService.queryAddressNameByDistrictId(districtId);

                // 非空验证 货品名称 区域对象 收藏的价格
                if (null != productId && null != districtId && null != goodsprice && null != addressUtil) {
                    // 操作日志
                    OperaLogUtil.addOperaLog(
                            request,
                            customerAllInfo.getCustomerUsername(),
                            "关注货品",
                            "关注货品-->货品地区：【" + addressUtil.getProvinceName() + "-" + addressUtil.getCityName() + "-" + addressUtil.getDistrictName() + "】货品名称:【"
                                    + goodsProductVo.getProductName() + "】货品价格：【" + goodsprice + "】-->用户名：" + customerAllInfo.getCustomerUsername());
                    // 记录日志
                    LOGGER.info("关注货品：地区【" + addressUtil.getProvinceName() + "-" + addressUtil.getCityName() + "-" + addressUtil.getDistrictName() + "】货品名称:【"
                            + goodsProductVo.getProductName() + "】货品价格：【" + goodsprice + "】");
                }
            }
            return this.goodsAtteService.newsaveGoodsAtte(custId, productId, districtId, goodsprice);
        } else {
            /* 未登录返回 */
            return -2;
        }
    }

    /**
     * 查询商品列表
     *
     * @param searchBean 查询辅助Bean
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/searchProduct")
    public ModelAndView searchProduct(PageBean pb, GoodsSiteSearchBean searchBean, HttpServletRequest request) throws UnsupportedEncodingException {
        pb.setPageSize(20);
        searchBean.setTitle(new String(searchBean.getTitle().getBytes("ISO-8859-1"), ConstantUtil.UTF));
        Map<String, Object> map = new HashMap<String, Object>();
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        map.put(DISTINCTID, distinctId);
        searchBean.setDistinctId(distinctId);
        map.put("pb", this.goodsService.searchGoods(pb, searchBean));
        //

        map.put(SEARCHBEAN, searchBean);
        map.put("titleArray", searchBean.getTitle().split(""));
        // 查询最近浏览记录,传递当前登陆的用户ID
        map.put(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));
        ModelAndView mav = new ModelAndView("goods/search").addObject(ValueUtil.MAP, map);
        return topAndBottomService.getTopAndBottom(mav);
    }

    /**
     * 查询商品列表
     *
     * @param searchBean 查询辅助Bean
     * @throws UnsupportedEncodingException
     * @author NINGPAI-WangHaiYang
     */
    // @RequestMapping("/searchProduct2")
    public ModelAndView searchProduct2(String[] params, PageBean pb, String priceMin, String priceMax, GoodsSiteSearchBean searchBean, HttpServletRequest request,
                                       HttpServletResponse response, Long cId) throws UnsupportedEncodingException {
        Long cId1 = cId;
        PageBean pb1 = pb;
        pb1.setPageSize(20);
        // 字符集转换为utf-8
        String title = StringCommonUtil.changeToUtf8(searchBean.getTitle());
        // 检查是否有要转义字符，没有，在cookie中保存搜索记录
        if (StringCommonUtil.checkSpecialCharacter(title)) {
            addCookieToSearchProduct(title, request, response);
        }
        // 特殊字符转义
        title = StringCommonUtil.escapeCharacterEntities(title);
        searchBean.setTitle(title);
        Map<String, Object> map = new HashMap<String, Object>();
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        map.put(DISTINCTID, distinctId);
        searchBean.setDistinctId(distinctId);
        if (priceMin != null && !"".equals(priceMin)) {
            searchBean.setPriceMin(new BigDecimal(priceMin));
        }
        if (priceMax != null && !"".equals(priceMax)) {
            searchBean.setPriceMax(new BigDecimal(priceMax));
        }
        if (cId1 != null && params != null) {
            pb1 = this.goodsService.searchGoods(pb, searchBean, params);
        } else {
            pb1 = this.goodsService.searchGoods(pb, searchBean);
        }
        map.put("pb", pb1);

        if (cId1 == null) {
            // 判断title是否是商品分类
            // 查询分类信息
            GoodsBreadCrumbVo goodsBreadCrumbVo = goodsCateService.queryBreadCrubByCatName(searchBean.getTitle());
            if (goodsBreadCrumbVo != null) {

                GoodsCateVo goodsCateVo = cateService.queryCateById(goodsBreadCrumbVo.getCatId());

                if (goodsCateVo.getCateVos() != null && !goodsCateVo.getCateVos().isEmpty()) {
                    if (goodsCateVo.getCateVos().get(0).getCateVos() != null && !goodsCateVo.getCateVos().get(0).getCateVos().isEmpty()) {
                        cId1 = goodsCateVo.getCateVos().get(0).getCateVos().get(0).getCatId();
                    } else {
                        cId1 = goodsCateVo.getCateVos().get(0).getCatId();
                    }
                } else {
                    cId1 = goodsCateVo.getCatId();
                }

            } else {
                // 不是就取第一个商品的Id
                if (pb.getList() != null && !pb.getList().isEmpty()) {
                    Long goodsInfoId = ((GoodsShowListVo) pb.getList().get(0)).getGoodsInfoId();
                    cId1 = goodsService.searchGoodsCatIdByGoodsInfoId(goodsInfoId);
                }
            }

        }
        // 有商品分类 查询相关规格
        if (cId1 != null) {
            GoodsTypeVo typeVo = null;
            List<GoodsListScreenVo> screenVo = null;
            if (null != request.getSession().getAttribute(DISTINCTID)) {
                distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
            }
            GoodsCateVo cateVo = goodsCateService.queryCateByCatId(cId1);
            GoodsCateVo gcv = null;
            if (cateVo.getCatGrade() != null) {
                gcv = goodsCateService.queryCateById(cId1);
            }
            if (gcv != null && gcv.getCateVos() != null && !gcv.getCateVos().isEmpty() && gcv.getCateVos().get(0).getCateVos() != null
                    && gcv.getCateVos().get(0).getCateVos().get(0) != null) {
                for (int j = 0; j < gcv.getCateVos().size(); j++) {
                    for (int i = 0; i < gcv.getCateVos().get(j).getCateVos().size(); i++) {
                        String ppId = null;
                        if (gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId() != null) {
                            ppId = gcv.getCateVos().get(j).getCateVos().get(i).getCatParentId().toString();
                        }

                        if (cId1.toString().equals(ppId)) {
                            cId1 = gcv.getCateVos().get(j).getCateVos().get(i).getCatId();
                        }
                    }
                }
            }

            typeVo = this.goodsTypeService.queryGoodsTypeByCatId(cId1);
            screenVo = this.goodsService.calcScreenParam(params, typeVo);
            map.put("cate", gcv);
            map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            if (cateVo.getCatGrade() == 1L || cateVo.getCatGrade() == 3L) {
                map.put(NOWCATE, goodsCateService.queryCateByCatId(cId1));
            } else {
                cId1 = cateVo.getCatId();
                map.put(NOWCATE, goodsCateService.queryCateByCatId(cId1));
            }
            map.put(PARAMS, screenVo);
        }

        map.put(SEARCHBEAN, searchBean);
        map.put("titleArray", searchBean.getTitle().split(""));
        // 查询最近浏览记录,传递当前登陆的用户ID
        map.put(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));
        ModelAndView mav = new ModelAndView("goods/search").addObject(ValueUtil.MAP, map);

        return topAndBottomService.getTopAndBottom(mav);
    }

    /**
     * 通过elasticsearch 搜索引擎搜索商品数据-liangck-20150819
     *
     * @param pageBean 搜索的分页工具类
     * @param keyWords 关键字
     * @param brands   品牌
     * @param cats     分类
     * @param params   扩展属性
     * @param sort     排序
     * @return 搜索结果页
     */
    @RequestMapping("/searchProduct2")
    public ModelAndView esearchProduct(HttpServletRequest request, HttpServletResponse response, SearchPageBean<EsGoodsInfo> pageBean,
                                       @RequestParam(value = "title") String keyWords, String[] brands, String[] cats, String[] params, String sort, String priceMin, String priceMax, String showStock, String isThird, String inMarketing) {
        Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
        String keyWords1 = keyWords;
        keyWords1 = scriptPattern.matcher(keyWords1).replaceAll("");

        scriptPattern = Pattern.compile("[<>\"]+");
        keyWords1 = scriptPattern.matcher(keyWords1).replaceAll("");

        pageBean.setPageSize(16);
        // 索引
        String[] indices = new String[]{GoodsIndexConstant.PRODUCT_INDEX_NAME()};
        // 类型
        String[] types = new String[]{GoodsIndexConstant.PRODUCT_TYPE};
        // 地区
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        // 根据地区ID查询关联仓库ID
        Long wareId = productService.selectWareIdByDistinctId(distinctId);
        Long[] wares = wareId == null ? null : new Long[]{wareId};
        // 商品搜索并获取结果, 第三方ID为空
        BasicSet basicSet = basicSetService.findBasicSet();
        showStock =  basicSet.getShowStock();
        Map<String, Object> resultMap = goodsElasticSearchServivice.searchGoods(pageBean, wares, indices, types, keyWords1, brands, cats, params, sort, priceMin, priceMax, null,
                null, showStock, null, isThird, inMarketing);

        ModelAndView model = new ModelAndView("goods/esearch");
        // 当前登录成功的会员ID
        Long custId = this.takeCustIdFromRequest(request);
        if (custId != null) {
            Map<String, Object> followMap = new HashMap<>();
            followMap.put(CUSTOMERID, custId);
            // 得到当前会员收藏货品的id集合
            List<String> goodsIds = customerFollowService.selectCustomerFollowForList(followMap);
            model.addObject("goodsIds", goodsIds);
        }
        model.addObject("map", resultMap);
        // 已选搜索条件
        model.addObject("keyWorlds", keyWords1);
        model.addObject(PARAMS, params);
        model.addObject("brands", brands);
        model.addObject("sort", sort);
        // 筛选的价格条件
        model.addObject("priceMin", priceMin);
        model.addObject("priceMax", priceMax);
        // 是否显示有货
        model.addObject("showStock", showStock);
        // 显示商城自营商品
        model.addObject("isThird", isThird);
        // 查询的仓库
        model.addObject("wareId", wareId);
        // 是否营销
        model.addObject("inMarketing", inMarketing);
        // 设置查询的地区
        model.addObject(DISTINCTID, distinctId);
        // model.addAttribute("cats",cats);//分类后台暂时未聚合
        // 查询最近浏览记录,传递当前登陆的用户ID
        model.addObject(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));
        return topAndBottomService.getTopAndBottom(model);
    }

    /**
     * 根据货品编号查询商品的评论
     *
     * @return 分页对象
     */
    @RequestMapping("/queryProducCommentForDetail")
    @ResponseBody
    public PageBean queryProductComment(PageBean pb, Long productId, String title, Character type, String askType) {
        if (null != askType && !"".equals(askType)) {
            // 根据货品ID获取 单个的货品对象
            GoodsProductVo goodsProductVo = productService.queryProductByProductId(productId);
            // 日志记录
            LOGGER.info(LOGGERINFO + goodsProductVo.getProductName() + "】的评论");
            return this.commentServiceMapper.selectCommByGoodsId(pb, productId, type, title, askType);
        } else {
            return this.commentServiceMapper.selectCommByGoodsId(pb, productId, type, title);
        }
    }

    /**
     * Ajax保存货品咨询
     *
     * @param request    请求对象
     * @param askComment 咨询的内容
     * @param type       咨询类型
     * @return 返回的标记 -1:未登录 1:成功 0:失败
     */
    @RequestMapping("/saveCommentAsk")
    @ResponseBody
    public int saveCommentAsk(HttpServletRequest request, String askComment, int type, Long productId) {
        if (null != takeCustIdFromRequest(request)) {
            // 保存货品咨询的返回值
            return this.productService.saveProductCommentAsk(type, askComment, takeCustIdFromRequest(request), productId, request);
        } else {
            return -1;
        }
    }

    /**
     * 根据分类IDAJAX查询面包屑信息
     *
     * @param catId 分类ID {@link Long}
     * @return 查询到的面包屑Bean {@link GoodsBreadCrumbVo}
     */
    @RequestMapping("/loadGoodsBreadCrumb")
    @ResponseBody
    public GoodsBreadCrumbVo queryGoodsBreadCrumbByCatId(Long catId) {
        // 根据主键 获取单个的分类信息
        GoodsBreadCrumbVo goodsBreadCrumbVo = goodsCateService.queryBreadCrubByCatId(catId);
        // 非空验证 分类名称
        if (null != goodsBreadCrumbVo.getCatName()) {
            // 日志记录
            LOGGER.info("查询分类名称为【" + goodsBreadCrumbVo.getCatName() + "】的面包屑信息！");
        }
        return goodsBreadCrumbVo;
    }

    /**
     * 根据评论编号查询回复列表
     *
     * @param commentId 评论ID{@link Long}
     * @return 查询到的回复列表 {@link List}
     */
    @RequestMapping("/queryCommentReplay")
    @ResponseBody
    public List<CommentReplay> queryCommentReplayList(Long commentId) {
        // 日志记录
        LOGGER.info("根据评论编号查询回复列表,评论ID为" + commentId);
        return this.commentReplayServiceMapper.selectByCommentId(commentId);
    }

    /**
     * 点击第一级第二级分类的时候跳转控制器
     *
     * @param catId 分类ID
     * @param level 分类层级标记
     */
    @RequestMapping("/jumpList")
    public ModelAndView jumpList(Long catId, String level) {
        return new ModelAndView(new RedirectView("../list/" + this.goodsCateService.calcCatUrl(catId, level) + ".html"));
    }

    /**
     * 发表评论回复控制器
     *
     * @param replayContent 评论内容
     * @param commentId     需要回复的评论ID
     * @param request       请求对象
     * @return 回复的状态标记 0:失败,1:成功
     */
    @RequestMapping("/uploadCommentReplay")
    @ResponseBody
    public int uploadCommentReplay(String replayContent, Long commentId, HttpServletRequest request) {
        String regEx = "[`~<>]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(replayContent);
        if (replayContent == null || m.find()) {
            return -1;
        }
        if (null != request.getSession().getAttribute(CUSTOMERID)) {
            // 根据主键查询评论的单个对象信息
            Comment comment = commentServiceMapper.selectByCommentId(commentId);
            // 非空验证 商品名称
            if (null != comment.getGoodsName()) {
                // 日志记录
                LOGGER.info("回复商品【" + comment.getGoodsName() + "】的评论");
            }
            return this.commentReplayServiceMapper.addCommentRepaly(request, commentId, replayContent);
        } else {
            return -1;
        }
    }

    /**
     * 查询所有的商品评论
     *
     * @param productId 货品ID
     * @param request   请求对象
     */
    @RequestMapping("/allComment")
    public ModelAndView allComment(Long productId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        map.put(DISTINCTID, distinctId);
        GoodsDetailBean detailBean = this.productService.queryDetailBeanByProductId(productId, distinctId);
        try {
            if (null != detailBean.getProductVo()) {
                map.put(DETAILBEAN, detailBean);
                /* 查询商品关联的标签 */
                map.put("tags", this.goodsPub.getGoodsReleTagService().queryreleListByProductId(detailBean.getProductVo().getGoodsInfoId()));
                ModelAndView mav = new ModelAndView("comment/comment", ValueUtil.MAP, map);
                // 根据货品ID获取 单个的货品对象
                GoodsProductVo goodsProductVo = productService.queryProductByProductId(productId);
                // 非空验证 货品名称
                if (null != goodsProductVo.getProductName()) {
                    LOGGER.info(LOGGERINFO + goodsProductVo.getProductName() + "】下面所有的商品评论");
                }
                return topAndBottomService.getTopAndBottom(mav);
            } else {
                return null;
            }
        } finally {
            detailBean = null;
            map = null;
            distinctId = null;
        }
    }

    /**
     * 查询所有的商品咨询
     *
     * @param productId 货品iD{@link Long}
     * @param request   请求对象
     */
    @RequestMapping("/allCosult")
    public ModelAndView allCosult(Long productId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long distinctId = null;
        /* 如果session中的地区ID为空,就设置为默认江苏南京 */
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        map.put(DISTINCTID, distinctId);
        GoodsDetailBean detailBean = this.productService.queryDetailBeanByProductId(productId, distinctId);
        try {
            // 验证登录
            if (LoginUtil.checkLoginStatus(request)) {
                if (null != detailBean.getProductVo()) {
                    map.put(DETAILBEAN, detailBean);
                    ModelAndView mav = new ModelAndView("comment/cosult", ValueUtil.MAP, map);
                    // 根据货品ID获取 单个的货品对象
                    GoodsProductVo goodsProductVo = productService.queryProductByProductId(productId);
                    // 非空验证 货品名称
                    if (null != goodsProductVo.getProductName()) {
                        LOGGER.info(LOGGERINFO + goodsProductVo.getProductName() + "】下面所有的商品咨询。");
                    }
                    return topAndBottomService.getTopAndBottom(mav);
                } else {
                    return null;
                }
            }else{
                return new ModelAndView(new RedirectView(request.getContextPath() +"/login.html?url=cosult/"+productId+".html"));
            }

        } finally {
            detailBean = null;
            map = null;
            distinctId = null;
        }
    }

    /**
     * 品牌货品列表页
     *
     * @param pageBean
     * @param brandId
     * @param request
     * @retur
     */
    @RequestMapping("/brandProductsList")
    public ModelAndView brandProductsList(PageBean pageBean, Long brandId, HttpServletRequest request) {
        pageBean.setPageSize(20);
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        Long distinctId = null;
        List<Long> list = goodsService.selectCatIdByBrandId(brandId);
        if (!list.isEmpty()) {
            map.put(HOTPRODUCT, productService.queryTopSalesByCatIds(list.get(0), 4));
            map.put(HOTSALES, productService.queryHotSalesTopSix(list.get(0), 4));
            map.put(FINALBUY, productService.browCatFinalBuyAndPrecent(list.get(0), 4L));
        }

        // 查询最近的浏览记录,需要传递当前登陆的会员ID
        map.put(ValueUtil.BROWSERPRODUCT, this.browerService.loadBrowHist(request));
        if (null != request.getSession().getAttribute(DISTINCTID)) {
            distinctId = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
        } else {
            distinctId = addressService.getDefaultIdService();
            if (distinctId == null) {
                distinctId = 1103L;
            }
        }
        map.put("pb", goodsService.selectProductsByBrandId(pageBean, brandId, distinctId));
        map.put(DISTINCTID, distinctId);
        map.put("brandId", brandId);
        mav.addObject(ValueUtil.MAP, map);
        mav.setViewName("goods/brandproductlist");
        return topAndBottomService.getTopAndBottom(mav);
    }

    /**
     * 添加到货通知
     *
     * @return 0失败 1成功
     */
    @RequestMapping("/addArrivalNotice")
    @ResponseBody
    public int addArrivalNotice(HttpServletRequest request, ArrivalNotice notice) {
        // 验证登录
        if (request.getSession().getAttribute(com.ningpai.site.customer.vo.CustomerConstantStr.CUSTOMERID) != null) {
            Long wareId = this.wareHouseService.selectWareIdByDistinctId(notice.getDistrictId());
            //验证手机是否已经添加过
            if(!notice.getInfoMobile().equals(null) && !notice.getInfoMobile().equals("")){
                Map<String, Object> paraMapMbile = new HashMap<String, Object>();
                paraMapMbile.put(NOTICESTURTS, "0");
                paraMapMbile.put(GOODSINFOID, notice.getGoodsInfoId());
                paraMapMbile.put(INFOMOBILE, notice.getInfoMobile());
                paraMapMbile.put(WAREID, wareId);
                Long resultMoblie = arrivalNoticeMapper.slelctArrivalNotice(paraMapMbile);
                if(resultMoblie ==1){
                    return 2;
                }
            }

            //验证邮箱是否已经添加过
            if(!notice.getInfoEmail().equals(null) && !notice.getInfoEmail().equals("")){
                Map<String, Object> paraMapEmail = new HashMap<String, Object>();
                paraMapEmail.put(NOTICESTURTS, "0");
                paraMapEmail.put(GOODSINFOID, notice.getGoodsInfoId());
                paraMapEmail.put(INFOEMAIL, notice.getInfoEmail());
                paraMapEmail.put(WAREID, wareId);
                //验证手机是否已经添加过
                Long resultEmail = arrivalNoticeMapper.slelctArrivalNotice(paraMapEmail);
                if(resultEmail ==1){
                    return 3;
                }
            }
            notice.setWareId(wareId);
            //添加
            int result = arrivalNoticeMapper.insertSelective(notice);
            if(result == 1){
                return 1;
            }else {
                return 0;
            }
        }else{
            return 4;
        }
    }

    /**
     * 添加到货通知
     *
     * @return 0失败 1成功
     */
    @RequestMapping("/loginArrivalNotice")
    @ResponseBody
    public int loginArrivalNotice(HttpServletRequest request) {
        // 验证登录
        if (request.getSession().getAttribute(com.ningpai.site.customer.vo.CustomerConstantStr.CUSTOMERID) != null) {
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 获取拼写建议词
     *
     * @param q 搜索关键词
     * @return 拼写建议词列表
     */
    @RequestMapping(value = "/completionWords", method = RequestMethod.GET)
    @ResponseBody
    public List<String> autoCompletionWords(@RequestParam(value = "keyWords") String q) {
        return goodsElasticSearchServivice.getCompletionSuggest(q);
    }

    public GoodsProductService getProductService(Long askId) {
        return productService;
    }

    @Resource(name = "HsiteGoodsProductService")
    public void setProductService(GoodsProductService productService) {
        this.productService = productService;
    }

    public GoodsService getGoodsService() {
        return goodsService;
    }

    @Resource(name = "HsiteGoodsService")
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public GoodsTypeService getGoodsTypeService() {
        return goodsTypeService;
    }

    @Resource(name = "HsiteGoodsTypeService")
    public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }

    public GoodsCateService getGoodsCateService() {
        return goodsCateService;
    }

    @Resource(name = "HsiteGoodsCateService")
    public void setGoodsCateService(GoodsCateService goodsCateService) {
        this.goodsCateService = goodsCateService;
    }

    public MarketingService getMarketingService() {
        return marketingService;
    }

    @Resource(name = "MarketingService")
    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public SiteGoodsAtteService getGoodsAtteService() {
        return goodsAtteService;
    }

    @Resource(name = "SiteGoodsAtteService")
    public void setGoodsAtteService(SiteGoodsAtteService goodsAtteService) {
        this.goodsAtteService = goodsAtteService;
    }

    public BrowserecordService getBrowserecordService() {
        return browserecordService;
    }

    @Resource(name = "browserecordService")
    public void setBrowserecordService(BrowserecordService browserecordService) {
        this.browserecordService = browserecordService;
    }

    public CommentServiceMapper getCommentServiceMapper() {
        return commentServiceMapper;
    }

    @Resource(name = "commentServiceMapper")
    public void setCommentServiceMapper(CommentServiceMapper commentServiceMapper) {
        this.commentServiceMapper = commentServiceMapper;
    }

    public BrowerService getBrowerService() {
        return browerService;
    }

    @Resource(name = "BrowerService")
    public void setBrowerService(BrowerService browerService) {
        this.browerService = browerService;
    }

    public CommentReplayServiceMapper getCommentReplayServiceMapper() {
        return commentReplayServiceMapper;
    }

    @Resource(name = "commentReplayServiceMapper")
    public void setCommentReplayServiceMapper(CommentReplayServiceMapper commentReplayServiceMapper) {
        this.commentReplayServiceMapper = commentReplayServiceMapper;
    }

    /**
     * 在cookie里添加搜索记录
     *
     * @param title
     * @param request
     * @param response
     * @author NINGPAI-WangHaiYang
     */
    private void addCookieToSearchProduct(String title, HttpServletRequest request, HttpServletResponse response) {

        LOGGER.debug("==================================" + title);
        // 生命周期
        int maxAge = 60 * 60 * 24 * 3;
        // 获取搜索历史
        Cookie cookie = NpCookieUtil.getCookieByName(request, SearchCookieController.COOKIENAME);
        String list = null;
        // 添加搜索历史
        if (null != cookie) {
            try {
                list = URLDecoder.decode(cookie.getValue(), ConstantUtil.UTF);
                // 判断要添加的搜索历史是否存在
                if (list.indexOf(title) == -1) {
                    list = title + "," + list;
                }

            } catch (UnsupportedEncodingException e) {
                LOGGER.error("", e);
            }
        } else {
            list = title + ",";
        }
        try {
            NpCookieUtil.addCookie(response, SearchCookieController.COOKIENAME, list, maxAge);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("", e);
        }
    }

    public GoodsOpenSpecService getGoodsOpenSpecService() {
        return goodsOpenSpecService;
    }

    @Resource(name = "GoodsOpenSpecService")
    public void setGoodsOpenSpecService(GoodsOpenSpecService goodsOpenSpecService) {
        this.goodsOpenSpecService = goodsOpenSpecService;
    }

    public CustomerServiceInterface getCustomerServiceInterface() {
        return customerServiceInterface;
    }

    /**
     * spring 注入属性
     *
     * @param customerServiceInterface
     */
    @Resource(name = "customerServiceSite")
    public void setCustomerServiceInterface(CustomerServiceInterface customerServiceInterface) {
        this.customerServiceInterface = customerServiceInterface;
    }
}
