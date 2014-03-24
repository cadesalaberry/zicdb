package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.Connector;
import db.SimpleWriter;

public class SimpleWriterTest {

	SimpleWriter simpleWriter = null;
	Statement drop = null;
	Statement populate = null;
	Connection c = null;

	@Before
	public void setUp() throws Exception {
		c = Connector.getTestConnection();
		String sqlPop = "";
		populate.executeUpdate(sqlPop);
		populate.close();
		simpleWriter = new SimpleWriter(c);
	}

	@Test
	public void addArtistTest() {
		simpleWriter.addArtist("testArtist", "testWebsite");
		assertFalse(false);
	}

	@Test
	public void addLinkTest() {
		simpleWriter.addLink("testLink", "testProvider");
		assertFalse(false);
	}

	@Test
	public void addSongTest() {
		simpleWriter.addSong("testSong", 6);
		assertFalse(false);
	}

	@After
	public void tearDown() throws Exception {
		drop = c.createStatement();
		String sqlDrop = "DROP DATABASE CS421TEST CASCADE;";
		drop.executeUpdate(sqlDrop);
		drop.close();
		c.close();

	}

}
