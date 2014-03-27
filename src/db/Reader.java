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

	/**
	 * Returns the names of playlist belonging to a given user.
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getPlaylistsFromUsername(String username) {

		String query = "SELECT C.collection_id, C.name"
				+ " FROM  collections C, user_has_playlist P, users U"
				+ " WHERE C.collection_id = P.collection_id"
				+ " AND   P.user_id = U.user_id" + " AND   U.username = "
				+ username + ";";

		return getColumnFromQuery("C.name", query);
	}

	/**
	 * Returns the names of songs inside a given playlist.
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getSongsFromPlaylist(String playlistName) {

		String query = "SELECT S.song_id, S.name"
				+ " FROM songs S, song_in_playlist SP, playlist P, collections C"
				+ " WHERE S.song_id = SP.song_id"
				+ " AND   SP.playlist_id = P.playlist_id"
				+ " AND   P.playlist_id = C.collection_id"
				+ " AND   C.name = ?;" + playlistName + ";";

		return getColumnFromQuery("S.name", query);
	}

	/**
	 * Returns the names of albums belonging to a given artist.
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getAlbumsFromArtist(String artistName) {

		String query = "SELECT C.collection_id, C.name"
				+ " FROM collections C, user_has_playlist P, users U"
				+ " WHERE C.collection_id = P.collection_id"
				+ " AND   P.user_id = U.user_id" + " AND   U.username = "
				+ artistName + ";";

		return getColumnFromQuery("C.name", query);
	}

	/**
	 * Returns the names of albums belonging to a given artist.
	 * 
	 * @param username
	 * @return
	 */
	public List<String> getSongsFromAlbum(String albumName) {

		String query = "SELECT S.song_id S.name"
				+ "FROM songs S, song_in_album SA, albums A, collections C"
				+ "WHERE S.song_id = SA.song_id"
				+ "AND   SA.album_id = A.album_id"
				+ "AND   A.album_id = C.collection_id" + "AND   C.name = "
				+ albumName + ";";

		return getColumnFromQuery("S.name", query);
	}

	/**
	 * Helper to turn a result set into a list.
	 * 
	 * @param colName
	 * @param query
	 * @return
	 */
	private List<String> getColumnFromQuery(String colName, String query) {

		PreparedStatement pst = null;
		ResultSet rst = null;

		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, colName);
			rst = pst.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<HashMap<String, String>> hList = resultSetToHashList(rst);

		return getColumnFromHashList(colName, hList);
	}

	/**
	 * Turns a hashlist into a simple list.
	 * 
	 * @param colName
	 * @param hList
	 * @return
	 */
	private List<String> getColumnFromHashList(String colName,
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
