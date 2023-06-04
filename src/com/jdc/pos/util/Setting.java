package com.jdc.pos.util;

import java.io.FileInputStream;
import java.util.Properties; //Ctrl+Shift+O;

public class Setting {

	private static Properties prop;
	// properties value, key value twae atwal lyk save poh;1 khu htl shi mhr moh
	// static;

	// prop htl mhr admin nk pw save ml, key tanphoe pay yin value tan phoe htwt ag;

	// static var initialize poh, 1 time pl obj anay nk shi nay mhr;

	static {
		try {
			prop = new Properties();// a twin htl key,value structure nk save loh ya dk obj structure memory por mhr
									// shi nay mhr;
			prop.load(new FileInputStream("application.properties"));
			// file htl ka data phat poh, most important = byte stream (bl lo data m so read
			// loh ya loh)
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static String get(String key) { //pos.user.login win lr first time, pos.user.password ka second time win lr
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		Properties p = System.getProperties();
		p.list(System.out);
		
		String value = p.getProperty("path.separator");
		System.out.println(value);
	}
}
