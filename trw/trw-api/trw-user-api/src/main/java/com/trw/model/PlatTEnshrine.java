package com.trw.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author linhai123
 * @since 2018-06-07
 */
@Getter
@Setter
@TableName("plat_t_enshrine")
@ApiModel(value = "收藏表")
public class PlatTEnshrine extends Model<PlatTEnshrine> {
    
    
    private static final long serialVersionUID = 3556931041313028911L;
    /**
     * 收藏主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 收藏标题
     */
    @ApiModelProperty(value = "收藏标题")
    private String ctypes;
    
    /**
     * 收藏类型
     */
    @ApiModelProperty(value = "收藏类型")
    private String ehrtype;
    
    /**
     * 收藏时间
     */
    @ApiModelProperty(value = "收藏时间")
    private Date ctime;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    private String userid;
    
    /**
     * 新闻id
     */
    @ApiModelProperty(value = "新闻id")
    private Integer newsid;
    
    /**
     * 土地id
     */
    @ApiModelProperty(value = "土地id")
    private String landid;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    
}
