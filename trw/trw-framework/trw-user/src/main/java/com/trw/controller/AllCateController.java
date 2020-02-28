package com.trw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.constant.Constant;
import com.trw.vo.CodeVo;

import io.swagger.annotations.ApiOperation;
/**
* @ClassName: AllCateController 
* @Description: 首发全部分类控制器
* @author luojing
* @date 2018年7月4日 上午10:40:42 
*
 */
@RestController
public class AllCateController extends BaseController {

	@SuppressWarnings("unchecked")
	@ApiOperation(httpMethod = "POST", value = "全部分类", notes = "全部分类")
	@RequestMapping(value = "/allCate")
	public List<CodeVo> findDic(HttpServletRequest request) throws Exception {
		List<String> keys = new ArrayList<>(3);
		keys.add("landFinancial");
		keys.add("professionals");
		keys.add("landType");
		List<CodeVo> result = new ArrayList<>(3);
		Object obj = redisService.get(Constant.DICPIX + "all");
		List<CodeVo> list = (List<CodeVo>) obj;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (keys.contains(list.get(i).getDictype())) {
				result.add(list.get(i));
			}
			if (result.size() == 3) {
				return result;
			}
		}
		return result;
	}
}