package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	private final static String url = "jdbc:postgresql://bendodev.no-ip.org:5432/CS421";
	
	private final static String username = "cs421g22";
	private final static String password = "I want to dive in the sky.";
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getTestConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url + "TEST", username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
