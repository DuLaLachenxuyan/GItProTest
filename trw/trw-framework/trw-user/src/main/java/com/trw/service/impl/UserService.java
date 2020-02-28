package com.trw.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.UserMapper;
import com.trw.model.TrwTUser;
import com.trw.service.IUserService;
 

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class UserService extends ServiceImpl<UserMapper, TrwTUser> implements IUserService {
	
	/**
	 * 通过电话查询用户
	 */
	public Integer selectByphone(String phone) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("phone", phone);
		return baseMapper.selectByphone(param);
	}

	@Override
	public TrwTUser getUser(String userId) {
		return baseMapper.selectById(userId);
	}

	public TrwTUser getUser(TrwTUser user) {
		return baseMapper.selectOne(user);
	}

	

}
