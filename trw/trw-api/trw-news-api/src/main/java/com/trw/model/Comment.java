package com.trw.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment implements Serializable{

	private static final long serialVersionUID = 6942610730045505183L;
	private int commentId;
	private int newsId=-1;
	private String newsTitle;
	private String content;
	private String userIP;
	private Date commentDate;
	
	
	
	public Comment(int newsId, String content, String userIP) {
		super();
		this.newsId = newsId;
		this.content = content;
		this.userIP = userIP;
	}
	public Comment() {
	}
 
	
	
	
}
