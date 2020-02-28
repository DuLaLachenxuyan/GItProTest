package com.trw.vo;

import java.io.Serializable;
import java.util.List;

import com.trw.model.TrwTFaci;
import com.trw.model.TrwTLand;
import com.trw.model.TrwTLookinfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 地源详情实体
 * 
 * @Description:
 * @Author: LinHai
 * @Date: Created in 13:35 2018/7/2
 * @Modified By:
 */
@Getter
@Setter
@ApiModel(value = "地源详情")
public class SoilSource extends TrwTLand implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5753243629188407374L;

	@ApiModelProperty("客户名称")
	private String aname;

	@ApiModelProperty("带看信息")
	private List<TrwTLookinfo> looks;

	@ApiModelProperty("服务中心信息")
	private TrwTFaci tFaci;
 
	@ApiModelProperty(value = "保证金")
	private String delookMonery;
}
