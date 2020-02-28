package com.trw.factory;

import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trw.constant.Constant;
import com.trw.manage.MuiThreadManager;
import com.trw.mapper.InitMapper;
import com.trw.model.TrwTFaci;
import com.trw.service.impl.RedisService;
import com.trw.util.Db;
import com.trw.util.SpringContextHolder;

/**
 * 服务中心操作任务创建工厂
 *
 */
public class FaciTaskFactory {

	private static RedisService redisService = SpringContextHolder.getBean(RedisService.class);
	private static Logger logger = LoggerFactory.getLogger(MuiThreadManager.class);
	private static InitMapper initMapper = Db.getMapper(InitMapper.class);

	public static TimerTask load() {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					//初始化服务中心
					List<TrwTFaci> facis = initMapper.findFaci();
					for (TrwTFaci faci : facis) {
						redisService.set(Constant.FACI + faci.getFaciid(),faci);
					}
				} catch (Exception e) {
					logger.error("初始化分类码异常!", e);
				}
			}
		};
	}
}
