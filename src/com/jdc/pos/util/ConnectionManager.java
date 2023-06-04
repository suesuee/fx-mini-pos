package com.jdc.pos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/fx_mini_pos";
//	private static final String URL = "jdbc:mysql://127.0.0.1:3306/fx_mini_pos?useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "";
	
	public static Connection getDBConnection() throws SQLException {
		return DriverManager.getConnection(URL,USER,PASS);
	}
}
