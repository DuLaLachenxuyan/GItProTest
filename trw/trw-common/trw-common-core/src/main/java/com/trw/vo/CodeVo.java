package com.trw.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodeVo implements Serializable{

	private static final long serialVersionUID = -8551165694484969902L;

	private String dictype;
	private String dicname;
	private String dickey;
	private String remark;

	private List<CodeSunVo> children = new ArrayList<>();
	
	public void add(CodeSunVo e) {
		children.add(e);
	}

	public String getDictype() {
		return dictype;
	}

	public void setDictype(String dictype) {
		this.dictype = dictype;
	}

	public String getDicname() {
		return dicname;
	}

	public void setDicname(String dicname) {
		this.dicname = dicname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<CodeSunVo> getChildren() {
		return children;
	}

	public void setChildren(List<CodeSunVo> children) {
		this.children = children;
	}

	public String getDickey() {
		return dickey;
	}

	public void setDickey(String dickey) {
		this.dickey = dickey;
	}

	
	
}
