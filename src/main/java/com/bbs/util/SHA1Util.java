package com.bbs.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {

	
	  public static String getSHA1String(String value) throws UnsupportedEncodingException {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA1");
	            byte[] e = md.digest(value.getBytes("UTF-8"));
	            return toHexString(e);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return value;
	        }
	    }
	
	  private static String toHexString(byte bytes[]) {
	        StringBuilder hs = new StringBuilder();
	        String stmp = "";
	        for (int n = 0; n < bytes.length; n++) {
	            stmp = Integer.toHexString(bytes[n] & 0xff);
	            if (stmp.length() == 1)
	                hs.append("0").append(stmp);
	            else
	                hs.append(stmp);
	        }
	 
	        return hs.toString();
	    }
}
