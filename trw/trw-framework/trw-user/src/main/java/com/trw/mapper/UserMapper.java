package com.trw.mapper;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.trw.model.TrwTUser;

/**
* @ClassName: UserMapper 
* @Description: 用户Mapper
* @author luojing
* @date 2018年7月4日 上午10:16:31 
*
 */
public interface UserMapper  extends BaseMapper<TrwTUser> {

	 public Integer selectByphone(Map<String,String> param);

}
