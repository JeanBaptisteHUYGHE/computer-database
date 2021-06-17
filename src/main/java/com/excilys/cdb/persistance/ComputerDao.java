package com.excilys.cdb.persistance;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.dao.ComputerNotFoundException;
import com.excilys.cdb.exception.dao.DatabaseConnectionException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistance.dto.ComputerPDto;
import com.excilys.cdb.persistance.dto.mapper.ComputerPDtoMapper;
import com.excilys.cdb.persistance.enumeration.ComputerRequestEnum;

@Repository
@Transactional
public class ComputerDao {
	
	private Logger logger;
	private SessionFactory sessionFactory;
	private ComputerPDtoMapper computerPDtoMapper;
	
	public ComputerDao(SessionFactory sessionFactory,  ComputerPDtoMapper computerPDtoMapper) {
		logger = LoggerFactory.getLogger(ComputerDao.class);
		
		this.sessionFactory = sessionFactory;
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
			
			Query<ComputerPDto> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.GET_COMPUTERS_LIST_FOR_PAGE.get(),
					ComputerPDto.class);
			query.setFirstResult(page.getIndex() * page.getSize());
			query.setMaxResults(page.getSize());
			
			List<ComputerPDto> results = query.list();
			
			List<Computer> computersList = results.stream()
					.map(computerPDto -> computerPDtoMapper.fromComputerPDtoToComputer(computerPDto))
					.collect(Collectors.toList());
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
			Query<ComputerPDto> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.GET_COMPUTERS_LIST_BY_PAGE_FOR_SEARCH.get(),
					ComputerPDto.class);
			String sqlSearch = "%" + search + "%";
			query.setFirstResult(page.getIndex() * page.getSize());
			query.setMaxResults(page.getSize());
			query.setParameter("computerNameSearch", sqlSearch);
			query.setParameter("companyNameSearch", sqlSearch);
			
			List<ComputerPDto> results = query.list();
			
			List<Computer> computersList = results.stream()
					.map(computerPDto -> computerPDtoMapper.fromComputerPDtoToComputer(computerPDto))
					.collect(Collectors.toList());
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
			
			Query<ComputerPDto> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.GET_COMPUTER_BY_ID.get(),
					ComputerPDto.class);
			query.setParameter("id", id);
			
			List<ComputerPDto> computersPDtoList = query.list();
			
			if (computersPDtoList.isEmpty()) {
				throw new ComputerNotFoundException();
			}
			return computerPDtoMapper.fromComputerPDtoToComputer(computersPDtoList.get(0));
			
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
			Query<?> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.UPDATE_COMPUTER_BY_ID.get());
			
			query.setParameter("id", computerPDto.getId());
			query.setParameter("name", computerPDto.getName());
			query.setParameter("introductionDate", computerPDto.getIntroductionDate());
			query.setParameter("discontinueDate", computerPDto.getDiscontinueDate());
			query.setParameter("companyPDto", computerPDto.getCompanyPDto());
			
			query.executeUpdate();
						
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
			sessionFactory.getCurrentSession().save(computerPDto);
						
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
			
			Query<?> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.DELETE_COMPUTER_BY_ID.get());
			
			query.setParameter("id", id);

			
			query.executeUpdate();
						
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
			
			Query<Long> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.GET_COMPUTERS_COUNT.get(),
					Long.class);
						
			return query.uniqueResult().intValue();
					
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
		logger.debug("getComputersCountForSearch({})", search);
		
		try {
			String sqlSearch = "%" + search + "%";
			Query<Long> query = sessionFactory.getCurrentSession().createQuery(
					ComputerRequestEnum.GET_COMPUTERS_COUNT_FOR_SEARCH.get(),
					Long.class);
			query.setParameter("computerNameSearch", sqlSearch);
			query.setParameter("companyNameSearch", sqlSearch);
						
			return query.uniqueResult().intValue();
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
