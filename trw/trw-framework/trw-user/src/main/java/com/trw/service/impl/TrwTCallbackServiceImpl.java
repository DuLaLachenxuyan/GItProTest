package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.TrwTCallbackMapper;
import com.trw.model.TrwTCallback;
import com.trw.service.ITrwTCallbackService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-23
 */
@Service
public class TrwTCallbackServiceImpl extends ServiceImpl<TrwTCallbackMapper, TrwTCallback>
		implements ITrwTCallbackService {

	@Autowired
	private TrwTCallbackMapper callbackMapper;

	@Override
	public List<Map<String, Object>> findCallback(Map<String, String> param, Page<TrwTCallback> page) {
		return callbackMapper.findCallback(param,page);
	}

	@Override
	public int updateCallback(Map<String, String> param) {
		return callbackMapper.updateCallback(param);
	}

	@Override
	public TrwTCallback findUserCall(Map<String, String> param) {
		return callbackMapper.findUserCall(param);
	}

}
