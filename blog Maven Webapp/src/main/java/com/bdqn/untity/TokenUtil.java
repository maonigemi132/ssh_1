package com.bdqn.untity;

import java.util.UUID;

public class TokenUtil {

	public static String encodeStr="lian";
	
	
	public static String  getToKen(){
		
		
		String str=UUID.randomUUID().toString().replace("-", "");
		return str;
	}
}
