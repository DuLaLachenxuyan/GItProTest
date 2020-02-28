package com.trw.service;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.TrwTFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-17
 */
public interface IFileService extends IService<TrwTFile> {
	/**
	 * 用户发布土地保存图片
	 * @param img
	 * @param userId
	 */
	void updateFile(String imgs, String userId);
//	/**
//	 * 查询的过期图片
//	 * @return
//	 */
//	List<TrwTFile> selecOverData();
	/**
	 * 删除过期图片
	 * @return
	 */
	int deleteOverData();



}
