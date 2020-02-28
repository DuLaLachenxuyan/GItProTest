package com.trw.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-05-29
 */
@TableName("TRW_T_CONFIG")
public class TrwTConfig extends Model<TrwTConfig> {
   
	private static final long serialVersionUID = -2972890779067176068L;
	/**
     * 参数名
     */
    private String paramcode;
    /**
     * 参数值
     */
    private String paramvalue;
    /**
     * 参数备注
     */
    private String remark;


    public String getParamcode() {
        return paramcode;
    }

    public void setParamcode(String paramcode) {
        this.paramcode = paramcode;
    }

    public String getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(String paramvalue) {
        this.paramvalue = paramvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.paramcode;
    }

    @Override
    public String toString() {
        return "TrwTConfig{" +
        "paramcode=" + paramcode +
        ", paramvalue=" + paramvalue +
        ", remark=" + remark +
        "}";
    }
}
