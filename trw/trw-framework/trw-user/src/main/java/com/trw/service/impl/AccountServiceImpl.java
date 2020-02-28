package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AccountMapper;
import com.trw.model.TrwTAccDetail;
import com.trw.model.TrwTAccount;
import com.trw.service.IAccountService;
import com.trw.vo.AccountVo;
 


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-16
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, TrwTAccount> implements IAccountService {
 
	public List<AccountVo> selectAccDetail(Page<TrwTAccDetail> page, Map<String, String> param) {
		return baseMapper.selectAccDetail(page,param);		
	}


	
}
