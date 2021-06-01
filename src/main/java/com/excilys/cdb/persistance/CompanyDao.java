package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DaoMapperException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyDao {
	
	private static CompanyDao instance = null;
	private Logger logger;
	
	private static final String REQUEST_GET_ALL_COMPANIES = "SELECT id, name FROM company ORDER BY name";
	private static final String REQUEST_GET_ALL_COMPANIES_FOR_PAGE = "SELECT id, name FROM company ORDER BY name LIMIT ? OFFSET ?";
	private static final String REQUEST_GET_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id = ?";

	
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
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesList() throws DatabaseConnectionException {
		logger.debug("getCompaniesList()");
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(REQUEST_GET_ALL_COMPANIES);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Company> companiesList;
			try {
				companiesList = CompanyDaoMapper.getInstance().fromResultSetToCompaniesList(resultSet);
			} catch (DaoMapperException e) {
				throw new DatabaseConnectionException();
			}
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return companiesList;
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
		
		
	}
	
	/**
	 * Return the companies list from the database in the page range.
	 * @param page the page
	 * @return the companies list page
	 * @throws DatabaseConnectionException
	 */
	public List<Company> getCompaniesListPage(Page page) throws DatabaseConnectionException {
		logger.debug("getCompaniesListPage({})", page);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(REQUEST_GET_ALL_COMPANIES_FOR_PAGE);
			preparedStatement.setInt(1, page.getSize());
			preparedStatement.setInt(2, page.getSize() * page.getIndex());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Company> companiesList;
			try {
				companiesList = CompanyDaoMapper.getInstance().fromResultSetToCompaniesList(resultSet);
			} catch (DaoMapperException e) {
				throw new DatabaseConnectionException();
			}
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return companiesList;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the company from the database.
	 * @param company the company (just the id is used)
	 * @return the company
	 * @throws CompanyNotFoundException
	 * @throws DatabaseConnectionException
	 */
	public Company getCompanyById(Integer companyId) throws CompanyNotFoundException, DatabaseConnectionException {
		logger.debug("getCompanyById({})", companyId);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(REQUEST_GET_COMPANY_BY_ID);
			preparedStatement.setInt(1, companyId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Company gettedCompany = CompanyDaoMapper.getInstance().fromResultSetToCompany(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return gettedCompany;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}
}
