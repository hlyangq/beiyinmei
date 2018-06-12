package com.ningpai.redis;

import java.io.Serializable;

import com.ningpai.common.util.RedisMap;

/**
 * redis的适配器
 * 
 * 完全 是为了  单元测试而做的 。。。。。。。
 * @author djk
 *
 */
public class RedisAdapter 
{
	/**
	 * 删除所有缓存
	 */
	public void deleteAll()
	{
		RedisMap.deleteAll();
	}
	
	/**
	 * 根据key删除缓存
	 * @param key
	 * @return
	 */
	public  void delete(final String key)
	{
		RedisMap.delete(key);
	}
	
	/**
	 * 保存数据到redis中
	 */
	public boolean put(final String key,final Serializable value) 
	{
		return RedisMap.put(key, value);
	}
	
	/**
	 * 从redis 中查询数据
	 */
	public Object get(final String key) 
	{
		return RedisMap.get(key);
	}
}
