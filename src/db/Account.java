package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Account {

	private Connection conn = null;
	private Writer w = null;

	public Account(Writer w) {
		this.conn = w.getConn();
		this.w = w;
	}

	public int loginUser(String username, String pass) {

		String query = "SELECT user_id FROM users WHERE email = " + username
				+ " AND password = " + pass + ";";

		List<String> list = columnFromQuery("user_id", query);

		if (list == null || list.size() < 1) {
			return -1;
		}

		int id = Integer.parseInt(list.get(0));

		return id;
	}

	public boolean userExists(String username) {

		String query = "SELECT user_id FROM users WHERE email = " + username + ";";

		List<String> list = columnFromQuery("user_id", query);

		if (list == null || list.size() < 1) {
			return false;
		}

		int id = Integer.parseInt(list.get(0));

		return (id != -1);
	}

	public int getNextId(String tableName) {

		String query = "SELECT MAX(id) as max_val FROM " + tableName + ";";

		List<String> list = columnFromQuery("max_val", query);

		if (list == null || list.size() < 1) {
			return -1;
		}

		int id = Integer.parseInt(list.get(0));

		return id + 1;
	}

	public boolean signUp(String username, String password) {
		if (!userExists(username)) {
			
			String id = String.valueOf(getNextId("users"));
			ArrayList<Object> list = new ArrayList<Object>();
			
			list.add(id);
			list.add(username);
			list.add(password);
			
			try {
				w.addStringsToTable("users", list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		return false;
	}

	private List<String> columnFromQuery(String colName, String query) {
		ResultSet rs = null;

		try {
			rs = conn.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Reader.getColumnFromHashList(colName,
				Reader.resultSetToHashList(rs));
	}
}
