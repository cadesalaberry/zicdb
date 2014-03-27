package ui;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import db.Account;
import db.Connector;
import db.Reader;
import db.SimpleWriter;

/**
 * A low level DB writer for postgreSQL.
 * 
 * @author bensous
 * 
 */
public class cli {

	public enum State {
		INDEX, HOME, SIGNIN, SIGNUP, VIEWARTISTS, VIEWALBUMS, VIEWALBUM, ADDSONG, VIEWPLAYLISTS, VIEWLIKEDPLAYLIST, VIEWLIKEDPLAYLISTS, VIEWPLAYLIST, VIEWMYPLAYLISTS, VIEWMYPLAYLIST, CREATEPLAYLIST
	}

	public static final String DEFAULT_USER = "benj";
	public static final String DEFAULT_PASS = "poops";

	public static Connection conn = Connector.getConnection();
	public static SimpleWriter sw = new SimpleWriter(conn);
	public static Account ac = new Account(sw);
	public static Reader r = new Reader(conn);

	public static void main(String[] args) {

		Scanner keyb = new Scanner(System.in);
		State state = State.INDEX;

		boolean quit = false;

		String user = "benj";
		String input;

		int playlist = 0;
		int artist = 0;
		int album = 0;

		System.out
				.println("Welcome to ZicDb. Where all you zicz belong to us ");
		while (!quit) {
			switch (state) {
			case INDEX:
				System.out.println();
				System.out.println("ZicDB -- INDEX ");
				System.out
						.println("Enter a number to chose one of the options below ");
				System.out.println("1 - Login");
				System.out.println("2 - Signup");
				System.out.println("3 - Quit");

				switch (Integer.parseInt(keyb.nextLine())) {
				case 1:
					state = State.SIGNIN;
					break;
				case 2:
					state = State.SIGNUP;
					break;
				case 3:
					quit = true;
					break;
				default:
					System.out.println("Invalid INPUT!!!");
					break;
				}
				break;

			case HOME:
				System.out.println();
				System.out.println("ZicDB -- HOME ");
				System.out.println("Hello, " + user + ", chose an option! ");
				System.out.println("1 - Browse Songs ");
				System.out.println("2 - Browse Playlists ");
				System.out.println("3 - Browse Liked Playlists ");
				System.out.println("4 - Browse my Playlists ");
				System.out.println("5 - Create New Playlist");
				System.out.println("6 - Logout");

				switch (Integer.parseInt(keyb.nextLine())) {
				case 1:
					state = State.VIEWARTISTS;
					break;
				case 2:
					state = State.VIEWPLAYLISTS;
					break;
				case 3:
					state = State.VIEWLIKEDPLAYLISTS;
					break;
				case 4:
					state = State.VIEWMYPLAYLISTS;
					break;
				case 5:
					state = State.CREATEPLAYLIST;
					break;
				case 6:
					System.out.println("GOODBYE, " + user + "!!");
					state = State.INDEX;
					break;

				}
				break;

			case SIGNIN:
				System.out.println();
				System.out.println("ZicDB -- SIGNIN ");

				System.out.print("Enter username: ");
				String inputUser = keyb.nextLine();

				System.out.print("Enter password: ");
				String inputPassword = keyb.nextLine();

				if (isValidUser(inputUser, inputPassword)) {
					user = inputUser;
					state = State.HOME;
				} else {
					System.out.println("No account match those credentials!");
					state = State.INDEX;
				}
				break;

			case SIGNUP:
				System.out.println();
				System.out.println("ZicDB -- SIGNUP ");

				System.out.print("Choose a username: ");
				user = keyb.nextLine();

				if (isExistingUser(user)) {
					System.out.println("Username already exists!");
					state = State.INDEX;
				} else {
					System.out.print("Choose a password: ");
					input = keyb.nextLine();

					System.out.print("Confirm  password: ");
					String password_conf = keyb.nextLine();

					if (input.equals(password_conf)) {
						signUpUser(user, password_conf);
						System.out.println("Account created successfully!");
						state = State.INDEX;
					} else {
						System.out
								.println("Password confirmation does not match password!");
						state = State.INDEX;
					}
				}
				break;

			case VIEWARTISTS:

				System.out.println();
				System.out.println("ZicDB -- BROWSE SONGS ");
				viewArtists();
				System.out
						.println("Enter the artist number you want to browse or type 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else {
					artist = Integer.parseInt(input);
					state = State.VIEWALBUMS;
				}

				break;

			case VIEWALBUMS:

				System.out.println();
				System.out.println("ZicDB -- BROWSE SONGS ");
				viewAlbums(artist);
				System.out
						.println("Enter the album number you want to browse or type 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.VIEWARTISTS;
				else {
					album = Integer.parseInt(input);
					state = State.VIEWALBUM;
				}

				break;

			case VIEWALBUM:

				System.out.println();
				System.out.println("ZicDB -- BROWSE SONGS ");
				viewAlbum(album);
				System.out
						.println("Type 'add' to add a song or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.VIEWALBUMS;
				else if (input.equals("add")) {
					state = State.ADDSONG;
				}
				break;

			case ADDSONG:
				System.out.print("Enter song name: ");
				String inputSong = keyb.nextLine();

				System.out.print("Enter link: ");
				String inputLink = keyb.nextLine();

				System.out.println("Enter link source: ");
				String inputLinkSource = keyb.nextLine();

				System.out.println(addSongToAlbum(album, inputSong, inputLink,
						inputLinkSource));
				state = State.VIEWALBUM;

				break;

			case VIEWMYPLAYLISTS:

				System.out.println();
				System.out.println("ZicDB -- BROWSE MY PLAYLISTS ");
				viewMyPlaylists(user);
				System.out
						.println("Enter the playlist number to browse it or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else {
					playlist = Integer.parseInt(input);
					state = State.VIEWPLAYLIST;
				}
				break;

			case VIEWLIKEDPLAYLISTS:
				System.out.println();
				System.out.println("ZicDB -- BROWSE MY LIKED PLAYLISTS ");
				viewLikedPlaylists(user);
				System.out
						.println("Enter the playlist number to browse it or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else {
					playlist = Integer.parseInt(input);
					state = State.VIEWPLAYLIST;
				}
				break;

			case VIEWPLAYLISTS:
				System.out.println();
				System.out.println("ZicDB -- BROWSE PLAYLISTS ");
				viewPlaylists();
				System.out
						.println("Enter the playlist number to browse it or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else {
					playlist = Integer.parseInt(input);
					state = State.VIEWPLAYLIST;
				}
				break;

			case VIEWPLAYLIST:
				System.out.println();
				System.out.println("ZicDB -- BROWSE PLAYLIST ");
				viewPlaylist(playlist);
				System.out
						.println("Type 'like' to like playlist or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else if (input.equals("like")) {
					likePlaylist(user, playlist);
					state = State.HOME;
				} else {
					System.out.println("INPUT NOT RECOGNIZED!");
				}
				break;

			case VIEWMYPLAYLIST:
				System.out.println();
				System.out.println("ZicDB -- BROWSE PLAYLIST ");
				viewPlaylist(playlist);
				System.out.println("Type 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else {
					System.out.println("INPUT NOT RECOGNIZED!");
				}
				break;

			case VIEWLIKEDPLAYLIST:
				System.out.println();
				System.out.println("ZicDB -- BROWSE PLAYLIST ");
				viewPlaylist(playlist);
				System.out
						.println("Type 'unlike' to unlike playlist or 'back' to go back");

				input = keyb.nextLine();
				if (input.equals("back"))
					state = State.HOME;
				else if (input.equals("unlike")) {
					unlikePlaylist(user, playlist);
					state = State.HOME;
				} else {
					System.out.println("INPUT NOT RECOGNIZED!");
				}
				break;

			case CREATEPLAYLIST:
				System.out.println("Playlist created!");
				state = State.HOME;
				break;
			}

		}
		keyb.close();

	}

	/**
	 * Checks if the credentials of the user are valid.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean isValidUser(String username, String password) {
		return ac.loginUser(username, password) != -1;
	}
	
	/**
	 * Signs up a user.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean signUpUser(String username, String password) {
		return ac.signUp(username, password);
	}

	/**
	 * Checks if a user already exists in the database.
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isExistingUser(String username) {
		return ac.userExists(username);
	}

	public static void viewArtists() {
		System.out.println("--- VIEWING ALL THE ARTISTS -- ");

	}

	public static void viewAlbums(int artist) {
		System.out.println("--- VIEWING ALL ALBUMS for artist " + artist
				+ " -- ");
		
		// TODO: change to songID
		for (HashMap<String, String> h : r.getSongsFromAlbum(artist)) {
			System.out.println(h.get("artist_id") + "\t" + h.get("name"));
		}

	}

	public static void viewAlbum(int albumID) {
		System.out.println("--- VIEWING ALL SONGS in album -- " + albumID + "--");
		
		// TODO: change to artistID
		for (HashMap<String, String> a : r.getAlbumsFromArtist(albumID)) {
			System.out.println(a.get("album_id") + "\t" + a.get("name"));
		}
	}

	public static String addSongToAlbum(int album, String song, String link,
			String linkSource) {
		boolean success = true;
		if (success) {
			return "Song successfully added";
		} else {
			return "FAILURE TO ADD SONG";
		}
	}

	public static void viewPlaylists() {
		System.out.println("--- VIEWING ALL PLAYLISTS -- ");
	}

	public static void viewMyPlaylists(String user) {
		System.out
				.println("--- VIEWING ALL PLAYLISTS BY USER " + user + " -- ");
	}

	public static void viewLikedPlaylists(String user) {
		System.out.println("--- VIEWING ALL PLAYLISTS LIKED BY USER " + user
				+ " -- ");
	}

	public static void viewPlaylist(int playlistID) {
		System.out.println("--- VIEWING ALL SONGS in playlist -- " + playlistID
				+ "--");
		for (HashMap<String, String> h : r.getSongsFromPlaylist(playlistID)) {
			System.out.println(h.get("playlist_id") + "\t" + h.get("name"));
		}
	}

	public static void likePlaylist(String user, int playlist) {
		System.out.println("--- USER " + user + " likes playlist " + playlist);
		sw.likePlaylistFromUser(playlist,user);
	}

	public static void unlikePlaylist(String user, int playlist) {
		System.out
				.println("--- USER " + user + " unlikes playlist " + playlist);
		sw.unlikePlaylistFromUser(playlist,user);
	}

}
