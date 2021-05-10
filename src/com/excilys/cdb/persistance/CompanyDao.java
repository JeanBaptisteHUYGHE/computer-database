package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class CompanyDao {

	/**
	 * Return the companies list from the database
	 * @return the companies list
	 * @throws SQLException
	 */
	public static List<Company> getCompaniesList() throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name FROM company";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		ResultSet resultSet = statement.executeQuery();
		
		return CompanyDaoMapper.getCompaniesList(resultSet);
	}
}
