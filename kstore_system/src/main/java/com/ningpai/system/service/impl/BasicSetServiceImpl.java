/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.common.util.CacheKeyConstant;
import com.ningpai.redis.RedisAdapter;
import com.ningpai.system.bean.BasicSet;
import com.ningpai.system.dao.BasicSetMapper;
import com.ningpai.system.service.BasicSetService;

/**
 * 站点设置服务层实现类
 * 
 * @author NINGPAI-LiHaoZe
 * @since 2013年12月16日 下午5:02:54
 * @version 1.0
 */
@Service("basicSetService")
public class BasicSetServiceImpl implements BasicSetService {
    /** spring注解 */
    @Resource(name = "basicSetMapper")
    private BasicSetMapper basicSetMapper;
    
    @Resource
    private RedisAdapter redisAdapter;

    /**
     * 查询站点信息
     * 
     * @return BasicSet
     */
    public BasicSet findBasicSet() {
    	
    	BasicSet basicSet =	(BasicSet) redisAdapter.get(CacheKeyConstant.BASICSET_KEY);
    	
    	if (null != basicSet)
    	{
    		return basicSet;
    	}
    	
    	basicSet = basicSetMapper.findBasicSet();
    	
    	redisAdapter.put(CacheKeyConstant.BASICSET_KEY, basicSet);
    	
        return basicSet;
    }

    /**
     * 修改配置
     * 
     * @param basicSet
     * @return int
     */
    public int updateBasicSet(BasicSet basicSet) {
    	redisAdapter.delete(CacheKeyConstant.BASICSET_KEY);
        return basicSetMapper.updateByPrimaryKeySelective(basicSet);
    }


    /**
     * 查看店铺街的开启状态
     *
     * @return
     */
    @Override
    public String getStoreStatus() {
        return basicSetMapper.getStoreStatus();
    }


}
