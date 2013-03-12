package org.onesy_Driver.test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CommandSrc {
	
	public static ArrayList<String> CommandSrc = new ArrayList<String>();
	
	public CommandSrc(){
		CommandSrc.add("");
	}
	
	public static void getMd5(String md5src){
		System.out.println(new BigInteger(calculateMD5(md5src)));
	}
	
	public static <T> byte[] calculateMD5(T src){
		String SRCStr = src.toString();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md5.update(SRCStr.getBytes());
		byte[] result = md5.digest();
		return result;
	}

}
