/**
 * 
 */
package com.excilys.cdb.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;

/**
 * @author excilys
 *
 */
class CompanyDaoTest {
	
	private static CompanyDao companyDao;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		companyDao = CompanyDao.getInstance();
		assertNotNull(companyDao);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		companyDao = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		Connection connection = Database.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM company");
	}

	/**
	 * Test method for {@link com.excilys.cdb.persistance.CompanyDao#getInstance()}.
	 */
	@Test
	void testGetInstance() {
		CompanyDao companyDao2 = CompanyDao.getInstance();
		assertSame(companyDao, companyDao2);
	}

	/**
	 * Test method for {@link com.excilys.cdb.persistance.CompanyDao#getCompaniesListPage(int, int)}.
	 */
	@Test
	void testGetCompaniesListPage() {
		List<Company> companiesList = null;
		try {
			companiesList = companyDao.getCompaniesListPage(1, 10);
		} catch (SQLException e) {
			fail("Cannot get companies list");
		}
		assertNotNull(companiesList);
	}

	/**
	 * Test method for {@link com.excilys.cdb.persistance.CompanyDao#getCompany(com.excilys.cdb.model.Company)}.
	 */
	@Test
	void testGetCompanyNull() {
		assertThrows(NoSuchElementException.class, () -> {
			companyDao.getCompany(new Company(null, null));
		});
	}
	
	/**
	 * Test method for {@link com.excilys.cdb.persistance.CompanyDao#getCompany(com.excilys.cdb.model.Company)}.
	 */
	@Test
	void testGetCompanyNotFound() {
		assertThrows(NoSuchElementException.class, () -> {
			companyDao.getCompany(new Company(-1, null));
		});
	}
	
	/**
	 * Test method for {@link com.excilys.cdb.persistance.CompanyDao#getCompany(com.excilys.cdb.model.Company)}.
	 */
	@Test
	void testGetCompanySuccess() {
		try {
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO company(id, name) values (4, 'MyCompany')");
		} catch (SQLException e) {
			fail("SQLException in initialization: " + e + " in " + e.getStackTrace());
		}
		
		Company expectedCompany = new Company(4, "MyCompany");
		Company gettedCompany = null;
		try {
			gettedCompany = companyDao.getCompany(expectedCompany);
		} catch (NoSuchElementException | SQLException e) {
			fail(e + " in " + e.getStackTrace());
		}
		assertEquals(expectedCompany, gettedCompany);
	}

}
