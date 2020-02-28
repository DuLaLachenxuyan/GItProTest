package com.trw.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.trw.manage.ShiroKit;
import com.trw.model.TrwTAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trw.constant.Constant;
import com.trw.model.LandFeedback;
import com.trw.service.LandFeedbackService;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author luojing
 * @ClassName: FeedbackController
 * @Description: 土地反馈
 * @date 2018年7月3日 下午2:10:23
 */
@RestController
public class LandFeedbackController extends BaseController {
    @Autowired
    private LandFeedbackService feedbackService;
    
    @RequestMapping(value = "/auth/mLFeedback")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-反馈", notes = "服务中心-我的土地-反馈")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地ID", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "FBcontent", value = "反馈内容", required = true, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "severtoken", value = "severtoken", required = true, dataType = "string", paramType = "query")
    })
    public ResultMsg<String> mLFeedback(HttpServletRequest req) {
	    TrwTAgent agent = ShiroKit.getUser();
        LandFeedback feedback = new LandFeedback();
        feedback.setFeedbackId(ToolUtil.generateId("FED"));
        feedback.setAgentId(agent.getId());
        feedback.setFBcontent(req.getParameter("FBcontent"));
        feedback.setProductid(req.getParameter("productid"));
        feedback.setFeedbacktype("01");
        feedbackService.insert(feedback);
        ResultMsg<String> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("反馈成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        return resultMsg;
        
    }
    
    @RequestMapping(value = "/auth/applyLand")
	@ApiOperation(httpMethod = "POST", value = "服务中心-土地市场-申请代理", notes = "服务中心-土地市场-申请代理")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productid", value = "土地ID", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "fBcontent", value = "申请代理反馈信息", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> applyLand(HttpServletRequest req) {
    	ResultMsg<String> resultMsg = new ResultMsg<>();
	    TrwTAgent agent = ShiroKit.getUser();
		// 修改土地中的服务商信息
		Map<String,String> param = new HashMap<>();
		
		String productid = req.getParameter("productid");
		String faciid = req.getParameter("faciid");
		if(StrKit.isBlank(productid)) {
			resultMsg.setMsg("productid不能为空");
    		resultMsg.setCode(Constant.CODE_FAIL);
    		return resultMsg;
		}
		
		if(StrKit.isBlank(faciid)) {
			resultMsg.setMsg("faciid不能为空");
    		resultMsg.setCode(Constant.CODE_FAIL);
    		return resultMsg;
		}
		
		
		param.put("productid", productid);
		param.put("faciid", faciid);
		
		// 将反馈信息插入到反馈表
		LandFeedback feedback = new LandFeedback();
		feedback.setFeedbacktype("02"); //01为土地反馈，02为申请代理反馈
		feedback.setFBcontent(req.getParameter("fBcontent"));
		feedback.setAgentId(agent.getId());
		feedback.setProductid(productid);
		feedback.setFeedbackId(ToolUtil.generateId("AAF"));
		
        Boolean flg = feedbackService.saveLandMark(param, feedback);
      
        if(flg) {
    		resultMsg.setMsg("申请成功");
    		resultMsg.setCode(Constant.CODE_SUCC);
        }else {
        	resultMsg.setMsg("申请失败");
    		resultMsg.setCode(Constant.CODE_FAIL);
        }
        return resultMsg;
		
	}
}

