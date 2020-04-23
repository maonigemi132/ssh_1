package com.bdqn.untity;

/**
 * 状态枚举
 * @author RP
 *
 */
public enum StatusType {

	AVAILABLE(0),
	DISABLE(1);
	
	
	private int value;
	
	StatusType(int value){
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
}
