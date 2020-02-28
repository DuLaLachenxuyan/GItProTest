package com.trw.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.ReportMapper;
import com.trw.model.TrwTReport;
import com.trw.service.IReportService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-25
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, TrwTReport> implements IReportService {

}
