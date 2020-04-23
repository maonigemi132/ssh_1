package com.bdqn.untity;

/**
 * 使用枚举 规范result返回结果
 * @author Mr_Jin
 */
public enum ResultType {

	SUCCESS("10000"),
	ERROR("10001"),
	LOGIN("10002"),
	MANAGER("10003"),
	REDIRECT("10004"),
	REDIRECTACTION("10005"),
	EXCEPTION("1006"),
	NODATA("1007");
	
	private  String value="";
	private ResultType(String value) {
		this.value = value;
	}
	
	
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
}
