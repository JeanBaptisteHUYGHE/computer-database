package com.excilys.cdb.persistance;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.dto.ComputerPDto;
import com.excilys.cdb.persistance.dto.mapper.ComputerPDtoMapper;
import com.excilys.cdb.persistance.dto.mapper.ComputerRowMapper;
import com.excilys.cdb.persistance.enumeration.ComputerRequestEnum;

@Repository
public class ComputerDao {
	
	private Logger logger;

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private ComputerRowMapper computerRowMapper;
	private ComputerPDtoMapper computerPDtoMapper;
	
	private ComputerDao(DataSource dataSource, ComputerRowMapper computerRowMapper, ComputerPDtoMapper computerPDtoMapper) {
		logger = LoggerFactory.getLogger(ComputerDao.class);
		
		jdbcTemplate = new JdbcTemplate(dataSource);
	    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    this.computerRowMapper = computerRowMapper;
	    this.computerPDtoMapper = computerPDtoMapper;	
	}

	/**
	 * Return the computers list from the database in the page range.
	 * @param page the page
	 * @return the computer list page
	 * @throws DatabaseConnectionException
	 */
	public List<Computer> getComputersListPage(Page page) throws DatabaseConnectionException {
		logger.debug("getComputersListPage({})", page);
		
		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("pageSize", page.getSize())
					.addValue("offset", page.getSize() * page.getIndex());
			List<Computer> computersList = namedParameterJdbcTemplate.query(ComputerRequestEnum.GET_COMPUTERS_LIST_FOR_PAGE.get(), requestParams, computerRowMapper);
			return computersList;
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
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
		
		try {
			String sqlSearch = "%" + search + "%";
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("pageSize", page.getSize())
					.addValue("offset", page.getSize() * page.getIndex())
					.addValue("computerNameSearch", sqlSearch)
					.addValue("companyNameSearch", sqlSearch);
			logger.debug("paramSource: {}",  requestParams.toString());

			
			List<Computer> computersList = namedParameterJdbcTemplate.query(ComputerRequestEnum.GET_COMPUTERS_LIST_BY_PAGE_FOR_SEARCH.get(), requestParams, computerRowMapper);
			return computersList;
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
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

		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("id", id);
			List<Computer> computersList = namedParameterJdbcTemplate.query(ComputerRequestEnum.GET_COMPUTER_BY_ID.get(),  requestParams, computerRowMapper);
			
			if (computersList.isEmpty()) {
				throw new ComputerNotFoundException();
			}
			return computersList.get(0);
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
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
		
		ComputerPDto computerPDto = computerPDtoMapper.fromComputerToComputerPDto(computer);
		
		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("id", computerPDto.getId())
					.addValue("name", computerPDto.getName())
					.addValue("introductionDate", computerPDto.getIntroductionDate())
					.addValue("discontinueDate", computerPDto.getDiscontinueDate())
					.addValue("companyId", computerPDto.getCompanyId());
			
			namedParameterJdbcTemplate.update(ComputerRequestEnum.UPDATE_COMPUTER_BY_ID.get(), requestParams);
			
		} catch (DataAccessException e) {
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
		
		ComputerPDto computerPDto = computerPDtoMapper.fromComputerToComputerPDto(computer);

		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("name", computerPDto.getName())
					.addValue("introductionDate", computerPDto.getIntroductionDate())
					.addValue("discontinueDate", computerPDto.getDiscontinueDate())
					.addValue("companyId", computerPDto.getCompanyId());
			
			namedParameterJdbcTemplate.update(ComputerRequestEnum.ADD_COMPUTER.get(), requestParams);
			
		} catch (DataAccessException e) {
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
		
		try {
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("id", id);
			
			namedParameterJdbcTemplate.update(ComputerRequestEnum.DELETE_COMPUTER_BY_ID.get(), requestParams);
			
		} catch (DataAccessException e) {
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
		
		try {
			return jdbcTemplate.queryForObject(ComputerRequestEnum.GET_COMPUTERS_COUNT.get(), Integer.class);
		
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
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
		
		try {
			String sqlSearch = "%" + search + "%";
			SqlParameterSource requestParams = new MapSqlParameterSource()
					.addValue("computerNameSearch", "%" + sqlSearch)
					.addValue("companyNameSearch", sqlSearch);

			return namedParameterJdbcTemplate.queryForObject(ComputerRequestEnum.GET_COMPUTERS_COUNT_FOR_SEARCH.get(), requestParams, Integer.class);
		
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
