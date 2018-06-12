/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ningpai.goods.vo.GoodsProductReleSpecVo;
import com.ningpai.m.goods.util.GoodsMarketingCodeUtil;

/**
 * 商品Bean
 * 
 * @author qiyuanyuan
 */
public class GoodsBean {
    /*
     *  商品编号
     */
    private Long goodsId;
    /*
     *  商品名称
     */
    private String goodsName;
    /*
     *  商品价格
     */
    private BigDecimal goodsPrice;
    /*
     *  商品图片
     */
    private String goodsImg;
    /*
     *  商品数量
     */
    private Long goodsNum;
    /**
     * 商品积分
     * 
     * @see #getIntegral()
     * @see #setIntegral(Long)
     */
    private Long integral;
    /**
     * 市场价
     * 
     * @see #goodsMarketPrice
     * @see #setGoodsMarketPrice(BigDecimal)
     */
    private BigDecimal goodsMarketPrice;
    /**
     * 库存
     * 
     * @see #getGoodStock()
     * @see #setGoodStock(Long)
     */
    private Long goodStock;
    /*
     *  评价数量
     */
    private Long commentCount;
    /*
     *  咨询数量
     */
    private Long consultCount;
    /*
     *  是否已经评价
     */
    private String evaluateFlag;
    /*
     *  评价编号
     */
    private Long commentId;
    /*
     *  订单商品编号
     */
    private Long orderGoodsId;
    /*
     *  货品编号
     */
    private String goodsNo;
    /*
     *  评论标签
     */
    private String commentTag;
    /*
     *  货品总评分
     */
    private Long goodsScore;

    /*
     *  订单Id
     */
    private Long orderId;
    
    /*
     * 商品评分
     */
    private BigDecimal commentScore;
    
   /*
    * 商品评价内容
    */
    private String commentContent;
    
    /*
     *  关联的规格值的集合
     */
    private List<GoodsProductReleSpecVo> specVo;

    /*
     * 促销类型
     */
    List<GoodsMarketingCodeUtil> codeUtils;

    /**
     * 是否匿名
     */
    private String isAnonymous;

    /**
     * 分类id
     */
    private Long catId;

    /**
     * 品牌id
     */
    private Long brandId;

    //退货时可退金额
    private BigDecimal goodsBackPrice;

    public BigDecimal getGoodsBackPrice() {
        return goodsBackPrice;
    }

    public void setGoodsBackPrice(BigDecimal goodsBackPrice) {
        this.goodsBackPrice = goodsBackPrice;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public List<GoodsMarketingCodeUtil> getCodeUtils() {
        return codeUtils;
    }

    public void setCodeUtils(List<GoodsMarketingCodeUtil> codeUtils) {
        this.codeUtils = codeUtils;
    }

    public List<GoodsProductReleSpecVo> getSpecVo() {
        return specVo;
    }

    public void setSpecVo(List<GoodsProductReleSpecVo> specVo) {
        this.specVo = specVo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsScore() {
        return goodsScore;
    }

    public void setGoodsScore(Long goodsScore) {
        this.goodsScore = goodsScore;
    }

    public String getCommentTag() {
        return commentTag;
    }

    public void setCommentTag(String commentTag) {
        this.commentTag = commentTag;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getEvaluateFlag() {
        return evaluateFlag;
    }

    public void setEvaluateFlag(String evaluateFlag) {
        this.evaluateFlag = evaluateFlag;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(Long consultCount) {
        this.consultCount = consultCount;
    }

    public Long getGoodStock() {
        return goodStock;
    }

    public void setGoodStock(Long goodStock) {
        this.goodStock = goodStock;
    }

    public BigDecimal getGoodsMarketPrice() {
        return goodsMarketPrice;
    }

    public void setGoodsMarketPrice(BigDecimal goodsMarketPrice) {
        this.goodsMarketPrice = goodsMarketPrice;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Long getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Long goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(BigDecimal commentScore) {
        this.commentScore = commentScore;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    /**
     * 是否是赠品
     */
    private String isPresent;

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}
