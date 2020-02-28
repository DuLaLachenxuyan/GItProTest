package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTUser;

public interface IUserService  extends IService<TrwTUser> {
	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 */
	public Integer selectByphone(String phone);
	/**
	 * 查询用户信息
	 * @param phone
	 * @return
	 */
	public TrwTUser getUser(String userId);
}
