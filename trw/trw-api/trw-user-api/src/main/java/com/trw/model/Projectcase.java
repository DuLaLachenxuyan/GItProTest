package com.trw.model;
import java.io.Serializable;
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
 * <p>
 * 
 * </p>
 *
 * @author gongzhen123
 * @since 2018-08-16
 */

@Getter
@Setter
@TableName("trw_t_projectcase")
@ApiModel(value="项目案例实体")
public class Projectcase extends Model<Projectcase> {

	private static final long serialVersionUID = -4766337884256263404L;
	/**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(value="主键id")
    private String id;
    /**
     * 服务商id
     */
    @ApiModelProperty(value="服务商id")
    private String faciid;
    /**
     * 案例内容
     */
    @ApiModelProperty(value="案例内容")
    private String content;
    /**
     * 案例时间
     */
    @ApiModelProperty(value="案例时间")
    private Date caseTime;
    /**
     * 案例图片
     */
    @ApiModelProperty(value=" 案例图片")
    private String caseImg;

    @Override
    public String toString() {
        return "TrwTProjectcase{" +
        "id=" + id +
        ", faciid=" + faciid +
        ", content=" + content +
        ", caseTime=" + caseTime +
        ", caseImg=" + caseImg +
        "}";
    }

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
