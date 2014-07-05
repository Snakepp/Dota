package com.dota.utils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class Encripter {
	
	private String encriptBytes(String cad) {
		 java.security.MessageDigest d = null;
		 byte[] passbyte = null;
		 try {
			d = java.security.MessageDigest.getInstance("SHA-1");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 try {
			passbyte = cad.getBytes("UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 passbyte = d.digest(passbyte);
		 String s = null;
		try {
			s = new String(passbyte, "US-ASCII");
		} catch(UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return s;
	}
	
	public String encript(String str) {

		
	    char[] chars = encriptBytes(str).toCharArray();

	    StringBuffer hex = new StringBuffer();
	    for (int i = 0; i < chars.length; i++) {
	        hex.append(Integer.toHexString((int) chars[i]));
	    }

	    return hex.toString();
	}
	
}
