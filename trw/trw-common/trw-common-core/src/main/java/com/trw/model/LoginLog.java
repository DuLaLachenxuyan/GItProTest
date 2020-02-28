package com.trw.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author jianglingyun123
 * @since 2018-06-29
 */
@TableName("trw_login_log")
public class LoginLog extends Model<LoginLog> {

	private static final long serialVersionUID = 6205415437683572812L;
	/**
     * 日志名称
     */
    private String logname;
    /**
     * 管理员id
     */
    private String userid;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 是否执行成功
     */
    private String succeed;
    /**
     * 具体消息
     */
    private String msg;
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 主键id
     */
    @TableId(type=IdType.INPUT)
    private String id;


    public String getLogname() {
        return logname;
    } 

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    
}
