package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.cdb.model.Computer;

public class ComputerDao {

	/**
	 * Return the computers list from the database
	 * @return the computers list
	 * @throws SQLException
	 */
	public static List<Computer> getComputersList() throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, "
				+ "company.id, company.name "
				+ "FROM computer INNER JOIN company ON computer.company_id = company.id";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		ResultSet resultSet = statement.executeQuery();
		
        return ComputerDaoMapper.getComputersList(resultSet);
	}
	
	/**
	 * Return the computer  from the database
	 * @return the computer
	 * @throws SQLException
	 */
	public static Computer getComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, "
				+ "company.id, company.name "
				+ "FROM computer INNER JOIN company ON computer.company_id = company.id "
				+ "WHERE computer.id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, computer.getId());
		ResultSet resultSet = statement.executeQuery();
		return ComputerDaoMapper.getComputer(resultSet);
	}
		
	/**
	 * Update the computer in the database
	 * @return the computer
	 * @throws SQLException
	 */
	public static void updateComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "UPDATE computer "
				+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setString(1, computer.getName());
		statement.setDate(2, Date.valueOf(computer.getIntroductionDate()));
		statement.setDate(3, Date.valueOf(computer.getDiscontinueDate()));
		if (computer.getManufacturer() == null || computer.getManufacturer().getId() == null) {
			statement.setNull(4, 0);
		}
		else {
			statement.setInt(4, computer.getManufacturer().getId());
		}
		statement.setInt(5, computer.getId());
		statement.executeUpdate();
	}
	
	/**
	 * Add a new computer in the database
	 * @param computer the computer to add
	 * @throws SQLException
	 */
	public static void addComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "INSERT INTO computer (name, introduced, discontinued, company_id) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, computer.getName());
		statement.setDate(2, Date.valueOf(computer.getIntroductionDate()));
		statement.setDate(3, Date.valueOf(computer.getDiscontinueDate()));
		if (computer.getManufacturer() == null || computer.getManufacturer().getId() == null) {
			statement.setNull(4, 0);
		}
		else {
			statement.setInt(4, computer.getManufacturer().getId());
		}


		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.first();
		computer.setId(resultSet.getInt(1));
	}
	
	/**
	 * Delete a computer in the database
	 * @param computer the computer to remove
	 * @throws SQLException
	 */
	public static void deleteComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "DELETE FROM computer WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, computer.getId());
		statement.executeUpdate();
	}
	
	
}
