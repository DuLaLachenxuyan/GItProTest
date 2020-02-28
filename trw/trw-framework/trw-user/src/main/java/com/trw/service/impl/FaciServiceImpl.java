package com.trw.service.impl;

 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.FaciMapper;
import com.trw.model.TrwTFaci;
import com.trw.service.IFaciService;
import com.trw.vo.OrderFaci;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
@Service
public class FaciServiceImpl extends ServiceImpl<FaciMapper, TrwTFaci> implements IFaciService {
	
	@Override
	public List<TrwTFaci> selectMainFaci(Page<TrwTFaci> page, Map<String, String> map) {
		return baseMapper.selectMainFaci(page, map);
	}

	
	@Override
	public TrwTFaci getFaci(String id) {
		TrwTFaci faci = baseMapper.selectById(id);
		return faci;
	}

	@Override
	public TrwTFaci getFaciByLoc(String location) {
		TrwTFaci re=null;
		List<TrwTFaci> facis = baseMapper.getFaciByLoc(location);
		if(facis!=null && facis.size()>0) {
			re = facis.get(0);
		}
		return re;
	}


	@Override
	public List<OrderFaci> selectOrderFaci(Page<OrderFaci> page, String faciid) {
		return baseMapper.selectOrderFaci(page, faciid);
	}


}
