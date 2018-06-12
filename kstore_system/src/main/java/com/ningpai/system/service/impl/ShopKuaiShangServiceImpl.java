/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.system.service.impl;

import com.ningpai.common.util.CacheKeyConstant;
import com.ningpai.redis.RedisAdapter;
import com.ningpai.system.bean.ShopKuaiShang;
import com.ningpai.system.dao.ShopKuaiShangMapper;
import com.ningpai.system.service.ShopKuaiShangService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 快商通实现类
 */
@Service("ShopKuaiShangService")
public class ShopKuaiShangServiceImpl implements ShopKuaiShangService {
    @Resource(name = "ShopKuaiShangMapper")
    private ShopKuaiShangMapper shopKuaiShangMapper;
    
    @Resource
    private RedisAdapter redisAdapter;

    @Override
    public int insertKuaiShang(ShopKuaiShang shopKuaiShang) {
    	redisAdapter.delete(CacheKeyConstant.SHOPKUAISHANG_KEY);
        return shopKuaiShangMapper.insertKuaiShang(shopKuaiShang);
    }

    @Override
    public ShopKuaiShang selectInitone() {
    	
    	ShopKuaiShang shopKuaiShang = (ShopKuaiShang) redisAdapter.get(CacheKeyConstant.SHOPKUAISHANG_KEY);
    	
    	if (null != shopKuaiShang)
    	{
    		return shopKuaiShang;
    	}
    	
    	shopKuaiShang = shopKuaiShangMapper.selectInitone();

        if(shopKuaiShang != null){
            redisAdapter.put(CacheKeyConstant.SHOPKUAISHANG_KEY, shopKuaiShang);
        }

        return shopKuaiShang;
    }

    @Override
    public int updateKuaiShangByPrimaryKey(ShopKuaiShang shopKuaiShang) {
    	// 有变化 通知内存刷新
    	redisAdapter.delete(CacheKeyConstant.SHOPKUAISHANG_KEY);
        return shopKuaiShangMapper.updateKuaiShangByPrimaryKey(shopKuaiShang);
    }
}
