package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-15
 */
@Getter
@Setter
@TableName("trw_t_acc_detail")
@ApiModel(value="资金账户表")
public class TrwTAccDetail extends Model<TrwTAccDetail> {

	private static final long serialVersionUID = 153295607210376670L;
	/**
     * 流水号
     */
    @TableId(type=IdType.INPUT)
    @ApiModelProperty(value = "流水号")
    private String id;
    /**
     * 账户id
     */
    @TableField("buyer_logon_id")
    @ApiModelProperty(value = "账户id")
    private String buyerLogonId;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    @NotBlank(message="订单id不能为空")
    private String orderId;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createTime;
    /**
     * 金额
     */
    @ApiModelProperty(value = "金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 资金流向(01-收入，02-支出)
     */
    @ApiModelProperty(value = "资金流向(01-收入，02-支出)")
    private String fundFlow;
    /**
     * 资金类型(01-普通资金,02-保证金)
     */
    @ApiModelProperty(value = "资金类型(01-普通资金,02-保证金)")
    private String fundType;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String body;
    /**
     * 交易号
     */
    @ApiModelProperty(value = "交易号")
    @TableField("trade_no")
    private String tradeNo;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userid;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
}
