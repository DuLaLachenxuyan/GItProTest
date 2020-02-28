package com.trw.vo;

import java.io.Serializable;

import com.trw.model.TrwTLand;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NoMatchLand  extends TrwTLand implements Serializable {

	private static final long serialVersionUID = -8438673477194181328L;
	
	private String needIds;
	
	private String flg;


}
