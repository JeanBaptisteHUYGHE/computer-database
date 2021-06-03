package com.excilys.cdb.persistance;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DaoMapperException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.enumeration.ComputerRequestEnum;
import com.excilys.cdb.persistance.mapper.ComputerDaoMapper;

public class ComputerDao {
	
	private static ComputerDao instance;
	private ComputerDaoMapper computerDaoMapper;
	private Logger logger;
	
	
	/**
	 * Return the computer Dao instance (singleton).
	 * @return the computer Dao instance
	 */
	public static ComputerDao getInstance() {
		if (instance == null) {
			instance = new ComputerDao();
		}
		return instance;
	}
	
	private ComputerDao() {
		computerDaoMapper = ComputerDaoMapper.getInstance();
		logger = LoggerFactory.getLogger(ComputerDao.class);
	}

	/**
	 * Return the computers list from the database in the page range.
	 * @param page the page
	 * @return the computer list page
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPage(Page page) throws DatabaseConnectionException {
		logger.debug("getComputersListPage({})", page);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.GET_COMPUTERS_LIST_BY_PAGE.get());
			preparedStatement.setInt(1, page.getSize());
			preparedStatement.setInt(2, page.getSize() * page.getIndex());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Computer> computersList = computerDaoMapper.fromResultSetToComputersList(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return computersList;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the computers list from the database in the page range and for a user search.
	 * @param search the user search
	 * @param page the page
	 * @return the computer list page
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPageForSearch(String search, Page page) throws DatabaseConnectionException {
		logger.debug("getComputersListPageForSearch({}, {})", search, page);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.GET_COMPUTERS_LIST_BY_PAGE_FOR_SEARCH.get());
			String searchExpression = "%" + search + "%";
			preparedStatement.setString(1, searchExpression);
			preparedStatement.setString(2, searchExpression);
			preparedStatement.setInt(3, page.getSize());
			preparedStatement.setInt(4, page.getSize() * page.getIndex());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Computer> computersList = computerDaoMapper.fromResultSetToComputersList(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return computersList;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Return the computer from the database.
	 * 
	 * @param id the computer id
	 * @return the computer
	 * @throws ComputerNotFoundException
	 * @throws DatabaseConnectionException 
	 */
	public Computer getComputerById(Integer id) throws ComputerNotFoundException, DatabaseConnectionException {
		logger.debug("getComputerById({})", id);

		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.GET_COMPUTER_BY_ID.get());
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Computer gettedComputer = computerDaoMapper.fromResultSetToComputer(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return gettedComputer;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Update the computer in the database.
	 * 
	 * @param computer the computer
	 * @throws DatabaseConnectionException 
	 */
	public void updateComputer(Computer computer) throws DatabaseConnectionException {
		logger.debug("updateComputer({})", computer);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.UPDATE_COMPUTER_BY_ID.get());
			
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
			
			if (!computer.getCompany().isPresent()) {
				preparedStatement.setNull(4, 0);
			} else {
				preparedStatement.setInt(4, computer.getCompany().get().getId());
			}
			
			preparedStatement.setInt(5, computer.getId().orElseThrow(() -> new IllegalArgumentException("Computer id is null")));
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			dbConnection.close();
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Add a new computer in the database.
	 * 
	 * @param computer the computer to add
	 * @throws DatabaseConnectionException 
	 */
	public void addComputer(Computer computer) throws DatabaseConnectionException {
		logger.debug("addComputer({})", computer);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.ADD_COMPUTER.get(), Statement.RETURN_GENERATED_KEYS);
			
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
			
			if (!computer.getCompany().isPresent() || computer.getCompany().get().getId() == null) {
				preparedStatement.setNull(4, 0);
			} else {
				preparedStatement.setInt(4, computer.getCompany().get().getId());
			}
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			int newId = resultSet.getInt(1);
			computer.setId(newId);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Delete a computer in the database.
	 * 
	 * @param id the computer id to remove
	 * @throws DatabaseConnectionException 
	 */
	public void deleteComputerById(Integer id) throws DatabaseConnectionException {
		logger.debug("deleteComputerById({})", id);
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.DELETE_COMPUTER_BY_ID.get());
			
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			dbConnection.close();
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the computers count.
	 * @return the computers number
	 * @throws DatabaseConnectionException
	 */
	public Integer getComputersCount() throws DatabaseConnectionException {
		logger.debug("getComputersCount()");
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.GET_COMPUTERS_COUNT.get());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Integer computersCount = computerDaoMapper.getComputersCount(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return computersCount;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the computers count for a search.
	 * @param search the search
	 * @return the computers number
	 * @throws DatabaseConnectionException
	 */
	public Integer getComputersCountForSearch(String search) throws DatabaseConnectionException {
		logger.debug("getComputersCountForSearch()");
		try (Connection dbConnection = DatabaseConnection.getInstance()) {
			PreparedStatement preparedStatement = dbConnection.prepareStatement(ComputerRequestEnum.GET_COMPUTERS_COUNT_FOR_SEARCH.get());
			String searchExpression = "%" + search + "%";
			preparedStatement.setString(1, searchExpression);
			preparedStatement.setString(2, searchExpression);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Integer computersCount = computerDaoMapper.getComputersCount(resultSet);
			
			resultSet.close();
			preparedStatement.close();
			dbConnection.close();
			return computersCount;
			
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		} catch (DaoMapperException e) {
			throw new DatabaseConnectionException();
		}
	}
}
