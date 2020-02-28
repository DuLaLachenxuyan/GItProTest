package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Getter;
import lombok.Setter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @ClassName: TrwTServiceMyFunds 
* @Description: 服务中心我的资金
* @author luojing
* @date 2018年7月16日 下午3:04:16 
*
 */
@Getter
@Setter
@TableName("trw_t_service_my_funds")
@ApiModel(value="服务中心我的资金实体")
public class ServiceMyFunds extends Model<ServiceMyFunds> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value="主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 经纪人ID
     */
    @ApiModelProperty(value="经纪人ID")
    private String userId;
    /**
     * 账户余额
     */
    @ApiModelProperty(value="账户余额")
    private BigDecimal balanceAmount;
    /**
     * 保证金额
     */
    @ApiModelProperty(value="保证金额")
    private BigDecimal ensureAmount;
    /**
     * 最后修改时间
     */
    @ApiModelProperty(value="最后修改时间")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}
