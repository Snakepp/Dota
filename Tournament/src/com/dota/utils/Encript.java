package com.dota.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Deprecated
public class Encript {

	 @Deprecated
	 public String encrypt(String x) {
		    java.security.MessageDigest d = null;
		    try {
				d = java.security.MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		    d.reset();
		    try {
				d.update(x.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    
		    String encripted=null;
			try {
				encripted = new String(d.digest(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(x);
			System.out.println(encripted);
		    return encripted;
		  }
	
}
