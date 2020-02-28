package com.trw.model;

import java.io.Serializable;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2018-06-14
 */
@Getter
@Setter
@TableName("trw_t_lookinfo")
@ApiModel(value = "带看费表")
public class TrwTLookinfo extends Model<TrwTLookinfo> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2305433439934213778L;
	/**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productid;
    /**
     * 费用
     */
    @ApiModelProperty(value = "费用")
    private BigDecimal lookfee;
    /**
     * 看地次数
     */
    @ApiModelProperty(value = "看地次数")
    private Integer num;
    
    @Override
    protected Serializable pkVal() {
        return this.productid;
    }
 
}
