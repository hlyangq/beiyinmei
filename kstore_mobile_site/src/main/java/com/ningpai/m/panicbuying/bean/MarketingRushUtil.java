/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.panicbuying.bean;

import com.ningpai.m.goods.vo.GoodsProductVo;
import com.ningpai.marketing.bean.Marketing;

/**
 * 抢购秒杀实体
 *
 * @author  djk
 */
public class MarketingRushUtil {
     /**
     * 抢购秒杀商品
     */
    private GoodsProductVo goodsProductVo;

    /**
     * 抢购秒杀优惠信息
     */
    private Marketing marketing;

    /**
     * 判断抢购状态
     */
    private String rushTime;
    
    public String getRushTime() {
        return rushTime;
    }

    public void setRushTime(String rushTime) {
        this.rushTime = rushTime;
    }

    public GoodsProductVo getGoodsProductVo() {
        return goodsProductVo;
    }

    public void setGoodsProductVo(GoodsProductVo goodsProductVo) {
        this.goodsProductVo = goodsProductVo;
    }

    public Marketing getMarketing() {
        return marketing;
    }

    public void setMarketing(Marketing marketing) {
        this.marketing = marketing;
    }
}
