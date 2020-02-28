package com.trw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.LandClientMapper;
import com.trw.model.LandClient;
import com.trw.model.TrwTNeedagent;
import com.trw.service.ILandClientService;
import com.trw.service.INeedagentService;
import com.trw.vo.ClientVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-14
 */
@Service
public class LandClientServiceImpl extends ServiceImpl<LandClientMapper, LandClient> implements ILandClientService {
    @Autowired
    private INeedagentService iNeedagentService;
    
    @Override
    public List<LandClient> selectLandClient(Page<LandClient> page, Map<String, String> param) {
        return baseMapper.selectLandClient(page, param);
    }
    
    @Override
    public List<LandClient> selectByUserid(String userId, Page<LandClient> page) {
        return baseMapper.selectByUserid(userId, page);
    }
    
    @Override
    public List<LandClient> selectByTerm(Page<LandClient> page, Map<String, String> param) {
        return baseMapper.selectByTerm(page, param);
    }
    
    @Override
    public List<ClientVo> selectClient(Page<LandClient> page, Map<String, String> param) {
        return baseMapper.selectClient(page, param);
    }
    
    @Override
    public Boolean updateAgentId(String needid, String agentId) {
        Map<String, String> map = new HashMap<>();
        map.put("needid", needid);
        map.put("agentId", agentId);
        return baseMapper.updateAgentId(map);
    }
    
    @Override
    public List<LandClient> selectSimilar(Page<LandClient> page, Map<String, String> map) {
        return baseMapper.selectSimilar(page, map);
    }
    
    @Override
    public List<LandClient> selectTrwClient(String id) {
        return baseMapper.selectTrwClient(id);
    }
    
    @Override
    public List<LandClient> selectAgentClient(String agentid) {
        return baseMapper.selectAgentClient(agentid);
    }
    
    @Override
    public List<LandClient> selectMatched(String id) {
        return baseMapper.selectMatched(id);
    }
    
    @Override
    @Transactional(readOnly = false)
    public Boolean insertClient(TrwTNeedagent trwTNeedagent, LandClient landClient) {
        insert(landClient);
        iNeedagentService.insert(trwTNeedagent);
        return true;
    }

	@Override
	public List<LandClient> getLandClientByFaciId(Page<LandClient> page, Map<String, String> param) {
		
		return baseMapper.getLandClientByFaciId(page,param);
	}
    
    
}
