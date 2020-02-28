package com.trw.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.feign.FileFeignApi;
import com.trw.mapper.LandMapper;
import com.trw.model.Records;
import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTLookinfo;
import com.trw.service.IAllocationService;
import com.trw.service.ILandService;
import com.trw.service.RecordsService;
import com.trw.util.StrKit;
import com.trw.vo.SoilSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 土地实现类
 * </p>
 */
@Service
public class LandService extends ServiceImpl<LandMapper, TrwTLand> implements ILandService {
	@Autowired
	private IAllocationService iAllocationService;

	@Autowired
	private FileFeignApi filApi;
	@Autowired
	private RecordsService recordsService;

	@Override
	public List<TrwTLand> selectMainLand(Page<TrwTLand> page, Map<String, String> map) {
		return baseMapper.selectMainLand(page, map);
	}

	/**
	 * 根据服务商查询相应的土地信息
	 *
	 * @param page
	 * @param
	 * @return
	 */
	@Override
	public List<TrwTLand> selectFaciLand(Page<TrwTLand> page, String faciid) {
		return baseMapper.selectFaciLand(page, faciid);
	}

	/**
	 * 根据标签查询土地
	 * 
	 * @param page
	 * @param landNote
	 * @return
	 */
	@Override
	public List<TrwTLand> selectLandNote(Page<TrwTLand> page, String landNote) {
		return baseMapper.selectLandNote(page, landNote);
	}

	@Override
	public List<TrwTLookinfo> selectLookModel(String productId) {
		return baseMapper.selectLookModel(productId);
	}

	/**
	 * 根据条件查询服务商中的土地
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public List<TrwTLand> selectLandFaci(Page<TrwTLand> page, Map<String, String> map) {
		return baseMapper.selectLandFaci(page, map);
	}

	@Override
	public TrwTLand getLandInfo(String productid) {
		return baseMapper.selectById(productid);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveLand(TrwTLand land) {
		int nu = baseMapper.insert(land);
		String imgs = land.getImgs();
		if (!StrKit.isBlank(imgs)) {
			filApi.updateFile(imgs,land.getReporter());
		}
		return nu > 0;
	}

	@Override
	public List<TrwTLand> selectMyLandsManage(Page<TrwTLand> page, Map<String, String> map) {
		return baseMapper.selectMyLandsManage(page, map);
	}
	
	@Override
	public List<SoilSource> selectSourceDetails(Page<SoilSource> page, Map<String, String> map) {
		return baseMapper.selectSourceDetails(page,map);
	}
	
	@Override
	public List<Map<String, Object>> selectLandMarket(Page<TrwTLand> page, Map<String, String> map) {
		return baseMapper.selectLandMarket(page,map);
	}
	
	@Override
	public List<Map<String, Object>> selectMarket(Page<TrwTLand> page, Map<String, String> map) {
		return baseMapper.selectMarket(page,map);
	}
	
	@Override
	public void allcalLand(String productid, String stat) {
		Map<String,String> map = new HashMap<>();
		map.put("productid", productid);
		map.put("stat", stat);
		baseMapper.allcalLand(map);
	}
	
	
	@Override
	public Boolean updateMyland(Map<String, String> map) {
		return baseMapper.updateMyland(map);
	}
	 /**
     * 从土地市场申请土地,修改土地信息成为我的土地
     * @param map
     * @return
     */
	@Override
	public Boolean updateLandFormMarket(Map<String, String> map) {
		return baseMapper.updateLandFormMarket(map);
	}
	
	@Override
	public List<TrwTLand> selectLandByNeedId(Page<TrwTLand> page,Map<String,String> map) {
		return baseMapper.selectLandByNeedId(page,map);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Boolean addLand(TrwTLand land, TrwTAllocation trwTAllocation) {
		saveLand(land);
		iAllocationService.insert(trwTAllocation);
		return true;
	}
	
	@Override
	public List<TrwTLand> selectIndexLand(Page<TrwTLand> page, Map<String, Object> map) {
		return baseMapper.selectIndexLand(page,map);
	}

	@Override
	public List<TrwTLand> selectLandTitle(String faciid) {
	 
		return baseMapper.selectLandTitle(faciid);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Boolean addLandApproval(TrwTLand land, Records records) {
		Boolean flg=updateById(land);
		Boolean fg=recordsService.insert(records);
		if (flg==true && fg==true){
			return true;
		}else {
			return false;
		}
	}
	
	
	@Override
	public List<TrwTLand> selectAgentLandById(Page<TrwTLand> page, Map<String, String> param) {
		return baseMapper.selectAgentLandById(page,param);
	}

}
