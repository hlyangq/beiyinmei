package com.ningpai.m.shoppingcart.bean;

/**
 * GoodsStockBean
 * 库存实体类
 *
 * @author djk
 * @date 2015/12/14
 */
public class GoodsStockBean {

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 库存数
     */
    private String stockNum;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    @Override
    public String toString() {
        return "GoodsStockBean{" +
                "goodsId=" + goodsId +
                ", stockNum='" + stockNum + '\'' +
                '}';
    }
}
