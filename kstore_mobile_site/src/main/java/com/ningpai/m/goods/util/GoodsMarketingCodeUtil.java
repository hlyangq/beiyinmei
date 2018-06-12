package com.ningpai.m.goods.util;

/**
 * 商品参与促销类型
 * @author lih
 * @version 2.0
 * @since 15/10/26
 */
public class GoodsMarketingCodeUtil {
    /**
     * 促销id
     */
    private Long marketingId;

    /**
     * 类型id
     */
    private Long codexId;

    public Long getMarketingId() {
        return marketingId;
    }

    public GoodsMarketingCodeUtil setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
        return this;
    }

    public Long getCodexId() {
        return codexId;
    }

    public GoodsMarketingCodeUtil setCodexId(Long codexId) {
        this.codexId = codexId;
        return this;
    }
}
