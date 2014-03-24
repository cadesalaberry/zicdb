package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.Connector;
import db.SimpleWriter;

public class SimpleWriterTest {

	SimpleWriter simpleWriter = null;
	Statement dropStmt = null;
	Statement populateStmt = null;
	Connection c = Connector.getConnection();
		
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	@Before
	public void setUp() throws Exception {
		simpleWriter = new SimpleWriter(c);
	}
	
	@After
	public void tearDown() throws Exception {
		dropStmt = c.createStatement();
		String sqlDrop = "DROP DATABASE CS421 CASCADE;";
		dropStmt.executeUpdate(sqlDrop);
		dropStmt.close();
		System.out.println("db has been dropped");
		c.close();
		System.out.println("connection has been terminated");

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
		simpleWriter.addSong((int)6, "test song", 300);
		assertFalse(false);
	}


}
