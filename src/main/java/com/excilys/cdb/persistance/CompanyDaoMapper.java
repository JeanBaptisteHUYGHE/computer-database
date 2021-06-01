package com.excilys.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DaoMapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;


public class CompanyDaoMapper {
	
	private static CompanyDaoMapper companyDaoMapper;
	private Logger logger;
	
	public static CompanyDaoMapper getInstance() {
		if (companyDaoMapper == null) {
			companyDaoMapper = new CompanyDaoMapper();
		}
		return companyDaoMapper;
	}
	
	private CompanyDaoMapper() {
		logger = LoggerFactory.getLogger(CompanyDaoMapper.class);
	}
	
	/**
	 * Read a company line from a result set and return the correspondent company object
	 * @param resultSet the result set
	 * @return the correspondent company
	 * @throws SQLException
	 */
	private Company readCompany(ResultSet resultSet) throws SQLException {
		CompanyBuilder companyBuilder = new CompanyBuilder();
		companyBuilder.withId(resultSet.getInt("id"));
		companyBuilder.withName(resultSet.getString("name"));
		return companyBuilder.build();
	}

	/**
	 * Return the companies list.
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return companies list
	 * @throws DaoMapperException
	 */
	public List<Company> fromResultSetToCompaniesList(ResultSet resultSet) throws DaoMapperException {
		try {
			ArrayList<Company> companiesList = new ArrayList<Company>();
			while (resultSet.next()) {
	            companiesList.add(readCompany(resultSet));
			}
	        return companiesList;
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DaoMapperException();
		}
	}

	/**
	 * Return the company.
	 * @param resultSet the result set
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Company fromResultSetToCompany(ResultSet resultSet) throws CompanyNotFoundException, DaoMapperException {
		try {
			resultSet.next();
			System.out.println(resultSet.getRow());
			if (resultSet.getRow() == 0) {
				throw new CompanyNotFoundException();
			}
	        return readCompany(resultSet);
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DaoMapperException();
		}
	}
}
