package com.dota.utils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class Encripter {
	
	public String encript2(String cad){
	        String generatedPassword = null;
	        try {
	            // Create MessageDigest instance for MD5
	        	java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	            //Add password bytes to digest
	            md.update(cad.getBytes());
	            //Get the hash's bytes 
	            byte[] bytes = md.digest();
	            //This bytes[] has bytes in decimal format;
	            //Convert it to hexadecimal format
	            StringBuilder sb = new StringBuilder();
	            for(int i=0; i< bytes.length ;i++)
	            {
	                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	            }
	            //Get complete hashed password in hex format
	            generatedPassword = sb.toString();
	        } 
	        catch (NoSuchAlgorithmException e) 
	        {
	            e.printStackTrace();
	        }
	        return generatedPassword;
	}
	
	
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
