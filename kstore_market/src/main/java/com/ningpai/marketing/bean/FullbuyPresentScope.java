package com.ningpai.marketing.bean;

import com.ningpai.goods.bean.GoodsProduct;
import com.ningpai.goods.vo.GoodsProductReleSpecVo;

import java.util.Date;
import java.util.List;

/**
 * 满赠赠品范围表 2016-07-25
 * @author fb
 */
public class FullbuyPresentScope {
    /**
     * 主键id
     */
    private Long presentScopeId;

    /**
     * 赠品范围类型0商品1优惠券
     */
    private String scopeType;

    /**
     * 赠品范围id
     */
    private Long scopeId;

    /**
     * 删除标识0未删除1已删除
     */
    private String delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 赠送数量
     */
    private Long scopeNum;

    /**
     * 满赠规则id
     */
    private Long fullbuyPresentMarketingId;

    /**
     * 赠品详情
     */
    private GoodsProduct goodsProduct;

    /**
     * 货品信息关联规格信息
     */
    private List<GoodsProductReleSpecVo> specVoList;

    public Long getPresentScopeId() {
        return presentScopeId;
    }

    public void setPresentScopeId(Long presentScopeId) {
        this.presentScopeId = presentScopeId;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return  this.createTime ==null?null:(Date) createTime.clone();

    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getScopeNum() {
        return scopeNum;
    }

    public void setScopeNum(Long scopeNum) {
        this.scopeNum = scopeNum;
    }

    public Long getFullbuyPresentMarketingId() {
        return fullbuyPresentMarketingId;
    }

    public void setFullbuyPresentMarketingId(Long fullbuyPresentMarketingId) {
        this.fullbuyPresentMarketingId = fullbuyPresentMarketingId;
    }

    public GoodsProduct getGoodsProduct() {
        return goodsProduct;
    }

    public void setGoodsProduct(GoodsProduct goodsProduct) {
        this.goodsProduct = goodsProduct;
    }

    public List<GoodsProductReleSpecVo> getSpecVoList() {
        return specVoList;
    }

    public void setSpecVoList(List<GoodsProductReleSpecVo> specVoList) {
        this.specVoList = specVoList;
    }
}