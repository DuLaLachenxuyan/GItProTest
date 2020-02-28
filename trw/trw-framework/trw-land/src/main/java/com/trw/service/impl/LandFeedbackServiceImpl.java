package com.trw.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.trw.mapper.LandFeedbackMapper;
import com.trw.model.LandFeedback;
import com.trw.service.ILandService;
import com.trw.service.LandFeedbackService;

/**
 * @author luojing
 * @ClassName: LandFeedbackServiceImpl
 * @Description: 土地反馈服务实现类
 * @date 2018年7月3日 下午2:50:15
 */
@Service
public class LandFeedbackServiceImpl extends ServiceImpl<LandFeedbackMapper, LandFeedback> implements LandFeedbackService {
    @Autowired
    private ILandService iLandService;
    
    @Override
    @Transactional(readOnly=false)
    public Boolean saveLandMark(Map<String,String> landMap, LandFeedback landFeedback) {
        insert(landFeedback);
        iLandService.updateLandFormMarket(landMap);
        return true;
    }
}
