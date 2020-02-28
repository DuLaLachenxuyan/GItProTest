package com.trw.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ServiceFundsDetailMapper;
import com.trw.model.ServiceFundsDetail;
import com.trw.service.ServiceFundsDetailService;

/**
* @ClassName: ServiceFundsDetailService 
* @Description: 服务中心资金明细服务类
* @author luojing
* @date 2018年7月16日 下午3:14:38 
*
 */
@Service(value="fundsDetailService")
public class ServiceFundsDetailServiceImpl extends ServiceImpl<ServiceFundsDetailMapper, ServiceFundsDetail> implements ServiceFundsDetailService {

}
