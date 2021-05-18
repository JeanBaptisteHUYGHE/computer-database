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
	
	private static ComputerDao instance = null;
	
	/**
	 * Return the computer Dao instance (singleton)
	 * @return the computer Dao instance
	 */
	public static ComputerDao getInstance() {
		if (instance == null) {
			instance = new ComputerDao();
		}
		return instance;
	}
	
	private ComputerDao() {}

	/**
	 * Return the computers list from the database in the page range
	 * @param pageIndex the page index
	 * @param pageSize the page size
	 * @return the computer list page
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPage(int pageIndex, int pageSize) throws SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as company_name "
				+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
				+ "ORDER BY computer.id "
				+ "LIMIT ? OFFSET ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		preparedStatement.setInt(1, pageSize);
		preparedStatement.setInt(2, pageSize * pageIndex);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<Computer> computersList = new ComputerDaoMapper().getComputersList(resultSet);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
		return computersList;
	}

	/**
	 * Return the computer from the database
	 * 
	 * @return the computer
	 * @throws IllegalArgumentException
	 * @throws NoSuchElementException
	 * @throws SQLException
	 */
	public Computer getComputer(Computer computer) throws IllegalArgumentException, NoSuchElementException, SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "SELECT computer.id as id, computer.name as name, introduced, discontinued, company_id, company.name as company_name "
				+ "FROM computer LEFT JOIN company ON computer.company_id = company.id "
				+ "WHERE computer.id = ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		Integer id = computer.getId().orElseThrow(() -> new IllegalArgumentException("Computer id null"));
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		Computer gettedComputer = new ComputerDaoMapper().getComputer(resultSet);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
		return gettedComputer;
	}

	/**
	 * Update the computer in the database
	 * 
	 * @return the computer
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	public void updateComputer(Computer computer) throws IllegalArgumentException, SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "UPDATE computer "
				+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
				+ "WHERE id = ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		preparedStatement.setString(1, computer.getName());
		if (!computer.getIntroductionDate().isPresent()) {
			preparedStatement.setNull(2, 0);
		} else {
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroductionDate().get()));
		}
		if (!computer.getDiscontinueDate().isPresent()) {
			preparedStatement.setNull(3, 0);
		} else {
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinueDate().get()));
		}
		if (!computer.getManufacturer().isPresent()) {
			preparedStatement.setNull(4, 0);
		} else {
			preparedStatement.setInt(4, computer.getManufacturer().get().getId());
		}
		preparedStatement.setInt(5, computer.getId().orElseThrow(() -> new IllegalArgumentException("Computer id is null")));
		preparedStatement.executeUpdate();
		preparedStatement.close();
		dbConnection.close();
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
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, computer.getName());
		if (!computer.getIntroductionDate().isPresent()) {
			preparedStatement.setNull(2, 0);
		} else {
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroductionDate().get()));
		}
		if (!computer.getDiscontinueDate().isPresent()) {
			preparedStatement.setNull(3, 0);
		} else {
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinueDate().get()));
		}
		if (!computer.getManufacturer().isPresent()) {
			preparedStatement.setNull(4, 0);
		} else {
			preparedStatement.setInt(4, computer.getManufacturer().get().getId());
		}
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		int newId = resultSet.getInt(1);
		computer.setId(newId);
		resultSet.close();
		preparedStatement.close();
		dbConnection.close();
	}

	/**
	 * Delete a computer in the database
	 * 
	 * @param computer the computer to remove
	 * @throws IllegalArgumentException
	 * @throws SQLException
	 */
	public void deleteComputer(Computer computer) throws IllegalArgumentException, SQLException {
		Connection dbConnection = Database.getConnection();
		String request = "DELETE FROM computer WHERE id = ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
		Integer id = computer.getId().orElseThrow(() -> new IllegalArgumentException("Computer id is null"));
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		dbConnection.close();
	}
}
