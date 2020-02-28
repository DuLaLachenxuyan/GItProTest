package com.trw.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.EnshrineMapper;
import com.trw.model.PlatTEnshrine;
import com.trw.service.IEnshrineService;
import com.trw.vo.Enshrine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
@Service
public class EnshrineServiceImpl extends ServiceImpl<EnshrineMapper, PlatTEnshrine> implements IEnshrineService {

    @Autowired
    private EnshrineMapper platTEnshrineMapper;
    @Override
   public List<Enshrine> selectPTS(Page<PlatTEnshrine> page, Map<String,Object> map){
       return platTEnshrineMapper.selectPTS(page,map);
    }
	@Override
	public List<Map<String, Object>> findLandClient(Page<PlatTEnshrine> page, Map<String, String> param) {
		return platTEnshrineMapper.findLandClient(page,param);
	}
    
    
}
