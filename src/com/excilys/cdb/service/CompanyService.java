package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistance.CompanyDao;

public class CompanyService {

	/**
	 * Return a list of all companies
	 * @return companies list
	 * @throws SQLException 
	 */
	public List<Company> getCompaniesList() throws SQLException {
		return CompanyDao.getCompaniesList();
	}
	
	/**
	 * Return the company
	 * @param company the company
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Company getCompany(Company company) throws NoSuchElementException, SQLException {
		return CompanyDao.getCompany(company);
	}
}
