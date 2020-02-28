package com.trw.vo;

import java.io.Serializable;
/**
 * 接口token
 * @author Mr.jly
 *
 */
public class TokenData implements Serializable{

	private static final long serialVersionUID = -8695086424503028959L;
	
	/**
	 * 过期时间
	 */
	private long expDate ;
	
	private String sid;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getExpDate() {
		return expDate;
	}

	public void setExpDate(long expDate) {
		this.expDate = expDate;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	
	public TokenData() {
	}
	
	public TokenData(String sid, String userId) {
		super();
		this.sid = sid;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TokenData [expDate=" + expDate + ", sid=" + sid + ", userId=" + userId + "]";
	}
	
}
