package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.TrwTAgent;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author linhai123
 * @since 2018-06-14
 */
public interface AgentMapper extends BaseMapper<TrwTAgent> {
	

	/**
	 * 分页查询中心下的所有经纪人
	 * 
	 * @param faciid
	 * @return
	 */
	List<Map<String,Object>> selectAgent(Page<TrwTAgent> page, String faciid);

	/**
	 * 查询经纪人和土地信息
	 * 
	 * @param page
	 * @return
	 */
	List<TrwTAgent> selectLandAgent(Page<TrwTAgent> page);

	/**
	 * 
	 * @Title: selectByfaciid
	 * @Description: 根据服务商id查询员工
	 * @author haochen
	 * @param @param
	 *            page
	 * @param @param
	 *            param
	 * @param @return
	 *            参数说明
	 * @return List<Map<String,Object>> 返回类型
	 * @throws @date
	 *             2018年8月2日 下午4:40:33
	 */
	public List<Map<String, Object>> selectByfaciid(Page<TrwTAgent> page, Map<String, String> param);

	/**
	 * 
	 * @Title: updateByPhone
	 * @Description: 根据电话修改密码
	 * @author haochen
	 * @param @param
	 *            agent
	 * @param @return
	 *            参数说明
	 * @return int 返回类型
	 * @throws @date
	 *             2018年7月17日
	 */
	public int updateByPhone(TrwTAgent agent);

}
