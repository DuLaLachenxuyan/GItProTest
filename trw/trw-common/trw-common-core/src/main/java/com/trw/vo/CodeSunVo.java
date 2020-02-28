package com.trw.vo;

import java.io.Serializable;

public class CodeSunVo implements Serializable{

	private static final long serialVersionUID = -6262830815565901825L;
	
	private String dictype;
	
	private String diclabel;
	
	private String diccode;
	
	private String min;
	
	private String max;
	
	private String remark;
	
	private String dickey;
	
	private String pkey;
	public String getDictype() {
		return dictype;
	}

	public void setDictype(String dictype) {
		this.dictype = dictype;
	}

	public String getDiclabel() {
		return diclabel;
	}

	public void setDiclabel(String diclabel) {
		this.diclabel = diclabel;
	}

	public String getDiccode() {
		return diccode;
	}

	public void setDiccode(String diccode) {
		this.diccode = diccode;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDickey() {
		return dickey;
	}

	public void setDickey(String dickey) {
		this.dickey = dickey;
	}
	
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
}
