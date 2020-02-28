package com.trw.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceMyFundsMapper;
import com.trw.model.ServiceMyFunds;
import com.trw.service.ServiceMyFundsService;

/**
 * 
* @ClassName: ServiceMyFundsService 
* @Description: 服务中心资金服务类
* @author luojing
* @date 2018年7月16日 下午3:14:51 
*
 */
@Service(value="myFundsService")
public class ServiceMyFundsServiceImpl extends ServiceImpl<ServiceMyFundsMapper, ServiceMyFunds> implements ServiceMyFundsService {

}
