package com.excilys.cdb.persistance;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.dto.mapper.CompanyRowMapper;
import com.excilys.cdb.persistance.enumeration.CompanyRequestEnum;
import com.excilys.cdb.persistance.enumeration.ComputerRequestEnum;

@Repository
public class CompanyDao {
	
	private Logger logger;

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private CompanyRowMapper companyRowMapper;
	
	public CompanyDao(DataSource dataSource, CompanyRowMapper companyRowMapper) {
		logger = LoggerFactory.getLogger(CompanyDao.class);
		
		jdbcTemplate = new JdbcTemplate(dataSource);
	    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.companyRowMapper = companyRowMapper;
	}
	
	/**
	 * Return the companies list from the database.
	 * @return the companies list page
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesList() throws DatabaseConnectionException {
		logger.debug("getCompaniesList()");
		try {
			List<Company> companiesList = jdbcTemplate.query(CompanyRequestEnum.GET_ALL_COMPANIES.get(), companyRowMapper);
			return companiesList;
			
		} catch (DataAccessException e) {
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
		
		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("pageSize", page.getSize())
					.addValue("offset", (page.getSize() * page.getIndex()));
			List<Company> companiesList = namedParameterJdbcTemplate.query(CompanyRequestEnum.GET_ALL_COMPANIES_FOR_PAGE.get(), requestParams, companyRowMapper);
			return companiesList;
			
		} catch (DataAccessException e) {
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
		
		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("id", companyId);
			List<Company> companiesList = namedParameterJdbcTemplate.query(CompanyRequestEnum.GET_COMPANY_BY_ID.get(),  requestParams, companyRowMapper);
			
			if (companiesList.isEmpty()) {
				throw new CompanyNotFoundException();
			}
			return companiesList.get(0);
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
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
		try {
			return jdbcTemplate.queryForObject(CompanyRequestEnum.GET_COMPANIES_COUNT.get(), Integer.class);
		
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Delete a company and all computer of this company from database.
	 * @param companyId the company id
	 * @throws DatabaseConnectionException
	 */
	@Transactional
	public void deleteCompanyById(Integer companyId) throws DatabaseConnectionException {
		logger.debug("deleteCompanyById()");
		try {
			SqlParameterSource requestParamsForComputers = new MapSqlParameterSource()
					.addValue("companyId", companyId);
			namedParameterJdbcTemplate.update(ComputerRequestEnum.DELETE_COMPUTERS_BY_COMPANY_ID.get(), requestParamsForComputers);
			SqlParameterSource requestParamsForCompany = new MapSqlParameterSource()
					.addValue("id", companyId);
			namedParameterJdbcTemplate.update(CompanyRequestEnum.DELETE_COMPANY_BY_ID.get(), requestParamsForCompany);
		
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
