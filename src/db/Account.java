package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Account {

	private Connection conn = null;
	private SimpleWriter w = null;

	public Account(SimpleWriter w) {
		this.conn = w.getWriter().getConn();
		this.w = w;
	}

	public int loginUser(String username, String pass) {

		String query = "SELECT user_id FROM cs421g22.users WHERE username = '" + username
				+ "' AND password = '" + pass + "';";

		List<HashMap<String, String>> hList = columnsFromQuery(query);

		if (hList == null || hList.size() < 1) {
			return -1;
		}

		int id = Integer.parseInt(hList.get(0).get("user_id"));

		return id;
	}

	public boolean userExists(String username) {

		String query = "SELECT user_id, username FROM cs421g22.users U WHERE U.username = '" + username + "';";

		List<HashMap<String, String>> list = columnsFromQuery(query);

		if (list == null || list.size() < 1) {
			return false;
		}

		int id = Integer.parseInt(list.get(0).get("user_id"));

		return (id != -1);
	}

	public int getNextId(String tableName) {

		String query = "SELECT MAX(user_id) as max_val FROM " + tableName + ";";

		List<HashMap<String, String>> list = columnsFromQuery(query);

		if (list == null || list.size() < 1) {
			return -1;
		}
		
		System.out.println("" + list + list.size());
		int id = Integer.parseInt(list.get(0).get("max_value"));

		return id + 1;
	}

	public boolean signUp(String username, String password) {
		if (!userExists(username)) {
			
			String id = String.valueOf(getNextId("cs421g22.users"));
			ArrayList<Object> list = new ArrayList<Object>();
			
			list.add(id);
			list.add(username);
			list.add(password);
			
			try {
				w.getWriter().addStringsToTable("cs421g22.users", list);
				System.out.println("User " + username + " added to db.");
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		return false;
	}

	private List<HashMap<String, String>> columnsFromQuery(String query) {
		ResultSet rs = null;

		try {
			
			rs = conn.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Reader.resultSetToHashList(rs);
	}
}
