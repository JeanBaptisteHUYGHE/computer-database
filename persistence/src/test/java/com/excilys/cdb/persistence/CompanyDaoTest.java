package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.excilys.cdb.config.SpringTestConfig;
import com.excilys.cdb.persistence.exception.CompanyNotFoundException;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;

@ContextConfiguration(classes = SpringTestConfig.class)
class CompanyDaoTest {
	
	@Autowired
	private static CompanyDao companyDao;
	@Autowired
	private static DataSource dataSource;
	
	private static AnnotationConfigApplicationContext applicationContext;
	private static Connection connection;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		applicationContext = new AnnotationConfigApplicationContext(SpringTestConfig.class);
		companyDao = applicationContext.getBean(CompanyDao.class);
		dataSource = applicationContext.getBean(DataSource.class);
		
		connection = dataSource.getConnection();
		assertNotNull(companyDao);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		companyDao = null;
		applicationContext.close();
	}
	
	@BeforeEach
	void setUp() throws Exception {
		connection = dataSource.getConnection();
	}

	@AfterEach
	void tearDown() throws Exception {
		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM company");
		connection.close();
	}

	@Test
	void testGetInstanceShlouldNotBeNull() {
		assertNotNull(companyDao);
	}
	
	@Test
	void testCountZeroCompany() {
		try {
			assertEquals(companyDao.getCompaniesCount(), 0);
		} catch (DatabaseConnectionException e) {
			fail(e.toString() + " in " + e.getStackTrace());
		}
	}
	
	@Test
	void testCountOneCompany() {
		try {
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO COMPANY (id, name) VALUES (1, 'My Company')");
			assertEquals(companyDao.getCompaniesCount(), 1);
		} catch (SQLException | DatabaseConnectionException e) {
			fail(e.toString() + " in " + e.getStackTrace());
		}
	}
	
	@Test
	void testCountTwoCompany() {
		try {
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO COMPANY (id, name) VALUES (1, 'My Company'), (2, 'My second company')");
			assertEquals(companyDao.getCompaniesCount(), 2);
		} catch (SQLException | DatabaseConnectionException e) {
			fail(e.toString() + " in " + e.getStackTrace());
		}
	}
	
	@Test
	void testGetCompaniesListPage() {
		Page mockedPage = mock(Page.class);
		when(mockedPage.getIndex()).thenReturn(0);
		when(mockedPage.getSize()).thenReturn(10);
		
		try {
			List<Company> companiesList = companyDao.getCompaniesListPage(mockedPage);
			assertNotNull(companiesList);
			assertEquals(companiesList.size(), 0);
		} catch (DatabaseConnectionException e) {
			fail("Cannot get companies list");
		}
	}

	
	@Test
	void testGetCompanySouldThrowCompanyNotFoundException() {
		assertThrows(CompanyNotFoundException.class, () -> {
			companyDao.getCompanyById(-1);
		});
	}
	/*

	@Test
	void testGetCompanyNotFound() {
		assertThrows(NoSuchElementException.class, () -> {
			companyDao.getCompany(new Company(-1, null));
		});
	}

	@Test
	void testGetCompanySuccess() {
		try {
			Connection connection = Database.getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO company(id, name) values (4, 'MyCompany')");
			statement.close();
			connection.close();
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
	}*/

}
