/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.goods.controller;

import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.bean.ParamIds;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.coupon.service.CouponRangeService;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.goods.bean.GoodsImage;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.goods.service.GoodsOpenSpecService;
import com.ningpai.goods.util.GoodsIndexConstant;
import com.ningpai.goods.util.SearchPageBean;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.goods.bean.GoodsDetailBean;
import com.ningpai.m.goods.service.*;
import com.ningpai.m.goods.util.GoodsSiteSearchBean;
import com.ningpai.m.goods.util.ValueUtil;
import com.ningpai.m.goods.vo.GoodsCateVo;
import com.ningpai.m.goods.vo.GoodsListScreenVo;
import com.ningpai.m.goods.vo.GoodsTypeVo;
import com.ningpai.m.shoppingcart.service.ShoppingCartService;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.marketing.service.MarketingService;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.service.MobCateBarService;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.system.bean.OnLineService;
import com.ningpai.system.bean.OnLineServiceItem;
import com.ningpai.system.bean.ShopKuaiShang;
import com.ningpai.system.service.*;
import com.ningpai.system.util.AddressUtil;
import com.ningpai.thirdaudit.bean.StoreInfo;
import com.ningpai.thirdaudit.service.StoreInfoService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.StringCommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月8日 下午2:15:52
 * @version
 */
@Controller
public final class GoodsSiteController {

    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(GoodsSiteController.class);

    /**
     *静态变量distinctId
     * */
    private static final String DISTINCTID = "distinctId";
    /**
     *静态变量searchBean
     * */
    private static final String SEARCHBEAN = "searchBean";
    /**
     *静态变量params
     * */
    private static final String PARAMS = "params";
    /**
     *静态变量goods/goods_list
     * */
    private static final String GOODS_LIST = "goods/pro_list";
    /**
     *静态变量storeId
     * */
    private static final String STOREID = "storeId";
    /**
     *静态变量width
     * */
    private static final String WIDTH = "width";

    private static final String CHADDRESS = "chAddress";

    private static final String CHPROVINCE = "chProvince";

    private static final String CHCITY = "chCity";

    private static final String CHDISTINCT = "chDistinct";

    private GoodsCateService goodsCateService;

    private GoodsTypeService goodsTypeService;

    private GoodsService goodsService;

    private GoodsProductService productService;

    private MarketingService marketingService;

    private SiteGoodsAtteService goodsAtteService;

    private CommentServiceMapper commentServiceMapper;

    private GoodsOpenSpecService goodsOpenSpecService;
    /**
     * Spring注入
     * */
    @Resource(name = "SeoService")
    private SeoService seoService;
    /**
     * Spring注入
     * */
    @Resource(name = "DefaultAddressService")
    private DefaultAddressService addressService;
    /**
     * Spring注入
     * */
    @Resource(name = "ShoppingCartService")
    private ShoppingCartService cartService;

    /**
     * 快商通
     */
    @Resource(name = "ShopKuaiShangService")
    private ShopKuaiShangService shopKuaiShangService;

    /**
     * Spring注入
     * */
    @Resource(name = "GoodsElasticSearchService")
    private GoodsElasticSearchService goodsElasticSearchServivice;
    
    @Resource(name="mbrowserecordService")
    private BrowserecordService browserecordService; 

    /**
     * Spring注入
     * 优惠券关系
     * */
    @Resource(name = "CouponRangeService")
    private CouponRangeService couponRangeService;

    /**
     * Spring注入
     * 优惠券列表
     * */
    @Resource(name = "CouponService")
    private CouponService couponService;

    @Resource(name = "DistrictService")
    private DistrictService districtService;

    /**
     * 优惠券券码接口
     */
    @Resource(name = "CouponNoService")
    private CouponNoService couponNoService;

    /**
     * 在线客服service
     */
    @Resource(name = "onLineServiceItemBizImpl")
    private IOnLineServiceItemBiz onLineServiceItemService;

    @Resource(name = "onLineServiceBizImpl")
    private IOnLineServiceBiz onLineServiceBiz;

    @Resource(name = "StoreService")
    private StoreInfoService storeInfoService;

    @Resource(name = "MobCateBarService")
    private MobCateBarService mobCateBarService;

    /**
     * 从请求中取出登陆的会员iD
     * 
     * @param request
     *            请求对象
     * @return 拿出的会员Id
     */
    public Long takeCustIdFromRequest(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute("customerId");
    }

