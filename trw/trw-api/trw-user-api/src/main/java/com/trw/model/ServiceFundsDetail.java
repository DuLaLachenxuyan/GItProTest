package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* @ClassName: TrwTServiceFundsDetail 
* @Description: 服务中心资金明细
* @author luojing
* @date 2018年7月16日 下午3:03:34 
*
 */
@Getter
@Setter
@TableName("trw_t_service_funds_detail")
@ApiModel(value="服务中心资金明细实体")
public class ServiceFundsDetail extends Model<ServiceFundsDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,资金流水号
     */
    @ApiModelProperty(value="主键id")
    @TableId(value = "fundsId", type = IdType.INPUT)
    private String fundsId;
    /**
     * 经纪人ID
     */
    @ApiModelProperty(value="经纪人ID")
    private String userId;
    /**
     * 订单/提现单价编号
     */
    @ApiModelProperty(value="订单/提现单价编号")
    private String orderNo;
    /**
     * 资金明细类型:0收入,1提现
     */
    @ApiModelProperty(value="资金明细类型:0收入,1提现")
    private String type;
    
    /**
     * 收入/支出金额
     */
    @ApiModelProperty(value="收入/支出金额")
    private BigDecimal amount;
    
    /**
     * 提现状态:0体现中,1提现完成,-1提现失败
     */
    @ApiModelProperty(value="提现状态:0体现中,1提现完成,-1提现失败")
    private String status;
    
    /**
     * 提现失败消息
     */
    @ApiModelProperty(value="提现失败消息")
    private String failMsg;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    
    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.fundsId;
    }
    
}
