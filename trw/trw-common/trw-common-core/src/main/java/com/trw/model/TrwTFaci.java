package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-13
 */
@Getter
@Setter
@TableName("TRW_T_FACI")
public class TrwTFaci extends Model<TrwTFaci> {
	
	private static final long serialVersionUID = -5340836819564395236L;

    /**
     * 服务商id
     */
    @TableId(value = "faciid")
    private String faciid;
    /**
     * 服务商名称
     */
    private String faciname;
    /**
     * 状态
     */
    private String state;
    /**
     * 授权时间
     */
    private String authtime;
    /**
     * 到期时间
     */
    private String endtime;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 地址
     */
    private String address;
    /**
     * 法人
     */
    private String corporation;
    /**
     * 服务区域
     */
    private String location;
    /**
     * 服务类型
     */
    private String sertype;
    /**
     * 服务类型Code
     */
    private String sercode;
    /**
     * 等级
     */
    private String serlevel;
    /**
     * 经验值
     */
    private Integer serscore;
    /**
     * 好评率
     */
    private BigDecimal goodperc;
    /**
     * 是否认证
     */
    private String isident;
    /**
     * 权限
     */
    private String rightscope;
    
    /**
     * 服务商图片
     */
    private String images;
    /**
      * 是否启用保证金看地 01 yes 02 no
     * @return
     */
    private String deposit; 
    /***
     * 封面图
     * @return
     */
    private String coverimg;
    /**
     * 支持代理区县
     */
    private String agencyCounty;
    /**
     * 自营区县
     */
    private String ptaryCounty;
    /**
     * 服务商介绍
     */
    private  String introduce;
	@Override
	protected Serializable pkVal() {
		return faciid;
	}
    
}
