package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

public class CompanyDao {
	
	private static CompanyDao instance = null;
	private Logger logger;
	
	/**
	 * Return the company Dao instance (singleton).
	 * @return the company Dao instance
	 */
	public static CompanyDao getInstance() {
		if (instance == null) {
			instance = new CompanyDao();
		}
		return instance;
	}
	
	private CompanyDao() {
		logger = LoggerFactory.getLogger(CompanyDao.class);
	}
	
	/**
	 * Return the companies list from the database.
	 * @return the companies list page
	 * @throws SQLException
	 */
	public List<Company> getCompaniesList() throws SQLException {
		logger.debug("getCompaniesList()");
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name FROM company ORDER BY name";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Company> companiesList = new CompanyDaoMapper().getCompaniesList(resultSet);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
		return companiesList;
	}
	
	/**
	 * Return the companies list from the database in the page range.
	 * @param pageIndex the page index
	 * @param pageSize the page size
	 * @return the companies list page
	 * @throws SQLException
	 */
	public List<Company> getCompaniesListPage(int pageIndex, int pageSize) throws SQLException {
		logger.debug("getCompaniesListPage({}, {})", pageIndex, pageSize);
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name "
				+ "FROM company "
				+ "ORDER BY name "
				+ "LIMIT ? OFFSET ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		preparedStatement.setInt(1, pageSize);
		preparedStatement.setInt(2, pageSize * pageIndex);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Company> companiesList = new CompanyDaoMapper().getCompaniesList(resultSet);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
		return companiesList;
	}
	
	/**
	 * Return the company from the database.
	 * @param company the company (just the id is used)
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Company getCompany(Company company) throws NoSuchElementException, SQLException {
		if (company == null || company.getId() == null) {
			throw new NoSuchElementException("Company asked is null");
		}
		logger.debug("getCompany({})", company);
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name FROM company WHERE id = ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		preparedStatement.setInt(1, company.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		Company gettedCompany = new CompanyDaoMapper().getCompany(resultSet);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
		return gettedCompany;
	}
}
