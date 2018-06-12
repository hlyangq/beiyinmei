/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 * 
 */
package com.ningpai.temp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ningpai.common.util.CacheKeyConstant;
import com.ningpai.redis.RedisAdapter;
import com.ningpai.temp.bean.SysTemp;
import com.ningpai.temp.dao.SysTempDao;
import com.ningpai.temp.service.TempService;

/**
 * SERVICE实现类-频道用模板
 * 
 * @author NINGPAI-WangHaiYang
 * @since 2014年5月4日下午4:13:32
 */
@Service("TempService")
public class TempServiceImpl implements TempService {

    private static final String DELETE_STATUS = "deleteStatus";
    private static final String START_ROW_NUM = "startRowNum";
    private static final String END_ROW_NUM = "endRowNum";

    private static final Long TEMPTYPE = 137L;

    private static final Long STARTROWNUM = 0L;

    private static final Long ENDROWNUM = 1000L;

    private SysTempDao systempDao;
    
    @Resource
    private RedisAdapter redisAdapter;

    /**
     *
     */
    @Override
    public List<SysTemp> queryAllSystemp() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DELETE_STATUS, "0");
        map.put(START_ROW_NUM, STARTROWNUM);
        map.put(END_ROW_NUM, ENDROWNUM);
        return systempDao.querySysTempByPage(map);
    }

    /**
     * 
     *
     */
    @Override
    public SysTemp getSystempById(Long systempId) {
        return systempDao.getSysTempById(systempId.intValue());
    }

    /**
     * 
     *
     */
    @Override
    public List<SysTemp> querySystempByType(Long typeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tempType", typeId);
        map.put("standby2", "0");
        map.put(DELETE_STATUS, "0");
        map.put(START_ROW_NUM, STARTROWNUM);
        map.put(END_ROW_NUM, ENDROWNUM);
        return systempDao.getSysTempByField(map);
    }

    /**
     * 
     *
     */
    @Override
    public int updateSystemp(SysTemp temp) {
        // 修改模板配置
        // 成功返回1
        // 失败返回0
        return systempDao.updateSysTemp(temp);
    }

    /**
     *
     */
    @Override
    public SysTemp getCurrTemp() 
    {
    	@SuppressWarnings("unchecked")
		List<SysTemp> list = (List<SysTemp>) redisAdapter.get(CacheKeyConstant.CURRSYSTEMP_KEY);
    	
    	if (!CollectionUtils.isEmpty(list))
    	{
    		return list.get(0);
    	}
    	
    	// 首先从redis 中获取 如果没有 则从数据库中查询 然后放入redis
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tempType", TEMPTYPE);
        map.put("standby2", "0");
        map.put("usedStatus", "1");
        map.put(DELETE_STATUS, "0");
        map.put(START_ROW_NUM, STARTROWNUM);
        map.put(END_ROW_NUM, ENDROWNUM);
        list = systempDao.getSysTempByField(map);
        // 将数据放入redis
        redisAdapter.put(CacheKeyConstant.CURRSYSTEMP_KEY,new ArrayList<>(list));
        return list.get(0);
    }

    /**
     *
     */
    @Override
    public void setUserd(Long tempId) {
    	redisAdapter.delete(CacheKeyConstant.CURRSYSTEMP_KEY);
        systempDao.setUserd(tempId);
        systempDao.removeUserd(tempId);
    }

    public SysTempDao getSystempDao() {
        return systempDao;
    }

    @Resource(name = "SysTempDao")
    public void setSystempDao(SysTempDao systempDao) {
        this.systempDao = systempDao;
    }
}
