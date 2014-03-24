package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * A low level DB writer for postgreSQL.
 * 
 * @author cadesalaberry
 * 
 */
public class Writer {

	private Connection conn = null;

	public Writer() {
		conn = Connector.getConnection();
	}

	public void addStringsToTable(String tbName, List<Object> strings)
			throws SQLException {
		addToTable(tbName, strings, "");
	}

	/**
	 * Inserts a list of strings as values to the specified table. The returning
	 * value is the name of the field to be returned from the newly created
	 * entry (returning the id generated by the db).
	 * 
	 * @param tbName
	 * @param values
	 * @param returning
	 * @return
	 * @throws SQLException
	 */
	public int addToTable(String tbName, List<Object> values,
			String returning) throws SQLException {

		PreparedStatement pst = null;
		ResultSet res = null;
		String query = "";
		int id = -1;

		query = prepareInsertQuery(tbName, values.size(), returning);
		System.out.println(query);

		try {
			conn.setAutoCommit(false);

			pst = conn.prepareStatement(query);

			pst = fillInsertQuery(pst, values);

			// Reads the answer from the db if specified
			if (returning != "") {
				res = pst.executeQuery();
				res.next();
				id = res.getInt(1);
			} else {
				id = pst.executeUpdate();
			}

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				// In case of exception, tries rolling back
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// Closes the connection
			pst.close();
			res.close();
			conn.close();
		}

		return id;
	}
	
	
	/**
	 * Fills in the {@link PreparedStatement} according to their types.
	 * 
	 * @param pst
	 * @param values
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement fillInsertQuery(PreparedStatement pst,
			List<Object> values) throws SQLException {

		for (int i = 0; i < values.size(); ++i) {
			System.out.println(i);
			Object o = values.get(i);
			if (o instanceof String)
				pst.setString(i + 1, (String) o);
			else if (o instanceof Integer)
				pst.setInt(i + 1, (Integer) o);
		}

		return pst;
	}

	/**
	 * Builds the query from the given values.
	 * 
	 * @param tbName
	 * @param size
	 * @param returning
	 * @return
	 */
	private String prepareInsertQuery(String tbName, int size, String returning) {
		//
		String query = "";

		query += "INSERT INTO " + tbName + " VALUES (?";

		for (int i = 1; i < size; ++i) {
			query += ",?";
		}

		query += ")";

		if (returning != "") {
			query += " RETURNING " + returning;
		}

		return query;
	}
}