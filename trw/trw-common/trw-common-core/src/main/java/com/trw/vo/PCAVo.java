package com.trw.vo;

import java.io.Serializable;
import java.util.List;

public class PCAVo implements Serializable{
	
	private static final long serialVersionUID = -4231107570857932187L;
	private String id;
	private String name;
	private List<PCAVo> children;
	 
	public List<PCAVo> getChildren() {
		return children;
	}
	public void setChildren(List<PCAVo> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
