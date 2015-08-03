package com.cqupt.pub.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Kevin
 * 2013.07.16 created
 */
public class DecodeUtils {
	/**
	 * 
	 * @param source the <code>String</code> to be decoded
	 * @return the decoded String 
	 * 
	 */
	public static String decodeRequestString(String source){
		String str = "";
		try {
			if(source != null && !source.equals("")){
				str = java.net.URLDecoder.decode(source,"UTF-8");
				str = java.net.URLDecoder.decode(str,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//To prevent injection vulnerabilities
		return str.replaceAll("'", "").trim();
	}
	
	
	/**
	 * 
	 * @param source the <code>String</code> to be decoded
	 * @param replaceStr the <code>String</code> to be replaced if <code>source</code> is equals null or ""
	 * @return the decoded String 
	 */
	public static String decodeRequestString(String source, String replaceStr){
		String str = replaceStr;
		try {
			if(source != null && !source.equals("")){
				str = java.net.URLDecoder.decode(source,"UTF-8");
				str = java.net.URLDecoder.decode(str,"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//To prevent injection vulnerabilities
		return str.replaceAll("'", "").trim();
	}
	
	
	/**
	 * 
	 * @param source the <code>String</code> to be decoded
	 * @param expectStr the expecting <code>String</code> 
	 * @param replaceStr the <code>String</code> to be replaced if <code>source</code> is equals null , ""  or expectStr
	 * @return the decoded String 
	 */
	public static String decodeRequestString(String source, String expectStr, String replaceStr){
		String str = replaceStr;
		try {
			if(source != null && !source.equals("")){
				str = java.net.URLDecoder.decode(source,"UTF-8");
				str = java.net.URLDecoder.decode(str,"UTF-8");
				if(str.equals(expectStr)){
					str = replaceStr;
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//To prevent injection vulnerabilities
		return str.replaceAll("'", "").trim();
	}
}
