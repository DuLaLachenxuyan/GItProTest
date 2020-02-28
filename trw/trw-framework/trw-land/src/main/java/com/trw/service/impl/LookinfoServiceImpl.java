package com.trw.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.constant.Constant;
import com.trw.mapper.LandMapper;
import com.trw.mapper.LookinfoMapper;
import com.trw.model.TrwTLookinfo;
import com.trw.service.LookinfoService;
import com.trw.util.StrKit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-22
 */
@Service
public class LookinfoServiceImpl extends ServiceImpl<LookinfoMapper, TrwTLookinfo> implements LookinfoService {
    @Autowired
	private LandMapper landMapper;
   

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertData(String productid, List<TrwTLookinfo> lookinfos,String deposit) {
		 //删除原来的数据
		baseMapper.delete(new EntityWrapper<TrwTLookinfo>().eq("productid", productid));
		insertBatch(lookinfos);
		
		//修改土地带看模式
		if(lookinfos.size()>0) {
			String model = Constant.MANY_TIMES;
			if(!StrKit.isBlank(deposit)) {
				model=model +"," + Constant.DEP_TIME;
			}
			Map<String,String> map = new HashMap<>();
			map.put("model", model);
			map.put("productid", productid);
			landMapper.updateLookInfo(map);
		}
		
	}
}
