package com.trw.vo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
* @ClassName: Tree 
* @Description: 返回前端菜单结构
* @author luojing
* @date 2018年7月12日 上午11:33:51 
*
 */
public class Tree<T> {
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 父ID
	 */
	private String parentId;
	/**
	 * 显示节点文本
	 */
	private String text;
	/**
	 * 节点是否被选中 true false
	 */
	private boolean checked = false;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 节点的子节点
	 */
	private List<Tree<T>> children = new ArrayList<Tree<T>>();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<Tree<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Tree<T>> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Tree() {
		super();
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
