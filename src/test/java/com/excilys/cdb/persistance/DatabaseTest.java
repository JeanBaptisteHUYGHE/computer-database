/**
 * 
 */
package com.excilys.cdb.persistance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;


class DatabaseTest {

	/**
	 * Test method for {@link com.excilys.cdb.persistance.Database#getConnection()}.
	 */
	@Test
	void testGetConnection() {
		Connection connection = null;
		try {
			connection = DatabaseConnection.getInstance();
		} catch (DatabaseConnectionException e) {
			fail("Connection throw DatabaseConnectionException:" + e.getMessage());
		}
		assertNotNull(connection);
		
		try {
			connection.close();
		} catch (SQLException e) {
			fail("Cannot close connection");
		}
	}
}
