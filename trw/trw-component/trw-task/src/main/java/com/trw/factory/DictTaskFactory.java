package com.trw.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.trw.constant.Constant;
import com.trw.manage.MuiThreadManager;
import com.trw.mapper.ConfigMapper;
import com.trw.mapper.DictMapper;
import com.trw.mapper.PCAMapper;
import com.trw.model.TrwTConfig;
import com.trw.model.TrwTDict;
import com.trw.service.impl.RedisService;
import com.trw.util.Db;
import com.trw.util.SpringContextHolder;
import com.trw.util.ToolUtil;
import com.trw.vo.Area;
import com.trw.vo.CodeSunVo;
import com.trw.vo.CodeVo;

/**
 * 字典操作任务创建工厂
 *
 */
public class DictTaskFactory {

	private static RedisService redisService = SpringContextHolder.getBean(RedisService.class);
	private static Logger logger = LoggerFactory.getLogger(MuiThreadManager.class);
	private static DictMapper dictMapper = Db.getMapper(DictMapper.class);
	private static ConfigMapper configMapper = Db.getMapper(ConfigMapper.class);
	private static PCAMapper pcaMapper = Db.getMapper(PCAMapper.class);

	public static TimerTask load() {
		return new TimerTask() {
			@Override
			public void run() {
				try {
					// 初始化字典
					List<TrwTDict> dictlist = dictMapper.selectCode(null);
					List<CodeVo> list = new ArrayList<>();
					int size = dictlist.size();
					for (TrwTDict dic : dictlist) {
						CodeVo vo = ToolUtil.has(dic, list);
						if (vo != null) {
							CodeSunVo newVo = ToolUtil.mapToVo(dic);
							vo.add(newVo);
						} else {
							vo = new CodeVo();
							vo.setDictype(dic.getDictype());
							vo.setDicname(dic.getDicname());
							vo.setDickey(dic.getDickey());
							CodeSunVo newVo = ToolUtil.mapToVo(dic);
							vo.add(newVo);
							list.add(vo);
						}
					}

					redisService.set(Constant.DICPIX + "all", list);

					for (int i = 0; i < list.size(); i++) {
						CodeVo vo = list.get(i);
						redisService.set(Constant.DICPIX + vo.getDictype(), vo.getChildren());
					}

					// 初始化参数
					List<TrwTConfig> configs = configMapper.selectList(new EntityWrapper<>());
					size = configs.size();
					for (int i = 0; i < size; i++) {
						TrwTConfig config = configs.get(i);
						String key = config.getParamcode();
						redisService.set(Constant.CONFIGPIX + key, config.getParamvalue());
					}

					// 省
					List<Area> provinces = pcaMapper.getProvince();
					Map<String, String> provinceMap = new HashMap<>();
					for (Area a : provinces) {
						provinceMap.put(a.getId(), a.getName());
					}
					redisService.set(Constant.PCAPIX + "Prov", JSON.toJSONString(provinceMap));
					// 市
					List<Area> citys = pcaMapper.getCity();
					Map<String, String> cityMap = new HashMap<>();
					for (Area a : citys) {
						cityMap.put(a.getId(), a.getName());
					}
					redisService.set(Constant.PCAPIX + "city", JSON.toJSONString(cityMap));
					// 县
					List<Area> areas = pcaMapper.getArea();
					Map<String, String> areaMap = new HashMap<>();
					for (Area a : areas) {
						areaMap.put(a.getId(), a.getName());
					}
					redisService.set(Constant.PCAPIX + "area", JSON.toJSONString(areaMap));
					
				} catch (Exception e) {
					logger.error("初始化分类码异常!", e);
				}
			}
		};
	}
}
