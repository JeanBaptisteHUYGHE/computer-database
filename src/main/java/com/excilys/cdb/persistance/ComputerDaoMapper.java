package com.excilys.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDaoMapper {
	
	/**
	 * Return the computer read from a result set.
	 * @param resultSet the result set
	 * @return the correspondent computer
	 * @throws SQLException
	 */
	private Computer readComputerLine(ResultSet resultSet) throws SQLException {
		Integer computerId = resultSet.getInt("id");
		String computerName = resultSet.getString("name");
        LocalDate introductionDate = null;
        try {
        	introductionDate = resultSet.getDate("introduced").toLocalDate();
        } catch (NullPointerException e) { }
        LocalDate discontinuedDate = null;
        try {
        	discontinuedDate = resultSet.getDate("discontinued").toLocalDate();
        } catch (NullPointerException e) { }
        Integer companyId = null;
        try {
        	companyId = resultSet.getInt("company_id");
			if (companyId == 0) {
        		companyId = null;
        	}
        } catch (NullPointerException e) { }
        String companyName = null;
        try {
        	companyName = resultSet.getString("company_name");
        } catch (NullPointerException e) { }
        Company company = null;
        if (companyId != null) {
        	company = new Company(companyId, companyName);
        }
        Computer computer = new Computer.ComputerBuilder().withId(companyId)
        		.withName(computerName)
        		.withIntroductionDate(introductionDate)
        		.withDiscontinueDate(discontinuedDate)
        		.withManufacturer(company)
        		.build();
        return computer;
	}

	/**
	 * Return the computers list.
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return computers list
	 * @throws SQLException
	 */
	public List<Computer> getComputersList(ResultSet resultSet) throws SQLException {
		ArrayList<Computer> computersList = new ArrayList<Computer>();
		while (resultSet.next()) {
            computersList.add(readComputerLine(resultSet));
		}
        return computersList;
	}
	
	/**
	 * Return the computer.
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return the computer 
	 * @throws SQLException
	 * @throws NoSuchElementException
	 */
	public Computer getComputer(ResultSet resultSet) throws NoSuchElementException, SQLException {
		resultSet.next();
		if (resultSet.getRow() == 0) {
			throw new NoSuchElementException("Computer not found");
		}
        return readComputerLine(resultSet);
	}
}
