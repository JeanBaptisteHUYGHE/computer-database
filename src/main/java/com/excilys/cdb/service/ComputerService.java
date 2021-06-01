package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.ComputerDao;

public class ComputerService {
	
	private static ComputerService computerService = null;
	private ComputerDao computerDao;
	
	public static ComputerService getInstance() {
		if (computerService == null) {
			computerService = new ComputerService();
		}
		return computerService;
	}
	
	private ComputerService() {
		computerDao = ComputerDao.getInstance();
	}

	/**
	 * Return the computers list of the asked page.
	 * @param page the page
	 * @return the computers list
	 * @throws DatabaseConnectionException
	 */
	public List<Computer> getComputersListPage(Page page) throws DatabaseConnectionException {
		List<Computer> computersList = null;
		computersList = computerDao.getComputersListPage(page);
		return computersList;
	}

	/**
	 * Get a computer by is id.
	 * @param id the computer id
	 * @return the computer
	 * @throws ComputerNotFoundException if the correspondent computer isn't found
	 * @throws DatabaseConnectionException
	 */
	public Computer getComputerById(Integer id) throws ComputerNotFoundException, DatabaseConnectionException {
		Computer computer = null;
		computer = computerDao.getComputerById(id);
		return computer;
	}

	/**
	 * Add a new computer in the database.
	 * @param newComputer the computer to add
	 * @throws DatabaseConnectionException
	 */
	public void addComputer(Computer newComputer) throws DatabaseConnectionException {
		computerDao.addComputer(newComputer);
	}

	/**
	 * Update a computer.
	 * @param computer the computer
	 * @throws DatabaseConnectionException 
	 * @throws DatabaseConnectionException
	 */
	public void updateComputer(Computer computer) throws DatabaseConnectionException {
		computerDao.updateComputer(computer);
	}

	/**
	 * Delete a computer.
	 * @param id the computer id
	 * @throws DatabaseConnectionException 
	 */
	public void deleteComputer(Integer id) throws DatabaseConnectionException {
		computerDao.deleteComputerById(id);
	}
	
	/**
	 * Return the total number of computers
	 * @return the computers count
	 * @throws DatabaseConnectionException
	 */
	public Integer getComputerCount() throws DatabaseConnectionException {
		Integer computersCount;
		computersCount = computerDao.getComputersCount();
		return computersCount;
	}
}
