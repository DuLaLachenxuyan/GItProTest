package com.trw.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.trw.model.LandFeedback;

/**
* @ClassName: LandFeedbackService 
* @Description: 土地反馈服务类
* @author luojing
* @date 2018年7月3日 下午2:49:46 
*
 */
public interface LandFeedbackService extends IService<LandFeedback> {

    Boolean saveLandMark(Map<String,String> landMap,LandFeedback landFeedback);
}
