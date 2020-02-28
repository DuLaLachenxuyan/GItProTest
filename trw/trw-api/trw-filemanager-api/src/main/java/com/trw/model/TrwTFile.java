package com.trw.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-17
 */
@TableName("TRW_T_FILE")
public class TrwTFile extends Model<TrwTFile> {

	private static final long serialVersionUID = -5809496722482598414L;
	/**
     * 主键id
     */
    @TableId(value="f_id",type=IdType.INPUT)
    private String fId;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 扩展名
     */
    private String ext;
    /**
     * 过期时间
     */
    @TableField("exp_date")
    private Date expDate;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;


    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    protected Serializable pkVal() {
        return this.fId;
    }

}
