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
		this.conn = connection;
	}

	/**
	 * Returns the names of albums belonging to a given artist.
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, String>> getAlbumsFromUserID(int userID) {

		String query = "SELECT C.collection_id, C.name"
				+ " FROM collections C, user_has_playlist P, users U"
				+ " WHERE C.collection_id = P.collection_id"
				+ " AND   P.user_id = "	+ userID + ";";

		return getColumnsFromQuery(query);
	}

	/**
	 * Returns the names of songs inside a given playlist.
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, String>> getSongsFromPlaylist(int playlistID) {

		String query = "SELECT S.song_id, S.name"
				+ " FROM songs S, song_in_playlist SP, playlist P, collections C"
				+ " WHERE S.song_id = SP.song_id"
				+ " AND   SP.playlist_id = P.playlist_id"
				+ " AND   P.playlist_id = " + playlistID + ";";

		return getColumnsFromQuery(query);
	}

	/**
	 * Returns the names of albums belonging to a given artist.
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, String>> getAlbumsFromArtist(int artistID) {

		String query = "SELECT C.collection_id, C.name"
				+ " FROM collections C, user_has_playlist P, users U"
				+ " WHERE C.collection_id = P.collection_id"
				+ " AND   P.user_id = "	+ artistID + ";";

		return getColumnsFromQuery(query);
	}

	/**
	 * Returns the names of albums belonging to a given artist.
	 * 
	 * @param username
	 * @return
	 */
	public List<HashMap<String, String>> getSongsFromAlbum(int albumID) {

		String query = "SELECT S.song_id S.name"
				+ "FROM songs S, song_in_album SA, albums A, collections C"
				+ "WHERE S.song_id = SA.song_id"
				+ "AND   SA.album_id = A.album_id"
				+ "AND   A.album_id = " + albumID + ";";

		return getColumnsFromQuery(query);
	}

	public List<HashMap<String, String>> getColumnsFromQuery(String query) {

		ResultSet rst = null;

		try {
			rst = conn.prepareStatement(query).executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSetToHashList(rst);
	}

	/**
	 * Helper to turn a result set into a list.
	 * 
	 * @param colName
	 * @param query
	 * @return
	 */
	public List<String> getColumnFromQuery(String colName, String query) {

		List<HashMap<String, String>> hList = getColumnsFromQuery(query);
		
		return getColumnFromHashList(colName, hList);
	}

	/**
	 * Turns a hashlist into a simple list.
	 * 
	 * @param colName
	 * @param hList
	 * @return
	 */
	public static List<String> getColumnFromHashList(String colName,
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
	public static List<HashMap<String, String>> resultSetToHashList(ResultSet rs) {

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
