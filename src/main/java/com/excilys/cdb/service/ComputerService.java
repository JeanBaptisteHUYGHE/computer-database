package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.CompanyDao;
import com.excilys.cdb.persistance.ComputerDao;

public class ComputerService {
	
	private static ComputerService computerService = null;
	private Logger logger;
	
	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}
	
	private ComputerService() {
		logger = LoggerFactory.getLogger(ComputerService.class);
	}

	/**
	 * Return a list of computer by page
	 * @param pageIndex page index
	 * @param pageSize page size
	 * @return the computer list
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPage(int pageIndex, int pageSize) throws SQLException {
		logger.debug("getComputersListPage({}, {})", pageIndex, pageSize);
		List<Computer> computersList = null;
		try {
			computersList = ComputerDao.getInstance().getComputersListPage(pageIndex, pageSize);
		}
		catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
		return computersList;
	}

	/**
	 * Return desire computer
	 * @param computerSrc the computer (just is id is checked)
	 * @return the correspondent computer in database
	 * @throws SQLException 
	 * @throws NoSuchElementException 
	 */
	public Computer getComputer(Computer computerSrc) throws NoSuchElementException, SQLException {
		logger.debug("getComputer({})", computerSrc);
		Computer gettedComputer = null;
		try {
			gettedComputer = ComputerDao.getInstance().getComputer(computerSrc);
		}
		catch (NoSuchElementException e) {
			logger.info("{} in {}", e, e.getStackTrace());
			throw e;
		}
		catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
		return gettedComputer;
	}

	/**
	 * Add a new computer
	 * @param newComputer the computer
	 * @throws SQLException
	 */
	public void addComputer(Computer newComputer) throws SQLException {
		logger.debug("addComputer({})", newComputer);
		try {
			ComputerDao.getInstance().addComputer(newComputer);
		}
		catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
	}

	/**
	 * Update a computer
	 * @param computer the computer
	 * @throws SQLException
	 */
	public void updateComputer(Computer computer) throws SQLException {
		logger.debug("updateComputer({})", computer);
		try {
			ComputerDao.getInstance().updateComputer(computer);
		}
		catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
	}

	/**
	 * Delete a computer
	 * @param computer
	 * @throws SQLException
	 */
	public void deleteComputer(Computer computer) throws SQLException {
		logger.debug("deleteComputer({})", computer);
		try {
			ComputerDao.getInstance().deleteComputer(computer);
		}
		catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw e;
		}
	}
}
