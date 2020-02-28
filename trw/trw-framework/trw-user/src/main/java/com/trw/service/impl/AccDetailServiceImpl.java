package com.trw.service.impl;


import org.springframework.stereotype.Service;
 

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.AccDetailMapper;
import com.trw.model.TrwTAccDetail;
import com.trw.service.IAccDetailService;

@Service
public class AccDetailServiceImpl extends ServiceImpl<AccDetailMapper, TrwTAccDetail> implements IAccDetailService {

}
