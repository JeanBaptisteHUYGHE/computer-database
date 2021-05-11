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
	 * Return the computers list
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return computers list
	 * @throws SQLException
	 */
	public static List<Computer> getComputersList(ResultSet resultSet) throws SQLException {
		ArrayList<Computer> computersList = new ArrayList<Computer>();
		while (resultSet.next()) {
			Integer computerId = resultSet.getInt("computer.id");
			String computerName = resultSet.getString("computer.name");
            LocalDate introductionDate = null;
            try {
            	introductionDate = resultSet.getDate("computer.introduced").toLocalDate();
            }
            catch (NullPointerException e) {}
            LocalDate discontinuedDate = null;
            try {
            	discontinuedDate = resultSet.getDate("computer.discontinued").toLocalDate();
            }
            catch (NullPointerException e) {}
            Integer companyId = resultSet.getInt("company.id");
            String companyName = resultSet.getString("company.name");
            Computer computer = new Computer(computerId,
            		computerName,
            		introductionDate,
            		discontinuedDate,
            		new Company(companyId, companyName));
            computersList.add(computer);
		}
        return computersList;
	}
	
	/**
	 * Return the computer
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return the computer 
	 * @throws SQLException
	 * @throws NoSuchElementException
	 */
	public static Computer getComputer(ResultSet resultSet) throws NoSuchElementException, SQLException {
		resultSet.next();
		if (resultSet.getRow() == 0) {
			throw new NoSuchElementException();
		}
		Integer computerId = resultSet.getInt("computer.id");
		String computerName = resultSet.getString("computer.name");
		LocalDate introductionDate = null;
		try {
        	introductionDate = resultSet.getDate("computer.introduced").toLocalDate();
        }
        catch (NullPointerException e) {}
        LocalDate discontinuedDate = null;
        try {
        	discontinuedDate = resultSet.getDate("computer.discontinued").toLocalDate();
        }
        catch (NullPointerException e) {}
        Integer companyId = resultSet.getInt("company.id");
        String companyName = resultSet.getString("company.name");
        Computer computer = new Computer(computerId,
        		computerName,
        		introductionDate,
        		discontinuedDate,
        		new Company(companyId, companyName));
        return computer;
	}
}
