package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author gongzhen123
 * @since 2018-06-11
 */
@Getter
@Setter
@TableName("TRW_T_BIMG")
@ApiModel(value="轮播实体")
public class TrwTBimg extends Model<TrwTBimg> {

	private static final long serialVersionUID = 3822282707286954814L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value="轮播主键id")
    private Integer id;
	
	@ApiModelProperty(value="轮播图名称")
    private String imgname;
	
	@ApiModelProperty(value="轮播url")
    private String imgurl;
	
	@ApiModelProperty(value="操作人id")
    private Integer userid;
	
	@ApiModelProperty(value="更新时间")
    private Date uptime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
   
}