    /**
     * 根据分类查询商品列表
     * 
     * @param cid
     *            分类ID
     */
    @RequestMapping("/list")
    public ModelAndView goodsList(Long cid, PageBean pb, GoodsSiteSearchBean searchBean, String[] params, HttpServletRequest request,String showStatus) {
        pb.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        GoodsTypeVo typeVo = null;
        List<GoodsListScreenVo> screenVo = null;
        try {
            //优先考虑移动端导航分类的id，获取其关联的分类id进行赋值
            if(searchBean.getCateBarId() != null){
                MobCateBar mobCateBar = mobCateBarService.selectMobcateBarById(searchBean.getCateBarId());
                if(mobCateBar != null && mobCateBar.getCateId() != null){
                    cid = mobCateBar.getCateId();
                }else{
                    cid = -1l;//如果导航分类没有关联分类id，直接显示没有相关的商品
                }
            }

            GoodsCateVo goodsCateVo = null;
            if(cid != null && cid.longValue() > 0){
                goodsCateVo = goodsCateService.queryCateByCatId(cid);
                if (goodsCateVo == null) {
                    cid = -1l;
                }
            }
            screenVo = this.goodsService.calcScreenParam(params, typeVo);
            map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            map.put("nowcate", goodsCateVo);
            Long dId = addressService.getDefaultIdService();
            if (dId == null) {
                dId = 1103L;
            }
            map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid, params, dId.toString()));
            map.put(DISTINCTID, dId.toString());
            map.put(SEARCHBEAN, searchBean);
            map.put(PARAMS, screenVo);
            ModelAndView mav = new ModelAndView(GOODS_LIST);
            mav.addObject(ValueUtil.MAP, map);
            mav.addObject("showStatus", showStatus);
            // 商家编号
            if (searchBean.getStoreId() == null) {
                mav.addObject(STOREID, 0);
            } else {
                mav.addObject(STOREID, searchBean.getStoreId());
            }
            return seoService.getCurrSeo(mav);
        } finally {
            map = null;
            typeVo = null;
            screenVo = null;
        }
    }

    /**
     * 根据Id查询店铺所有商品列表
     *
     * @param searchBean
     *            storeId
     *
     */
    @RequestMapping("/allGoodsListByStoreId")
    public ModelAndView allGoodsListByStoreId(PageBean pb, GoodsSiteSearchBean searchBean, String[] params, HttpServletRequest request) {
        // 显示行数
        pb.setPageSize(20);
        // 创建容器
        Map<String, Object> map = new HashMap<String, Object>();
        GoodsTypeVo typeVo = null;
        List<GoodsListScreenVo> screenVo = null;
        try {
            screenVo = this.goodsService.calcScreenParam(params, typeVo);
            map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            // 地区
            Long dId = addressService.getDefaultIdService();
            // 设置默认地址
            if (dId == null) {
                dId = 1103L;
            }
            // 查询货品列表
            map.put("pb", this.goodsService.searchGoods(pb, searchBean, null, params, dId.toString()));
            map.put(DISTINCTID, dId.toString());
            map.put(SEARCHBEAN, searchBean);
            map.put(PARAMS, screenVo);
            ModelAndView mav = new ModelAndView(GOODS_LIST);
            mav.addObject(ValueUtil.MAP, map);
            // 商家编号
            if (searchBean.getStoreId() == null) {
                mav.addObject(STOREID, 0);
            } else {
                mav.addObject(STOREID, searchBean.getStoreId());
            }
            return seoService.getCurrSeo(mav);
        } finally {
            map = null;
            typeVo = null;
            screenVo = null;
        }
    }

    /**
     * 商品搜索
     * 
     * @param params
     *            参数
     * @param pb
     *            分页工具累
     * @param siteSearchBean
     *            搜索实体类
     * @param request
     * @param response
     * @return
     */
    // @RequestMapping("/searchProduct")
    public ModelAndView searchProduct(String[] params, PageBean pb, GoodsSiteSearchBean siteSearchBean, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<String, Object>();
        pb.setPageSize(20);
        // GoodsTypeVo typeVo = null;
        // List<GoodsListScreenVo> screenVo = null;
        try {
            // screenVo = this.goodsService.calcScreenParam(params, typeVo);
            // map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            // 字符集转换为utf-8
            // String title =
            // StringCommonUtil.changeToUtf8(siteSearchBean.getTitle());
            // String title=new
            // String(siteSearchBean.getTitle().getBytes(Charset.forName("ISO-8859-1")),"utf-8");
            // 特殊字符转义
            String title = StringCommonUtil.escapeCharacterEntities(siteSearchBean.getTitle());
            siteSearchBean.setTitle(title);
            Long dId = addressService.getDefaultIdService();
            if (dId == null) {
                dId = 1103L;
            }
            // 搜索结果
            map.put("pb", this.goodsService.searchGoods(pb, siteSearchBean));
            // 地址
            map.put(DISTINCTID, dId.toString());
            // 搜索类
            map.put(SEARCHBEAN, siteSearchBean);
            // map.put(PARAMS, screenVo);
            // map.put("title", siteSearchBean.getTitle());
            // 返回页面
            ModelAndView mav = new ModelAndView("goods/goods_search");
            // 搜索内容
            mav.addObject("title", siteSearchBean.getTitle());
            // 排序
            mav.addObject("sort", siteSearchBean.getSort());
            mav.addObject(ValueUtil.MAP, map);
            // 商家编号
            if (siteSearchBean.getStoreId() == null) {
                mav.addObject(STOREID, 0);
            } else {
                mav.addObject(STOREID, siteSearchBean.getStoreId());
            }
            return seoService.getCurrSeo(mav);
        } finally {
            map = null;
            // typeVo = null;
            // screenVo = null;
        }
    }


    /**
     * 基于eslaticsearch的搜索
     *
     * @param request
     * @param pageBean
     * @param params
     * @param keyWords
     * @param sort
     *            排序
     * @param storeId
     *            店铺ID
     * @param showStock
     *            是否只显示有货{1:是,0:否}
     * @return
     */
    @RequestMapping("/searchproductnew")
    @ResponseBody
    public Map<String,Object> esearchProductNew(HttpServletRequest request, SearchPageBean<EsGoodsInfo> pageBean, String[] params, String keyWords, String sort, Long storeId,
                                       String showStock, String isThird) {
        pageBean.setPageSize(20);
        // 索引
        String[] indices = new String[] { GoodsIndexConstant.PRODUCT_INDEX_NAME() };
        // 类型
        String[] types = new String[] { GoodsIndexConstant.PRODUCT_TYPE };
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
        Long[] wares = wareId == null ? null : new Long[] { wareId };
        // 商品搜索结果
        Map<String, Object> resultMap = goodsElasticSearchServivice.searchGoods(pageBean, wares, indices, types, keyWords, null, null, params, sort, null, null,
                (storeId != null && 0 == storeId) ? null : storeId, null, showStock, "1", isThird);
        // 页面数据
        return resultMap;
    }

    /**
     * 基于eslaticsearch的搜索
     *
     * @param request
     * @param pageBean
     * @param params
     * @param keyWords
     * @param sort
     *            排序
     * @param storeId
     *            店铺ID
     * @param showStock
     *            是否只显示有货{1:是,0:否}
     * @return
     */
    @RequestMapping("/searchProduct")
    public ModelAndView esearchProduct(HttpServletRequest request, SearchPageBean<EsGoodsInfo> pageBean, String[] params, String keyWords, String sort, Long storeId,String showStatus
                                       ,String showStock, String isThird) {
        pageBean.setPageSize(20);
        // 索引
        String[] indices = new String[] { GoodsIndexConstant.PRODUCT_INDEX_NAME() };
        // 类型
        String[] types = new String[] { GoodsIndexConstant.PRODUCT_TYPE };
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
        Long[] wares = wareId == null ? null : new Long[] { wareId };
        // 商品搜索结果
        Map<String, Object> resultMap = goodsElasticSearchServivice.searchGoods(pageBean, wares, indices, types, keyWords, null, null, params, sort, null, null,
                (storeId != null && 0 == storeId) ? null : storeId, null, showStock, "1", isThird);
        // 拼装返回结构
        ModelAndView model = new ModelAndView("goods/pro_es_list");
        // 搜索条件
        model.addObject("keyWords", keyWords);
        model.addObject("sort", sort == null ? "" : sort);
        model.addObject(STOREID, storeId == null ? 0L : storeId);
        // 查询的仓库
        model.addObject("wareId", wareId);
        model.addObject("showStatus", showStatus);
        // 页面数据
        model.addObject("map", resultMap);
        return seoService.getCurrSeo(model);
    }

    /**
     * 查询所有移动版商品列表
     *
     * @param params
     *            分类ID
     */
    @RequestMapping("/allproajax")
    @ResponseBody
    public Map<String,Object> allGoodsListAjax(PageBean pb, GoodsSiteSearchBean searchBean, String[] params, HttpServletRequest request,Long cid) {
        pb.setPageSize(20);
            Map<String, Object> map = new HashMap<String, Object>();
        GoodsTypeVo typeVo = null;
        List<GoodsListScreenVo> screenVo = null;
        try {
            //优先考虑移动端导航分类的id，获取其关联的分类id进行赋值
            if(searchBean.getCateBarId() != null){
                MobCateBar mobCateBar = mobCateBarService.selectMobcateBarById(searchBean.getCateBarId());
                if(mobCateBar != null && mobCateBar.getCateId() != null){
                    cid = mobCateBar.getCateId();
                }else{
                    cid = -1l;//如果导航分类没有关联分类id，直接显示没有相关的商品
                }
            }

            screenVo = this.goodsService.calcScreenParam(params, typeVo);
            map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            Long dId = addressService.getDefaultIdService();
            if (dId == null) {
                dId = 1103L;
            }
            if(cid==null){
                map.put("pb", this.goodsService.searchGoods(pb, searchBean, null, params, dId.toString()));
            }else{
                map.put("pb", this.goodsService.searchGoods(pb, searchBean, cid, params, dId.toString()));
            }
            map.put(DISTINCTID, dId.toString());
            map.put(SEARCHBEAN, searchBean);
            map.put(PARAMS, screenVo);
            return map;
        } finally {
            map = null;
            typeVo = null;
            screenVo = null;
        }
    }



    /**
     * 查询所有移动版商品列表
     * 
     * @param params
     *            分类ID
     */
    @RequestMapping("/allpro")
    public ModelAndView allGoodsList(PageBean pb, GoodsSiteSearchBean searchBean, String[] params, HttpServletRequest request,String showStatus) {
        pb.setPageSize(20);
        Map<String, Object> map = new HashMap<String, Object>();
        GoodsTypeVo typeVo = null;
        List<GoodsListScreenVo> screenVo = null;
        try {
            screenVo = this.goodsService.calcScreenParam(params, typeVo);
            map.put("type", this.goodsService.calcTypeVo(screenVo, typeVo));
            Long dId = addressService.getDefaultIdService();
            if (dId == null) {
                dId = 1103L;
            }
            map.put("pb", this.goodsService.searchGoods(pb, searchBean, null, params, dId.toString()));
            map.put(DISTINCTID, dId.toString());
            map.put(SEARCHBEAN, searchBean);
            map.put(PARAMS, screenVo);
            ModelAndView mav = new ModelAndView(GOODS_LIST);
            mav.addObject(ValueUtil.MAP, map);
            if (searchBean.getStoreId() == null) {
                mav.addObject(STOREID, 0);
            } else {
                mav.addObject(STOREID, searchBean.getStoreId());
            }
            mav.addObject("showStatus",showStatus);
            return seoService.getCurrSeo(mav);
        } finally {
            map = null;
            typeVo = null;
            screenVo = null;
        }
    }



    /**
     * 商品详情页
     * 
     * @param productId
     * @author lih 货品ID
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/productdetail")
    public ModelAndView goodsDetail(Long productId,Long distinctId, String chAddress, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //获取是否是切换规格标志
        String isAjax;
        try {
             isAjax = request.getParameter("isAjax");
            if(null == isAjax || "".equals(isAjax)){
                isAjax = "0";
            }
        }catch(Exception e){
            isAjax = "0";
            LOGGER.error("从商品列表页过来的");
        }

        Map<String, Object> map = new HashMap<>();
        Long distinctIdNew = distinctId;

        if(null == distinctIdNew){
            if (null != request.getSession().getAttribute(ValueUtil.ADDRESS) && null == request.getSession().getAttribute(CHADDRESS)) {
                distinctIdNew = ((CustomerAddress)request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictId();
                map.put(CHADDRESS,
                        ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getProvince().getProvinceName()
                                + ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getCity().getCityName()
                                + ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictName());
                map.put(CHPROVINCE, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getProvince().getProvinceName());
                map.put(CHCITY, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getCity().getCityName());
                map.put(CHDISTINCT, ((CustomerAddress) request.getSession().getAttribute(ValueUtil.ADDRESS)).getDistrict().getDistrictName());
            }else{
                if(null == request.getSession().getAttribute(CHADDRESS)){
                    Long dId = addressService.getDefaultIdService();
                    if(null == dId ){
                        dId = 1103L;
                        // 默认设置为南京建邺区仓库
                        distinctIdNew = Long.valueOf(dId);
                        map.put(CHADDRESS, "江苏南京建邺区");
                        map.put(CHPROVINCE, "江苏");
                        map.put(CHCITY, "南京市");
                        map.put(CHDISTINCT, "建邺区");
                    }else{
                        distinctIdNew = Long.valueOf(dId);
                        AddressUtil addressUtil = districtService.queryAddressNameByDistrictId(dId);
                        map.put(CHADDRESS, addressUtil.getProvinceName() + addressUtil.getCityName() + addressUtil.getDistrictName());
                        map.put(CHPROVINCE, addressUtil.getProvinceName());
                        map.put(CHCITY, addressUtil.getCityName());
                        map.put(CHDISTINCT, addressUtil.getDistrictName());
                    }
                }else{
                    /* 取session的数据 */
                    map.put(CHPROVINCE, request.getSession().getAttribute(CHPROVINCE));
                    map.put(CHADDRESS, request.getSession().getAttribute(CHADDRESS));
                    map.put(CHCITY, request.getSession().getAttribute(CHCITY));
                    map.put(CHDISTINCT, request.getSession().getAttribute(CHDISTINCT));
                    distinctIdNew = Long.parseLong(request.getSession().getAttribute(DISTINCTID).toString());
                }
            }
        }else{
             /* 如果页面传过来的地区值不为null */
            map.put(CHADDRESS, chAddress);
            String chProcince = request.getParameter(CHPROVINCE);
            String chCity = request.getParameter(CHCITY);
            String chDistinct = request.getParameter(CHDISTINCT);
            map.put(CHPROVINCE, request.getParameter(CHPROVINCE));
            map.put(CHCITY, request.getParameter(CHCITY));
            map.put(CHDISTINCT, request.getParameter(CHDISTINCT));
        }
        map.put(DISTINCTID, distinctIdNew);


        /* 保存到session中 */
        request.getSession().setAttribute(CHPROVINCE, map.get(CHPROVINCE));
        request.getSession().setAttribute(CHADDRESS, map.get(CHADDRESS));
        request.getSession().setAttribute(CHCITY, map.get(CHCITY));
        request.getSession().setAttribute(CHDISTINCT, map.get(CHDISTINCT));
        request.getSession().setAttribute(DISTINCTID, map.get(DISTINCTID));

        try {
            // 查询商品详情
            GoodsDetailBean detailBean = this.productService.queryDetailBeanByProductId(productId, distinctIdNew);
            // 判断商品是否下架
            if (null != detailBean) {
                // 限购
                detailBean = cartService.forPurchasing(detailBean, (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
                // 重新为图片排序，默认图片放到第一个
                if (null != detailBean.getProductVo()) {
                    List<GoodsImage> imageList = detailBean.getProductVo().getImageList();
                    if (CollectionUtils.isNotEmpty(imageList)) {
                        for (int i = 0; i < imageList.size(); i++) {
                            if ("1".equals(imageList.get(i).getGoodsImgSort().toString())) {
                                GoodsImage go = imageList.get(0);
                                imageList.set(0, imageList.get(i));
                                imageList.set(i, go);
                            }
                        }
                        detailBean.getProductVo().setImageList(imageList);
                    }
                }
                String mobileDesc = detailBean.getProductVo().getGoods().getMobileDesc();
                while (mobileDesc.indexOf(WIDTH) != -1) {
                    mobileDesc = mobileDesc.substring(0, mobileDesc.indexOf(WIDTH)) + mobileDesc.substring(mobileDesc.indexOf(WIDTH) + 6);
                }
                detailBean.getProductVo().getGoods().setMobileDesc(mobileDesc);
                if(null != detailBean.getProductVo().getThirdId() && 0 != detailBean.getProductVo().getThirdId()){
                    StoreInfo storeInfo = storeInfoService.selectByPrimaryKey(detailBean.getProductVo().getThirdId());
                    map.put("storeInfo", storeInfo);
                }
                // 设置商品
                map.put("detailBean", detailBean);
                // s设置规格参数
                map.put("openSpec", this.goodsOpenSpecService.queryOpenListByGoodsId(detailBean.getProductVo().getGoodsId()));
                //保存浏览记录
                browserecordService.addBrowerecord(request, response, productId);
                map.put("isAjax",isAjax);
                List<OnLineServiceItem> qqlist = onLineServiceItemService.selectItemsByType(0);
                if(qqlist != null && qqlist.size()>0){
                    map.put("QQ", qqlist.get(0));
                }else{
                    map.put("QQ", null);
                }
                map.put("customerService", onLineServiceBiz.selectSetting());
                ModelAndView mav = new ModelAndView("goods/goods_detailNew", ValueUtil.MAP, map);
                ShopKuaiShang kst = shopKuaiShangService.selectInitone();
                mav.addObject("shopKuaiShang",kst);
                OnLineService onlineService = onLineServiceBiz.selectSetting();
                if(qqlist != null && qqlist.size() == 1 && onlineService!=null && onlineService.getEffectiveTerminal().contains("3")){
                    map.put("url",qqlist.get(0).getContactNumber());
                    map.put("type","QQ");
                }else if(qqlist != null && qqlist.size()>1 && onlineService!=null && onlineService.getEffectiveTerminal().contains("3")){
                    map.put("url","readOnLineServiceQQList.htm");
                    map.put("type","QQList");
                }else if(kst != null && kst.getIsuseing().equals("0") && kst.getKstEffectiveTerminal().contains("3") && !kst.getKstEffectiveTerminal().isEmpty()){
                    map.put("url","goToKst.htm");
                    map.put("type","KST");
                }else{
                    map.put("url","");
                    map.put("type","NO");
                }
                return seoService.getCurrSeo(mav);
            } else {
                // 返回异常页面
                return seoService.getCurrSeo(new ModelAndView("goods/no_exit"));
            }
        } catch (Exception e) {
            LOGGER.error("",e);
            // 返回异常页面
            return seoService.getCurrSeo(new ModelAndView("goods/no_exit"));
        }
    }

    /**
     * 根据商品ID查询所有的货品
     * 
     * @param goodsId
     *            商品ID
     * @return 查询到的货品列表
     */
    @RequestMapping("/queryProductListByGoodsId")
    @ResponseBody
    public List<Object> queryAllProductByGoodsId(Long goodsId) {
        return this.productService.queryAllProductListByGoodsId(goodsId);
    }

    /**
     * 保存商品关注
     * 
     * @param productId
     *            会员iD
     * @param productId
     *            商品ID
     */
    @RequestMapping("/saveAtte")
    @ResponseBody
    public int saveAtte(Long productId, Long districtId, HttpServletRequest request, BigDecimal goodsprice) {
        Long custId = this.takeCustIdFromRequest(request);
        if (null != custId) {
            return this.goodsAtteService.newsaveGoodsAtte(custId, productId,districtId,goodsprice);
        } else {
            /* 未登录返回 */
            return -2;
        }
    }

    // 加入收藏
    @RequestMapping("/addcollection")
    @ResponseBody
    public int addCollection(Long productId, Long districtId, HttpServletRequest request, BigDecimal goodsprice) {
        Long custId = this.takeCustIdFromRequest(request);
        if (null != custId) {
            return this.goodsAtteService.addGoodsCollection(custId, productId, districtId, goodsprice);
        } else {
            /* 未登录返回 */
            return -2;
        }
    }



    /**
     * 根据货品编号查询商品的评论
     * 
     * @return 分页对象
     */
    @RequestMapping("/queryProducCommentForDetail")
    @ResponseBody
    public PageBean queryProductComment(PageBean pb, Long productId, String title, Character type, String askType) {
        return this.commentServiceMapper.selectCommByGoodsIdHyc(pb, productId, type, title);
    }

    /**
     * 根据货品id查询未使用的优惠券列表
     * 移动端前台商品详情页使用
     * @author houyichang 2015/10/19
     * @param productId 货品id
     *
     * */
    @RequestMapping("/queryCouponList")
    @ResponseBody
    public List<Coupon> queryCouponListBySpuId(HttpServletRequest request,Long productId,Long cateId,Long brandId,Long thirdId){
        //查询优惠券所需的参数集合
        List<ParamIds> paramIdsList = new ArrayList<ParamIds>();
        //查询优惠券所需的参数封装类
        ParamIds paramIds = new ParamIds();
        //查询优惠券所需的货品id
        paramIds.setCouponRangeFkId(productId);
        //查询优惠券所需的优惠券类型
        paramIds.setCouponRangeType("2");

        ParamIds paramIds1 = new ParamIds();
        //查询优惠券所需的分类id
        paramIds1.setCouponRangeFkId(cateId);
        paramIds1.setCouponRangeType("0");

        ParamIds paramIds2 = new ParamIds();
        //查询优惠券所需的品牌id
        paramIds2.setCouponRangeFkId(brandId);
        paramIds2.setCouponRangeType("1");
        paramIdsList.add(paramIds);
        paramIdsList.add(paramIds1);
        paramIdsList.add(paramIds2);

        //需要在此再加一个按品牌查和按分类查
        //查询优惠券列表并返回数据到前台
        return this.couponService.selectCouponListByIdsNew(paramIdsList,thirdId,request);
    }


    /**
     * 跳转到优惠券领取页面
     * 移动端前台商品详情页使用(现在修改为领取时才验证登陆，跳转页面不验证登陆)
     * @author houyichang 2015/10/19
     * @param productId 货品id
     *
     *
     * */
    @RequestMapping("/goCoupon")
    public ModelAndView goCoupon(HttpServletRequest request,Long productId,Long cateId,Long brandId,Long thirdId){
        ModelAndView mav = null;
//        //检查用户是否登录
//        if(LoginUtil.checkLoginStatus(request)){
            //查询优惠券所需的参数集合
            List<ParamIds> paramIdsList = new ArrayList<ParamIds>();
            //查询优惠券所需的参数封装类
            ParamIds paramIds = new ParamIds();
            //查询优惠券所需的货品id
            paramIds.setCouponRangeFkId(productId);
            //查询优惠券所需的优惠券类型
            paramIds.setCouponRangeType("2");

            ParamIds paramIds1 = new ParamIds();
            //查询优惠券所需的分类id
            paramIds1.setCouponRangeFkId(cateId);
            paramIds1.setCouponRangeType("0");

            ParamIds paramIds2 = new ParamIds();
            //查询优惠券所需的品牌id
            paramIds2.setCouponRangeFkId(brandId);
            paramIds2.setCouponRangeType("1");
            paramIdsList.add(paramIds);
            paramIdsList.add(paramIds1);
            paramIdsList.add(paramIds2);

            Long cId = (Long) request.getSession().getAttribute("customerId");
            List<Coupon>  couponList = this.couponService.selectCouponListByIdsNew(paramIdsList,thirdId,request);
            List<Object> cList = new ArrayList<Object>();
            for(int i = 0;i<couponList.size();i++){
                Coupon coupon = couponList.get(i);
                // 查询该用户此优惠券领取的张数
                int counts = couponNoService.selectReadyGet(coupon.getCouponId(), cId);
                //获取优惠券码去查询
                if(counts >= coupon.getCouponGetNo()){
                    cList.add(coupon.getCouponId());
                }
            }

            mav = new ModelAndView("coupon/coupons-new").addObject("couponList",this.couponService.selectCouponListByIdsNew(paramIdsList,thirdId,request)).addObject("cList",cList);
//        }else{
//            // 没登录的用户跳转到登录页面
//            mav = new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
//        }

        // 跳转到我的优惠券
        return seoService.getCurrSeo(mav);
    }


    /**
     * 商品图片
     * 移动版前端商品详情页点击轮播大图处调用
     *
     * @author houyichang 2015/10/26
     * */
    @RequestMapping("/showBigImage")
     public ModelAndView queryBigImage(Long productId){
        GoodsDetailBean detailBean = this.productService.queryDetailBeanByProductId(productId, 1103L);
        // 重新为图片排序，默认图片放到第一个
        if (null != detailBean && null != detailBean.getProductVo()) {
            List<GoodsImage> imageList = detailBean.getProductVo().getImageList();
            if (CollectionUtils.isNotEmpty(imageList)) {
                for (int i = 0; i < imageList.size(); i++) {
                    if ("1".equals(imageList.get(i).getGoodsImgSort().toString())) {
                        GoodsImage go = imageList.get(0);
                        imageList.set(0, imageList.get(i));
                        imageList.set(i, go);
                    }
                }
                detailBean.getProductVo().setImageList(imageList);
            }
        }
        return new ModelAndView("/goods/goods_detail_pic").addObject("detailBean",detailBean);
    }

    /**
     * 根据货品编号查询商品的评论
     *
     * @return 分页对象
     */
    @RequestMapping("/queryProducCommentForDetailHyc")
    @ResponseBody
    public PageBean queryProductCommentHyc(PageBean pb, Long productId, String title, Character type, String askType) {
        return this.commentServiceMapper.selectCommByGoodsIdHyc(pb, productId, type, title);
    }

    /**
     * 根据货品编号查询商品的评论
     *
     * @return 分页对象
     */
    @RequestMapping("/queryCommentCountByType")
    @ResponseBody
    public int queryCommentCountByType(PageBean pb, Long productId, String title, Character type) {
        return this.commentServiceMapper.queryCommentCountByType(productId, type, title);
    }

    /**
     * 根据货品编号查询商品的评论
     *
     * @return 分页对象
     */
    @RequestMapping("/queryProducCommentDetailForDetailHyc")
    @ResponseBody
    public PageBean queryProductCommentDetailHycCount(PageBean pb, Long productId, String title, Character type, String askType) {
        return this.commentServiceMapper.selectCommDetailByGoodsIdHyc(pb, productId, type, title);
    }

    /**
     * 跳转到商品评价页面
     * */
    @RequestMapping("/showPingLun")
    public ModelAndView showPingLun(Long productId){
        return new ModelAndView("/goods/pro_common").addObject("productId",productId);
    }

    public GoodsProductService getProductService(Long askId) {
        return productService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "HsiteGoodsProductService")
    public void setProductService(GoodsProductService productService) {
        this.productService = productService;
    }

    public GoodsService getGoodsService() {
        return goodsService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "HsiteGoodsService")
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    public GoodsTypeService getGoodsTypeService() {
        return goodsTypeService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "HsiteGoodsTypeService")
    public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }

    public GoodsCateService getGoodsCateService() {
        return goodsCateService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "HsiteGoodsCateService")
    public void setGoodsCateService(GoodsCateService goodsCateService) {
        this.goodsCateService = goodsCateService;
    }

    public MarketingService getMarketingService() {
        return marketingService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "MarketingService")
    public void setMarketingService(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    public SiteGoodsAtteService getGoodsAtteService() {
        return goodsAtteService;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "SiteGoodsAtteService")
    public void setGoodsAtteService(SiteGoodsAtteService goodsAtteService) {
        this.goodsAtteService = goodsAtteService;
    }

    public CommentServiceMapper getCommentServiceMapper() {
        return commentServiceMapper;
    }
    /**
     * Spring注入
     * */
    @Resource(name = "commentServiceMapper")
    public void setCommentServiceMapper(CommentServiceMapper commentServiceMapper) {
        this.commentServiceMapper = commentServiceMapper;
    }



    /**
     * 获取
     * */
    public GoodsOpenSpecService getGoodsOpenSpecService() {
        return goodsOpenSpecService;
    }

    /**
     * 货品规格接口
     * 
     * @param goodsOpenSpecService
     */
    @Resource(name = "GoodsOpenSpecService")
    public void setGoodsOpenSpecService(GoodsOpenSpecService goodsOpenSpecService) {
        this.goodsOpenSpecService = goodsOpenSpecService;
    }



       public static void main(String[] args) {
           String qUrl = "712336176";
           int state = 1243;

           String urlQ = "http://wpa.qq.com/pa?p=2:"+qUrl+":41";

           try {
               URL url = new URL(urlQ);
               HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();

               if(state==httpUrl.getContentLength()){
                   System.out.println(httpUrl.getContentLength());
                   System.out.println("QQ用户：["+qUrl+"]状态为在线");
               }else{
                   System.out.println(httpUrl.getContentLength());
                   System.out.println("QQ用户：["+qUrl+"]状态为离线/隐身");
               }
           }catch (IOException e) {
               System.out.println("连接此URL"+urlQ+"抛出异常信息");
           }
//           while (true) {
//               System.out.println("请输入要查询的QQ号码、输入“0”退出");
//               java.util.Scanner in=new java.util.Scanner(System.in);
//               String qq = in.next();
//               if(qq.equals("0")){
//                   break;
//               }
//               String result = "";
//               String charset = "UTF-8";
//               String callurl = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx/qqCheckOnline?qqCode=";
//               try {
//
//                /*
//                 * 通过输入QQ号码（String）检测QQ在线状态。 返回数据（String）Y = 在线；N = 离线 ；E =
//                 * QQ号码错误
//                 */
//                   java.net.URL url = new java.net.URL(callurl + qq);
//                   java.net.URLConnection connection = url.openConnection();
//                   connection.connect();
//                   java.io.BufferedReader reader = new java.io.BufferedReader(
//                           new java.io.InputStreamReader(connection
//                                   .getInputStream(), charset));
//                   String line;
//                /*
//                 * 返回的格式 <?xml version="1.0" encoding="utf-8"?> <string
//                 * xmlns="http://WebXml.com.cn/">E</string>
//                 */
//                   while ((line = reader.readLine()) != null) {
//                       result += line;
//                       result += "\n";
//                   }
//               } catch (Exception e) {
//                   e.printStackTrace();
//               }
//               int len = result.indexOf("\">");
//               if (len != -1) {
//                   String qStruts = result.substring(len + 2, len + 3);
//                   if (qStruts.equals("E")) {
//                       System.out.println("QQ号码错误");
//                   } else if (qStruts.equals("Y")) {
//                       System.out.println("在线");
//                   } else if (qStruts.equals("N")) {
//                       System.out.println("离线");
//                   }
//               } else {
//                   System.out.println("服务器繁忙、请从试!");
//               }
//           }
     }
}

