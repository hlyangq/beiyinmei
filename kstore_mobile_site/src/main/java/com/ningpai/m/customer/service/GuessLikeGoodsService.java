package com.ningpai.m.customer.service;

import java.util.List;

import com.ningpai.goods.bean.GoodsProduct;

/**
 * 猜你喜欢业务层接口
 * @author zhangwenchang
 *
 */
public interface GuessLikeGoodsService {
    
    /**
     * 计算猜你喜欢商品
     * @return
     */
   List<GoodsProduct> guessLikeGoodsList(Long customerId);
}
