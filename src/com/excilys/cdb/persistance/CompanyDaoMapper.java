package com.excilys.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

public class CompanyDaoMapper {

	/**
	 * Return the companies list
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return companies list
	 * @throws SQLException
	 */
	public static List<Company> getCompaniesList(ResultSet resultSet) throws SQLException {
		ArrayList<Company> companiesList = new ArrayList<Company>();
		while (resultSet.next()) {
			Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Company company = new Company(id, name);
            companiesList.add(company);
		}
        return companiesList;
	}
}
