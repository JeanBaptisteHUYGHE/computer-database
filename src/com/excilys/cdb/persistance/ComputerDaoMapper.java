package com.excilys.cdb.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            LocalDate introductionDate = resultSet.getDate("computer.introduced").toLocalDate();
            LocalDate discontinuedDate = resultSet.getDate("computer.discontinued").toLocalDate();
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
	 */
	public static Computer getComputer(ResultSet resultSet) throws SQLException {
		resultSet.next();
		Integer computerId = resultSet.getInt("computer.id");
		String computerName = resultSet.getString("computer.name");
		LocalDate introductionDate = resultSet.getDate("computer.introduced").toLocalDate();
		LocalDate discontinuedDate = resultSet.getDate("computer.discontinued").toLocalDate();
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
