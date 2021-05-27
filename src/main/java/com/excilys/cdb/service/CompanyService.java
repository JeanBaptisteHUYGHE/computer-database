package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistance.CompanyDao;

public class CompanyService {
	
	private static CompanyService companyService = null;
	private Logger logger;

	
	/**
	 * Return the company service instance.
	 * @return the company service
	 */
	public static CompanyService getInstance() {
		if (companyService == null) {
			companyService = new CompanyService();
		}
		return companyService;
	}
	
	private CompanyService() {
		logger = LoggerFactory.getLogger(CompanyService.class);
	}

	/**
	 * Return the company.
	 * @param company the company
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Company getCompany(Company company) throws NoSuchElementException, SQLException {
		logger.debug("getCompany({})", company);
		Company gettedCompany = null;
		try {
			gettedCompany = CompanyDao.getInstance().getCompany(company);
		} catch (NoSuchElementException e) {
			logger.info("{} in {}", e, e.getStackTrace());
			throw e;
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
		return gettedCompany;
	}

	/**
	 * Return a list of companies.
	 * @return the companies list
	 * @throws SQLException
	 */
	public List<Company> getCompaniesList() throws SQLException {
		logger.debug("getCompaniesList()");
		List<Company> companiesList = null;
		try {
			companiesList = CompanyDao.getInstance().getCompaniesList();
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
		return companiesList;
	}
	/**
	 * Return a list of companies by page.
	 * @param pageIndex page index
	 * @param pageSize page size
	 * @return the companies list
	 * @throws SQLException
	 */
	public List<Company> getCompaniesListPage(int pageIndex, int pageSize) throws SQLException {
		logger.debug("getCompaniesListPage({}, {})", pageIndex, pageSize);
		List<Company> companiesList = null;
		try {
			companiesList = CompanyDao.getInstance().getCompaniesListPage(pageIndex, pageSize);
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
		return companiesList;
	}
}
