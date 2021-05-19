/**
 * 
 */
package com.excilys.cdb.persistance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;


class DatabaseTest {

	/**
	 * Test method for {@link com.excilys.cdb.persistance.Database#getConnection()}.
	 */
	@Test
	void testGetConnection() {
		Connection connection = null;
		try {
			connection = Database.getConnection();
			assertNotNull(connection);
		} catch (SQLException e) {
			fail("Connection is null");
		}
		
		try {
			connection.createStatement();
		} catch (SQLException e) {
			fail("Cannot create statement");
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			fail("Cannot close connection");
		}
	}
}
