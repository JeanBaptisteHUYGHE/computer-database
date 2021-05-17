package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Computer;

public class ComputerDao {

	/**
	 * Return the computers list from the database in the page range
	 * @param pageIndex the page index
	 * @param pageSize the page size
	 * @return the computer list page
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPage(int pageIndex, int pageSize) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name, introduced, discontinued, company_id "
				+ "FROM computer "
				+ "ORDER BY id "
				+ "LIMIT ? OFFSET ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, pageSize);
		statement.setInt(2, pageSize * pageIndex);
		ResultSet resultSet = statement.executeQuery();
		return ComputerDaoMapper.getComputersList(resultSet);
	}

	/**
	 * Return the computer from the database
	 * 
	 * @return the computer
	 * @throws SQLException
	 * @throws NoSuchElementException
	 */
	public Computer getComputer(Computer computer) throws NoSuchElementException, SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT id, name, introduced, discontinued, company_id "
				+ "FROM computer "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, computer.getId());
		ResultSet resultSet = statement.executeQuery();
		return ComputerDaoMapper.getComputer(resultSet);
	}

	/**
	 * Update the computer in the database
	 * 
	 * @return the computer
	 * @throws SQLException
	 */
	public void updateComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "UPDATE computer " + "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setString(1, computer.getName());
		if (computer.getIntroductionDate() == null) {
			statement.setNull(2, 0);
		} else {
			statement.setDate(2, Date.valueOf(computer.getIntroductionDate()));
		}
		if (computer.getDiscontinueDate() == null) {
			statement.setNull(3, 0);
		} else {
			statement.setDate(3, Date.valueOf(computer.getDiscontinueDate()));
		}
		if (computer.getManufacturer() == null || computer.getManufacturer().getId() == null) {
			statement.setNull(4, 0);
		} else {
			statement.setInt(4, computer.getManufacturer().getId());
		}
		statement.setInt(5, computer.getId());
		statement.executeUpdate();
	}

	/**
	 * Add a new computer in the database
	 * 
	 * @param computer the computer to add
	 * @throws SQLException
	 */
	public void addComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "INSERT INTO computer (name, introduced, discontinued, company_id) " + "VALUES (?, ?, ?, ?)";
		PreparedStatement statement = dbConnection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, computer.getName());
		if (computer.getIntroductionDate() == null) {
			statement.setNull(2, 0);
		} else {
			statement.setDate(2, Date.valueOf(computer.getIntroductionDate()));
		}
		if (computer.getDiscontinueDate() == null) {
			statement.setNull(3, 0);
		} else {
			statement.setDate(3, Date.valueOf(computer.getDiscontinueDate()));
		}
		if (computer.getManufacturer() == null || computer.getManufacturer().getId() == null) {
			statement.setNull(4, 0);
		} else {
			statement.setInt(4, computer.getManufacturer().getId());
		}
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		int newId = resultSet.getInt(1);
		computer.setId(newId);
	}

	/**
	 * Delete a computer in the database
	 * 
	 * @param computer the computer to remove
	 * @throws SQLException
	 */
	public void deleteComputer(Computer computer) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "DELETE FROM computer WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(request);
		statement.setInt(1, computer.getId());
		statement.executeUpdate();
	}
}
