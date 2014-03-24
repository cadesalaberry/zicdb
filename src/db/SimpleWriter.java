package db;

import java.sql.SQLException;
import java.util.ArrayList;

public class SimpleWriter {

	Writer w = null;

	private String schema = "";
	private String linksTBL = schema + "links";
	private String songsTBL = schema + "songs";
	private String artistsTBL = schema + "artists";
	private String linkToSongTBL = schema + "link_to_song";
	private String albumToSongTBL = schema + "song_in_album";
	private String artistToSongTBL = schema + "song_by_artist";

	public SimpleWriter() {
		this("cs421g22");
	}

	public SimpleWriter(String schema) {

		this.schema = schema == "" ? "" : schema + ".";
		w = new Writer();
	}

	/**
	 * Adds an artist entry into the database. Returns the ID of the created
	 * entry.
	 * 
	 * @param name
	 * @param website
	 * @return
	 */
	public int addArtist(String name, String website) {

		ArrayList<Object> values = new ArrayList<Object>();
		int artistID = -1;

		values.add(name);
		values.add(website);

		try {

			artistID = w.addStringsToTable(artistsTBL, values, "artist_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return artistID;
	}

	/**
	 * Adds a link entry into the database. Returns the ID of the created entry.
	 * 
	 * @param linkURL
	 * @param provider
	 * @return
	 */
	public int addLink(String linkURL, String provider) {

		ArrayList<Object> values = new ArrayList<Object>();
		int linkID = -1;

		values.add(linkURL);
		values.add(provider);

		try {

			linkID = w.addStringsToTable(linksTBL, values, "link_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return linkID;
	}

	/**
	 * Adds a song entry to the database. Returns the ID of the created entry.
	 * 
	 * @param name
	 * @param length
	 * @return
	 */
	public int addSong(String name, int length) {

		ArrayList<Object> values = new ArrayList<Object>();
		int songID = -1;

		values.add(name);
		values.add(length);

		try {

			songID = w.addStringsToTable(songsTBL, values, "song_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return songID;
	}

	/**
	 * Creates a relation between the specified songID and linkID.
	 * 
	 * @param songID
	 * @param linkID
	 * @return
	 */
	public boolean matchLinkToSong(int songID, int linkID) {

		ArrayList<Object> values = new ArrayList<Object>();

		values.add(songID);
		values.add(linkID);

		try {

			w.addStringsToTable(linkToSongTBL, values);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Creates a relation between the specified songID and artistID.
	 * 
	 * @param songID
	 * @param artistID
	 * @return
	 */
	public boolean matchArtistToSong(int songID, int artistID) {

		ArrayList<Object> values = new ArrayList<Object>();

		values.add(songID);
		values.add(artistID);

		try {

			w.addStringsToTable(artistToSongTBL, values);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Creates a relation between the specified songID and albumID.
	 * 
	 * @param songID
	 * @param albumID
	 * @return
	 */
	public boolean matchAlbumToSong(int songID, int albumID) {

		ArrayList<Object> values = new ArrayList<Object>();

		values.add(songID);
		values.add(albumID);

		try {

			w.addStringsToTable(albumToSongTBL, values);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
