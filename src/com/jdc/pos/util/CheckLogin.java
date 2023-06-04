package com.jdc.pos.util;

public class CheckLogin {

	public static void login(String loginId, String password) { //UI ka data ko controller ka lan poh;
		
//		System.out.println("User Input: "+password);
		
		//When login ID is empty
		if(loginId == null || loginId.isEmpty()) { //empty: nouk mhr obj m htwt yin or string length 0 nk pyn pay dr ll shi
			throw new MiniPosException("Login ID should not be empty !!!");
		}
		
		//When password is empty
		if(password == null || password.isEmpty()) {
			throw new MiniPosException("Password should not be empty !!!");
		}
		
		//When login doesn't match with the info in properties file(text encoding tamyo)
		if(!loginId.equals(Setting.get("pos.user.login"))) {
			throw new MiniPosException("Please check Login ID!!!");
		}
			
		//When password doesn't match with the info in properties file
		if(!password.equals(Setting.get("pos.user.password"))) {
			throw new MiniPosException("Please check password!!!");
		}
	}
}
