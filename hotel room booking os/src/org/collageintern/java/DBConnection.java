package org.collageintern.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
		String URL = "jdbc:mysql://localhost:3306/hotel_db";
		String USER = "root";
		String PASS = "ASDFGHJK123@";

		return DriverManager.getConnection(URL, USER, PASS);
	}
}
