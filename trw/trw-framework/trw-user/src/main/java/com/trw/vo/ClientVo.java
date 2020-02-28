package com.trw.vo;

import com.trw.model.LandClient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientVo  extends LandClient{

	private static final long serialVersionUID = -1901422853175877225L;
	/**
	 * 经纪人name
	 */
	private String aname;
	/**
	 * 经纪人id
	 */
	private String id;
	
	 
}

