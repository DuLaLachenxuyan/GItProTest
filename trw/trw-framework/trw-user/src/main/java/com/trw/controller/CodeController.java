package com.trw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.constant.Constant;
import com.trw.service.IDictService;
import com.trw.vo.CodeVo;

import io.swagger.annotations.ApiOperation;
/**
* @ClassName: CodeController 
* @Description: 数据字典控制器
* @author luojing
* @date 2018年7月4日 上午10:14:50 
*
 */
@RestController
public class CodeController extends BaseController {
    @Autowired
    private IDictService dicService;

    @ApiOperation(httpMethod = "POST",value = "获取字典", notes = "获取字典")
    @RequestMapping(value = "/findDic")
    public List<CodeVo> findDic(HttpServletRequest request) throws Exception {
    	
    	Object obj =redisService.get(Constant.DICPIX+"all");
    	if(obj !=null) {
    		@SuppressWarnings("unchecked")
			List<CodeVo> list = (List<CodeVo>) obj;
    		return list;
    	}
    	List<CodeVo> codeList =dicService.selectCode();
    	redisService.set(Constant.DICPIX+"all" , codeList);
        return codeList;
    }

 
}
