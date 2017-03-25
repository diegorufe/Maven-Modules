package com.rfBaseCore.utils;

public class RFUtils {
	
	@SuppressWarnings("rawtypes")
	public static String getSimpleName(Class className){
		return className.getSimpleName();
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSimpleNameLower(Class className){
		return getSimpleName(className).toLowerCase();
	}
	
	@SuppressWarnings("rawtypes")
	public static String getSimpleNameUpper(Class className){
		return getSimpleName(className).toUpperCase();
	}
}
