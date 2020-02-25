package com.pro.util;
/**
 * 基础工具糊糊
 * @author Administrator
 *
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class BaseUtils {
	/**
	 * 获取请求中指定前缀的数据
	 */
	public static Map<String, String> getRequestParam(HttpServletRequest request,String prefix){
		Map<String, String> result=new HashMap<String,String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements())    
        {
  		  String t=paramNames.nextElement();
  		  if(t.indexOf(prefix) != -1) {
  			result.put(t, request.getParameter(t));
  		  }
        }
		return result;
	}
	/**
	 * 密码加密后字符串
	 * @throws NoSuchAlgorithmException 
	 */
	public static String  encryptionPassword(String password){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());
		String md_password=new BigInteger(md.digest()).toString(32);
		return md_password;
	}
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) { 
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); 
        return pattern.matcher(str).matches();
	}
}
