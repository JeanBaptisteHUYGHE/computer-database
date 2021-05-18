package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistance.CompanyDao;
import com.excilys.cdb.persistance.ComputerDao;

public class ComputerService {

	/**
	 * Return a list of computer by page
	 * @param pageIndex page index
	 * @param pageSize page size
	 * @return the computer list
	 * @throws SQLException
	 */
	public List<Computer> getComputersListPage(int pageIndex, int pageSize) throws SQLException {
		return ComputerDao.getInstance().getComputersListPage(pageIndex, pageSize);
	}

	/**
	 * Return desire computer
	 * @param computerSrc the computer (just is id is checked)
	 * @return the correspondent computer in database
	 * @throws SQLException 
	 * @throws NoSuchElementException 
	 */
	public Computer getComputer(Computer computerSrc) throws NoSuchElementException, SQLException {
		return ComputerDao.getInstance().getComputer(computerSrc);
	}

	/**
	 * Add a new computer
	 * @param newComputer the computer
	 * @throws SQLException
	 */
	public void addComputer(Computer newComputer) throws SQLException {
		ComputerDao.getInstance().addComputer(newComputer);
	}

	/**
	 * Update a computer
	 * @param computer the computer
	 * @throws SQLException
	 */
	public void updateComputer(Computer computer) throws SQLException {
		ComputerDao.getInstance().updateComputer(computer);
	}

	/**
	 * Delete a computer
	 * @param computer
	 * @throws SQLException
	 */
	public void deleteComputer(Computer computer) throws SQLException {
		ComputerDao.getInstance().deleteComputer(computer);
	}
}
