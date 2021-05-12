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
			Integer computerId = resultSet.getInt("id");
			String computerName = resultSet.getString("name");
            LocalDate introductionDate = null;
            try {
            	introductionDate = resultSet.getDate("introduced").toLocalDate();
            }
            catch (NullPointerException e) {}
            LocalDate discontinuedDate = null;
            try {
            	discontinuedDate = resultSet.getDate("discontinued").toLocalDate();
            }
            catch (NullPointerException e) {}
            Integer companyId = null;
            try {
            	companyId = resultSet.getInt("company_id");
    			if (companyId == 0) {
            		companyId = null;
            	};
            }
            catch (NullPointerException e) {}
            Computer computer = new Computer(computerId,
            		computerName,
            		introductionDate,
            		discontinuedDate,
            		new Company(companyId, null));
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
		Integer computerId = resultSet.getInt("id");
		String computerName = resultSet.getString("name");
		LocalDate introductionDate = null;
		try {
        	introductionDate = resultSet.getDate("introduced").toLocalDate();
        }
        catch (NullPointerException e) {}
        LocalDate discontinuedDate = null;
        try {
        	discontinuedDate = resultSet.getDate("discontinued").toLocalDate();
        }
        catch (NullPointerException e) {}
        Integer companyId = null;
        try {
        	companyId = resultSet.getInt("company_id");
        	if (companyId == 0) {
        		companyId = null;
        	}
        }
        catch (NullPointerException e) {}
        Computer computer = new Computer(computerId,
        		computerName,
        		introductionDate,
        		discontinuedDate,
        		new Company(companyId, null));
        return computer;
	}
}
