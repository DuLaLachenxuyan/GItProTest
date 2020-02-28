package com.trw.feign.hystrix;

import com.trw.feign.CustomerFeignApi;
import com.trw.model.LandClient;
import com.trw.vo.ResultMsg;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: LinHai
 * @Date: Created in 19:44 2018/7/9
 * @Modified By:
 */
@Service
public class CustomerFeignHystrix implements CustomerFeignApi{
    @Override
    public ResultMsg<LandClient> postClient(HttpServletRequest req) {
        return null;
    }
    
    @Override
    public ResultMsg<List<LandClient>> getLandClient(HttpServletRequest req) {
    	ResultMsg<List<LandClient>> re= new ResultMsg<>();
        return re;
    }
    
    @Override
    public ResultMsg<LandClient> getClientById(String needid) {
    	ResultMsg<LandClient> re= new ResultMsg<>();
        return re;
    }
    
    @Override
    public ResultMsg<List<LandClient>> selectMatched(String productid) {
        return null;
    }

	@Override
	public ResultMsg<LandClient> updateClientById(LandClient landClient) {
		return null;
	}
}
