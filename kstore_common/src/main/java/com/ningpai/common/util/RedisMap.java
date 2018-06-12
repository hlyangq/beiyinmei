package com.ningpai.common.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.util.CollectionUtils;

import com.ningpai.util.MyLogger;

/**
 * redis 工具类
 * @author djk
 *
 */
public class RedisMap
{
	
    private static final MyLogger DEBUG = new MyLogger(RedisMap.class);
	/**
	 * 注入redis的模板
	 */
	@SuppressWarnings("unchecked")
	@Resource
	private  static RedisTemplate<String, Serializable> redisTemplate = (RedisTemplate<String, Serializable>) ApplicationContextHelper.getBean("redisTemplate");
	
	/**
	 * 是否需要redis
	 */
	private static boolean isNeedRedis = false;
	
	static
	{
		try
		{
			Properties properties = new Properties();
			properties.load(RedisMap.class.getClassLoader().getResourceAsStream("com/ningpai/web/config/redis.properties"));
			String value = (String) properties.get("redis.start");
			
			if ("yes".equals(value))
			{
				isNeedRedis = true;
			}
			
			DEBUG.debug("Load redis success and redis start flag :" +isNeedRedis);
			
		} catch (Exception e) 
		{
			DEBUG.error("Load redis.properties fail",e);
		}
	}
	
	/**
	 * 删除所有缓存
	 */
	public static void deleteAll()
	{
		// 如果redis 不需要 则直接返回
		if (!isNeedRedis)
		{
			return;
		}
		
		// 活动所有缓存的key值
		List<String> keys = getAllCacheKeys();
		
		DEBUG.debug("Begin to delete keys :" +keys);
		
		if (!CollectionUtils.isEmpty(keys))
		{
			try 
			{
				redisTemplate.delete(new ArrayList<String>(keys));
			} catch (Exception e) 
			{
				DEBUG.error("DeleteAll Fail..."+keys);
			}
		}
		
	}
	
	/**
	 * 获得所有缓存的key
	 * @return
	 */
	private static List<String> getAllCacheKeys()
	{
		List<String> keys = new ArrayList<String>();
		
		Class<CacheKeyConstant> class1 = CacheKeyConstant.class;
		
		Field[] fields = class1.getDeclaredFields();
		
		for (Field field : fields)
		{
			keys.add(field.getName());
		}
		
		return keys;
		
	}
	
	
	/**
	 * 根据key删除缓存
	 * @param key
	 * @return
	 */
	public static void delete(final String key)
	{
		// 如果redis 不需要 则直接返回
		if (!isNeedRedis)
		{
			return;
		}
		try {
			redisTemplate.delete(key);
		} catch (Exception e) 
		{
			DEBUG.error("Delete cache fail and key : "+key);
		}
	}
	
	/**
	 * 保存数据到redis中
	 */
	public static boolean put(final String key,final Serializable value) 
	{
		// 如果redis 不需要 则直接返回
		if (!isNeedRedis)
		{
			return true;
		}
		
		try 
		{
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.set(redisTemplate.getStringSerializer().serialize(key), new JdkSerializationRedisSerializer().serialize(value));
					return true;
				}
			});
		} catch (Exception e) 
		{
			DEBUG.error("Put value to redis fail...",e);
		}
		
		return false;
	}
	
	/**
	 * 从redis 中查询数据
	 */
	public static Object get(final String key) 
	{
		// 如果redis 不需要 则直接返回
		if (!isNeedRedis)
		{
			return null;
		}
		
		try {
			return redisTemplate.execute(new RedisCallback<Object>() {
				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					return  new JdkSerializationRedisSerializer().deserialize(connection.get(redisTemplate.getStringSerializer().serialize(key)));
				}
			});
		} catch (Exception e) {
			DEBUG.error("Get value from  redis fail...",e);
		}
		return null;
	}
}
