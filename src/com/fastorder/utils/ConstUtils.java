package com.fastorder.utils;

import java.io.File;

public class ConstUtils {
	
	public static final String USER_NOT_FOUND = "User not found";
	
	public static final String IMAGE_PATH = System.getProperty("user.dir")+File.separator+"imagesBdd";
	
	public static void main(String[] args) {
		System.out.println(IMAGE_PATH);
	}

}
