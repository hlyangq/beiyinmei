/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.m.goods.util;

/**
 * 
 * 商品列表页评论帮助Bean
 * 
 * @author NINGPAI-YuanKangKang
 * @since 2014年4月9日 下午4:10:29
 * @version 1.0
 */
public class ProductCommentUtilBean {
    /**
     * 评分个数
     */
    private String count = "0";

    /**
     * 评分的平均数
     */
    private String score = "0";

    /**
     * 好评数量
     */
    private String goodCount="0";

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public ProductCommentUtilBean setGoodCount(String goodCount) {
        this.goodCount = goodCount;
        return this;
    }
}
