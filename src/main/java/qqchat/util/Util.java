
package com.github.zyxgad.qqchat.util;

import java.io.UnsupportedEncodingException;

public final class Util{
	private Util(){}

	public static String bytesToString(final byte[] bytes){
		if(bytes == null){
			return null;
		}
		try{
			return new String(bytes, "UTF-8");
		}catch(UnsupportedEncodingException e){
			return new String(bytes);
		}
	}

	public static byte[] stringToBytes(final String str){
		if(str == null){
			return null;
		}
		try{
			return str.getBytes("UTF-8");
		}catch(UnsupportedEncodingException e){
			return str.getBytes();
		}
	}
}