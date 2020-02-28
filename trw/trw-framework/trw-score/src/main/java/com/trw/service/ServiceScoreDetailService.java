package com.trw.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.ServiceScoreDetail;
import com.trw.vo.ScoreDetailRule;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 123
 * @since 2018-08-07
 */
public interface ServiceScoreDetailService extends IService<ServiceScoreDetail> {

	boolean insertDetail(ServiceScoreDetail entity);
	
	/**
	 * 查询积分明细
	 * @param faciid
	 * @return
	 */
	List<ScoreDetailRule> selectByFaciId(Page<ScoreDetailRule> page,String faciid);

}
