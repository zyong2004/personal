package util;

import jodd.util.Base64;

public class Base64Util {

	
	public static void main(String[] args) {

		Base64 b = new Base64();
		System.out.println(Base64.encodeToString("中国"));
		System.out.println(Base64.decodeToString("5Lit5Zu9"));
		
	}

}
