package com.trw.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.trw.vo.Tree;
/**
* @ClassName: BuildTree 
* @Description: 构建树菜单
* @author luojing
* @date 2018年7月12日 下午3:11:07 
*
 */
public class BuildTree {

	public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes) {
		if (nodes == null) {
			return null;
		}
		LinkedList<Tree<T>> topNodes = new LinkedList<>();
		for (Tree<T> children : nodes) {
			String pid = children.getParentId();
			if (pid == null || "0".equals(pid)) {
				topNodes.add(children);
				continue;
			}
			for (Tree<T> parent : nodes) {
				String id = parent.getId();
				if (id != null && id.equals(pid)) {
					parent.getChildren().add(children);
					continue;
				}
			}
		}
		
		Iterator<Tree<T>> it = topNodes.iterator();
		 while(it.hasNext()){
			 Tree<T> node = it.next();
			 if(node.getChildren().isEmpty()) {
				 it.remove();
			 }
		 }
		return topNodes;
	}

}