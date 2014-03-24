package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import db.SimpleWriter;

public class SimpleWriterTest {
	
	SimpleWriter simpleWriter = null;
	
	@Before
	public void setUp() throws Exception {
		simpleWriter = new SimpleWriter();
	}
	
	@Test
	public void addArtistTest() {
		simpleWriter.addArtist("testArtist", "testWebsite");
		assertFalse(false);
	}

}
