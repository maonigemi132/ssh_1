package com.bdqn.component;

import java.util.Arrays;
import java.util.List;

public enum ArticleStatus {

	AVAILABLR(0,"已发布"),
	DISABLR(1,"未发布"),
	DRAFTS(2,"草稿箱"),
	IN_AUDIT(3,"审核中"),
	NOT_PASS(4,"未通过"),
	PASS(5,"通过");
	
	private Integer value;
	private String name;
	
	
	
	
	private ArticleStatus(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	
	public static List<Integer> getNotDraftStatus(){
		return Arrays.asList(ArticleStatus.AVAILABLR.getValue(),ArticleStatus.DISABLR.getValue(),
				ArticleStatus.IN_AUDIT.getValue(),ArticleStatus.NOT_PASS.getValue(),ArticleStatus.PASS.getValue());
	}
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
	
	
	
	
	
}
