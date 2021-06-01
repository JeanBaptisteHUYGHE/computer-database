package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.CompanyDao;

public class CompanyService {
	
	private static CompanyService companyService = null;

	
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
	
	private CompanyService() { }

	/**
	 * Return the company.
	 * @param company the company
	 * @return the company
	 * @throws CompanyNotFoundException 
	 * @throws DatabaseConnectionException 
	 */
	public Company getCompanyById(Integer id) throws CompanyNotFoundException, DatabaseConnectionException {
		Company gettedCompany = null;
		gettedCompany = CompanyDao.getInstance().getCompanyById(id);
		return gettedCompany;
	}

	/**
	 * Return a list of companies.
	 * @return the companies list
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesList() throws DatabaseConnectionException {
		List<Company> companiesList = null;
		companiesList = CompanyDao.getInstance().getCompaniesList();
		return companiesList;
	}
	/**
	 * Return a list of companies by page.
	 * @param page the page
	 * @return the companies list
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesListPage(Page page) throws DatabaseConnectionException {
		List<Company> companiesList = null;
		companiesList = CompanyDao.getInstance().getCompaniesListPage(page);
		return companiesList;
	}
}
