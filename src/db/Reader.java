package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	private List<String> getPlaylistFromUsername(String username) {

		String query = "SELECT C.collection_id, C.name"
				+ " FROM  collections C, user_has_playlist P, users U"
				+ " WHERE C.collection_id = P.collection_id"
				+ " AND   P.user_id = U.user_id" + " AND   U.username = ?;";
		
		List<HashMap<String, String>> hList = resultSetToHashList(rst);
		return columnFromHashList(username, hList);
	}

	private List<String> getColumnFromQuery(String colName, String query) {

		List<String> list = new ArrayList<String>();
		PreparedStatement pst = null;
		ResultSet rst = null;

		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, colName);
			rst = pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private List<String> columnFromHashList(String colName,
			List<HashMap<String, String>> hList) {

		List<String> list = new ArrayList<String>();

		for (HashMap<String, String> map : hList) {
			list.add(map.get(colName));
		}

		return list;
	}

	/**
	 * Convert a result set entity to a hash list.
	 * https://github.com/timcolonel/
	 * comp421-project-d3/blob/master/src/main/SQLConnection.java
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
