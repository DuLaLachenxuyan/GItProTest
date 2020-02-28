package com.trw.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trw.factory.RedisSequenceFactory;
@Component
public class IdGenerate {
	 @Autowired
	 private RedisSequenceFactory mRedisSequenceFactory;
	 
	 /**
	  * 主键生成 6位递增主键
	  * @return
	  */
	public String generateSeq(String prifix) {
		 long v = mRedisSequenceFactory.generate(prifix);
		 String str = String.format("%06d", v);     
		 return prifix + str;
	}
}
