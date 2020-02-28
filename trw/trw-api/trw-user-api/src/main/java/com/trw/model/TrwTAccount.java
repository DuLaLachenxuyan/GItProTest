package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
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
@TableName("trw_t_account")
@ApiModel(value="资金账户表")
public class TrwTAccount extends Model<TrwTAccount> {

	private static final long serialVersionUID = -8859259184778552589L;
	/**
     * 用户id
     */
	@TableId
    private String userid;
    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    private BigDecimal balance;
    /**
     * 剩余带看次数
     */
    @ApiModelProperty(value = "剩余带看次数")
    private Integer retimes;
    /**
     * 保证金
     */
    @ApiModelProperty(value = "保证金")
    private BigDecimal deposit;
    
    @Override
    protected Serializable pkVal() {
        return this.userid;
    }
   
}
