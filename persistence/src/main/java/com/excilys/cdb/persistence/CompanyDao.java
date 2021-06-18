package com.excilys.cdb.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.dto.CompanyPDto;
import com.excilys.cdb.persistence.dto.mapper.CompanyPDtoMapper;
import com.excilys.cdb.persistence.enumeration.CompanyRequestEnum;
import com.excilys.cdb.persistence.exception.CompanyNotFoundException;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;

@Repository
@Transactional
public class CompanyDao {
	
	private Logger logger;
	private SessionFactory sessionFactory;
	private CompanyPDtoMapper companyPDtoMapper;
	
	public CompanyDao(SessionFactory sessionFactory, CompanyPDtoMapper companyPDtoMapper) {
		logger = LoggerFactory.getLogger(CompanyDao.class);
		
		this.sessionFactory = sessionFactory;
		
	    this.companyPDtoMapper = companyPDtoMapper;
	}
	
	/**
	 * Return the companies list from the database.
	 * @return the companies list page
	 * @throws DatabaseConnectionException 
	 */
	public List<Company> getCompaniesList() throws DatabaseConnectionException {
		logger.debug("getCompaniesList()");
		try {
			Query<CompanyPDto> query = sessionFactory.getCurrentSession().createQuery(
					CompanyRequestEnum.GET_ALL_COMPANIES.get(),
					CompanyPDto.class);
			List<CompanyPDto> results = query.list();
			
			List<Company> companiesList = results.stream()
					.map(companyPDto -> companyPDtoMapper.fromCompanyPDtoToCompany(companyPDto))
					.collect(Collectors.toList());
			return companiesList;
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the companies list from the database in the page range.
	 * @param page the page
	 * @return the companies list page
	 * @throws DatabaseConnectionException
	 */
	public List<Company> getCompaniesListPage(Page page) throws DatabaseConnectionException {
		logger.debug("getCompaniesListPage({})", page);
		
		try {
			
			Query<CompanyPDto> query = sessionFactory.getCurrentSession().createQuery(
					CompanyRequestEnum.GET_ALL_COMPANIES.get(),
					CompanyPDto.class);
			query.setFirstResult(page.getIndex() * page.getSize());
			query.setMaxResults(page.getSize());
			
			List<CompanyPDto> results = query.list();
			
			List<Company> companiesList = results.stream()
					.map(companyPDto -> companyPDtoMapper.fromCompanyPDtoToCompany(companyPDto))
					.collect(Collectors.toList());
			return companiesList;
						
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the company from the database.
	 * @param companyId the company id
	 * @return the company
	 * @throws CompanyNotFoundException
	 * @throws DatabaseConnectionException
	 */
	public Company getCompanyById(Integer companyId) throws CompanyNotFoundException, DatabaseConnectionException {
		logger.debug("getCompanyById({})", companyId);
		
		try {
			Query<CompanyPDto> query = sessionFactory.getCurrentSession().createQuery(
					CompanyRequestEnum.GET_COMPANY_BY_ID.get(),
					CompanyPDto.class);
			query.setParameter("id", companyId);
			query.setMaxResults(1);
			
			List<CompanyPDto> companyPDtoList = query.list();
			
			if (companyPDtoList.isEmpty()) {
				throw new CompanyNotFoundException();
			}		
			
			return companyPDtoMapper.fromCompanyPDtoToCompany(companyPDtoList.get(0));
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the companies count.
	 * @return the companies number
	 * @throws DatabaseConnectionException
	 */
	public Integer getCompaniesCount() throws DatabaseConnectionException {
		logger.debug("getCompaniesCount()");
		try {
			Query<Long> query = sessionFactory.getCurrentSession().createQuery(
					CompanyRequestEnum.GET_COMPANIES_COUNT.get(),
					Long.class);
						
			return query.uniqueResult().intValue();
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Delete a company and all computer of this company from database.
	 * @param companyId the company id
	 * @throws DatabaseConnectionException
	 */
	public void deleteCompanyById(Integer companyId) throws DatabaseConnectionException {
		logger.debug("deleteCompanyById({})", companyId);
		
		try {
			Session session = sessionFactory.getCurrentSession();
			
			Query<?> query = session.createQuery(
					CompanyRequestEnum.DELETE_COMPANY_BY_ID.get());
			query.setParameter("id", companyId);
			
			query.executeUpdate();
			
			session.getTransaction().commit();
		
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
}
