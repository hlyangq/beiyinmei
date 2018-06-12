/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ningpai.goods.bean.Goods;
import com.ningpai.goods.bean.GoodsImage;
import com.ningpai.goods.bean.ProductWare;
import com.ningpai.m.goods.util.ProductCommentUtilBean;

/**
 * 货品信息Vo
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2013年12月27日 下午5:14:20
 * @version 1.0
 */
public class GoodsProductVo {
    /**
     *主键ID
      */
    private Long goodsInfoId;
    /**
     *商品ID
      */
    private Long goodsId;
    /**
     *货号
      */
    private String goodsInfoItemNo;
    /**
     *货品名称
      */
    private String productName;
    /**
     *货品副标题
      */
    private String subtitle;
    /**
     *是否上架 默认1上架
      */
    private String goodsInfoAdded;
    /**
     *上架时间
      */
    private Date goodsInfoAddedTime;
    /**
     *货品库存
      */
    private Long goodsInfoStock;
    /**
     *销售价
      */
    private BigDecimal goodsInfoPreferPrice;
    /**
     *成本价
      */
    private BigDecimal goodsInfoCostPrice;
    /**
     *市场价
      */
    private BigDecimal goodsInfoMarketPrice;
    /**
     *重量
      */
    private BigDecimal goodsInfoWeight;
    /**
     *货品初始图片
      */
    private String goodsInfoImgId;
    /**
     *删除标记
      */
    private String goodsInfoDelflag;
    /**
     *关联的规格值的集合
      */
    private List<GoodsProductReleSpecVo> specVo;
    /**
     *创建时间
      */
    private Date goodsInfoCreateTime;
    /**
     *货品图片的集合
      */
    private List<GoodsImage> imageList;
    /**
     *货品所属的商品
      */
    private Goods goods;
    /**
     *评分帮助
      */
    private ProductCommentUtilBean commentUtilBean;
    /**
     *第三方ID
      */
    private Long thirdId;
    /**
     *第三方店铺名称
      */
    private String thirdName;
    /**
     *第三方标记 0:不是第三方商品 1:第三方商品 2:B2B商品
      */
    private String isThird;
    /**
    *1卖家包邮 0,买家自费
     */
    private Long isMailBay;

    /**
    *是否在手机端显示
     */
    private String showMobile;

    /**
     * 参加促销的Id
     */
    private Long marketingId;

    /**
     * 套装货品数量
     */
    private BigDecimal groupProductNum;

    /*
    *分仓的数据
    */
    private List<ProductWare> productWares;

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public String getShowMobile() {
        return showMobile;
    }

    public void setShowMobile(String showMobile) {
        this.showMobile = showMobile;
    }

    public Long getIsMailBay() {
        return isMailBay;
    }

    public void setIsMailBay(Long isMailBay) {
        this.isMailBay = isMailBay;
    }
    public List<GoodsImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<GoodsImage> imageList) {
        this.imageList = imageList;
    }

    public Long getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(Long goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsInfoItemNo() {
        return goodsInfoItemNo;
    }

    public void setGoodsInfoItemNo(String goodsInfoItemNo) {
        this.goodsInfoItemNo = goodsInfoItemNo;
    }

    public String getGoodsInfoAdded() {
        return goodsInfoAdded;
    }

    public void setGoodsInfoAdded(String goodsInfoAdded) {
        this.goodsInfoAdded = goodsInfoAdded;
    }

    public Long getGoodsInfoStock() {
        return goodsInfoStock;
    }

    public void setGoodsInfoStock(Long goodsInfoStock) {
        this.goodsInfoStock = goodsInfoStock;
    }

    public BigDecimal getGoodsInfoPreferPrice() {
        return goodsInfoPreferPrice;
    }

    public void setGoodsInfoPreferPrice(BigDecimal goodsInfoPreferPrice) {
        this.goodsInfoPreferPrice = goodsInfoPreferPrice;
    }

    public BigDecimal getGoodsInfoCostPrice() {
        return goodsInfoCostPrice;
    }

    public void setGoodsInfoCostPrice(BigDecimal goodsInfoCostPrice) {
        this.goodsInfoCostPrice = goodsInfoCostPrice;
    }

    public BigDecimal getGoodsInfoMarketPrice() {
        return goodsInfoMarketPrice;
    }

    public void setGoodsInfoMarketPrice(BigDecimal goodsInfoMarketPrice) {
        this.goodsInfoMarketPrice = goodsInfoMarketPrice;
    }

    public BigDecimal getGoodsInfoWeight() {
        return goodsInfoWeight;
    }

    public void setGoodsInfoWeight(BigDecimal goodsInfoWeight) {
        this.goodsInfoWeight = goodsInfoWeight;
    }

    public String getGoodsInfoImgId() {
        return goodsInfoImgId;
    }

    public void setGoodsInfoImgId(String goodsInfoImgId) {
        this.goodsInfoImgId = goodsInfoImgId;
    }

    public String getGoodsInfoDelflag() {
        return goodsInfoDelflag;
    }

    public void setGoodsInfoDelflag(String goodsInfoDelflag) {
        this.goodsInfoDelflag = goodsInfoDelflag;
    }

    public List<GoodsProductReleSpecVo> getSpecVo() {
        return specVo;
    }

    public void setSpecVo(List<GoodsProductReleSpecVo> specVo) {
        this.specVo = specVo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public ProductCommentUtilBean getCommentUtilBean() {
        return commentUtilBean;
    }

    public void setCommentUtilBean(ProductCommentUtilBean commentUtilBean) {
        this.commentUtilBean = commentUtilBean;
    }
    /**
     * 获取创建时间
     * */
    public Date getGoodsInfoCreateTime() {
        if (this.goodsInfoCreateTime != null) {
            return new Date(this.goodsInfoCreateTime.getTime());
        }
        return null;
    }
    /**
     * 设置创建时间
     * */
    public void setGoodsInfoCreateTime(Date goodsInfoCreateTime) {
        if (goodsInfoCreateTime != null) {
            Date tEmp = (Date) goodsInfoCreateTime.clone();
            if (tEmp != null) {
                this.goodsInfoCreateTime = tEmp;
            }
        }
    }
    /**
     * 获取添加时间
     * */
    public Date getGoodsInfoAddedTime() {
        if (this.goodsInfoAddedTime != null) {
            return new Date(this.goodsInfoAddedTime.getTime());
        }
        return null;
    }
    /**
     * 设置添加时间
     * */
    public void setGoodsInfoAddedTime(Date goodsInfoAddedTime) {
        if (goodsInfoAddedTime != null) {
            Date tEmp = (Date) goodsInfoAddedTime.clone();
            if (tEmp != null) {
                this.goodsInfoAddedTime = tEmp;
            }
        }
    }


    /**
     * 给详情添加样式
     */
    public void addStyleForDesc() {
        if (null != goods) {
            goods.addStyleForDesc();
            goods.addStyleForMobileDesc();
        }
    }

    public Long getThirdId() {
        return thirdId;
    }

    public void setThirdId(Long thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getIsThird() {
        return isThird;
    }

    public void setIsThird(String isThird) {
        this.isThird = isThird;
    }

    public BigDecimal getGroupProductNum() {
        return groupProductNum;
    }

    public void setGroupProductNum(BigDecimal groupProductNum) {
        this.groupProductNum = groupProductNum;
    }

    public List<ProductWare> getProductWares() {
        return productWares;
    }

    public void setProductWares(List<ProductWare> productWares) {
        this.productWares = productWares;
    }
}
