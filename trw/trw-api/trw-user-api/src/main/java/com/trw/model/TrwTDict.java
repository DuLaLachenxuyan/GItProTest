package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="数据字典")
public class TrwTDict extends Model<TrwTDict> {

    private static final long serialVersionUID = 4035597695415438088L;

    /**
     * 显示值
     */
    private String diclabel;
    /**
     * 排序
     */
    private Integer orderby;
    /**
     * 编码值
     */
    private String diccode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主类
     */
    private String dictype;
    /**
     * 01-启用,02-禁用
     */
    private String status;
    /**
     * 字典key
     */
	@TableId
    private String dickey;
    /**
     * 参数类型01-简单参数;02-复杂参数
     */
    private String ptype;
    /**
     * 最小值
     */
    private String cmin;
    /**
     * 最大值
     */
    private String cmax;
    /**
     * 父分类码
     */
    private String pkey;
    /**
     * 分类码名字
     */
    private String dicname;
    
    @Override
    protected Serializable pkVal() {
        return this.dickey;
    }

    
}
