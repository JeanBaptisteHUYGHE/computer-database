package com.excilys.cdb.persistance.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DaoMapperException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Company.CompanyBuilder;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class ComputerDaoMapper {
	
	private static ComputerDaoMapper computerDaoMapper;
	private Logger logger;
	
	public static ComputerDaoMapper getInstance() {
		if (computerDaoMapper == null) {
			computerDaoMapper = new ComputerDaoMapper();
		}
		return computerDaoMapper;
	}
	
	private ComputerDaoMapper() {
		logger = LoggerFactory.getLogger(ComputerDaoMapper.class);
	}
	
	/**
	 * Return the computer read from a result set.
	 * @param resultSet the result set
	 * @return the correspondent computer
	 * @throws SQLException
	 */
	private Computer readComputerLine(ResultSet resultSet) throws SQLException {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		computerBuilder.withId(resultSet.getInt("id"));
		computerBuilder.withName(resultSet.getString("name"));
		
        LocalDate introductionDate = null;
        try {
        	introductionDate = resultSet.getDate("introduced").toLocalDate();
        } catch (NullPointerException e) { }
        computerBuilder.withIntroductionDate(introductionDate);
        
        LocalDate discontinuedDate = null;
        try {
        	discontinuedDate = resultSet.getDate("discontinued").toLocalDate();
        } catch (NullPointerException e) { }
        computerBuilder.withDiscontinueDate(discontinuedDate);
        
        computerBuilder.withCompany(readCompanyPartOfComputerLine(resultSet));

        return computerBuilder.build();
	}
	
	/**
	 * Return the company read on the part of a computer result set.
	 * @param resultSet
	 * @return the company
	 * @throws SQLException
	 */
	private Company readCompanyPartOfComputerLine(ResultSet resultSet) throws SQLException {
		Integer companyId = null;
        try {
        	companyId = resultSet.getInt("company_id");
			if (companyId == 0) {
        		companyId = null;
        	}
        } catch (NullPointerException e) { }
        
        String companyName = null;
        try {
        	companyName = resultSet.getString("company_name");
        } catch (NullPointerException e) { }
        
        if (companyId == null && companyName == null) {
        	return null;
        } else {
        	return new CompanyBuilder().withId(companyId).withName(companyName).build();
        }
	}

	/**
	 * Return the computers list.
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return computers list
	 * @throws DaoMapperException
	 */
	public List<Computer> fromResultSetToComputersList(ResultSet resultSet) throws DaoMapperException {
		try {
			ArrayList<Computer> computersList = new ArrayList<Computer>();
			while (resultSet.next()) {
	            computersList.add(readComputerLine(resultSet));
			}
	        return computersList;
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DaoMapperException();
		}
	}
	
	/**
	 * Return the computer.
	 * @param resultSet the resultSet from the SQL request (DAO file)
	 * @return the computer 
	 * @throws ComputerNotFoundException
	 * @throws DaoMapperException
	 */
	public Computer fromResultSetToComputer(ResultSet resultSet) throws ComputerNotFoundException, DaoMapperException {
		try {
			resultSet.next();
			if (resultSet.getRow() == 0) {
				throw new ComputerNotFoundException();
			}
	        return readComputerLine(resultSet);
		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DaoMapperException();
		}
	}

	/**
	 * Return the computers count.
	 * @param resultSet the resultSet
	 * @return the computers number
	 * @throws DaoMapperException 
	 */
	public Integer getComputersCount(ResultSet resultSet) throws DaoMapperException {
		try {
			resultSet.next();
			return resultSet.getInt(1);

		} catch (SQLException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DaoMapperException();
		}
	}
}
