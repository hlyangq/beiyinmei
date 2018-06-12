package com.ningpai.goods.bean;

/**
 *
 * 商品评价
 *
 * @author lih
 *
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
     * 好评
     */
    private String  goodCount="0";

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

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }
}
