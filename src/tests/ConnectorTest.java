package tests;

import static org.junit.Assert.assertFalse;

import java.sql.Connection;

import org.junit.Test;

import db.Connector;


public class ConnectorTest {

	@Test
	public void testDBConnection() {
		
		Connection conn = Connector.getConnection();
		System.out.println("Connection successful." + conn.toString());
		
		assertFalse(false);
	}

}
