package com.jdc.pos.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.Set;

public class PassEncryptor {
	
	public static String encrypt(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encode = digest.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(encode);//Byte Array to String;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;//Data m pr lr ma;
	}
	
	public static void main(String[] args) {
		System.out.println(encrypt("admin@123"));
//		showDigestAlgorithm();
	}
	
	@SuppressWarnings("unused")
	private static void showDigestAlgorithm() {
		Set<String> algorithms = Security.getAlgorithms("MessageDigest");
		for(String name : algorithms) {
			System.out.println(name);
		}
	}
}
