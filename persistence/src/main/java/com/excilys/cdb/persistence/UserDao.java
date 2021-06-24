package com.excilys.cdb.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.dto.UserPDto;
import com.excilys.cdb.persistence.dto.mapper.UserPDtoMapper;
import com.excilys.cdb.persistence.enumeration.UserRequestEnum;
import com.excilys.cdb.persistence.exception.DatabaseConnectionException;
import com.excilys.cdb.persistence.exception.UserNotFoundException;

@Repository
@Transactional
public class UserDao {
	
	private Logger logger;
	private SessionFactory sessionFactory;
	private UserPDtoMapper userPDtoMapper;
	
	public UserDao(SessionFactory sessionFactory, UserPDtoMapper userPDtoMapper) {
		logger = LoggerFactory.getLogger(UserDao.class);
		
		this.sessionFactory = sessionFactory;
		
	    this.userPDtoMapper = userPDtoMapper;
	}
	
	/**
	 * Return the users list from the database.
	 * @return the users list page
	 * @throws DatabaseConnectionException 
	 */
	public List<User> getUsersList() throws DatabaseConnectionException {
		logger.debug("getUsersList()");
		try {
			Query<UserPDto> query = sessionFactory.getCurrentSession().createQuery(
					UserRequestEnum.GET_ALL_USERS.get(),
					UserPDto.class);
			List<UserPDto> results = query.list();
			
			List<User> usersList = results.stream()
					.map(userPDto -> userPDtoMapper.fromUserPDtoToUser(userPDto))
					.collect(Collectors.toList());
			return usersList;
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the user from the database by is id.
	 * @param userId the user id
	 * @return the user
	 * @throws UserNotFoundException
	 * @throws DatabaseConnectionException
	 */
	public User getUserById(Integer userId) throws UserNotFoundException, DatabaseConnectionException {
		logger.debug("getUserById({})", userId);
		
		try {
			Query<UserPDto> query = sessionFactory.getCurrentSession().createQuery(
					UserRequestEnum.GET_USER_BY_ID.get(),
					UserPDto.class);
			query.setParameter("id", userId);
			query.setMaxResults(1);
			
			List<UserPDto> usersPDtoList = query.list();
			
			if (usersPDtoList.isEmpty()) {
				throw new UserNotFoundException();
			}		
			
			return userPDtoMapper.fromUserPDtoToUser(usersPDtoList.get(0));
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
	/**
	 * Return the user from the database by is login.
	 * @param login the user login
	 * @return the user
	 * @throws UserNotFoundException
	 * @throws DatabaseConnectionException
	 */
	public User getUserByLogin(String login) throws UserNotFoundException, DatabaseConnectionException {
		logger.debug("getUserByLogin({})", login);
		
		try {
			Query<UserPDto> query = sessionFactory.getCurrentSession().createQuery(
					UserRequestEnum.GET_USER_BY_LOGIN.get(),
					UserPDto.class);
			query.setParameter("login", login);
			query.setMaxResults(1);
			
			List<UserPDto> usersPDtoList = query.list();
			
			if (usersPDtoList.isEmpty()) {
				throw new UserNotFoundException();
			}		
			
			return userPDtoMapper.fromUserPDtoToUser(usersPDtoList.get(0));
			
		} catch (DataAccessException e) {
			logger.error("{} in {}", e, e.getStackTrace());
			throw new DatabaseConnectionException();
		}
	}
	
}
