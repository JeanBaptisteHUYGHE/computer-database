package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.ComputerDao;

public class ComputerService {
	
	private static ComputerService computerService;
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
		if (page.isEmpty()) {
			return new ArrayList<Computer>(0);
		}
		List<Computer> computersList = computerDao.getComputersListPage(page);
		return computersList;
	}
	
	/**
	 * Return the computers list of the asked page for an user search.
	 * @param search the user search
	 * @param page the page
	 * @return the computers list
	 * @throws DatabaseConnectionException
	 */
	public List<Computer> getComputersListPageForSearch(String search, Page page) throws DatabaseConnectionException {
		if (page.isEmpty()) {
			return new ArrayList<Computer>(0);
		}
		List<Computer> computersList = computerDao.getComputersListPageForSearch(search, page);
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
	 * Return the total number of computers.
	 * @return the computers count
	 * @throws DatabaseConnectionException
	 */
	public Integer getComputersCount() throws DatabaseConnectionException {
		Integer computersCount;
		computersCount = computerDao.getComputersCount();
		return computersCount;
	}
	
	/**
	 * Return the total number of computers for a search.
	 * @param search the search
	 * @return the computers count
	 * @throws DatabaseConnectionException
	 */
	public Integer getComputersCountForSearch(String search) throws DatabaseConnectionException {
		Integer computersCount;
		computersCount = computerDao.getComputersCountForSearch(search);
		return computersCount;
	}
}
