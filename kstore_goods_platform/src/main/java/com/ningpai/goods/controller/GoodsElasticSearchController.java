package com.ningpai.goods.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ningpai.goods.service.GoodsElasticSearchService;

/**
 * 创建商品索引ES
 * @author ggn
 */
@Controller
public class GoodsElasticSearchController {

    // Spring注入
    @Resource(name = "GoodsElasticSearchService")
    private GoodsElasticSearchService goodsElasticSearchServivice;

    /**
     * 插入索引
     */
    @RequestMapping("/insertelasticgoods")
    @ResponseBody
    public int insertLasticGoods() {
        return goodsElasticSearchServivice.createGoodsIndexToEs();
    }

}
