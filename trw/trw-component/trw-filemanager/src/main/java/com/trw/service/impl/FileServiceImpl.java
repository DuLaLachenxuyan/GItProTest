package com.trw.service.impl;

 
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.FileMapper;
import com.trw.model.TrwTFile;
import com.trw.service.IFileService;
import com.trw.util.StrKit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-17
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, TrwTFile> implements IFileService {
	/**
	 * 用户发布土地保存图片
	 * @param imgs
	 * @param userId
	 */
	@Override
	public void updateFile(String imgs,String userId) {
			if(!StrKit.isBlank(imgs)) {
				String[] img =imgs.split(",");
				Map<String,Object> map = new HashMap<>();
				map.put("imgs", img);
				map.put("userId", userId);
				baseMapper.updateFile(map);
			}
		 
	}
//	/**
//	 * 查询的过期图片
//	 * @return
//	 */
//	@Override
//	public List<TrwTFile> selecOverData() {
//		return null;
//	}
    /**
	 * 删除过期图片
	 * @return
	 */
	@Override
	public int deleteOverData() {
		return baseMapper.deleteOverData();
	}
		
}
