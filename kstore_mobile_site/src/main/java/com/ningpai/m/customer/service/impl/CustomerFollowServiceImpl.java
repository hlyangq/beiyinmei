/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.goods.bean.ProductWare;
import com.ningpai.goods.service.ProductWareService;
import com.ningpai.m.customer.bean.CustomerFollow;
import com.ningpai.m.customer.mapper.CustomerFollowMapper;
import com.ningpai.m.customer.service.CustomerFollowService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.goods.service.GoodsProductService;
import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.system.service.DefaultAddressService;
import com.ningpai.util.PageBean;

/**
 * @author qiyuanyuan
 */
@Service("customerFollowServiceMSite")
public class CustomerFollowServiceImpl implements CustomerFollowService {

    /**
     * 会员收藏Mapper
     */
    private CustomerFollowMapper customerFollowMapper;

    /**
     * 查询默认地址id
     * */
    @Resource(name = "DefaultAddressService")
    private DefaultAddressService defaultAddressService;

    /**
     * 查询分仓库存
     * */
    @Resource(name = "ProductWareService")
    private ProductWareService productWareService;
    
    @Resource(name="HsiteGoodsProductService")
    private GoodsProductService  goodsProductService;

    private static final String CUSTOMERID="customerId";


    /**
     * 查询收藏记录
     * @param paramMap
     *            查询条件
     * @param pb
     * @return
     */
    @Override
    public PageBean selectCustomerFollow(Long customerId,PageBean pb) {
        Map<String, Object> paramMap = new HashMap<String,Object>();
        pb.setPageSize(15);
        paramMap.put(CUSTOMERID, customerId);
        //查询收藏总数
        Long count = customerFollowMapper
                .selectCustomerFollowCount((Long) paramMap.get(CUSTOMERID));
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        paramMap.put(CustomerConstants.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstants.ENDNUM, pb.getEndRowNum());
        //查询按条件查询消费记录
        List<Object> customerFollows = customerFollowMapper.selectCustFollowByCustId(paramMap);
        if (null != customerFollows && !customerFollows.isEmpty()) {
            for (int i = 0; i < customerFollows.size(); i++) {
                CustomerFollow childCustomerFollow = (CustomerFollow) customerFollows.get(i);
                //根据货品ID获取 该货品的评分
                childCustomerFollow.setUtilBean(customerFollowMapper.queryCommentCountAndScoreByProductId(childCustomerFollow.getGoodsId()));
                //根据货品id查询货品
                GoodsProductVo goodsProductVo  = goodsProductService.queryProductByProductId(childCustomerFollow.getGoodsId());
                if(goodsProductVo!=null){
                    //判断货品是否是第三方货品
                    if(goodsProductVo.getIsThird() !=null && !"".equals(goodsProductVo.getIsThird()) && "1".equals(goodsProductVo.getIsThird())){
                        childCustomerFollow.getGood().setGoodStock(goodsProductVo.getGoodsInfoStock());
                        if(childCustomerFollow.getGood()!=null){

                            childCustomerFollow.getGood().setGoodStock(goodsProductVo.getGoodsInfoStock());
                        }
                    }else{
                        /**
                         * 下面1,2,3条是在前台点击收藏的商品时使用的 houyichag 2015/9/29
                         *
                         * */
                        // 1.查询出后台设置的默认地区
                        Long distinctId = this.defaultAddressService.getDefaultIdService();
                        if(distinctId == null){
                            distinctId = 1103L;
                        }
                        // 2.根据默认地区以及货品id去查询改货品的分仓库存
                        ProductWare productWare = this.productWareService.queryProductWareByProductIdAndDistinctId(childCustomerFollow.getGoodsId(),distinctId);
                        // 3.对customerFollows中的goodBean对象中的库存进行赋值
                        if(childCustomerFollow.getGood()!=null){

                            childCustomerFollow.getGood().setGoodStock(productWare.getWareStock());
                        }
                    }
                }
                //获取货品好评度
                Map<String,Object> paramMap2 = new HashMap<String,Object>();
                paramMap2.put("commentType", "3");
                paramMap2.put("goodsId", childCustomerFollow.getGoodsId());
                //评价总数
                int totalCount  = customerFollowMapper.selectCommByGoodsId(paramMap2);
                paramMap2.put("commentType", "0");
                //好评
                int goodCount = customerFollowMapper.selectCommByGoodsId(paramMap2);
                if(totalCount>0){
                    int goodsCommentCount = (int)((double)goodCount/totalCount*100.0);
                    childCustomerFollow.setProductComment(goodsCommentCount);
                }else{
                    childCustomerFollow.setProductComment(0);
                }
            }
        }
        pb.setList(customerFollows);
        return pb;
    }

    /**
     * 查询收藏记录
     * 根据 会员id以及货品id
     *
     * @author houyichang 2015/11/3
     */
    @Override
    public int selectCUstFollowByCustIdAndProId(Long custId,Long productId) {
        Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put(CUSTOMERID, custId);
        paramMap.put("productId",productId);
        return this.customerFollowMapper.selectCUstFollowByCustIdAndProId(paramMap);
    }

    /**
     * 商品列表专用 查询当前会员是否
     * @param paramMap
     * @return
     */
    @Override
    public List<String> selectCustomerFollowForList(Map<String, Object> paramMap) {
        //查询按条件查询消费记录
        return    customerFollowMapper.selectCustFollowByCustIdForList(paramMap);

    }

    /**
     * 取消关注
     * @param followId
     *            关注编号
     * @param customerId
     * @return
     */
    @Override
    public int deleteFollow(Long followId, Long customerId) {
        Map<String, Object> map = new HashMap<>();
        // 封装参数
        map.put("followId", followId);
        map.put(CUSTOMERID, customerId);
        //根据主键删除
        return customerFollowMapper.deleteByPrimaryKey(map);
    }

    /**
     * 查询我的收藏数目
     * @param customerId 用户id
     * @return int
     */
    @Override
    public Long myCollectionsCount(Long customerId) {
        return customerFollowMapper.selectCustomerFollowCount(customerId);
    }
    
    public CustomerFollowMapper getCustomerFollowMapper() {
        return customerFollowMapper;
    }

    @Resource(name = "customerFollowMapperSite")
    public void setCustomerFollowMapper(
            CustomerFollowMapper customerFollowMapper) {
        this.customerFollowMapper = customerFollowMapper;
    }


}
