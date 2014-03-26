package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {

	private Connection conn = null;

	public Reader(Connection connection) {
		conn = connection;
	}

	private List<String> getUserRecipe(int userId) {
		
		List<HashMap<String, String>> resultSet = conn.prepareStatement(
				"SELECT name FROM recipe WHERE user_id = " +
				String.valueOf(userId)).executeUpdate();
		
		List<String> list = new ArrayList<String>();

		for (HashMap<String, String> map : resultSet) {
			list.add(map.get("name"));
		}

		return list;
	}
	
	/**
	 * Builds the query from the given values.
	 * 
	 * @param tbName
	 * @param size
	 * @param returning
	 * @return
	 */
	private String prepareSelectQuery(String tbName, int size, String returning) {
		
		String query = "";

		query += "SELECT (?"; 

		for (int i = 1; i < size; ++i) {
			query += ",?";
		}

		query += ") FROM " + tbName + ")";

		return query;
	}
	
	
	/**
	 * Convert a result set entity to a hash list.
	 * https://github.com/timcolonel/comp421-project-d3/blob/master/src/main/SQLConnection.java
	 * 
	 * @author timcolonel
	 * @param rs
	 * @return
	 */
	private List<HashMap<String, String>> resultSetToHashList(ResultSet rs) {

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		try {

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();

				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String colName = rsmd.getColumnName(i + 1);
					map.put(colName, rs.getString(colName));
				}

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
