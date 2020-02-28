package com.trw.feign;

import com.trw.model.TrwTAllocation;
import com.trw.model.TrwTLand;
import com.trw.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "trw-land")
public interface LandFeignApi {
    
    
    /**
     * 通过服务商id查询出下面的土地
     * @param faciid
     * @return
     */
    @RequestMapping(value = "/selectFaciLand",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultMsg<Map<String,Object>> selectFaciLand(@RequestParam("faciid") String faciid);
    /**
     * @param @param  productid
     * @param @return 参数说明
     * @return TrwTLand 返回类型
     * @throws
     * @Title: getLandById
     * @Description: 根据ID查询土地
     * @author luojing
     * @date 2018年7月4日
     */
    @RequestMapping(value = "/getLandById",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResultMsg<TrwTLand> getLandById(@RequestParam("productid") String productid);
    
    /**
     * 分配经纪人
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/saveAllocal",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean saveAllocal(@RequestBody TrwTAllocation entity);
    
    /**
     * 批量分配经纪人
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/saveAllAllotAgent",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Boolean saveAllAllotAgent(@RequestParam("landsID") String landsID, @RequestParam("angentID") String angentID);
    
    
    /**
     * 土地市场-申请代理
     * @param landFeedback
     * @return
     */
    @RequestMapping(value = "/addLandMarkFeedback",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg<String> addLandMarkFeedback(@RequestBody Map<String,String> map);
    
    /**
     * 根据土地id修改土地信息
     * @param trwTLand
     * @return
     */
    @RequestMapping(value = "/updateByid",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg<String> updateByid(@RequestBody TrwTLand trwTLand);
}
