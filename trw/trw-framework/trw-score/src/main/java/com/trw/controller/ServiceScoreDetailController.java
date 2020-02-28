package com.trw.controller;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.model.ServiceScoreDetail;
import com.trw.model.ServiceScoreRule;
import com.trw.service.ServiceScoreDetailService;
import com.trw.service.ServiceScoreRuleService;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import com.trw.vo.ScoreDetailRule;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author gongzhen
 * @ClassName: ServiceScoreDetailController
 * @Description: 积分明细控制器
 * @date 2018年8月8日 上午9:53:22
 */
@RestController
public class ServiceScoreDetailController extends BaseController {
    @Autowired
    private ServiceScoreRuleService scoreRuleService;
    
    @Autowired
    private ServiceScoreDetailService scoreDetailService;
    
    @RequestMapping(value = "/modifyScore")
    @ApiOperation(httpMethod = "POST", value = "修改积分操作记录", notes = "添加积分操作记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "ruleid", value = "规则id", dataType = "String", paramType = "query")
    })
    public ResultMsg<String> modifyScore(@RequestParam("faciid") String faciid, @RequestParam("ruleid") String ruleid) {
        ResultMsg<String> rmg = new ResultMsg<>();
        ServiceScoreDetail entity = new ServiceScoreDetail();
        //查看对应规则
        ServiceScoreRule rule = scoreRuleService.selectById(ruleid);
        String ruletype = rule.getRuletype();//规则类型
        Integer rulescore = rule.getRulescore();//规则变动分数
        //设置插入实体
        entity.setId(ToolUtil.generateId("TSD"));
        entity.setDate(LocalDateTime.now());
        entity.setType(ruletype);
        entity.setScore(rulescore);
        entity.setFaciid(faciid);
        entity.setRuleid(ruleid);
        
        boolean flg = scoreDetailService.insertDetail(entity);
        
        if (!flg) {
            rmg.setMsg("修改积分记录操作失败!");
            rmg.setCode(Constant.CODE_FAIL);
            return rmg;
        }
        rmg.setMsg("修改积分记录操作成功!");
        rmg.setCode(Constant.CODE_SUCC);
        return rmg;
    }
    
    @RequestMapping(value = "/auth/selectScoreDetail")
    @ApiOperation(httpMethod = "POST", value = "查询服务商对应的积分明细", notes = "查询服务商对应的积分明细")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query")
    })
    public ResultMsg<Map<String,Object>> selectScoreDetail(HttpServletRequest req) {
        String faciid = req.getParameter("faciid");
        Page<ScoreDetailRule> page=new PageFactory<ScoreDetailRule>().defaultPage();
        List<ScoreDetailRule> ScoreDetailRule = scoreDetailService.selectByFaciId(page,faciid);
        Map<String,Object> map=new HashMap<>();
        map.put("ScoreDetailRule",ScoreDetailRule);
        map.put("total",page.getTotal());
        ResultMsg<Map<String,Object>> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(map);
        return resultMsg;
    }
    
    
}

