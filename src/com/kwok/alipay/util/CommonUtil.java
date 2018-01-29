package com.kwok.alipay.util;

import java.util.Random;

public class CommonUtil {

	public static String getOrderId(){
		
		String timePrefix = new Long(System.currentTimeMillis()).toString(); //13位
		String randomSuffix = new Integer(new Random().nextInt(1000)).toString();
		return timePrefix + randomSuffix;
		
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(getOrderId());
		
	}
	
}
