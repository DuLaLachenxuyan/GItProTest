package com.trw.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
* @ClassName: TrwTDict 
* @Description: 数据字典实体
* @author luojing
* @date 2018年7月4日 上午10:15:53 
*
 */
@Getter
@Setter
@TableName("TRW_T_DICT")
@ApiModel(value="数据字典实体")
public class TrwTDict extends Model<TrwTDict> {

    private static final long serialVersionUID = 4035597695415438088L;

    /**
     * 显示值
     */
    @ApiModelProperty(value="显示值")
    private String diclabel;
    /**
     * 排序
     */
    @ApiModelProperty(value="排序")
    private Integer orderby;
    /**
     * 编码值
     */
    @ApiModelProperty(value="编码值")
    private String diccode;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
    /**
     * 主类
     */
    @ApiModelProperty(value="主类")
    private String dictype;
    /**
     * 01-启用,02-禁用
     */
    @ApiModelProperty(value="01-启用,02-禁用")
    private String status;
    /**
     * 字典key
     */
	@TableId
    @ApiModelProperty(value="字典key")
    private String dickey;
    /**
     * 参数类型01-简单参数;02-复杂参数
     */
    @ApiModelProperty(value="参数类型01-简单参数;02-复杂参数")
    private String ptype;
    /**
     * 最小值
     */
    @ApiModelProperty(value="最小值")
    private String cmin;
    /**
     * 最大值
     */
    @ApiModelProperty(value="最大值")
    private String cmax;
    /**
     * 父分类码
     */
    @ApiModelProperty(value="父分类码")
    private String pkey;
    /**
     * 分类码名字
     */
    @ApiModelProperty(value="分类码名字")
    private String dicname;
    
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operator;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operatorTime;

    @Override
    protected Serializable pkVal() {
        return this.dickey;
    }

    
}
