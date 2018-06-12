/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.Browserecord;
import com.ningpai.m.customer.mapper.BrowserecordMapper;
import com.ningpai.m.customer.service.BrowserecordService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;

/**
 * 浏览记录Service
 * @author qiyuanyuan
 * @version
 */
@Service("mbrowserecordService")
public class BrowserecordServiceImpl implements BrowserecordService {
    private static final String COOKIE = "_npstore_browpro";
    private static final String CUSTOMERID="customerId";
    
    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(BrowserecordServiceImpl.class);

    /**
     * 浏览记录
     */
    private BrowserecordMapper browserecordMapper;
    
    /**
     * 查询分仓库存
     * */
    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;
    
    @Resource(name="HsiteGoodsProductService")
    private GoodsProductService  goodsProductService;
    
    /**
     * 查询默认地址id
     * */
    @Resource(name = "DefaultAddressService")
    private DefaultAddressService defaultAddressService;

    /**
     * 查询浏览记录
     * @param customerId
     *            会员编号
     * @return
     */
    @Override
    public PageBean selectBrowserecord(Long customerId,PageBean pb) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put(CUSTOMERID, customerId);
        paramMap.put("isMobile", 1);
        Long rows = browserecordMapper.selectBrowserecordCount(paramMap);
        if(rows!=null && rows>0){
            pb.setRows(rows.intValue());
        }else{
            pb.setRows(0);
        }
        pb.setPageSize(5);
        paramMap.put(CustomerConstants.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstants.ENDNUM, pb.getEndRowNum());
        List<Object> browereList = browserecordMapper.selectBrowserecord(paramMap);
        if(browereList != null && !browereList.isEmpty()){
            for(int i = 0;i < browereList.size();i++){
                Browserecord browserecord = (Browserecord) browereList.get(i);
              //根据货品id查询货品
                GoodsProductVo goodsProductVo  = goodsProductService.queryProductByProductId(browserecord.getGoodsId().longValue());
                if(goodsProductVo!=null){
                   //判断货品是否是第三方货品
                    if(goodsProductVo.getIsThird() !=null && !"".equals(goodsProductVo.getIsThird()) && "1".equals(goodsProductVo.getIsThird())){
                        if(browserecord.getGoods()!=null){

                            browserecord.getGoods().setGoodStock(goodsProductVo.getGoodsInfoStock());
                        }
                    }else{
                        // 1.查询出后台设置的默认地区
                        Long distinctId = this.defaultAddressService.getDefaultIdService();
                        if(distinctId == null){
                            distinctId = 1103L;
                        }
                        // 2.根据默认地区以及货品id去查询改货品的分仓库存
                        ProductWare productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(browserecord.getGoodsId().longValue(),distinctId);
                        // 3.对customerFollows中的goodBean对象中的库存进行赋值
                        if(browserecord.getGoods()!=null){

                            browserecord.getGoods().setGoodStock(productWare.getWareStock());
                        }
                    }
                }
            }
        }
        pb.setList(browereList);
        return pb;
    }

    /**
     * 根据主键删除
     * @param likeId
     *            浏览编号
     * @param customerId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long likeId, Long customerId) {
        // 创建封装容器
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("likeId", likeId);
        map.put(CUSTOMERID, customerId);
        // 删除浏览记录
        return browserecordMapper.deleteByPrimaryKey(map);
    }
    /**
     * 根据货品编号删除
     *
     * @param goodInfoId
     *           货品编号
     * @return 0失败 1成功
     */
    @Override
    public int deleteByGoodsInfoId(Long goodInfoId, Long customerId) {
        // 创建封装容器
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("goodInfoId", goodInfoId);
        map.put(CUSTOMERID, customerId);
        // 删除浏览记录
        return browserecordMapper.deleteByPrimaryKey(map);
    }

    public BrowserecordMapper getBrowserecordMapper() {
        return browserecordMapper;
    }

    /**
     * 添加浏览记录
     * @param request
     * @param response
     * @param productId
     * @return
     */
    @Override
    public int addBrowerecord(HttpServletRequest request,
            HttpServletResponse response, Long productId) {
        int addFlag =0;
        // 取出当期那登陆的用户ID
        Long custId = (Long) request.getSession().getAttribute(CUSTOMERID);
        Cookie cookie;
        String allBroPro = "";
        boolean cookieExits = false;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (null != custId) {
                map.put(CUSTOMERID, custId);
                map.put("goodsId", productId);
                //判断该浏览记录是否存在
                Browserecord browserecord = browserecordMapper.selectByBrowereId(map);
                if(browserecord!=null){
                    browserecord.setCreateTime(new Date());
                    browserecord.setCustomerId(custId);
                    browserecord.setGoodsId(productId.intValue());
                    browserecord.setIsMobile("1");
                    browserecord.setDelFlag("0");
                    addFlag=browserecordMapper.updataBrowereById(browserecord);
                }else{
                    browserecord = new Browserecord();
                    browserecord.setCustomerId(custId);
                    browserecord.setGoodsId(productId.intValue());
                    browserecord.setCreateTime(new Date());
                    browserecord.setDelFlag("0");
                    browserecord.setIsMobile("1");
                    addFlag=browserecordMapper.insertSelective(browserecord);  
                }
            } else {
                // 先取出来cookie中的值,然后拼接,重新保存到cookie中
                Cookie[] oldCookie = request.getCookies();
                if (null != oldCookie) {
                    for (Cookie cookie2 : oldCookie) {
                        if (null != cookie2 && COOKIE.equals(cookie2.getName())) {
                            try {
                                allBroPro = URLDecoder.decode(cookie2.getValue(), ConstantUtil.UTF);
                            } catch (UnsupportedEncodingException e) {
                                LOGGER.info(e);
                            }
                            if (allBroPro.indexOf(",s" + productId + "e") != -1) {
                                allBroPro = allBroPro.replace(",s" + productId + "e", "");
                            }
                            if (allBroPro.indexOf(",s" + productId) != -1) {
                                allBroPro = allBroPro.replace(",s" + productId, "");
                            }
                            // 说明存储浏览记录的cookie已经存在
                            cookieExits = true;
                        }
                    }
                    // 如果存在标记为false
                    allBroPro += ",s" + productId + "e";
                }
                // 如果存储浏览记录的cookie不存在
                if (!cookieExits) {
                    allBroPro = String.valueOf(",s" + productId + "e");
                }
                // 如果当前用户是未登录写状态就保存浏览记录到cookie中
                try {
                    cookie = new Cookie(COOKIE, URLEncoder.encode(allBroPro, ConstantUtil.UTF));
                    cookie.setMaxAge(15 * 24 * 3600);
                    // 保存Cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.info("");
                }
            }
            return addFlag;
        } finally {
            custId = null;
            allBroPro = null;
            map = null;
        }
    }
    
    /**
     * 查询浏览记录数目
     * @param customerId
     *          查询参数
     * @return Long
     */
    @Override
    public Long browereCount(Long customerId) {
        //查询参数Map集合
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //用户id
        paramMap.put(CUSTOMERID, customerId);
        //是否是手机端
        paramMap.put("isMobile", "1");
        //执行查询浏览记录数目操作
        return browserecordMapper.selectBrowserecordCount(paramMap);
        
    }
    
    /**
     * 从cookie添加浏览记录
     * 
     * @param request
     * 
     * @return int
     */
    @Override
    public int loadBrowerecord(HttpServletRequest request) {
        //获取用户Id
        Long custId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        //浏览集合
        List<Browserecord> browserecords = null;
        //添加标记
        int addFlag = 0;
        try {
            //执行加载cookie里面的浏览记录操作
            browserecords = loadBrowserecords(request);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("加载cookie中的浏览信息错误" + e);
        }
        //判断加载记录是否为空
        if(browserecords != null && !browserecords.isEmpty()){
            //遍历浏览集合
            for(Browserecord browserecord:browserecords){
                //setCustomerId属性值
                browserecord.setCustomerId(custId);
                //删除标记值
                browserecord.setDelFlag("0");
                //是否是手机端浏览
                browserecord.setIsMobile("1");
                //查询参数Map集合
                Map<String, Object> paramMap = new HashMap<>();
                //用户id
                paramMap.put(CUSTOMERID, custId);
                //货品id
                paramMap.put("goodsId", browserecord.getGoodsId());
                //判断该浏览记录是否存在
                Browserecord browserecord2 = browserecordMapper.selectByBrowereId(paramMap);
                if(browserecord2!=null){
                    //该浏览记录已存在 更新记录
                    browserecord.setLikeId(browserecord2.getLikeId());
                    browserecord.setCreateTime(new Date());
                    //执行更新记录操作
                    addFlag = browserecordMapper.updataBrowereById(browserecord);
                }else{
                    //不存在 则插入一条新数据
                    browserecord.setCreateTime(new Date());
                    //执行插入数据的操作
                    addFlag = browserecordMapper.insertSelective(browserecord);  
                }
                
            }
        }
        //返回添加标记
        return addFlag;
    }
    
    /**
     * 加载cookie中的
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    public List<Browserecord> loadBrowserecords(HttpServletRequest request) throws UnsupportedEncodingException{
        //浏览list
        List<Browserecord> browserecords = new ArrayList<Browserecord>();
        Cookie[] cookies = request.getCookies();
        String brow = "";
        String[] brows = null;
        String browere = "";
        Browserecord browserecord = null;
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie != null && COOKIE.equals(cookie.getName()) && cookie.getValue()!=null && !"".equals(cookie.getValue())){
                    brow = URLDecoder.decode(cookie.getValue(),ConstantUtil.UTF);
                    brow = brow.substring(0, brow.length());
                    brow = brow.substring(1,brow.length()-1);
                    brows = brow.split("e,");
                  if(brows != null && brows.length > 0){
                        for(int i = 0;i < brows.length;i++){
                            browserecord  = new Browserecord();
                            browere = brows[i].substring(1,brows[i].length());
                            browserecord.setGoodsId(Integer.parseInt(browere));
                            browserecords.add(browserecord);
                        }
                    }
                    
                }
            }
        }
        return browserecords;
    }
    @Resource(name = "browserecordMapper")
    public void setBrowserecordMapper(BrowserecordMapper browserecordMapper) {
        this.browserecordMapper = browserecordMapper;
    }



   

}
