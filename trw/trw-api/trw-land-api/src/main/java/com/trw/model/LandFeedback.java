package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* @ClassName: LandFeedback 
* @Description: 土地反馈bean
* @author luojing
* @date 2018年7月3日 下午2:12:50 
 */
@Setter
@Getter
@TableName("trw_t_feedback")
@ApiModel(value = "反馈表")
public class LandFeedback extends Model<LandFeedback> {

    private static final long serialVersionUID = 1L;
    
    /**
     * 反馈id
     */
    @ApiModelProperty(value = "反馈id")
    private String feedbackId;
    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容")
    private String fBcontent;
    /**
     * 经纪人id
     */
    @ApiModelProperty(value = "经纪人id")
    private String agentId;
    /**
     * 土地id
     */
    @ApiModelProperty(value = "土地id")
    private String productid;
    /**
     * 反馈类型，01为土地反馈，02为申请代理反馈
     */
    @ApiModelProperty(value = "反馈类型，01为土地反馈，02为申请代理反馈")
    private String feedbacktype;
    
    @Override
    protected Serializable pkVal() {
        return this.feedbackId;
    }
    
}
