package com.trw.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class News implements Serializable{

	private static final long serialVersionUID = 6396895061834514165L;
	private Integer newsId;
	private String title;
	private String content;
	private Date publishDate;
	private String author;
	private Integer typeId=-1;
	private String typeName;
	private Integer click;
	private Integer isHead=0;
	private Integer isImage=0;
	private String imageName;
	private Integer isHot;
	
}
