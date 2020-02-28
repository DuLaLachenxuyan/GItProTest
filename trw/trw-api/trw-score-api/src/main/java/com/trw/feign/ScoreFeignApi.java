package com.trw.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.trw.vo.ResultMsg;

@FeignClient(value = "trw-score")
public interface ScoreFeignApi {
    
    @RequestMapping(value = "/modifyScore",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResultMsg<String> modifyScore(@RequestParam("faciid") String faciid, @RequestParam("ruleid") String ruleid);
    
    
}
