/**
 * 
 */
package com.excilys.cdb.persistance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.exception.dao.DatabaseConnectionException;


class DatabaseTest {
	
	@Autowired
	private DataSource dataSource;

	/**
	 * Test method for {@link com.excilys.cdb.persistance.Database#getConnection()}.
	 */
	@Test
	void testGetConnection() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
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
