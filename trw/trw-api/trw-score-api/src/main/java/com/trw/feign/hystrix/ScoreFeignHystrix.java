package com.trw.feign.hystrix;


import com.trw.feign.ScoreFeignApi;
import com.trw.vo.ResultMsg;
import org.springframework.stereotype.Service;

@Service
public class ScoreFeignHystrix implements ScoreFeignApi {
    
    
    @Override
    public ResultMsg<String> modifyScore(String faciid, String ruleid) {
        return null;
    }
}
