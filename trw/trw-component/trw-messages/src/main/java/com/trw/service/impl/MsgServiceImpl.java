package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.MsgMapper;
import com.trw.model.TrwTMsg;
import com.trw.service.IMsgService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-25
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, TrwTMsg> implements IMsgService {

	@Autowired
	private MsgMapper msgMapper;
	
	@Override
	public List<TrwTMsg> findMyMsg(Page<TrwTMsg> page, Map<String, String> param) {
		return msgMapper.findMyMsg(page,param);
	}

}
