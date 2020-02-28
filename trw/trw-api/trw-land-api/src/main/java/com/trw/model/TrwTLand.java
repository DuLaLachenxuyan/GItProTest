package com.trw.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author linhai123
 * @since 2018-06-15
 */
@Getter
@Setter
@TableName("trw_t_land")
@ApiModel(value = "土地表")
public class TrwTLand extends Model<TrwTLand> {
    
    private static final long serialVersionUID = -108056455237553710L;
    
    /**
     * 土地编码
     */
    @ApiModelProperty(value = "土地编码")
    @TableId(value = "productid", type = IdType.INPUT)
    private String productid;
    /**
     * 土地标题
     */
    @ApiModelProperty(value = "土地标题")
    @Length(min =0,max=60,message="土地标题长度不能超过20")
    private String productname;
    /**
     * 流转方式
     */
    @ApiModelProperty(value = "流转方式")
    @NotBlank(message="流转方式不能为空")
    private String transMode;
    /**
     * 所在区域
     */
    @ApiModelProperty(value = "所在区域")
    @NotBlank(message="所在区域不能为空")
    private String location;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;
    /**
     * 土地介绍
     */
    @ApiModelProperty(value = "土地介绍")
    @Length(min =0,max=300,message="土地介绍长度不能超过300")
    private String productDesc;
    /**
     * 土地联系人
     */
    @ApiModelProperty(value = "土地联系人")
    @Length(min =0,max=20,message="土地联系人长度不能超过20")
    private String landContact;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 发布人
     */
    @ApiModelProperty(value = "发布人")
    private String reporter;
    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reporttime;
    /**
     * 土地面积
     */
    @ApiModelProperty(value = "土地面积")
    private Integer acreage;
    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String purpose;
    /**
     * 流转年限
     */
    @ApiModelProperty(value = "流转年限")
    private Integer rentyear;
    /**
     * 土地价格
     */
    @ApiModelProperty(value = "土地价格")
    private BigDecimal price;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatetime;
    /**
     * 土地类型
     */
    @ApiModelProperty(value = "土地类型")
    private String landType;
    /**
     * 证权类型
     */
    @ApiModelProperty(value = "证权类型")
    private String righttype;
    /**
     * 证权有效期
     */
    @ApiModelProperty(value = "证权有效期")
    private String rightexpirdate;
    /**
     * 权属类型
     */
    @ApiModelProperty(value = "权属类型")
    private String ownership;
    /**
     * 供水条件
     */
    @ApiModelProperty(value = "供水条件")
    private String watersupply;
    /**
     * 供电条件
     */
    @ApiModelProperty(value = "供电条件")
    private String powersupply;
    /**
     * 网络条件
     */
    @ApiModelProperty(value = "网络条件")
    private String netsupply;
    /**
     * 机械化程度
     */
    @ApiModelProperty(value = "机械化程度")
    private String mechanization;
    /**
     * 土质
     */
    @ApiModelProperty(value = "土质")
    private String soil;
    /**
     * 灌溉条件
     */
    @ApiModelProperty(value = "灌溉条件")
    private String irrigation;
    /**
     * 排水条件
     */
    @ApiModelProperty(value = "排水条件")
    private String drainage;
    /**
     * 土壤酸碱度
     */
    @ApiModelProperty(value = "土壤酸碱度")
    private String phsoil;
    /**
     * 平整度
     */
    @ApiModelProperty(value = "平整度")
    private String flatness;
    /**
     * 所在城市年收入
     */
    @ApiModelProperty(value = "所在城市年收入")
    private String aum;
    /**
     * 城市化率
     */
    @ApiModelProperty(value = "城市化率")
    private String cityrate;
    /**
     * 常驻人口
     */
    @ApiModelProperty(value = "常驻人口")
    private String residentpop;
    /**
     * 附近产业群
     */
    @ApiModelProperty(value = "附近产业群")
    private String neargroup;
    /**
     * 国道高速
     */
    @ApiModelProperty(value = "国道高速")
    private String highway;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgs;
    /**
     * 土地标签
     */
    @ApiModelProperty(value = "土地标签")
    private String landNote;
    /**
     * 带看模式
     */
    @ApiModelProperty(value = "带看模式")
    private String lookmodel;
    /**
     * 权限
     */
    @ApiModelProperty(value = "权限")
    private Integer rightscope;
    /**
     * 审批状态（审核中01，审核通过02,审核不通过03）
     */
    @ApiModelProperty(value = "审核状态（审核中01，审核通过02,审核不通过03）")
    private String approvalStat;//landstat;
    /**
     * 审批人
     */
    @ApiModelProperty(value = "审批人")
    private String reviewer;
    /**
     * 审批时间
     */
    @ApiModelProperty(value = "审批时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewtime;
    /**
     * 服务商ID
     */
    @ApiModelProperty(value = "服务商ID")
    private String faciid;
//    /**
//     * 地源情况(01待审核，02待交易，03交易完成，04待申诉，05申诉中，06申诉失败，07暂停，08已过期)
//     */
//    @ApiModelProperty(value = "地源情况(01待交易，02交易完成，03待审核，04待申诉，05申诉中，06申诉失败，07暂停，08已过期)")
//    private String groundSource;
//    /**
//     * 代理情况(01-未确认，02-已代理)
//     */
    @ApiModelProperty(value = "代理情况(01-未确认，02-已代理)")
    private String agencySituation;
//    /**
//     * 申请取消代理状况（01申请中，02未申请）
//     */
//    @ApiModelProperty(value = "申请取消代理状况（01申请中，02未申请）")
//    private String aFCOAstatus;
    
    /**
     *暂停（01为暂停不在首页显示，02为不暂停)
     */
    @ApiModelProperty(value = "暂停（01为暂停不在首页显示，02为不暂停)")
    private String earthSourceNote;
    /**
     * 土地来源
     */
    @ApiModelProperty(value = "土地来源")
    private String landSource;
//    /**
//     * 可否申请代理(01可以，02不可以)
//     */
//    @ApiModelProperty(value = "可否申请代理(01可以，02不可以)")
//    private String applyAgent;
    /**
     * 经纪人id
     */
    @ApiModelProperty(value = "经纪人id")
    private String agentId;
    /**
     * 土地状态
     */
    @ApiModelProperty(value = "土地状态")
    private String landState;
    /**
     * 土地设施
     */
    @ApiModelProperty(value = "土地设施")
    private String landFacilities;
    /**
     * 场地属性
     */
    @ApiModelProperty(value = "场地属性")
    private String siteProperties;
    /**
     * 距机场距离
     */
    @ApiModelProperty(value = "距机场距离")
    private String awayAirport;
    /**
     * 距高速距离
     */
    @ApiModelProperty(value = "距高速距离")
    private String awayExpressway;
    /**
     * 距国道
     */
    @ApiModelProperty(value = "距国道")
    private String nationalHighway;
    /**
     * 距高铁
     */
    @ApiModelProperty(value = "距高铁")
    private String speedRailway;
    /**
     * 距港口
     */
    @ApiModelProperty(value = "距港口")
    private String awayPort;
    /**
     * 距城市
     */
    @ApiModelProperty(value = "距城市")
    private String awayCity;
    /**
     * 是否选择代理
     */
    @ApiModelProperty(value = "是否选择代理")
    private String proxy;
    /**
     * 封面
     */
    @ApiModelProperty(value = "封面")
    private String coverimg;
    /**
     * 电话核实
     */
    @ApiModelProperty(value = "电话核实")
    private String phoneCheck;
    /**
     * 现场核实
     */
    @ApiModelProperty(value = "现场核实")
    private String fieldCheck;
    /**
     * 通讯
     */
    @ApiModelProperty(value = "通讯")
    private String communication;
    /**
     * 地上设施
     */
    @ApiModelProperty(value = "地上设施")
    private String groundFacilitie;
    
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
    
    /**
     * 备用号码
     */
    @ApiModelProperty(value = "备用号码")
    private String spareNumber;
    
    @Override
    protected Serializable pkVal() {
        return this.productid;
    }
    
   
}
