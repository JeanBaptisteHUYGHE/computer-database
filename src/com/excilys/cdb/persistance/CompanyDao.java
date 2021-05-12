package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class CompanyDao {

	/**
	 * Return the companies list from the database
	 * @return the companies list
	 * @throws SQLException
	 */
	@Deprecated
	public static List<Company> getCompaniesList() throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name FROM company ORDER BY id";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		ResultSet resultSet = statement.executeQuery();
		
		return CompanyDaoMapper.getCompaniesList(resultSet);
	}
	
	/**
	 * Return the companies list from the database in the page range
	 * @param pageIndex the page index
	 * @param pageSize the page size
	 * @return the companies list page
	 * @throws SQLException
	 */
	public static List<Company> getCompaniesListPage(int pageIndex, int pageSize) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name "
				+ "FROM company "
				+ "ORDER BY id "
				+ "LIMIT ? OFFSET ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, pageSize);
		statement.setInt(2, pageSize * pageIndex);
		ResultSet resultSet = statement.executeQuery();
		return CompanyDaoMapper.getCompaniesList(resultSet);
	}
	
	/**
	 * Return the company from the database
	 * @param company the company (just the id is used)
	 * @return the company
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public static Company getCompany(Company company) throws NoSuchElementException, SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name FROM company WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, company.getId());
		ResultSet resultSet = statement.executeQuery();
		return CompanyDaoMapper.getCompany(resultSet);
	}
}
