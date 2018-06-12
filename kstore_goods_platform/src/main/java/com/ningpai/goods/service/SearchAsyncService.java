package com.ningpai.goods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 异步操作索引数据Service
 * @author liangck
 * @version 1.0
 * @since 16/4/28 09:38
 */
@Service
public class SearchAsyncService {

    /**
     * 记录日志工具
     */
    private static final Logger LOGGER= LoggerFactory.getLogger(SearchAsyncService.class);

    /**
     * 操作索引数据的service
     */
    @Autowired
    private GoodsElasticSearchService elasticSearchService;

    /** 配置的线程池 **/
    @Resource(name = "threadPool")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 插入 单 个索引
     * @param goodsId
     */
    public void insertOneGoodsIndexToEs(final Long goodsId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.insertOneGoodsIndexToEs(goodsId);
            }
        });
    }

    /**
     * 修改单个商品下货品的索引数据
     * @param goodsId
     * @return
     */
    public void updateOneGoodsIndexToEs(final Long goodsId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.updateOneGoodsIndexToEs(goodsId);
            }
        });
    }

    /**
     * 修改第三方索引数据
     * @param goodsId
     * @return
     */
    public void updateThirdOneGoodsIndexToEs(final Long goodsId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.updateThirdOneGoodsIndexToEs(goodsId);
            }
        });
    }

    /**
     * 删除单个商品下货品的索引数据
     * @param goodsInfoId
     */
    public void deleteGoodsIndexToEs(final Long goodsInfoId) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.deleteGoodsIndexToEs(goodsInfoId);
            }
        });
    }

    /**
     *
     * @param storeInfoIds
     * @return
     */
    public void deleteGoodsByThirdId(final String[] storeInfoIds) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.deleteGoodsByThirdId(storeInfoIds);
            }
        });
    }

    /**
     * 批量删除
     * @param list
     * @return
     */
    public void batchDeleteGoodsIndexToEs(final List<Long> list) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                elasticSearchService.batchDeleteGoodsIndexToEs(list);
            }
        });
    }
}
