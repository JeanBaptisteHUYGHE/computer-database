package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DaoMapperException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.enumeration.CompanyRequestEnum;
import com.excilys.cdb.persistance.mapper.CompanyDaoMapper;

@Repository
public class CompanyDao {
	
	@Autowired
	private DatabaseConnection databaseConnection;
	@Autowired
	private CompanyDaoMapper companyDaoMapper;
	private Logger logger;
	
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
		try (Connection dbConnection = databaseConnection.getConnection()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(CompanyRequestEnum.GET_ALL_COMPANIES.get());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Company> companiesList;
			try {
				companiesList = companyDaoMapper.fromResultSetToCompaniesList(resultSet);
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
		try (Connection dbConnection = databaseConnection.getConnection()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(CompanyRequestEnum.GET_ALL_COMPANIES_FOR_PAGE.get());
			preparedStatement.setInt(1, page.getSize());
			preparedStatement.setInt(2, page.getSize() * page.getIndex());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Company> companiesList;
			try {
				companiesList = companyDaoMapper.fromResultSetToCompaniesList(resultSet);
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
	 * @param companyId the company id
	 * @return the company
	 * @throws CompanyNotFoundException
	 * @throws DatabaseConnectionException
	 */
	public Company getCompanyById(Integer companyId) throws CompanyNotFoundException, DatabaseConnectionException {
		logger.debug("getCompanyById({})", companyId);
		try (Connection dbConnection = databaseConnection.getConnection()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(CompanyRequestEnum.GET_COMPANY_BY_ID.get());
			preparedStatement.setInt(1, companyId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Company gettedCompany = companyDaoMapper.fromResultSetToCompany(resultSet);
			
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
	
	/**
	 * Return the companies count.
	 * @return the companies number
	 * @throws DatabaseConnectionException
	 */
	public Integer getCompaniesCount() throws DatabaseConnectionException {
		logger.debug("getCompaniesCount()");
		try (Connection dbConnection = databaseConnection.getConnection()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(CompanyRequestEnum.GET_COMPANIES_COUNT.get());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Integer companiesCount = companyDaoMapper.fromResultSetToComputersCount(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return companiesCount;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}
}
