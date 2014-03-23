package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	private final static String url = "jdbc:postgresql://localhost:5432/CS421";
	private final static String username = "cs421g22";
	private final static String password = "I want to die in the sky.";
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
