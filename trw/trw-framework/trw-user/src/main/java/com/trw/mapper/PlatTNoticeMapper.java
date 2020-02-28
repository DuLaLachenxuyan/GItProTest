package com.trw.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.model.PlatTNotice;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author haochen123
 * @since 2018-07-20
 */
public interface PlatTNoticeMapper extends BaseMapper<PlatTNotice> {
	/**
	 * 
	 * @Title: findNotices
	 * @Description: 查看服务商的咨询
	 * @author haochen
	 * @param @param
	 *            page
	 * @param @param
	 *            param
	 * @param @return
	 *            参数说明
	 * @return List<PlatTNotice> 返回类型
	 * @throws @date
	 *             2018年7月20日 上午11:24:45
	 */
	List<PlatTNotice> findNotices(Page<PlatTNotice> page, Map<String, String> param);

}
