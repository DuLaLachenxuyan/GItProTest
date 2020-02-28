package com.trw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.PlatTNoticeMapper;
import com.trw.model.PlatTNotice;
import com.trw.service.IPlatTNoticeService;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author haochen123
 * @since 2018-07-20
 */
@Service
public class PlatTNoticeServiceImpl extends ServiceImpl<PlatTNoticeMapper, PlatTNotice> implements IPlatTNoticeService {

	@Override
	public List<PlatTNotice> findNotices(Page<PlatTNotice> page, Map<String, String> param) {
		return baseMapper.findNotices(page, param);
	}

}
