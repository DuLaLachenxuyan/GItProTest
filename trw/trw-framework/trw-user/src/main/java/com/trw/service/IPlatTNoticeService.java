package com.trw.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.trw.model.PlatTNotice;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author haochen123
 * @since 2018-07-20
 */
public interface IPlatTNoticeService extends IService<PlatTNotice> {
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
