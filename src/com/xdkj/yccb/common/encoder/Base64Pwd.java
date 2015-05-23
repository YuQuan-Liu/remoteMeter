package com.xdkj.yccb.common.encoder;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Pwd {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();
	
	public static String encode(String password){
		return encoder.encode(password.getBytes());
	}

	public static String decode(String code){
		String password = "";
		try {
			password = new String(decoder.decodeBuffer(code));
		} catch (IOException e) {
			
		}
		return password;
	}
}
