package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistance.CompanyDao;

public class CompanyService {

	/**
	 * Return the company
	 * @param company the company
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Company getCompany(Company company) throws NoSuchElementException, SQLException {
		return CompanyDao.getInstance().getCompany(company);
	}

	/**
	 * Return a list of companies by page
	 * @param pageIndex page index
	 * @param pageSize page size
	 * @return the computer list
	 * @throws SQLException
	 */
	public List<Company> getCompaniesListPage(int pageIndex, int pageSize) throws SQLException {
		return CompanyDao.getInstance().getCompaniesListPage(pageIndex, pageSize);
	}
}
