package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.exception.dao.CompanyNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.CompanyDao;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyDao companyDao;

	
	private CompanyService() { }

	/**
	 * Return the company with this id.
	 * @param id the company
	 * @return the company
	 * @throws CompanyNotFoundException 
	 * @throws DatabaseConnectionException 
	 */
	public Company getCompanyById(Integer id) throws CompanyNotFoundException, DatabaseConnectionException {
		Company gettedCompany = null;
		gettedCompany = companyDao.getCompanyById(id);
		return gettedCompany;
	}

	/**
	 * Return a list of companies.
	 * @return the companies list
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesList() throws DatabaseConnectionException {
		List<Company> companiesList = null;
		companiesList = companyDao.getCompaniesList();
		return companiesList;
	}
	/**
	 * Return a list of companies by page.
	 * @param page the page
	 * @return the companies list
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesListPage(Page page) throws DatabaseConnectionException {
		if (page.isEmpty()) {
			return new ArrayList<Company>(0);
		}
		List<Company> companiesList = companyDao.getCompaniesListPage(page);
		return companiesList;
	}
	
	/**
	 * Return the total number of companies.
	 * @return companies count
	 * @throws DatabaseConnectionException
	 */
	public Integer getCompaniesCount() throws DatabaseConnectionException {
		Integer companiesCount;
		companiesCount = companyDao.getCompaniesCount();
		return companiesCount;
	}
	
	/**
	 * Delete a company and all computer of this company.
	 * @param companyId the company id
	 * @throws DatabaseConnectionException
	 */
	public void deleteCompanyById(Integer companyId) throws DatabaseConnectionException {
		companyDao.deleteCompanyById(companyId);
	}
}
