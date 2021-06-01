/**
 * 
 */
package com.excilys.cdb.persistance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
		Connection connection2 = null;
		try {
			connection = Database.getConnection();
		} catch (SQLException e) {
			fail("Connection throw SQL exception:" + e.getMessage());
		}
		assertNotNull(connection);

		try {
			connection2 = Database.getConnection();
		} catch (SQLException e) {
			fail("Connection throw SQL exception:" + e.getMessage());
		}
		assertNotNull(connection2);

		try {
			connection.createStatement();
		} catch (SQLException e) {
			fail("Cannot create statement");
		}
		
		assertSame(connection, connection2);
		
		try {
			connection.close();
		} catch (SQLException e) {
			fail("Cannot close connection");
		}
	}
}
