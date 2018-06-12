package com.ningpai.site.util;

import com.ningpai.common.util.RedisMap;


/**
 * 项目启动的时候 删除redis中所有的缓存
 * @author djk
 *
 */
public class RedisStartUtil 
{
    public void init()
    {
    	RedisMap.deleteAll();
    }
}
