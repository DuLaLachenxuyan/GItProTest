package com.trw.factory;
 
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
@Component
public class RedisSequenceFactory {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
 
	
	/** 
	* @Title: generate 
	* @Description: Atomically increments by one the current value.
	* @param key
	* @return      
	*/  
	public long generate(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return counter.incrementAndGet();
	}	
	
	/** 
	* @Title: generate 
	* @Description: Atomically increments by one the current value.
	* @param key
	* @return      
	*/  
	public long generate(String key,Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.incrementAndGet();	      
	}		
	
	/** 
	* @Title: generate 
	* @Description: Atomically adds the given value to the current value.
	* @param key
	* @param increment
	* @return      
	*/  
	public long generate(String key,int increment) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		return counter.addAndGet(increment);		      
	}
	
	/** 
	* @Title: generate 
	* @Description: Atomically adds the given value to the current value.
	* @param key
	* @param increment
	* @param expireTime
	* @return      
	*/  
	public long generate(String key,int increment,Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.addAndGet(increment);		      
	}	
}
