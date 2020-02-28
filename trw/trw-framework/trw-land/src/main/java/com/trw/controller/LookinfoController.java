package com.trw.controller;


import com.alibaba.fastjson.JSONArray;
import com.trw.constant.Constant;
import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLookinfo;
import com.trw.service.LookinfoService;
import com.trw.vo.ResultMsg;
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
 * @author linhai123
 * @since 2018-06-22
 */
@RestController
public class LookinfoController extends BaseController {
	@Autowired
    private LookinfoService lookinfoService;
    
    @RequestMapping(value = "/addAndupdateinfo")
    @ApiOperation(httpMethod = "POST", value = "服务中心-我的土地-设置带看费", notes = "服务中心-我的土地-设置带看费")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "productid", value = "土地编号", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "priceList", value = "多个带看费，与带看次数", dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "faciid", value = "服务中心Id", dataType = "string", paramType = "query"),
    })
    public ResultMsg<String> addAndupdateinfo(HttpServletRequest req) {
    	//获取服务中心信息
    	String faciid = req.getParameter("faciid");
    	TrwTFaci faci =(TrwTFaci) redisService.get(Constant.FACI+faciid);
    	String deposit = "";
    	if(faci!=null) {
    		deposit = faci.getDeposit();
    	}
        ResultMsg<String> resultMsg=new ResultMsg<>();
        String productid = req.getParameter("productid");
        String takeFees = req.getParameter("priceList");
      
        JSONArray jsonArray= JSONArray.parseArray(takeFees);
        List<TrwTLookinfo> lookinfos=jsonArray.toJavaList(TrwTLookinfo.class);
        lookinfoService.insertData(productid,lookinfos,deposit);
       
        resultMsg.setCode(Constant.CODE_SUCC);
        resultMsg.setMsg("设置带看费成功");
        return resultMsg;
    }
    
}

