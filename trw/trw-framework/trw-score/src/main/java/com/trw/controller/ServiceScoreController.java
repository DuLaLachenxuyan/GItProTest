package com.trw.controller;


import com.trw.constant.Constant;
import com.trw.model.ServiceScore;
import com.trw.service.ServiceScoreService;
import com.trw.vo.ResultMsg;
import com.trw.vo.ScoreRank;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-08-07
 */
@RestController
public class ServiceScoreController extends BaseController {
    @Autowired
    private ServiceScoreService serviceScoreService;
    
    @RequestMapping(value = "/auth/getServiceScore")
    @ApiOperation(httpMethod = "POST", value = "查询服务商对应的总积分", notes = "查询服务商对应的总积分")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "String", paramType = "query")
    })
    public ResultMsg<ServiceScore> getServiceScore(HttpServletRequest req) {
        String faciid = req.getParameter("faciid");
        ServiceScore serviceScore = serviceScoreService.selectById(faciid);
        ResultMsg<ServiceScore> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(serviceScore);
        return resultMsg;
    }
    
    @RequestMapping(value = "/auth/selectScoreRank")
    @ApiOperation(httpMethod = "POST", value = "查询服务商积分排行", notes = "查询服务商积分排行")
    public ResultMsg<List<ScoreRank>> selectScoreRank(HttpServletRequest req) {
        List<ScoreRank> scoreRank = serviceScoreService.selectPageScoreRank();
        ResultMsg<List<ScoreRank>> resultMsg = new ResultMsg<>();
        resultMsg.setMsg("查询成功");
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setData(scoreRank);
        return resultMsg;
    }
    
}

